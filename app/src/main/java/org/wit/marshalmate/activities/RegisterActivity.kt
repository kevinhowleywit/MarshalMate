package org.wit.marshalmate.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
import org.wit.marshalmate.R
import org.wit.marshalmate.main.MainApp
import org.wit.marshalmate.models.Person


class RegisterActivity : AppCompatActivity() {
    var auth: FirebaseAuth =FirebaseAuth.getInstance()
    var app: MainApp? = null
    var person= Person()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        hideProgress()

        registerBtn.setOnClickListener {
            val email = regEmail.text.toString()
            val password =regPassword.text.toString()
            val confirmedPw=regPasswordConfirm.text.toString()

            if (email == "" || password == ""){
                toast("Please fill out all fields")
            }
            else if(password != confirmedPw)
            {
                toast("Passwords do not match")
            }
            else{
                showProgress()
                app = application as MainApp

                person.mail=email
                app?.addUserToDB(person)
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(){task ->
                    if(task.isSuccessful){

                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        hideProgress()
                    }
                    else{
                        toast("Registration Error: ${task.exception?.message}")
                    }
                    hideProgress()

                }
            }

        }
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(org.wit.marshalmate.R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            org.wit.marshalmate.R.id.item_logout -> {
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, LoginActivity::class.java)
                this.startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
*/

    fun showProgress() {
        regProgressBar.visibility = View.VISIBLE
    }
    fun hideProgress() {
        regProgressBar.visibility = View.GONE
    }
}
