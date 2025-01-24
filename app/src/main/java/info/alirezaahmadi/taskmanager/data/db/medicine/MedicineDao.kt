package info.alirezaahmadi.taskmanager.data.db.medicine

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    @Upsert
    suspend fun upsertMedicineItem(medicineItem: MedicineItem)

    @Query("SELECT * FROM MedicineItem WHERE id = :id")
    fun getMedicineById(id: Int): Flow<MedicineItem?>

    @Query("SELECT * FROM MedicineItem")
    fun getAllMedicine(): Flow<List<MedicineItem>>

    @Query("DELETE FROM MedicineItem WHERE id = :id")
    suspend fun deleteMedicineById(id: Int)
}