package info.alirezaahmadi.taskmanager.data.db.curriculum

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CurriculumDao {
    @Upsert
    suspend fun upsertCurriculum(curriculumItem: CurriculumItem)

    @Query("SELECT * FROM CurriculumItem")
    fun getAllCurriculum(): Flow<List<CurriculumItem>>

    @Query("DELETE FROM CurriculumItem WHERE id = :id")
    suspend fun deleteCurriculum(id: Int)

    @Query("SELECT * FROM CurriculumItem WHERE id = :id")
    fun getCurriculum(id: Int): Flow<CurriculumItem?>
}