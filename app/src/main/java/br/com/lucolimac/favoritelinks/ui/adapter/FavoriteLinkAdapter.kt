package br.com.lucolimac.favoritelinks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.lucolimac.favoritelinks.R
import br.com.lucolimac.favoritelinks.data.FavoriteLink
import br.com.lucolimac.favoritelinks.databinding.FavoriteLinkCellBinding
import br.com.lucolimac.favoritelinks.ui.componnets.OnFavoriteClickListener

class FavoriteLinkAdapter(private val onFavoriteClickListener: OnFavoriteClickListener) :
    ListAdapter<FavoriteLink, FavoriteLinkAdapter.ContactViewHolder>(diffCallback), Filterable {
    private lateinit var binding: FavoriteLinkCellBinding
    private var originalList = arrayListOf<FavoriteLink>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ContactViewHolder {
        binding =
            FavoriteLinkCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContactViewHolder(private val view: FavoriteLinkCellBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(favoriteLink: FavoriteLink) {
            view.title.text = favoriteLink.title
            view.descriptionSubject.text = favoriteLink.description
            view.counterEntrance.text =
                view.root.context.getString(R.string.access_number, favoriteLink.counter.toString())
            view.root.setOnClickListener {
                onFavoriteClickListener.onFavoriteLinkClick(favoriteLink)
            }
            view.root.setOnLongClickListener {
                val popupMenu = PopupMenu(it.context, it)

                // Inflating popup menu from popup_menu.xml file
                popupMenu.menuInflater.inflate(R.menu.detail_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.actionChangeFavoriteLink -> onFavoriteClickListener.onEditClick(
                            favoriteLink
                        )

                        R.id.actionDeleteFavoriteLink -> onFavoriteClickListener.onDeleteClick(
                            favoriteLink
                        )

                    }
                    true
                }
                popupMenu.show()
                true
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<FavoriteLink>() {
            override fun areItemsTheSame(oldItem: FavoriteLink, newItem: FavoriteLink): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteLink, newItem: FavoriteLink): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (originalList.isEmpty()) originalList = ArrayList(currentList)
                return if (p0.isNullOrEmpty()) FilterResults().apply { values = originalList }
                else {
                    FilterResults().apply {
                        values = originalList.filter { it.title.contains(p0, true) }
                    }
                }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                submitList(p1?.values as ArrayList<FavoriteLink>)
            }
        }
    }
}