package com.digipod.demo

import android.content.Intent
import android.media.session.MediaController
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digipod.demo.fragments.FragmentHome
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_diet_recommendation.*
import kotlinx.android.synthetic.main.activity_diet_recommendation.btnBack
import retrofit2.http.Url

class DietRecommendation : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_recommendation)

        val weightT = findViewById<EditText>(R.id.gwText)
        val heightT = findViewById<EditText>(R.id.ghText)
        val calBtn = findViewById<Button>(R.id.calBtn)
        recyclerView = findViewById(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        videoAdapter = VideoAdapter()
        recyclerView.adapter = videoAdapter
        articleRecyclerView = findViewById(R.id.articleRecyclerView)
        articleRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        btnBack.setOnClickListener {
            val intent = Intent(this, FragmentHome::class.java)
            startActivity(intent)
        }




        calBtn.setOnClickListener {
            val weight= weightT.text.toString()
            val height = heightT.text.toString()
            if (validInput(weight,height))
            {
                val bmi= weight.toFloat()/((height.toFloat()/100)*(height.toFloat()/100))
                val bmi2D = String.format("%.2f",bmi).toFloat()
                displayResult(bmi2D)
            }
        }
    }


    private fun validInput(weight:String?,height:String?):Boolean{
        return when{
            weight.isNullOrEmpty() ->{
                Toast.makeText(this,"Weight is empty",Toast.LENGTH_SHORT).show()
                return false
            }
            height.isNullOrEmpty() ->{
                Toast.makeText(this,"Height is empty",Toast.LENGTH_SHORT).show()
                return false
            }

            else ->{
                return true
            }

        }
    }



    private fun displayResult(bmi:Float){
        val resultV= findViewById<TextView>(R.id.resultVidw)
        val resultDescV= findViewById<TextView>(R.id.resultDesView)
        val condViewV= findViewById<TextView>(R.id.resultVidwCondition)



        resultV.text=bmi.toString()
        condViewV.text= "(Normal range is 18.5 - 24.9)"

        var resultText= ""
        var color=0
        var articles= listOf<String>()



        when{
            bmi<18.50 ->{
                resultText="Underweight"
                color=R.color.uw
                videoAdapter.setVideos(getVideoList("vid1", "vid2", "vid3"))
                 articles= listOf("https://www.lybrate.com/topic/diet-for-underweight",
                    "https://www.eatingwell.com/article/2060706/healthy-weight-gain-meal-pla",
                "https://www.healthkart.com/connect/2600-calorie-indian-weight-gain-diet-plan/bid-673")

            }
            bmi in 18.50..24.99 ->{
                resultText="Normal"
                color=R.color.nor
                videoAdapter.setVideos(getVideoList("vid4", "vid5", "vid6"))
                 articles= listOf("https://yehaindia.com/best-indian-diet-plan-for-a-healthy-life/",
                "https://thedaily9.in/post/state-of-indian-diets/",
                "https://www.lybrate.com/topic/indian-diet")
            }
            bmi > 25.00 ->{
                resultText="Overweight"
                color=R.color.over
                videoAdapter.setVideos(getVideoList("video7", "video8", "video9"))
                 articles= listOf("https://www.healthifyme.com/blog/best-indian-diet-plan-weight-loss/",
                "https://www.lybrate.com/topic/obesity-diet-chart",
                "https://www.planetayurveda.com/library/diet-plan-for-patients-of-obesity/")
            }


        }
        val articleNames = getArticleNamesForUrls(articles)
        articleAdapter = ArticleAdapter(articleNames, articles)
        resultDescV.setTextColor(ContextCompat.getColor(this,color))
        resultDescV.text= resultText;
        articleRecyclerView.adapter = articleAdapter

    }
    private fun getVideoList(vararg videoNames: String): List<String> {
        return videoNames.map { "android.resource://${packageName}/raw/$it" }
    }
    private fun getArticleNamesForUrls(articleUrls: List<String>): List<String> {
        // Replace this with your logic to extract article names from the article URLs
        // You can use regular expressions or any other parsing technique

        val articleNames = mutableListOf<String>()

        for (articleUrl in articleUrls) {
            val articleName = extractArticleName(articleUrl)
            articleNames.add(articleName)
        }

        return articleNames
    }

    private fun extractArticleName(articleUrl: String): String {
        // Replace this with your logic to extract the article name from the article URL
        // You can use regular expressions or any other parsing technique

        // Example logic: Extract the article name from the URL by splitting it using a delimiter
        val delimiter = "/"
        val urlParts = articleUrl.split(delimiter)
        val articleName = urlParts.last()

        return articleName
    }


}