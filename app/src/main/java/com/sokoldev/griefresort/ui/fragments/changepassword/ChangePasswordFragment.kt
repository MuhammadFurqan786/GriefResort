package com.sokoldev.griefresort.ui.fragments.changepassword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sokoldev.griefresort.data.viewmodel.UserViewModel
import com.sokoldev.griefresort.databinding.FragmentChangePasswordBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants
import com.sokoldev.griefresort.utils.Global

class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel : UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        binding.back.setOnClickListener {
            activity?.finish()
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(Constants.TYPE, "RENEW")
            startActivity(intent)
        }
        binding.btnChangePassword.setOnClickListener {
            val currentPassword = binding.edCurrentPassword.text.toString()
            val newPassword = binding.edNewPassword.text.toString()
            val confirmPassword = binding.edConfirmPassword.text.toString()

            if (currentPassword.isEmpty()) {
              binding.edCurrentPassword.error = "Please enter current password"
                return@setOnClickListener
            }
            if (newPassword.isEmpty()) {
                binding.edNewPassword.error = "Please enter new password"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.edConfirmPassword.error = "Please enter confirm password"
                return@setOnClickListener
            }
            if (newPassword != confirmPassword) {
                binding.edConfirmPassword.error = "Password does not match"
                return@setOnClickListener
            }

            viewModel.changePassword(newPassword)
            Global.showMessage(it,"Password Changed")
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(Constants.TYPE, "RENEW")
            startActivity(intent)
            activity?.finish()
        }

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.GONE
        return binding.root
    }

}