package com.sokoldev.griefresort.ui.fragments.grouphug

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var adapter: GroupHugAdapter

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
                adapter = GroupHugAdapter(this)
                binding.rvGroupHug.adapter = adapter
                adapter.setList(it)
            }
        })
    }

    override fun onGroupHugClick(position: Int, like: AppCompatTextView?, totalHugs: String) {
        var hugs = totalHugs.toInt()
        hugs += 1
        like?.text = hugs.toString()
        Global.showMessage(binding.root.rootView, "Hug Sent", Snackbar.LENGTH_SHORT)
    }


    override fun onSupportclick(
        position: Int,
        commentText: AppCompatTextView?,
        ed_support: AppCompatEditText?,
        totalComments: String
    ) {
        ed_support?.setText("")
        var comment = totalComments.toInt()
        comment += 1
        commentText?.text = comment.toString()
        Global.showMessage(binding.root.rootView, "Support Sent", Snackbar.LENGTH_SHORT)
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
        popup?.inflate(R.menu.options_menu_hug)
        popup?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.report) {
                showDialog()
                return@setOnMenuItemClickListener true
            } else
                return@setOnMenuItemClickListener false
        }
        popup?.show()

    }

    override fun onResume() {
        super.onResume()
        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.group_hug)

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE
    }
}