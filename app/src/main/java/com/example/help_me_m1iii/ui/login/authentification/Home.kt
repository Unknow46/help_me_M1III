package com.example.help_me_m1iii.ui.login.authentification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.help_me_m1iii.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAuth = FirebaseAuth.getInstance()
        signOut.setOnClickListener {
                view: View? -> mAuth.signOut()
            startActivity(Intent(this, PhoneAuthentication::class.java))
            Toast.makeText(this, "Logged out Successfully :)", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, PhoneAuthentication::class.java))
        }else {
            Toast.makeText(this, "Already Signed in :)", Toast.LENGTH_LONG).show()
        }
    }
}
