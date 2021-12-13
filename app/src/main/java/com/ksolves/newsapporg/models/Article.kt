package com.ksolves.newsapporg.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "Article")
data class Article(
    var author: String?,
    var content: String?,
    var description: String?,
    var publishedAt: String?,
//    val source: List<Source>?,
    @PrimaryKey @NotNull
    var title: String,
    var url: String?,
    var urlToImage: String?,
    var isLiked: Boolean? = false
)