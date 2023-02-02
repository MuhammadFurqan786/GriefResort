package com.sokoldev.griefresort.ui.fragments.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.LibraryAdapter
import com.sokoldev.griefresort.data.viewmodel.LibraryViewModel
import com.sokoldev.griefresort.databinding.FragmentLibraryBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity


class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private lateinit var libraryViewModel: LibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLibraryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarText = requireActivity().findViewById<AppCompatTextView>(R.id.toolbarText)
        toolbarText.text = getString(R.string.library)


        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        binding.rvLibrary.layoutManager = GridLayoutManager(context, 2)
        binding.rvLibrary.setHasFixedSize(true)

        libraryViewModel = ViewModelProvider(this)[LibraryViewModel::class.java]

        setupObserver()
    }

    private fun setupObserver() {
        libraryViewModel.getList().observe(viewLifecycleOwner) {
            it.let {
                val adapter = LibraryAdapter(it)
                binding.rvLibrary.adapter = adapter
            }
        }
    }

}