package com.example.tp1imob.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tp1imob.MainActivity
import com.example.tp1imob.R
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.example.tp1imob.viewmodel.ImovelViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_view.*

/**
 * A simple [Fragment] subclass.
 */
class ViewFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        identEdit.text = String.format("Identificação: %s", imovelViewModel.imovel?.identificacao)
        ruaEdit.text = String.format("Rua: %s", imovelViewModel.imovel?.logradouro)
        numeroText.text = String.format("Número: %s", imovelViewModel.imovel?.numero)
        complementoText.text = String.format("Complemento: %s", imovelViewModel.imovel?.compAdicional)
        cidadeText.text = String.format("Cidade: %s", imovelViewModel.imovel?.localidade)
        estadoText.text = String.format("Estado: %s", imovelViewModel.imovel?.uf)
        identCep.text = String.format("CEP: %s", imovelViewModel.imovel?.cep)
        bairroTextDetail.text = String.format("Bairro: %s", imovelViewModel.imovel?.bairro)

        editBt.setOnClickListener {
            findNavController().navigate(R.id.editFragment)
        }

        deletBt.setOnClickListener {
            exibirDialogo()
        }
    }

    fun exibirDialogo(){
        var magBox = AlertDialog.Builder(context)
        magBox.setTitle("Excluindo")
        magBox.setIcon(android.R.drawable.ic_delete)
        magBox.setMessage("Tem certeza que deseja excuir esse imovél?")
        magBox.setPositiveButton("Sim"){dialog, which ->
            deletarImovel()
        }
        magBox.setNegativeButton("Não"){dialog, which ->
            Toast.makeText(context,"Imovel não deletado!!!",Toast.LENGTH_SHORT).show()
        }
        magBox.show();
    }

    private fun deletarImovel(){
        var imovel = imovelViewModel.imovel
        dbFirestore.collection("imoveis").document(imovel?.id!!).delete().addOnCompleteListener {
            when{
                it.isSuccessful ->{
                    Toast.makeText(activity, "Imovel detelado com sucesso", Toast.LENGTH_LONG).show()

                } else -> {
                    Toast.makeText(activity, "Falha ao deletar", Toast.LENGTH_LONG).show()
                }
            }
        }
        var intt = Intent(context, MainActivity::class.java)
        intt.putExtra("imob", imobiliariaViewModel.imobiliaria)
        startActivity(intt)
    }
}
