package com.example.data.repository

import com.example.data.model.*

object KhanqahRepository {

    const val OFFICIAL_WEBSITE_URL = "https://www.qadri.in/"
    const val OFFICIAL_YOUTUBE_URL = "https://www.youtube.com/@haqmultimedia"
    const val OFFICIAL_YOUTUBE_HANDLE = "@haqmultimedia"
    const val OFFICIAL_BOOKS_URL = "https://www.qadri.in/books"
    const val OFFICIAL_CONTACT_EMAIL = "info@qadri.in"
    const val OFFICIAL_CONTACT_PHONE = "+91 94121 23456"
    const val OFFICIAL_ADDRESS_URDU = "خانقاہ قادریہ مجیدیہ، محلہ سوتھا، بدایوں شریف، اتر پردیش، پن کوڈ: 243601، بھارت"
    const val OFFICIAL_ADDRESS_ENGLISH = "Khanqah Qadriah Majeediah, Mohalla Sotha, Budaun, Uttar Pradesh - 243601, India"
    const val GOOGLE_MAPS_GEO = "geo:28.0315,79.1176?q=Khanqah+Qadriah+Budaun"

    // Recognized Scholars & Mashaikh of Khanqah Qadriah Budaun Shareef
    val SCHOLARS_LIST: List<ScholarProfile> = listOf(
        ScholarProfile(
            id = "shah_fazle_rasool",
            nameUrdu = "حضرت شاہ فضلِ رسول قادری بدایونی ؒ",
            nameHindi = "हज़रत शाह फ़ज़्ल-ए-रसूल क़ादरी बदायूंनी ؒ",
            nameEnglish = "Hazrat Shah Fazl-e-Rasool Qadri Badayuni ؒ",
            nameHinglish = "Hazrat Shah Fazl-e-Rasool Qadri Badayuni ؒ",
            titleUrdu = "جامع المعقول والمنقول، مصلحِ ملت، قطبِ وقت",
            titleHindi = "जामेउल माक़ूल वल मनक़ूल, मुसलेह-ए-मिल्लत",
            titleEnglish = "Jami-ul-Ma'qool wal Manqool, Reformer of the Era",
            biographyUrdu = "آپ برصغیر پاک و ہند کے جلیل القدر عالمِ دین، عظیم روحانی رہنما اور خانوادۂ قادریہ بدایوں شریف کے مایہ ناز بزرگ ہیں۔ آپ نے علومِ عقلیہ و نقلیہ میں بے مثال مہارت حاصل کی اور مسلکِ حق اہل سنت و جماعت کے تحفظ کے لیے نمایاں خدمات انجام دیں۔ آپ کے قلم سے درجنوں معرکۃ الآراء تصانیف منظر عام پر آئیں جن میں احقاقِ حق اور اصلاحِ احوال سر فہرست ہیں۔",
            biographyHindi = "आप उपमहाद्वीप के महान इस्लामी विद्वान, आध्यात्मिक मार्गदर्शक और खानवादा-ए-क़ादरिया बदायूं शरीफ़ के बुज़ुर्ग हैं। आपने सुन्नत व शरीअत के संरक्षण में अविस्मरणीय सेवाएं दीं।",
            biographyEnglish = "Hazrat Shah Fazl-e-Rasool Qadri Badayuni was a preeminent Islamic scholar, spiritual guide, and revered figure of Khanwada-e-Qadria Budaun Shareef. He was a master of traditional and rational sciences.",
            biographyHinglish = "Aap subcontinent ke azeem alim-e-deen aur Khanwada-e-Qadria Budaun Shareef ke azeem buzurgan-e-deen mein se hain.",
            eraOrDates = "1213ھ تا 1289ھ (1798ء تا 1872ء)",
            roleUrdu = "بانی و سرخیل خانوادۂ قادریہ بدایوں شریف",
            roleEnglish = "Foundational Pillar of Budaun Qadria Lineage",
            spiritualLineage = "سلسلہ عالیہ قادریہ مجددیہ و برکاتیہ",
            scholarlyServicesUrdu = "دینِ متین کی مدافعت، ہزاروں تشنگانِ علم کی تربیت، فتنوں کا قلمی رد اور خانقاہی نظام کی شریعت کے مطابق تزئین۔",
            scholarlyServicesEnglish = "Defense of Orthodox Sunni creed, education of thousands of scholars, and systematic institutionalization of Khanqah.",
            booksAuthored = listOf(
                "احقاق الحق المبين في إبطال أصول المبتدعين",
                "البوارق المحمدية في رد الشياطين النجدية",
                "فصل الخطاب",
                "تلخیص التلخیص",
                "جامع الفتاویٰ البدایونیہ"
            ),
            relatedVideoTitles = listOf("سوانحِ حیات حضرت شاہ فضلِ رسول قادری بدایونی ؒ", "علمی و تصنیفی کارنامے"),
            references = listOf("تذکرۂ خانوادۂ قادریہ بدایوں شریف", "نزہۃ الخواطر", "حیاتِ فضلِ رسول")
        ),
        ScholarProfile(
            id = "shah_abdul_majeed",
            nameUrdu = "حضرت شاہ عبد المجید قادری بدایونی ؒ",
            nameHindi = "हज़रत शाह अब्दुल मजीद क़ादरी बदायूंनी ؒ",
            nameEnglish = "Hazrat Shah Abdul Majeed Qadri Badayuni ؒ",
            nameHinglish = "Hazrat Shah Abdul Majeed Qadri Badayuni ؒ",
            titleUrdu = "صاحبِ خانقاہِ قادریہ مجیدیہ، زبدۃ العارفین",
            titleHindi = "साहिब-ए-ख़ानक़ाह-ए-क़ादरिया मजीदिया, ज़ुब्दतुल आरिफ़ीन",
            titleEnglish = "Founder of Khanqah Qadriah Majeediah, Eminent Gnostic",
            biographyUrdu = "آپ ہی کی نسبتِ مبارکہ سے یہ خانقاہ 'خانقاہِ قادریہ مجیدیہ' کہلاتی ہے۔ آپ نے اپنی پوری حیات ذکر و عبادت، مساکین کی دستگیری، تزکیۂ نفس اور روحانی فیض کی تقسیم میں بسر فرمائی۔ آپ کی خانقاہ بدایوں شریف میں مرجعِ خلائق رہی جہاں روزانہ سینکڑوں طالبانِ حق کو روحانی تسکین ملتی تھی۔",
            biographyHindi = "आपकी मुबारक निस्बत से इस ख़ानक़ाह को 'ख़ानक़ाह-ए-क़ादरिया मजीदिया' कहा जाता है। आपने जीवन भर ज़िक्र, इबादत और लोगों की आत्मिक सेवा की।",
            biographyEnglish = "It is after his blessed name that this noble spiritual center is named 'Khanqah Qadriah Majeediah'. He dedicated his life to spiritual purification, zikr, and serving humanity.",
            biographyHinglish = "Aap hi ki nisbat se yeh Mubarak Markaz 'Khanqah Qadriah Majeediah' kehlata hai.",
            eraOrDates = "1250ھ تا 1319ھ (1834ء تا 1901ء)",
            roleUrdu = "صاحبِ نام و سجادہ نشینِ اول خانقاہ مجیدیہ",
            roleEnglish = "Named Sajjadah of Khanqah Majeediah",
            spiritualLineage = "سلسلہ عالیہ قادریہ مجیدیہ",
            scholarlyServicesUrdu = "خانقاہ کے بنیادی احاطے و مسجد کی تعمیر، لنگرِ عام کا باقاعدہ انتظام، تصوف و اخلاق کی عملی تربیت۔",
            scholarlyServicesEnglish = "Construction of the Khanqah central complex, public kitchen (langar), and practical sufi ethics.",
            booksAuthored = listOf(
                "ارشاد المجيد في سلوك المريد",
                "مجموعہ اوراد و وظائفِ قادریہ",
                "رسالہ در سلوک و مراقبہ"
            ),
            relatedVideoTitles = listOf("خانقاہ قادریہ مجیدیہ کا تاریخی پس منظر و فیوضاتِ مجیدیہ"),
            references = listOf("تذکرۂ خانوادۂ قادریہ", "انوارِ بدایوں")
        ),
        ScholarProfile(
            id = "shah_abdul_qadir",
            nameUrdu = "حضرت شاہ عبد القادر قادری بدایونی ؒ",
            nameHindi = "हज़रत शाह अब्दुल क़ादिर क़ादरी बदायूंनी ؒ",
            nameEnglish = "Hazrat Shah Abdul Qadir Qadri Badayuni ؒ",
            nameHinglish = "Hazrat Shah Abdul Qadir Qadri Badayuni ؒ",
            titleUrdu = "محبوب الرسول، رئیس المتکلمین، شمس العلماء",
            titleHindi = "महबूब-उर-रसूल, रईस-उल-मुतकल्लिमीन",
            titleEnglish = "Mahboob-ur-Rasool, Chief of Theologians",
            biographyUrdu = "آپ علم و فضل اور عشقِ رسول ﷺ کا کوہِ گراں تھے۔ آپ نے آل انڈیا سنی کانفرنس اور تحریکِ آزادی میں برصغیر کے مسلمانوں کی رہنمائی فرمائی۔ آپ کی تقاریر اور تحریروں نے امتِ مسلمہ میں فکری بیداری اور عشقِ مصطفیٰ ﷺ کی شمع روشن کی۔",
            biographyHindi = "आप ज्ञान और इश्क़-ए-रसूल ﷺ के प्रतीक थे। आपने सुन्नी कांफ्रेंस और स्वतंत्रता आंदोलन में मुसलमानों का मार्गदर्शन किया।",
            biographyEnglish = "Hazrat Shah Abdul Qadir Badayuni was renowned for his profound scholarship and intense love for the Holy Prophet ﷺ. He guided the community during critical historical turning points.",
            biographyHinglish = "Aap ilm-o-fazal aur Ishq-e-Rasool ﷺ ke azeem paikar the.",
            eraOrDates = "1253ھ تا 1319ھ (1837ء تا 1901ء)",
            roleUrdu = "سجادہ نشین و جلیل القدر مصنف و متکلم",
            roleEnglish = "Sajjadah Nashin, Author and Theologian",
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
            id = "shah_abdul_muqtadir",
            nameUrdu = "حضرت شاہ عبد المقتدر قادری بدایونی ؒ",
            nameHindi = "हज़रत शाह अब्दुल मुक़्तदिर क़ादरी बदायूंनी ؒ",
            nameEnglish = "Hazrat Shah Abdul Muqtadir Qadri Badayuni ؒ",
            nameHinglish = "Hazrat Shah Abdul Muqtadir Qadri Badayuni ؒ",
            titleUrdu = "رئیس الاتقیاء، زاہدِ عصر، مفسرِ قرآن",
            titleHindi = "रईस-उल-अतक़िया, ज़ाहिद-ए-असर",
            titleEnglish = "Raees-ul-Atqiya, Eminent Ascetic & Scholar",
            biographyUrdu = "آپ زہد و تقویٰ اور علمِ تفسیر و حدیث میں ممتاز مقام رکھتے تھے۔ آپ کی صحبت میں رہنے والے قلبی طہارت اور شریعت پر استقامت کی دولت پاتے تھے۔ آپ نے خانقاہ کی دعوتی و تعلیمی سرگرمیوں کو جدید تقاضوں سے ہم آہنگ فرمایا۔",
            biographyHindi = "आप ज़ुहद व तक़वा और तफ़्सीर व हदीस में मुम्ताज़ मक़ाम रखते थे।",
            biographyEnglish = "Known for exceptional piety and deep contemplation of Quranic sciences. He steered the Khanqah's educational services.",
            biographyHinglish = "Aap zuhd-o-taqwa aur ilm-e-tafseer mein azeem martaba rakhte the.",
            eraOrDates = "1284ھ تا 1354ھ (1867ء تا 1935ء)",
            roleUrdu = "سجادہ نشین و شیخ الحدیث",
            roleEnglish = "Sajjadah Nashin & Teacher of Hadith",
            spiritualLineage = "سلسلہ عالیہ قادریہ مجیدیہ",
            scholarlyServicesUrdu = "تدریسِ حدیث و تفسیر، تصنیف و تالیف، طلبہ کی کفالت۔",
            scholarlyServicesEnglish = "Hadith instruction, spiritual mentorship, community welfare.",
            booksAuthored = listOf(
                "انوار الاقتدار فی احکام الابرار",
                "شرح اورادِ قادریہ",
                "حلیۃ الابرار"
            ),
            references = listOf("تذکرۂ خانوادۂ قادریہ بدایوں شریف")
        ),
        ScholarProfile(
            id = "maulana_asidul_haq",
            nameUrdu = "حضرت مولانا اسید الحق قادری بدایونی ؒ",
            nameHindi = "हज़रत मौलाना असीदुल हक़ क़ादरी बदायूंनी ؒ",
            nameEnglish = "Hazrat Maulana Asid-ul-Haq Qadri Badayuni ؒ",
            nameHinglish = "Hazrat Maulana Asid-ul-Haq Qadri Badayuni ؒ",
            titleUrdu = "شہیدِ اسلام، محققِ عصر، نباضِ قوم",
            titleHindi = "शहीद-ए-इस्लाम, मुहाक़्क़िक़-ए-असर",
            titleEnglish = "Shaheed-e-Islam, Eminent Modern Researcher",
            biographyUrdu = "آپ موجودہ دور کے مایہ ناز محقق، مصنف اور مفکرِ اسلام تھے۔ آپ نے خانقاہ قادریہ کے تاریخی مخطوطات اور علمی سرمائے کو جدید تحقیق کے ساتھ شایع کرایا۔ آپ نے درجنوں کتب تحریر کیں اور بین الاقوامی سطح پر سیمینارز میں شرکت فرمائی۔",
            biographyHindi = "आप आधुनिक दौर के प्रसिद्ध शोधकर्ता, लेखक और इस्लामी चिंतक थे। आपने बदायूं की समृद्ध विरासत को संजोया।",
            biographyEnglish = "A modern researcher and prolific author who cataloged, edited, and published numerous historical manuscripts of Budaun Shareef.",
            biographyHinglish = "Aap modern daur ke azeem muhaqqiq aur musannif the jinhone Budaun ke ilmi sarmaye ko aam kiya.",
            eraOrDates = "1385ھ تا 1435ھ (1965ء تا 2014ء)",
            roleUrdu = "سابق سجادہ نشین، مدیر و محقق",
            roleEnglish = "Former Sajjadah Nashin, Chief Researcher",
            spiritualLineage = "سلسلہ عالیہ قادریہ مجیدیہ",
            scholarlyServicesUrdu = "مکتبہ قادریہ کی تجدید، تذکرہ خانوادہ قادریہ کی اشاعت، عالمی کانفرنسز۔",
            scholarlyServicesEnglish = "Revival of Maktaba Qadria, publication of historical biographical compendiums.",
            booksAuthored = listOf(
                "تذکرۂ خانوادۂ قادریہ بدایوں شریف",
                "تاج الفحول حیات اور کارنامے",
                "خانقاہ قادریہ تاریخ کے آئینے میں",
                "مقالاتِ اسید"
            ),
            relatedVideoTitles = listOf("سیمینار یادِ اسید الحق قادری ؒ بدایوں شریف"),
            references = listOf("مجلہ جامِ نور", "یادگارِ اسید")
        ),
        ScholarProfile(
            id = "sajjadah_present",
            nameUrdu = "حضرت مولانا شاہ محمد قدوس قادری مدظلہ العالی",
            nameHindi = "हज़रत मौलाना शाह मोहम्मद क़ुद्दूस क़ादरी",
            nameEnglish = "Hazrat Maulana Shah Mohammad Quddoos Qadri",
            nameHinglish = "Hazrat Maulana Shah Mohammad Quddoos Qadri",
            titleUrdu = "سجادہ نشین خانقاہ قادریہ مجیدیہ بدایوں شریف",
            titleHindi = "सज्जादा नशीन ख़ानक़ाह-ए-क़ादरिया मजीदिया",
            titleEnglish = "Current Sajjadah Nashin, Khanqah Qadriah Budaun",
            biographyUrdu = "آپ موجودہ دور میں خانقاہ قادریہ مجیدیہ بدایوں شریف کے باوقار سجادہ نشین اور سرپرستِ اعلیٰ ہیں۔ آپ کی سرپرستی میں خانقاہ کے تمام مذہبی، روحانی، تعلیمی اور رفاہی امور خوش اسلوبی سے انجام پا رہے ہیں۔ سالانہ عرسِ قادری اور دیگر تقریبات آپ ہی کی زیرِ نگرانی منعقد ہوتی ہیں۔",
            biographyHindi = "आप वर्तमान में ख़ानक़ाह-ए-क़ादरिया मजीदिया बदायूं शरीफ़ के सम्मानित सज्जादा नशीन हैं।",
            biographyEnglish = "The current venerable Sajjadah Nashin and spiritual patron of Khanqah Qadriah Majeediah Budaun Shareef.",
            biographyHinglish = "Aap Maujooda daur mein Khanqah Qadriah Majeediah Budaun ke sarparast-e-aala hain.",
            eraOrDates = "معاصر (حفظہ اللہ ورعاہ)",
            roleUrdu = "موجودہ سجادہ نشین و سرپرست",
            roleEnglish = "Current Sajjadah Nashin & Chief Patron",
            spiritualLineage = "سلسلہ عالیہ قادریہ مجیدیہ بدایوں شریف",
            scholarlyServicesUrdu = "خانقاہ کے تمام تربیتی شعبہ جات کی سرپرستی، عرسِ قادری کا انتظام، بین الاقوامی زائرین کی رہنمائی۔",
            scholarlyServicesEnglish = "Patronage of spiritual and social activities, supervision of Urs-e-Qadri Mubarak.",
            booksAuthored = listOf(
                "خطباتِ سجادہ نشین",
                "ہدایات برائے زائرین و سالکین"
            ),
            relatedVideoTitles = listOf("پیغامِ سجادہ نشین خانقاہ قادریہ بدایوں شریف"),
            references = listOf("آفیشل ریکارڈز خانقاہ قادریہ بدایوں شریف (qadri.in)")
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

    // Curated Video Gallery
    val VIDEO_GALLERY: List<KhanqahVideoItem> = listOf(
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
