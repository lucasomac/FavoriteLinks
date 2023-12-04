package br.com.lucolimac.qrmanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.lucolimac.qrmanager.data.FavoriteLink
import br.com.lucolimac.qrmanager.databinding.ContactCellBinding

class FavoriteLinkAdapter(private val onFavoriteClickListener: OnFavoriteClickListener) :
    ListAdapter<FavoriteLink, FavoriteLinkAdapter.ContactViewHolder>(diffCallback), Filterable {
    private lateinit var binding: ContactCellBinding
    private var originalList = arrayListOf<FavoriteLink>()
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ContactViewHolder {
        binding = ContactCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContactViewHolder(private val view: ContactCellBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(favoriteLink: FavoriteLink) {
            view.name.text = favoriteLink.title
            view.phone.text = favoriteLink.title
            view.root.setOnClickListener {
                onFavoriteClickListener.onQrCodeClick(favoriteLink)
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