package com.spark.presentation.ui.editprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.data.utils.Resource
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.usecases.GetEnthnicCities
import com.spark.domain.usecases.GetReligions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditProfileViewModel @ViewModelInject constructor(
    private val getEnthnicCities: GetEnthnicCities ,
    private val getReligions: GetReligions
) : ViewModel(), LifecycleObserver {

    val ethnicities = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val religions = MutableLiveData<Resource<List<SingleValueEntity>>>()

    init {
        getEnthnics()
        getReligions()
    }

    private fun getEnthnics() {
        viewModelScope.launch {
            getEnthnicCities.getData().collect {
                ethnicities.postValue(it)
            }
        }
    }

    private fun getReligions() {
        viewModelScope.launch {
            getReligions.getData().collect {
                religions.postValue(it)
            }
        }
    }


}