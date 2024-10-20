package com.example.segunda

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PrincipalFragment : Fragment() {
    var param1: String? = null
    var param2: String? = null

    lateinit var btn_tareas: ImageButton
    lateinit var btn_agregar_tarea: ImageButton
    lateinit var btn_agregar_usuario: ImageButton
    lateinit var btn_configuracion: ImageButton
    lateinit var nombre_usuario: TextView
    lateinit var admin:DataBase
    lateinit var db: SQLiteDatabase
    var showmsg:Int=0

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
        val rootview = inflater.inflate(R.layout.fragment_principal, container, false)
        findelements(rootview)
        listeners()
        return rootview
    }

    fun findelements(view:View){
        nombre_usuario=view.findViewById(R.id.nombre_user)
        btn_tareas=view.findViewById(R.id.btn_tareas)
        btn_agregar_tarea=view.findViewById(R.id.btn_agregar_tareas)
        btn_agregar_usuario=view.findViewById(R.id.btn_agregar_usuario)
        btn_configuracion=view.findViewById(R.id.btn_configuracion)

        val nombreuser=activity?.intent?.getStringExtra("UserName")
        nombre_usuario.setText("Panel de "+nombreuser)

        admin=DataBase(context,"db",null,1)
        db=admin.readableDatabase

        val cursorshowmsg=db.rawQuery("SELECT * FROM settings",null)
        if (cursorshowmsg.moveToFirst()){
            showmsg=cursorshowmsg.getInt(0)
        }
        db.close()
    }

    fun listeners() {
        btn_tareas.setOnClickListener {
            tareas()
        }
        btn_agregar_tarea.setOnClickListener {
            agregar_tarea()
        }
        btn_agregar_usuario.setOnClickListener {
            agregar_ususario()
        }
        btn_configuracion.setOnClickListener {
            configuracion()
        }
    }

    fun tareas(){

        if (showmsg==1) {
            Toast.makeText(context, "Tareas", Toast.LENGTH_SHORT).show()
        }

        //reemplazar el fragment
        parentFragmentManager.commit{
            replace<TaskFragment>(R.id.fragmentContainerView2)
            setReorderingAllowed(true)
            addToBackStack("principal")
        }
    }

    fun agregar_tarea(){

        if (showmsg==1) {
            Toast.makeText(context, "Agregar tarea", Toast.LENGTH_SHORT).show()
        }

        parentFragmentManager.commit{
            replace<AddTaskFragment>(R.id.fragmentContainerView2)
            setReorderingAllowed(true)
            addToBackStack("principal")
        }
    }

    fun agregar_ususario(){

        if (showmsg==1) {
            Toast.makeText(context, "Agregar usuario", Toast.LENGTH_SHORT).show()
        }

        parentFragmentManager.commit{
            replace<AddUserFragment>(R.id.fragmentContainerView2)
            setReorderingAllowed(true)
            addToBackStack("principal")
        }
    }

    fun configuracion(){

        if (showmsg==1) {
            Toast.makeText(context, "Configuracion", Toast.LENGTH_SHORT).show()
        }

        parentFragmentManager.commit{
            replace<SettingsFragment>(R.id.fragmentContainerView2)
            setReorderingAllowed(true)
            addToBackStack("principal")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PrincipalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}