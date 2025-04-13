package com.sokoldev.griefresort.ui.fragments.register

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.viewmodel.UserViewModel
import com.sokoldev.griefresort.databinding.FragmentRegisterBinding
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        (requireActivity() as AuthActivity).binding.relativeLayout.visibility = View.GONE
        binding.loadingView.addBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_loader))
        binding.loadingView.setShadowColor(Color.LTGRAY)
        binding.loadingView.setDuration(800)
        binding.loadingView.start()
        binding.loadingView.visibility = View.GONE

        binding.textViewLogin.setOnClickListener {
            startActivity(Intent(context,AuthActivity::class.java))
            activity?.finish()
        }

        binding.buttonSignUp.setOnClickListener {

            val firstName = binding.edFirstName.text.toString()
            val lastName = binding.edLastName.text.toString()
            val userName = binding.edUserName.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            val confirmPassword = binding.edConfirmPassword.text.toString()

            if (firstName.isEmpty()) {
                binding.edFirstName.error = "please add first name"
                return@setOnClickListener
            }
            if (lastName.isEmpty()) {
                binding.edLastName.error = "please add last name"
                return@setOnClickListener
            }

            if (userName.isEmpty()) {
                binding.edUserName.error = "please add username"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.edEmail.error = "please add username"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edPassword.error = "please add password"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.edConfirmPassword.error = "please add confirm password"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.edConfirmPassword.error = "Password Don't Match"
                return@setOnClickListener
            }
            binding.loadingView.visibility = View.VISIBLE
            viewModel.registerUser(firstName, lastName, userName, email, password)

        }


        binding.edUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val username = s.toString()
                if (username.isNotEmpty()) {
                    viewModel.checkUserNameAvailability(username) { isAvailable ->
                        if (isAvailable) {
                            binding.userNameStatus.text = "Username is available"
                            binding.userNameStatus.setTextColor(Color.GREEN)
                        } else {
                            binding.userNameStatus.text = "Username is taken"
                            binding.userNameStatus.setTextColor(Color.RED)
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        initObserver()

        binding.privacyButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.PRIVACY)
            bundle.putBoolean(Constants.ISREGISTERFORM, true)
            findNavController().navigate(R.id.action_registerFragment_to_contactUsFragment2, bundle)
        }

        binding.termsButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.TERMS)
            bundle.putBoolean(Constants.ISREGISTERFORM, true)
            findNavController().navigate(R.id.action_registerFragment_to_contactUsFragment2, bundle)
        }

        return binding.root
    }

    private fun initObserver() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            Snackbar.make(binding.root, status, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.isUserCreated.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingView.visibility = View.GONE
                showDialog()

            } else {
                binding.loadingView.visibility = View.GONE
            }
        }
    }

    private fun showDialog() {
        val alertDialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(context)
            .setTitle("Grief Resort would like to send you Notifications.")
            .setMessage("Notifications may include alerts, sounds and icon badges.") //set positive button
            .setPositiveButton("Allow",
                DialogInterface.OnClickListener { dialogInterface, i -> //set what would happen when positive button is clicked
                    dialogInterface.dismiss()
                    startActivity(Intent(context, HomeActivity::class.java))
                    activity?.finish()

                }).setNegativeButton(
                "Don't Allow",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    startActivity(Intent(context, HomeActivity::class.java))
                    activity?.finish()
                }).show()
    }

}