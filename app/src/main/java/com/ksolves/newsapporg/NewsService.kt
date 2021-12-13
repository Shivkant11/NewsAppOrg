package com.ksolves.newsapporg

import com.ksolves.newsapporg.models.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//http://newsapi.org/v2/top-headlines?country=in&apiKey=1625f9f25cf84493969d51e28bdcea16

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "1625f9f25cf84493969d51e28bdcea16"

interface NewsInterface{

    @GET("top-headlines?apiKey=$API_KEY")
    fun getHeadlines(
        @Query("country")country:String
    ):Call<News>

    @GET("top-headlines?apiKey=$API_KEY")
    fun getSports(
        @Query("country") country: String?,
        @Query("category") category: String?
    ): Call<News>

    @GET("top-headlines?apiKey=$API_KEY")
    fun getTechnology(
        @Query("country") country: String?,
        @Query("category") category: String?
    ): Call<News>

    @GET("top-headlines?apiKey=$API_KEY")
    fun getBusiness(
        @Query("country") country: String?,
        @Query("category") category: String?
    ): Call<News>

    @GET("top-headlines?apiKey=$API_KEY")
    fun getHealth(
        @Query("country") country: String?,
        @Query("category") category: String?
    ): Call<News>

    @GET("top-headlines?apiKey=$API_KEY")
    fun getEntertainment(
        @Query("country") country: String?,
        @Query("category") category: String?
    ): Call<News>

    @GET("top-headlines?apiKey=$API_KEY")
    fun getScience(
        @Query("country") country: String?,
        @Query("category") category: String?
    ): Call<News>

    @GET("everything?apiKey=$API_KEY")
    fun getSearchNews(
        @Query("language") language: String?,
        @Query("q") keyword: String?,
    ): Call<News>
}

object NewsService{
    val newsInstances: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstances = retrofit.create(NewsInterface::class.java)
         }
}