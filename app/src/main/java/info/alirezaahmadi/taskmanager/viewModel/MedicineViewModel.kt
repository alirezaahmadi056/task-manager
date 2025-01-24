package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItem
import info.alirezaahmadi.taskmanager.data.db.medicine.MedicineItem
import info.alirezaahmadi.taskmanager.repository.MedicineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {
    fun upsertMedicineItem(medicineItem: MedicineItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertMedicineItem(medicineItem) }
    }

    fun getMedicineById(id: Int): Flow<MedicineItem?> = repository.getMedicineById(id)
    fun getAllMedicine(): Flow<List<MedicineItem>> = repository.getAllMedicine()
    fun deleteMedicineById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMedicineById(id)
        }
    }


}