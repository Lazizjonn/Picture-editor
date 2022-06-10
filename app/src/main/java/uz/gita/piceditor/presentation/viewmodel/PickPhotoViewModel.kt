package uz.gita.piceditor.presentation.viewmodel

import androidx.lifecycle.LiveData


interface PickPhotoViewModel {
    val shareAppLiveData: LiveData<Unit>
    val rateAppLiveData: LiveData<Unit>
    val contactUsLiveData: LiveData<Unit>
    val pickImageLiveData: LiveData<Unit>

    fun shareApp()
    fun rateApp()
    fun contactUs()
    fun pickImage()
}