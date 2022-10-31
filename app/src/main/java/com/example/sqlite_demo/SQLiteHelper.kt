package com.example.sqlite_demo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Books.db"
        private const val TBL_BOOK = "tbl_cars"
        private const val ID = "id"
        private const val NAME = "name"
        private const val AUTHOR = "Author"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTBLStudent = ("CREATE TABLE " + TBL_BOOK + "("
                + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, "
                + AUTHOR + " Text" + ")" )
        p0?.execSQL(createTBLStudent)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TBL_BOOK")
        onCreate(p0)
    }

    fun insertStudent(bk: BookModel): Long{
        val db = this.writableDatabase
        val contentValues =  ContentValues()
        contentValues.put(ID, bk.id)
        contentValues.put(NAME, bk.name)
        contentValues.put(AUTHOR, bk.author)

        val success = db.insert(TBL_BOOK, null, contentValues)
        db.close()
        return success
    }



    fun getAllStudent(): ArrayList<BookModel>{
        val bkList: ArrayList<BookModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_BOOK"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }


        var id:Int
        var name:String
        var author:String


        if(cursor.moveToFirst()){

            do{
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                author = cursor.getString(cursor.getColumnIndexOrThrow("Author"))

                val books = BookModel(id = id, name = name, author = author)
                bkList.add(books)

            }while (cursor.moveToNext())


        }

        return bkList

    }

    fun updateBook(book: BookModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, book.id)
        contentValues.put(NAME, book.name)
        contentValues.put(AUTHOR, book.author)

        val success = db.update(TBL_BOOK, contentValues, "id=${book.id}", null)
        db.close()
        return success
    }


    fun deleteBook(id: Int):Int{
       val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID , id)
        val success = db.delete(TBL_BOOK, "id=$id", null)
        db.close()
        return success

    }
}