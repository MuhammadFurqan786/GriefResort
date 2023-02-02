package com.sokoldev.griefresort.ui.fragments.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentMyAccountBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class MyAccountFragment : Fragment() {
    private lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarImage = (requireActivity() as HomeActivity).binding.back
        toolbarImage.setImageResource(R.drawable.ic_back)
        toolbarImage.setOnClickListener {
            activity?.finish()
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(Constants.TYPE, "RENEW")
            startActivity(intent)
        }

        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.my_account)


        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        binding.editAccount.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_editAccountDetailFragment)
        }

        binding.deleteAccount.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountFragment_to_deleteAccountFragment)
        }


    }
}