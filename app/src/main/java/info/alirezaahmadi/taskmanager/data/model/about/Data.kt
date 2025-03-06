package info.alirezaahmadi.taskmanager.data.model.about

data class Data(
    val avatar: String="",
    val blog_count: Int=0,
    val blogs: List<Blog> = emptyList(),
    val course_count: Int=0,
    val courses: List<Course> = emptyList(),
    val description: String="",
    val first_name: String="",
    val last_name: String="",
    val rate: Double=0.0,
    val role: List<String> = emptyList(),
    val student_count: Int =0,
    val total_teach_duration: Int =0
)