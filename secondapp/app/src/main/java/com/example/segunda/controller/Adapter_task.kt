package com.example.segunda.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.segunda.R
import com.example.segunda.data.task

class Adapter_task(val tareaslist : ArrayList<task>) :RecyclerView.Adapter<Adapter_task.ViewHolder>(){

    var onItemClick: ((task) -> Unit)? = null //funcion lambda "Unit" en Kotlin es equivalente a "void" en Java

    //inflar layout personalizado (item_tareas_list)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_tareas_list,parent,false)
        return ViewHolder(itemView)
    }

    //tamaño del arreglo la cantidad de tareas
    override fun getItemCount(): Int {
        return tareaslist.size
    }

    //recorrer los elementos del arreglo
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea=tareaslist[position]

        holder.idtarea=tarea.id
        holder.tareaName.text=tarea.name
        holder.tareaDescription.text=tarea.description
        holder.tareaImage.setImageResource(tarea.image)
        holder.tareaUser=tarea.user

        // Configura el listener para manejar clics en cada item
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(tarea)//envio la lista a la funcion lambda
        }
    }

    //contruir los elementos para el (item_tareas_list)
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        var idtarea:Int=0
        var tareaName:TextView=itemView.findViewById(R.id.dialog_item_title)
        var tareaDescription:TextView=itemView.findViewById(R.id.dialog_item_content)
        var tareaImage: ImageView =itemView.findViewById(R.id.dialog_item_image)
        var tareaUser:Int=0
    }
}