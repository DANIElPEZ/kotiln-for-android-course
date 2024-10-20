package com.example.segunda

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    lateinit var user:EditText
    lateinit var password:EditText
    lateinit var admin: DataBase
    lateinit var db: SQLiteDatabase
    lateinit var cursorshowmsg: Cursor
    var showmsg:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        user=findViewById(R.id.user)
        password=findViewById(R.id.password)

        admin=DataBase(this,"db",null,1)
        db=admin.readableDatabase
        cursorshowmsg=db.rawQuery("SELECT * FROM settings",null)

        var fondo:Int=0

        if (cursorshowmsg.moveToFirst()){
            showmsg=cursorshowmsg.getInt(0)
            fondo=cursorshowmsg.getInt(1)
        }

        var setfondo:View=findViewById(R.id.main)
        if(fondo==0){
            setfondo.setBackgroundColor(Color.rgb(98,129,85))
        }
    }

    override fun onBackPressed() {}

    fun login(view:View){
        var user_txt=user.text.toString().trim()
        var password_txt=password.text.toString().trim()

        var nombre_check= ""
        var password_check = ""
        var iduser=0

        if(user_txt.isNotEmpty() && password_txt.isNotEmpty()){

            val cursor = db.rawQuery(
                "SELECT * FROM users WHERE Name = ? AND Password = ?",
                arrayOf(user_txt, password_txt)
            )

            if (cursor.moveToFirst()){
                iduser=cursor.getInt(0)
                nombre_check = cursor.getString(1)
                password_check = cursor.getString(2)
            }

            if (user_txt==nombre_check && password_txt==password_check){
                if (showmsg==1){
                    Toast.makeText(this,"Iniciaste sesion",Toast.LENGTH_SHORT).show()
                }

                var panel=Intent(this,PanelActivity::class.java).apply {
                    putExtra("UserName",user_txt)
                    putExtra("UserId",iduser)
                }
                startActivity(panel)

            }else{
                if (showmsg==1) {
                    Toast.makeText(this, "Datos incorrectos, intente nuevamente", Toast.LENGTH_SHORT).show()
                }

                user.requestFocus()
                var imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(user,InputMethodManager.SHOW_IMPLICIT)
            }

        }else{
            if (showmsg==1) {
                Toast.makeText(this, "Ingresa tus datos", Toast.LENGTH_SHORT).show()
            }
            user.requestFocus()
            var imm=getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(user,InputMethodManager.SHOW_IMPLICIT)
        }
        db.close()
    }
}