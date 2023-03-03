package com.sokoldev.griefresort.ui.fragments.account

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sokoldev.griefresort.R
import com.sokoldev.griefresort.databinding.FragmentDeleteAccountBinding
import com.sokoldev.griefresort.ui.activities.AuthActivity
import com.sokoldev.griefresort.ui.activities.HomeActivity
import com.sokoldev.griefresort.utils.Constants
import com.sokoldev.griefresort.utils.Global


class DeleteAccountFragment : Fragment() {
    private lateinit var binding: FragmentDeleteAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDeleteAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarImage = (requireActivity() as HomeActivity).binding.back
        toolbarImage.setImageResource(R.drawable.ic_back)
        toolbarImage.setOnClickListener {
            activity?.findNavController(R.id.nav_host_fragment_activity_home)?.navigateUp()
        }
        val toolbarText = (requireActivity() as HomeActivity).binding.toolbarText
        toolbarText.text = getString(R.string.delete_account)

        (requireActivity() as HomeActivity).binding.relativeLayout.visibility = View.VISIBLE

        binding.contact.setOnClickListener {
            var bundle = Bundle()
            bundle.putString(Constants.TYPE, Constants.CONTACT)
            findNavController().navigate(
                R.id.action_deleteAccountFragment_to_contactUsFragment, bundle
            )
        }

        binding.btnDeleteAccount.setOnClickListener {
            showDialog()
        }

    }

    private fun showDialog() {
        val alertDialog: android.app.AlertDialog? = android.app.AlertDialog.Builder(context)
            .setTitle("Are you sure you want to delete your account?")
            .setMessage("Deleting your account is irreversible and means that you will no longer be able to access your posts, pictures/videos, reminder notifications and more.") //set positive button
            .setPositiveButton("Delete Account",
                DialogInterface.OnClickListener { dialogInterface, i -> //set what would happen when positive button is clicked
                    dialogInterface.dismiss()
                    Global.showMessage(binding.root.rootView, "Account Deleted")
                    startActivity(Intent(context, AuthActivity::class.java))
                    activity?.finish()

                }).setNegativeButton(
                "Keep Account",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    startActivity(Intent(context, HomeActivity::class.java))
                    activity?.finish()
                }).show()
    }

}