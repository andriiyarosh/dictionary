package com.work.dictionarry.presentation.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.work.dictionarry.R
import com.work.dictionarry.presentation.info.InfoViewModel
import kotlinx.android.synthetic.main.fragment_main_search.*

class MainSearchFragment : Fragment(R.layout.fragment_main_search), SearchView.OnQueryTextListener {

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SearchViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        viewModel.wordLd.observe(viewLifecycleOwner, Observer { handleLoadingProgress(it) })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            viewModel.findWord(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun handleLoadingProgress(progress: SearchViewModel.DataLoadingProgress) {
        when (progress) {
            is SearchViewModel.DataLoadingProgress.Loading -> {
                toggleLoadingView(true)
                emptyView.isVisible = false
            }
            is SearchViewModel.DataLoadingProgress.Error -> {
                toggleLoadingView(false)
                emptyView.isVisible = true
            }
            is SearchViewModel.DataLoadingProgress.Loaded -> {
                toggleLoadingView(false)
                emptyView.isVisible = false
                word.text = progress.words.firstOrNull()?.word
                syllables.text = requireContext().getString(
                    R.string.syllables,
                    progress.words.firstOrNull()?.syllables?.list?.joinToString("-")
                )
                pronunciation.text = requireContext().getString(
                    R.string.pronunciation,
                    progress.words.firstOrNull()?.pronunciation?.pronunciation
                )
                group.isVisible = true
                wordApi.setOnClickListener {
                    progress.words.firstOrNull()?.word?.let {
                        onWordClicked(
                            it
                        )
                    }
                }
                urban.setOnClickListener {
                    progress.words.firstOrNull()?.word?.let {
                        onWordClickedUrban(it)
                    }
                }
            }
        }
    }

    private fun initToolbar() {
        val searchItem: SearchView = toolbar.menu.findItem(R.id.search).actionView as SearchView
        searchItem.queryHint = getString(R.string.find_word)
        searchItem.setOnQueryTextListener(this)
        searchItem.isIconified = false
    }

    private fun toggleLoadingView(isLoading: Boolean) {
        progress.isVisible = isLoading
        group.isVisible = !isLoading
    }

    private fun onWordClickedUrban(word: String) {
        findNavController().navigate(
            MainSearchFragmentDirections.searchFragmentAction(
                word,
                InfoViewModel.ApiType.URBAN.ordinal
            )
        )
    }

    private fun onWordClicked(word: String) {
        findNavController().navigate(
            MainSearchFragmentDirections.searchFragmentAction(
                word,
                InfoViewModel.ApiType.WORD_API.ordinal
            )
        )
    }
}