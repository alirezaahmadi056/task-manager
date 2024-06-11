package ir.hoseinahmadi.taskmanager.data.remote

import ir.hoseinahmadi.taskmanager.data.model.about.AboutResponse
import retrofit2.Response
import retrofit2.http.GET

interface AboutApiInterFace {

    @GET("teacher/alireza-ahmadi")
    suspend fun getAboutData(): Response<AboutResponse>

}