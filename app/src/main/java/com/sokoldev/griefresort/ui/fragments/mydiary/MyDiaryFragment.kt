package com.sokoldev.griefresort.ui.fragments.mydiary

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
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
import com.sokoldev.griefresort.data.models.Diary
import com.sokoldev.griefresort.data.viewmodel.DiaryViewModel
import com.sokoldev.griefresort.databinding.FragmentMyDiaryBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.ui.activities.ShareDiaryActivity

class MyDiaryFragment : Fragment(), DiaryAdapter.OnDiaryItemClickListener {


    private lateinit var viewModel: DiaryViewModel
    private lateinit var arrayList: ArrayList<Diary>

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

        binding.shareHere.setOnClickListener {
            startActivity(Intent(context, ShareDiaryActivity::class.java))
        }

        binding.sendSupport.setOnClickListener {
            binding.linearLayout.visibility = View.GONE
        }
    }

    private fun initObserver() {
        viewModel.getList().observe(viewLifecycleOwner) {
            arrayList = ArrayList()
            arrayList = it as ArrayList<Diary>
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
                val desc = arrayList[position].description
                val intent = Intent(context, ShareDiaryActivity::class.java)
                intent.putExtra(com.sokoldev.griefresort.utils.Constants.TYPE, desc)
                startActivity(intent)
                return@setOnMenuItemClickListener true
            } else if (item.itemId == R.id.delete) {
                binding.rvMyDiary.visibility = View.GONE
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