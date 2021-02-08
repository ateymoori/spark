package com.spark.presentation.ui.showprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.data.utils.Resource
import com.spark.data.utils.log
import com.spark.domain.models.ProfileEntity
import com.spark.domain.usecases.*
import com.spark.presentation.utils.components.base.BaseViewModel
import com.spark.presentation.utils.components.base.EspressoIdlingResource
import kotlinx.coroutines.launch

class ShowProfileViewModel @ViewModelInject constructor(
    private val getProfile: GetProfile
) : BaseViewModel(), LifecycleObserver {

    val profileState = MutableLiveData<Resource<ProfileEntity>>()

//    init {
//        getProfile()
//    }

    fun getProfile() {
        viewModelScope.launch {
            profileState.postValue(Resource.Loading())
            profileState.postValue(getProfile.invoke())
        }
    }


}