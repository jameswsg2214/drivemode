package com.wils.drivingmode.utils

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.wils.drivingmode.R

class Utils {

    companion object{

        fun progressDialog(
            contact:Context
        ):Dialog{
            val dialog = Dialog(contact)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_progress)
            return dialog
        }

    }



}