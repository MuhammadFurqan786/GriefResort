package com.sokoldev.griefresort.ui.fragments.mydiary

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.DiaryAdapter
import com.sokoldev.griefresort.data.models.GroupHug
import com.sokoldev.griefresort.data.viewmodel.GroupHugViewModel
import com.sokoldev.griefresort.databinding.FragmentMyDiaryBinding
import com.sokoldev.griefresort.preference.PreferenceHelper
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.ui.activities.ShareDiaryActivity
import com.sokoldev.griefresort.utils.Constants

class MyDiaryFragment : Fragment(), DiaryAdapter.OnDiaryItemClickListener {


    private lateinit var viewModel: GroupHugViewModel
    private lateinit var arrayList: ArrayList<GroupHug>
    private lateinit var helper: PreferenceHelper

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
        helper = PreferenceHelper.getPref(requireContext())

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        binding.rvMyDiary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMyDiary.setHasFixedSize(true)

        viewModel = ViewModelProvider(this)[GroupHugViewModel::class.java]
        helper.getCurrentUser()?.userId?.let { viewModel.getGroupHugsForUser(it) }
        initObserver()

        binding.shareHere.setOnClickListener {
            startActivity(Intent(context, ShareDiaryActivity::class.java))
        }

        binding.sendSupport.setOnClickListener {
            binding.linearLayout.visibility = View.GONE
        }
    }

    private fun initObserver() {
        viewModel.groupHugs.observe(viewLifecycleOwner) {
            arrayList = ArrayList()
            arrayList = it as ArrayList<GroupHug>
            val adapter = DiaryAdapter(this)
            adapter.setList(it)
            binding.rvMyDiary.adapter = adapter
        }
    }


    private fun showDialog() {
        val alertDialog: android.app.AlertDialog? =
            android.app.AlertDialog.Builder(context)
                .setMessage("Thank You for Reporting, we are looking into this.") //set positive button
                .setPositiveButton(
                    "ok",
                    DialogInterface.OnClickListener { dialogInterface, i -> //set what would happen when positive button is clicked
                        dialogInterface.dismiss()
                    })
                .show()
    }

    override fun onMenuImageClick(position: Int, menuImageView: AppCompatImageView) {
        val popup = context?.let { PopupMenu(it, menuImageView, Gravity.END) }
        popup?.inflate(R.menu.option_menu_diary)
        popup?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.edit) {
                val groupHug = arrayList[position]
                val intent = Intent(context, ShareDiaryActivity::class.java)
                intent.putExtra(Constants.GROUP_HUG, groupHug)
                startActivity(intent)
                return@setOnMenuItemClickListener true
            } else if (item.itemId == R.id.delete) {
                val groupHug = arrayList[position]
                groupHug.id?.let { it1 -> viewModel.deleteGroupHug(it1) }
                return@setOnMenuItemClickListener true
            } else return@setOnMenuItemClickListener item.itemId == R.id.delete
        }
        popup?.show()

    }

    @SuppressLint("StaticFieldLeak")
    companion object {
        lateinit var binding: FragmentMyDiaryBinding
    }


}