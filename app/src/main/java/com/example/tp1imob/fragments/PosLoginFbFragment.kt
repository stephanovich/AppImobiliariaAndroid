package com.example.tp1imob.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tp1imob.MainActivity
import com.example.tp1imob.R
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_pos_login_fb.*

class PosLoginFbFragment : Fragment() {
    lateinit var imobiliariaViewModel : ImobiliariaViewModel
    private var dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {

            imobiliariaViewModel = ViewModelProviders.of(it).get(ImobiliariaViewModel::class.java)

        }
        return inflater.inflate(R.layout.fragment_pos_login_fb, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        concluirBtFb.setOnClickListener {
            val nome = posFbNomeText.text.toString()
            val cnpj = posFbCnpjText.text.toString()
            if(nome.isNotEmpty() && cnpj.isNotEmpty()){
                imobiliariaViewModel.imobiliaria?.nome = nome
                imobiliariaViewModel.imobiliaria?.cnpj = cnpj
                salvarDados(imobiliariaViewModel.imobiliaria!!)
            }
        }
    }

    private fun salvarDados(imobiliaria: Imobiliaria){
        val userReference = dbFirestore.collection("imobiliarias")
        val document = userReference.document()
        imobiliaria.id = document.id
        document.set(imobiliaria).addOnCompleteListener {
            when{
                it.isSuccessful -> {
                    var intent = Intent(activity, MainActivity::class.java)
                    intent.putExtra("imob", imobiliaria)
                    startActivity(intent)
                }
            }
        }
    }
}