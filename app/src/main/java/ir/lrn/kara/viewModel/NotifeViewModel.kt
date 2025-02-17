package ir.lrn.kara.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.lrn.kara.data.model.notif.NotifeResponse
import ir.lrn.kara.repository.NotifeApiInterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifeViewModel @Inject constructor(
    private val repository: NotifeApiInterRepository
) : ViewModel() {

    val notifeResponse:MutableStateFlow<NotifeResponse> = repository.notifeResponse


    fun senMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.senMessage(message)
        }
    }

    fun resetResponse(){
        viewModelScope.launch {
            notifeResponse.emit(NotifeResponse())
        }
    }

}