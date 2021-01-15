package com.dixitpatel.mycoffeevenue.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dixitpatel.mycoffeevenue.R
import com.dixitpatel.mycoffeevenue.databinding.RowItemAllBinding
import com.dixitpatel.mycoffeevenue.model.NearByPlaceResponse

/**
 *  Adapter class : Display data of Nearby places.
 */
class PlacesAdapter constructor(private val onClickListener: OnClickListener): RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

  // OnClick Event Listener.
  interface OnClickListener {
    fun onClick(view : View,position: Int,item: NearByPlaceResponse.Item)
  }

  private val items: MutableList<NearByPlaceResponse.Item> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding =
      DataBindingUtil.inflate<RowItemAllBinding>(inflater, R.layout.row_item_all, parent, false)
    return PlacesViewHolder(binding).apply {

      binding.root.setOnClickListener {
        val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                ?: return@setOnClickListener
        onClickListener.onClick( binding.root,position,items[position])
      }
    }
  }

  fun setPlacesList(placesList: List<NearByPlaceResponse.Item>) {
    val previousItemSize = items.size
    items.clear()
    items.addAll(placesList)
    notifyItemRangeChanged(previousItemSize, placesList.size)
  }

  override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
    holder.binding.apply {
      item = items[position]
      executePendingBindings()
    }
  }

  override fun getItemCount() = items.size

  class PlacesViewHolder(val binding: RowItemAllBinding) : RecyclerView.ViewHolder(binding.root)
}
