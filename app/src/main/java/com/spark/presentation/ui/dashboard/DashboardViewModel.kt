package com.spark.presentation.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.data.utils.*
import com.spark.domain.usecases.AppSettingUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class DashboardViewModel @ViewModelInject constructor(
    private val appSettingUseCase: AppSettingUseCase
) : ViewModel(), LifecycleObserver {

    val firstName = MutableLiveData<String>()

    fun test() {
        viewModelScope.launch {

            appSettingUseCase.getData().collect {

                it.onSuccess { firstName.postValue(it?.facebook) }

            }

        }
    }


}