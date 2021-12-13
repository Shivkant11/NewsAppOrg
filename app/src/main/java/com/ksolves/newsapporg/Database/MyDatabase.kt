package com.ksolves.newsapporg.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ksolves.newsapporg.Database.MyDao
import com.ksolves.newsapporg.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun myDao(): MyDao

//    companion object {
//        private var instance: MyDatabase? = null
//
//        @Synchronized
//        fun getDatabase(context: Context): MyDatabase {
//            if (instance == null)
//                instance = Room.databaseBuilder(
//                    context.applicationContext, MyDatabase::class.java,
//                    "My_database"
//                )
//                    .build()
//
//            return instance!!
//
//        }
//
//
//    }
}