package com.app.perk99restaurant.home.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.models.NearByDriverModel
import de.hdodenhof.circleimageview.CircleImageView

class NearByDriversAdapter (val mList:ArrayList<NearByDriverModel>,val context:Context):RecyclerView.Adapter<NearByDriversAdapter.NearByViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NearByViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return NearByDriversAdapter.NearByViewHolder(inflater, parent, context)
    }

    override fun getItemCount(): Int
    {
        return mList.size
    }

    override fun onBindViewHolder(holder: NearByViewHolder, position: Int)
    {
       val mObj: NearByDriverModel = mList[position]
       Log.e("Adapter<<<<< "," "+mObj.name)
       holder.tvName?.setText(mObj.name)
       holder.tvDesc?.setText(mObj.description)
       holder.tvRating?.setText(mObj.ratings)
       holder.tvAssign!!.setOnClickListener { listener!!.onAssignDriver(position)}
    }

    class NearByViewHolder(inflator: LayoutInflater, parent: ViewGroup, var context: Context) :
    RecyclerView.ViewHolder(inflator.inflate(R.layout.custom_near_by_driver, parent, false))
    {
    var ivDelievryBoy: CircleImageView? = null
    var tvName: TextView? = null
    var tvRating: TextView? = null
    var tvDesc: TextView? = null
    var tvAssign: TextView? = null

   init
   {
        ivDelievryBoy = itemView.findViewById(R.id.ivDelievryBoy)
        tvName = itemView.findViewById(R.id.tvName)
        tvRating = itemView.findViewById(R.id.tvRating)
        tvDesc = itemView.findViewById(R.id.tvDesc)
        tvAssign = itemView.findViewById(R.id.tvAssign)
   }
    }

    internal var listener: onClickListener?=null

    interface onClickListener
    {
        fun onAssignDriver(layoutPosition: Int)
    }

    fun onItemSelectedListener(clickListener: onClickListener)
    {
        listener = clickListener
    }

}