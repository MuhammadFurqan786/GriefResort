package com.sokoldev.griefresort.ui.fragments.mydiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.DiaryAdapter
import com.sokoldev.griefresort.data.viewmodel.DiaryViewModel
import com.sokoldev.griefresort.databinding.FragmentMyDiaryBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity

class MyDiaryFragment : Fragment() {

    private lateinit var binding: FragmentMyDiaryBinding
    private lateinit var viewModel: DiaryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyDiaryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.my_diary)

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        binding.rvMyDiary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyDiary.setHasFixedSize(true)

        viewModel = ViewModelProvider(this)[DiaryViewModel::class.java]

        initObserver()

    }

    private fun initObserver() {
        viewModel.getList().observe(viewLifecycleOwner) {

            val adapter = DiaryAdapter()
            adapter.setList(it)
            binding.rvMyDiary.adapter = adapter
        }
    }
}