package com.example.composingradio.dagger

import android.content.Context
import android.content.SharedPreferences
import com.example.composingradio.data.remote.RadioApi
import com.example.composingradio.exoplayer.RadioSource
import com.example.composingradio.utils.Constants.BASE_RADIO_URL3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRadioApi () : RadioApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_RADIO_URL3)
            .build()
            .create(RadioApi::class.java)
    }

    @Provides
    @Singleton
    fun providesValidUrlPrefs(
        @ApplicationContext app : Context
    ) : SharedPreferences {
        return app.getSharedPreferences("valid url preferences", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideRadioSource (
        radioApi: RadioApi,
//        radioDAO: RadioDAO,
        sharedPref : SharedPreferences
    ) : RadioSource
            = RadioSource(
        radioApi,
//        radioDAO,
        sharedPref
    )


}