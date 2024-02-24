package com.example.alfredo_herraez_dam2_t1_t2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.alfredo_herraez_dam2_t1_t2.R
import com.example.alfredo_herraez_dam2_t1_t2.model.Producto
import org.w3c.dom.Text

class CarritoAdapter(var context: Context, var lista: ArrayList<Producto>): RecyclerView.Adapter<CarritoAdapter.MyHolder>() {

    // No sé por qué, pero me funciona sin tener que poner más que los dos primeros pasos de la Interfaz de Callback

    class MyHolder(item: View): ViewHolder(item) {
        var img: ImageView
        var name: TextView
        var precio: TextView

        init{
            img = item.findViewById(R.id.imProductoCarrito)
            name = item.findViewById(R.id.nombreProducto)
            precio = item.findViewById(R.id.precio)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val vista: View  = LayoutInflater.from(context).inflate(R.layout.item_carrito_recycler, parent, false)
        return MyHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val producto: Producto = lista[position]
        Glide.with(context).load(producto.imagen).into(holder.img)
        holder.name.text = producto.nombre
        holder.precio.text = producto.precio.toString()
    }

    //1.- Creo Interfaz de Callback
    interface OnCarritoProductoListener{
        //2.- Creo el método
        fun onProductoSelected(){

        }
    }
}