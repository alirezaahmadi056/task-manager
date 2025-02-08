package info.alirezaahmadi.taskmanager.data.model

import info.alirezaahmadi.taskmanager.R

data class Book(
    val id: Int,
    val image: Int,
    val title: String,
    val author: String,
    val summary: String
) {
    companion object {
        val books = listOf(
            Book(
                1,
                R.drawable.b1,
                "قدرت عادت",
                "چارلز دوهیگ",
                "این کتاب بررسی می‌کند که چگونه عادت‌ها شکل می‌گیرند، چگونه بر زندگی ما تأثیر می‌گذارند و چگونه می‌توانیم عادت‌های بد را با عادت‌های مفید جایگزین کنیم."
            ),
            Book(
                2,
                R.drawable.b2,
                "اثر مرکب",
                "دارن هاردی",
                "کتاب توضیح می‌دهد که موفقیت نتیجه‌ی اقدامات کوچک و مداوم در طول زمان است."
            ),
            Book(
                3,
                R.drawable.b3,
                "بیدار کردن غول درون",
                "آنتونی رابینز",
                "این کتاب به شما می‌آموزد که چگونه کنترل زندگی خود را به دست بگیرید و به موفقیت و رضایت شخصی برسید."
            ),
            Book(
                4,
                R.drawable.b4,
                "از خوب به عالی",
                "جیم کالینز",
                "این کتاب نشان می‌دهد که چگونه شرکت‌های معمولی به شرکت‌های خارق‌العاده تبدیل شده‌اند."
            ),
            Book(
                5,
                R.drawable.b5,
                "قوانین موفقیت",
                "ناپلئون هیل",
                "این کتاب اصول بنیادین موفقیت را بر اساس مصاحبه با صدها فرد موفق ارائه می‌دهد."
            ),
            Book(
                6,
                R.drawable.b6,
                "تخت‌خوابت را مرتب کن",
                "ویلیام مک‌ریون",
                "این کتاب شامل درس‌هایی ساده اما عمیق درباره‌ی نظم، پشتکار و قدرت تغییرات کوچک است."
            ),
            Book(
                7,
                R.drawable.b7,
                "راز",
                "روندا برن",
                "این کتاب توضیح می‌دهد که چگونه افکار و احساسات ما می‌توانند واقعیت زندگی‌مان را شکل دهند."
            ),
            Book(
                8,
                R.drawable.b8,
                "معجزه سپاسگزاری",
                "راندا برن",
                "این کتاب بر اهمیت شکرگزاری در تغییر زندگی تأکید دارد."
            ),
            Book(
                9,
                R.drawable.b9,
                "عادت‌های اتمی",
                "جیمز کلیر",
                "این کتاب نشان می‌دهد که چگونه تغییرات کوچک در عادت‌های روزانه می‌توانند تأثیرات بزرگی بر زندگی‌تان داشته باشند."
            ),
            Book(
                10,
                R.drawable.b10,
                "باشگاه ۵ صبحی‌ها",
                "رابین شارما",
                "این کتاب اهمیت بیدار شدن زودهنگام و ایجاد یک روتین صبحگاهی سازنده را بررسی می‌کند."
            )
        )
    }
}

data class Movie(
    val id: Int,
    val image:Int,
    val title: String,
    val director: String,
    val actors: List<String>,
    val summary: String
) {
    companion object {
        val movies = listOf(
            Movie(
                1,
                R.drawable.m1,
                "The Pursuit of Happyness",
                "گابریله موچینو",
                listOf("ویل اسمیت", "جیدن اسمیت"),
                "این فیلم بر اساس داستان واقعی کریس گاردنر ساخته شده و روایتگر تلاش‌های بی‌وقفه‌ی یک پدر برای تأمین آینده‌ی پسرش است."
            ),
            Movie(
                2,
                R.drawable.m2,
                "Rocky",
                "جان جی. آویلدسن",
                listOf("سیلوستر استالونه", "تالیا شایر"),
                "این فیلم یکی از نمادهای تلاش و پشتکار در سینما است. راکی بالبوا فرصتی پیدا می‌کند تا با قهرمان جهان مبارزه کند."
            ),
            Movie(
                3,
                R.drawable.m3,
                "The Social Network",
                "دیوید فینچر",
                listOf("جسی آیزنبرگ", "اندرو گارفیلد", "جاستین تیمبرلیک"),
                "این فیلم داستان مارک زاکربرگ و چگونگی خلق فیس‌بوک را روایت می‌کند."
            ),
            Movie(
                4,
                R.drawable.m4,
                "Forrest Gump",
                "رابرت زمه‌کیس",
                listOf("تام هنکس", "رابین رایت"),
                "این فیلم درباره‌ی مردی ساده و خوش‌قلب است که با وجود محدودیت‌های ذهنی، به موفقیت‌های بزرگی در زندگی دست پیدا می‌کند."
            ),
            Movie(
                5,
                R.drawable.m5,
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