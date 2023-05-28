package com.digipod.demo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.digipod.demo.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btn: Button = findViewById(R.id.btnCheckout)
        val amt: EditText = findViewById(R.id.totalAmt)
        btn.setOnClickListener {
            Intent(this, CheckoutActivity::class.java).also {
                it.putExtra("amount", amt.text.toString().toFloat())
                startActivity(it)
            }
        }
    }



    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
        return true
    }
}