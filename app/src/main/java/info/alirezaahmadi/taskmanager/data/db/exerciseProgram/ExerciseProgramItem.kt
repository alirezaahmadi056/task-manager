package info.alirezaahmadi.taskmanager.data.db.exerciseProgram

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseProgramItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val name: String,
    val imageUri:String,
    val videoUri:String,
    val description: String,
    val deyWeek:List<String>,
    val setNumber:Int,
    val repetitionSetNumber:Int,
    val disability:Boolean,
    val dropdown:Boolean,
    val time :Int,
){
    companion object{
        val fakeItem =ExerciseProgramItem(
            name = "جلو بازو با دمبل",
            time = 0,
            description = "",
            imageUri = "",
            dropdown = false,
            videoUri = "",
            deyWeek = emptyList(),
            setNumber = 3,
            repetitionSetNumber = 15,
            disability = false
        )
    }
}