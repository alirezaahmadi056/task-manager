package ir.lrn.kara.repository

import ir.lrn.kara.data.db.curriculum.CurriculumDao
import ir.lrn.kara.data.db.curriculum.CurriculumItem
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