package com.example.sqlite_demo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.BookViewHolder>() {
    private var booksList: ArrayList<BookModel> = ArrayList()
    private var onClickItem: ((BookModel) -> Unit)? = null
    private var onclickDeleteItem: ((BookModel) -> Unit)? = null

    fun addItem(item: ArrayList<BookModel>){
        this.booksList = item
        notifyDataSetChanged()
    }

    fun setOnclickItem(callback: (BookModel)-> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (BookModel) -> Unit){
        this.onclickDeleteItem = callback
    }

    override fun getItemCount(): Int {
       return booksList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false
        )
    )


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = booksList[position]
        holder.bindView(book)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(book)}
        holder.btnDelete.setOnClickListener{ onclickDeleteItem?.invoke(book) }
    }



    class BookViewHolder(var view: View) : RecyclerView.ViewHolder(view){
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var author = view.findViewById<TextView>(R.id.tvAuthor)
         var btnDelete = view.findViewById<TextView>(R.id.btnDelete)


        fun bindView(books: BookModel){
            name.text = books.name
            author.text = books.author
        }
    }


}