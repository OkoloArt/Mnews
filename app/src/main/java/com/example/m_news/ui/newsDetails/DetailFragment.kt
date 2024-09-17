package com.example.m_news.ui.newsDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.m_news.R
import com.example.m_news.data.model.News
import com.example.m_news.database.NewsEntity
import com.example.m_news.databinding.FragmentDetailBinding
import com.example.m_news.ui.viewmodel.BookmarkViewModel
import com.example.m_news.utils.Helper.formatDate
import com.example.m_news.utils.Helper.toNewsEntity
import com.example.m_news.utils.Result
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val newsJson: DetailFragmentArgs by navArgs()
    private val bookmarkViewModel: BookmarkViewModel by activityViewModels()
    private var isBookmarked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val news = newsJson.newsArg.let { Json.decodeFromString<News>(it) }
        val newsEntity = news.toNewsEntity()

        setUpDetailView(news)

        binding.bookmarkButton.setOnClickListener {
            toggleBookmark(newsEntity)
        }
    }

    private fun setUpDetailView(news: News) {
        lifecycleScope.launch {
            bookmarkViewModel.bookmarkedArticles.collect { result ->
                when (result) {
                    is Result.Error -> {}

                    Result.Loading -> {}

                    is Result.Success -> {
                        isBookmarked = result.data.any { it.id == news.url.hashCode().toString() }
                        updateBookmarkButton(isBookmarked)
                    }
                }
            }
        }

        binding.apply {
            newsTitle.text = news.title ?: "Title"
            authorName.text = news.author ?: "Author"
            publishedDate.text = news.publishedAt?.let { formatDate(it) } ?: "Jan 01, 1999"
            sourceName.text = news.source?.name ?: "Source name"
            newsDescription.text = news.description ?: ""

            val imageUrl = news.urlToImage
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get().load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.newsImage)
            } else {
                Picasso.get().load(R.drawable.ic_launcher_background).into(binding.newsImage)
            }
        }
    }

    private fun updateBookmarkButton(isBookmarked: Boolean) {
        binding.bookmarkButton.setImageResource(
            if (isBookmarked) R.drawable.bookmark_checked else R.drawable.bookmark_outline
        )
    }

    private fun toggleBookmark(newsEntity: NewsEntity) {
        if (isBookmarked) {
            bookmarkViewModel.removeBookmark(newsEntity)
        } else {
            bookmarkViewModel.addBookmarkArticle(newsEntity)
        }

        isBookmarked = !isBookmarked
        updateBookmarkButton(isBookmarked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
