package com.example.help_me_m1iii.ui.login.authentification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.help_me_m1iii.R
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_phone_authentication.*
import java.util.concurrent.TimeUnit


class PhoneAuthentication : AppCompatActivity() {

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    var verificationId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_authentication)
        mAuth = FirebaseAuth.getInstance()
        veriBtn.setOnClickListener {
                view: View? -> progress.visibility = View.VISIBLE
            verify ()
        }
    }

    private fun verificationCallbacks () {
        mCallbacks = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //progress.visibility = View.INVISIBLE
                signIn(credential)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationId = p0.toString()
                codeVerfication(verificationId)
                progress.visibility = View.INVISIBLE
            }
        }
    }

    private fun verify () {

        verificationCallbacks()

        val phnNo = phnNoTxt.text.toString()

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phnNo,
            60,
            TimeUnit.SECONDS,
            this,
            mCallbacks
        )
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

    private fun codeVerfication (verificationId: String){
        val intent = Intent(this,CodeVerification::class.java)
        intent.putExtra("verificationId",verificationId)
        startActivity(intent)
    }

    private fun toast (msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}
