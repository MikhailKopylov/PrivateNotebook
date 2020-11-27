package com.amk.privatenotebook.ui.dialogFragmens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amk.privatenotebook.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_add_new_header.*
import kotlinx.android.synthetic.main.dialog_add_new_header.view.*

class AddNewHeaderDialog(private val onDialogListener: OnDialogListener) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.dialog_add_new_header, container, false)
        isCancelable = true

        view.ok_button.setOnClickListener {
            onDialogListener.onDialogOK(addNewHeaderEditText.text.toString())
            dismiss()
        }

        view.cancel_button.setOnClickListener{
            dismiss()
        }

        return view
    }
}