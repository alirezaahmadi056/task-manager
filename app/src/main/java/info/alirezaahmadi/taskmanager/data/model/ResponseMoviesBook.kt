package info.alirezaahmadi.taskmanager.data.model

data class ResponseMoviesBook(
    val name: String,
    val image: String,
    val description: String,
    val author: String,
    val star: Double
){
    companion object{ val fakeMoviesBooks = listOf(
            ResponseMoviesBook(
                name = "کتاب سکوت",
                image = "https://example.com/image1.jpg",
                description = "رمانی هیجان‌انگیز درباره یک جزیره مرموز و اسراری که در دل آن نهفته است.",
                author = "جان دو",
                star = 4.5
            ),
            ResponseMoviesBook(
                name = "دنیای گمشده",
                image = "https://example.com/image2.jpg",
                description = "سفری پرهیجان به سرزمین‌های ناشناخته و تاریخ فراموش‌شده.",
                author = "جین اسمیت",
                star = 4.8
            ),
            ResponseMoviesBook(
                name = "انعکاس‌های گذشته",
                image = "https://example.com/image3.jpg",
                description = "درامی تاریخی که داستان‌های قدیمی را به زندگی بازمی‌گرداند.",
                author = "امیلی جانسون",
                star = 4.2
            )
        ) }
}

