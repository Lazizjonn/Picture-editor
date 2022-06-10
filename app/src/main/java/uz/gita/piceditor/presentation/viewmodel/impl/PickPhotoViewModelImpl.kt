package uz.gita.piceditor.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.piceditor.presentation.viewmodel.PickPhotoViewModel
import javax.inject.Inject

@HiltViewModel
class PickPhotoViewModelImpl @Inject constructor() : ViewModel(), PickPhotoViewModel {
    override val shareAppLiveData = MutableLiveData<Unit>()
    override val rateAppLiveData = MutableLiveData<Unit>()
    override val contactUsLiveData = MutableLiveData<Unit>()
    override val pickImageLiveData = MutableLiveData<Unit>()

    override fun shareApp() {
        shareAppLiveData.value = Unit
    }
    override fun rateApp() {
        rateAppLiveData.value = Unit
    }
    override fun contactUs() {
        contactUsLiveData.value = Unit
    }
    override fun pickImage() {
        pickImageLiveData.value = Unit
    }
}