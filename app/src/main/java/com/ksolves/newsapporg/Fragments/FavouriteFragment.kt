package com.ksolves.newsapporg.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.ksolves.newsapporg.adapters.Adapter
import com.ksolves.newsapporg.models.Article
import com.ksolves.newsapporg.Database.MyDatabase
import com.ksolves.newsapporg.R


class FavouriteFragment : Fragment() {


    lateinit var adapter: Adapter
    lateinit var articless: List<Article>
    private var articles = mutableListOf<Article>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        val myDatabase : MyDatabase
        var fraglistNews = view.findViewById<RecyclerView>(R.id.fraglistNews)
        myDatabase = Room.databaseBuilder(requireContext(),
        MyDatabase::class.java,
            "ArticleDB")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
         articles.addAll(myDatabase.myDao().readAlldata())

        Log.d("Mydebug", "onCreateView: ${articles}" )
        fraglistNews.adapter =  Adapter(requireContext(), articles)
        fraglistNews.layoutManager = LinearLayoutManager(requireContext())

        return view



    }


}