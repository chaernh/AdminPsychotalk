package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth



class MainActivity : AppCompatActivity() {

    private var emailTV: TextInputEditText? = null
    private  var passwordTV:TextInputEditText? = null

    private var layoutEmail:TextInputLayout?=null
    private var layoutPassword:TextInputLayout?=null

    private var progressBar: ProgressBar? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance();



        initializeUI();
    }

    override fun onStart() {
        super.onStart()
        if(mAuth?.currentUser != null){

        }
    }

    private fun initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);

        layoutEmail = findViewById(R.id.layoutEmail)
        layoutPassword = findViewById(R.id.layoutPassword)
        progressBar = findViewById(R.id.progressBar);

    }

    fun Login(view: View) {
        progressBar!!.visibility = View.VISIBLE

        val email: String
        val password: String
        email = emailTV!!.text.toString()
        password = passwordTV!!.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG)
                .show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG)
                .show()
            return
        }
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Login successful!",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar!!.visibility = View.GONE
                    val intent =
                        Intent(this, Dashboard::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Login failed! Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("MainActivity", "Failed Login"+ task.exception?.message);
                    progressBar!!.visibility = View.GONE
                }
            }
    }
    fun Register(view: View) {
        var intent = Intent(this,Registration::class.java)
        startActivity(intent)
    }
}
