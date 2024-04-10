package com.gracodev.pokeapidemo.viewmodel

import androidx.lifecycle.ViewModel
import com.gracodev.data.usecaseresult.UseCaseResult
import com.gracodev.pokeapidemo.states.UIStates

open class BaseViewModel : ViewModel() {

    fun <T : Any> UseCaseResult<T>.toUIStates(): UIStates<T> {
        return when (this) {
            is UseCaseResult.Success -> com.gracodev.pokeapidemo.states.UIStates.Success(this.data)
            is UseCaseResult.Error -> com.gracodev.pokeapidemo.states.UIStates.Error(this.exception.message ?: "An error occurred")
        }
    }
}