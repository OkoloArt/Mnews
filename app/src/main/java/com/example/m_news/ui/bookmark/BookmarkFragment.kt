package com.example.m_news.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m_news.adapter.NewsListAdapter
import com.example.m_news.databinding.FragmentBookmarkBinding
import com.example.m_news.ui.viewmodel.BookmarkViewModel
import com.example.m_news.utils.Helper.toNews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.m_news.utils.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.system.exitProcess

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
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

        newsListAdapter = NewsListAdapter(
            isBookmark = true,
            onItemClicked = { news ->
                val newsJson = Json.encodeToString(news)
                val action =
                    BookmarkFragmentDirections.actionNavigationBookmarkToDetailFragment(newsJson)
                findNavController().navigate(action)
            },
            onBookmarkClick = {}
        )

        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.newsRecyclerView.adapter = newsListAdapter

        fetchBookMarkedNews()
    }


    private fun fetchBookMarkedNews() {
        viewLifecycleOwner.lifecycleScope.launch {
            bookmarkViewModel.bookmarkedArticles.collect { result ->
                when (result) {
                    is Result.Error -> {

                        Log.e("BookmarkFragment", "Error fetching bookmarks", result.error)
                    }

                    Result.Loading -> {

                        binding.loading.visibility = View.VISIBLE
                        binding.emptyNewsText.visibility = View.GONE
                    }

                    is Result.Success -> {
                        binding.loading.visibility = View.GONE
                        val newsList = result.data.map { it.toNews() }
                        if (newsList.isNotEmpty()) {
                            newsListAdapter.submitList(newsList.toList())
                            binding.emptyNewsText.visibility = View.GONE
                        } else {
                            binding.emptyNewsText.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
