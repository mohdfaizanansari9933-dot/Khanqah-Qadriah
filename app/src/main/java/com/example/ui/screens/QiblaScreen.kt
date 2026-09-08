package com.example.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LocationInfo
import com.example.data.prayer.PrayerCalculationEngine
import com.example.ui.components.KhanqahOfficialLogo
import com.example.ui.theme.*
import kotlin.math.*

@Composable
fun QiblaScreen(
    location: LocationInfo,
    isUrdu: Boolean = true
) {
    val context = LocalContext.current
    var deviceAzimuth by remember { mutableFloatStateOf(0f) }
    var hasSensor by remember { mutableStateOf(true) }

    // Calculate Qibla angle and distance from current location
    val qiblaAngle = remember(location.latitude, location.longitude) {
        PrayerCalculationEngine.calculateQiblaDirection(location.latitude, location.longitude)
    }

    val qiblaDistance = remember(location.latitude, location.longitude) {
        PrayerCalculationEngine.calculateDistanceToKaabaKm(location.latitude, location.longitude)
    }

    // Register Sensor Listener
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val rotationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            ?: sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        if (rotationSensor == null) {
            hasSensor = false
        }

        val listener = object : SensorEventListener {
            val rotationMatrix = FloatArray(9)
            val orientationAngles = FloatArray(3)

            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    SensorManager.getOrientation(rotationMatrix, orientationAngles)
                    val azimuthInRadians = orientationAngles[0]
                    val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()
                    deviceAzimuth = (azimuthInDegrees + 360f) % 360f
                } else if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
                    deviceAzimuth = event.values[0]
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        rotationSensor?.let {
            sensorManager?.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            sensorManager?.unregisterListener(listener)
        }
    }

    // Relative needle rotation: Qibla angle minus device azimuth
    val animatedDialRotation by animateFloatAsState(
        targetValue = -deviceAzimuth,
        animationSpec = tween(durationMillis = 250),
        label = "compassDial"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamBg)
            .padding(16.dp)
            .testTag("qibla_screen_container"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                KhanqahOfficialLogo(
                    size = 46.dp,
                    circular = true,
                    elevation = 2.dp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "خانقاہِ قادریہ بدایوں شریف",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isUrdu) "قبلہ رخ (سمتِ کعبہ مشرفہ)" else "Qibla Direction (Kaaba)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${location.cityName} • ${location.countryName}",
                    fontSize = 13.sp,
                    color = TextCharcoal
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isUrdu) "زاویۂ قبلہ" else "Qibla Bearing",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                        Text(
                            text = "${qiblaAngle.roundToInt()}°",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldDark
                        )
                    }

                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .width(1.dp)
                            .background(Color(0xFFE0DACB))
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isUrdu) "فاصلہ تا کعبہ" else "Distance to Kaaba",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                        Text(
                            text = "${qiblaDistance.roundToInt()} کلومیٹر",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldPrimary
                        )
                    }
                }
            }
        }

        // Compass Canvas
        Box(
            modifier = Modifier
                .size(290.dp)
                .testTag("qibla_compass_canvas"),
            contentAlignment = Alignment.Center
        ) {
            // Static Outer Gold Ring
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2 - 8.dp.toPx()

                // Outer decorative circle
                drawCircle(
                    color = GoldPrimary.copy(alpha = 0.3f),
                    radius = radius + 6.dp.toPx(),
                    center = center,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
                )

                drawCircle(
                    color = CreamSurface,
                    radius = radius,
                    center = center
                )
            }

            // Rotating Dial with Compass Markers and Qibla Pointer
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2 - 16.dp.toPx()

                rotate(degrees = animatedDialRotation, pivot = center) {
                    // Compass Tick marks
                    for (deg in 0 until 360 step 30) {
                        val rad = Math.toRadians(deg.toDouble())
                        val isCard = deg % 90 == 0
                        val tickLen = if (isCard) 16.dp.toPx() else 8.dp.toPx()

                        val startX = (center.x + (radius - tickLen) * sin(rad)).toFloat()
                        val startY = (center.y - (radius - tickLen) * cos(rad)).toFloat()
                        val endX = (center.x + radius * sin(rad)).toFloat()
                        val endY = (center.y - radius * cos(rad)).toFloat()

                        drawLine(
                            color = if (deg == 0) Color.Red else EmeraldPrimary.copy(alpha = 0.6f),
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = if (isCard) 3.dp.toPx() else 1.5.dp.toPx()
                        )
                    }

                    // North Needle (Red triangle pointer)
                    val northPath = Path().apply {
                        moveTo(center.x, center.y - radius + 18.dp.toPx())
                        lineTo(center.x - 10.dp.toPx(), center.y)
                        lineTo(center.x + 10.dp.toPx(), center.y)
                        close()
                    }
                    drawPath(northPath, color = Color(0xFFD32F2F))

                    // South Needle (Charcoal triangle pointer)
                    val southPath = Path().apply {
                        moveTo(center.x, center.y + radius - 18.dp.toPx())
                        lineTo(center.x - 10.dp.toPx(), center.y)
                        lineTo(center.x + 10.dp.toPx(), center.y)
                        close()
                    }
                    drawPath(southPath, color = Color(0xFF555555))

                    // Qibla Pointer (Green & Gold Kaaba marker needle pointing at qiblaAngle)
                    rotate(degrees = qiblaAngle.toFloat(), pivot = center) {
                        val kaabaPointerPath = Path().apply {
                            moveTo(center.x, center.y - radius + 4.dp.toPx())
                            lineTo(center.x - 14.dp.toPx(), center.y - radius + 38.dp.toPx())
                            lineTo(center.x + 14.dp.toPx(), center.y - radius + 38.dp.toPx())
                            close()
                        }
                        drawPath(kaabaPointerPath, color = GoldPrimary)

                        // Kaaba icon representation (black cube at tip)
                        drawRect(
                            color = Color.Black,
                            topLeft = Offset(center.x - 10.dp.toPx(), center.y - radius + 12.dp.toPx()),
                            size = androidx.compose.ui.geometry.Size(20.dp.toPx(), 20.dp.toPx())
                        )
                        // Gold band on Kaaba
                        drawRect(
                            color = GoldLight,
                            topLeft = Offset(center.x - 10.dp.toPx(), center.y - radius + 15.dp.toPx()),
                            size = androidx.compose.ui.geometry.Size(20.dp.toPx(), 4.dp.toPx())
                        )
                    }
                }

                // Center Pin
                drawCircle(color = GoldDark, radius = 9.dp.toPx(), center = center)
                drawCircle(color = Color.White, radius = 4.dp.toPx(), center = center)
            }

            // Central Khanqah Emblem Pin
            KhanqahOfficialLogo(
                size = 36.dp,
                circular = true,
                elevation = 3.dp
            )
        }

        // Bottom Guidance & Status Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CreamSurface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Guidance",
                    tint = EmeraldPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = if (isUrdu) "رہنمائی برائے درستگیِ قبلہ" else "Compass Calibration",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextCharcoal
                    )
                    Text(
                        text = if (isUrdu) "موبائل کو ہموار سطح پر رکھیں یا ہوا میں 8 کے ہندسے کی طرح گھمائیں تاکہ مقناطیسی سنسر درست سمت دکھائے۔" else "Hold phone flat and wave in a figure-8 motion to calibrate the compass sensor.",
                        fontSize = 11.sp,
                        color = TextMuted,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
