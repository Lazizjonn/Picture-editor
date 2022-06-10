package uz.gita.piceditor.presentation.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.piceditor.R
import uz.gita.piceditor.presentation.viewmodel.SplashViewModel
import uz.gita.piceditor.presentation.viewmodel.impl.SplashViewModelImpl


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(2000)
            viewModel.openNextScreen()
        }

        viewModel.openNextScreenLiveData.observe(this@SplashScreen, openNextScreenObserver)
    }

    private val openNextScreenObserver = Observer<Unit> {
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainFragment())
    }
}