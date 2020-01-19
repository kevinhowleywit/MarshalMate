package org.wit.marshalmate.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.wit.marshalmate.R


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth=FirebaseAuth.getInstance()


        loginBtn.setOnClickListener {
            val email=email.text.toString()
            val pw= password.text.toString()




        }
        regBtn.setOnClickListener { toast("reg pressed") }
    }
}
