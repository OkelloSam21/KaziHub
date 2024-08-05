package com.samuelokello.kazihub.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.samuelokello.kazihub.data.remote.KaziHubApi
import com.samuelokello.kazihub.data.repository.AuthRepositoryImpl
import com.samuelokello.kazihub.data.repository.BusinessRepositoryImpl
import com.samuelokello.kazihub.data.repository.JobRepositoryImpl
import com.samuelokello.kazihub.data.repository.ProposalRepositoryImpl
import com.samuelokello.kazihub.data.repository.WorkerRepositoryImp
import com.samuelokello.kazihub.domain.repositpry.AuthRepository
import com.samuelokello.kazihub.domain.repositpry.BusinessRepository
import com.samuelokello.kazihub.domain.repositpry.JobRepository
import com.samuelokello.kazihub.domain.repositpry.ProposalRepository
import com.samuelokello.kazihub.domain.repositpry.WorkerRepository
import com.samuelokello.kazihub.utils.AuthInterceptor
import com.samuelokello.kazihub.utils.TokenManager
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
    fun provideTokenManager(): TokenManager = TokenManager()

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun providesLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        logger: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
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
        token: TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(api, token)
    }

    @Provides
    @Singleton
    fun provideBusinessRepository(
        api: KaziHubApi,
    ): BusinessRepository {
        return BusinessRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideWorkerRepository(
        api: KaziHubApi,
    ): WorkerRepository {
        return WorkerRepositoryImp(api)
    }

    @Provides
    @Singleton
    fun provideJobRepository(
        api: KaziHubApi,
    ): JobRepository {
        return JobRepositoryImpl(api)
    }

    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    // provide proposal repository
    @Provides
    @Singleton
    fun provideProposalRepository(
        api: KaziHubApi
    ): ProposalRepository {
        return ProposalRepositoryImpl(api)
    }
}