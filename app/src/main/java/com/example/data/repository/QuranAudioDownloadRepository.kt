package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.entity.CachedAudioEntity
import com.example.util.QuranReciter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

data class DownloadProgress(
    val surahNumber: Int,
    val reciterId: String,
    val progressPercent: Int = 0,
    val bytesDownloaded: Long = 0,
    val totalBytes: Long = 0,
    val isDownloading: Boolean = false,
    val isFailed: Boolean = false,
    val errorMessage: String? = null
)

object QuranAudioDownloadRepository {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val activeDownloads = mutableMapOf<String, Job>()

    private val _downloadStates = MutableStateFlow<Map<String, DownloadProgress>>(emptyMap())
    val downloadStates: StateFlow<Map<String, DownloadProgress>> = _downloadStates.asStateFlow()

    private fun getDownloadKey(surahNumber: Int, reciterId: String) = "surah_${surahNumber}_$reciterId"

    fun observeAllCachedAudio(context: Context): Flow<List<CachedAudioEntity>> {
        val db = AppDatabase.getDatabase(context)
        return db.cachedAudioDao().getAllCachedAudio()
    }

    suspend fun getCachedAudio(context: Context, surahNumber: Int, reciterId: String): CachedAudioEntity? {
        val db = AppDatabase.getDatabase(context)
        return db.cachedAudioDao().getCachedAudio(surahNumber, reciterId)
    }

    fun isSurahCachedLocally(context: Context, surahNumber: Int, reciter: QuranReciter): Boolean {
        val dir = File(context.getExternalFilesDir(null) ?: context.cacheDir, "quran_audio")
        val file = File(dir, "surah_${surahNumber}_${reciter.id}.mp3")
        return file.exists() && file.length() > 10000
    }

    fun startDownload(
        context: Context,
        surahNumber: Int,
        surahNameUrdu: String,
        surahNameEnglish: String,
        reciter: QuranReciter,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val key = getDownloadKey(surahNumber, reciter.id)
        if (activeDownloads[key]?.isActive == true) return

        val dir = File(context.getExternalFilesDir(null) ?: context.cacheDir, "quran_audio")
        if (!dir.exists()) dir.mkdirs()
        val targetFile = File(dir, "surah_${surahNumber}_${reciter.id}.mp3")

        updateProgress(key, DownloadProgress(surahNumber, reciter.id, isDownloading = true))

        val job = scope.launch {
            try {
                val audioUrl = String.format(reciter.audioUrlPattern, surahNumber)
                val url = URL(audioUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 20000
                connection.readTimeout = 20000
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    throw Exception("HTTP ${connection.responseCode}: ${connection.responseMessage}")
                }

                val totalLength = connection.contentLength.toLong()
                val input = connection.inputStream
                val output = FileOutputStream(targetFile)

                val buffer = ByteArray(8192)
                var bytesRead: Int
                var downloaded: Long = 0

                while (input.read(buffer).also { bytesRead = it } != -1) {
                    ensureActive()
                    output.write(buffer, 0, bytesRead)
                    downloaded += bytesRead
                    if (totalLength > 0) {
                        val percent = ((downloaded * 100) / totalLength).toInt().coerceIn(0, 100)
                        updateProgress(
                            key,
                            DownloadProgress(
                                surahNumber = surahNumber,
                                reciterId = reciter.id,
                                progressPercent = percent,
                                bytesDownloaded = downloaded,
                                totalBytes = totalLength,
                                isDownloading = true
                            )
                        )
                    }
                }

                output.flush()
                output.close()
                input.close()

                // Insert record into Room Database
                val db = AppDatabase.getDatabase(context)
                val entity = CachedAudioEntity(
                    id = key,
                    surahNumber = surahNumber,
                    surahNameUrdu = surahNameUrdu,
                    surahNameEnglish = surahNameEnglish,
                    reciterId = reciter.id,
                    reciterName = reciter.nameUrdu,
                    filePath = targetFile.absolutePath,
                    fileSizeBytes = targetFile.length(),
                    downloadedAt = System.currentTimeMillis()
                )
                db.cachedAudioDao().insertCachedAudio(entity)

                updateProgress(
                    key,
                    DownloadProgress(
                        surahNumber = surahNumber,
                        reciterId = reciter.id,
                        progressPercent = 100,
                        bytesDownloaded = targetFile.length(),
                        totalBytes = targetFile.length(),
                        isDownloading = false
                    )
                )

                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            } catch (e: Exception) {
                if (targetFile.exists()) targetFile.delete()
                updateProgress(
                    key,
                    DownloadProgress(
                        surahNumber = surahNumber,
                        reciterId = reciter.id,
                        isDownloading = false,
                        isFailed = true,
                        errorMessage = e.message
                    )
                )
                withContext(Dispatchers.Main) {
                    onError(e.message ?: "Download failed")
                }
            } finally {
                activeDownloads.remove(key)
            }
        }

        activeDownloads[key] = job
    }

    fun cancelDownload(surahNumber: Int, reciterId: String) {
        val key = getDownloadKey(surahNumber, reciterId)
        activeDownloads[key]?.cancel()
        activeDownloads.remove(key)
        _downloadStates.value = _downloadStates.value - key
    }

    suspend fun deleteCachedSurah(context: Context, surahNumber: Int, reciterId: String) {
        val key = getDownloadKey(surahNumber, reciterId)
        val dir = File(context.getExternalFilesDir(null) ?: context.cacheDir, "quran_audio")
        val file = File(dir, "surah_${surahNumber}_$reciterId.mp3")
        if (file.exists()) {
            file.delete()
        }
        val db = AppDatabase.getDatabase(context)
        db.cachedAudioDao().deleteCachedAudio(surahNumber, reciterId)
        _downloadStates.value = _downloadStates.value - key
    }

    private fun updateProgress(key: String, progress: DownloadProgress) {
        _downloadStates.value = _downloadStates.value.toMutableMap().apply {
            put(key, progress)
        }
    }
}
