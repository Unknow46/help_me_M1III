package com.example.help_me_m1iii.ui.login.authentification

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.help_me_m1iii.R
import com.example.help_me_m1iii.ui.activities.MainActivity
import com.example.help_me_m1iii.ui.fragments.HomeFragments
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_code_verification.*


class CodeVerification : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    var currentCode = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_verification)
        mAuth = FirebaseAuth.getInstance()
        authBtn.setOnClickListener {
                view: View? -> progress.visibility = View.VISIBLE
            authenticate()
        }

        verifiTxt1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                val edtChar: String = verifiTxt1.getText().toString()
                if (edtChar.length == 1) {
                    currentCode.append(edtChar)
                    verifiTxt2.requestFocus()
                } else if (edtChar.length == 0) {
                    currentCode.deleteCharAt(0)
                    verifiTxt1.requestFocus()
                }
            }
        })


        verifiTxt2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                val edtChar: String = verifiTxt2.getText().toString()
                if (edtChar.length == 1) {
                    currentCode.append(edtChar)
                     verifiTxt3.requestFocus()
                } else if (edtChar.length == 0) {
                    currentCode.deleteCharAt(1)
                    verifiTxt2.requestFocus()
                }
            }
        })
        verifiTxt3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                val edtChar: String = verifiTxt3.getText().toString()
                if (edtChar.length == 1) {
                    currentCode.append(edtChar)
                    verifiTxt4.requestFocus()
                } else if (edtChar.length == 0) {
                    currentCode.deleteCharAt(2)
                    verifiTxt3.requestFocus()
                }
            }
        })

        verifiTxt4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                val edtChar: String = verifiTxt4.getText().toString()
                if (edtChar.length == 1) {
                    currentCode.append(edtChar)
                    verifiTxt5.requestFocus()
                } else if (edtChar.length == 0) {
                    currentCode.deleteCharAt(3)
                    verifiTxt4.requestFocus()
                }
            }
        })

        verifiTxt5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                val edtChar: String = verifiTxt5.getText().toString()
                if (edtChar.length == 1) {
                    currentCode.append(edtChar)
                    verifiTxt6.requestFocus()
                } else if (edtChar.length == 0) {
                    currentCode.deleteCharAt(4)
                    verifiTxt5.requestFocus()
                }
            }
        })

        verifiTxt6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                val edtChar: String = verifiTxt6.getText().toString()
                if (edtChar.length == 1) {
                    currentCode.append(edtChar)
                    verifiTxt6.requestFocus()
                } else if (edtChar.length == 0) {
                    currentCode.deleteCharAt(5)
                    verifiTxt6.requestFocus()
                }
            }
        })
    }

    private fun authenticate () {

        val verifiNo = currentCode

        if(verifiNo.length != 6){
            toast("Veuillez entrer un code valide")
            progress.visibility = View.INVISIBLE
        }
        else {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId"), verifiNo.toString())
            signIn(credential)
        }
    }

    private fun signIn (credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                    task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    toast("Logged in Successfully")
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else {
                    toast("Wrong code")
                    progress.visibility = View.INVISIBLE
                }
            }
    }

    private fun toast (msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}
