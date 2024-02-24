package com.example.alfredo_herraez_dam2_t1_t2

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.alfredo_herraez_dam2_t1_t2.adapter.ProductosAdapter
import com.example.alfredo_herraez_dam2_t1_t2.databinding.ActivityMainBinding
import com.example.alfredo_herraez_dam2_t1_t2.model.Producto
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), ProductosAdapter.OnRecyclerProductoListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productosAdapter: ProductosAdapter
    private lateinit var listaProductos: ArrayList<Producto>
    private lateinit var carrito: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaProductos = ArrayList()
        productosAdapter = ProductosAdapter(this, listaProductos)
        //productosAdapter.listener = this

        setSupportActionBar(binding.toolbar)                    // conecta con el Menú superior creado con el Toolbar
        supportActionBar!!.title = ""                           //Quito el nombre del archivo, que aparecía en el menú

        binding.carrito.setOnClickListener({
            val intent: Intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("carrito", carrito)
            startActivity(intent)
        })

        binding.recyclerFrontPage.adapter = productosAdapter
        binding.recyclerFrontPage.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        rellenarSpinnerListaCategorias()

        rellenarRecyclerListaProductos()

        carrito = ArrayList() // Inicializo el carrito

    }

    // VOLLEY (uso librería Volley para importar un JSONArray)
    //RELLENO LAS CATEGORÍAS DEL SPINNER IMPORTÁNDOLAS DE LA API
    fun rellenarSpinnerListaCategorias(){
        val listaCategoriasPedidas: JsonArrayRequest = JsonArrayRequest("https://dummyjson.com/products/categories", {
                response ->
                    // Manejo de la respuesta exitosa
                    val categorias = ArrayList<String>()

                    // Recorriendo el array de categorías
                    for (i in 0 until response.length()) {
                        categorias.add(response.getString(i))
                    }

            // Creo un adaptador para el Spinner
            val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, categorias)

            // Especifico el diseño del desplegable
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Uno la gráfica con la lógica
            binding.spinner.adapter = adapter
                }, {error ->
                    Log.e("Error", "Error al obtener las categorías: $error")

        })

        // Obtener lanzamos la solicitud
        Volley.newRequestQueue(applicationContext).add(listaCategoriasPedidas)
    }

    fun rellenarRecyclerListaProductos() {
        //Log.d("YYYY-------------------------------------------------------", "------------------------------Y--------------------")

        val peticion: JsonObjectRequest = JsonObjectRequest("https://dummyjson.com/products",{

            val listado: JSONArray = it.getJSONArray("products")
            for(i in 0 until listado.length()){
                val elementoLista: JSONObject = listado.getJSONObject(i)
                val producto: Producto = Producto(
                    elementoLista.getString("title"),
                    elementoLista.getString("price").toInt(),
                    elementoLista.getJSONArray("images").getString(0)
                )
                productosAdapter.addProducto(producto)
            }

        }, {})

        Volley.newRequestQueue(applicationContext).add(peticion)
    }

    // Método de la Interfaz
    override fun onProductoSelected(producto: Producto) {
        carrito.add(producto)
        //Log.v("--------", "--------")
    }

    fun verProductosCarrito() {
        Log.v("Productos en Carrito: ", carrito.size.toString())
        for(i in carrito){
            Log.v("Producto: " , i.nombre)
        }
    }

}