package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_main.*

class ChangePassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = FirebaseAuth.getInstance()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun ChangePassword(view: View) {
        if(et_current.text.isNotEmpty() && et_new.text.isNotEmpty() && et_confirm.text.isNotEmpty()) {
            if(et_new.text.toString().equals(et_confirm.text.toString())) {
                val user = auth.currentUser
                if(user != null) {
                    val credential = EmailAuthProvider.getCredential(user.email!!, et_current.text.toString())
                    user?.reauthenticate(credential)?.addOnCompleteListener {
                        if(it.isSuccessful) {
//                            Toast.makeText(this, "Re-authentication success!", Toast.LENGTH_SHORT).show()
                            user?.updatePassword(et_new.text.toString())?.addOnCompleteListener {
                                task -> if(it.isSuccessful) {
                                Toast.makeText(this, "Password changed!", Toast.LENGTH_SHORT).show()
                                FirebaseAuth.getInstance().signOut()
                                finish()
                                startActivity(Intent(this, MainActivity::class.java))
                                }
                            }
                        }
                        else {
                            Toast.makeText(this, "Re-authentication failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
//            else {
//
//            }
        }
//        else {
//
//        }
    }
}
