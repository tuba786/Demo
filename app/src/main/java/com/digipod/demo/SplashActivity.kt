package com.digipod.demo

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : AppCompatActivity() {
    private lateinit var atextView: TextView
    private var delay = 200;
    private lateinit var charSequence:CharSequence
    private var index:Int = 0
    private var mHandler =  Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        atextView = findViewById(R.id.atext_View)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        Handler().postDelayed({
//            val intent = Intent(this@SplashActivity, SignUp::class.java)
//            startActivity(intent)
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                // User is signed in
                val i = Intent(this@SplashActivity, SignUp::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                // User is signed out
                val i = Intent(this@SplashActivity, SignUp::class.java)
                startActivity(i)
                Log.d("SIGNOUT", "onAuthStateChanged:signed_out")
            }
            val objectAnimator:ObjectAnimator= ObjectAnimator.ofPropertyValuesHolder(
                atextView,
            ) as ObjectAnimator

//Set duration


//Set repeat mode

            objectAnimator.repeatMode = ValueAnimator.REVERSE
            objectAnimator.start()
            animatText("CliniNet")

            finish()
        }, 6000)
    }
    private var runnable:Runnable= kotlinx.coroutines.Runnable {
        run {

            atextView.text = charSequence.subSequence(0, index++);
//Check condition
            if (index <= charSequence.length) {
                mHandler.postDelayed(runnable, delay.toLong())
            }
        }
    }


    private fun animatText(cs: CharSequence) {
        charSequence=cs
        index=0
        atextView.text = ""
        mHandler.removeCallbacks(runnable)
        mHandler.postDelayed(runnable, delay.toLong())

    }

}