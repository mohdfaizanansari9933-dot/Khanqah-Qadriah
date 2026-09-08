package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing locally cached Quran recitation audio.
 * Enables offline playback and caching management.
 */
@Entity(tableName = "cached_quran_audio")
data class CachedAudioEntity(
    @PrimaryKey val id: String, // e.g. "surah_1_alafasy"
    val surahNumber: Int,
    val surahNameUrdu: String,
    val surahNameEnglish: String,
    val reciterId: String,
    val reciterName: String,
    val filePath: String,
    val fileSizeBytes: Long,
    val durationMs: Int = 0,
    val downloadedAt: Long = System.currentTimeMillis()
)
