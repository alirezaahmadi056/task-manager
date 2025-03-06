package info.alirezaahmadi.taskmanager.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.alirezaahmadi.taskmanager.data.remote.AboutApiInterFace
import info.alirezaahmadi.taskmanager.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AboutApiModule {

    @Provides
    @Singleton
    internal fun interceptor(): HttpLoggingInterceptor {
        val login = HttpLoggingInterceptor()
        login.setLevel(HttpLoggingInterceptor.Level.BODY)
        return login
    }

    @Provides
    @Singleton
    fun provideOkhttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor())
            .build()
    }


    @Provides
    @Singleton
    @Named("About")
    fun provideAboutNetWork(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.ABOUT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @Named("About")
    fun provideAboutApiInterFace(
        @Named("About")
        retrofit: Retrofit
    ): AboutApiInterFace =
        retrofit.create(AboutApiInterFace::class.java)

}