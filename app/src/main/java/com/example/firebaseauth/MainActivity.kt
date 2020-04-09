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
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
//        val btnLogin = findViewById(R.id.bt_login) as Button
//        btnLogin.setOnClickListener() {
//            val email = et_email.text.toString()
//            val password = et_password.text.toString()
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Harap masukkan email dan password!", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
//                .addOnCompleteListener{
//
//                    if (!it.isSuccessful){ return@addOnCompleteListener
//                        val intent = Intent (this, Login::class.java)
//                        startActivity(intent)
//                    }
//                    else
//                        Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
//                        val intent = Intent (this, MainActivity::class.java)
//                        startActivity(intent)
//                }
//                .addOnFailureListener{
//                    Log.d("Main", "Failed Login: ${it.message}")
//                    Toast.makeText(this, "Email atau password salah!", Toast.LENGTH_SHORT).show()
//                }
    }
    fun loginExec(email:String, password:String){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }

    fun btnLogin(view: View) {
        var email :String = et_email.text.toString()
        var password :String = et_password.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Silahkan masukkan email!", Toast.LENGTH_LONG)
                .show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Silahkan masukkan password", Toast.LENGTH_LONG)
                .show()
            return
        }
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Login sukses!",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent =
                        Intent(this, Dashboard::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Login gagal! Silahkan coba lagi...",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("Login", "Failed Login"+ task.exception?.message);
                }
            }
    }
}
