package com.example.segunda

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateUsertable = """
            CREATE TABLE users (
                UserId INTEGER PRIMARY KEY AUTOINCREMENT,
                Name TEXT,
                Password TEXT
            )
         """.trimIndent()
        val sqlCreateTaskTable =  """CREATE TABLE task(
                TaskId INTEGER PRIMARY KEY AUTOINCREMENT,
                NameTask TEXT,
                DescriptionTask TEXT,
                ImageTask INTEGER,
                UserTask INTEGER
            )
        """.trimIndent()
        val sqlCreateSettingTable =  """CREATE TABLE settings(
                showMessages INTEGER,
                showBackground INTEGER
            )
        """.trimIndent()

        db?.execSQL(sqlCreateUsertable)
        db?.execSQL(sqlCreateTaskTable)
        db?.execSQL(sqlCreateSettingTable)

        db?.execSQL("INSERT INTO settings (showMessages,showBackground) VALUES (0,1)")

        var sqlInsertUser="""
            INSERT INTO users (Name,Password) VALUES ('Juan', '123')
        """.trimIndent()
        var sqlInsertTask="""
            INSERT INTO task (NameTask,Descriptiontask,ImageTask,UserTask) VALUES ('Pagar', 'Recuerda pagar platzi.', 4, 1)
        """.trimIndent()

        db?.execSQL(sqlInsertUser)
        db?.execSQL(sqlInsertTask)

        sqlInsertUser="""
            INSERT INTO users (Name,Password) VALUES ('Miguel', '123')
        """.trimIndent()
        db?.execSQL(sqlInsertUser)

        sqlInsertTask="""
            INSERT INTO task (NameTask,Descriptiontask,ImageTask,UserTask) VALUES ('Llamar', 'Lama al cliente Carlos, que necesita modificar.', 4, 2)
        """.trimIndent()
        db?.execSQL(sqlInsertTask)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}