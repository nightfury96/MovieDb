package com.nightfury.moviedb.presentation.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nightfury.moviedb.BuildConfig
import com.nightfury.moviedb.data.api.DiscoverApi
import com.nightfury.moviedb.data.repository.MovieRepositoryImpl
import com.nightfury.moviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): DiscoverApi {
        return retrofitBuilder
            .build()
            .create(DiscoverApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        api: DiscoverApi
    ) = MovieRepositoryImpl(api) as MovieRepository

}