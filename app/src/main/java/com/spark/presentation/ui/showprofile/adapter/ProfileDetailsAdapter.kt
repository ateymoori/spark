package com.spark.presentation.ui.showprofile.adapter

import android.view.View
import com.spark.R
import com.spark.domain.models.SingleValueEntity
import com.spark.presentation.utils.components.bottomSheetList.base.BaseAdapter
import com.spark.presentation.utils.components.bottomSheetList.base.IBaseItemListener
import kotlinx.android.synthetic.main.item_list.view.title
import kotlinx.android.synthetic.main.profile_details_item.view.*

class ProfileDetailsAdapter(
    items: MutableList<SingleValueEntity>?,
    callbacks: IBaseItemListener<SingleValueEntity>?
) :
    BaseAdapter<SingleValueEntity>(items, callbacks) {

    override fun onBindEmpty(view: View) {
    }

    override fun getLayoutResId(): Int {
        return R.layout.profile_details_item
    }

    override fun onBindData(view: View, item: SingleValueEntity, position: Int) {
        view.title.text = item.title
        view.subtitle.text = item.subTitle
    }

}