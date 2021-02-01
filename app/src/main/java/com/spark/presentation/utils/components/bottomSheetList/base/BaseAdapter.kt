package com.spark.presentation.utils.components.bottomSheetList.base

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.spark.R
import com.spark.presentation.utils.ext.inflate
import com.spark.presentation.utils.ext.log
import java.util.*

abstract class BaseAdapter<T : BaseModel>(
    var
    items: MutableList<T>?,
    protected val callbacks: IBaseItemListener<T>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    constructor(items: MutableList<T>) : this(items, null)

    private var origList: MutableList<T>? = items

    abstract fun onBindData(view: View, item: T, position: Int)

    abstract fun onBindEmpty(view: View)

    abstract fun getLayoutResId(): Int

    open fun addItems(arrayList: MutableList<T>) {
        items = (items?.plus(arrayList))?.toMutableList()
        notifyDataSetChanged()
    }

    open fun remove(position: Int) {
        items?.removeAt(position)
        notifyItemRemoved(position)
        items?.size?.let { notifyItemRangeChanged(position, it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == EMPTY)
            VHEmpty(parent.inflate(R.layout.item_list))
        else
            return items?.let { VHItem(parent.inflate(getLayoutResId()), it, callbacks) }!!
    }

    override fun getItemCount(): Int {
        return if (items.isNullOrEmpty()) EMPTY else items?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isNullOrEmpty()) EMPTY else ITEM
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == EMPTY)
            onBindEmpty((holder as VHEmpty).itemView)
        else
            onBindData((holder as VHItem<T>).itemView, items?.get(position)!!, position)
    }

    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults? {
                val oReturn = FilterResults()
                val results: ArrayList<T> = arrayListOf()
                if (constraint.isNotEmpty()) {
                    origList?.let {
                        if (it.isNotEmpty()) {
                            for (cd in it) {
                                if (cd.attributeToSearch.toLowerCase(Locale.ROOT)
                                        .contains(constraint.toString().toLowerCase(Locale.ROOT))
                                ) results.add(cd)
                            }
                        }
                    }
                    oReturn.values = results
                    oReturn.count = results.size //newly added by ZA
                } else {
                    oReturn.values = origList
                    oReturn.count = origList?.size!! //newly added by ZA
                }
                return oReturn
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
            ) {
                items = results.values as MutableList<T>
                notifyDataSetChanged()
            }
        }
    }

    /**
     *  The ViewHolder class which binds the data to the RecyclerView item
     */
    class VHItem<T>(itemView: View, item: MutableList<T>?, callbacks: IBaseItemListener<T>?) :
        RecyclerView.ViewHolder(itemView) {

        init {
            if (callbacks != null)
                itemView.setOnClickListener {
                    callbacks.onClick(
                        adapterPosition,
                        item?.get(adapterPosition),
                        itemView
                    )
                }
        }

    }

    class VHEmpty(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        const val EMPTY = 1
        const val ITEM = 0
    }
}