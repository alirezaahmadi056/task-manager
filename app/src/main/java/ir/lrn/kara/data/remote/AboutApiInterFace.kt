package ir.lrn.kara.data.remote

import ir.lrn.kara.data.model.about.AboutResponse
import retrofit2.Response
import retrofit2.http.GET

interface AboutApiInterFace {

    @GET("teacher/alireza-ahmadi")
    suspend fun getAboutData(): Response<AboutResponse>

}