package com.example.tp1imob.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tp1imob.R
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_cadastro.*


class CadastroFragment : Fragment() {
    lateinit var imobiliariaViewModel : ImobiliariaViewModel

    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private var dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SalvarBt.setOnClickListener{
            val login = loginCad.text.toString()
            val senha = senhaCadas.text.toString()
            val confirSenha = confrSenhaCad.text.toString()
            val nome = nomeCad.text.toString()
            val cnpj = cnpjCad.text.toString()
            if(login.isNotEmpty() && senha.isNotEmpty() && nome.isNotEmpty() && cnpj.isNotEmpty()) {
                if (senha == confirSenha) {
                    var imobiliaria = Imobiliaria(login, nome, cnpj)
                    cadastro(imobiliaria, senha)
                } else {
                    Toast.makeText(activity, "Senhas não coincidem", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(activity, "Dados inválidos", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun cadastro(imobiliaria: Imobiliaria, senha: String){
        mAuth.createUserWithEmailAndPassword(imobiliaria.email, senha).addOnCompleteListener{
            when{
                it.isSuccessful ->{
                    salvarImovel(imobiliaria)
                } else -> {
                    Toast.makeText(activity, "Erro ao cadastrar.\nVerifique a sua conexão com a internet e tente novamente.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun salvarImovel(imobiliaria: Imobiliaria){
        val userReference =dbFirestore.collection("imobiliarias")
        val document = userReference.document()
        imobiliaria.id = document.id
        document.set(imobiliaria).addOnCompleteListener {
            when{
                it.isSuccessful -> {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        }
    }
}
