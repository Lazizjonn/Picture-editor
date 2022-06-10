package uz.gita.piceditor.presentation.ui.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.piceditor.R
import uz.gita.piceditor.databinding.ScreenPickPhotoBinding
import uz.gita.piceditor.presentation.viewmodel.PickPhotoViewModel
import uz.gita.piceditor.presentation.viewmodel.impl.PickPhotoViewModelImpl
import uz.gita.piceditor.util.checkPermissions

@AndroidEntryPoint
class PickPhotoScreen : Fragment(R.layout.screen_pick_photo) {
    private val binding by viewBinding(ScreenPickPhotoBinding::bind)
    private val viewModel: PickPhotoViewModel by viewModels<PickPhotoViewModelImpl>()
    private val PICK_IMAGE = 1010

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            val uri = bundleOf("image" to data?.data.toString())
            findNavController().navigate(R.id.editingFragment, uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        allClicks()
        viewModel.shareAppLiveData.observe(viewLifecycleOwner, shareAppObserver)
        viewModel.rateAppLiveData.observe(viewLifecycleOwner, rateAppObserver)
        viewModel.contactUsLiveData.observe(viewLifecycleOwner, contactUsObserver)
        viewModel.pickImageLiveData.observe(viewLifecycleOwner, pickImageObserver)
    }

    private val shareAppObserver = Observer<Unit> {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val packageName = "developer?id=uz.gita.italiandictionary"    // GITA+Dasturchilar+Akademiyasi"  "details?id=uz.gita.wooden15puzzleapp"
        val shareBody = "http://play.google.com/store/apps/$packageName"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Our Apps")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }
    private val rateAppObserver = Observer<Unit> {
        val packageName = "developer?id=uz.gita.italiandictionary"    // GITA+Dasturchilar+Akademiyasi" // "details?id=uz.gita.wooden15puzzleapp"
        val uri: Uri = Uri.parse("market://$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/$packageName")))
        }
    }
    private val contactUsObserver = Observer<Unit> {
        val email = "suyunovlaziz1997@gmail.com"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(email))
        intent.data = Uri.parse("mailto:")
        startActivity(intent)
    }
    private val pickImageObserver = Observer<Unit> {
        requireActivity().checkPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            val intent = Intent().setType("image/*")
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
    }

    private fun allClicks() = with(binding) {
        pickImage.setOnClickListener {
            viewModel.pickImage()
        }
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.menu_home -> {}
                R.id.share -> viewModel.shareApp()
                R.id.rate_us -> viewModel.rateApp()
                R.id.contact -> viewModel.contactUs()
            }
            true
        }
        mainMenuButton.setOnClickListener(View.OnClickListener {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.openDrawer(GravityCompat.START)
            else drawerLayout.closeDrawer(GravityCompat.END)
        })
    }

}