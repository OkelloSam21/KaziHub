package com.samuelokello.kazihub.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.data.repository.AuthRepositoryImpl
import com.samuelokello.kazihub.data.repository.BusinessRepositoryImpl
import com.samuelokello.kazihub.data.repository.JobRepositoryImpl
import com.samuelokello.kazihub.data.repository.WorkerRepositoryImp
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.domain.repositpry.WorkerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kazihub.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providesKaziHubApi(retrofit: Retrofit): KaziHubApi {
        return retrofit.create(KaziHubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: KaziHubApi,
        @ApplicationContext context: Context,
    ): AuthRepository {
        return AuthRepositoryImpl(api, context)
    }

    @Provides
    @Singleton
    fun provideBusinessRepository(
        api: KaziHubApi,
        @ApplicationContext context: Context
    ): BusinessRepository {
        return BusinessRepositoryImpl(api,context)
    }

    @Provides
    @Singleton
    fun provideWorkerRepository(
        api: KaziHubApi,
        @ApplicationContext context: Context
    ): WorkerRepository {
        return WorkerRepositoryImp(api,context)
    }
    @Provides
    @Singleton
    fun provideJobRepository(
        api: KaziHubApi,
        context: Context
    ): JobRepository {
        return JobRepositoryImpl(api = api, context = context)
    }
    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext appContext: Context): Context{
        return appContext
    }

}