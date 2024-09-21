package info.alirezaahmadi.taskmanager.data.remote

import info.alirezaahmadi.taskmanager.data.model.notif.NotifeResponse
import info.alirezaahmadi.taskmanager.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NotifeApiInterFace {

    @GET("send")
    suspend fun sendMessage(
        @Query("to") token: String = Constants.TOKEN,
        @Query("text") text: String,
    ): Response<NotifeResponse>

}