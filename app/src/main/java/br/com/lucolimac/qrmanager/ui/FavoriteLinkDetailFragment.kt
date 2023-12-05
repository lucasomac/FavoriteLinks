package br.com.lucolimac.qrmanager.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import br.com.lucolimac.qrmanager.FavoriteLinksDatabase
import br.com.lucolimac.qrmanager.R
import br.com.lucolimac.qrmanager.data.FavoriteLink
import br.com.lucolimac.qrmanager.databinding.FragmentFavoriteLinkDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteLinkDetailFragment : Fragment() {
    private var _binding: FragmentFavoriteLinkDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteLink: FavoriteLink
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteLinkDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteLink = requireArguments().getParcelable(
            "favoriteLink", FavoriteLink::class.java
        ) as FavoriteLink
        with(binding.webview) {
            setWebViewClient(this)
            this.loadUrl(favoriteLink.url)
        }
//        val menuHost: MenuHost = requireActivity()
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                // Add menu items here
//                menuInflater.inflate(R.menu.detail_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                // Handle the menu selection
//                return when (menuItem.itemId) {
//                    R.id.actionChangeContact -> {
//                        val db =
//                            FavoriteLinksDatabase.getDatabase(requireActivity().applicationContext)
//                        val favoriteLinkUpdate = FavoriteLink(
//                            favoriteLink.id,
//                            favoriteLink.title,
//                            favoriteLink.description,
//                            favoriteLink.url
//                        )
//                        CoroutineScope(Dispatchers.IO).launch {
//                            db.contactDAO().updateFavoriteLink(favoriteLinkUpdate)
//                        }
//                        findNavController().popBackStack()
//                        true
//                    }
//
//                    R.id.actionDeleteContact -> {
//                        val db =
//                            FavoriteLinksDatabase.getDatabase(requireActivity().applicationContext)
//                        CoroutineScope(Dispatchers.IO).launch {
//                            db.contactDAO().deleteFavoriteLink(favoriteLink)
//                        }
//                        findNavController().popBackStack()
//                        true
//                    }
//
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setWebViewClient(webView: WebView?) {
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Ativa o PreogressBar
                binding.progress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Desativa o PreogressBar
                binding.progress.visibility = View.INVISIBLE
            }
        }
    }
}