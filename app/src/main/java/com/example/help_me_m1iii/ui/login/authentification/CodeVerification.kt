package com.example.help_me_m1iii.ui.login.authentification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.help_me_m1iii.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_code_verification.*
import kotlin.math.log

class CodeVerification : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_verification)
        mAuth = FirebaseAuth.getInstance()
        authBtn.setOnClickListener {
                view: View? -> progress.visibility = View.VISIBLE
            authenticate()
        }
    }

    private fun authenticate () {

        val verifiNo = verifiTxt1.text.toString() + verifiTxt2.text.toString() + verifiTxt3.text.toString() + verifiTxt4.text.toString()+ verifiTxt5.text.toString() +verifiTxt6.text.toString()

        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId"), verifiNo)

        signIn(credential)

    }

    private fun signIn (credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    toast("Logged in Successfully :)")
                    startActivity(Intent(this, Home::class.java))
                }
            }
    }

    private fun toast (msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}
