package ir.hoseinahmadi.taskmanager.data.remote

import ir.hoseinahmadi.taskmanager.data.model.notif.NotifeResponse
import ir.hoseinahmadi.taskmanager.util.Constants
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NotifeApiInterFace {

    @GET("send")
    suspend fun sendMessage(
        @Query("to") token: String = Constants.TOKEN,
        @Query("text") text: String,
    ): Response<NotifeResponse>

}