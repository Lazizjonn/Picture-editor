package uz.gita.piceditor.presentation.viewmodel

import androidx.lifecycle.LiveData


interface SplashViewModel {
    val openNextScreenLiveData: LiveData<Unit>

    fun openNextScreen()
}