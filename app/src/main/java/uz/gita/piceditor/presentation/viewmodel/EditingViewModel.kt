package uz.gita.piceditor.presentation.viewmodel

import android.view.MotionEvent
import androidx.lifecycle.LiveData
import uz.gita.piceditor.data.model.model.common.ImageData


interface EditingViewModel {
    val imageList: ArrayList<ImageData>
    val loadImagesToRecyclerViewLiveData: LiveData<Unit>
    val addImageSelectCoordinateLiveData: LiveData<MotionEvent>

    fun loadImagesToRecyclerView()
    fun addImageSelectCoordinate(motionEvent: MotionEvent)
}