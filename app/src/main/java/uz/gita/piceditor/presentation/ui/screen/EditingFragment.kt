package uz.gita.piceditor.presentation.ui.screen

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.net.toUri
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.piceditor.R
import uz.gita.piceditor.databinding.FragmentEditingBinding
import java.io.File

@AndroidEntryPoint
class EditingFragment : Fragment(R.layout.fragment_editing) {
    private val binding by viewBinding(FragmentEditingBinding::bind)
    private var imageUri = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUri = arguments?.getString("image") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setView()
        clicks()

    }

    private fun setView() {
        if (imageUri.length > 0){
//            val image = File(imageUri)
            binding.image.setImageURI(imageUri.toUri())

        }
    }

    private fun clicks() = with(binding){
    }

}