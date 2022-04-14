package com.leebaeng.lbpushupcounter.presenter.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.leebaeng.lbpushupcounter.data.db.DataBase
import com.leebaeng.lbpushupcounter.data.model.entity.User
import com.leebaeng.lbpushupcounter.databinding.DialogCreateProfileBinding

class CreateProfileDialog(context: Context, val db: DataBase) : Dialog(context) {
    private lateinit var bindingDialog : DialogCreateProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDialog = DialogCreateProfileBinding.inflate(layoutInflater)
        bindingDialog.dialog = this
        setContentView(bindingDialog.root)
    }

    fun onClick(view:View){
        when(view){
            bindingDialog.btnOK -> {
                val nickName = bindingDialog.edtNickname.text
                val user = User(nickName.toString() + System.currentTimeMillis(), nickName.toString(), bindingDialog.edtTargetCount.toString().toInt())
                db.userDao().setUser(user)
                dismiss()
            }
        }
    }
}