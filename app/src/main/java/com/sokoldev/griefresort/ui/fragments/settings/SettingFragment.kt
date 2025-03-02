package com.sokoldev.griefresort.ui.fragments.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentSettingBinding
import com.sokoldev.griefresort.preference.PreferenceHelper
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.setting)


        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        val toolbarImage = (requireActivity() as HomeActivity).binding.back
        toolbarImage.setImageResource(R.drawable.ic_menu)





        binding.myAccount.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_myAccountFragment)
        }

        binding.subscription.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_subscriptionFragment)
        }
        binding.contactUs.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.CONTACT)
            findNavController().navigate(R.id.action_settingFragment_to_contactUsFragment, bundle)
        }
        binding.privacy.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.PRIVACY)
            findNavController().navigate(
                R.id.action_settingFragment_to_contactUsFragment, bundle
            )

        }
        binding.termAndConditions.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.TERMS)
            findNavController().navigate(
                R.id.action_settingFragment_to_contactUsFragment, bundle
            )
        }
        binding.faqs.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.FAQS)
            findNavController().navigate(R.id.action_settingFragment_to_contactUsFragment, bundle)
        }

        binding.changePassword.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_to_changePasswordFragment2)
        }

        binding.logOut.setOnClickListener {
            val pref = PreferenceHelper.getPref(requireContext())
            FirebaseAuth.getInstance().signOut()
            pref.setUserLogin(false)
            pref.clearSharedPref()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        }

    }




}