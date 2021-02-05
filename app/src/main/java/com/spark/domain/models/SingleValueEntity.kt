package com.spark.domain.models

import com.spark.data.utils.GsonUtils.toStringByGson
import com.spark.presentation.utils.components.bottomSheetList.base.BaseModel

data class SingleValueEntity(
    val title: String?=null,
    val subTitle: String? = null
) : BaseModel() {

    override val attributeToSearch: String
        get() = title.toString()

    override fun toString(): String {
        return toStringByGson()
    }
}