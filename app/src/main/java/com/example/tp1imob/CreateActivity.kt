package com.example.tp1imob

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tp1imob.apiservice.ApiClient
import com.example.tp1imob.model.Imobiliaria
import com.example.tp1imob.model.Imovel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {
    var imobiliaria: Imobiliaria? = null
    private var dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        imobiliaria = intent.getSerializableExtra("usuario") as Imobiliaria

        validarCepBt.setOnClickListener {
            val cep = cepText.text.toString()
            if(cep.isNotEmpty() && cep.isNotBlank()){
                setTextWithCep(cep)
            }
        }

        salvarBt.setOnClickListener {
            val cep = cepText.text.toString()
            val ident = itentText.text.toString()
            val logradouro = ruaEdit.text.toString()
            val numero = numeroText.text.toString()
            val compAdicional = complementoText.text.toString()
            val localidade = cidadeText.text.toString()
            val bairro = bairroText.text.toString()
            val uf = estadoText.text.toString()
            if (ident.isNotEmpty() && logradouro.isNotEmpty() && numero.isNotEmpty() && localidade.isNotEmpty() && uf.isNotEmpty()) {
                if (imobiliaria != null) {
                    var imovel = Imovel(ident,cep,logradouro,numero,compAdicional,bairro,localidade,uf)
                    imovel.idimobiliaria = imobiliaria!!.id
                    inserirImovel(imovel)
                }
            }
        }
        cepCorreiosBt.setOnClickListener {
            openNavigator()
        }
    }

    private fun inserirImovel(imovel: Imovel) {
        val userReference = dbFirestore.collection("imoveis")
        val document = userReference.document()
        imovel.id = document.id
        document.set(imovel).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    var intt = Intent(applicationContext, MainActivity::class.java)
                    intt.putExtra("imob", imobiliaria)
                    startActivity(intt)
                }
                else -> {
                    Toast.makeText(this, "Falha ao cadastrar o imóvel!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setTextWithCep(cep: String){
        val call = ApiClient.getImovelService().getCep(cep)
        call.enqueue(
            object : Callback<Imovel> {
                override fun onFailure(
                    call: Call<Imovel>,
                    t: Throwable
                ) {
                    Log.d("Retrofit ERROR", t.message!!)
                }
                override fun onResponse(
                    call: Call<Imovel>,
                    response: Response<Imovel>
                ) {
                    val imovel = response.body()
                    cepText.setText(imovel?.cep)
                    ruaEdit.setText(imovel?.logradouro)
                    cidadeText.setText(imovel?.localidade)
                    bairroText.setText(imovel?.bairro)
                    estadoText.setText(imovel?.uf)
                    Toast.makeText(applicationContext,"Dados Obtidos com sucesso.\nConfira os dados e complete os campos.", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun openNavigator(){
        val url = "http://www.buscacep.correios.com.br/sistemas/buscacep/buscaCepEndereco.cfm"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}