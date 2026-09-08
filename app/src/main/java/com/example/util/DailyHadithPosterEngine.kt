package com.example.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

data class DailySpiritualMessage(
    val id: String,
    val dateHijri: String,
    val hindiHadithTitle: String,
    val arabicHadith: String,
    val hindiHadithText: String,
    val hadithReference: String,
    val khanqahBookName: String,
    val ekAchhiBaatHindi: String,
    val lessonTakeaway: String
)

object DailyHadithPosterEngine {

    val MESSAGES: List<DailySpiritualMessage> = listOf(
        DailySpiritualMessage(
            id = "msg_1",
            dateHijri = "माह-ए-मुबारक • बदायूं शरीफ़",
            hindiHadithTitle = "मुसलमानों में आपस में भलाई व रहमदिली",
            arabicHadith = "الْمُسْلِمُ أَخُو الْمُسْلِمِ لَا يَظْلِمُهُ وَلَا يُسْلِمُهُ",
            hindiHadithText = "एक मुसलमान दूसरे मुसलमान का भाई है, न वह उस पर ज़ुल्म करता है और न ही उसे बेयारो-मददगार छोड़ता है।",
            hadithReference = "सहीह बुखारी व सहीह मुस्लिम",
            khanqahBookName = "किताब: सैफ़ुल जब्बार अल-मशलूल (हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ)",
            ekAchhiBaatHindi = "हमेशा दूसरों के ऐबों पर पर्दा डालो और कमज़ोरों की मदद को अपनी सआदत समझो। दिल की सफ़ाई ही अल्लाह की रज़ा का सबसे बड़ा वसीला है।",
            lessonTakeaway = "क़ादरी उसूल: ख़िदमत-ए-ख़ल्क़ व हुस्न-ए-अख़लाक़"
        ),
        DailySpiritualMessage(
            id = "msg_2",
            dateHijri = "नूरानी पैग़ाम • खानक़ाह क़ादरिया",
            hindiHadithTitle = "इल्म और सुन्नत पर इस्तिक़ामत",
            arabicHadith = "مَنْ سَلَكَ طَرِيقًا يَلْتَمِسُ فِيهِ عِلْمًا سَهَّلَ اللَّهُ لَهُ طَرِيقًا إِلَى الْجَنَّةِ",
            hindiHadithText = "जो शख्स इल्म-ए-दीन की तलाश में किसी रास्ते पर चलता है, अल्लाह तआला उसके लिए जन्नत का रास्ता आसान फ़रमा देता है।",
            hadithReference = "सहीह मुस्लिम शरीफ़",
            khanqahBookName = "किताब: अल-बवारिक़ुल मुहम्मदिया (हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ)",
            ekAchhiBaatHindi = "दीनी इल्म हासिल करना हर मोमिन की रूह की ग़िज़ा है। जो इंसान इल्म के साथ अमल करता है, उसकी दुआएं बारगाह-ए-इलाही में क़बूल होती हैं।",
            lessonTakeaway = "क़ादरी उसूल: इल्म-ओ-अमल का संगम"
        ),
        DailySpiritualMessage(
            id = "msg_3",
            dateHijri = "मुबारक रोज़ • बदायूं शरीफ़",
            hindiHadithTitle = "अल्लाह और उसके रसूल ﷺ से सच्ची मोहब्बत",
            arabicHadith = "لَا يُؤْمِنُ أَحَدُكُمْ حَتَّى أَكُونَ أَحَبَّ إِلَيْهِ مِنْ وَالِدِهِ وَوَلَدِهِ وَالنَّاسِ أَجْمَعِينَ",
            hindiHadithText = "तुम में से कोई शख्स उस वक़्त तक कामिल मोमिन नहीं हो सकता जब तक मैं (रसूलुल्लाह ﷺ) उसे उसके वालिद, उसकी औलाद और तमाम इंसानों से ज़्यादा महबूब न हो जाऊं।",
            hadithReference = "सहीह बुखारी शरीफ़",
            khanqahBookName = "किताब: अल-मोतक़द अल-मुन्तक़द (हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ)",
            ekAchhiBaatHindi = "हुज़ूर अकरम ﷺ की सुन्नतों को ज़िंदा करना और दुरूद-ओ-सलाम की कसरत करना ही दिल की बीमारियों का सच्चा इलाज है।",
            lessonTakeaway = "क़ादरी उसूल: इश्क़-ए-रसूल ﷺ ही ईमान की जान है"
        ),
        DailySpiritualMessage(
            id = "msg_4",
            dateHijri = "पैग़ाम-ए-हक़ • खानक़ाह क़ादरिया",
            hindiHadithTitle = "अहले सुन्नत व जमात की राह",
            arabicHadith = "عَلَيْكُمْ بِالسَّوَادِ الْأَعْظَمِ فَإِنَّهُ مَنْ شَذَّ شَذَّ فِي النَّارِ",
            hindiHadithText = "सवाद-ए-आज़म (मुसलमानों के बड़े जमाअती गिरोह यानी अहले सुन्नत) को लाज़िम पकड़ो, क्योंकि जो अलग हुआ वह आग में गया।",
            hadithReference = "सुनन इब्ने माजा व मुसनद",
            khanqahBookName = "किताब: अहक़ाक़ुल हक़ अल-मुबीन (हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ)",
            ekAchhiBaatHindi = "हमेशा बुज़ुर्गान-ए-दीन और औलिया-ए-किराम के अक़ीदे पर क़ायम रहो। तफ़रक़ाबाज़ी से बचकर सुन्नत व शरीअत को मज़बूती से थामे रहो।",
            lessonTakeaway = "क़ादरी उसूल: इत्तिहाद व अक़ीदा-ए-हक़"
        ),
        DailySpiritualMessage(
            id = "msg_5",
            dateHijri = "रूहानी ताज़गी • बदायूं शरीफ़",
            hindiHadithTitle = "फ़ितनों से हिफ़ाज़त व नर्म गुफ़्तगू",
            arabicHadith = "إِنَّ الرِّفْقَ لَا يَكُونُ فِي شَيْءٍ إِلَّا زَانَهُ",
            hindiHadithText = "नर्मी जिस चीज़ में भी शामिल होती है उसे ख़ूबसूरत बना देती है, और जिस चीज़ से छीन ली जाती है उसे बदरंग कर देती है।",
            hadithReference = "सहीह मुस्लिम शरीफ़",
            khanqahBookName = "किताब: तब्बत-उन-नजदी (हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ)",
            ekAchhiBaatHindi = "अपनी ज़बान को हमेशा मीठा और साफ़ रखो। किसी की ग़ीबत या दिल आज़ारी मत करो, क्योंकि तोड़े हुए दिल की आह अर्श को हिला देती है।",
            lessonTakeaway = "क़ादरी उसूल: अख़्लाक़-ए-हसना व ज़बान की हिफ़ाज़त"
        ),
        DailySpiritualMessage(
            id = "msg_6",
            dateHijri = "फैज़ान-ए-क़ादरिया • बदायूं शरीफ़",
            hindiHadithTitle = "औलिया-ए-किराम का मुक़ाम व विलायत",
            arabicHadith = "مَنْ عَادَى لِي وَلِيًّا فَقَدْ آذَنْتُهُ بِالْحَرْبِ",
            hindiHadithText = "अल्लाह तआला फ़रमाता है: जिसने मेरे किसी वली से दुश्मनी की, मैं उसके ख़िलाफ़ जंग का एलान करता हूं।",
            hadithReference = "हदीस-ए-क़ुदसी, सहीह बुखारी",
            khanqahBookName = "किताब: फ़स्लुल ख़िताब फ़ी इसबात-ए-अहवालिल अक़ताब (हज़रत शाह फ़ज़्ल-ए-रसूल ؒ)",
            ekAchhiBaatHindi = "अल्लाह के नेक बंदों की बारगाह में अदब व एहतिराम सीखो। जो औलिया के नक़्शे-क़दम पर चलता है, दुनिया और आख़िरत में कामयाब होता है।",
            lessonTakeaway = "क़ादरी उसूल: अदब पहला क़रीना है मोहब्बत के क़रीनों में"
        ),
        DailySpiritualMessage(
            id = "msg_7",
            dateHijri = "नूर-ए-इरादत • बदायूं शरीफ़",
            hindiHadithTitle = "हलाल रिज़्क़ और पाकीज़ा नीयत",
            arabicHadith = "إِنَّ اللَّهَ طَيِّبٌ لَا يَقْبَلُ إِلَّا طَيِّبًا",
            hindiHadithText = "बेशक अल्लाह पाक है और वह केवल पाकीज़ा (हलाल) चीज़ को ही क़बूल फ़रमाता है।",
            hadithReference = "सहीह मुस्लिम शरीफ़",
            khanqahBookName = "किताब: तल्ख़ीसुल हक़ (हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ)",
            ekAchhiBaatHindi = "अपनी कमाई में कभी बेईमानी मत आने दो। हलाल लुक़्मा इंसान की इबादतों में नूर और औलाद में फ़रमांबरदारी पैदा करता है।",
            lessonTakeaway = "क़ादरी उसूल: हलाल रिज़्क़ व नीयत की पाकीज़गी"
        )
    )

    fun getTodayMessage(): DailySpiritualMessage {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val index = (dayOfYear % MESSAGES.size)
        return MESSAGES[index]
    }

    /**
     * Generates a high-definition, beautifully ornamented Islamic Poster Bitmap.
     */
    fun generatePosterBitmap(context: Context, message: DailySpiritualMessage): Bitmap {
        val width = 1080
        val height = 1440
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 1. Background Gradient (Deep Emerald Green to Rich Night Green)
        val bgPaint = Paint().apply {
            shader = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                intArrayOf(0xFF0D3325.toInt(), 0xFF082017.toInt(), 0xFF051710.toInt()),
                floatArrayOf(0f, 0.5f, 1f),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // 2. Gold Borders and Ornate Arches
        val borderPaint = Paint().apply {
            color = 0xFFD4AF37.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
        }
        val innerBorderPaint = Paint().apply {
            color = 0x88D4AF37.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 2f
            isAntiAlias = true
        }
        canvas.drawRoundRect(28f, 28f, width - 28f, height - 28f, 32f, 32f, borderPaint)
        canvas.drawRoundRect(42f, 42f, width - 42f, height - 42f, 24f, 24f, innerBorderPaint)

        // Corner ornaments
        val cornerPaint = Paint().apply {
            color = 0xFFD4AF37.toInt()
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawCircle(56f, 56f, 8f, cornerPaint)
        canvas.drawCircle(width - 56f, 56f, 8f, cornerPaint)
        canvas.drawCircle(56f, height - 56f, 8f, cornerPaint)
        canvas.drawCircle(width - 56f, height - 56f, 8f, cornerPaint)

        // 3. Header: Khanqah Title & Arch
        val titlePaint = Paint().apply {
            color = 0xFFFFDF79.toInt()
            textSize = 42f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            setShadowLayer(8f, 0f, 2f, 0xAA000000.toInt())
        }
        canvas.drawText("खानक़ाह-ए-आलिया क़ादरिया, बदायूं शरीफ़", (width / 2).toFloat(), 125f, titlePaint)

        val subTitlePaint = Paint().apply {
            color = 0xFFE0E0E0.toInt()
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("उ.प्र., भारत • रोज़ाना रूहानी पैग़ाम • www.qadri.in", (width / 2).toFloat(), 168f, subTitlePaint)

        // Bismillah Header
        val bismillahPaint = Paint().apply {
            color = 0xFFD4AF37.toInt()
            textSize = 38f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", (width / 2).toFloat(), 235f, bismillahPaint)

        // Divider Line
        val divPaint = Paint().apply {
            color = 0xFFD4AF37.toInt()
            strokeWidth = 3f
        }
        canvas.drawLine(160f, 265f, width - 160f, 265f, divPaint)

        // 4. Section 1: Hadees-e-Mubarak Box
        val boxPaint = Paint().apply {
            color = 0x22FFFFFF.toInt()
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val boxStroke = Paint().apply {
            color = 0x55D4AF37.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 2f
            isAntiAlias = true
        }

        // Hadith Section Box
        canvas.drawRoundRect(70f, 295f, width - 70f, 740f, 24f, 24f, boxPaint)
        canvas.drawRoundRect(70f, 295f, width - 70f, 740f, 24f, 24f, boxStroke)

        val secHeaderPaint = Paint().apply {
            color = 0xFFFFDF79.toInt()
            textSize = 34f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("✨ आज की मुबारक हदीस: ${message.hindiHadithTitle}", (width / 2).toFloat(), 350f, secHeaderPaint)

        // Arabic text
        val arabicPaint = Paint().apply {
            color = 0xFFFFFFFF.toInt()
            textSize = 34f
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        drawMultilineText(canvas, message.arabicHadith, (width / 2).toFloat(), 410f, arabicPaint, width - 180, 48f)

        // Hindi translation
        val hindiHadithPaint = Paint().apply {
            color = 0xFFEAEAEA.toInt()
            textSize = 28f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        drawMultilineText(canvas, "अनुवाद: \"${message.hindiHadithText}\"", (width / 2).toFloat(), 530f, hindiHadithPaint, width - 180, 42f)

        // Hadith Reference
        val refPaint = Paint().apply {
            color = 0xFFD4AF37.toInt()
            textSize = 22f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("हवाला: ${message.hadithReference}", (width / 2).toFloat(), 705f, refPaint)

        // 5. Section 2: "Rozana Ek Achhi Baat" from Khanqah Books
        canvas.drawRoundRect(70f, 770f, width - 70f, 1260f, 24f, 24f, boxPaint)
        canvas.drawRoundRect(70f, 770f, width - 70f, 1260f, 24f, 24f, boxStroke)

        val achhiBaatHeader = Paint().apply {
            color = 0xFFFFDF79.toInt()
            textSize = 34f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("🌸 रोज़ाना एक अच्छी बात (रूहानी नसीहत)", (width / 2).toFloat(), 830f, achhiBaatHeader)

        // Khanqah Book Source
        val bookSourcePaint = Paint().apply {
            color = 0xFF81C784.toInt()
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText(message.khanqahBookName, (width / 2).toFloat(), 880f, bookSourcePaint)

        // Advice text
        val advicePaint = Paint().apply {
            color = 0xFFFFFFFF.toInt()
            textSize = 30f
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        drawMultilineText(canvas, "“${message.ekAchhiBaatHindi}”", (width / 2).toFloat(), 950f, advicePaint, width - 190, 46f)

        // Lesson takeaway pill
        val takeawayPaint = Paint().apply {
            color = 0xFFFFD700.toInt()
            textSize = 24f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("✦ ${message.lessonTakeaway} ✦", (width / 2).toFloat(), 1220f, takeawayPaint)

        // 6. Footer: Sajjada Nashin Attribution and Helpline
        val footerPaint = Paint().apply {
            color = 0xFFD4AF37.toInt()
            textSize = 22f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("सरपरस्ती: जानशीन हुज़ूर ताजदार-ए-अहले सुन्नत हज़रत अतीफ़ मियां क़ादरी (सज्जादा नशीन)", (width / 2).toFloat(), 1315f, footerPaint)

        val contactFooterPaint = Paint().apply {
            color = 0xFFCCCCCC.toInt()
            textSize = 20f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText("खानक़ाह हेल्पलाइन: +91 94125 61786 • हक़ मल्टीमीडिया (@haqmultimedia)", (width / 2).toFloat(), 1355f, contactFooterPaint)

        return bitmap
    }

    private fun drawMultilineText(
        canvas: Canvas,
        text: String,
        x: Float,
        y: Float,
        paint: Paint,
        maxWidth: Int,
        lineHeight: Float
    ) {
        val words = text.split(" ")
        var currentLine = ""
        var currentY = y

        for (word in words) {
            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            val testWidth = paint.measureText(testLine)
            if (testWidth <= maxWidth) {
                currentLine = testLine
            } else {
                if (currentLine.isNotEmpty()) {
                    canvas.drawText(currentLine, x, currentY, paint)
                    currentY += lineHeight
                }
                currentLine = word
            }
        }
        if (currentLine.isNotEmpty()) {
            canvas.drawText(currentLine, x, currentY, paint)
        }
    }

    /**
     * Saves poster image to application cache directory and returns the FileProvider Uri.
     */
    fun savePosterToCache(context: Context, bitmap: Bitmap): Uri? {
        return try {
            val cacheImagesDir = File(context.cacheDir, "images").apply { mkdirs() }
            val file = File(cacheImagesDir, "khanqah_daily_poster_${System.currentTimeMillis()}.png")
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
            out.close()
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Downloads and saves poster to external Pictures/KhanqahQadriah directory.
     */
    fun downloadPoster(context: Context, message: DailySpiritualMessage) {
        try {
            val bitmap = generatePosterBitmap(context, message)
            val filename = "Khanqah_Daily_Hadith_${System.currentTimeMillis()}.png"
            var outputStream: OutputStream? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/KhanqahQadriah")
                }
                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    outputStream = context.contentResolver.openOutputStream(uri)
                }
            } else {
                val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/KhanqahQadriah"
                val file = File(imagesDir)
                if (!file.exists()) file.mkdirs()
                val image = File(imagesDir, filename)
                outputStream = FileOutputStream(image)
            }

            outputStream?.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                it.flush()
            }

            Toast.makeText(context, "پوسٹر محفوظ کر لیا گیا (Poster Saved to Pictures)!", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "پوسٹر محفوظ کرنے میں خرابی ہوئی", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Shares poster directly on WhatsApp (or app chooser) with formatted caption text.
     */
    fun sharePosterImage(context: Context, message: DailySpiritualMessage) {
        shareOnWhatsApp(context, message)
    }

    fun savePosterToGallery(context: Context, message: DailySpiritualMessage): Boolean {
        downloadPoster(context, message)
        return true
    }

    fun shareOnWhatsApp(context: Context, message: DailySpiritualMessage) {
        try {
            val bitmap = generatePosterBitmap(context, message)
            val uri = savePosterToCache(context, bitmap)

            val caption = buildString {
                append("✨ *खानक़ाह-ए-आलिया क़ादरिया बदायूं शरीफ़*\n")
                append("📖 *आज की मुबारक हदीस*: ${message.hindiHadithTitle}\n")
                append("\"${message.hindiHadithText}\"\n")
                append("(हवाला: ${message.hadithReference})\n\n")
                append("🌸 *रोज़ाना एक अच्छी बात*:\n")
                append("\"${message.ekAchhiBaatHindi}\"\n")
                append("📚 ${message.khanqahBookName}\n\n")
                append("🔗 www.qadri.in | संपर्क: +91 94125 61786\n")
                append("📱 खानक़ाह क़ादरिया आधिकारिक ऐप से प्रसारित")
            }

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_TEXT, caption)
                if (uri != null) {
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
            }

            // Prefer WhatsApp if installed
            intent.setPackage("com.whatsapp")
            try {
                context.startActivity(intent)
            } catch (_: Exception) {
                // Fallback to chooser if WhatsApp not specifically installed
                val chooser = Intent.createChooser(
                    Intent(Intent.ACTION_SEND).apply {
                        type = "image/png"
                        putExtra(Intent.EXTRA_TEXT, caption)
                        if (uri != null) {
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                    },
                    "خانقاہ قادریہ پوسٹر شیئر کریں"
                )
                context.startActivity(chooser)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "شیئر کرنے میں خرابی ہوئی", Toast.LENGTH_SHORT).show()
        }
    }
}
