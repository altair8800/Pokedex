package com.barco.pokedex.di

import com.barco.pokedex.api.PokeService
import com.barco.pokedex.data.PokeRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ArticlesModule {

    @Provides
    fun provideArticlesRepository(): PokeRepository {
        return PokeRepository(createPokeService())
    }

    private fun createPokeService(): PokeService {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(createOkHttpClient())
                .build()
                .create(PokeService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(loggingInterceptor)
        return clientBuilder.build()
    }

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
