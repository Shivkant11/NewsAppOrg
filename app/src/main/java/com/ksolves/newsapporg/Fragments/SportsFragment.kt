package com.ksolves.newsapporg.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksolves.newsapporg.adapters.Adapter
import com.ksolves.newsapporg.models.Article
import com.ksolves.newsapporg.models.News
import com.ksolves.newsapporg.NewsService
import com.ksolves.newsapporg.R
import retrofit2.Call
import retrofit2.Response


class SportsFragment : Fragment() {

    lateinit var adapter: Adapter
    private var articles = mutableListOf<Article>()
    lateinit var fraglistNews : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        fraglistNews = view.findViewById(R.id.fraglistNews)
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = Adapter(requireContext(),articles)
        fraglistNews.adapter = adapter
        fraglistNews.layoutManager = layoutManager
        getNews()
        return view
    }

    private fun getNews(){
        val news = NewsService.newsInstances.getSports("in","sports")
        news.enqueue(object :retrofit2.Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Error","Error",t)
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if(news!= null){
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }
            }
        })
    }
}