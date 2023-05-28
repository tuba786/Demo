package com.digipod.demo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(private val articleNames: List<String>,private val articles: List<String>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articleUrl = articles[position]
        val articleName = articleNames[position]
        holder.bind(articleName,articleUrl)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val webView: WebView = itemView.findViewById(R.id.webView)
        private val articleTitleTextView: TextView =
            itemView.findViewById(R.id.articleTitleTextView)

        @SuppressLint("SetJavaScriptEnabled")
        fun bind(articleName:String,articleUrl: String) {
            articleTitleTextView.text = articleName
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(articleUrl)
        }
    }
}
