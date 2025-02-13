package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumDao
import info.alirezaahmadi.taskmanager.data.db.curriculum.CurriculumItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurriculumRepository @Inject constructor(
    private val curriculumDao: CurriculumDao
) {
    suspend fun upsertCurriculum(curriculumItem: CurriculumItem) {
        curriculumDao.upsertCurriculum(curriculumItem)
    }

    fun getAllCurriculum(): Flow<List<CurriculumItem>> =
        curriculumDao.getAllCurriculum()

    suspend fun deleteCurriculum(id: Int) {
        curriculumDao.deleteCurriculum(id)
    }

    fun getCurriculum(id: Int): Flow<CurriculumItem?> =
        curriculumDao.getCurriculum(id)
}