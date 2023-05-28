package com.digipod.demo

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.digipod.demo.databinding.ActivityCheckoutBinding
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class CheckoutActivity : AppCompatActivity() {

    private lateinit var paymentSheet: PaymentSheet
    private lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    private lateinit var paymentIntentClientSecret: String
    private lateinit var binding: ActivityCheckoutBinding
    private var slot: String? = null
    private var type: String = "instant"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        paymentIntentClientSecret = getString(R.string.pk)
        val amount = intent.getFloatExtra("amount", 0f)
        if (intent.hasExtra("slot")) {
            slot = intent.getStringExtra("slot")
            type = "slot"
        }
        if (intent.hasExtra("instant")) {
            type = "instant"
        }
        if (intent.hasExtra("purchase")) {
            type = "purchase"
        }

        binding.tvTotalAmount.text = amount.toString()

        binding.button.setOnClickListener {
            presentPaymentSheet()
        }
        binding.button.isEnabled = false
        validateFromServer(amount)
    }

    private fun validateFromServer(amount: Float) {
        val url = getString(R.string.api_url)
        val params = listOf(
            "amount" to amount * 100,
            "currency" to "inr",
            "email" to "dummyuser@gmail.com",
            "name" to "Dummy User",
        )
        url.httpPost(
            params
        ).responseJson { req, res, result ->
            Log.d("Request", req.toString())
            Log.d("Response", res.toString())
            Log.d("Result", result.toString())
            if (result is Result.Success) {
                val responseJson = result.get().obj()
                paymentIntentClientSecret = responseJson.getString("paymentIntent")
                val customerId = responseJson.getString("customer")
                val ephemeralKeySecret = responseJson.getString("ephemeralKey")
                customerConfig = PaymentSheet.CustomerConfiguration(customerId, ephemeralKeySecret)
                val publishableKey = responseJson.getString("publishableKey")
                PaymentConfiguration.init(this, publishableKey)
                presentPaymentSheet()
                binding.button.isEnabled = true
            } else {
                showAlert("Error validating from server, make sure server is running on the network and update the API URL in strings.xml")
            }
        }

    }

    private fun presentPaymentSheet() {

        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret, PaymentSheet.Configuration(
                merchantDisplayName = "My Business Merch",
                customer = customerConfig,
                allowsDelayedPaymentMethods = true
            )
        )
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                showAlert("Payment cancelled")
            }
            is PaymentSheetResult.Failed -> {
                showAlert("Payment failed ${paymentSheetResult.error.message}")
            }
            is PaymentSheetResult.Completed -> {
                when (type) {
                    "slot" -> showAlert("Slot is Booked")
                    "instant" -> makeCall()
                    "purchase" -> {
                        showAlert("You Payment is complete")
                        val uid = Firebase.auth.currentUser?.uid
                        val db = Firebase.firestore
                        uid?.let { db.collection("carts").document(it).delete() }
                    }
                }
//                showAlert("Payment completed successfully")
            }
        }
    }

    private fun makeCall() {
        val mimeString = "vnd.android.cursor.item/vnd.com.whatsapp.voip.call"

        val resolver: ContentResolver = this.contentResolver
        val cursor: Cursor? = resolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME
        )

        while (cursor!!.moveToNext()) {
            var Col_Index = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
            val _id = cursor.getLong(Col_Index)

            Col_Index = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            var number = cursor.getString(Col_Index)

            Col_Index =
                cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val displayName = cursor.getString(Col_Index)

            Col_Index = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.MIMETYPE)
            val mimeType = cursor.getString(Col_Index)
            // println("Data: " + _id.toString() + " ---" + displayName + "---" + number + "---" + mimeType )
            val numbers = listOf("+917518139985", "+917754934110", "+919580763025")
            var my_number = numbers.random()
            my_number = my_number.replace(" ", "")
            my_number = my_number.replace("+", "")


            if (!number.isNullOrBlank()) {
                // println("Number : " + number )
                number = number.replace(" ", "")
                number = number.replace("+", "")

                // my_number.substring(1)
                // println(">>" + my_number)
                if (number.endsWith(my_number.substring(1) + "@s.whatsapp.net")) {
                    if (mimeType.equals(mimeString)) {
                        val data = "content://com.android.contacts/data/$_id"
                        val sendIntent = Intent()
                        sendIntent.action = Intent.ACTION_VIEW
                        sendIntent.setDataAndType(Uri.parse(data), mimeString)
                        sendIntent.setPackage("com.whatsapp")
                        if (checkAndRequestPermissions()) {
                            startActivity(sendIntent)
                        }
                        break;
                    }
                }
            }
        }
    }

    fun checkAndRequestPermissions(): Boolean {
        val call =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
        val listPermissionsNeeded = ArrayList<String>()
        if (call != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                1
            )
            return false
        }
        return true
    }

    fun showAlert(message: String) {
        AlertDialog.Builder(this).setTitle("Alert").setMessage(message)
            .setPositiveButton("OK", null).show()
    }

}