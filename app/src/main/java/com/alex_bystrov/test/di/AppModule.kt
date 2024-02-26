package com.alex_bystrov.test.di

import com.alex_bystrov.test.common.AppDispatchers
import com.alex_bystrov.test.data.remote.characters.CharactersApi
import com.alex_bystrov.test.data.remote.characters.CharactersRepositoryImpl
import com.alex_bystrov.test.data.remote.characters.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    @Provides
    @Singleton
    fun provideCharactersApi(): CharactersApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CharactersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharactersRepository(api: CharactersApi): CharactersRepository {
        return CharactersRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesDispatchers(): AppDispatchers = AppDispatchers()
}