package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauth.Model.User
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Registration : AppCompatActivity() {

    private var emailTV: EditText? = null
    private  var passwordTV:EditText? = null
    private  var nameTv:EditText? = null
    private  var phoneTv:EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var spinner: Spinner? = null
    private var mAuth1: FirebaseAuth? = null
    private var mAuth2: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mAuth1 = FirebaseAuth.getInstance();

        val firebaseOptions = FirebaseOptions.Builder()
            .setDatabaseUrl("https://psikotalk-86cd2.firebaseio.com")
            .setApiKey("AIzaSyDFzh3eB23KRhQ18bs08HFRyozuSSMVu-g")
            .setApplicationId("psikotalk-86cd2").build()
        mAuth2 = try {
            val myApp = FirebaseApp.initializeApp(applicationContext, firebaseOptions, "AnyAppName")
            FirebaseAuth.getInstance(myApp)
        } catch (e: IllegalStateException) {
            FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"))
        }

        initializeUI();

        ArrayAdapter.createFromResource(this,R.array.role_array,android.R.layout.simple_spinner_item).also {
                arrayAdapter ->  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner?.adapter = arrayAdapter
        }
    }

    private fun initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        nameTv = findViewById(R.id.nama)
        phoneTv = findViewById(R.id.phone)
        progressBar = findViewById(R.id.progressBar);
        spinner = findViewById(R.id.spinnerRole);
    }

    fun register(view: View) {
        progressBar?.setVisibility(View.VISIBLE);
        val email: String
        val password: String
        email = emailTV!!.text.toString()
        password = passwordTV!!.text.toString()
        val name = nameTv?.text.toString()
        val phone = phoneTv?.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth2!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var user = User(name,email,phone,spinner?.selectedItem.toString())
                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(user)
                    Toast.makeText(
                        applicationContext,
                        "Registration successful!",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar!!.visibility = View.GONE
                    mAuth2!!.signOut();
                    val intent =
                        Intent(this, Dashboard::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Registration failed! Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("Registration", "Failed Registration"+ task.exception?.message);
                    progressBar!!.visibility = View.GONE
                }
            }
    }


}

