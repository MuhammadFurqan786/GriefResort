package com.sokoldev.griefresort.ui.fragments.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.data.viewmodel.UserViewModel
import com.sokoldev.griefresort.databinding.FragmentForgotPasswordBinding
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.utils.Global


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel : UserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        (requireActivity() as AuthActivity).binding.relativeLayout.visibility = View.GONE
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnResetPassword.setOnClickListener {
            val email = binding.edittextEmail.text.toString()
            if (email.isEmpty()){
                binding.edittextEmail.error = "Email is required"
                return@setOnClickListener
            }

            viewModel.forgotPassword(email)
        }

        initObserver()
        return binding.root
    }

    private fun initObserver() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            Global.showMessage(binding.root.rootView, status)
        }
        viewModel.isEmailSent.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigateUp()
            }
        }
    }

}