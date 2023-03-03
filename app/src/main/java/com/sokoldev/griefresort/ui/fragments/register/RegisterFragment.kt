package com.sokoldev.griefresort.ui.fragments.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentRegisterBinding
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)


        binding.textViewLogin.setOnClickListener {

            startActivity(Intent(context,AuthActivity::class.java))
            activity?.finish()
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(context,HomeActivity::class.java))
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}