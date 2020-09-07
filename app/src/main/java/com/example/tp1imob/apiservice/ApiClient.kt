package com.example.tp1imob.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var instance : Retrofit? = null
    private val url : String = "https://viacep.com.br/ws/"
    private fun getInstance(): Retrofit {
        if (instance == null){
            instance = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance as Retrofit
    }
    fun getImovelService(): ImovelService = getInstance().create(ImovelService::class.java)

}