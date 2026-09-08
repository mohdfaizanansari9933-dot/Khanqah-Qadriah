package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.CachedAudioEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for cached Quran audio files.
 */
@Dao
interface CachedAudioDao {

    @Query("SELECT * FROM cached_quran_audio ORDER BY surahNumber ASC")
    fun getAllCachedAudio(): Flow<List<CachedAudioEntity>>

    @Query("SELECT * FROM cached_quran_audio WHERE surahNumber = :surahNumber AND reciterId = :reciterId LIMIT 1")
    suspend fun getCachedAudio(surahNumber: Int, reciterId: String): CachedAudioEntity?

    @Query("SELECT * FROM cached_quran_audio WHERE surahNumber = :surahNumber AND reciterId = :reciterId LIMIT 1")
    fun observeCachedAudio(surahNumber: Int, reciterId: String): Flow<CachedAudioEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedAudio(audio: CachedAudioEntity)

    @Query("DELETE FROM cached_quran_audio WHERE surahNumber = :surahNumber AND reciterId = :reciterId")
    suspend fun deleteCachedAudio(surahNumber: Int, reciterId: String)

    @Query("DELETE FROM cached_quran_audio WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM cached_quran_audio")
    suspend fun clearAll()
}
