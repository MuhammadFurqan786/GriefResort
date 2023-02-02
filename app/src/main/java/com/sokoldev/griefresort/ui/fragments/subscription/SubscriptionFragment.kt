package com.sokoldev.griefresort.ui.fragments.subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentSubscriptionBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity


class SubscriptionFragment : Fragment() {

    private lateinit var binding: FragmentSubscriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSubscriptionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            activity?.findNavController(R.id.nav_host_fragment_activity_home)?.navigateUp()
        }


        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.GONE
    }

    companion object {}
}