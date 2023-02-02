package com.sokoldev.griefresort.ui.fragments.grouphug

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.GroupHugAdapter
import com.sokoldev.griefresort.data.viewmodel.GroupHugViewModel
import com.sokoldev.griefresort.databinding.FragmentGroupHugBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Global


class GroupHugFragment : Fragment(), GroupHugAdapter.OnGroupHugItemsClickListener {

    private lateinit var binding: FragmentGroupHugBinding
    lateinit var viewModel: GroupHugViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGroupHugBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        (requireActivity() as HomeActivity).setText(getString(R.string.group_hug))

        viewModel = ViewModelProvider(this).get(GroupHugViewModel::class.java)
        binding.rvGroupHug.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGroupHug.setHasFixedSize(true)


        initObserver()

    }

    private fun initObserver() {
        viewModel.getList().observe(viewLifecycleOwner, Observer {
            it.let {
                val adapter = GroupHugAdapter(this)
                binding.rvGroupHug.adapter = adapter
                adapter.setList(it)
            }
        })
    }

    override fun onGroupHugClick(position: Int, like: AppCompatTextView?) {
        Global.showMessage(binding.root.rootView, "Clicked Group Hug", Snackbar.LENGTH_SHORT)
    }


    override fun onSupportclick(
        position: Int,
        comment: AppCompatTextView?,
        ed_support: AppCompatEditText?
    ) {
        Global.showMessage(binding.root.rootView, "Clicked Support Hug", Snackbar.LENGTH_SHORT)
    }

    override fun onResume() {
        super.onResume()
        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.group_hug)

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE
    }
}