package com.spark.presentation.ui.editprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.data.utils.Resource
import com.spark.domain.models.ProfileEntity
import com.spark.domain.models.SingleValueEntity
import com.spark.domain.usecases.GetEthnicities
import com.spark.domain.usecases.GetMaritalList
import com.spark.domain.usecases.GetProfile
import com.spark.domain.usecases.GetReligions
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditProfileViewModel @ViewModelInject constructor(
    private val getEthnicities: GetEthnicities,
    private val getReligions: GetReligions,
    private val getMaritalList: GetMaritalList,
    private val getProfile: GetProfile
) : ViewModel(), LifecycleObserver {

    val ethnicities = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val religions = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val maritalList = MutableLiveData<Resource<List<SingleValueEntity>>>()
    val profile = MutableLiveData<Resource<ProfileEntity>>()

    init {
        getEnthnics()
        getReligions()
        getMaritalList()
        getProfile()
    }

    private fun getEnthnics() {
        viewModelScope.launch {
            getEthnicities.getData().collect {
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

    private fun getMaritalList() {
        viewModelScope.launch {
            getMaritalList.getData().collect {
                maritalList.postValue(it)
            }
        }
    }
    private fun getProfile() {
        viewModelScope.launch {
            getProfile.getData().collect {
                profile.postValue(it)
            }
        }
    }


}