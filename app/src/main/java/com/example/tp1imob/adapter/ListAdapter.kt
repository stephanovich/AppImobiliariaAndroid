package com.example.tp1imob.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp1imob.R
import com.example.tp1imob.model.Imovel
import kotlinx.android.synthetic.main.list_imoveis.view.*

class ListAdapter(
    val mensagens: List<Imovel>,
    val callback: (Imovel) -> Unit
)    : RecyclerView.Adapter<ListAdapter.UsuarioViewHolder>(){

    class UsuarioViewHolder(view : View)
        : RecyclerView.ViewHolder(view) {
        val campoIndent = view.identEdit
        val campoCidade = view.cidadeText
        val campoBairro = view.
        bairroTextList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : UsuarioViewHolder {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.list_imoveis, // Representa um item
                parent,false
            )
        val usuarioViewHolder = UsuarioViewHolder(v)

        usuarioViewHolder.itemView.setOnClickListener {
            val imovel = mensagens[usuarioViewHolder.adapterPosition]
            callback(imovel)
        }

        return usuarioViewHolder
    }

    override fun getItemCount(): Int = mensagens.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val mensagem = mensagens[position]

        holder.campoIndent.text = mensagem.identificacao
        holder.campoBairro.text = mensagem.bairro
        holder.campoCidade.text = mensagem.localidade + " - " + mensagem.uf
    }
}