package com.example.segunda

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddUserFragment : Fragment() {

    var param1: String? = null
    var param2: String? = null

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
        val adduserview=inflater.inflate(R.layout.fragment_add_user, container, false)
        findelements(adduserview)
        return adduserview
    }

    lateinit var name_txt: EditText
    lateinit var password_txt:EditText
    lateinit var confirm_password_txt:EditText
    lateinit var btn_add_user:Button

    fun findelements(view:View){
        name_txt=view.findViewById(R.id.nombre_input)
        password_txt=view.findViewById(R.id.password_input)
        confirm_password_txt=view.findViewById(R.id.confirm_password_input)
        btn_add_user=view.findViewById(R.id.btn_add_user)

        btn_add_user.setOnClickListener{
            adduser()
        }
    }

    fun adduser(){
        var name=name_txt.text.toString().trim()
        var password=password_txt.text.toString().trim()
        var confirm_password=confirm_password_txt.text.toString().trim()

        var showmsg:Int=0

        if (name.isNotEmpty() && password.isNotEmpty() && confirm_password.isNotEmpty()) {
            if (password == confirm_password) {
                //instanciar clase, hacer escritura, crear contenedor para valores
                val admin=DataBase(context,"db",null,1)
                val query=admin.writableDatabase
                val register=ContentValues()

                //llenar el contenedor
                register.put("Name",name)
                register.put("Password",password)

                //insertar datos a la base de datos
                query.insert("users",null,register)

                name_txt.setText("")
                password_txt.setText("")
                confirm_password_txt.setText("")

                val cursorshowmsg=query.rawQuery("SELECT * FROM settings",null)
                if (cursorshowmsg.moveToFirst()){
                    showmsg=cursorshowmsg.getInt(0)
                }

                query.close()

                if (showmsg==1) {
                    Toast.makeText(context, "Usuario agregado", Toast.LENGTH_SHORT).show()
                }
            }else{
                if (showmsg==1) {
                    Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(context,"Ingresa los datos",Toast.LENGTH_SHORT).show()
            name_txt.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(name_txt, InputMethodManager.SHOW_IMPLICIT)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}