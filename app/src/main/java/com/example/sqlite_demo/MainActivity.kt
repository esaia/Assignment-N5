package com.example.sqlite_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edName: EditText
    private lateinit var edAuthor: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button


    private lateinit var sqLiteHelper: SQLiteHelper

    private lateinit var  recyclerView: RecyclerView
    private var adapter: BooksAdapter? = null
    private var book:BookModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initRecycleView()


        sqLiteHelper = SQLiteHelper(this)
        btnAdd.setOnClickListener { addBook() }
        btnView.setOnClickListener { getBooks() }
        btnUpdate.setOnClickListener{ updateBook() }

        adapter?.setOnclickItem {
            Toast.makeText(this, "${it.name}", Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edAuthor.setText(it.author)
            book = it

        }
        adapter?.setOnClickDeleteItem {
            deleteBook(it.id)
        }

    }


    private fun getBooks(){
        val booklist = sqLiteHelper.getAllStudent()
        Log.e("ppp", "${booklist.size}")

        adapter?.addItem(booklist)

    }

    private fun addBook (){
        val name = edName.text.toString()
        val author = edAuthor.text.toString()

        if(name.isEmpty() || author.isEmpty()){
            Toast.makeText(this, "Please enter erquired field", Toast.LENGTH_SHORT).show()
        } else{
            val book = BookModel(name= name, author = author)
            val status = sqLiteHelper.insertStudent(book)
            if(status > -1){
                Toast.makeText(this, "Student Added...", Toast.LENGTH_SHORT).show()
                clearEditText()

            }else{
                Toast.makeText(this, "record not saved...", Toast.LENGTH_SHORT).show()

            }

        }
    }

    private fun updateBook(){
        val name = edName.text.toString()
        val author = edAuthor.text.toString()

        if(name == book?.name && author == book?.author){
            Toast.makeText(this, "Record not changed...", Toast.LENGTH_SHORT).show()
            return
        }

        if(book == null) return

        val book = BookModel(id =book!!.id , name = name, author = author )
        val status = sqLiteHelper.updateBook(book)
        if(status > -1){
            clearEditText()
            getBooks()
        }else{
            Toast.makeText(this, "update failed", Toast.LENGTH_SHORT).show()
        }

    }


    private fun deleteBook(id:Int){
        if(id == null) return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("are you sure you want to delete item?")
        builder.setPositiveButton("yes"){dialog, _ ->

            sqLiteHelper.deleteBook(id)
            getBooks()
            dialog.dismiss()

        }
        builder.setNegativeButton("no"){dialog, _ -> dialog.dismiss()}
        val alert = builder.create()
        alert.show()

    }


    private fun clearEditText (){
        edName.setText("")
        edAuthor.setText("")
        edName.requestFocus()
    }

    private fun initRecycleView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BooksAdapter()
        recyclerView.adapter = adapter
    }



    private fun init(){
        edName = findViewById(R.id.name)
        edAuthor = findViewById(R.id.author)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recycleView)
    }



}