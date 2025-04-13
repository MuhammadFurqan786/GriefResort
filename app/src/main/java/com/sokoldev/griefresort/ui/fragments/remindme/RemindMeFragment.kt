package com.sokoldev.griefresort.ui.fragments.remindme

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.RemindMeAdapter
import com.sokoldev.griefresort.data.models.RemindMe
import com.sokoldev.griefresort.data.viewmodel.RemindViewModel
import com.sokoldev.griefresort.databinding.FragmentRemindMeBinding
import com.sokoldev.griefresort.preference.PreferenceHelper
import com.sokoldev.griefresort.ui.activities.AddReminderActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants


class RemindMeFragment : Fragment(), RemindMeAdapter.OnRemindMeItemsClickListener {

    private lateinit var viewModel: RemindViewModel
    private lateinit var binding: FragmentRemindMeBinding
    private lateinit var adapter: RemindMeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRemindMeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.remind_me)

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        binding.rvRemindMe.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRemindMe.setHasFixedSize(true)



        viewModel = ViewModelProvider(this)[RemindViewModel::class.java]
        PreferenceHelper.getPref(requireContext()).getCurrentUser()?.userId?.let {
            viewModel.getReminders(
                it
            )
        }
        setUpObserver()

        binding.addDate.setOnClickListener {
            startActivity(Intent(context, AddReminderActivity::class.java))
        }

    }

    private fun setUpObserver() {
        viewModel.reminders.observe(viewLifecycleOwner) {
             adapter = RemindMeAdapter(it as ArrayList<RemindMe>, this)
            binding.rvRemindMe.adapter = adapter
        }
    }

    override fun onDeleteClick(position: Int) {
        adapter.removeAt(position)
    }

    override fun onEditClick(position: Int) {
        val intent = Intent(context,AddReminderActivity::class.java)
        intent.putExtra(Constants.TITLE,adapter.arrayList[position].title)
        intent.putExtra(Constants.ID, adapter.arrayList[position].id)
        intent.putExtra(Constants.DATE, adapter.arrayList[position].date)
        intent.putExtra(Constants.EDIT, true)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        PreferenceHelper.getPref(requireContext()).getCurrentUser()?.userId?.let {
            viewModel.getReminders(
                it
            )
        }
    }
}