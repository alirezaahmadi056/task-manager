package info.alirezaahmadi.taskmanager.repository

import android.util.Log
import info.alirezaahmadi.taskmanager.data.model.about.AboutResponse
import info.alirezaahmadi.taskmanager.data.remote.AboutApiInterFace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Named

class AboutRepository @Inject constructor(
    @Named("About")
    private val apiInterFace: AboutApiInterFace
) {
    private val _allData = MutableStateFlow(AboutResponse())
    val allData = _allData.asStateFlow()


    val loading =MutableStateFlow(false)
    suspend fun getAboutData() {
        loading.emit(true)
        val response =try {
            apiInterFace.getAboutData()
        }catch (e:Exception){
            Log.e("hosein","getAboutData failed ${e.message}")
            return
        }
        if (response.isSuccessful){
            val body =  response.body()
            body?.let {
                _allData.emit(it)
            }
            loading.emit(false)
        }else{
            Log.e("hosein","getAboutData not success")
        }

    }
}