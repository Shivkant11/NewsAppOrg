package com.ksolves.newsapporg.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.*
import com.ksolves.newsapporg.R
import com.ksolves.newsapporg.activity.MainActivity

const val shortcut_like_id = "id_like"
const val shortcut_search_id = "id_search"

@RequiresApi(Build.VERSION_CODES.N_MR1)
object Shortcuts {

    fun setUp(context: Context) {
        val shortcutManager =
            getSystemService(context, ShortcutManager::class.java)

        //Create an array of intents to create a more fluent user experience in the back stack
        val intentA = arrayOf(
            Intent("FAV", null, context, MainActivity::class.java)
        )
        val intentB = arrayOf(
            Intent("SEARCH", null, context, MainActivity::class.java)
        )


//        val shortcut = ShortcutInfo.Builder(context, shortcut_like_id)
//            .setShortLabel("Favourites")
//            .setLongLabel("Open Liked News")
//            .setIcon(Icon.createWithResource(context, R.drawable.ic_favorite_black_24dp))
//            .setIntents(intentA)
//            .build()



        val shortcut2 = ShortcutInfo.Builder(context, shortcut_search_id)
            .setShortLabel("Search")
            .setLongLabel("Search News")
            .setIcon(Icon.createWithResource(context, R.drawable.ic_search))
            .setIntents(intentB)
            .build()


//        shortcutManager!!.dynamicShortcuts = listOf(shortcut, shortcut2)
        shortcutManager!!.dynamicShortcuts = listOf( shortcut2)
    }

}