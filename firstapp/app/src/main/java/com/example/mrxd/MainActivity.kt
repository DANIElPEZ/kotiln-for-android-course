package com.example.mrxd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mrxd.mrdx_lol

class MainActivity : AppCompatActivity() {

    lateinit var txt_one:TextView
    lateinit var input_txt:EditText
    lateinit var list_view:ListView
    lateinit var switch:Switch
    lateinit var progress_Bar: ProgressBar
    lateinit var lista_countries:Array<String>

    //ciclo de vida de app
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Toast.makeText(this,"Creado",Toast.LENGTH_SHORT).show()

        txt_one=findViewById(R.id.txt_main)
        input_txt=findViewById(R.id.txt_input)

        progress_Bar=findViewById(R.id.progressBar)

        list_view=findViewById(R.id.countries)
        lista_countries= arrayOf("Chile","Colombia","Peru","España","Ecuador","Peru","Argentina","Bolivia","Panama")
        val adater_countries=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lista_countries)
        list_view.adapter=adater_countries
        //listview event
        list_view.setOnItemClickListener{ adapterView ,view, position, id->
            onItemClick(position)
        }

        switch=findViewById(R.id.switch_1)
        switch.setOnCheckedChangeListener({_,ischecked ->
            loading(ischecked)
        })
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this,"Empezando",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this,"continuando",Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,"Pausado",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this,"parando",Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this,"reiniciando",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"Cerrando",Toast.LENGTH_SHORT).show()
    }

    //listview function
    fun onItemClick(position: Int) {
        // Acción a realizar cuando se hace clic en un elemento
        val item = lista_countries[position]
        txt_one.setText(item.toString())
    }

    //funcion switch
    fun loading(isloading: Boolean){
        progress_Bar.isIndeterminate=isloading
        progress_Bar.visibility=if(isloading) View.VISIBLE else View.GONE
    }

    //funcion para boton
    var op=0
    fun changecolor(view: View){
        if (op==0) {
            txt_one.setTextColor(android.graphics.Color.GREEN)
            op=1
        }else{
            txt_one.setTextColor(android.graphics.Color.RED)
            op=0
        }
        txt_one.setText(input_txt.text)

    }

    //funcion imageButon
    fun tool(view:View){
        Toast.makeText(this,"mr xd",Toast.LENGTH_SHORT).show()
        var intent2=Intent(this,mrdx_lol::class.java)
        startActivity(intent2)
    }

    //intent
    fun goto_activity(view: View){
        var sendtext=input_txt.text.toString()
        if (sendtext != ""){
            //intent
            var intent=Intent(this,mrdx::class.java).apply {
                putExtra("msgsd",sendtext) //send to second activity
            }
            startActivity(intent)
        }else{
            Toast.makeText(this,"ponga un texto",Toast.LENGTH_SHORT).show()
            input_txt.requestFocus()
            var imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(input_txt,InputMethodManager.SHOW_IMPLICIT)
        }
    }
}