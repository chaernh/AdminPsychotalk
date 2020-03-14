package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauth.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class Registration : AppCompatActivity() {

    private var emailTV: EditText? = null
    private  var passwordTV:EditText? = null
    private  var nameTv:EditText? = null
    private  var phoneTv:EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mAuth = FirebaseAuth.getInstance();

        initializeUI();
    }

    private fun initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        nameTv = findViewById(R.id.nama)
        phoneTv = findViewById(R.id.phone)
        progressBar = findViewById(R.id.progressBar);
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
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var user = User(name,email,phone)

                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).set(user)

                    Toast.makeText(
                        applicationContext,
                        "Registration successful!",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar!!.visibility = View.GONE
                    val intent =
                        Intent(this, MainActivity::class.java)
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

