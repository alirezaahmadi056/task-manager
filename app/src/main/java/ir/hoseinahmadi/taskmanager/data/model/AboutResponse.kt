package ir.hoseinahmadi.taskmanager.data.model

data class AboutResponse(
    val data: Data=Data(),
    val message: String="",
    val success: Int=0
)