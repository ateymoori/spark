package com.spark.presentation.utils.components.base

import android.view.View
import com.spark.R
import com.spark.domain.models.SingleValueEntity
import com.spark.presentation.utils.components.bottomSheetList.base.BaseAdapter
import com.spark.presentation.utils.components.bottomSheetList.base.IBaseItemListener
import kotlinx.android.synthetic.main.item_list.view.*

class SingleValueAdapter(
    items: MutableList<SingleValueEntity>?,
    callbacks: IBaseItemListener<SingleValueEntity>?
) :
    BaseAdapter<SingleValueEntity>(items, callbacks) {

    override fun onBindEmpty(view: View) {
    }

    override fun getLayoutResId(): Int {
        return R.layout.item_list
    }


    override fun onBindData(view: View, item: SingleValueEntity, position: Int) {
        view.title.text = item.title

    }

}