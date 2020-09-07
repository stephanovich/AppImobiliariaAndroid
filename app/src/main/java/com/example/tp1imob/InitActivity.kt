package com.example.tp1imob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.viewmodel.ImobiliariaViewModel


class InitActivity : AppCompatActivity() {
    lateinit var imobiliariaViewModel: ImobiliariaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        imobiliariaViewModel = ViewModelProviders.of(this)[ImobiliariaViewModel::class.java]
        imobiliariaViewModel.imobiliaria = Imobiliaria()
    }

}
