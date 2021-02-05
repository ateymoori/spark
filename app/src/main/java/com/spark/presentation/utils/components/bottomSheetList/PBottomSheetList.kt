package com.spark.presentation.utils.components.bottomSheetList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.R
import com.spark.presentation.utils.ext.height
import com.spark.presentation.utils.ext.hideKeyboard
import com.spark.presentation.utils.components.bottomSheetList.base.BaseAdapter
import kotlinx.android.synthetic.main.bottom_sheet_list_simple.*

class PBottomSheetList : BottomSheetDialogFragment(), TextWatcher {

    companion object {
        fun newInstance(keyword: String): PBottomSheetList = PBottomSheetList().apply {
            arguments = Bundle().apply {
                putString("keyword", keyword)
            }
        }
    }

    private lateinit var adapter: BaseAdapter<*>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_list_simple, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = arguments
        if (bundle != null)
            searchEdt.setText(bundle.getString("keyword"))

        btmRv.layoutParams.height = (context.height() * 0.7).toInt()

        btmRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        btmRv.adapter = adapter

        searchEdt.addTextChangedListener(this)

        dialog?.window.let { it?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE) }
    }

    override fun onDestroy() {
        activity.hideKeyboard()
        super.onDestroy()
    }

    fun setAdapter(adapter: BaseAdapter<*>) {
        this.adapter = adapter
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        adapter.filter?.filter(searchEdt.text)
    }

}