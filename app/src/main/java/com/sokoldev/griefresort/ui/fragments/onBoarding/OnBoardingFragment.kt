package com.sokoldev.griefresort.ui.fragments.onBoarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentOnBoardingBinding
import com.sokoldev.griefresort.databinding.OnboardingDesignBinding
import com.sokoldev.griefresort.ui.fragments.onBoarding.OnBoardingFragment.Companion.MAX_STEP
import com.sokoldev.griefresort.ui.fragments.subscription.UnlockActivity
import com.sokoldev.griefresort.utils.animation.ZoomOutPageTransformer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OnBoardingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnBoardingFragment : Fragment() {
    private var _binding: FragmentOnBoardingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2.adapter = AppIntroViewPager2Adapter()

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
        }.attach()

        binding.viewPager2.setPageTransformer(ZoomOutPageTransformer())

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == MAX_STEP - 1) {
                    binding.btnNext.visibility = View.VISIBLE
                } else {
                    binding.btnNext.visibility = View.GONE
                }
            }
        })


        binding.btnNext.setOnClickListener {
            startActivity(Intent(context, UnlockActivity::class.java))
        }
    }

    companion object {
        const val MAX_STEP = 6
    }
}


/*.+.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-..-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-*/


class AppIntroViewPager2Adapter : RecyclerView.Adapter<PagerVH2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH2 {
        return PagerVH2(
            OnboardingDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //get the size of color array
    override fun getItemCount(): Int = MAX_STEP // Int.MAX_VALUE

    //binding the screen with view
    override fun onBindViewHolder(holder: PagerVH2, position: Int) = holder.itemView.run {

        with(holder) {
            if (position == 0) {
                bindingDesign.introTitle.text = context.getString(R.string.title1)
                bindingDesign.introDescription.text = context.getString(R.string.description1)
                bindingDesign.introImage.setImageResource(R.drawable.img_group_hug)
            }
            if (position == 1) {
                bindingDesign.introTitle.text = context.getString(R.string.title2)
                bindingDesign.introDescription.text = context.getString(R.string.description2)
                bindingDesign.introImage.setImageResource(R.drawable.img_memory_box)
            }
            if (position == 2) {
                bindingDesign.introTitle.text = context.getString(R.string.title2)
                bindingDesign.introDescription.text = context.getString(R.string.description2)
                bindingDesign.introImage.setImageResource(R.drawable.img_memory_box_2)
            }
            if (position == 3) {
                bindingDesign.introTitle.text = context.getString(R.string.title3)
                bindingDesign.introDescription.text = context.getString(R.string.description3)
                bindingDesign.introImage.setImageResource(R.drawable.img_remind_me)
            }
            if (position == 4) {
                bindingDesign.introTitle.text = context.getString(R.string.title4)
                bindingDesign.introDescription.text = context.getString(R.string.description4)
                bindingDesign.introImage.setImageResource(R.drawable.img_library)
            }
            if (position == 5) {
                bindingDesign.introTitle.text = context.getString(R.string.title6)
                bindingDesign.introDescription.text = context.getString(R.string.description6)
                bindingDesign.introImage.setImageResource(R.drawable.img_my_diary)
            }
        }
    }
}

class PagerVH2(val bindingDesign: OnboardingDesignBinding) :
    RecyclerView.ViewHolder(bindingDesign.root)