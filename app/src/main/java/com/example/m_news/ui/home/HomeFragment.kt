package com.example.m_news.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m_news.adapter.NewsListAdapter
import com.example.m_news.data.model.News
import com.example.m_news.databinding.FragmentHomeBinding
import com.example.m_news.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity!!.finish()
                    exitProcess(0)
                }
            })

        newsListAdapter = NewsListAdapter (
            isBookmark = false,
            onItemClicked = { news ->
                val newsJson = Json.encodeToString(news)
                val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(newsJson)
                findNavController().navigate(action)
            },
            onBookmarkClick = {news ->
                Toast.makeText(requireContext(), news.isBookmarked.toString(), Toast.LENGTH_SHORT).show()
                val position = newsListAdapter.currentList.indexOf(news)
                homeViewModel.toggleBookmark(news)
                newsListAdapter.notifyItemChanged(position)
            }
        )

        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.newsRecyclerView.adapter = newsListAdapter

        binding.tryAgain.setOnClickListener {
            homeViewModel.reloadNews()
        }

        fetchNews()
    }

    private fun fetchNews() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.news.collect { result ->
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: Result<List<News>>) {
        when (result) {
            is Result.Error -> {
                showErrorState()
            }

            Result.Loading -> {
                showLoadingState()
            }

            is Result.Success -> {
                showSuccessState(result.data)
            }
        }
    }

    private fun showErrorState() {
        binding.errorText.visibility = View.VISIBLE
        binding.tryAgain.visibility = View.VISIBLE
        binding.loading.visibility = View.GONE
    }

    private fun showLoadingState() {
        binding.errorText.visibility = View.GONE
        binding.tryAgain.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
    }

    private fun showSuccessState(newsList: List<News>) {
        binding.loading.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.tryAgain.visibility = View.GONE
        newsListAdapter.submitList(newsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
