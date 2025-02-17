package info.alirezaahmadi.taskmanager.data.model

import info.alirezaahmadi.taskmanager.R

data class Book(
    val id: Int,
    val image: String,
    val title: String,
    val author: String,
    val summary: String
) {
    companion object {
        val books = listOf(
            Book(
                1,
                "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b1.png",
                "قدرت عادت",
                "چارلز دوهیگ",
                "این کتاب بررسی می‌کند که چگونه عادت‌ها شکل می‌گیرند، چگونه بر زندگی ما تأثیر می‌گذارند و چگونه می‌توانیم عادت‌های بد را با عادت‌های مفید جایگزین کنیم."
            ),
            Book(
                2,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b2.png",
                "اثر مرکب",
                "دارن هاردی",
                "کتاب توضیح می‌دهد که موفقیت نتیجه‌ی اقدامات کوچک و مداوم در طول زمان است."
            ),
            Book(
                3,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b3.png",
                "بیدار کردن غول درون",
                "آنتونی رابینز",
                "این کتاب به شما می‌آموزد که چگونه کنترل زندگی خود را به دست بگیرید و به موفقیت و رضایت شخصی برسید."
            ),
            Book(
                4,
                "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b4.png",
                "از خوب به عالی",
                "جیم کالینز",
                "این کتاب نشان می‌دهد که چگونه شرکت‌های معمولی به شرکت‌های خارق‌العاده تبدیل شده‌اند."
            ),
            Book(
                5,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b5.png",
                "قوانین موفقیت",
                "ناپلئون هیل",
                "این کتاب اصول بنیادین موفقیت را بر اساس مصاحبه با صدها فرد موفق ارائه می‌دهد."
            ),
            Book(
                6,
            "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b6.png",
                "تخت‌خوابت را مرتب کن",
                "ویلیام مک‌ریون",
                "این کتاب شامل درس‌هایی ساده اما عمیق درباره‌ی نظم، پشتکار و قدرت تغییرات کوچک است."
            ),
            Book(
                7,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b7.png",
                "راز",
                "روندا برن",
                "این کتاب توضیح می‌دهد که چگونه افکار و احساسات ما می‌توانند واقعیت زندگی‌مان را شکل دهند."
            ),
            Book(
                8,
              "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b8.png",
                "معجزه سپاسگزاری",
                "راندا برن",
                "این کتاب بر اهمیت شکرگزاری در تغییر زندگی تأکید دارد."
            ),
            Book(
                9,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b9.png",
                "عادت‌های اتمی",
                "جیمز کلیر",
                "این کتاب نشان می‌دهد که چگونه تغییرات کوچک در عادت‌های روزانه می‌توانند تأثیرات بزرگی بر زندگی‌تان داشته باشند."
            ),
            Book(
                10,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/b10.png",
                "باشگاه ۵ صبحی‌ها",
                "رابین شارما",
                "این کتاب اهمیت بیدار شدن زودهنگام و ایجاد یک روتین صبحگاهی سازنده را بررسی می‌کند."
            )
        )
    }
}

data class Movie(
    val id: Int,
    val image:String,
    val title: String,
    val director: String,
    val actors: List<String>,
    val summary: String
) {
    companion object {
        val movies = listOf(
            Movie(
                1,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/m1.png",
                "The Pursuit of Happyness",
                "گابریله موچینو",
                listOf("ویل اسمیت", "جیدن اسمیت"),
                "این فیلم بر اساس داستان واقعی کریس گاردنر ساخته شده و روایتگر تلاش‌های بی‌وقفه‌ی یک پدر برای تأمین آینده‌ی پسرش است."
            ),
            Movie(
                2,
                "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/m2.png",
                "Rocky",
                "جان جی. آویلدسن",
                listOf("سیلوستر استالونه", "تالیا شایر"),
                "این فیلم یکی از نمادهای تلاش و پشتکار در سینما است. راکی بالبوا فرصتی پیدا می‌کند تا با قهرمان جهان مبارزه کند."
            ),
            Movie(
                3,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/m3.png",
                "The Social Network",
                "دیوید فینچر",
                listOf("جسی آیزنبرگ", "اندرو گارفیلد", "جاستین تیمبرلیک"),
                "این فیلم داستان مارک زاکربرگ و چگونگی خلق فیس‌بوک را روایت می‌کند."
            ),
            Movie(
                4,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/m4.png",
                "Forrest Gump",
                "رابرت زمه‌کیس",
                listOf("تام هنکس", "رابین رایت"),
                "این فیلم درباره‌ی مردی ساده و خوش‌قلب است که با وجود محدودیت‌های ذهنی، به موفقیت‌های بزرگی در زندگی دست پیدا می‌کند."
            ),
            Movie(
                5,
               "https://dl.daneshjooyar.com/mvie/Ahmadi-Alireza/Files/kara/m5.png",
                "Whiplash",
                "دیمین شزل",
                listOf("مایلز تلر", "جی. کی. سیمونز"),
                "این فیلم داستان اندرو نیمن، یک نوازنده‌ی درام، را روایت می‌کند که برای رسیدن به موفقیت تحت فشارهای شدید قرار می‌گیرد."
            )
        )
    }
}

enum class BookMovieType {
    BOOK, MOVIE
}