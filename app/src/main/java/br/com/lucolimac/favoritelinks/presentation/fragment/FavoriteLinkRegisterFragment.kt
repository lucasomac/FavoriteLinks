package br.com.lucolimac.favoritelinks.presentation.fragment

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
import br.com.lucolimac.favoritelinks.databinding.FragmentFavoriteLinkRegisterBinding
import br.com.lucolimac.favoritelinks.domain.entity.FavoriteLink
import br.com.lucolimac.favoritelinks.presentation.viewmodel.FavoriteLinkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteLinkRegisterFragment : Fragment() {
    private var _binding: FragmentFavoriteLinkRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteLinkViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteLinkRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.register_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.actionSaveContact -> {
                        val name = binding.commonLayout.editTextTitle.text.toString()
                        val phone = binding.commonLayout.editTextDescription.text.toString()
                        val email = binding.commonLayout.editTextUrl.text.toString()
                        val favoriteLink = FavoriteLink(0, name, phone, email)
                        viewModel.createFavoriteLink(favoriteLink)
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}