package com.ksolves.newsapporg.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksolves.newsapporg.adapters.Adapter
import com.ksolves.newsapporg.models.Article
import com.ksolves.newsapporg.models.News
import com.ksolves.newsapporg.NewsService
import com.ksolves.newsapporg.R
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Response


class SearchFragment : Fragment() {

    lateinit var adapter: Adapter
    private var articles = mutableListOf<Article>()
    lateinit var fraglistNews : RecyclerView
    lateinit var imageClear : ImageView
    lateinit var etSearchView : EditText
    lateinit var linearNews : LinearLayout
    var strKeywords: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_search, container, false)
        fraglistNews = view.findViewById<RecyclerView>(R.id.rvListNews) as RecyclerView
        imageClear = view.findViewById<ImageView>(R.id.imageClear) as ImageView
        etSearchView = view.findViewById<EditText>(R.id.etSearchView) as EditText
        linearNews = view.findViewById(R.id.linearNews)
        val layoutManager = LinearLayoutManager(requireContext())
        fraglistNews.layoutManager = layoutManager
        imageClear.setVisibility(View.GONE)
        linearNews.setVisibility(View.GONE)

        imageClear.setOnClickListener {
            etSearchView.getText().clear()
            articles.clear()
            linearNews.setVisibility(View.GONE)
            imageClear.setVisibility(View.GONE)
        }

        //action search
        etSearchView.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                strKeywords = etSearchView.getText().toString()
                if (strKeywords.isEmpty()) {
                    Toast.makeText(context, "Form tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                } else {
                    getSearchNews(strKeywords)
                }
                val inputManager =
                    v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(v.windowToken, 0)
                return@OnEditorActionListener true
            }
            false
        })
        return view
    }

    private fun getSearchNews(strKeywords: String){
        articles.clear()
        val news = NewsService.newsInstances.getSearchNews("en",strKeywords)
        news.enqueue(object :retrofit2.Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Error","Error",t)
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if(news!= null){
                    articles.addAll(news.articles)
                    adapter = Adapter(requireContext(),articles)
                    fraglistNews.adapter = adapter
                    adapter.notifyDataSetChanged()
                    linearNews.visibility = View.VISIBLE
                    imageClear.visibility = View.VISIBLE

                }
            }
        })
    }
}