package uz.gita.piceditor.presentation.viewmodel.impl

import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.piceditor.data.model.model.common.ImageData
import uz.gita.piceditor.domain.repository.AppRepository
import uz.gita.piceditor.presentation.viewmodel.EditingViewModel
import javax.inject.Inject

@HiltViewModel
class EditingViewModelImpl @Inject constructor(
    repository: AppRepository
) : ViewModel(), EditingViewModel {
    override val imageList: ArrayList<ImageData> = repository.imageList
    override val loadImagesToRecyclerViewLiveData = MutableLiveData<Unit>()
    override val addImageSelectCoordinateLiveData = MutableLiveData<MotionEvent>()

    override fun loadImagesToRecyclerView() {
        loadImagesToRecyclerViewLiveData.value = Unit
    }

    override fun addImageSelectCoordinate(motionEvent: MotionEvent) {
        addImageSelectCoordinateLiveData.value = motionEvent
    }
}