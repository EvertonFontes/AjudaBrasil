package com.everton.ajudabrasil

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class ProposicaoAdapter (val list:List<Proposicao>,val context:Context, val requestQueue:RequestQueue) : RecyclerView.Adapter<ProposicaoAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_proposicao,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proposicao = list[position]
        holder.apply {
            pet.text = proposicao.siglaTipo
            ementa.text = proposicao.ementa
            ano.text = proposicao.ano.toString()
            loadPDF(proposicao.uri, holder)
        }
    }

    // metodo para trazer pdf da api acionando o botão de cada CardView
    private fun loadPDF(uri:String, holder:ViewHolder){
        var request = JsonObjectRequest(Request.Method.GET,
            uri,
            null,
            Response.Listener<JSONObject> {

                val dados = it.getJSONObject("dados")

                holder.verMais.setOnClickListener(){
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse(dados.getString("urlInteiroTeor")))
                    context.startActivity(intent)
                }
            },
            null)
        requestQueue.add(request)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val pet: TextView = itemView.findViewById(R.id.tv_pet)
        val ementa: TextView = itemView.findViewById(R.id.tv_ementa)
        val ano: TextView = itemView.findViewById(R.id.tv_ano_api)
        val verMais: Button = itemView.findViewById(R.id.bt_ver_mais)
    }
}