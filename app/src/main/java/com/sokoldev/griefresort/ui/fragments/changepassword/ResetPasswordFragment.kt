package com.sokoldev.griefresort.ui.fragments.changepassword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.viewmodel.UserViewModel
import com.sokoldev.griefresort.databinding.FragmentChangePasswordBinding
import com.sokoldev.griefresort.databinding.FragmentResetPasswordBinding
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants
import com.sokoldev.griefresort.utils.Global

class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        (requireActivity() as AuthActivity).binding.relativeLayout.visibility = View.GONE
        binding.back.setOnClickListener {

            findNavController().navigateUp()
        }
        binding.btnChangePassword.setOnClickListener {
            val currentPassword = binding.edittextCurrentPassword.text.toString()
            val newPassword = binding.edittextNewPassword.text.toString()
            val confirmPassword = binding.edittextConfirmPassword.text.toString()

            if (currentPassword.isEmpty()) {
                binding.edittextCurrentPassword.error = "Please enter current password"
                return@setOnClickListener
            }
            if (newPassword.isEmpty()) {
                binding.edittextNewPassword.error = "Please enter new password"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.edittextConfirmPassword.error = "Please enter confirm password"
                return@setOnClickListener
            }
            if (newPassword != confirmPassword) {
                binding.edittextConfirmPassword.error = "Password does not match"
                return@setOnClickListener
            }

            viewModel.changePassword(newPassword)
            Global.showMessage(it,"Password Changed")
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(Constants.TYPE, "RENEW")
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }

}