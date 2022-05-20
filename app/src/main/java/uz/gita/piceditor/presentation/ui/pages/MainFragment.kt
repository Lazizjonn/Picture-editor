package uz.gita.piceditor.presentation.ui.pages

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.piceditor.R
import uz.gita.piceditor.databinding.FragmentMainBinding
import uz.gita.piceditor.util.checkPermissions

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val PICK_IMAGE = 1010

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        clicks()

    }

    private fun clicks() = with(binding) {
        pickImage.setOnClickListener {
            requireActivity().checkPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                val intent = Intent().setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }

        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
            val uri = bundleOf("image" to data?.data.toString())
            findNavController().navigate(R.id.editingFragment, uri)
        }
    }

}