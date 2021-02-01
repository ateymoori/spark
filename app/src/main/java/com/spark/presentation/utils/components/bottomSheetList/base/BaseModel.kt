package com.spark.presentation.utils.components.bottomSheetList.base

import com.spark.data.utils.GsonUtils.toStringByGson


abstract class BaseModel {

    abstract val attributeToSearch: String

    override fun toString(): String {
        return toStringByGson()
    }

}