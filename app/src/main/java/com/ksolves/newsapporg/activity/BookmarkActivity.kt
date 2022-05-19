package com.ksolves.newsapporg.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.ksolves.newsapporg.R
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File

class BookmarkActivity : AppCompatActivity() {
    lateinit var progressBar : ProgressBar
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        floatingActionButton.visibility = View.GONE
        progressBar = findViewById(R.id.progressBar)
        startActivityForResult(Intent(this,SavedPages::class.java), FILE_OPEN_REQUEST)
            detailWebView.settings.javaScriptEnabled = true
            detailWebView.settings.allowFileAccess = true
            detailWebView.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                    detailWebView.visibility = View.VISIBLE
                }
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== FILE_OPEN_REQUEST && data!=null && data.hasExtra(FILE_DATA)){
            val filePath = data.getStringExtra(FILE_DATA) ?: return
            detailWebView.loadUrl("file://"+ File(filePath).path)
        }
    }
    override fun onBackPressed() {
        if (detailWebView.canGoBack()){
            detailWebView.goBack()
        }else{
            super.onBackPressed()
        }
    }
    companion object{
        const val FILE_OPEN_REQUEST = 23422
        const val FILE_DATA = "file_open_path"
    }
}


