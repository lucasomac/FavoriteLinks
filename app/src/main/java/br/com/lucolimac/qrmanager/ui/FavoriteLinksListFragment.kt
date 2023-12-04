package br.com.lucolimac.qrmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import br.com.lucolimac.qrmanager.FavoriteLinksDatabase
import br.com.lucolimac.qrmanager.R
import br.com.lucolimac.qrmanager.adapter.FavoriteLinkAdapter
import br.com.lucolimac.qrmanager.adapter.OnFavoriteClickListener
import br.com.lucolimac.qrmanager.data.FavoriteLink
import br.com.lucolimac.qrmanager.databinding.FragmentContactsListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteLinksListFragment : Fragment(), OnFavoriteClickListener {
    private var _binding: FragmentContactsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteLinkAdapter: FavoriteLinkAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_contactsListFragment_to_registerFragment)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchView = menu.findItem(R.id.actionSearch).actionView as? SearchView
                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
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
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val db = FavoriteLinksDatabase.getDatabase(requireActivity().applicationContext)
        var contactsList: ArrayList<FavoriteLink>
        CoroutineScope(Dispatchers.IO).launch {
            contactsList = db.contactDAO().getQrCodeByName() as ArrayList<FavoriteLink>
            favoriteLinkAdapter = FavoriteLinkAdapter(this@FavoriteLinksListFragment).apply {
                submitList(contactsList)
            }
            withContext(Dispatchers.Main) {
                binding.recyclerview.adapter = favoriteLinkAdapter
            }
        }
    }

    override fun onQrCodeClick(favoriteLink: FavoriteLink) {
        val bundle = Bundle()
        bundle.putParcelable("favoriteLink", favoriteLink)
        findNavController().navigate(
            R.id.action_contactsListFragment_to_contactDetailFragment, bundle
        )
    }
}