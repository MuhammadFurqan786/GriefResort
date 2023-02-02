package com.sokoldev.griefresort.ui.fragments.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentFaqsBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class FaqsFragment : Fragment() {
    private lateinit var binding: FragmentFaqsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFaqsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarImage = (requireActivity() as HomeActivity).binding.back
        toolbarImage.setImageResource(R.drawable.ic_back)
        toolbarImage.setOnClickListener {
            activity?.findNavController(R.id.nav_host_fragment_activity_home)?.navigateUp()
        }
        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.faqs)


        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        binding.griefResort.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.FAQS)
            findNavController().navigate(R.id.action_faqsFragment2_to_contactUsFragment, bundle)

        }
        binding.tvSub.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.FAQS)
            findNavController().navigate(R.id.action_faqsFragment2_to_contactUsFragment, bundle)
        }


    }
}