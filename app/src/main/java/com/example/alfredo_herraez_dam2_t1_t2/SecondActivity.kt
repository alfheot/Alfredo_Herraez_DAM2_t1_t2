package com.example.alfredo_herraez_dam2_t1_t2

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alfredo_herraez_dam2_t1_t2.adapter.CarritoAdapter
import com.example.alfredo_herraez_dam2_t1_t2.databinding.ActivitySecondBinding
import com.example.alfredo_herraez_dam2_t1_t2.model.Producto
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class SecondActivity: AppCompatActivity(), Serializable {

    private lateinit var binding: ActivitySecondBinding


    private lateinit var carritoAdapter: CarritoAdapter
    //private lateinit var listadoCarrito: ArrayList<Producto>

    private lateinit var carritoRecuperado: ArrayList<Producto>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Decimos que ponga el ActionBar de soporte que pusimos con el Toolbar
        setSupportActionBar(binding.toolbar2)
        supportActionBar!!.title = ""  //Vacío el título del menú, que  no sé por qué me pone algo!!

        //listadoCarrito = ArrayList()
        carritoRecuperado = intent.extras!!.getSerializable("carrito") as ArrayList<Producto>
        carritoAdapter = CarritoAdapter(this, carritoRecuperado)

        //pego la parte gráfica a la lógica
        binding.recyclerCarrito.adapter = carritoAdapter
        binding.recyclerCarrito.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        binding.total.text= calcularTotal(carritoRecuperado)
    }

    //Función que usaré en el carrito
    fun calcularTotal(carro: ArrayList<Producto>): String{
        var total = 0
        for(i in carro) {
            total += i.precio
        }
        return total.toString() + "€"
    }


    //Coloca el Menú arriba a la derecha
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Función para escuchar a los elementos del Menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.confirmar -> {
                Snackbar.make(binding.root, "Enhorabuenaaa por la compra de ${calcularTotal(carritoRecuperado)}",Snackbar.LENGTH_SHORT ).show()
            }
            R.id.vaciar -> {

                //Log.v("tamaño 1: ", carritoRecuperado.size.toString())
                //Vacío el carrito de productos
                carritoRecuperado.clear()
                //Log.v("tamaño 2: ", carritoRecuperado.size.toString())
                //Vuelvo a presentar el recycler, esta vez vacío.
                binding.recyclerCarrito.adapter = carritoAdapter
                binding.recyclerCarrito.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                //Vuelvo a poner el coste del carrito, ahora a 0€
                binding.total.text= calcularTotal(carritoRecuperado)

                Snackbar.make(binding.root, "Carrito vacío",Snackbar.LENGTH_SHORT ).show()

            }
        }
        return true
    }


}