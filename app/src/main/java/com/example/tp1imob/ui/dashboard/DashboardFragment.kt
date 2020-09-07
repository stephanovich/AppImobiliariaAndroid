package com.example.tp1imob.ui.dashboard


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp1imob.CreateActivity
import com.example.tp1imob.CrudActivity
import com.example.tp1imob.R
import com.example.tp1imob.adapter.ListAdapter
import com.example.tp1imob.model.Imovel
import com.example.tp1imob.viewmodel.ImobiliariaViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.lang.IllegalStateException


class DashboardFragment : Fragment() {

    private lateinit var imobiliariaViewModel: ImobiliariaViewModel
    private var dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        activity?.let {

            imobiliariaViewModel = ViewModelProviders.of(it).get(ImobiliariaViewModel::class.java)

        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SetupListTask().execute()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setText()
        addBtn.setOnClickListener {
            val intt = Intent(context, CreateActivity::class.java)
            intt.putExtra("usuario", imobiliariaViewModel.imobiliaria)
            startActivity(intt)
        }
    }

    private fun setText() {
        nomeImo.text = String.format("Imobiliaria: %s", imobiliariaViewModel.imobiliaria?.nome)
        ncpjImo.text = String.format("CNPJ: %s", imobiliariaViewModel.imobiliaria?.cnpj)
    }

    @SuppressLint("StaticFieldLeak")
    inner class SetupListTask : AsyncTask<
            Unit,
            Unit,
            List<Any?>
            >() {
        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: Unit): List<Any> {
            getLista(view!!)
            return listOf(true, "foi")
        }

        override fun onPostExecute(result: List<Any?>) {
            if (result.get(0) == true) {
                Log.e("OK", result.get(1).toString())
            }
        }

        fun callback(imovel: Imovel) {
            val intt = Intent(context, CrudActivity::class.java)
            intt.putExtra("imovel", imovel)
            intt.putExtra("imob", imobiliariaViewModel.imobiliaria)
            startActivity(intt)
        }

        fun getLista(v: View) {
            dbFirestore.collection("imoveis")
            .whereEqualTo("idimobiliaria", imobiliariaViewModel.imobiliaria?.id)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                val lista = ArrayList<Imovel>()
                for (doc in value!!) {
                    val imovel = doc.toObject(Imovel::class.java)
                    lista.add(imovel)
                }
                try{
                    var listAdapter = ListAdapter(lista, this::callback)
                    v.recView.adapter = listAdapter
                    v.recView.layoutManager = LinearLayoutManager(activity?.applicationContext)
                } catch (e: IllegalStateException){
                    Log.e("ERROR", e.message!!)
                }
            }
        }
    }

    /*private fun listarImoveis() {
        var lista = ArrayList<Imovel>()

        dbFirestore.collection("imoveis").whereEqualTo("idimobiliaria", imobiliariaViewModel.imobiliaria?.id).get().addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    for (document in it.result?.documents!!) {
                        var imovel = document.toObject(Imovel::class.java)
                        if (imovel != null) {
                            lista.add(imovel)
                        }
                    }
                    var listAdapter = ListAdapter(lista, this::callback)
                    recView.adapter = listAdapter
                    recView.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }*/

}

