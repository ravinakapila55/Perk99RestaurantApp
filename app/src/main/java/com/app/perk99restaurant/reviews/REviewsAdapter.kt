package com.app.perk99restaurant.reviews

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.utils.Constants

class REviewsAdapter(val mList: List<RatingReviewModel>, val context: Context) :
    RecyclerView.Adapter<REviewsAdapter.FavouriteListViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): REviewsAdapter.FavouriteListViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return FavouriteListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int
    {
        return mList.size
    }

    override fun onBindViewHolder(holder: FavouriteListViewHolder, position: Int)
    {
        val child = mList[position]

        Log.e("insideAdapter ","inside")

        holder.tvOrderNo!!.setText(child.order_id)
        holder.tvComment!!.setText(child.user_message)

        if (child.restaurant_msg.equals(null,ignoreCase = true))
        {
            Log.e("position if",position.toString())
            holder.ccRightReviww!!.visibility=View.VISIBLE
            holder.tvRestaurantComment!!.visibility=View.GONE
        }
        else{
            Log.e("position else",position.toString())

//            holder.tvRestaurantComment!!.setText("No Comment")
            holder.tvRestaurantComment!!.setText(child.restaurant_msg)
            holder.ccRightReviww!!.visibility=View.GONE
            holder.tvRestaurantComment!!.visibility=View.VISIBLE

        }

        holder.recyclerMenus!!.setLayoutManager(
            LinearLayoutManager(
                holder.recyclerMenus!!.context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )


        if (child.details.size>0)
        {
            holder.recyclerMenus!!.visibility= View.VISIBLE
            val cheldadapter = RatingMenuAdapter(child.details)
            holder.recyclerMenus!!.adapter = cheldadapter
        }
        else
        {
            holder.recyclerMenus!!.visibility= View.GONE
        }


        holder.btSUbmit!!.setOnClickListener {
            listener!!.onSubmitClick(position,holder.etREview!!.text.toString().trim(),child.rating_id)
        }




    }

    class FavouriteListViewHolder (inflator: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflator.inflate(
            R.layout.custom_rating_review, parent, false))
    {
        var tvOrderNo: TextView? = null
        var tvComment: TextView? = null
        var tvRestaurantComment: TextView? = null
        var btSUbmit: TextView? = null
        var recyclerMenus: RecyclerView? = null
        var ccRightReviww: ConstraintLayout? = null
        var etREview: EditText? = null

        init
        {
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo)
            tvComment = itemView.findViewById(R.id.tvComment)
            tvRestaurantComment = itemView.findViewById(R.id.tvRestaurantComment)
            btSUbmit = itemView.findViewById(R.id.btSUbmit)
            recyclerMenus = itemView.findViewById(R.id.recyclerMenus)
            ccRightReviww = itemView.findViewById(R.id.ccRightReviww)
            etREview = itemView.findViewById(R.id.etREview)
        }
    }

    internal var listener: onClickListener?=null

    interface onClickListener
    {
        fun onSubmitClick(layoutPosition: Int,review:String,rating_id:String)
    }

    fun onItemSelectedListener(clickListener: onClickListener)
    {
        listener = clickListener
    }
}