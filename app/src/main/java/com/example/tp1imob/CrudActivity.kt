package com.example.tp1imob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.model.Imovel
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.example.tp1imob.viewmodel.ImovelViewModel

class CrudActivity : AppCompatActivity() {
    lateinit var imovelViewModel: ImovelViewModel
    lateinit var imobiliariaViewModel: ImobiliariaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)
        imovelViewModel = ViewModelProviders.of(this)[ImovelViewModel::class.java]
        imobiliariaViewModel = ViewModelProviders.of(this)[ImobiliariaViewModel::class.java]
        var imovel = intent.getSerializableExtra("imovel") as Imovel
        var imobiliaria = intent.getSerializableExtra("imob") as Imobiliaria
        imovelViewModel.imovel = imovel
        imobiliariaViewModel.imobiliaria = imobiliaria
    }
}
