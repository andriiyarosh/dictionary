package com.work.dictionarry.presentation.info

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.dictionarry.R
import com.work.dictionarry.model.networking.models.UrbanWord
import com.work.dictionarry.model.networking.models.Word
import com.work.dictionarry.presentation.addition.WordAdditionsFragment
import kotlinx.android.synthetic.main.fragment_word_info.*

class WordInfoFragment : Fragment(R.layout.fragment_word_info) {

    private val viewModel: InfoViewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            ViewModelProvider.NewInstanceFactory()
        )[InfoViewModel::class.java]
    }
    private val args by navArgs<WordInfoFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getWordInfo(args.word, args.api)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        definitionsList.layoutManager = LinearLayoutManager(requireContext())
        definitionsList.adapter = DefinitionsListAdapter()
        viewModel.loadingState.observe(viewLifecycleOwner, Observer(::handleLoadingStater))
        viewModel.definitions.observe(viewLifecycleOwner, Observer(::handleLoadingDefinitions))
        rhymes.setOnClickListener {
            findNavController().navigate(
                WordInfoFragmentDirections.additionsFragmentAction(
                    args.word,
                    WordAdditionsFragment.AdditionType.RHYMES.ordinal
                )
            )
        }
        synonyms.setOnClickListener {
            findNavController().navigate(
                WordInfoFragmentDirections.additionsFragmentAction(
                    args.word,
                    WordAdditionsFragment.AdditionType.SYNONYM.ordinal
                )
            )
        }
    }

    private fun handleLoadingDefinitions(list: List<String>) {
        setDefinitions(list)
    }

    private fun handleLoadingStater(states: InfoViewModel.LoadingState) {
        when (states) {
            is InfoViewModel.LoadingState.Loaded -> {
                toggleLoadingView(false)
                setData(states.word)
            }
            is InfoViewModel.LoadingState.Loading -> toggleLoadingView(true)
            is InfoViewModel.LoadingState.Error -> toggleLoadingView(false)
        }
    }

    private fun toggleLoadingView(show: Boolean) {
        group.isVisible = !show
        progress.isVisible = show
    }

    private fun setData(w: Word) {
        word.text = w.word
        pronunciation.text = getString(R.string.pronunciation, w.pronunciation?.let {it.pronunciation} ?: "")
        syllables.text = getString(R.string.syllables, w.syllables?.let {
            it.list.joinToString("-")
        }?: "")
    }

    private fun setDefinitions(w: List<String>) {
        (definitionsList.adapter as DefinitionsListAdapter).setDefinitions(w)
    }
}