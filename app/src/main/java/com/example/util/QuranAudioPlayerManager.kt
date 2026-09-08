package com.example.util

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.widget.Toast
import com.example.data.local.AppDatabase
import com.example.data.local.entity.CachedAudioEntity
import com.example.data.repository.QuranAudioDownloadRepository
import com.example.data.repository.QuranRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

enum class QuranReciter(
    val id: String,
    val nameUrdu: String,
    val nameEnglish: String,
    val audioUrlPattern: String
) {
    MISHARY_ALAFASY(
        id = "alafasy",
        nameUrdu = "شیخ مشاری راشد العفاسی",
        nameEnglish = "Mishary Rashid Alafasy",
        audioUrlPattern = "https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/%d.mp3"
    ),
    ABDUL_BASIT(
        id = "abdul_basit",
        nameUrdu = "شیخ عبد الباسط عبد الصمد",
        nameEnglish = "Abdul Basit Abdul Samad",
        audioUrlPattern = "https://cdn.islamic.network/quran/audio-surah/128/ar.abdulbasitmurattal/%d.mp3"
    )
}

data class QuranPlayerState(
    val currentSurahNumber: Int? = null,
    val reciter: QuranReciter = QuranReciter.MISHARY_ALAFASY,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val currentPositionMs: Int = 0,
    val durationMs: Int = 0,
    val playbackSpeed: Float = 1.0f,
    val isCachedOffline: Boolean = false,
    val isDownloading: Boolean = false,
    val downloadProgress: Float = 0f
)

object QuranAudioPlayerManager {

    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var progressJob: Job? = null

    private val _playerState = MutableStateFlow(QuranPlayerState())
    val playerState: StateFlow<QuranPlayerState> = _playerState.asStateFlow()

    fun getAudioFile(context: Context, surahNumber: Int, reciter: QuranReciter): File {
        val dir = File(context.getExternalFilesDir(null) ?: context.cacheDir, "quran_audio")
        if (!dir.exists()) dir.mkdirs()
        return File(dir, "surah_${surahNumber}_${reciter.id}.mp3")
    }

    fun isSurahCached(context: Context, surahNumber: Int, reciter: QuranReciter): Boolean {
        val file = getAudioFile(context, surahNumber, reciter)
        return file.exists() && file.length() > 5000
    }

    fun getAudioUrl(surahNumber: Int, reciter: QuranReciter): String {
        return String.format(reciter.audioUrlPattern, surahNumber)
    }

    fun playSurah(context: Context, surahNumber: Int, reciter: QuranReciter = _playerState.value.reciter) {
        // If already playing this surah, just resume
        if (_playerState.value.currentSurahNumber == surahNumber && _playerState.value.reciter == reciter && mediaPlayer != null) {
            mediaPlayer?.start()
            _playerState.value = _playerState.value.copy(isPlaying = true)
            startProgressTracker()
            return
        }

        stop()

        val isCached = isSurahCached(context, surahNumber, reciter)
        _playerState.value = _playerState.value.copy(
            currentSurahNumber = surahNumber,
            reciter = reciter,
            isBuffering = true,
            isPlaying = false,
            currentPositionMs = 0,
            durationMs = 0,
            isCachedOffline = isCached
        )

        try {
            val player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                if (isCached) {
                    val file = getAudioFile(context, surahNumber, reciter)
                    setDataSource(context, Uri.fromFile(file))
                } else {
                    val url = getAudioUrl(surahNumber, reciter)
                    setDataSource(url)
                }

                setOnPreparedListener { mp ->
                    _playerState.value = _playerState.value.copy(
                        isBuffering = false,
                        isPlaying = true,
                        durationMs = mp.duration
                    )
                    applyPlaybackSpeed(_playerState.value.playbackSpeed)
                    mp.start()
                    startProgressTracker()
                }

                setOnCompletionListener {
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        currentPositionMs = 0
                    )
                    stopProgressTracker()
                }

                setOnErrorListener { _, what, extra ->
                    _playerState.value = _playerState.value.copy(
                        isBuffering = false,
                        isPlaying = false
                    )
                    Toast.makeText(context, "آڈیو چلانے میں خرابی ہوئی، برائے کرم انٹرنیٹ چیک فرمائیں", Toast.LENGTH_SHORT).show()
                    true
                }

                prepareAsync()
            }
            mediaPlayer = player
        } catch (e: Exception) {
            e.printStackTrace()
            _playerState.value = _playerState.value.copy(isBuffering = false, isPlaying = false)
        }
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _playerState.value = _playerState.value.copy(isPlaying = false)
                stopProgressTracker()
            }
        }
    }

    fun resume() {
        mediaPlayer?.let {
            it.start()
            _playerState.value = _playerState.value.copy(isPlaying = true)
            startProgressTracker()
        }
    }

    fun togglePlayPause(context: Context, surahNumber: Int) {
        if (_playerState.value.currentSurahNumber == surahNumber && mediaPlayer != null) {
            if (_playerState.value.isPlaying) {
                pause()
            } else {
                resume()
            }
        } else {
            playSurah(context, surahNumber)
        }
    }

    fun seekTo(positionMs: Int) {
        mediaPlayer?.seekTo(positionMs)
        _playerState.value = _playerState.value.copy(currentPositionMs = positionMs)
    }

    fun setSpeed(speed: Float) {
        _playerState.value = _playerState.value.copy(playbackSpeed = speed)
        applyPlaybackSpeed(speed)
    }

    private fun applyPlaybackSpeed(speed: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer?.let { mp ->
                try {
                    mp.playbackParams = mp.playbackParams.setSpeed(speed)
                } catch (_: Exception) {}
            }
        }
    }

    fun stop() {
        stopProgressTracker()
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (_: Exception) {}
        mediaPlayer = null
        _playerState.value = _playerState.value.copy(
            isPlaying = false,
            isBuffering = false,
            currentPositionMs = 0,
            durationMs = 0
        )
    }

    private fun startProgressTracker() {
        stopProgressTracker()
        progressJob = scope.launch {
            while (isActive) {
                mediaPlayer?.let { mp ->
                    if (mp.isPlaying) {
                        _playerState.value = _playerState.value.copy(
                            currentPositionMs = mp.currentPosition,
                            durationMs = mp.duration
                        )
                    }
                }
                delay(500)
            }
        }
    }

    private fun stopProgressTracker() {
        progressJob?.cancel()
        progressJob = null
    }

    /**
     * Downloads Surah audio for offline playback.
     */
    fun downloadSurahForOffline(context: Context, surahNumber: Int, reciter: QuranReciter = _playerState.value.reciter) {
        if (isSurahCached(context, surahNumber, reciter)) {
            Toast.makeText(context, "یہ سورۃ پہلے سے آف لائن محفوظ ہے", Toast.LENGTH_SHORT).show()
            return
        }

        _playerState.value = _playerState.value.copy(isDownloading = true, downloadProgress = 0f)

        scope.launch(Dispatchers.IO) {
            try {
                val url = URL(getAudioUrl(surahNumber, reciter))
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    withContext(Dispatchers.Main) {
                        _playerState.value = _playerState.value.copy(isDownloading = false)
                        Toast.makeText(context, "ڈاؤنلوڈ سرور دستیاب نہیں", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val fileLength = connection.contentLength
                val targetFile = getAudioFile(context, surahNumber, reciter)
                val input = connection.inputStream
                val output = FileOutputStream(targetFile)

                val data = ByteArray(4096)
                var total: Long = 0
                var count: Int
                while (input.read(data).also { count = it } != -1) {
                    total += count
                    if (fileLength > 0) {
                        val progress = total.toFloat() / fileLength
                        withContext(Dispatchers.Main) {
                            _playerState.value = _playerState.value.copy(downloadProgress = progress)
                        }
                    }
                    output.write(data, 0, count)
                }

                output.flush()
                output.close()
                input.close()

                // Register cache in Room Database
                try {
                    val surahObj = QuranRepository.getSurah(surahNumber)
                    val surahNameUrdu = surahObj?.urduName ?: "سورۃ نمبر $surahNumber"
                    val surahNameEnglish = surahObj?.englishName ?: "Surah $surahNumber"
                    val db = AppDatabase.getDatabase(context)
                    val entity = CachedAudioEntity(
                        id = "surah_${surahNumber}_${reciter.id}",
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
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                withContext(Dispatchers.Main) {
                    _playerState.value = _playerState.value.copy(
                        isDownloading = false,
                        isCachedOffline = true
                    )
                    Toast.makeText(context, "سورۃ مبارکہ کامیابی سے آف لائن محفوظ ہو گئی", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    _playerState.value = _playerState.value.copy(isDownloading = false)
                    Toast.makeText(context, "آف لائن ڈاؤنلوڈ میں خرابی ہوئی", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Deletes cached Surah audio from Room database and local storage.
     */
    fun deleteOfflineSurah(context: Context, surahNumber: Int, reciter: QuranReciter = _playerState.value.reciter) {
        scope.launch(Dispatchers.IO) {
            QuranAudioDownloadRepository.deleteCachedSurah(context, surahNumber, reciter.id)
            withContext(Dispatchers.Main) {
                if (_playerState.value.currentSurahNumber == surahNumber && _playerState.value.reciter == reciter) {
                    _playerState.value = _playerState.value.copy(isCachedOffline = false)
                }
                Toast.makeText(context, "آف لائن کیش سے حذف کر دیا گیا", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
