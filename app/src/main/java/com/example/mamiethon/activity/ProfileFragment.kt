package com.example.mamiethon.activity

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.mamiethon.R
import com.example.mamiethon.factory.MainViewModelFactory
import com.example.mamiethon.interfaces.IAuthenticator
import com.example.mamiethon.resource.Authenticator
import com.example.mamiethon.viewModel.MainViewModel

class ProfileFragment : Fragment() {
    private val authenticator: IAuthenticator = Authenticator()
    private lateinit var viewModel: MainViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(
            requireActivity(),
            MainViewModelFactory(requireActivity())
        )[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val disconnectButton = view.findViewById<Button>(R.id.disconnectButton)
        disconnectButton.setOnClickListener{
            viewModel.disconnectAndClearActivity()
        }
        val textView = view.findViewById<TextView>(R.id.user_email)
        val user = authenticator.getCurrentUser()
        if(user != null)
            textView.text = user.email
        return view
    }
}