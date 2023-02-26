package com.example.mamiethon.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mamiethon.R
import com.example.mamiethon.interfaces.IAuthenticator
import com.example.mamiethon.resource.Authenticator

class RecipeListFragment : Fragment() {
    private val authenticator: IAuthenticator = Authenticator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)
        val textView = view.findViewById<TextView>(R.id.user_name)
        val user = authenticator.getCurrentUser()
        if(user != null)
            textView.text = user.displayName
        else
            textView.text = "Youpi"
        return view
    }
}