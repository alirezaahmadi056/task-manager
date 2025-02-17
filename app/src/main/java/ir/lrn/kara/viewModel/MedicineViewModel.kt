package ir.lrn.kara.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.lrn.kara.data.db.medicine.MedicineItem
import ir.lrn.kara.repository.MedicineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: MedicineRepository
) : ViewModel() {

    private val _allMedicineItems = MutableStateFlow<List<MedicineItem>>(emptyList())
    val allMedicineItems:StateFlow<List<MedicineItem>> =_allMedicineItems.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMedicine().collectLatest {
                _allMedicineItems.emit(it)
            }
        }
    }
    fun upsertMedicineItem(medicineItem: MedicineItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertMedicineItem(medicineItem) }
    }

    fun getMedicineById(id: Int): Flow<MedicineItem?> = repository.getMedicineById(id)
    fun deleteMedicineById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMedicineById(id)
        }
    }


}