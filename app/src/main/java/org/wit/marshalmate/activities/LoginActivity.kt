package org.wit.marshalmate.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.wit.marshalmate.R


class LoginActivity : AppCompatActivity() {

     var mAuth: FirebaseAuth? = null
    /*val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()*/




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        hideProgress()
        var auth: FirebaseAuth =FirebaseAuth.getInstance()


        loginBtn.setOnClickListener {
            //regEmail.setText("test@test.com")
            //regPassword.setText("Memes1234")
            val email=regEmail.text.toString()
            val pw= regPassword.text.toString()

            if (email == "" || pw == "") {
                toast("Please provide email + password")
            }
            else{
                showProgress()
                auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener() { task ->
                    if(task.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        hideProgress()
                        toast("Login Error ${task.exception?.message}")
                    }
                }
            }

        }
        regBtn.setOnClickListener {
            val intent =Intent(this,RegisterActivity::class.java)
            startActivity(intent)

        }
    }


     fun showProgress() {
        progressbar.visibility = View.VISIBLE
    }
     fun hideProgress() {
        progressbar.visibility = View.GONE
    }
}
