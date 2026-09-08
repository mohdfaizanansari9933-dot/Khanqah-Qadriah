package com.example.data.repository

import com.example.data.model.Book
import com.example.data.model.BookCategory
import com.example.data.model.BookChapter

object BooksRepository {

    val BOOKS_LIST: List<Book> = listOf(
        Book(
            id = "ihqaq_ul_haq",
            titleUrdu = "احقاق الحق المبين في إبطال أصول المبتدعين",
            titleHindi = "अहक़ाक़ुल हक़ अल-मुबीन",
            titleEnglish = "Ihqaq-ul-Haq al-Mubeen",
            titleHinglish = "Ihqaq-ul-Haq al-Mubeen",
            authorUrdu = "حضرت شاہ فضلِ رسول قادری بدایونی ؒ",
            authorHindi = "हज़रत शाह फ़ज़्ल-ए-रसूल क़ादरी बदायूंनी ؒ",
            authorEnglish = "Hazrat Shah Fazl-e-Rasool Qadri Badayuni ؒ",
            authorHinglish = "Hazrat Shah Fazl-e-Rasool Qadri Badayuni ؒ",
            authorId = "shah_fazle_rasool",
            category = BookCategory.KHANQAH_QADRIAH,
            descriptionUrdu = "عقائدِ اہل سنت و جماعت کا عظیم الشان علمی دفاع اور بدعات و گمراہ کن نظریات کا مدلل رد۔ خانوادۂ قادریہ بدایوں شریف کی شاہکار تصنیف۔",
            descriptionHindi = "अहले सुन्नत व जमात के अक़ीदे का ऐतिहासिक प्रमाणिक ग्रंथ, बदायूं शरीफ़ की महान कृति।",
            descriptionEnglish = "Monumental masterpiece authored by Shah Fazl-e-Rasool Badayuni in defense of traditional Sunni orthodoxy.",
            publicationInfo = "مکتبہ قادریہ بدایوں شریف / qadri.in",
            chapters = listOf(
                BookChapter(
                    "ih_ch1", 1, "مقدمہ در اثباتِ سنت و لزومِ جماعت",
                    "सत्य सुन्नत का महत्व",
                    "Introduction to Adherence to Sunnah",
                    """اہل سنت و جماعت کے اساسی اصول:
قرآن مجید اور سنتِ نبویہ ﷺ کی روشنی میں سلفِ صالحین، صحابہ کرام اور ائمہ اربعہ کا فہم ہی میزانِ حق ہے۔
ارشادِ باری تعالیٰ ہے: 'وَاتَّبِعْ سَبِيلَ مَنْ أَنَابَ إِلَيَّ'۔
حضور نبی کریم ﷺ کا ارشاد گرامی ہے: 'عَلَيْكُمْ بِالسَّوَادِ الْأَعْظَمِ' (بڑے گروہ یعنی جمہور امت کو لازم پکڑو)۔
بدایوں شریف کے اکابر نے ہمیشہ امت کو تفرقہ بازی سے بچا کر سوادِ اعظم کے ساتھ وابستہ رہنے کی تلقین فرمائی۔"""
                ),
                BookChapter(
                    "ih_ch2", 2, "ردِ بدعات اور مقامِ مصطفیٰ ﷺ کا تحفظ",
                    "बिदअत का खंडन व मुक़ाम-ए-मुस्तफ़ा ﷺ",
                    "Refutation of Innovations & Protection of Prophetic Dignity",
                    """شانِ رسالت مآب ﷺ میں ذرہ برابر تنقیص یا گستاخی کفر و ضلالت ہے۔
اہل سنت کے نزدیک حضور اقدس ﷺ اللہ کے محبوب ترین بندے، افضل الرسل، نورِ مجسم اور ہر خیر و برکت کا سرچشمہ ہیں۔
اولیاء کرام اور مزاراتِ اولیاء کے وسیلے سے دعاؤں کا جواز اور اس کے مسنون احکام۔"""
                )
            ),
            totalPagesEstimate = 280,
            officialWebsiteUrl = "https://www.qadri.in/books"
        ),
        Book(
            id = "irshad_ul_majeed",
            titleUrdu = "ارشاد المجيد في سلوك المريد",
            titleHindi = "इरशादुल मजीद फ़ी सुलूकिल मुरीद",
            titleEnglish = "Irshad-ul-Majeed fi Sulook-il-Mureed",
            titleHinglish = "Irshad-ul-Majeed fi Sulook-il-Mureed",
            authorUrdu = "حضرت شاہ عبد المجید قادری بدایونی ؒ",
            authorHindi = "हज़रत शाह अब्दुल मजीद क़ादरी बदायूंनी ؒ",
            authorEnglish = "Hazrat Shah Abdul Majeed Qadri Badayuni ؒ",
            authorHinglish = "Hazrat Shah Abdul Majeed Qadri Badayuni ؒ",
            authorId = "shah_abdul_majeed",
            category = BookCategory.TASAWWUF,
            descriptionUrdu = "سلسلہ عالیہ قادریہ مجیدیہ بدایوں شریف میں طالبانِ حق اور مریدین کے لیے تزکیۂ نفس، اخلاقِ حسنہ اور اوراد کے آداب پر مشتمل روحانی گلدستہ۔",
            descriptionHindi = "मुरीदों और सालिकों के लिए आत्मशुद्धि, शिष्टाचार और ज़िक्र का मार्गदर्शन।",
            descriptionEnglish = "Spiritual manual for seekers in Silsila Qadria Majeediah regarding soul purification and ethics.",
            publicationInfo = "مکتبہ قادریہ مجیدیہ بدایوں شریف / qadri.in",
            chapters = listOf(
                BookChapter(
                    "im_ch1", 1, "آدابِ بیعت و ارادت در سلسلہ قادریہ",
                    "बैअत व मुरीदी के अदब",
                    "Etiquette of Allegiance and Mentorship",
                    """سلسلہ عالیہ قادریہ میں بیعت کا بنیادی مقصد شریعتِ مطہرہ پر استقامت اور قلب کو گناہوں کی آلائشوں سے پاک کرنا ہے۔
سالک پر لازم ہے کہ وہ:
۱. پنجگانہ نماز کی باجماعت پابندی کرے۔
۲. رزقِ حلال کی طلب میں حریص رہے اور لقمۂ حرام سے پرہیز کرے۔
۳. اپنے شیخِ طریقت کے بتائے ہوئے اوراد و تسبیحات کو بلا ناغہ ادا کرے۔"""
                ),
                BookChapter(
                    "im_ch2", 2, "طہارتِ باطن اور ذکرِ الٰہی کی برکات",
                    "आंतरिक पवित्रता व ज़िक्र की बरकतें",
                    "Inner Purification & Blessings of Divine Remembrance",
                    """دل کو کینہ، حسد، ریاکاری، تکبر اور حرص سے پاک رکھنا طریقت کا پہلا زینہ ہے۔
ارشادِ غوثِ پاک رضی اللہ عنہ ہے: 'پہلے اپنے باطن کو درست کرو، ظاہر خود بخود درست ہو جائے گا'۔
ذکرِ الٰہی سے دلوں کا زنگ اترتا ہے اور قربِ الٰہی کی راہیں کھلتی ہیں۔"""
                )
            ),
            totalPagesEstimate = 160,
            officialWebsiteUrl = "https://www.qadri.in/books"
        ),
        Book(
            id = "tazkira_khanwada_qadria",
            titleUrdu = "تذکرۂ خانوادۂ قادریہ بدایوں شریف",
            titleHindi = "तज़किरा-ए-ख़ानवादा-ए-क़ादरिया बदायूं शरीफ़",
            titleEnglish = "Tazkira-e-Khanwada-e-Qadria Budaun Shareef",
            titleHinglish = "Tazkira-e-Khanwada-e-Qadria Budaun Shareef",
            authorUrdu = "حضرت مولانا اسید الحق قادری بدایونی ؒ",
            authorHindi = "हज़रत मौलाना असीदुल हक़ क़ादरी बदायूंनी ؒ",
            authorEnglish = "Hazrat Maulana Asid-ul-Haq Qadri Badayuni ؒ",
            authorHinglish = "Hazrat Maulana Asid-ul-Haq Qadri Badayuni ؒ",
            authorId = "maulana_asidul_haq",
            category = BookCategory.ISLAMIC_HISTORY,
            descriptionUrdu = "بدایوں شریف کے قادری خاندان کی مفصل تاریخ، سوانحِ اکابر، قلمی یادگاریں، علمی و ملی خدمات کا دستاویزی اور تحقیقی جائزہ۔",
            descriptionHindi = "बदायूं के क़ादरी घराने का इतिहास, संतों की जीवनियां और शैक्षिक योगदान।",
            descriptionEnglish = "Authoritative historical and biographical chronicling of Khanqah Qadriah Budaun Shareef and its scholars.",
            publicationInfo = "تاج الفحول اکیڈمی، بدایوں شریف / qadri.in",
            chapters = listOf(
                BookChapter(
                    "tk_ch1", 1, "بدایوں شریف کی علمی و روحانی تاریخ",
                    "बदायूं शरीफ़ का इतिहास",
                    "Spiritual and Intellectual History of Budaun",
                    """بدایوں شریف ہندوستان کے ان قدیم تاریخی شہروں میں شمار ہوتا ہے جسے 'مدینۃ الاولیاء' کا شرف حاصل رہا ہے۔
سلطنتِ دہلی سے لے کر دورِ حاضر تک یہاں جید محدثین، فقہاء اور صوفیاء نے مسندِ علم و ارشاد کو رونق بخشی۔
خانوادۂ قادریہ نے تصوف اور فقہ کا ایسا حسین سنگم قائم کیا جس نے بدایوں کو عالمی شہرت بخشی۔"""
                ),
                BookChapter(
                    "tk_ch2", 2, "حضرت شاہ عبد القادر بدایونی ؒ محبوب الرسول",
                    "हज़रत शाह अब्दुल क़ादिर का जीवन",
                    "Life of Hazrat Shah Abdul Qadir Badayuni",
                    """آپ کی تقریر و تحریر برصغیر میں عشقِ رسالت کا روشن چراغ تھی۔
آپ نے مسلمانوں کی فکری و سیاسی رہنمائی کے لیے تاریخی آل انڈیا سنی کانفرنس کی بنیادوں میں حصہ لیا۔
آپ کی تصانیف نے بدایوں کا نام عالمِ اسلام کے کتب خانوں میں معتبر کیا۔"""
                )
            ),
            totalPagesEstimate = 350,
            officialWebsiteUrl = "https://www.qadri.in/books"
        ),
        Book(
            id = "shams_uz_zuha",
            titleUrdu = "رسالہ شمس الضحیٰ في رد منكر الضياء",
            titleHindi = "रिसाला शम्सुज़्ज़ुहा",
            titleEnglish = "Risala Shams-uz-Zuha",
            titleHinglish = "Risala Shams-uz-Zuha",
            authorUrdu = "حضرت شاہ عبد القادر قادری بدایونی ؒ",
            authorHindi = "हज़रत शाह अब्दुल क़ादिर क़ादरी बदायूंनी ؒ",
            authorEnglish = "Hazrat Shah Abdul Qadir Qadri Badayuni ؒ",
            authorHinglish = "Hazrat Shah Abdul Qadir Qadri Badayuni ؒ",
            authorId = "shah_abdul_qadir",
            category = BookCategory.AKABIR_BUDAUN,
            descriptionUrdu = "حضور نبی کریم ﷺ کے نورِ مجسم ہونے اور میلادِ مصطفیٰ ﷺ کی شرعی حیثیت پر قاطع اور مدلل برہان۔",
            descriptionHindi = "हुज़ूर ﷺ के नूरानी मक़ाम व मिलाद-उन-नबी ﷺ का प्रमाणिक बयानिया।",
            descriptionEnglish = "Scholarly treatise on the spiritual station and illumination of the Holy Prophet Muhammad ﷺ.",
            publicationInfo = "مکتبہ قادریہ، بدایوں شریف",
            chapters = listOf(
                BookChapter(
                    "sz_ch1", 1, "نورانیتِ مصطفیٰ ﷺ قرآن کی روشنی میں",
                    "क़ुरआन में नूर-ए-मुस्तफ़ा ﷺ",
                    "Prophetic Illumination in the Holy Quran",
                    """قولہ تعالیٰ: 'قَدْ جَاءَكُمْ مِنَ اللَّهِ نُورٌ وَكِتَابٌ مُبِينٌ'۔
اکابرِ مفسرین جیسے امام طبری، امام رازی اور امام بغوی نے تصریح فرمائی ہے کہ یہاں 'نور' سے مراد سیدنا محمد رسول اللہ ﷺ ہیں۔
آپ کا سایہ زمین پر نہ پڑنا اور آپ کی برکات سے تمام کائنات کا فیضیاب ہونا احادیثِ صحیحہ سے ثابت ہے۔"""
                )
            ),
            totalPagesEstimate = 110,
            officialWebsiteUrl = "https://www.qadri.in/books"
        ),
        Book(
            id = "bahar_e_shariat",
            titleUrdu = "بہارِ شریعت (احکامِ نماز و طہارت)",
            titleHindi = "बहार-ए-शरीअत (नमाज़ व तहारत)",
            titleEnglish = "Bahar-e-Shariat (Salah & Taharah)",
            titleHinglish = "Bahar-e-Shariat (Namaz & Taharat)",
            authorUrdu = "صدر الشریعہ مفتی محمد امجد علی اعظمی ؒ",
            authorHindi = "सद्रुश शरीअह मुफ़्ती अमजद अली आज़मी ؒ",
            authorEnglish = "Sadrush Shariah Mufti Amjad Ali Azmi ؒ",
            authorHinglish = "Sadrush Shariah Mufti Amjad Ali Azmi ؒ",
            authorId = "amjad_ali_azmi",
            category = BookCategory.FIQH,
            descriptionUrdu = "برصغیر پاک و ہند میں فقہ حنفی اور اہل سنت و جماعت کا سب سے مستند اور جامع ترین فتاویٰ و احکام کا مجموعہ۔",
            descriptionHindi = "हनफ़ी फ़िक़्ह और अहले सुन्नत का सबसे प्रमाणिक संकलन।",
            descriptionEnglish = "The most authentic, widely consulted encyclopedia of Hanafi Islamic jurisprudence in South Asia.",
            publicationInfo = "مکتبۃ المدینہ / عوامی دائرہ معارفِ فقہ حنفی",
            chapters = listOf(
                BookChapter(
                    "bs_ch1", 1, "وضو کے فرائض اور مسنون طریقہ",
                    "वज़ू के फ़राइज़ व तरीक़ा",
                    "Obligations and Sunnah Method of Wudu",
                    """وضو کے چار فرائض قرآنِ پاک کی نصِ قطعی سے ثابت ہیں:
۱. پیشانی کے بالوں سے لے کر ٹھوڑی کے نیچے تک اور ایک کان کی لو سے دوسرے کان کی لو تک پورا چہرہ دھونا۔
۲. دونوں ہاتھ کہنیوں سمیت دھونا۔
۳. چوتھائی سر کا مسح کرنا۔
۴. دونوں پاؤں ٹخنوں سمیت دھونا۔

وضو کی مسنون ترتیب:
تسمیہ پڑھنا، ہاتھوں کو دھونا، مسواک کرنا، تین بار کلی و غرغرہ کرنا، ناک صاف کرنا، چہرہ دھونا، ہاتھ کہنیوں تک، سر کا مسح اور پاؤں ٹخنوں سمیت دھونا۔"""
                ),
                BookChapter(
                    "bs_ch2", 2, "نماز کے ارکان اور شرائط",
                    "नमाज़ के अरकान व शर्तें",
                    "Pillars and Conditions of Salah",
                    """نماز کے لیے چھ شرائط ہیں:
۱. طہارت (بدن، کپڑے اور جگہ کا پاک ہونا)
۲. سترِ عورت
۳. استقبالِ قبلہ
۴. وقت کا ہونا
۵. نیت
۶. تکبیرِ تحریمہ

ارکان (فرائضِ نماز): قیام، قراءت، رکوع، سجود، قعدہ اخیرہ، اور خروج بصنعہ۔"""
                )
            ),
            totalPagesEstimate = 650,
            officialWebsiteUrl = "https://www.qadri.in/books"
        ),
        Book(
            id = "qasida_ghousia",
            titleUrdu = "قصیدہ غوثیہ شریف مع شرح و فضائل",
            titleHindi = "क़सीदा-ए-ग़ौसिया शरीफ़ शरह सहित",
            titleEnglish = "Qasida Ghousia Shareef with Commentary",
            titleHinglish = "Qasida Ghousia Shareef Sharah Ke Sath",
            authorUrdu = "سیدنا غوثِ اعظم شیخ عبدالقادر جیلانی ؓ",
            authorHindi = "सैयदना ग़ौस-ए-आज़म शैख़ अब्दुल क़ादिर जीलानी ؓ",
            authorEnglish = "Hazrat Ghous-ul-Azam Sheikh Abdul Qadir Jilani ؓ",
            authorHinglish = "Hazrat Ghous-ul-Azam Sheikh Abdul Qadir Jilani ؓ",
            authorId = "ghous_e_azam",
            category = BookCategory.TASAWWUF,
            descriptionUrdu = "سلسلہ عالیہ قادریہ کا نہایت متبرک اور وجد آفرین کلام جس میں حضور غوث پاک کے ولایتِ کبریٰ کے احوال و مقامات کا تذکرہ ہے۔",
            descriptionHindi = "सिलसिला-ए-क़ादरिया का पवित्र व रूहानी कलाम।",
            descriptionEnglish = "Celebrated spiritual ode by the founder of the Qadria Order, Hazrat Ghous-e-Azam Jilani.",
            publicationInfo = "سلسلہ عالیہ قادریہ رضویہ عطاریہ",
            chapters = listOf(
                BookChapter(
                    "qg_ch1", 1, "قصیدہ غوثیہ کے ابتدائی ابیات و معانی",
                    "प्रारंभिक शेर व अर्थ",
                    "Opening Couplets and Spiritual Meaning",
                    """سَقَانِي الْحُبُّ كَاسَاتِ الْوِصَالِ
فَقُلْتُ لِخَمْرَتِي نَحْوِي تَعَالِي

ترجمہ: مجھے محبتِ الٰہی کے جام ہائے وصال پلائے گئے، تو میں نے شرابِ معرفت سے کہا کہ میری طرف چلی آ۔

سَعَتْ وَمَشَتْ لِنَحْوِي فِي كُؤُوسٍ
فَهِمْتُ بِسَكْرَتِي بَيْنَ الْمَوَالِي"""
                )
            ),
            totalPagesEstimate = 90,
            officialWebsiteUrl = "https://www.qadri.in/books"
        ),
        Book(
            id = "al_wazeefa_tul_kareema",
            titleUrdu = "الوظیفۃ الکریمہ (قادری اوراد و وظائف)",
            titleHindi = "अल-वज़ीफ़तुल करीमा",
            titleEnglish = "Al-Wazeefa-tul-Kareema (Qadri Litanies)",
            titleHinglish = "Al-Wazeefa-tul-Kareema",
            authorUrdu = "اعلیٰ حضرت امام احمد رضا خان قادری بریلوی ؒ",
            authorHindi = "आला हज़रत इमाम अहमद रज़ा ख़ान ؒ",
            authorEnglish = "Ala Hazrat Imam Ahmad Raza Khan ؒ",
            authorHinglish = "Ala Hazrat Imam Ahmad Raza Khan ؒ",
            authorId = "ala_hazrat",
            category = BookCategory.FAZAIL,
            descriptionUrdu = "صبح و شام کے مجرب اوراد، درودِ تاج، درودِ رضویہ، استغفار اور سلسلہ قادریہ کے معمولات۔",
            descriptionHindi = "सुबह व शाम के मुजर्रब वज़ाइफ़ और दुरूद शरीफ़।",
            descriptionEnglish = "Daily spiritual litanies, invocations, and litanies prescribed in the Qadria tradition.",
            publicationInfo = "مکتبۃ الرضا / qadri.in",
            chapters = listOf(
                BookChapter(
                    "wk_ch1", 1, "صبح و شام کے معمولات و تسبیحات",
                    "दैनिक वज़ाइफ़",
                    "Morning and Evening Litanies",
                    """بعد نمازِ فجر:
۱. سورۃ یٰسٓ شریف کی تلاوت۔
۲. کلمہ طیبہ ۱۰۰ بار۔
۳. درودِ رضویہ ۱۰۰ بار۔
۴. استغفار ۱۰۰ بار۔
۵. یا قادر یا قیوم ۱۱۱ بار برائے وسعتِ رزق و برکت۔"""
                )
            ),
            totalPagesEstimate = 80,
            officialWebsiteUrl = "https://www.qadri.in/books"
        ),
        Book(
            id = "seerat_un_nabi",
            titleUrdu = "سیرتِ رسولِ عربی ﷺ",
            titleHindi = "सीरत-ए-रसूल-ए-अरबी ﷺ",
            titleEnglish = "Seerat-un-Nabi ﷺ",
            titleHinglish = "Seerat-un-Nabi ﷺ",
            authorUrdu = "علامہ نور بخش توکلی ؒ",
            authorHindi = "अल्लामा नूर बख़्श तवक़्क़ुली ؒ",
            authorEnglish = "Allama Noor Bakhsh Tawakali ؒ",
            authorHinglish = "Allama Noor Bakhsh Tawakali ؒ",
            authorId = "tawakali",
            category = BookCategory.SEERAT,
            descriptionUrdu = "سرورِ کونین ﷺ کی ولادتِ باسعادت، اخلاقِ حسنہ، غزوات، معجزات اور پاکیزہ زندگی کا بصیرت افروز بیان۔",
            descriptionHindi = "हुज़ूर अकरम ﷺ की पाकीज़ा ज़िंदगी, अख़लाक़ व मोजिज़ात का बयानिया।",
            descriptionEnglish = "Comprehensive, authentic biography of the Prophet of Islam Muhammad ﷺ.",
            publicationInfo = "مکتبہ نبویہ",
            chapters = listOf(
                BookChapter(
                    "sn_ch1", 1, "ولادتِ باسعادت اور صبحِ بہاراں",
                    "पवित्र विलादत",
                    "Blessed Birth of the Holy Prophet ﷺ",
                    """۱۲ ربیع الاول شریف بروز پیر مکہ مکرمہ میں سید الانبیاء حضرت محمد مصطفیٰ ﷺ کی ولادت ہوئی۔
سیدہ آمنہ رضی اللہ عنہا فرماتی ہیں کہ ولادت کے وقت ایک ایسا نور چمکا جس سے مشرق و مغرب جگمگا اٹھے۔"""
                )
            ),
            totalPagesEstimate = 420,
            officialWebsiteUrl = "https://www.qadri.in/books"
        )
    )

    fun getBooksByCategory(category: BookCategory): List<Book> {
        if (category == BookCategory.ALL) return BOOKS_LIST
        return BOOKS_LIST.filter { it.category == category }
    }

    fun getBookById(id: String): Book? {
        return BOOKS_LIST.firstOrNull { it.id == id }
    }

    fun getBooksByAuthorId(authorId: String): List<Book> {
        return BOOKS_LIST.filter { it.authorId.equals(authorId, ignoreCase = true) }
    }

    fun searchBooks(query: String): List<Book> {
        if (query.isBlank()) return BOOKS_LIST
        val cleanQuery = query.trim().lowercase()
        return BOOKS_LIST.filter { book ->
            book.titleUrdu.contains(cleanQuery, ignoreCase = true) ||
            book.titleHindi.contains(cleanQuery, ignoreCase = true) ||
            book.titleEnglish.contains(cleanQuery, ignoreCase = true) ||
            book.titleHinglish.contains(cleanQuery, ignoreCase = true) ||
            book.authorUrdu.contains(cleanQuery, ignoreCase = true) ||
            book.authorHindi.contains(cleanQuery, ignoreCase = true) ||
            book.authorEnglish.contains(cleanQuery, ignoreCase = true) ||
            book.authorHinglish.contains(cleanQuery, ignoreCase = true) ||
            book.descriptionUrdu.contains(cleanQuery, ignoreCase = true) ||
            book.category.urduName.contains(cleanQuery, ignoreCase = true) ||
            book.category.englishName.contains(cleanQuery, ignoreCase = true)
        }
    }
}
