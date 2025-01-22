package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.db.goals.GoalsItemDao
import javax.inject.Inject

class GoalsRepository @Inject constructor(
    private val dao: GoalsItemDao
) {
}