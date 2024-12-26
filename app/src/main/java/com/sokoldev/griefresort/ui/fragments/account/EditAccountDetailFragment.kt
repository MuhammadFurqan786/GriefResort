package com.sokoldev.griefresort.ui.fragments.account

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.models.User
import com.sokoldev.griefresort.data.viewmodel.UserViewModel
import com.sokoldev.griefresort.databinding.FragmentEditAccountDetailBinding
import com.sokoldev.griefresort.preference.PreferenceHelper
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Global


class EditAccountDetailFragment : Fragment() {
    private lateinit var binding: FragmentEditAccountDetailBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var helper: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditAccountDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = PreferenceHelper.getPref(requireContext())
        val user = helper.getCurrentUser()
        setData(user)
        val toolbarImage = (requireActivity() as HomeActivity).binding.back
        toolbarImage.setImageResource(R.drawable.ic_back)
        toolbarImage.setOnClickListener {
            activity?.findNavController(R.id.nav_host_fragment_activity_home)?.navigateUp()
        }

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.edit_account2)


        binding.btnSave.setOnClickListener {
            val firstName = binding.edFirstName.text.toString()
            val lastName = binding.edLastName.text.toString()
            val userName = binding.edUserName.text.toString()

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

            viewModel.updateUser(firstName, lastName, userName)

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


    }

    private fun initObserver() {
        viewModel.status.observe(viewLifecycleOwner) {
            Global.showMessage(binding.root.rootView, it)
        }
        viewModel.isUpdateUser.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigateUp()
            }
        }
    }

    private fun setData(user: User?) {
        if (user != null) {
            binding.edFirstName.setText(user.firstName)
            binding.edLastName.setText(user.lastName)
            binding.edUserName.setText(user.userName)
            binding.edEmail.setText(user.email)
        }
    }

}