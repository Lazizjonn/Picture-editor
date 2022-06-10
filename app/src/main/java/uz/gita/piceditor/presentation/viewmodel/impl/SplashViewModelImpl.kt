package uz.gita.piceditor.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.piceditor.domain.repository.AppRepository
import uz.gita.piceditor.presentation.viewmodel.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), SplashViewModel {
    override val openNextScreenLiveData = MutableLiveData<Unit>()

    override fun openNextScreen() {
        repository.loadData()
        openNextScreenLiveData.value = Unit
    }
}