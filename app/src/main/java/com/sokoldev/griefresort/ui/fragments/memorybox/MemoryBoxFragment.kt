package com.sokoldev.griefresort.ui.fragments.memorybox

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.MemoryBoxAdapter
import com.sokoldev.griefresort.data.models.MemoryBox
import com.sokoldev.griefresort.data.viewmodel.MemoryViewModel
import com.sokoldev.griefresort.databinding.FragmentMemoryBoxBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity


class MemoryBoxFragment : Fragment(), MemoryBoxAdapter.OnMemoryBoxItemsClickListener {

    private lateinit var binding: FragmentMemoryBoxBinding
    lateinit var viewModel: MemoryViewModel
    var position1: Int = 0
    private lateinit var adapter: MemoryBoxAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMemoryBoxBinding.inflate(layoutInflater, container, false)


        val resultLauncher =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onActivityResult(result)
            }

        binding.addImageVideo.setOnClickListener {
            pickMediaFiles(resultLauncher)
        }


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

    private fun pickMediaFiles(resultLauncher: ActivityResultLauncher<Intent>) {


        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/* video/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        resultLauncher.launch(intent)
    }


    private fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK && result.data != null) {
            //If selected multiple medias
            if (result.data?.clipData != null) {
                val count: Int =
                    result.data!!.clipData!!.itemCount
                for (i in 0 until count) {
                    val selectedUri: Uri? = result.data!!.clipData?.getItemAt(i)?.uri
                }
            }
            //If selected single media
            else if (result.data?.data != null) {
                val selectedUri: Uri? = result.data?.data
            }
        }
    }

    private fun initObserver() {
        viewModel.getList().observe(viewLifecycleOwner, Observer {
            it.let {
                adapter = MemoryBoxAdapter(it as ArrayList<MemoryBox>, this)
                binding.rvMemoryBox.adapter = adapter
            }
        })
    }

    override fun onitemClcik(position: Int) {

    }

    override fun onMenuClick(position: Int, menu: AppCompatImageView) {
        position1 = position

        val popup = context?.let { PopupMenu(it, menu, Gravity.END) }
        popup?.inflate(R.menu.options_menu)
        popup?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.delete) {
                adapter.removeAt(position)
                return@setOnMenuItemClickListener true
            } else
                return@setOnMenuItemClickListener false
        }
        popup?.show()

    }

}