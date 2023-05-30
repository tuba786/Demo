package com.digipod.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_account.*
import android.text.method.PasswordTransformationMethod

import android.text.method.HideReturnsTransformationMethod
import android.view.WindowManager
import android.widget.*
import com.digipod.demo.Model.User
import com.digipod.demo.fragments.FragmentHome
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class CreateAccount : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etEmail: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var radioButtonMale: RadioButton
    private lateinit var radioButtonFemale: RadioButton
    private lateinit var radioButtonOthers: RadioButton

    private lateinit var btnCreateAccount: Button

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize views
        etName = findViewById(R.id.etNameCreateAccount)
        etAge = findViewById(R.id.etAge)
        etEmail = findViewById(R.id.etEmailCreateAccount)
        // Inside onCreate() method, after initializing other views
        radioGroupGender = findViewById(R.id.radioGroupSelectGender)
        radioButtonMale = findViewById(R.id.rbMale)
        radioButtonFemale = findViewById(R.id.rbFemale)
        radioButtonOthers = findViewById(R.id.rbOthers)

        btnCreateAccount = findViewById(R.id.btnCreate)

        // Handle create account button click
        btnCreateAccount.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val name = etName.text.toString()
        val age = etAge.text.toString()
        val email = etEmail.text.toString()
        val gender = when (radioGroupGender.checkedRadioButtonId) {
            R.id.rbMale -> "Male"
            R.id.rbFemale -> "Female"
            R.id.rbOthers -> "Others"
            else -> "Select a radio button"// Handle the default case if no radio button is selected
        }

        // Validate user input (add your own validation logic as per your requirements)

        // Create a user data object
        val userData = hashMapOf(
            "name" to name,
            "age" to age,
            "email" to email,
            "gender" to gender
        )

        // Store user data in Firestore
        firestore.collection("users")
            .add(userData)
            .addOnSuccessListener {
                    documentReference ->
                val userId = documentReference.id

                // Start the other activity and pass the name as an extra
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                // Inside createAccount() method after startActivity(intent)

            }
            .addOnFailureListener { e ->
                // Failed to store user data in Firestore
                // Handle the error appropriately
                Toast.makeText(this, "Failed to create account", Toast.LENGTH_SHORT).show()
            }
    }
}

