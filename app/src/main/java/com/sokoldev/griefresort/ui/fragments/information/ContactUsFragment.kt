package com.sokoldev.griefresort.ui.fragments.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentContactUsBinding
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class ContactUsFragment : Fragment() {
    private lateinit var binding: FragmentContactUsBinding
    private lateinit var toolbarText: AppCompatTextView
    private lateinit var toolbarImage: AppCompatImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactUsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = arguments?.getString(Constants.TYPE)
        val isRegisterForm = arguments?.getBoolean(Constants.ISREGISTERFORM)



        if (isRegisterForm == true) {
            toolbarImage = (requireActivity() as AuthActivity).binding.back
            toolbarImage.setImageResource(R.drawable.ic_back)
            toolbarText = (requireActivity() as AuthActivity).binding.toolbarText
            (requireActivity() as AuthActivity).binding.relativeLayout.visibility = View.VISIBLE
        } else {
            toolbarImage = (requireActivity() as HomeActivity).binding.back
            toolbarImage.setImageResource(R.drawable.ic_back)
            toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
            (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE
        }
        toolbarImage.setOnClickListener {
            if (isRegisterForm == true) {
                findNavController().navigateUp()
            } else {
            activity?.finish()
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(Constants.TYPE, "RENEW")
            startActivity(intent)
            }
        }



        if (type.equals(Constants.CONTACT)) {
            toolbarText.text = getString(R.string.contatct_us)
            binding.infoContact.visibility = View.VISIBLE
        } else if (type.equals(Constants.PRIVACY)) {
            toolbarText.text = getString(R.string.privacy_policy)
            binding.infoPrivacy.visibility = View.VISIBLE
        } else if (type.equals(Constants.TERMS)) {
            toolbarText.text = getString(R.string.termsCondition)
            binding.infoTerms.visibility = View.VISIBLE
        } else if (type.equals(Constants.FAQS)) {
            toolbarText.text = getString(R.string.faqs)
            binding.infoFaqs.visibility = View.VISIBLE
        }

    }
}