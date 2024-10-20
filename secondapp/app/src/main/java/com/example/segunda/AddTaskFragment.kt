package com.example.segunda

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddTaskFragment : Fragment() {

    var param1: String? = null
    var param2: String? = null

    var pickImage=0
    var imageUri:Uri?=null

    lateinit var title_task:EditText
    lateinit var description_task:EditText
    lateinit var btn_select_image:Button
    lateinit var imageLoad: ImageView
    lateinit var btn_add_task:Button

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
        val addtaskview=inflater.inflate(R.layout.fragment_add_task, container, false)
        findelements(addtaskview)
        return addtaskview
    }

    fun findelements(view:View){
        title_task=view.findViewById(R.id.TitleTask)
        description_task=view.findViewById(R.id.DescriptionTask)
        btn_select_image=view.findViewById(R.id.btn_select_image)
        imageLoad=view.findViewById(R.id.imageLoad)
        btn_add_task=view.findViewById(R.id.btn_add_task)
        btn_select_image.setOnClickListener{
            searchimage()
        }
        btn_add_task.setOnClickListener {
            addtask()
        }
    }

    fun searchimage(){
        //intencion de entrar a la galeria interna
        val galleryintent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(galleryintent,pickImage)
    }

    //obtener la imagen y colocarla en el imageview
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==RESULT_OK && requestCode == pickImage){
            imageUri=data?.data //obtiene la imagen
            imageLoad.setImageURI(imageUri) //cargamos la imagen
        }
    }

    fun addtask(){
        val titleTaskInsert=title_task.text.toString().trim()
        val descriptionTaskInsert=description_task.text.toString().trim()
        val userId=activity?.intent?.getIntExtra("UserId",1)
        var showmsg:Int=0

        if (titleTaskInsert.isNotEmpty() && descriptionTaskInsert.isNotEmpty()){
            val admin=DataBase(context,"db",null,1)
            val cursor=admin.writableDatabase
            var register=ContentValues()

            register.put("NameTask",titleTaskInsert)
            register.put("DescriptionTask",descriptionTaskInsert)
            register.put("ImageTask",1)
            register.put("UserTask",userId)

            cursor.insert("task",null,register)

            title_task.setText("")
            description_task.setText("")

            val cursorshowmsg=cursor.rawQuery("SELECT * FROM settings",null)
            if (cursorshowmsg.moveToFirst()){
                showmsg=cursorshowmsg.getInt(0)
            }

            cursor.close()

            if (showmsg==1) {
                Toast.makeText(context, "Tarea agregada", Toast.LENGTH_SHORT).show()
            }
        }else{
            if (showmsg==1) {
                Toast.makeText(context, "Ingresa los datos", Toast.LENGTH_SHORT).show()
            }
            title_task.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(title_task, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}