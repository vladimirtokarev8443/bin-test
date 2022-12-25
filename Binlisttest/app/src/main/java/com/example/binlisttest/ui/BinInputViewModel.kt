package com.example.binlisttest.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlisttest.Repository
import com.example.binlisttest.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BinInputViewModel : ViewModel() {
    private val repository = Repository()

    private val uiStateMutFlow = MutableStateFlow(UiState())
    val uiStateFlow: StateFlow<UiState> = uiStateMutFlow

    fun updateUiState(number: String) {
        viewModelScope.launch {
            updateLoading(true)
            try {
                getBinInfo(number)
            } catch (e: Exception) {
                setNullBinInfo()
                Log.e("qqq", "$e")
            } finally {
                updateLoading(false)
            }
        }
    }

    private fun updateLoading(isLoading: Boolean) {
        uiStateMutFlow.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    private suspend fun getBinInfo(number: String) {
        uiStateMutFlow.update {
            it.copy(
                binInfo = repository.getBinInfo(number)
            )
        }
        updateBinList(number)
    }

    private fun updateBinList(number: String){
        //save room
        val binList = uiStateMutFlow.value.binList
        if (binList.contains(number)) return
        uiStateMutFlow.update {
            it.copy(
                binList = listOf(number) + binList
            )
        }
    }

    private fun setNullBinInfo(){
        uiStateMutFlow.update {
            it.copy(
                binInfo = null
            )
        }
    }
}