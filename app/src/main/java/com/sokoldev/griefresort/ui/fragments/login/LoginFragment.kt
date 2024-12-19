package com.sokoldev.griefresort.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.viewmodel.UserViewModel
import com.sokoldev.griefresort.databinding.FragmentLoginBinding
import com.sokoldev.griefresort.ui.activities.GetStartedActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)


        if (activity?.intent?.getStringExtra(Constants.TYPE) != null) {
            val string = activity?.intent?.getStringExtra(Constants.TYPE)
            if (string.equals(Constants.AGAIN)) {

                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(context, GetStartedActivity::class.java))
        }
        binding.tvForgot.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.edittextEmail.text.toString()
            val password = binding.edittextPassword.text.toString()

            if (email.isEmpty()) {
                binding.edittextEmail.error = "please add email address"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.edittextPassword.error = "please add password"
                return@setOnClickListener
            }

            viewModel.loginUser(email, password)


        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun initObserver() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            Snackbar.make(binding.root, status, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.isUserCreated.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingView.visibility = View.GONE
                startActivity(Intent(context, HomeActivity::class.java))
                activity?.finish()
            } else {
                binding.loadingView.visibility = View.GONE
            }
        }
    }


}