package com.sokoldev.griefresort.ui.fragments.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentContactUsBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class ContactUsFragment : Fragment() {
    private lateinit var binding: FragmentContactUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentContactUsBinding.inflate(layoutInflater, container, false)
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


        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        val type = arguments?.getString(Constants.TYPE)
        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText


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