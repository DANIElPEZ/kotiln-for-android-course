package com.example.segunda

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.segunda.controller.Adapter_task
import com.example.segunda.data.task

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TaskFragment : Fragment() {

    var param1: String? = null
    var param2: String? = null

    lateinit var adapterlista: Adapter_task
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val taskview = inflater.inflate(R.layout.fragment_task, container, false)
        return taskview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterlista=Adapter_task(getTaskList()) //enviar el arreglo a la clase Adapter_task y obtener los elementos de item_tareas_list construidos

        val LayoutManager=LinearLayoutManager(context)//obtenga el contexto

        recyclerView=view.findViewById(R.id.tareas)//buscar el elemento del fragment_task
        recyclerView.layoutManager=LayoutManager//asignar manejador
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter=adapterlista //asignar las tareas al recyclerview

        adapterlista.onItemClick={//los items ya tienen configurado la funcion onItemClick y en task fragment estan a la escucha

            val dialogView=LayoutInflater.from(context).inflate(R.layout.dialog_task_item,null)
            val dialog=AlertDialog.Builder(context).setView(dialogView).create()

            val imagedialog:ImageView=dialogView.findViewById(R.id.dialog_task_image)
            val titledialog:TextView=dialogView.findViewById(R.id.dialog_task_title)
            val description:TextView=dialogView.findViewById(R.id.dialog_task_description)

            titledialog.setText(it.name)
            description.setText(it.description)
            imagedialog.setImageResource(it.image)

            dialog.show()
        }
    }

    fun getTaskList():ArrayList<task>{
        val arrayIcons= arrayOf<String>("call","code","configure","mail","pay")
        val userid=activity?.intent?.getIntExtra("UserId",1)

        //contiene tarea de la base de datos
        var tarealist=ArrayList<task>()

        val admin=DataBase(context,"db",null,1)
        val db=admin.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM task WHERE UserTask=${userid}",null)

        if (cursor.moveToFirst()){
            //llenar el arreglo
            do {

                //obtener el recurso imge
                var iconstr=arrayIcons[cursor.getInt(3)]
                var image=resources.getIdentifier(iconstr,"drawable", "com.example.segunda")

                tarealist.add(
                    task(
                        cursor.getInt(0),
                   userid?:0,
                        image,
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )

            }while (cursor.moveToNext())
        }

        db.close()

        return tarealist
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}