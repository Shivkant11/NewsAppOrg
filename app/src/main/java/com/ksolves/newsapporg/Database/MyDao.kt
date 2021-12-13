package com.ksolves.newsapporg.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksolves.newsapporg.models.Article

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun addArticle(article: Article)

    @Query("select distinct * FROM Article ")
     fun readAlldata() : List<Article>
}