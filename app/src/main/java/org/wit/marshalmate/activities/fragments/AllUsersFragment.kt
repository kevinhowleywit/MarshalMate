package org.wit.marshalmate.activities.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fr_all_users.*
import kotlinx.android.synthetic.main.fr_search.*
import org.wit.marshalmate.R
import org.wit.marshalmate.activities.MainActivity
import org.wit.marshalmate.models.Person
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.marshalmate.activities.helpers.UserAdapter
import org.wit.marshalmate.activities.helpers.UserListener

class AllUsersFragment : Fragment(),AnkoLogger,UserListener {

    private var users=ArrayList<Person>()

    //private var myClipboard: ClipboardManager? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fr_all_users, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "All Users"
        //gets all the users from the main activity which gets it from the MainApp
        users=(activity as MainActivity).getAllUsers()
        users.forEach({info { "LOGGING FROM ALL USERS$it" }})
        val mUserAdapter=UserAdapter(users,this)
        allUsersRecyclerView.layoutManager=LinearLayoutManager(context)
        allUsersRecyclerView.adapter=mUserAdapter



    }

    override fun onUserClick(person: Person) {
        val myClipboard: ClipboardManager = activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        var myClip: ClipData? = null
        myClip=ClipData.newPlainText("text",person.mail)
        myClipboard?.setPrimaryClip(myClip)
        Toast.makeText(context,"Email Copied",Toast.LENGTH_SHORT).show()

    }


    //you can set the title for your toolbar here for different fragments different titles


}


