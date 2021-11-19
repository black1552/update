package com.liuchang.view

import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.liuchang.update.R

class BottonDialog(content: Context) {
    lateinit var builder: AlertDialog.Builder
    lateinit var content: Context
    lateinit var view: View
    lateinit var btn1: Button
    lateinit var btn2: Button
    lateinit var cancelBtn: Button
    lateinit var dialog: AlertDialog

    init {
        this.content = content
        builder = AlertDialog.Builder(content)
        view = LayoutInflater.from(content).inflate(R.layout.dialog_button, null)
    }

    fun setBtnOne(name: String, listener: View.OnClickListener) {
        btn1 = view.findViewById(R.id.open_carme)
        btn1.text = name
        btn1.setOnClickListener(listener)
    }

    fun setBtnTwo(name: String, listener: View.OnClickListener) {
        btn2 = view.findViewById(R.id.open_pic)
        btn2.text = name
        btn2.setOnClickListener(listener)
    }

    fun dismiss(){
        dialog.dismiss()
    }

    fun show() {
        dialog = builder.create()
        dialog.show()
        cancelBtn = view.findViewById(R.id.close)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        val window = dialog.window
        window!!.setContentView(view)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setGravity(Gravity.BOTTOM)
        window.setLayout(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
}
