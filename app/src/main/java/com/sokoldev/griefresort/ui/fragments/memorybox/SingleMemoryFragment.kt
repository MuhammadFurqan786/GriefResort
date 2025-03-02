package com.sokoldev.griefresort.ui.fragments.memorybox

import android.annotation.SuppressLint
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.data.adapters.MemoryBoxAdapter
import com.sokoldev.griefresort.data.models.MemoryBox
import com.sokoldev.griefresort.data.viewmodel.MemoryViewModel
import com.sokoldev.griefresort.databinding.FragmentSingleMemoryBinding
import com.sokoldev.griefresort.preference.PreferenceHelper
import com.sokoldev.griefresort.ui.activities.MediaPlayerActivity
import com.sokoldev.griefresort.utils.Constants
import com.sokoldev.griefresort.utils.Global

class SingleMemoryFragment : Fragment(), MemoryBoxAdapter.OnMemoryBoxItemsClickListener {

    private var _binding: FragmentSingleMemoryBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MemoryViewModel
    private lateinit var adapter: MemoryBoxAdapter
    private val storage = FirebaseStorage.getInstance().reference
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var userId: String
    private var fileType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSingleMemoryBinding.inflate(inflater, container, false)
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onActivityResult(result)
            }

        binding.layoutAddMemory.setOnClickListener {
            pickMediaFiles(resultLauncher)
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MemoryViewModel::class.java]
        val bundle = arguments
        fileType = bundle?.getString(Constants.FILE_TYPE)
        if (fileType.equals("image")) {
            binding.singleMemoryTitle.text = getString(R.string.single_memory_title, "photos")
            binding.addMemoryText.text = "Add Photos Memory"
        } else if (fileType.equals("video")) {
            binding.singleMemoryTitle.text = getString(R.string.single_memory_title, fileType + "s")
            binding.addMemoryText.text = "Add Video Memory"
        } else {
            binding.singleMemoryTitle.text = getString(R.string.single_memory_title, fileType + "s")
            binding.addMemoryText.text = "Add Audio Memory"
        }



        binding.rvMemoryBox.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMemoryBox.setHasFixedSize(true)


        userId = PreferenceHelper.getPref(requireContext())
            .getCurrentUser()?.userId!!
        viewModel.getMemoryBoxes(userId)
        showLoading(true)
        initObserver()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun pickMediaFiles(resultLauncher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/* video/* audio/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        if (fileType.equals("image")) {
            intent.type = "image/*"
        } else if (fileType.equals("video")) {
            intent.type = "video/*"
        } else if (fileType.equals("audio")) {
            intent.type = "audio/*"
        }
        resultLauncher.launch(intent)
    }

    private fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK && result.data != null) {
            val mediaUris: MutableList<Uri> = mutableListOf()

            // If selected multiple media
            result.data?.clipData?.let { clipData ->
                val count = clipData.itemCount
                for (i in 0 until count) {
                    val selectedUri = clipData.getItemAt(i).uri
                    mediaUris.add(selectedUri)
                }
            }
            // If selected a single media
            result.data?.data?.let { selectedUri ->
                mediaUris.add(selectedUri)
            }

            // Show the progress bar (loading indicator)
            showLoading(true)

            // Upload media files to Firebase Storage
            mediaUris.forEach { uri ->
                uploadMediaToStorage(uri)
            }
        }
    }

    private fun uploadMediaToStorage(uri: Uri) {
        val fileType = when {
            uri.toString().contains("image") -> "image"
            uri.toString().contains("video") -> "video"
            uri.toString().contains("audio") -> "audio"
            else -> "unknown"
        }

        val fileName = System.currentTimeMillis().toString() // Unique ID for each file
        val fileRef = storage.child("memory_box/$userId/$fileName")

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { fileUrl ->
                    viewModel.addMemoryBox(fileName, fileUrl.toString(), fileType, userId)

                    viewModel.getMemoryBoxes(userId)
                }

            }
            .addOnFailureListener {
                // Handle upload failure
                showLoading(false)
            }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun initObserver() {
        viewModel.memoryBoxes.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (fileType != null) {
                    val list = it.filter { item -> item.fileType == fileType } // Filter directly
                    adapter = MemoryBoxAdapter(list as ArrayList<MemoryBox>, this)
                    binding.rvMemoryBox.adapter = adapter
                }
            }
            showLoading(false)
        })

        viewModel.response.observe(viewLifecycleOwner, Observer {
            it?.let { isUplaoded ->
                if (isUplaoded) {
                    showLoading(false)
                    Global.showMessage(
                        binding.root.rootView,
                        "Memory Upload Successfully!",
                        Snackbar.LENGTH_SHORT
                    )
                } else {
                    showLoading(false)
                    Global.showMessage(
                        binding.root.rootView,
                        "Memory Failed to Upload!",
                        Snackbar.LENGTH_SHORT
                    )
                }
            }
        })

        viewModel.delete.observe(viewLifecycleOwner, Observer {
            it?.let { isDeleted ->
                if (isDeleted) {
                    showLoading(false)
                    Global.showMessage(
                        binding.root.rootView,
                        "Memory Deleted Successfully!",
                        Snackbar.LENGTH_SHORT
                    )
                } else {
                    showLoading(false)
                    Global.showMessage(
                        binding.root.rootView,
                        "Memory Failed to Delete!",
                        Snackbar.LENGTH_SHORT
                    )
                }
            }
        })
    }

    override fun onitemClcik(position: Int) {
        val allMemoryBoxes = viewModel.memoryBoxes.value ?: emptyList()
        val filteredList = allMemoryBoxes.filter { it.fileType == fileType }
        val memoryBox = filteredList.getOrNull(position) // Get item from the full list
        val filteredItem = filteredList.find { it.fileUrl == memoryBox?.fileUrl }

        filteredItem?.let {
            when (it.fileType) {
                "image" -> {
                    val intent = Intent(requireContext(), MediaPlayerActivity::class.java)
                    intent.putExtra(Constants.MEDIA_URL, it.fileUrl)
                    intent.putExtra(Constants.FILE_TYPE, it.fileType)
                    startActivity(intent)
                }

                "video" -> {
                    val intent = Intent(requireContext(), MediaPlayerActivity::class.java)
                    intent.putExtra(Constants.MEDIA_URL, it.fileUrl)
                    intent.putExtra(Constants.FILE_TYPE, it.fileType)
                    startActivity(intent)
                }

                "audio" -> {
                    // Play audio
                    val intent = Intent(requireContext(), MediaPlayerActivity::class.java)
                    intent.putExtra(Constants.MEDIA_URL, it.fileUrl)
                    intent.putExtra(Constants.FILE_TYPE, it.fileType)
                    startActivity(intent)
                }
            }
        }
    }


    override fun onMenuClick(position: Int, menu: AppCompatImageView) {
        val popup = context?.let { PopupMenu(it, menu, Gravity.END) }
        popup?.inflate(R.menu.options_menu)
        popup?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.delete) {
                viewModel.deleteMemoryBox(position, userId)
                return@setOnMenuItemClickListener true
            } else
                return@setOnMenuItemClickListener false
        }
        popup?.show()
    }

}