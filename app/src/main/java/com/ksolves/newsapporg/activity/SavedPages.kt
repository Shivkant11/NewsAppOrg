package com.ksolves.newsapporg.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ksolves.newsapporg.R
import kotlinx.android.synthetic.main.activity_saved_pages.*
import java.io.File

class SavedPages : AppCompatActivity() {

    private lateinit var fileProcessor : LoadFilesFromDisk
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_pages)

    }

    override fun onStart() {
        super.onStart()
        fileProcessor = LoadFilesFromDisk()
        fileProcessor.execute()
    }

    override fun onStop() {
        super.onStop()
        fileProcessor.cancel(true)
    }


    @SuppressLint("StaticFieldLeak")
    inner class LoadFilesFromDisk : AsyncTask<Void,Int,List<File>>(){
        override fun onPreExecute() {
            super.onPreExecute()
            progressLayout.visibility = View.VISIBLE
        }

        override fun onPostExecute(result: List<File>) {
            super.onPostExecute(result)
            progressLayout.visibility = View.GONE
            setUpFileList(result)
        }

        override fun doInBackground(vararg params: Void?): List<File> {
            val savedPagesFolder = File(filesDir.absolutePath  )
            val fileList = savedPagesFolder.listFiles()
            return fileList?.toList() ?: emptyList()
        }
    }

    private fun setUpFileList(result: List<File>) {
        val fileList = mutableListOf<String>()
        result.forEach { fileList.add(it.name) }
        filesList.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,fileList)
        filesList.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = Intent()
                intent.putExtra( BookmarkActivity.FILE_DATA , result[position].path)
                setResult( BookmarkActivity.FILE_OPEN_REQUEST , intent )
                finish()
            }
    }
}
