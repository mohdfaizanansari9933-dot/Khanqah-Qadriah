package com.example.data.repository

import com.example.data.model.*

object KhanqahRepository {

    const val OFFICIAL_WEBSITE_URL = "https://www.qadri.in/"
    const val OFFICIAL_YOUTUBE_URL = "https://www.youtube.com/@haqmultimedia"
    const val OFFICIAL_YOUTUBE_HANDLE = "@haqmultimedia"
    const val OFFICIAL_BOOKS_URL = "https://www.qadri.in/books"
    const val OFFICIAL_CONTACT_EMAIL = "info@qadri.in"
    const val OFFICIAL_CONTACT_PHONE = "+91 94125 61786"
    const val OFFICIAL_WHATSAPP_PHONE = "+919412561786"
    const val OFFICIAL_WHATSAPP_URL = "https://wa.me/919412561786?text=Assalamu%20Alaikum%20Khanqah%20Qadriah%20Badaun%20Shareef"
    const val OFFICIAL_ADDRESS_URDU = "خانقاہِ عالیہ قادریہ، بدایوں شریف، اتر پردیش، بھارت"
    const val OFFICIAL_ADDRESS_ENGLISH = "Khanqah-e-Aaliya Qadriah, Badaun Shareef, Uttar Pradesh, India"
    const val GOOGLE_MAPS_GEO = "geo:28.0315,79.1176?q=Khanqah+Qadriah+Badaun+Shareef"

    // Recognized Scholars & Mashaikh of Khanqah Qadriah Badaun Shareef
    val SCHOLARS_LIST: List<ScholarProfile> = listOf(
        ScholarProfile(
            id = "shah_fazle_rasool",
            nameUrdu = "حضرت شاہ فضلِ رسول قادری بدایونی ؒ (سیف اللہ المسلول)",
            nameHindi = "हज़रत शाह फ़ज़्ल-ए-रसूल क़ादरी बदायूंनी ؒ (सैफ़ुल्लाहिल मशलूल)",
            nameEnglish = "Hazrat Shah Fazle Rasool Badayuni ؒ (Saifullahil Mashlool)",
            nameHinglish = "Hazrat Shah Fazle Rasool Badayuni ؒ",
            titleUrdu = "سیف اللہ المسلول، جامع المعقول والمنقول، مصلحِ ملت",
            titleHindi = "सैफ़ुल्लाहिल मशलूल, जामेउल माक़ूल वल मनक़ूल",
            titleEnglish = "Saifullahil Mashlool, Master of Sacred Sciences, Mujahid of Ahle Sunnat",
            biographyUrdu = "آپ برصغیر پاک و ہند کے جلیل القدر صوفی، مصلحِ ملت، مجاہدِ آزادی اور خانوادۂ قادریہ بدایوں شریف کے سرخیل ہیں۔ آپ نے علومِ عقلیہ و نقلیہ میں بے مثال امامت قائم کی اور مسلکِ حق اہل سنت و جماعت کے تحفظ کے لیے 'سیف اللہ المسلول' کہلائے۔ آپ کی معرکۃ الآراء کتب جیسے سیف الجبار، البوارق المحمدیہ، المعتقد المنتقد، احقاق الحق، تبت النجدی، فصل الخطاب اور تلخیص الحق نے عالمِ اسلام میں فتنوں کا قلمی قلع قمع فرمایا۔",
            biographyHindi = "आप उपमहाद्वीप के महान सूफ़ी, इस्लामी विद्वान और खानवादा-ए-क़ादरिया बदायूं शरीफ़ के आदि-स्तंभ हैं। आपने अक़ीदा-ए-अहले सुन्नत के बचाव में ऐतिहासिक किताबें जैसे सैफ़ुल जब्बार, अल-बवारिक़ुल मुहम्मदिया, अल-मोतक़द अल-मुन्तक़द और अहक़ाक़ुल हक़ लिखीं।",
            biographyEnglish = "Preeminent Islamic luminary, spiritual guide, and founder-pillar of Khanqah Qadriah Badaun Shareef. Famously titled 'Saifullahil Mashlool' for his unyielding scholarly defense of pristine Ahle Sunnat beliefs.",
            biographyHinglish = "Aap subcontinent ke azeem alim-e-deen aur Khanqah Qadriah Badaun Shareef ke azeem buzurgan-e-deen hain.",
            eraOrDates = "1213ھ تا 1289ھ (1798ء تا 1872ء)",
            roleUrdu = "بانی و سرخیل خانوادۂ قادریہ بدایوں شریف",
            roleEnglish = "Foundational Pillar of Badaun Qadriah Lineage",
            spiritualLineage = "سلسلہ عالیہ قادریہ مجددیہ و برکاتیہ",
            scholarlyServicesUrdu = "دینِ متین کی مدافعت، سیف الجبار و المعتقد المنتقد کی تصنیف، شریعت و طریقت کی ہم آہنگی۔",
            scholarlyServicesEnglish = "Defense of Orthodox Sunni creed, authorship of Saiful Jabbar and Al-Mu'taqad Al-Muntaqad.",
            booksAuthored = listOf(
                "سیف الجبار المسلول على أعداء الأبرار",
                "البوارق المحمدية في رد الشياطين النجدية",
                "المعتقد المنتقد معتمد المستند",
                "احقاق الحق المبين في إبطال أصول المبتدعين",
                "تبت النجدي واستقر الهدى",
                "فصل الخطاب في إثبات أحوال الأقطاب",
                "تلخیص الحق في عقائد الصدق"
            ),
            relatedVideoTitles = listOf("سوانحِ حیات حضرت شاہ فضلِ رسول قادری بدایونی ؒ", "علمی و تصنیفی کارنامے"),
            references = listOf("تذکرۂ خانوادۂ قادریہ بدایوں شریف", "نزہۃ الخواطر", "حیاتِ فضلِ رسول")
        ),
        ScholarProfile(
            id = "shah_abdul_qadir",
            nameUrdu = "حضرت شاہ عبد القادر قادری بدایونی ؒ (محب الرسول)",
            nameHindi = "हज़रत शाह अब्दुल क़ादिर क़ादरी बदायूंनी ؒ (मुहिब-उर-रसूल)",
            nameEnglish = "Hazrat Shah Abdul Qadir Badayuni ؒ (Muhibb-e-Rasool)",
            nameHinglish = "Hazrat Shah Abdul Qadir Badayuni ؒ",
            titleUrdu = "محبوب الرسول، رئیس المتکلمین، شمس العلماء",
            titleHindi = "महबूब-उर-रसूल, रईस-उल-मुतकल्लिमीन",
            titleEnglish = "Mahboob-ur-Rasool, Chief of Theologians, Shams-ul-Ulama",
            biographyUrdu = "حضرت شاہ فضلِ رسول قدس سرہ کے برگزیدہ فرزند، علم و فضل اور عشقِ رسول ﷺ کے کوہِ گراں۔ آپ نے آل انڈیا سنی کانفرنس اور تحریکِ آزادی میں برصغیر کے مسلمانوں کی تاریخی رہنمائی فرمائی۔ آپ نے ردِ بدعات، تحفظِ ختم نبوت اور خانقاہی نظام کو رفعتیں بخشیں۔",
            biographyHindi = "हज़रत शाह फ़ज़्ल-ए-रसूल के सुपुत्र, ज्ञान व इश्क़-ए-रसूल ﷺ के प्रतीक। आपने सुन्नी कांफ्रेंस और स्वतंत्रता संग्राम में अहम भूमिका निभाई।",
            biographyEnglish = "Distinguished theologian and son of Shah Fazle Rasool, revered for profound scholarship and divine love for Prophet Muhammad ﷺ.",
            biographyHinglish = "Aap ilm-o-fazal aur Ishq-e-Rasool ﷺ ke azeem paikar the.",
            eraOrDates = "1253ھ تا 1319ھ (1837ء تا 1901ء)",
            roleUrdu = "سجادہ نشین و جلیل القدر مصنف و متکلم",
            roleEnglish = "Sajjadah Nashin, Theologian and Statesman",
            spiritualLineage = "سلسلہ عالیہ قادریہ",
            scholarlyServicesUrdu = "دفاعِ ختمِ نبوت و ناموسِ رسالت ﷺ، کثیر تصانیف، مدارس کا قیام اور فتاویٰ نویسی۔",
            scholarlyServicesEnglish = "Defense of the sanctity of Prophethood, establishing madrasas, and legal jurisprudence.",
            booksAuthored = listOf(
                "رسالہ شمس الضحیٰ في رد منكر الضياء",
                "الدر المنثور في بيان البعث والنشور",
                "تحفۃ الاخیار",
                "فتاویٰ قادریہ"
            ),
            relatedVideoTitles = listOf("حضرت محبوب الرسول شاہ عبد القادر بدایونی ؒ کی حیات و خدمات"),
            references = listOf("تذکرہ علمائے ہند", "تذکرۂ خانوادۂ قادریہ")
        ),
        ScholarProfile(
            id = "qadri_dulha",
            nameUrdu = "حضرت قادری دولہا ؒ (حضرت شاہ محمد عبد المجید قادری)",
            nameHindi = "हज़रत क़ादरी दूल्हा ؒ (हज़रत शाह मोहम्मद अब्दुल मजीद क़ादरी)",
            nameEnglish = "Hazrat Qadri Dulha ؒ (Hazrat Shah Mohammad Abdul Majeed Qadri)",
            nameHinglish = "Hazrat Qadri Dulha ؒ",
            titleUrdu = "قادری دولہا، صاحبِ خانقاہِ مجیدیہ، زبدۃ العارفین",
            titleHindi = "क़ादरी दूल्हा, साहिब-ए-ख़ानक़ाह-ए-मजीदिया",
            titleEnglish = "Qadri Dulha, Gnostic Master, Founder of Khanqah Majeediah",
            biographyUrdu = "آپ برصغیر پاک و ہند کے معروف ترین صوفی باصفا اور محبوبِ بارگاہِ قادریت ہیں جنہیں عوام و خواص میں 'قادری دولہا' کے لقبِ مبارک سے جانا جاتا ہے۔ آپ ہی کی نسبت سے خانقاہ کا ایک حصہ 'خانقاہِ مجیدیہ' کہلاتا ہے۔ آپ نے اپنی پوری حیات ذکر و عبادت، مساکین کی دستگیری، تزکیۂ نفس اور روحانی فیض کی تقسیم میں بسر فرمائی۔",
            biographyHindi = "आप अत्यंत आदरणीय सूफ़ी बुज़ुर्ग हैं जिन्हें अवाम में 'क़ादरी दूल्हा' के नाम से जाना जाता है। आपकी बरकतों से लाखों लोगों ने आत्मिक रोशनी पाई।",
            biographyEnglish = "Revered Sufi master of Badaun affectionately titled 'Qadri Dulha'. His spiritual gatherings and benevolence transformed countless hearts.",
            biographyHinglish = "Aap 'Qadri Dulha' ke laqab-e-mubarak se mashhoor hain aur Khanqah Majeediah ke rukh-e-roshan hain.",
            eraOrDates = "1250ھ تا 1319ھ (1834ء تا 1901ء)",
            roleUrdu = "سجادہ نشین و مرجعِ خلائق",
            roleEnglish = "Sajjadah Nashin & Revered Spiritual Anchor",
            spiritualLineage = "سلسلہ عالیہ قادریہ مجیدیہ",
            scholarlyServicesUrdu = "خانقاہ کے بنیادی احاطے و مسجد کی تعمیر، لنگرِ عام، تصوف و اخلاق کی عملی تربیت۔",
            scholarlyServicesEnglish = "Construction of the Khanqah complex, public kitchen (langar), and practical spiritual ethics.",
            booksAuthored = listOf(
                "ارشاد المجيد في سلوك المريد",
                "مجموعہ اوراد و وظائفِ قادریہ",
                "رسالہ در سلوک و مراقبہ"
            ),
            relatedVideoTitles = listOf("فیوضاتِ قادری دولہا حضرت شاہ عبد المجید قادری بدایونی ؒ"),
            references = listOf("تذکرۂ خانوادۂ قادریہ", "انوارِ بدایوں")
        ),
        ScholarProfile(
            id = "hazrat_salimul_qadri",
            nameUrdu = "حضرت سالم القادری بدایونی ؒ",
            nameHindi = "हज़रत सालिमुल क़ादरी बदायूंनी ؒ",
            nameEnglish = "Hazrat Salimul Qadri Badayuni ؒ",
            nameHinglish = "Hazrat Salimul Qadri Badayuni ؒ",
            titleUrdu = "عالمِ باعمل، مربیِ سالکین، شیخِ طریقت",
            titleHindi = "आलिम-ए-बाअमल, मुरब्बी-ए-सालिकीन",
            titleEnglish = "Eminent Spiritual Mentor and Scholar of Hadith & Tariqah",
            biographyUrdu = "آپ خانوادۂ قادریہ بدایوں شریف کے نہایت متقی اور جلیل القدر شیخِ طریقت تھے۔ آپ نے تصوفِ اسلامی کو شریعتِ مطہرہ کے اصولوں کے عین مطابق عام فرمایا اور ہزاروں طالبانِ حق کی روحانی تربیت فرمائی۔ آپ کی مجلسِ وعظ و نصیحت دلوں میں تقویٰ اور للہیت بیدار کرتی تھی۔",
            biographyHindi = "आप ख़ानवादा-ए-क़ादरिया बदायूं शरीफ़ के मुत्तक़ी और सम्मानित शैख़-ए-तरीक़त थे। आपने हज़ारों सालिकों का आत्मिक मार्गदर्शन किया।",
            biographyEnglish = "A revered saint and scholar of Badaun whose devotion to Sunnah and mentoring nurtured generations of seekers across India.",
            biographyHinglish = "Aap Khanwada-e-Qadria ke azeem shaikh-e-tariqat the.",
            eraOrDates = "13ویں تا 14ویں صدی ہجری",
            roleUrdu = "شیخِ طریقت و مربی",
            roleEnglish = "Spiritual Guide & Mentor",
            spiritualLineage = "سلسلہ عالیہ قادریہ",
            scholarlyServicesUrdu = "تربیتِ مریدین، شریعت کی ترویج، اصلاحِ معاشرہ۔",
            scholarlyServicesEnglish = "Spiritual mentorship, adherence to Shariah, and communal guidance.",
            booksAuthored = listOf(
                "رسالہ در تصوف و تقویٰ",
                "وظائفِ سالمین"
            ),
            references = listOf("تذکرۂ خانوادۂ قادریہ بدایوں شریف")
        ),
        ScholarProfile(
            id = "shaheed_e_baghdad",
            nameUrdu = "حضرت شہیدِ بغداد ؒ (حضرت شاہ آلِ رسول حسنین بدایونی)",
            nameHindi = "हज़रत शहीद-ए-बग़दाद ؒ (हज़रत शाह आले रसूल हसनैन बदायूंनी)",
            nameEnglish = "Hazrat Shaheed-e-Baghdad ؒ (Hazrat Shah Aale Rasool Hasnain Badayuni)",
            nameHinglish = "Hazrat Shaheed-e-Baghdad ؒ",
            titleUrdu = "شہیدِ بغداد، عاشقِ غوثِ اعظم، فدائے مدینۃ الاولیاء",
            titleHindi = "शहीद-ए-बग़दाद, आशिक़-ए-ग़ौस-ए-आज़म",
            titleEnglish = "Shaheed-e-Baghdad, Devotee of Ghous-ul-Azam, Revered Martyr",
            biographyUrdu = "آپ خانوادۂ قادریہ بدایوں شریف کے وہ مایہ ناز بزرگ ہیں جنہیں حضور غوثِ اعظم سیدنا شیخ عبد القادر جیلانی رضی اللہ عنہ کی بارگاہِ اقدس (بغداد شریف) سے بے پناہ والہانہ عشق تھا اور آپ نے بغداد معلیٰ کی مقدس سرزمین پر شہادت کا عظیم رتبہ پایا۔ آپ کا مزار اور یادگار آج بھی عقیدت مندوں کے دلوں میں عشق و وفا کی علامت ہے۔",
            biographyHindi = "आप बदायूं शरीफ़ के वह महान बुज़ुर्ग हैं जिनका हज़रत ग़ौस-ए-आज़म के आस्ताने (बग़दाद शरीफ़) से गहरा रूहानी इश्क़ था और आपने वहीं शहादत पाई।",
            biographyEnglish = "Illustrious saint of Badaun renowned as 'Shaheed-e-Baghdad' due to his profound spiritual bond with Baghdad Shareef and attainment of martyrdom there.",
            biographyHinglish = "Aap 'Shaheed-e-Baghdad' ke azeem martabe par faiz hain.",
            eraOrDates = "14ویں صدی ہجری",
            roleUrdu = "شہیدِ راہِ حق و بزرگِ سلسلہ قادریہ",
            roleEnglish = "Martyr of Baghdad & Qadria Saint",
            spiritualLineage = "سلسلہ عالیہ قادریہ بدایوں شریف",
            scholarlyServicesUrdu = "بغداد شریف و برصغیر کے روحانی تعلق کو استحکام بخشنا، عشقِ اہل بیت و اولیاء۔",
            scholarlyServicesEnglish = "Deepening spiritual ties between Baghdad Shareef and the Indian Subcontinent.",
            booksAuthored = listOf(
                "ارمغانِ بغداد",
                "مناجاتِ قادریہ"
            ),
            references = listOf("تذکرۂ خانوادۂ قادریہ بدایوں شریف", "نزہۃ العاشقین")
        ),
        ScholarProfile(
            id = "hazrat_ateef_miya",
            nameUrdu = "جانشین حضور تاجدارِ اہل سنت حضرت علامہ مولانا شیخ عبد الغنی محمد عاطف قادری ازہری عشقی بدایونی (عاطف میاں)",
            nameHindi = "जानशीन हुज़ूर ताजदार-ए-अहले सुन्नत हज़रत अल्लामा मौलाना शैख़ अब्दुल ग़नी मोहम्मद अतीफ़ क़ादरी अज़हरी इश्क़ी बदायूंनी (सज्जादा नशीन)",
            nameEnglish = "Janasheen Huzoor Tajdar-e-Ahle Sunnat Hazrat Allama Maulana Sheikh Abdul Ghani Muhammad Ateef Qadri Azhari Ishqui Badayuni (Ateef Miya Qadri)",
            nameHinglish = "Hazrat Ateef Miya Qadri Azhari Badayuni",
            titleUrdu = "جانشین حضور تاجدارِ اہل سنت، صاحبِ سجادہ خانقاہِ عالیہ قادریہ بدایوں شریف (یو پی، بھارت)",
            titleHindi = "जानशीन हुज़ूर ताजदार-ए-अहले सुन्नत, साहिब-ए-सज्जादा ख़ानक़ाह-ए-आलिया क़ादरिया, बदायूं शरीफ़",
            titleEnglish = "Sahib-e-Sajjada Khanqah-e-Aaliya Qadriah, Badaun Shareef (U.P., India)",
            biographyUrdu = "جانشین حضور تاجدارِ اہل سنت حضرت علامہ مولانا شیخ عبد الغنی محمد عاطف قادری ازہری عشقی بدایونی، صاحبِ سجادہ خانقاہِ عالیہ قادریہ، بدایوں شریف (یو پی، بھارت)۔ آپ جامعہ ازہر مصر کے ممتاز فاضل، جید محقق اور سلسلہ عالیہ قادریہ کے مسند نشین ہیں۔ آپ کی زیرِ سرپرستی خانقاہِ عالیہ قادریہ بدایوں شریف کے تمام دینی، روحانی، اشاعتی اور تعلیمی امور خوش اسلوبی سے جاری ہیں اور دنیا بھر کے مریدین و زائرین آپ کے فیض سے مستفید ہو رہے ہیں۔",
            biographyHindi = "जानशीन हुज़ूर ताजदार-ए-अहले सुन्नत हज़रत अल्लामा मौलाना शैख़ अब्दुल ग़नी मोहम्मद अतीफ़ क़ादरी अज़हरी इश्क़ी बदायूंनी, साहिब-ए-सज्जादा ख़ानक़ाह-ए-आलिया क़ादरिया, बदायूं शरीफ़ (उ.प्र., भारत)। आप जामिया अज़हर मिस्र के प्रतिष्ठित विद्वान और वर्तमान सज्जादा नशीन हैं।",
            biographyEnglish = "Janasheen Huzoor Tajdar-e-Ahle Sunnat Hazrat Allama Maulana Sheikh Abdul Ghani Muhammad Ateef Qadri Azhari Ishqui Badayuni, Sahib-e-Sajjada Khanqah-e-Aaliya Qadriah, Badaun Shareef (U.P., India). Prominent Al-Azhar graduate, spiritual leader, and head of Khanqah Qadriah.",
            biographyHinglish = "Janasheen Huzoor Tajdar-e-Ahle Sunnat Hazrat Allama Maulana Sheikh Abdul Ghani Muhammad Ateef Qadri Azhari Ishqui Badayuni, Sahib-e-Sajjada Khanqah-e-Aaliya Qadriah, Badaun Shareef.",
            eraOrDates = "معاصر (حفظہ اللہ ورعاہ)",
            roleUrdu = "صاحبِ سجادہ خانقاہِ عالیہ قادریہ بدایوں شریف",
            roleEnglish = "Sahib-e-Sajjada Khanqah-e-Aaliya Qadriah, Badaun Shareef",
            spiritualLineage = "سلسلہ عالیہ قادریہ برکاتیہ مجیدیہ بدایوں شریف",
            scholarlyServicesUrdu = "خانقاہِ عالیہ قادریہ کی عالمی سرپرستی، کتب خانہ قادریہ کے تاریخی مخطوطات کی اشاعت، تصوف و شریعت کی رہنمائی۔",
            scholarlyServicesEnglish = "Global spiritual patronage of Khanqah-e-Aaliya Qadriah, preservation of historical manuscripts, and guidance of seekers worldwide.",
            booksAuthored = listOf(
                "خطبات و ارشاداتِ عشقی",
                "نظامِ تصوف اور عصرِ حاضر",
                "ہدایات برائے سالکینِ راہِ قادریت"
            ),
            relatedVideoTitles = listOf("خطاباتِ مبارکہ سجادہ نشین حضرت عاطف میاں قادری مدظلہ العالی"),
            references = listOf("آفیشل ریکارڈز خانقاہ عالیہ قادریہ بدایوں شریف (qadri.in)")
        ),
        ScholarProfile(
            id = "hazrat_azzam_miya",
            nameUrdu = "حضرت علامہ اعظم قادری بدایونی (اعظم میاں)",
            nameHindi = "हज़रत अल्लामा अज़्ज़ाम क़ादरी बदायूंनी (अज़्ज़ाम मियां)",
            nameEnglish = "Hazrat Allama Azzam Qadri Badayuni (Azzam Miya)",
            nameHinglish = "Hazrat Azzam Miya Qadri Badayuni",
            titleUrdu = "شیخ المشائخ، نباضِ ملت، خادمِ سلسلہ عالیہ قادریہ",
            titleHindi = "शैख़ुल मशाइख़, ख़ादिम-ए-सिलसिला-ए-क़ादरिया",
            titleEnglish = "Senior Scholar and Spiritual Pillar of Khanqah Qadriah",
            biographyUrdu = "آپ خانوادۂ قادریہ بدایوں شریف کے نہایت معزز اور محترم بزرگ ہیں۔ آپ نے خانقاہ کی دعوتی، تعلیمی اور رفاہی سرگرمیوں میں ہمیشہ پیش پیش رہ کر دینِ حق اہل سنت کی ترویج فرمائی۔ مریدین و متوسلین کے دلوں میں آپ کی شفقت اور رہنمائی کا گہرا احترام ہے۔",
            biographyHindi = "आप ख़ानवादा-ए-क़ादरिया बदायूं शरीफ़ के अत्यंत सम्मानित बुज़ुर्ग हैं जिन्होंने दीन-ए-हक़ और ख़ानक़ाह की ख़िदमात को आगे बढ़ाया।",
            biographyEnglish = "Respected senior figure of Khanwada-e-Qadria Badaun Shareef, dedicated to teaching, spiritual reform, and community service.",
            biographyHinglish = "Aap Khanqah Qadriah Badaun Shareef ke azeem buzurgan mein se hain.",
            eraOrDates = "معاصر (حفظہ اللہ ورعاہ)",
            roleUrdu = "بزرگِ خانوادۂ قادریہ و رہنما",
            roleEnglish = "Senior Spiritual Luminary of Badaun",
            spiritualLineage = "سلسلہ عالیہ قادریہ",
            scholarlyServicesUrdu = "جامعہ و خانقاہ کے انتظامات، دینی بیداری، مریدین کی فکری و روحانی تربیت۔",
            scholarlyServicesEnglish = "Madrasa education, religious guidance, and spiritual upbringing of devotees.",
            booksAuthored = listOf(
                "مقالاتِ اعظمی",
                "فیوضاتِ قادریہ بدایوں"
            ),
            references = listOf("خانقاہ قادریہ بدایوں شریف")
        )
    )

    // Historical Tazkira Timeline
    val TAZKIRA_TIMELINE: List<TazkiraMilestone> = listOf(
        TazkiraMilestone(
            year = "1213ھ / 1798ء",
            titleUrdu = "ولادتِ باسعادت حضرت شاہ فضلِ رسول بدایونی ؒ",
            titleHindi = "हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ की विलादत",
            titleEnglish = "Birth of Hazrat Shah Fazl-e-Rasool Badayuni ؒ",
            descriptionUrdu = "بدایوں کی پاکیزہ سرزمین پر اس عظیم شخصیت کی ولادت ہوئی جنہوں نے آگے چل کر علم و عرفان کے روشن مینار قائم کیے۔",
            descriptionEnglish = "Birth of the great scholar who laid foundational scholarly and spiritual traditions in Budaun."
        ),
        TazkiraMilestone(
            year = "1250ھ / 1834ء",
            titleUrdu = "قیامِ خانقاہ قادریہ مجیدیہ بدایوں شریف",
            titleHindi = "ख़ानक़ाह क़ादरिया मजीदिया की स्थापना",
            titleEnglish = "Establishment of Khanqah Qadriah Majeediah",
            descriptionUrdu = "محلہ سوتھا بدایوں شریف میں خانقاہ قادریہ مجیدیہ کی بنیاد رکھی گئی جو خطے میں تصوف اور شریعت کا مرکز بنی۔",
            descriptionEnglish = "Formal establishment of Khanqah Qadriah Majeediah in Mohalla Sotha, Budaun, becoming a center of Sufism and sacred learning."
        ),
        TazkiraMilestone(
            year = "1289ھ / 1872ء",
            titleUrdu = "اشاعتِ احقاق الحق و مصنفاتِ کثیرہ",
            titleHindi = "अहक़ाक़ुल हक़ का ऐतिहासिक प्रकाशन",
            titleEnglish = "Publication of Ihqaq-ul-Haq and seminal treatises",
            descriptionUrdu = "عقائدِ اہل سنت کے تحفظ میں معرکۃ الآراء کتاب 'احقاق الحق' منظر عام پر آئی اور خانقاہ نے اشاعتی میدان میں نئی تاریخ رقم کی۔",
            descriptionEnglish = "Publication of monumental scholarly works in defense of the traditional Sunni creed."
        ),
        TazkiraMilestone(
            year = "1319ھ / 1901ء",
            titleUrdu = "دورِ شاہ عبد المجید و شاہ عبد القادر بدایونی ؒ",
            titleHindi = "हज़रत शाह अब्दुल मजीद व शाह अब्दुल क़ादिर का दौर",
            titleEnglish = "Era of Shah Abdul Majeed & Shah Abdul Qadir Badayuni",
            descriptionUrdu = "خانقاہ کی روحانی توسیع اور خطے بھر میں تحریکاتِ دینیہ کی قیادت۔",
            descriptionEnglish = "Spiritual flourishing and national leadership in religious and educational movements."
        ),
        TazkiraMilestone(
            year = "1430ھ / 2009ء",
            titleUrdu = "تاسیسِ مکتبہ قادریہ و تذکرہ خانوادہ قادریہ",
            titleHindi = "मकतब-ए-क़ादरिया व तज़किरा ख़ानवादा का संपादन",
            titleEnglish = "Cataloging Historical Manuscripts & Tazkira",
            descriptionUrdu = "مولانا اسید الحق قادری ؒ کی نگرانی میں بدایوں کے نادر قلمی نسخوں اور تذکرہ خانوادہ قادریہ کی تدوین و جدید اشاعت۔",
            descriptionEnglish = "Comprehensive academic editing and publication of historical manuscripts and family chronicles."
        ),
        TazkiraMilestone(
            year = "تاحال",
            titleUrdu = "عہدِ حاضر: جدید ڈیجیٹل دور (qadri.in)",
            titleHindi = "वर्तमान: डिजिटल युग और ऑनलाइन लाइब्रेरी",
            titleEnglish = "Present Era: Official Digital Platform (qadri.in)",
            descriptionUrdu = "خانقاہ قادریہ مجیدیہ کی کتب، ویڈیوز اور خدمات کو دنیا بھر کے مسلمانوں کے لیے آن لائن پورٹل اور موبائل ایپ کے ذریعے میسر کرنا۔",
            descriptionEnglish = "Expanding access to Khanqah Qadriah's literature, events, and spiritual guidance through modern digital platforms."
        )
    )

    // Dedicated Curated Speeches of Janasheen Huzoor Tajdar-e-Ahle Sunnat Hazrat Sufi Mohammad Ateef Miya Qadri (Haq Multimedia Official)
    val HUZOOR_ATEEF_MIYA_SPEECHES: List<HuzoorAteefMiyaSpeech> = listOf(
        HuzoorAteefMiyaSpeech(
            id = "ateef_speech_urs_qadri",
            titleUrdu = "عرسِ قادری بدایوں شریف: صدارتی خطابِ مبارک",
            titleHindi = "उर्स-ए-क़ादरी बदायूं शरीफ़: सदारती ख़िताब-ए-मुबारक",
            titleEnglish = "Presidential Address at Urs-e-Qadri Budaun Shareef",
            occasionUrdu = "سالانہ عرسِ قادری مبارک، خانقاہ بدایوں شریف",
            occasionEnglish = "Annual Urs-e-Qadri Mubarak, Khanqah Budaun",
            duration = "38:45",
            youtubeId = "haq_ateef_urs",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia",
            summaryUrdu = "سلسلہ عالیہ قادریہ کی روحانی ضیا پاشیاں، مسلکِ حق اہل سنت کی پاسداری اور خانوادۂ قادریہ بدایوں شریف کے تاریخی فیوض و برکات پر جانشین حضور تاجدارِ اہلِ سنت کا ولولہ انگیز اور ایمان افروز خطاب۔",
            isFeatured = true,
            viewsCount = "حق ملٹی میڈیا آفیشل"
        ),
        HuzoorAteefMiyaSpeech(
            id = "ateef_speech_ishq_mustafa",
            titleUrdu = "عظمت و محبتِ مصطفیٰ ﷺ اور اکابرینِ بدایوں کا منہج",
            titleHindi = "अज़्मत व मोहब्बत-ए-मुस्तफ़ा ﷺ और अकाबिर-ए-बदायूँ का मिन्हाज",
            titleEnglish = "Love of Prophet ﷺ & Heritage of Budaun Elders",
            occasionUrdu = "محفلِ میلاد النبی ﷺ و سالانہ روحانی کانفرنس",
            occasionEnglish = "Milad-un-Nabi ﷺ Gathering & Spiritual Conference",
            duration = "34:10",
            youtubeId = "haq_ateef_ishq",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia",
            summaryUrdu = "ایمان کی اصل اور بنیاد عشقِ رسول ﷺ پر استواری، شریعتِ مطہرہ کی پابندی اور صوفیائے کرام کے طرزِ عمل کی پیروی پر بصیرت افروز بیان۔",
            isFeatured = true,
            viewsCount = "حق ملٹی میڈیا آفیشل"
        ),
        HuzoorAteefMiyaSpeech(
            id = "ateef_speech_tarbiyat_youth",
            titleUrdu = "اصلاحِ معاشرہ، حقوق العباد اور نوجوانوں کی اسلامی تربیت",
            titleHindi = "इस्लाहे मुआशरा, हुक़ूक़ुल इबाद और नौजवानों की इस्लामी तरबियत",
            titleEnglish = "Social Reform & Moral Guidance for Youth",
            occasionUrdu = "روحانی تربیتی اجتماع، خانقاہ قادریہ بدایوں",
            occasionEnglish = "Spiritual Guidance Congregation, Khanqah Budaun",
            duration = "29:30",
            youtubeId = "haq_ateef_youth",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia",
            summaryUrdu = "والدین کے حقوق، نماز کی پابندی، بدعقیدگی و فتنوں سے اجتناب اور نوجوان نسل کو بااخلاق و باکردار مسلمان بنانے پر پرتاثیر نصیحتیں۔",
            isFeatured = false,
            viewsCount = "حق ملٹی میڈیا آفیشل"
        ),
        HuzoorAteefMiyaSpeech(
            id = "ateef_speech_ghous_azam",
            titleUrdu = "سیرت و ارشاداتِ سیدنا غوث الثقلین حضور غوثِ اعظم ؓ",
            titleHindi = "सीरत व इरशादात हुज़ूर ग़ौस-ए-आज़म ؓ",
            titleEnglish = "Teachings of Hazrat Ghous-e-Azam (RA)",
            occasionUrdu = "ماہانہ گیارہویں شریف محفلِ نور",
            occasionEnglish = "Monthly Gyarwee Shareef Gathering",
            duration = "42:15",
            youtubeId = "haq_ateef_ghous",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia",
            summaryUrdu = "سلطان الاولیاء حضرت شیخ عبد القادر جیلانی قدس سرہ کے افکارِ عالیہ اور سلسلہ عالیہ قادریہ کے سالکین کے لیے سلوک و معرفت کی راہیں کھولتا ہوا بیان۔",
            isFeatured = false,
            viewsCount = "حق ملٹی میڈیا آفیشل"
        ),
        HuzoorAteefMiyaSpeech(
            id = "ateef_speech_shah_fazle_rasool",
            titleUrdu = "سیف اللہ المسلول شاہ فضلِ رسول بدایونی ؒ کے علمی کارنامے",
            titleHindi = "सैफ़ुल्लाहिल मशलूल हज़रत शाह फ़ज़्ल-ए-रसूल बदायूंनी ؒ के कारनामे",
            titleEnglish = "Scholarly Services of Hazrat Shah Fazle Rasool (RA)",
            occasionUrdu = "یومِ فضلِ رسول ؒ، بدایوں شریف",
            occasionEnglish = "Day of Shah Fazle Rasool, Budaun Shareef",
            duration = "36:20",
            youtubeId = "haq_ateef_fazlerasool",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia",
            summaryUrdu = "مسلکِ حق اہل سنت و جماعت کے دفاع میں سیف اللہ المسلول حضرت شاہ فضلِ رسول قادری بدایونی قدس سرہ کے قلمی و فکری جہاد پر تحقیقی روشنی۔",
            isFeatured = false,
            viewsCount = "حق ملٹی میڈیا آفیشل"
        ),
        HuzoorAteefMiyaSpeech(
            id = "ateef_speech_zikr_tazkiya",
            titleUrdu = "ذکر اللہ کی حقیقت، دل کا سکون اور خانقاہی تربیت",
            titleHindi = "ज़िक्रुल्लाह की हक़ीक़त व ख़ानक़ाही तरबियत",
            titleEnglish = "Remembrance of Allah & Spiritual Peace",
            occasionUrdu = "شبِ جمعہ کی روحانی مجلس و مراقبہ",
            occasionEnglish = "Thursday Spiritual Night & Meditation",
            duration = "25:40",
            youtubeId = "haq_ateef_zikr",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia",
            summaryUrdu = "قلبی سکون حاصل کرنے کے نبوی طریقے، اوراد و وظائفِ قادریہ کی برکات اور باطنی پاکیزگی کے اسباق۔",
            isFeatured = false,
            viewsCount = "حق ملٹی میڈیا آفیشل"
        )
    )

    // Curated Video Gallery
    val VIDEO_GALLERY: List<KhanqahVideoItem> = listOf(
        KhanqahVideoItem(
            id = "vid_ateef_urs_speech",
            titleUrdu = "صدارتی خطاب: عرسِ قادری بدایوں شریف - حضور عاطف میاں قادری",
            titleHindi = "सदारती ख़िताब: उर्स-ए-क़ादरी बदायूं - हुज़ूर आतिफ़ मियां क़ादरी",
            titleEnglish = "Presidential Speech: Urs-e-Qadri Budaun - Huzoor Ateef Miya Qadri",
            speakerUrdu = "حضور صوفی محمد عاطف میاں قادری (سجادہ نشین)",
            speakerEnglish = "Hazrat Sufi Mohammad Ateef Miya Qadri (Sajjadah Nashin)",
            duration = "38:45",
            category = VideoCategory.HUZOOR_ATEEF_MIYA,
            youtubeId = "haq_ateef_urs",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_ateef_ishq_rasool",
            titleUrdu = "خطابِ خاص: عظمت و محبتِ مصطفیٰ ﷺ - حضور عاطف میاں قادری",
            titleHindi = "ख़िताब-ए-ख़ास: अज़्मत व मोहब्बत-ए-मुस्तफ़ा ﷺ - हुज़ूर आतिफ़ मियां",
            titleEnglish = "Special Speech: Greatness & Love of Prophet ﷺ - Huzoor Ateef Miya",
            speakerUrdu = "حضور صوفی محمد عاطف میاں قادری (سجادہ نشین)",
            speakerEnglish = "Hazrat Sufi Mohammad Ateef Miya Qadri (Sajjadah Nashin)",
            duration = "34:10",
            category = VideoCategory.HUZOOR_ATEEF_MIYA,
            youtubeId = "haq_ateef_ishq",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_ateef_youth_guidance",
            titleUrdu = "تربیتی بیان: اصلاحِ معاشرہ و رہنمائی نوجوانانِ اسلام - حضور عاطف میاں",
            titleHindi = "इस्लाहे मुआशरा व रहनुमाई नौजवान - हुज़ूर आतिफ़ मियां",
            titleEnglish = "Social Reform & Guidance for Muslim Youth - Huzoor Ateef Miya",
            speakerUrdu = "حضور صوفی محمد عاطف میاں قادری (سجادہ نشین)",
            speakerEnglish = "Hazrat Sufi Mohammad Ateef Miya Qadri (Sajjadah Nashin)",
            duration = "29:30",
            category = VideoCategory.HUZOOR_ATEEF_MIYA,
            youtubeId = "haq_ateef_youth",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_urs_qadri_1",
            titleUrdu = "مستند مناظر: عرسِ قادری مبارک خانقاہ بدایوں شریف",
            titleHindi = "उर्स-ए-क़ादरी मुबारक ख़ानक़ाह बदायूं शरीफ़",
            titleEnglish = "Visuals of Urs-e-Qadri Mubarak at Khanqah Budaun",
            speakerUrdu = "زیرِ سرپرستی سجادہ نشین خانقاہ قادریہ",
            speakerEnglish = "Under the patronage of Sajjadah Nashin",
            duration = "15:40",
            category = VideoCategory.URS_QADRI,
            youtubeId = "haq_urs_special",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_lecture_silsila",
            titleUrdu = "سلسلہ عالیہ قادریہ کی خصوصیات اور خانقاہ کا کردار",
            titleHindi = "सिलसिला-ए-क़ादरिया और ख़ानक़ाह की भूमिका",
            titleEnglish = "Significance of Silsila Qadria and Khanqah Heritage",
            speakerUrdu = "علمائے کرام خانقاہ قادریہ",
            speakerEnglish = "Scholars of Khanqah Qadriah",
            duration = "32:10",
            category = VideoCategory.LECTURES,
            youtubeId = "haq_lectures_01",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_seerat_nabi",
            titleUrdu = "کانفرنس سیرت النبی ﷺ - خانقاہ قادریہ مجیدیہ",
            titleHindi = "सीरत-उन-नबी ﷺ कांफ्रेंस ख़ानक़ाह बदायूं",
            titleEnglish = "Seerat-un-Nabi ﷺ Conference at Khanqah Budaun",
            speakerUrdu = "علمائے اہل سنت بدایوں شریف",
            speakerEnglish = "Eminent Sunni Scholars of Budaun",
            duration = "45:15",
            category = VideoCategory.SEERAT,
            youtubeId = "haq_seerat_conf",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_mehfil_naat",
            titleUrdu = "محفلِ نعت و مناقبِ حضور غوثِ اعظم ؓ",
            titleHindi = "महफ़िल-ए-नात व मनाक़िब-ए-ग़ौस-ए-आज़म ؓ",
            titleEnglish = "Mehfil-e-Naat & Manaqib-e-Ghous-e-Azam ؓ",
            speakerUrdu = "نعت خوانانِ بدایوں شریف",
            speakerEnglish = "Renowned Naat Khwans",
            duration = "28:30",
            category = VideoCategory.MEHFIL_SAMA,
            youtubeId = "haq_naat_mehfil",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_history_budaun",
            titleUrdu = "بدایوں شریف: تاریخ، اولیاء اور علمی روایات",
            titleHindi = "बदायूं शरीफ़: इतिहास, औलिया और शैक्षिक विरासत",
            titleEnglish = "Budaun Shareef: History, Saints and Scholarly Heritage",
            speakerUrdu = "محققینِ خانقاہ قادریہ",
            speakerEnglish = "Researchers of Khanqah Qadriah",
            duration = "22:50",
            category = VideoCategory.HISTORICAL,
            youtubeId = "haq_history_doc",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_latest_haq_1",
            titleUrdu = "تازہ ترین بیانات و مواعظِ حسنہ: حق ملٹی میڈیا آفیشل",
            titleHindi = "ताज़ा तरीन बयानात व प्रवचन: हक़ मल्टीमीडिया",
            titleEnglish = "Latest Speeches & Spiritual Discourses - Haq Multimedia",
            speakerUrdu = "اکابرین و علمائے خانقاہ قادریہ",
            speakerEnglish = "Elders & Scholars of Khanqah Qadriah",
            duration = "38:40",
            category = VideoCategory.LATEST,
            youtubeId = "haq_latest_01",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        ),
        KhanqahVideoItem(
            id = "vid_bayaan_tazkiya",
            titleUrdu = "تزکیۂ نفس اور معرفتِ الٰہی: تعلیماتِ غوث الثقلین ؒ",
            titleHindi = "तज़्किया-ए-नफ़्स व तालीमात-ए-ग़ौस-ए-आज़म",
            titleEnglish = "Spiritual Purification & Teachings of Ghaus-e-Azam",
            speakerUrdu = "مقررینِ خانقاہ قادریہ بدایوں",
            speakerEnglish = "Scholars of Khanqah Qadriah",
            duration = "40:20",
            category = VideoCategory.BAYAANS,
            youtubeId = "haq_bayaan_tazkiya",
            youtubeUrl = "https://www.youtube.com/@haqmultimedia"
        )
    )

    // Curated Photo Gallery Items
    val PHOTO_GALLERY: List<PhotoGalleryItem> = listOf(
        PhotoGalleryItem(
            id = "photo_khanqah_entrance",
            titleUrdu = "صدر دروازہ خانقاہ قادریہ مجیدیہ بدایوں شریف",
            titleHindi = "मुख्य द्वार ख़ानक़ाह क़ादरिया मजीदिया बदायूं",
            titleEnglish = "Main Gate of Khanqah Qadriah Majeediah Budaun",
            categoryUrdu = "خانقاہ و درگاہ",
            categoryEnglish = "Khanqah & Dargah",
            descriptionUrdu = "خانقاہ قادریہ مجیدیہ بدایوں شریف کا تاریخی بیرونی دروازہ اور محراب۔",
            descriptionEnglish = "Historic archway and main entrance of Khanqah Qadriah Majeediah Budaun."
        ),
        PhotoGalleryItem(
            id = "photo_dargah_shareef",
            titleUrdu = "مزاراتِ مقدسہ اکابرینِ بدایوں شریف",
            titleHindi = "मज़ार शरीफ़ अकाबरीन-ए-बदायूं",
            titleEnglish = "Sacred Shrines of Akabir-e-Budaun",
            categoryUrdu = "درگاہ شریف",
            categoryEnglish = "Sacred Shrines",
            descriptionUrdu = "حضرت شاہ فضل رسول و شاہ عبد المجید قادری ؒ کے مزاراتِ مطہرہ۔",
            descriptionEnglish = "Sanctuary and resting place of the spiritual masters of Budaun."
        ),
        PhotoGalleryItem(
            id = "photo_urs_gathering",
            titleUrdu = "سالانہ عرسِ قادری کا روحانی اجتماع",
            titleHindi = "सालाना उर्स-ए-क़ादरी का रूहानी मजमा",
            titleEnglish = "Spiritual Congregation at Annual Urs-e-Qadri",
            categoryUrdu = "عرسِ قادری",
            categoryEnglish = "Urs-e-Qadri",
            descriptionUrdu = "ملک و بیرونِ ملک سے آئے ہزاروں عقیدت مندوں کی شرکت۔",
            descriptionEnglish = "Thousands of devotees gathered for prayer, zikr, and blessings."
        ),
        PhotoGalleryItem(
            id = "photo_manuscript",
            titleUrdu = "قدیم قلمی مخطوطہ (مکتبہ قادریہ آرکائیو)",
            titleHindi = "प्राचीन पांडुलिपि (मकतब-ए-क़ादरिया)",
            titleEnglish = "Ancient Manuscript from Maktaba Qadria",
            categoryUrdu = "تاریخی نوادرات",
            categoryEnglish = "Historical Artifacts",
            descriptionUrdu = "خانقاہ قادریہ بدایوں شریف کے کتب خانے کا نایاب تاریخی نسخہ۔",
            descriptionEnglish = "Rare manuscript preserved in the Khanqah's official archival library."
        )
    )

    // Daily Spiritual Packet
    val TODAY_SPIRITUAL_PACKET = DailySpiritualPacket(
        hadithTitleUrdu = "طلبِ علم کی فضیلت",
        hadithArabic = "طَلَبُ الْعِلْمِ فَرِيضَةٌ عَلَى كُلِّ مُسْلِمٍ",
        hadithTranslationUrdu = "علمِ دین حاصل کرنا ہر مسلمان (مرد و عورت) پر فرض ہے۔",
        hadithHindi = "दीनी इल्म हासिल करना हर मुसलमान पर फ़र्ज़ है।",
        hadithEnglish = "Seeking sacred Islamic knowledge is an obligation upon every Muslim.",
        hadithReference = "سنن ابن ماجہ، حدیث: 224",
        
        duaTitleUrdu = "علم و حکمت میں برکت کی دعا",
        duaArabic = "رَّبِّ زِدْنِي عِلْمًا",
        duaTranslationUrdu = "اے میرے رب! میرے علم میں اضافہ فرما۔",
        duaHindi = "ऐ मेरे रब! मेरे ज्ञान में वृद्धि फ़रमा।",
        duaEnglish = "My Lord, increase me in knowledge.",
        duaReference = "سورة طٰہٰ، آیت: 114",
        
        eventTitleUrdu = "تاسیسِ خانقاہ قادریہ بدایوں شریف کی یادگار",
        eventDescriptionUrdu = "بدایوں شریف میں خانقاہ قادریہ کا قیام علم، خدمتِ خلق اور اصلاحِ باطن کا وہ تاریخی سنگ میل تھا جس کے اثرات صدیوں بعد آج بھی قادریہ سلسلے کے ذریعے جلوہ گر ہیں۔",
        eventDateOrPeriod = "ماہانہ روحانی یاد دہانی",
        
        personalityNameUrdu = "حضرت شاہ فضلِ رسول قادری بدایونی ؒ",
        personalityTitleUrdu = "جامع المعقول والمنقول، مصلحِ اعظم",
        personalityBriefUrdu = "برصغیر میں اہل سنت و جماعت کے مستند عقائد کے محافظ، مصنفِ احقاق الحق اور خانوادۂ قادریہ بدایوں شریف کے عظیم مورثِ روحانی۔",
        
        bookTitleUrdu = "احقاق الحق المبين",
        bookAuthorUrdu = "حضرت شاہ فضلِ رسول قادری بدایونی ؒ",
        bookBriefUrdu = "عقائدِ حقہ اہل سنت کے اثبات اور شبہات کے مدلل رد میں لکھی گئی تاریخی معرکۃ الآراء عربی و اردو کتاب۔",
        bookCategoryUrdu = "عقائد و کلام"
    )

    fun getScholarById(id: String): ScholarProfile? {
        return SCHOLARS_LIST.firstOrNull { it.id == id }
    }

    fun getVideosByCategory(category: VideoCategory): List<KhanqahVideoItem> {
        return VIDEO_GALLERY.filter { it.category == category }
    }
}
