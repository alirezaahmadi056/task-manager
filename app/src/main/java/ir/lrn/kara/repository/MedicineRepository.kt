package ir.lrn.kara.repository

import ir.lrn.kara.data.db.medicine.MedicineDao
import ir.lrn.kara.data.db.medicine.MedicineItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicineRepository @Inject constructor(
    private val dao: MedicineDao
) {
    suspend fun upsertMedicineItem(medicineItem: MedicineItem) { dao.upsertMedicineItem(medicineItem) }
    fun getMedicineById(id: Int): Flow<MedicineItem?> = dao.getMedicineById(id)
    fun getAllMedicine(): Flow<List<MedicineItem>> = dao.getAllMedicine()
    suspend fun deleteMedicineById(id: Int) { dao.deleteMedicineById(id) }

}