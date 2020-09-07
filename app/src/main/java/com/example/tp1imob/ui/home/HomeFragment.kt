package com.example.tp1imob.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tp1imob.InitActivity
import com.example.tp1imob.MainActivity
import com.example.tp1imob.R
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var imobiliariaViewModel: ImobiliariaViewModel
    private var dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        activity?.let {

            imobiliariaViewModel = ViewModelProviders.of(it).get(ImobiliariaViewModel::class.java)

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setText()
        salvarDadosBt.setOnClickListener{
            val endereco = enderecoHome.text.toString()
            val telefone = telefoneHome.text.toString()
            val celular = celularHome.text.toString()
            if(endereco.isNotBlank() && endereco.isNotEmpty()){
                var imob = imobiliariaViewModel.imobiliaria
                imob?.endereco = endereco
                imob?.telefone = telefone
                imob?.celular = celular
                completarCadastro(imob)
            }
        }
        sairBt.setOnClickListener {
            logout()
        }
    }
    private fun completarCadastro(imob: Imobiliaria?){
        if(imob != null){
            dbFirestore.collection("imobiliarias").document(imob.id).set(imob, SetOptions.merge()).addOnCompleteListener {
                if(it.isSuccessful){
                    imobiliariaViewModel.imobiliaria = imob
                    Toast.makeText(activity, "Dados salvos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun setText(){
        enderecoHome.setText(imobiliariaViewModel.imobiliaria?.endereco)
        telefoneHome.setText(imobiliariaViewModel.imobiliaria?.telefone)
        celularHome.setText(imobiliariaViewModel.imobiliaria?.celular)
    }
    private fun logout(){
        mAuth.signOut()
        FirebaseAuth.getInstance().signOut()
        LoginManager.getInstance().logOut()
        activity?.finish()
        startActivity(Intent(context, InitActivity::class.java))
    }
}
