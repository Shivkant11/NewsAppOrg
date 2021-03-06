package com.ksolves.newsapporg.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import com.ksolves.newsapporg.R
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File


class DetailActivity : AppCompatActivity() {
    lateinit var progressBar : ProgressBar
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        progressBar = findViewById(R.id.progressBar)
        val url = intent.getStringExtra("URL")
        if (url != null){
            detailWebView.settings.javaScriptEnabled = true
            detailWebView.settings.allowFileAccess = true
            detailWebView.webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                    detailWebView.visibility = View.VISIBLE
                }
            }
            detailWebView.loadUrl(url)
        }
        floatingActionButton.setOnClickListener {
            detailWebView.saveWebArchive( filesDir.absolutePath  + File.separator + detailWebView.title + ".mhtml", false ){
                val message = if (it!=null) "Page saved" else "Failed to save page"
                Toast.makeText(this, message,Toast.LENGTH_LONG).show()
            }
            floatingActionButton.visibility = View.GONE

        }
    }
}