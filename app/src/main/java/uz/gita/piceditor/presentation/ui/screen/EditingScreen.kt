package uz.gita.piceditor.presentation.ui.screen

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.piceditor.R
import uz.gita.piceditor.data.model.model.common.ImageData
import uz.gita.piceditor.data.model.model.common.ViewData
import uz.gita.piceditor.databinding.ScreenEditingBinding
import uz.gita.piceditor.presentation.ui.adapter.AttributeAdapter
import uz.gita.piceditor.presentation.viewmodel.EditingViewModel
import uz.gita.piceditor.presentation.viewmodel.impl.EditingViewModelImpl
import uz.gita.piceditor.util.distance
import uz.gita.piceditor.util.px
import kotlin.math.atan2


@AndroidEntryPoint
class EditingScreen : Fragment(R.layout.screen_editing) {
    private val viewModel: EditingViewModel by viewModels<EditingViewModelImpl>()
    private val binding by viewBinding(ScreenEditingBinding::bind)
    private var imageUri = ""
    private val inflater by lazy { LayoutInflater.from(requireContext()) }
    private var selectImage: ImageData? = null
    private val attributeAdapter by lazy { AttributeAdapter() }
    private val views = ArrayList<ViewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUri = arguments?.getString("image") ?: ""
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (imageUri.isNotEmpty()) binding.image.setImageURI(imageUri.toUri())

        binding.addImage.setOnClickListener { viewModel.loadImagesToRecyclerView() }
        binding.frame.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN && selectImage != null) viewModel.addImageSelectCoordinate(motionEvent)
            return@setOnTouchListener true
        }

        viewModel.loadImagesToRecyclerViewLiveData.observe(viewLifecycleOwner, loadImagesToRecyclerViewObserver)
        viewModel.addImageSelectCoordinateLiveData.observe(viewLifecycleOwner, addImageSelectCoordinateObserver)
    }

    @SuppressLint("ClickableViewAccessibility")
    private val addImageSelectCoordinateObserver = Observer<MotionEvent> {
        val container = inflater.inflate(R.layout.container, binding.frame, false) as ConstraintLayout
        var isZoomable = false
        var firstPoint = PointF()
        var secondPoint: PointF? = null
        var firstPointAfterMove = PointF()
        var secondPointAfterMove = PointF()
        var oldDistance: Float? = null
        var oldDelta = PointF()
        var oldDegree = 0f
        var angle1 = 0.0

        // putting image to surface
        container.x = it.x - selectImage!!.defaultWidth.px / 2
        container.y = it.y - selectImage!!.defHeight.px / 2
        binding.frame.addView(container)
        val image = ImageView(requireContext())
        image.setImageResource(selectImage!!.imgRes)
        container.isSelected = true
        (container.getChildAt(1) as FrameLayout).addView(image)
        image.layoutParams.apply {
            height = selectImage!!.defHeight.px
            width = selectImage!!.defaultWidth.px
        }
        //

        // setOnClicks
        container.setOnClickListener {
            clearFrameFromOldViews()
            showFrameOfContainer(container)
        }
        container.getChildAt(0).setOnClickListener {
            binding.frame.removeView(container)
            deleteViewByClicked(container)
        }
        container.getChildAt(1).setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (!isZoomable) {
                        container.y += (motionEvent.y - firstPoint.y)
                        container.x += (motionEvent.x - firstPoint.x)
                    }
                    if (motionEvent.pointerCount == 2) {
                        isZoomable = true
                        val index1 = motionEvent.findPointerIndex(0)
                        val index2 = motionEvent.findPointerIndex(1)


                        if (secondPoint == null) {
                            secondPoint = PointF(motionEvent.getX(index2), motionEvent.getY(index2))
                            //
                            oldDelta.x = secondPoint!!.x - firstPoint.x
                            oldDelta.y = secondPoint!!.y - firstPoint.y
                            angle1 = atan2(oldDelta.y.toDouble(), oldDelta.x.toDouble())
                            //
                            oldDistance = secondPoint!! distance firstPoint
                            oldDegree = container.rotation
                        }

                        if (firstPointAfterMove.x == 0f) {
                            firstPointAfterMove.x = motionEvent.getX(index1)
                            firstPointAfterMove.y = motionEvent.getY(index1)
                            secondPointAfterMove.x = motionEvent.getX(index2)
                            secondPointAfterMove.y = motionEvent.getY(index2)
                        }

                        firstPointAfterMove.x = (motionEvent.getX(index1) + firstPointAfterMove.x) / 2
                        firstPointAfterMove.y = (motionEvent.getY(index1) + firstPointAfterMove.y) / 2
                        secondPointAfterMove.x = (motionEvent.getX(index2) + secondPointAfterMove.x) / 2
                        secondPointAfterMove.y = (motionEvent.getY(index2) + secondPointAfterMove.y) / 2


                        // find a distance and scale
                        val newDistance = PointF(firstPointAfterMove.x, firstPointAfterMove.y) distance PointF(secondPointAfterMove.x, secondPointAfterMove.y)
                        val scale = newDistance / oldDistance!! * container.scaleX
                        container.scaleX = scale
                        container.scaleY = scale
                        //

                        // rotate image
                        val newDx2 = secondPointAfterMove.x - firstPointAfterMove.x
                        val newDy2 = secondPointAfterMove.y - firstPointAfterMove.y
                        val angle2 = atan2(newDy2.toDouble(), newDx2.toDouble())
                        container.rotation = (oldDegree + (angle1 + angle2) * 180 / Math.PI).toFloat()
                        //
                    }
                }
                MotionEvent.ACTION_DOWN -> {
                    firstPoint = PointF(motionEvent.x, motionEvent.y)
                    secondPoint = null
                    clearFrameFromOldViews()
                    showFrameOfContainer(container)
                }
                MotionEvent.ACTION_UP -> {
                    secondPoint = null
                    isZoomable = false
                    oldDegree = 0f
                    oldDelta = PointF(0F, 0F)
                    angle1 = 0.0
                    firstPointAfterMove = PointF(0F, 0F)
                    secondPointAfterMove = PointF(0F, 0F)
                }
            }
            return@setOnTouchListener true
        }
        clearFrameFromOldViews()
        views.add(ViewData(container, true))
    }
    private val loadImagesToRecyclerViewObserver = Observer<Unit> {
        val imageList = viewModel.imageList
        binding.bottomList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.bottomList.adapter = attributeAdapter
        attributeAdapter.submitList(imageList)
        attributeAdapter.setSelectImageListener { pos ->
            imageList.filter { it.isSelected }.forEach { it_ -> it_.isSelected = false }
            imageList[pos].isSelected = true
            selectImage = imageList[pos]
            attributeAdapter.notifyDataSetChanged()
        }
    }

    private fun clearFrameFromOldViews() {
        views.forEach {
            if (it.isSelect) {
                it.isSelect = false
                it.viewGroup.getChildAt(1).isSelected = false
                it.viewGroup.getChildAt(0).visibility = View.GONE
            }
        }
    }
    private fun showFrameOfContainer(viewGroup: ViewGroup) {
        views.forEach { if (it.viewGroup == viewGroup) it.isSelect = true }
        viewGroup.getChildAt(1).isSelected = true
        viewGroup.getChildAt(0).visibility = View.VISIBLE
    }
    private fun deleteViewByClicked(container: ViewGroup) {
        var deleteData: ViewData? = null
        views.forEach { if (it.viewGroup == container) deleteData = it }
        deleteData?.let { views.remove(it) }
    }
}