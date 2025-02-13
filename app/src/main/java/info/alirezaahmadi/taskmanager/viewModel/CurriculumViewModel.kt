package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumItem
import info.alirezaahmadi.taskmanager.repository.CurriculumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurriculumViewModel @Inject constructor(
    private val curriculumRepository: CurriculumRepository
) : ViewModel() {

    fun upsertCurriculum(curriculumItem: CurriculumItem) {
        viewModelScope.launch(Dispatchers.IO) {
            curriculumRepository.upsertCurriculum(curriculumItem)
        }
    }
    fun getAllCurriculum(): Flow<List<CurriculumItem>> =
        curriculumRepository.getAllCurriculum()
    fun deleteCurriculum(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            curriculumRepository.deleteCurriculum(id)
        }
    }
    fun getCurriculum(id: Int): Flow<CurriculumItem?> =
        curriculumRepository.getCurriculum(id)

}