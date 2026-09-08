package com.example.util

import com.example.data.repository.KhanqahRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val sourceBook: String? = null
)

data class AiResponse(
    val answer: String,
    val referenceBook: String? = null
)

object KhanqahAiAssistantEngine {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .build()

    val PRESET_QUESTIONS = listOf(
        "خانقاہ قادریہ بدایوں شریف کی تاریخ کیا ہے؟",
        "حضرت شاہ فضل رسول قدس سرہ کی کتب بتائیں",
        "کتاب 'سیف الجبار' کے اہم مضامین کیا ہیں؟",
        "خانقاہ قادریہ کے موجودہ سجادہ نشین کون ہیں؟",
        "سلسلہ عالیہ قادریہ مجیدیہ کے اوراد و آداب",
        "عرسِ قادری دولہا کی تاریخ و تفصیلات"
    )

    suspend fun getAnswer(question: String): AiResponse = withContext(Dispatchers.IO) {
        val trimmed = question.trim()

        // 1. Check local authentic knowledge base first for instantaneous, high-precision answers
        val localResponse = matchLocalKnowledge(trimmed)
        if (localResponse != null) {
            delay(400) // Brief natural pacing
            return@withContext localResponse
        }

        // 2. Try online Gemini API if configured
        val geminiResponse = queryGeminiIfAvailable(trimmed)
        if (geminiResponse != null) {
            return@withContext geminiResponse
        }

        // 3. Fallback to generalized authentic Khanqah response
        return@withContext AiResponse(
            answer = """وعلیکم السلام ورحمۃ اللہ وبرکاتہ۔
خانقاہِ عالیہ قادریہ بدایوں شریف کے مسلک و اصول:
۱. مسلکِ اہل سنت و جماعت (حنفی، قادری) پر استقامت۔
۲. محبت و عظمتِ مصطفیٰ ﷺ، اہلِ بیتِ اطہار اور صحابہ کرام کا احترام۔
۳. تزکیۂ نفس، پابندیِ شریعت، اور رزقِ حلال کی پاسداری۔

مزید علمی و روحانی رہنمائی کے لیے خانقاہ کے باضابطہ دار الافتاء یا دفتر سے رابطہ فرمائیں:
فون و واٹس ایپ: +91 94125 61786
ویب سائٹ: www.qadri.in""",
            referenceBook = "خانقاہِ عالیہ قادریہ بدایوں شریف (مکتبہ قادریہ)"
        )
    }

    private fun matchLocalKnowledge(query: String): AiResponse? {
        val q = query.lowercase()

        // History of Khanqah Qadriah Budaun
        if (q.contains("تاریخ") || q.contains("history") || q.contains("قیام") || q.contains("کب بنی") || q.contains("بدایوں شریف")) {
            return AiResponse(
                answer = """خانقاہِ عالیہ قادریہ بدایوں شریف (اتر پردیش، بھارت) برصغیر پاک و ہند کے عظیم روحانی و علمی مراکز میں سرِ فہرست ہے۔
اس مبارک خانقاہ کی بنیاد ساداتِ قادریہ کے جلیل القدر بزرگ حضرت شاہ فضلِ رسول قادری بدایونی قدس سرہ (المتوفی ۱۲۸۹ھ) کے دور میں مستحکم ہوئی اور یہاں سے علومِ اسلامیہ، فقہ، حدیث اور تصوفِ قادریہ کے چشمے پھوٹے۔
یہ مرکزِ رشد و ہدایت ہمیشہ عشقِ مصطفیٰ ﷺ کے فروغ، ردِ بدعات، اور شریعت و طریقت کی پاسداری کا قلعہ رہا ہے۔
آل انڈیا سنی کانفرنس (۱۹۴۶ء) جیسی تاریخی تحریکوں میں بھی بدایوں شریف کے اکابرین نے قائدانہ کردار ادا فرمایا۔""",
                referenceBook = "تذکرۂ خانوادۂ قادریہ بدایوں شریف (مکتبہ قادریہ)"
            )
        }

        // Hazrat Shah Fazle Rasool Badayuni
        if (q.contains("فضل رسول") || q.contains("سیف اللہ") || q.contains("fazl") || q.contains("fazle rasool")) {
            return AiResponse(
                answer = """حضرت شاہ فضلِ رسول قادری بدایونی ؒ (۱۲۱۳ھ - ۱۲۸۹ھ):
آپ 'سیف اللہ المسلول' کے لقب سے جانے جاتے ہیں۔ آپ جامع المعقول والمنقول، مجددِ مسلکِ اہل سنت اور عظیم صوفی بزرگ تھے۔
آپ نے بدعات و ضلالت کے خلاف تاریخی علمی جہاد فرمایا اور حق کا پرچم بلند رکھا۔
آپ کی معروف کتب:
۱. سیف الجبار المسلول على أعداء الأبرار
۲. احقاق الحق المبين
۳. البوارق المحمدية
۴. المعتقد المنتقد (جس پر امام احمد رضا خان نے 'معتمد المستند' حاشیہ لکھا)
۵. تبت النجدی
۶. فصل الخطاب
۷. تلخیص الحق""",
                referenceBook = "سیف الجبار المسلول و احقاق الحق (حضرت شاہ فضلِ رسول بدایونی ؒ)"
            )
        }

        // Saiful Jabbar Book
        if (q.contains("سیف الجبار") || q.contains("saiful jabbar")) {
            return AiResponse(
                answer = """کتاب 'سیف الجبار المسلول علی اعداء الابرار':
یہ حضرت شاہ فضلِ رسول قادری بدایونی قدس سرہ کی معرکۃ الآراء اور تاریخی تصنیف ہے۔
اس کتاب میں:
۱. عقائدِ اہل سنت و جماعت کا دلائلِ قاہرہ سے ثبوت۔
۲. حضور اقدس ﷺ کی نبوت و اختیارات اور علمِ غیب کا تحفظ۔
۳. اولیاء اللہ کے توسل اور استمداد کا شرعی اثبات۔
۴. معترضین اور گمراہ فرقوں کے شکوک و شبہات کا دندان شکن جواب پیش کیا گیا ہے۔""",
                referenceBook = "سیف الجبار المسلول (مکتبہ قادریہ بدایوں شریف)"
            )
        }

        // Current Sajjada Nashin & Waliahd
        if (q.contains("عاطف میاں") || q.contains("سجادہ نشین") || q.contains("ateef") || q.contains("sajjada") || q.contains("عزام میاں") || q.contains("azzam")) {
            return AiResponse(
                answer = """خانقاہِ عالیہ قادریہ بدایوں شریف کی موجودہ مسندِ سجادگی:
۱. صاحبِ سجادہ: جانشین حضور تاجدارِ اہل سنت، حضرت علامہ مولانا شیخ عبد الغنی محمد عاطف قادری ازہری عشقی بدایونی صاحب مدظلہ العالی۔
آپ جامعہ ازہر (مصر) کے فاضل ہیں اور دنیا بھر میں سلسلہ قادریہ کی ترویج اور مسلکِ حق کی آبیاری فرما رہے ہیں۔

۲. ولی عہدِ خانقاہ: حضرت مولانا عزام قادری بدایونی مدظلہ۔
آپ خانقاہ کے دعوتی، تنظیمی اور سماجی شعبوں کی فعال سرپرستی فرماتے ہیں۔""",
                referenceBook = "دفترِ خانقاہِ عالیہ قادریہ بدایوں شریف / qadri.in"
            )
        }

        // Qadri Dulha
        if (q.contains("قادری دولہا") || q.contains("عبد المجید") || q.contains("qadri dulha")) {
            return AiResponse(
                answer = """حضرت شاہ عبد المجید قادری بدایونی ؒ (المعروف قادری دولہا):
آپ سلسلہ عالیہ قادریہ مجیدیہ کے بانی اور برصغیر کے عظیم قطب الاقطاب ہیں۔
آپ کی روحانی کشش اور اخلاقِ کریمانہ کی وجہ سے آپ کو 'قادری دولہا' کے پرنور لقب سے یاد کیا جاتا ہے۔
آپ کا مزارِ پرانوار خانقاہِ عالیہ قادریہ بدایوں شریف میں مرجعِ خلائق ہے جہاں سالانہ عرسِ مبارک رجب المرجب میں بڑی شان و شوکت سے منایا جاتا ہے۔""",
                referenceBook = "ارشاد المجید فی سلوک المرید (مکتبہ قادریہ)"
            )
        }

        // Shaheed-e-Baghdad
        if (q.contains("شہید بغداد") || q.contains("اسید الحق") || q.contains("asid")) {
            return AiResponse(
                answer = """حضرت مولانا اسید الحق قادری بدایونی ؒ (شہیدِ بغداد):
آپ ایک نابغۂ روزگار محقق، مصنف، خطیب اور تاج الفحول اکیڈمی کے بانی تھے۔
آپ نے عربی اور اردو میں درجنوں تحقیقی کتب تصنیف فرمائیں۔
سنہ ۲۰۱۴ء میں بغداد شریف کی مقدس سرزمین پر دورانِ زیارت جامِ شہادت نوش فرمایا۔ آپ کا علمی ورثہ آج بھی نئی نسل کے لیے مشعلِ راہ ہے۔""",
                referenceBook = "تاج الفحول اکیڈمی، بدایوں شریف"
            )
        }

        // Urs dates
        if (q.contains("عرس") || q.contains("تاریخ عرس") || q.contains("urs")) {
            return AiResponse(
                answer = """خانقاہِ عالیہ قادریہ بدایوں شریف کے اہم اعراسِ مبارکہ:
۱. عرسِ قادری دولہا (حضرت شاہ عبد المجید قادری ؒ): ۱۸ اور ۱۹ رجب المرجب۔
۲. عرسِ حضرت شاہ فضلِ رسول قادری بدایونی ؒ: ۲۹ جمادی الثانی۔
۳. عرسِ حضرت شاہ عبد القادر بدایونی ؒ: ۱۹ ربیع الثانی۔
۴. عرسِ حضرت شہیدِ بغداد مولانا اسید الحق قادری ؒ: ۲۳ جمادی الاولیٰ۔
ان تمام ایام میں محافلِ نعت، ختمِ قرآن، قل شریف اور خصوصی روحانی مجالس منعقد ہوتی ہیں۔""",
                referenceBook = "تقویمِ اعراسِ خانقاہِ قادریہ بدایوں شریف"
            )
        }

        // Namaz / Wudu / Fiqh
        if (q.contains("نماز") || q.contains("وضو") || q.contains("طہارت") || q.contains("namaz") || q.contains("salah")) {
            return AiResponse(
                answer = """فقہ حنفی کے مطابق طہارت و نماز کے اساسی احکام:
• وضو کے ۴ فرائض: ۱. پورا چہرہ دھونا ۲. کہنیوں سمیت دونوں ہاتھ دھونا ۳. چوتھائی سر کا مسح ۴. ٹخنوں سمیت دونوں پاؤں دھونا۔
• نماز کے ۶ فرائضِ خارجہ (شرائط): طہارت، سترِ عورت، استقبالِ قبلہ، وقت، نیت، اور تکبیرِ تحریمہ۔
• نماز کے ۶ فرائضِ داخلہ (ارکان): قیام، قراءت، رکوع، سجود، قعدہ اخیرہ، اور خروج بصنعہ۔
مزید تفصیل کے لیے ایپ میں موجود 'بہارِ شریعت' کے فقہی ابواب کا مطالعہ فرمائیں۔""",
                referenceBook = "بہارِ شریعت (صدر الشریعہ مفتی امجد علی اعظمی ؒ)"
            )
        }

        return null
    }

    private suspend fun queryGeminiIfAvailable(query: String): AiResponse? {
        return try {
            val apiKey = try {
                val clazz = Class.forName("com.example.BuildConfig")
                val field = clazz.getField("GEMINI_API_KEY")
                field.get(null) as? String
            } catch (_: Exception) {
                null
            }

            if (apiKey.isNullOrBlank()) return null

            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"
            val systemPrompt = """You are the official spiritual AI knowledge assistant of Khanqah-e-Aalia Qadria Badaun Shareef (خانقاہ عالیہ قادریہ بدایوں شریف, U.P. India). 
Always uphold Ahle Sunnat wa Jamaat beliefs, Hanafi Fiqh, and reverence for Prophet Muhammad ﷺ, Sahaba, Ahle Bait, and Sufi saints, especially Hazrat Shah Fazle Rasool Badayuni and the Sajjada Nashin Hazrat Ateef Miya. 
Answer with high respect, accuracy, and cite authentic sources when possible in Urdu/Hindi/English."""

            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", "$systemPrompt\n\nUser Question: $query")
                            })
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val resBody = response.body?.string() ?: return null
                val root = JSONObject(resBody)
                val candidates = root.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val candidate = candidates.getJSONObject(0)
                    val content = candidate.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        val text = parts.getJSONObject(0).optString("text", "")
                        if (text.isNotBlank()) {
                            return AiResponse(
                                answer = text,
                                referenceBook = "خانقاہِ عالیہ قادریہ بدایوں شریف (مکتبہ قادریہ)"
                            )
                        }
                    }
                }
            }
            null
        } catch (_: Exception) {
            null
        }
    }
}
