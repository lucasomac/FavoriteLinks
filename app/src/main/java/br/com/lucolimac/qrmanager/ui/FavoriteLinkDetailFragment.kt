package br.com.lucolimac.qrmanager.ui

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
import br.com.lucolimac.qrmanager.FavoriteLinksDatabase
import br.com.lucolimac.qrmanager.R
import br.com.lucolimac.qrmanager.data.FavoriteLink
import br.com.lucolimac.qrmanager.databinding.FragmentContactDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteLinkDetailFragment : Fragment() {
    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteLink: FavoriteLink
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteLink = requireArguments().getParcelable("favoriteLink", FavoriteLink::class.java) as FavoriteLink
        with(binding.commonLayout) {
            editTextName.setText(favoriteLink.title)
            editTextPhone.setText(favoriteLink.description)
            editTextEmail.setText(favoriteLink.url)
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
                    R.id.actionChangeContact -> {
                        val db = FavoriteLinksDatabase.getDatabase(requireActivity().applicationContext)
                        val favoriteLinkUpdate = FavoriteLink(
                            favoriteLink.id, favoriteLink.title, favoriteLink.description, favoriteLink.url
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            db.contactDAO().updateQrCode(favoriteLinkUpdate)
                        }
                        findNavController().popBackStack()
                        true
                    }

                    R.id.actionDeleteContact -> {
                        val db = FavoriteLinksDatabase.getDatabase(requireActivity().applicationContext)
                        CoroutineScope(Dispatchers.IO).launch {
                            db.contactDAO().deleteQrCode(favoriteLink)
                        }
                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}