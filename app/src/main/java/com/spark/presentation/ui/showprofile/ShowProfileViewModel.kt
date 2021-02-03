package com.spark.presentation.ui.showprofile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spark.data.utils.Resource
import com.spark.data.utils.loadUrl
import com.spark.domain.models.ProfileEntity
import com.spark.domain.usecases.*
import kotlinx.android.synthetic.main.edit_profile_fragment.*
import kotlinx.coroutines.launch

class ShowProfileViewModel @ViewModelInject constructor(
    private val getProfile: GetProfile
) : ViewModel(), LifecycleObserver {

    val profileState = MutableLiveData<Resource<ProfileEntity>>()

    init {
        getProfile()
    }


    private fun getProfile() {
        viewModelScope.launch {
            profileState.postValue(Resource.Loading(""))
            profileState.postValue(getProfile.invoke())
        }
    }




}