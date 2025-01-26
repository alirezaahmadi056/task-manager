package info.alirezaahmadi.taskmanager.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.alirezaahmadi.taskmanager.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThemViewModel @Inject constructor() : ViewModel() {

    private val _darkThem = MutableStateFlow(Constants.isThemDark)
    val darkThem: StateFlow<Boolean> = _darkThem.asStateFlow()

    fun changeThem(dark: Boolean) {
        _darkThem.value = dark
    }
}