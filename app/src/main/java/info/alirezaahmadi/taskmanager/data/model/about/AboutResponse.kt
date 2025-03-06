package info.alirezaahmadi.taskmanager.data.model.about

data class AboutResponse(
    val data: Data = Data(),
    val message: String="",
    val success: Int=0
)