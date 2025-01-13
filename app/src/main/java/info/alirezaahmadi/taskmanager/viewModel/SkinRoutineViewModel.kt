package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.skinRoutine.SkinRoutineItem
import info.alirezaahmadi.taskmanager.repository.SkinRoutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkinRoutineViewModel @Inject constructor(
    private val repository: SkinRoutineRepository
) : ViewModel() {

    fun upsertSkinRoutine(skinRoutineItem: SkinRoutineItem) {
        viewModelScope.launch(Dispatchers.IO) { repository.upsertSkinRoutine(skinRoutineItem) }
    }
    fun getAllSkinRoutine(): Flow<List<SkinRoutineItem>> = repository.getAllSkinRoutine()
    fun deletedSkinRoutine(id: Int) {
        viewModelScope.launch { repository.deletedSkinRoutine(id) }
    }
    fun getSkinRoutine(itemId: Int): Flow<SkinRoutineItem?> = repository.getSkinRoutine(itemId)

}