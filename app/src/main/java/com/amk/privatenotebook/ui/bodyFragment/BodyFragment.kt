package com.amk.privatenotebook.ui.bodyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amk.privatenotebook.databinding.FragmentBodyBinding
import com.amk.privatenotebook.presentation.BodyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BodyFragment : Fragment() {

//    private val toBodyViewModel by (activity)?.viewModel<BodyViewModel>() ?: viewModel()

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
        val toBodyViewModel by (activity)?.viewModel<BodyViewModel>() ?: viewModel()
        toBodyViewModel.subtopicLiveData().observe(viewLifecycleOwner) {
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
            val toBodyViewModel by (activity)?.viewModel<BodyViewModel>() ?: viewModel()
            toBodyViewModel.getNoteById().observe(viewLifecycleOwner) {
                if (it != null) {
                    toBodyViewModel.onUpdate(it, subtopicName, body)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}