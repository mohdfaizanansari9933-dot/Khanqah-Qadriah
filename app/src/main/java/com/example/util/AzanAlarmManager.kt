package com.example.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object AzanAlarmManager {
    private const val TAG = "AzanAlarmManager"
    private const val CHANNEL_ID = "prayer_azan_channel_v2"
    private const val CHANNEL_NAME = "اوقاتِ نماز و اذان الرٹ"

    // High quality online Azan audio stream (Abdul Basit / Madinah Azan)
    private const val AZAN_AUDIO_URL = "https://cdn.aladhan.com/audio/adhans/makkah.mp3"
    private const val FAJR_AZAN_AUDIO_URL = "https://cdn.aladhan.com/audio/adhans/fajr.mp3"

    private var mediaPlayer: MediaPlayer? = null

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPlayingTitle = MutableStateFlow<String?>(null)
    val currentPlayingTitle: StateFlow<String?> = _currentPlayingTitle.asStateFlow()

    fun initNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "خانقاہِ عالیہ قادریہ بدایوں شریف: مسنون اذان و نماز کے اعلانات"
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 250, 500)
                setSound(defaultSoundUri, audioAttributes)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }
    }

    /**
     * Play Azan or test alarm sound
     */
    fun playTestAzan(context: Context, isFajr: Boolean = false) {
        stopSound()

        val url = if (isFajr) FAJR_AZAN_AUDIO_URL else AZAN_AUDIO_URL
        _currentPlayingTitle.value = if (isFajr) "اذانِ فجر (الصلاة خير من النوم)" else "اذانِ مصطفیٰ ﷺ"
        _isPlaying.value = true

        CoroutineScope(Dispatchers.IO).launch {
            try {
                mediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(url)
                    setOnPreparedListener { mp ->
                        mp.start()
                        _isPlaying.value = true
                    }
                    setOnCompletionListener {
                        stopSound()
                    }
                    setOnErrorListener { _, _, _ ->
                        playSystemAlertSound(context)
                        true
                    }
                    prepareAsync()
                }
            } catch (e: Exception) {
                Log.w(TAG, "Online Azan failed, playing system tone: ${e.message}")
                playSystemAlertSound(context)
            }
        }
    }

    private fun playSystemAlertSound(context: Context) {
        try {
            val alertUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context.applicationContext, alertUri)?.apply {
                setOnCompletionListener {
                    stopSound()
                }
                start()
            }
            _isPlaying.value = true
        } catch (e: Exception) {
            Log.e(TAG, "Error playing fallback alert tone", e)
            stopSound()
        }
    }

    fun stopSound() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping sound", e)
        } finally {
            mediaPlayer = null
            _isPlaying.value = false
            _currentPlayingTitle.value = null
        }
    }

    /**
     * Post visual prayer notification with sound & vibration
     */
    fun showPrayerAlertNotification(
        context: Context,
        prayerUrduName: String,
        prayerEnglishName: String,
        time: String,
        isSound: Boolean = true
    ) {
        initNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            prayerUrduName.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("حی علی الصلاة • وقتِ نماز: $prayerUrduName")
            .setContentText("خانقاہِ قادریہ بدایوں شریف: $prayerUrduName کا وقت ہو گیا ہے ($time)")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "حی علی الصلاة • حی علی الفلاح!\n" +
                            "حضرت صوفی محمد عاطف میاں قادری کی خانقاہِ عالیہ بدایوں شریف کے مطابق $prayerUrduName کا وقت ($time) شروع ہو گیا ہے۔ باجماعت نماز کا اہتمام فرمائیں۔"
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (isSound) {
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            builder.setSound(soundUri)
            builder.setVibrate(longArrayOf(0, 500, 200, 500))
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.notify(prayerUrduName.hashCode(), builder.build())
    }
}
