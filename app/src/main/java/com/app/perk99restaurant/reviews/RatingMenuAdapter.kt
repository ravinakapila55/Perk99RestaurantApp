package com.app.perk99restaurant.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R


class RatingMenuAdapter(val mList: List<RatingMenusModel>) :
    RecyclerView.Adapter<RatingMenuAdapter.HistoryViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int
    {
        return mList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int)
    {
        val child = mList[position]

        holder.tvMenu!!.setText(child.menu_id)
        holder.rating!!.setRating(child.getRating().toFloat())





    }

    class HistoryViewHolder(inflator: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflator.inflate(
            R.layout.custom_menu_rating, parent, false))
    {
        var tvMenu: TextView? = null
        var rating: RatingBar? = null


        init
        {
            tvMenu = itemView.findViewById(R.id.tvMenu)
            rating = itemView.findViewById(R.id.rating)


        }
    }


}