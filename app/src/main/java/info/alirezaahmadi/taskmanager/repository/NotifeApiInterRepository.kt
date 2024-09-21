package info.alirezaahmadi.taskmanager.repository

import info.alirezaahmadi.taskmanager.data.model.notif.NotifeResponse
import info.alirezaahmadi.taskmanager.data.remote.NotifeApiInterFace
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Named

class NotifeApiInterRepository @Inject constructor(
    @Named("notife")
    private var api:NotifeApiInterFace
){
    val notifeResponse =MutableStateFlow(NotifeResponse())

    suspend fun senMessage(message:String){
        val response =try {
            api.sendMessage(text =message)
        }catch (e:Exception){
            return
        }
        if (response.isSuccessful){
            response.body()?.let {
                notifeResponse.emit(it)
            }
        }
    }

}