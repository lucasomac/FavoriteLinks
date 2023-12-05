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
import br.com.lucolimac.qrmanager.databinding.FragmentFavoriteLinkRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteLinkRegisterFragment : Fragment() {
    private var _binding: FragmentFavoriteLinkRegisterBinding? = null
    private val binding get() = _binding!!

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
                        val name = binding.commonLayout.editTextName.text.toString()
                        val phone = binding.commonLayout.editTextPhone.text.toString()
                        val email = binding.commonLayout.editTextEmail.text.toString()
                        val favoriteLink = FavoriteLink(0, name, phone, email)
                        val db = FavoriteLinksDatabase.getDatabase(requireActivity().applicationContext)
                        CoroutineScope(Dispatchers.IO).launch {
                            db.contactDAO().createContact(favoriteLink)
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