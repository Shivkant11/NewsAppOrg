package com.ksolves.newsapporg.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.ksolves.newsapporg.Database.MyDatabase
import com.ksolves.newsapporg.R
import com.ksolves.newsapporg.activity.DetailActivity
import com.ksolves.newsapporg.models.Article


class Adapter(private val context: Context, val article: List<Article>): RecyclerView.Adapter<Adapter.ArticleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate((R.layout.item_layout),parent,false)
        return ArticleViewHolder(view)
    }
    override fun getItemCount(): Int {
        return article.size
    }
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = article[position]

        holder.title.text = article.title
        holder.description.text = article.description
        Glide.with(context).load(article.urlToImage).placeholder(R.drawable.ic_error).into(holder.image)
        holder.itemView.setOnClickListener{
            Toast.makeText(context,article.title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("URL",article.url)

            context.startActivity(intent)
        }

        holder.shareButton.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "${article.title} \n \n ${article.url}")
            shareIntent.type = "text/plain"
           //shareIntent.setPackage("com.whatsapp")
            context.startActivity(Intent.createChooser(shareIntent, "share to"))
            //context.startActivity(shareIntent)

        }

        holder.button.setOnClickListener {
            article.isLiked = true
            val myDatabase : MyDatabase = Room.databaseBuilder(
                 context,
                 MyDatabase::class.java,
                 "ArticleDB")
                 .fallbackToDestructiveMigration()
                 .allowMainThreadQueries()
                 .build()
            myDatabase.myDao().addArticle(article)

            Toast.makeText(context, "Saved to Like Category",Toast.LENGTH_LONG).show()
            notifyDataSetChanged()
        }
        if (article.isLiked == true){
            holder.button.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            holder.button.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var image = itemView.findViewById<ImageView>(R.id.image)!!
        var title = itemView.findViewById<TextView>(R.id.title)!!
        var description = itemView.findViewById<TextView>(R.id.description)!!
        var button : ImageButton = itemView.findViewById(R.id.favButton)
        var shareButton : ImageButton = itemView.findViewById(R.id.shareButton)
    }
}



