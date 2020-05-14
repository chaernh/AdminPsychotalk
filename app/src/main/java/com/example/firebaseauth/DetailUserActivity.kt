package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauth.Model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class DetailUserActivity : AppCompatActivity() {
    private var emailTV: EditText? = null
    private  var passwordTV: EditText? = null
    private  var nameTv: EditText? = null
    private  var phoneTv: EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    lateinit var spinner:Spinner
    lateinit var spinnerRole:Spinner
    lateinit var uid:String
    lateinit var roleAdapter: ArrayAdapter<String>
    lateinit var activeAdapter: ArrayAdapter<String>
    lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        initializeUI()

        roleAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,resources.getStringArray(R.array.role_array))
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRole.adapter = roleAdapter
        activeAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,resources.getStringArray(R.array.active_array))
        activeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = activeAdapter


        uid = intent.extras!!.getString("uid").toString()


        val docRef = FirebaseFirestore.getInstance().collection("Users").document(uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    emailTV?.setText(document.data!!["email"]?.toString())
                    nameTv?.setText(document.data!!["name"]?.toString())
                    phoneTv?.setText(document.data!!["phone"]?.toString())
                    spinner.setSelection(activeAdapter.getPosition(document.data!!["active"]?.toString()))
                    spinnerRole.setSelection(roleAdapter.getPosition(document.data!!["role"]?.toString()))
                } else {

                }
            }
            .addOnFailureListener { exception ->
                Log.d("Failure get users/id", "get failed with ", exception)
            }


    }
    private fun initializeUI() {
        emailTV = findViewById(R.id.email);
        regBtn = findViewById(R.id.saveBtn);
        nameTv = findViewById(R.id.nama)
        phoneTv = findViewById(R.id.phone)
        progressBar = findViewById(R.id.progressBar);
        spinner = findViewById(R.id.spinerActive);
        spinnerRole = findViewById(R.id.spinnerRole);
    }

    fun btnSave(view: View) {

        if (emailTV?.text!=null && emailTV?.text!=null && nameTv?.text!=null && phoneTv?.text!=null){
            val email: String
            val password: String
            email = emailTV!!.text.toString()
            val name = nameTv?.text.toString()
            val phone = phoneTv?.text.toString()
            if(spinner!!.selectedItem.toString().equals("PASIEN")){
                user = User(name,email,phone,spinnerRole?.selectedItem.toString(),uid,spinner?.selectedItem.toString(),"","")
            }else{
                user = User(name,email,phone,spinner?.selectedItem.toString(),uid,spinner?.selectedItem.toString())
            }
            FirebaseFirestore.getInstance().collection("Users").document(uid).set(user, SetOptions.merge()).addOnSuccessListener { Toast.makeText(this,"Save Success",Toast.LENGTH_LONG).show()
                val intent = Intent(this,Dashboard::class.java)
                startActivity(intent)
            }.addOnFailureListener { e -> Log.w("Error save user/id", "Error writing document", e) }
        }
    }
}
