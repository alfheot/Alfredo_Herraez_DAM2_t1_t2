package com.example.alfredo_herraez_dam2_t1_t2.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.alfredo_herraez_dam2_t1_t2.R
import com.example.alfredo_herraez_dam2_t1_t2.model.Producto

class ProductosAdapter(var context: Context, var lista: ArrayList<Producto> ): RecyclerView.Adapter<ProductosAdapter.MyHolder>() {

    // Punto 3 Objeto de la Interfaz de Callback
    public lateinit var listener: OnRecyclerProductoListener

    // Punto 4.- Inicializo Objeto Interfaz
    init{
        listener = context as OnRecyclerProductoListener
    }

    class MyHolder(item: View): ViewHolder(item) {
        var foto: ImageView
        var nombre: TextView
        var carrito: ImageView

        init{
            foto = item.findViewById(R.id.imagen)
            nombre = item.findViewById(R.id.nombre)
            carrito = item.findViewById(R.id.carrito)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista: View = LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false)
        return MyHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val producto = lista[position]
        holder.nombre.text = producto.nombre
        Glide.with(context).load(producto.imagen).into(holder.foto)

        // Punto 5.- Llamo al método Interfaz
        holder.carrito.setOnClickListener({
            listener.onProductoSelected(producto)
        })

    }

    fun addProducto(producto: Producto){
        lista.add(producto)
        notifyItemInserted(lista.size - 1)
    }

    // Punto 1 Interfaz de Callback
    public interface OnRecyclerProductoListener{
        // Punto 2 Método de la interfaz
        fun onProductoSelected(producto: Producto)
    }



}