package com.sokoldev.griefresort.ui.fragments.login

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
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
import com.sokoldev.griefresort.ui.activities.AuthActivity
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
        (requireActivity() as AuthActivity).binding.relativeLayout.visibility = View.GONE

        if (activity?.intent?.getStringExtra(Constants.TYPE) != null) {
            val string = activity?.intent?.getStringExtra(Constants.TYPE)
            if (string.equals(Constants.AGAIN)) {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        binding.loadingView.addBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_loader))
        binding.loadingView.setShadowColor(Color.LTGRAY)
        binding.loadingView.setDuration(800)
        binding.loadingView.start()
        binding.loadingView.visibility = View.GONE


        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(context, GetStartedActivity::class.java))
        }
        binding.tvForgot.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.btnSignIn.setOnClickListener {
            binding.loadingView.visibility = View.VISIBLE
            val email = binding.edittextEmail.text.toString()
            val password = binding.edittextPassword.text.toString()

            if (email.isEmpty()) {
                binding.loadingView.visibility = View.GONE
                binding.edittextEmail.error = "please add email address"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.loadingView.visibility = View.GONE
                binding.edittextPassword.error = "please add password"
                return@setOnClickListener
            }
            binding.loadingView.visibility = View.VISIBLE
            viewModel.loginUser(email, password)


        }

        initObserver()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun initObserver() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            Snackbar.make(binding.root, status, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
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