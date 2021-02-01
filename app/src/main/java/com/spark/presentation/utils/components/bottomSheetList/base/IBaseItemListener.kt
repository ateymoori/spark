package com.spark.presentation.utils.components.bottomSheetList.base

import android.view.View

interface IBaseItemListener<T> {
    fun onClick(position: Int?, model: T?, viewId: View?)
}