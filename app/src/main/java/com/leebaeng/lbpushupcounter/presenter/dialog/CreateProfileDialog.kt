package com.leebaeng.lbpushupcounter.presenter.dialog

import android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.leebaeng.lbpushupcounter.databinding.DialogCreateProfileBinding

class CreateProfileDialog(context: Context) : Dialog(context) {
    val bindingDialog = DialogCreateProfileBinding.inflate(layoutInflater)

    private var customDialogClickListener: CustomDialogClickListener? = null
    private var tvTitle: TextView? = null
    private  var tvNegative:TextView? = null
    private  var tvPositive:TextView? = null

    fun OptionCodeTypeDialog(context: Context, customDialogClickListener: CustomDialogClickListener?) {
        super(context)
        this.context = context
        this.customDialogClickListener = customDialogClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.option_codetype_dialog)
        tvTitle = findViewById(R.id.option_codetype_dialog_title_tv)
        tvPositive = findViewById<TextView>(R.id.option_codetype_dialog_positive)
        tvNegative = findViewById<TextView>(R.id.option_codetype_dialog_negative)
        tvPositive.setOnClickListener(View.OnClickListener { v: View? ->
            // 저장버튼 클릭
            customDialogClickListener.onPositiveClick()
            dismiss()
        })
        tvNegative.setOnClickListener(View.OnClickListener { v: View? ->
            // 취소버튼 클릭
            customDialogClickListener.onNegativeClick()
            dismiss()
        })
    }
}