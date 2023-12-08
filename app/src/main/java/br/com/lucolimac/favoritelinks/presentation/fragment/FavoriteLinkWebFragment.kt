package br.com.lucolimac.favoritelinks.presentation.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import br.com.lucolimac.favoritelinks.databinding.FragmentFavoriteLinkWebBinding
import br.com.lucolimac.favoritelinks.domain.entity.FavoriteLink
import br.com.lucolimac.favoritelinks.presentation.viewmodel.FavoriteLinkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteLinkWebFragment : Fragment() {
    private var _binding: FragmentFavoriteLinkWebBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteLink: FavoriteLink
    private val viewModel: FavoriteLinkViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteLinkWebBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteLink = requireArguments().getParcelable(
            "favoriteLinkModel", FavoriteLink::class.java
        ) as FavoriteLink
        with(binding.webview) {
            setWebViewClient(this)
            loadUrl(favoriteLink.url)
        }
        val favoriteLinkToUpdate = favoriteLink.copy(counter = favoriteLink.counter + 1)
        viewModel.updateFavoriteLink(favoriteLinkToUpdate)
    }

    private fun setWebViewClient(webView: WebView?) {
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progress.visibility = View.INVISIBLE
            }
        }
    }
}