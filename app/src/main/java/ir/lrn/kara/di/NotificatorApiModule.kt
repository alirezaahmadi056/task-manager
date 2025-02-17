package ir.lrn.kara.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.lrn.kara.data.remote.NotifeApiInterFace
import ir.lrn.kara.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotificatorApiModule {

    @Provides
    @Singleton
    @Named("notife")
    fun provideNotificatorApi(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.NOTIFE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    @Named("notife")
    fun provideNotifeApiInterFace(
        @Named("notife")
        retrofit: Retrofit
    ): NotifeApiInterFace =
        retrofit.create(NotifeApiInterFace::class.java)
}