package br.com.lucolimac.favoritelinks.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import br.com.lucolimac.favoritelinks.R
import br.com.lucolimac.favoritelinks.data.FavoriteLink
import br.com.lucolimac.favoritelinks.databinding.FragmentFavoriteLinkDetailBinding
import br.com.lucolimac.favoritelinks.ui.viewmodel.FavoriteLinkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteLinkDetailFragment : Fragment() {
    private var _binding: FragmentFavoriteLinkDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteLink: FavoriteLink
    private val viewModel: FavoriteLinkViewModel by viewModel()
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
        with(binding.commonLayout) {
            editTextTitle.setText(favoriteLink.title)
            editTextDescription.setText(favoriteLink.description)
            editTextUrl.setText(favoriteLink.url)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.actionChangeFavoriteLink -> {
                        val favoriteLinkUpdate = FavoriteLink(
                            favoriteLink.id,
                            binding.commonLayout.editTextTitle.text.toString(),
                            binding.commonLayout.editTextDescription.text.toString(),
                            binding.commonLayout.editTextUrl.text.toString(),
                        )
                        viewModel.updateFavoriteLink(favoriteLinkUpdate)
                        findNavController().popBackStack()
                        true
                    }

                    R.id.actionDeleteFavoriteLink -> {
                        viewModel.deleteFavoriteLink(favoriteLink)
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}