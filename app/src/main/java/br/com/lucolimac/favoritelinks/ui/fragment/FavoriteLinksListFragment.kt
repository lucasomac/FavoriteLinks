package br.com.lucolimac.favoritelinks.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import br.com.lucolimac.favoritelinks.R
import br.com.lucolimac.favoritelinks.data.FavoriteLink
import br.com.lucolimac.favoritelinks.databinding.FragmentFavoriteLinksListBinding
import br.com.lucolimac.favoritelinks.ui.adapter.FavoriteLinkAdapter
import br.com.lucolimac.favoritelinks.ui.componnets.OnFavoriteClickListener
import br.com.lucolimac.favoritelinks.ui.viewmodel.FavoriteLinkViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteLinksListFragment : Fragment(), OnFavoriteClickListener {
    private var _binding: FragmentFavoriteLinksListBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteLinkAdapter: FavoriteLinkAdapter
    private val viewModel: FavoriteLinkViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteLinksListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.actionListToRegister)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavoriteLinks()
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchView = menu.findItem(R.id.actionSearch).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        favoriteLinkAdapter.filter.filter(p0)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.listOfFavoriteLinks.collect {
                    updateUI(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavoriteLinks()
        }
    }

    private fun updateUI(favoriteLinks: List<FavoriteLink>) {
        favoriteLinkAdapter = FavoriteLinkAdapter(this@FavoriteLinksListFragment).apply {
            submitList(favoriteLinks)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                binding.recyclerview.adapter = favoriteLinkAdapter
            }
        }
    }

    override fun onFavoriteLinkClick(favoriteLink: FavoriteLink) {
        val bundle = Bundle()
        bundle.putParcelable("favoriteLink", favoriteLink)
        findNavController().navigate(
            R.id.actionListToWeb, bundle
        )
    }

    override fun onEditClick(favoriteLink: FavoriteLink) {
        val bundle = Bundle()
        bundle.putParcelable("favoriteLink", favoriteLink)
        findNavController().navigate(
            R.id.actionListToEdit, bundle
        )
    }

    override fun onDeleteClick(favoriteLink: FavoriteLink) {
        viewModel.deleteFavoriteLink(favoriteLink)
    }
}