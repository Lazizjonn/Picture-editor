package uz.gita.piceditor.presentation.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.piceditor.R
import uz.gita.piceditor.data.model.model.common.ImageData
import uz.gita.piceditor.data.model.model.common.ViewData
import uz.gita.piceditor.databinding.FragmentEditingBinding
import uz.gita.piceditor.util.px

@AndroidEntryPoint
class EditingFragment : Fragment(R.layout.fragment_editing) {
    private val binding by viewBinding(FragmentEditingBinding::bind)
    private var imageUri = ""
    private val inflater by lazy { LayoutInflater.from(requireActivity()) }
    private var selectImage: ImageData? = null
    private val viewList = ArrayList<ViewData>()

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

    @SuppressLint("ClickableViewAccessibility")
    private fun clicks() = with(binding){

        image.setOnTouchListener { v, event ->
            true
        }

        item1.setOnClickListener {
            if (selectImage != null){
                selectImage!!.isSelect = false
                selectImage = null
            }
            selectImage = ImageData(1, R.drawable.emoji_icon, 120, 120, imageName = "emoji")
        }
        item2.setOnClickListener {
            if (selectImage != null){
                selectImage!!.isSelect = false
                selectImage = null
            }
            selectImage = ImageData(2, com.vinaygaba.creditcardview.R.drawable.visa, 100, 130, imageName = "Visa")
        }
        item3.setOnClickListener {
            if (selectImage != null){
                selectImage!!.isSelect = false
                selectImage = null
            }
            selectImage = ImageData(3, com.vinaygaba.creditcardview.R.drawable.round_rect, 120, 120, imageName = "Rect")
        }

        binding.image.setOnTouchListener { v, event ->
            Log.d("TAG", "editing addImageSelectCoordinate: selectImage" + selectImage.toString())
            if (event.action == MotionEvent.ACTION_DOWN && selectImage != null){
                addImageSelectCoordinate(event)
            }

            true
        }

    }

    private fun addImageSelectCoordinate(event: MotionEvent){
        val container = inflater.inflate(R.layout.container, binding.frame, false) as FrameLayout
        container.x = event.x - selectImage!!.defWidth.px/3
        container.y = event.y - selectImage!!.defHeight.px/4
        binding.frame.addView(container)
        val imageView = ImageView(requireContext())
        selectImage!!.isSelect = true
        container.isSelected = true
        imageView.setImageResource(selectImage!!.imageRes)
        (container.getChildAt(1) as FrameLayout).addView(imageView)
        imageView.layoutParams.apply {
            height = selectImage!!.defHeight
            width = selectImage!!.defWidth
        }
        clearOldFrame()     // unselect
        viewList.add(ViewData(container, true))


        container.getChildAt(0).setOnClickListener {
            deleteView(container)  // deleting
        }
        onMove(container)   // moving

    }

    private fun onMove(container: FrameLayout) {
        var old_x = 0f
        var old_y = 0f
        container.getChildAt(1).setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    old_x = event.x
                    old_y = event.y

                    clearOldFrame()                         // unselect
                    returnOldFrame(container as ViewGroup)  // select
                }
                MotionEvent.ACTION_MOVE -> {
                    val dX = event.x - old_x
                    val dY = event.y - old_y
                    container.x += dX
                    container.y += dY
                }
                MotionEvent.ACTION_UP -> { }

            }

            return@setOnTouchListener true
        }
    }

    private fun deleteView(container: FrameLayout) {
        var deletingData: ViewData? = null
        viewList.filter { it.viewGroup == container }.forEach {
            deletingData = it
        }
        viewList.remove(deletingData)
        binding.frame.removeView(container)
    }

    private fun clearOldFrame(){
        viewList.filter { it.isChosen }.forEach {
            it.isChosen = false
            it.viewGroup.getChildAt(1).isSelected = false
            it.viewGroup.getChildAt(0).isVisible = false
        }
    }

    private fun returnOldFrame(viewGr: ViewGroup){
        viewList.filter { it.viewGroup == viewGr }.forEach {
            it.isChosen = true
            it.viewGroup.getChildAt(1).isSelected = true
            it.viewGroup.getChildAt(0).isVisible = true
        }

    }
}