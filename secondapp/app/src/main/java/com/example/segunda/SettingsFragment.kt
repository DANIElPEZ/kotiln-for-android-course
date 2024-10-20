package com.example.segunda

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.Switch
import android.widget.ToggleButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : Fragment() {
    var param1: String? = null
    var param2: String? = null

    lateinit var setting_switch:Switch
    lateinit var setting_togglebtn_background:ToggleButton
    lateinit var setting_btn_about:Button
    lateinit var setting_btn_direction:Button
    lateinit var setting_btn_save:Button

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
        val settingsview=inflater.inflate(R.layout.fragment_settings, container, false)
        findelements(settingsview)
        return settingsview
    }

    fun findelements(view: View){

        var admins=DataBase(context,"db",null,1)
        var dbs = admins.readableDatabase
        val cursorshowmsg=dbs.rawQuery("SELECT * FROM settings",null)

        var msgOpc: Int=0
        var backOpc: Int=0

        if (cursorshowmsg.moveToFirst()){
            msgOpc = cursorshowmsg.getInt(0)
            backOpc= cursorshowmsg.getInt(1)
        }

        dbs.close()

        setting_switch=view.findViewById(R.id.settings_show_messages)
        setting_togglebtn_background=view.findViewById(R.id.settings_show_background)
        setting_btn_about=view.findViewById(R.id.btn_about)
        setting_btn_direction=view.findViewById(R.id.btn_direction)
        setting_btn_save=view.findViewById(R.id.btn_save_settings)

        setting_btn_direction.setOnClickListener {
            parentFragmentManager.commit{
                replace<fragment_direction>(R.id.fragmentContainerView2)
                setReorderingAllowed(true)
                addToBackStack("principal")
            }
        }

        if (msgOpc==1){
            setting_switch.setChecked(true)
        }else{
            setting_switch.setChecked(false)
        }

        if (backOpc==1){
            setting_togglebtn_background.setChecked(true)
        }else{
            setting_togglebtn_background.setChecked(false)
        }

        setting_btn_about.setOnClickListener {
            val Webfd=view.findViewById<WebView>(R.id.Webfd)
            Webfd.loadUrl("https://danidev.reflex.run/")
        }

        setting_btn_save.setOnClickListener {
            saveInosettings()
        }

        val thumbStates = arrayOf(
            intArrayOf(android.R.attr.state_checked), // Estado activado
            intArrayOf(-android.R.attr.state_checked) // Estado desactivado
        )
        val thumbColors = intArrayOf(
            Color.parseColor("#5CD2FF"), // azul cuando está activado
            Color.parseColor("#B7EBFF") // Color especificado cuando está desactivado
        )
        val thumbColorStateList = ColorStateList(thumbStates, thumbColors)
        setting_switch.thumbTintList = thumbColorStateList

        // Define los colores para la pista en diferentes estados
        val trackStates = arrayOf(
            intArrayOf(android.R.attr.state_checked), // Estado activado
            intArrayOf(-android.R.attr.state_checked) // Estado desactivado
        )
        val trackColors = intArrayOf(
            Color.parseColor("#000000"), // negro cuando está activado
            Color.parseColor("#000000") // Color especificado cuando está desactivado
        )
        val trackColorStateList = ColorStateList(trackStates, trackColors)
        setting_switch.trackTintList = trackColorStateList
    }

    fun saveInosettings(){
        val message:Boolean=setting_switch.isChecked
        val background:Boolean=setting_togglebtn_background.isChecked

        val msgOpc: Int = if (message) 1 else 0
        val backOpc: Int = if (background) 1 else 0

        var admins=DataBase(context,"db",null,1)
        var dbs = admins.writableDatabase
        var sqlupdate="UPDATE settings SET showMessages=$msgOpc, showBackground=$backOpc"

        dbs.execSQL(sqlupdate)

        dbs.close()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}