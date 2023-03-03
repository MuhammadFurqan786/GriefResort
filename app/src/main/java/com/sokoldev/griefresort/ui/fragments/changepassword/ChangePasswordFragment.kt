package com.sokoldev.griefresort.ui.fragments.changepassword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sokoldev.griefresort.databinding.FragmentChangePasswordBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants
import com.sokoldev.griefresort.utils.Global

class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding

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