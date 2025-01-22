package info.alirezaahmadi.taskmanager.data.db.goals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoalsItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val imageUri:String,
    val description: String,
    val timeFrame: String=GoalsTimeFrame.SHORT.name,
    val isCompleted: Boolean,
    val data: String
){
    companion object {
        val fakeList = listOf(
            GoalsItem(
                id = 1,
                title = "یادگیری زبان برنامه‌نویسی کاتلین",
                imageUri = "content://fake/image/kotlin",
                description = "یک دوره جامع آموزش کاتلین را به پایان برسانید",
                timeFrame = GoalsTimeFrame.SHORT.name,
                isCompleted = false,
                data = "1403-11-01"
            ),
            GoalsItem(
                id = 2,
                title = "شروع ورزش",
                imageUri = "content://fake/image/gym",
                description = "به باشگاه محلی بروید و ورزش را آغاز کنید",
                timeFrame = GoalsTimeFrame.MEDIUM.name,
                isCompleted = false,
                data = "1403-12-15"
            ),
            GoalsItem(
                id = 3,
                title = "نوشتن یک مقاله",
                imageUri = "content://fake/image/blog",
                description = "یک مقاله فنی در مورد برنامه‌نویسی در مدیوم منتشر کنید",
                timeFrame = GoalsTimeFrame.LONG.name,
                isCompleted = true,
                data = "1403-10-01"
            )
        )
    }
}

enum class GoalsTimeFrame(val perName: String) {
    SHORT("کوتاه مدت"),
    MEDIUM("میان مدت"),
    LONG("بلند مدت")
}