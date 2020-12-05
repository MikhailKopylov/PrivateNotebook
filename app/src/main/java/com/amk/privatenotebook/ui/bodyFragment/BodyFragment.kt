package com.amk.privatenotebook.ui.bodyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amk.privatenotebook.databinding.FragmentBodyBinding
import com.amk.privatenotebook.presentation.BodyViewModel


class BodyFragment : Fragment() {

    private val toBodyViewModel by lazy(LazyThreadSafetyMode.NONE) {
        activity?.let { ViewModelProvider(it).get(BodyViewModel::class.java) }
    }

    private var _binding: FragmentBodyBinding? = null
    private val binding: FragmentBodyBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBodyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toBodyViewModel?.subtopicLiveData()?.observe(viewLifecycleOwner) {
            if (it != null) {
                with(binding) {
                    subtopicEditText.setText(it.subtopicName)
                    bodyEditText.setText(it.body)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            val subtopicName = subtopicEditText.text.toString()
            val body = bodyEditText.text.toString()
            toBodyViewModel?.getNoteById()?.observe(viewLifecycleOwner) {
                if (it != null) {
                    toBodyViewModel?.onUpdate(it, subtopicName, body)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}