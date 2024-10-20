package com.example.mrxd

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class mrdx : AppCompatActivity() {

    lateinit var text_act1:TextView
    lateinit var rb_1: RadioButton
    lateinit var rb_2: RadioButton
    lateinit var rb_3: RadioButton
    lateinit var ch_1: CheckBox
    lateinit var ch_2: CheckBox
    lateinit var btn: Button
    lateinit var sp_op:Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mrdx)

        rb_1=findViewById(R.id.rb1)
        rb_2=findViewById(R.id.rb2)
        rb_3=findViewById(R.id.rb3)

        ch_1=findViewById(R.id.ch1)
        ch_2=findViewById(R.id.ch2)

        btn=findViewById(R.id.button)

        sp_op=findViewById(R.id.spcolor)

        val lista_colors= arrayOf("rojo","verde","azul")
        val adapter_color=ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lista_colors)
        sp_op.adapter=adapter_color

        var msg_act_1=intent.getStringExtra("msgsd")
        text_act1=findViewById(R.id.txt_act_1)
        text_act1.setText(msg_act_1)
    }

    fun applicar(view:View){
        if (rb_1.isChecked){
            text_act1.setTextColor(Color.MAGENTA)
            Toast.makeText(this,"no se",Toast.LENGTH_SHORT).show()
        }
        if (rb_2.isChecked){
            text_act1.setTextColor(Color.CYAN)
            Toast.makeText(this,"no si",Toast.LENGTH_SHORT).show()
        }
        if (rb_3.isChecked){
            text_act1.setTextColor(Color.YELLOW)
            Toast.makeText(this,"no no no",Toast.LENGTH_SHORT).show()
        }

        if (ch_1.isChecked){
            btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#aa0000"))
        }else{
            btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#aaaaaa"))
        }

        if (ch_2.isChecked){
            rb_1.setBackgroundColor(Color.CYAN)
        }else{
            rb_1.setBackgroundColor(Color.TRANSPARENT)
        }

        if(sp_op.selectedItem.toString()=="rojo"){
            btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff0000"))
        }else if(sp_op.selectedItem.toString()=="verde"){
            btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00ff00"))
        }else if(sp_op.selectedItem.toString()=="azul"){
            btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0000ff"))
        }
    }
}