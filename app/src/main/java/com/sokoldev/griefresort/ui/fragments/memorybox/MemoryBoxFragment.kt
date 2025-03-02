package com.sokoldev.griefresort.ui.fragments.memorybox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentMemoryBoxBinding
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants

class MemoryBoxFragment : Fragment() {

    private lateinit var binding: FragmentMemoryBoxBinding


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



        binding.layoutAudio.setOnClickListener {
            binding.layoutAudio.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title_selected)
            binding.layoutVideo.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title)
            binding.layoutPhoto.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title)

            binding.textViewAudio.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.textViewVideo.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.textViewPhoto.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            binding.textViewAudioDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.textViewVideoDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.textViewPhotoDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            val bundle = Bundle()
            bundle.putString(Constants.FILE_TYPE, "audio")
            findNavController().navigate(R.id.action_memoryBox_to_singleMemoryFragment, bundle)

        }

        binding.layoutPhoto.setOnClickListener {

            binding.layoutPhoto.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title_selected)
            binding.layoutVideo.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title)
            binding.layoutAudio.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title)

            binding.textViewPhoto.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.textViewVideo.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.textViewAudio.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            binding.textViewPhotoDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.textViewVideoDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.textViewAudioDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            val bundle = Bundle()
            bundle.putString(Constants.FILE_TYPE, "image")
            findNavController().navigate(R.id.action_memoryBox_to_singleMemoryFragment, bundle)

        }

        binding.layoutVideo.setOnClickListener {

            binding.layoutVideo.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title_selected)
            binding.layoutPhoto.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title)
            binding.layoutAudio.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.bg_title)

            binding.textViewVideo.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.textViewPhoto.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.textViewAudio.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            binding.textViewVideoDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.textViewPhotoDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.textViewAudioDesc.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )

            val bundle = Bundle()
            bundle.putString(Constants.FILE_TYPE, "video")
            findNavController().navigate(R.id.action_memoryBox_to_singleMemoryFragment, bundle)

        }


    }


}
