package com.amk.privatenotebook.ui.bodyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.R
import com.amk.privatenotebook.core.Note
import com.amk.privatenotebook.presentation.FromBodyViewModel
import com.amk.privatenotebook.presentation.ToBodyViewModel
import kotlinx.android.synthetic.main.fragment_body.*


class BodyFragment : Fragment() {

    private lateinit var note: Note

    private val toBodyViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(ToBodyViewModel::class.java) }
    }

    private val fromBodyViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(FromBodyViewModel::class.java) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_body, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toBodyViewModel?.subtopicLiveData?.observe(viewLifecycleOwner) {
            note = it.note
            subtopic_edit_text.setText(it.subtopicName)
            body_edit_text.setText(it.body)
            fromBodyViewModel?.select(it)
        }
    }

    override fun onPause() {
        super.onPause()
        val subtopicName = subtopic_edit_text.text.toString()
        val body = body_edit_text.text.toString()
        fromBodyViewModel?.onUpdate(subtopicName, body)
    }
}