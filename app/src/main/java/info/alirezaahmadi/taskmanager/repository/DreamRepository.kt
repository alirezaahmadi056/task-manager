package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.dream.DreamItemDao
import javax.inject.Inject

class DreamRepository @Inject constructor(
    private val dao: DreamItemDao
) {

}