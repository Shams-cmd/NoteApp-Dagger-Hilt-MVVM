package com.example.noteapp.di

import com.example.noteapp.Utlils.Constants
import com.example.noteapp.api.AuthInterceptor
import com.example.noteapp.api.NoteAPI
import com.example.noteapp.api.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
  fun providesReytrofitBulder():Retrofit.Builder{
      return Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl(Constants.BASE_URL)

  }

    @Singleton
    @Provides
    fun providesOkHttpClient( authInterceptor: AuthInterceptor) : OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()

    }

    @Singleton
    @Provides
    fun providesUserApi(retrofitBulder: Retrofit.Builder) : UserAPI{
        return retrofitBulder.build().create(UserAPI::class.java)
    }

//    fun providesAuthReytrofit(okHttpClient: OkHttpClient):Retrofit{
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .baseUrl(Constants.BASE_URL)
//            .build()
//    }

    fun providesNotesAPI(retrofitBulder: Builder,okHttpClient: OkHttpClient) : NoteAPI{
        return retrofitBulder
            .client(okHttpClient)
            .build().create(NoteAPI::class.java)
    }
}