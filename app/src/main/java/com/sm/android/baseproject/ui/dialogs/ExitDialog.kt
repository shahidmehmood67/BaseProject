package com.sm.android.baseproject.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sm.android.baseproject.databinding.DialogExitBinding
import com.sm.android.baseproject.listeners.OnDialogDismissListener
import com.sm.android.baseproject.utils.animExpandCollapse
import com.sm.android.baseproject.utils.setSafeOnClickListener

class ExitDialog : DialogFragment() {

    private lateinit var dialogLayoutBinding: DialogExitBinding

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    private var listener: OnDialogDismissListener? = null

    fun setOnDialogDismissListener(listener: OnDialogDismissListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dialogLayoutBinding = DialogExitBinding.inflate(inflater, container, false)


        with(dialogLayoutBinding)
        {


            tvContinueBtn.setSafeOnClickListener {
                it.animExpandCollapse {
                    dismiss()
                    listener?.onPositiveButton()
                    requireActivity().finish()
                    requireActivity().finishAffinity()
                }
            }

            tvCancel.setSafeOnClickListener {
                it.animExpandCollapse {
                    dismiss()
                    listener?.onNegativeButton()
                }
            }
        }

        return dialogLayoutBinding.root
    }
}