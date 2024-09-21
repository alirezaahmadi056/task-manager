package info.alirezaahmadi.taskmanager.data.model.about

data class Course(
    val ID: Int,
    val duration: Int,
    val is_gold: Boolean,
    val price: Int,
    val url:String,
    val rate: Rate,
    val sale_price: Int,
    val thumbnail: String,
    val title: String
)