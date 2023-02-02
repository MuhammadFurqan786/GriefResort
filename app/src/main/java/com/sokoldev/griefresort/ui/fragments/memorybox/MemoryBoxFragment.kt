package com.sokoldev.griefresort.ui.fragments.memorybox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.MemoryBoxAdapter
import com.sokoldev.griefresort.data.viewmodel.MemoryViewModel
import com.sokoldev.griefresort.databinding.FragmentMemoryBoxBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Global

class MemoryBoxFragment : Fragment(), MemoryBoxAdapter.OnMemoryBoxItemsClickListener {

    private lateinit var binding: FragmentMemoryBoxBinding
    lateinit var viewModel: MemoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMemoryBoxBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.memory_box)


        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        viewModel = ViewModelProvider(this).get(MemoryViewModel::class.java)
        binding.rvMemoryBox.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMemoryBox.setHasFixedSize(true)


        initObserver()


    }

    private fun initObserver() {
        viewModel.getList().observe(viewLifecycleOwner, Observer {
            it.let {
                val adapter = MemoryBoxAdapter(it, this)
                binding.rvMemoryBox.adapter = adapter
            }
        })
    }

    override fun onitemClcik(position: Int) {

    }

    override fun onMenuClick(position: Int) {
        Global.showMessage(binding.root.rootView, "Clicked ", Snackbar.LENGTH_SHORT)
    }


}