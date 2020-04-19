package com.everton.ajudabrasil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ProposicaoAdapter
    var list: MutableList<Proposicao> = emptyList<Proposicao>().toMutableList()

    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // populando a lista para teste
        //list.add(Proposicao(1,"uri","ttt",2014,"Testando kotlin"))

        requestQueue = Volley.newRequestQueue(this)

        recyclerView = findViewById(R.id.recyler)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        adapter = ProposicaoAdapter(list,this, requestQueue)
        recyclerView.adapter = adapter
        load()
    }

    // acessando a api da camera e retornando os dados
    private fun load(){
        var request = JsonObjectRequest(Request.Method.GET,
            "https://dadosabertos.camara.leg.br/api/v2/proposicoes?ordem=ASC&ordenarPor=id",
            null,
            Response.Listener<JSONObject> {
                list.clear()
                val dados = it.getJSONArray("dados")

                for(i in 0 until dados.length()){
                    val item = dados.getJSONObject(i)
                    list.add(Proposicao(item.getInt("id"),
                                        item.getString("uri"),
                                        item.getString("siglaTipo"),
                                        item.getInt("ano"),
                                        item.getString("ementa")))
                }
                adapter.notifyDataSetChanged()
            },
            null)
         requestQueue.add(request)

    }
}
