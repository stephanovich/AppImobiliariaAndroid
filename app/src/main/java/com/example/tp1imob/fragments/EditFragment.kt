package com.example.tp1imob.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.tp1imob.MainActivity

import com.example.tp1imob.R
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.example.tp1imob.viewmodel.ImovelViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_edit.*

/**
 * A simple [Fragment] subclass.
 */
class EditFragment : Fragment() {
    private lateinit var imovelViewModel  : ImovelViewModel
    lateinit var imobiliariaViewModel: ImobiliariaViewModel
    private var dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.let {

            imovelViewModel  = ViewModelProviders.of(it).get(ImovelViewModel::class.java)

        }
        activity?.let {

            imobiliariaViewModel = ViewModelProviders.of(it).get(ImobiliariaViewModel::class.java)

        }
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHint()
        saveEditBtn.setOnClickListener {
            editImovel()
        }
    }

    private fun editImovel(){
        val ident = identEdit.text.toString()
        val rua = ruaEdit.text.toString()
        val numero = numeroEdit.text.toString()
        var complemento = complementoEdit.text.toString()
        val cidade = cidadeEdit.text.toString()
        val estado = estadoEdit.text.toString()
        var imovel = imovelViewModel.imovel
        val cep = cepTextex.text.toString()
        val bairro = bairrotexte.text.toString()
        if(ident.isNotEmpty() && rua.isNotEmpty() && numero.isNotEmpty()  && cidade.isNotEmpty() && estado.isNotEmpty() && cep.isNotEmpty() && bairro.isNotEmpty()) {
            if (complemento.isEmpty() || complemento.isBlank()) {
                complemento = ""
            }
            imovel?.identificacao = ident
            imovel?.logradouro = rua
            imovel?.numero  = numero
            imovel?.compAdicional = complemento
            imovel?.localidade = cidade
            imovel?.uf = estado
            imovel?.cep = cep
            imovel?.bairro = bairro
            dbFirestore.collection("imoveis").document(imovel?.id!!)
                .set(imovel, SetOptions.merge())
                .addOnCompleteListener {
                when{
                    it.isSuccessful -> {
                        Toast.makeText(activity, "Dados alterados", Toast.LENGTH_LONG).show()
                    } else -> {
                        Toast.makeText(activity, "Dados nãO alterados", Toast.LENGTH_LONG).show()
                    }
                }
            }
            var intt = Intent(context, MainActivity::class.java)
            intt.putExtra("imob", imobiliariaViewModel.imobiliaria)
            startActivity(intt)
        } else {
            Toast.makeText(activity, "Campos em branco!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun setHint(){
        identEdit.setText(imovelViewModel.imovel?.identificacao)
        ruaEdit.setText(imovelViewModel.imovel?.logradouro)
        numeroEdit.setText(imovelViewModel.imovel?.numero)
        complementoEdit.setText(imovelViewModel.imovel?.compAdicional)
        cidadeEdit.setText(imovelViewModel.imovel?.localidade)
        estadoEdit.setText(imovelViewModel.imovel?.uf)
        ediTextCepDetail.setText(imovelViewModel.imovel?.cep)
        ediTextBairoEdit.setText(imovelViewModel.imovel?.bairro)
    }

}
