package com.example.tp1imob.apiservice

import com.example.tp1imob.model.Imovel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImovelService {

    @GET("{cep}/json")
    fun getCep(@Path("cep") cep:String): Call<Imovel>

}