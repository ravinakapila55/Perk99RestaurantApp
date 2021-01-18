package com.app.perk99restaurant.perks

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.adapters.RequestAdapter
import com.app.perk99restaurant.home.adapters.RequestItemAdapter
import com.app.perk99restaurant.home.models.GetOrderList
import java.util.ArrayList

class PerksAdapter(val mList: ArrayList<GetOrderList>, val context: Context) :
    RecyclerView.Adapter<PerksAdapter.RequestViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return RequestViewHolder(inflater, parent,context)
    }

    override fun getItemCount(): Int
    {
        return mList.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int)
    {
        val mObj: GetOrderList = mList[position]
        Log.e("Adapter<<<<< "," "+mObj)
        holder.tvId?.setText("Id : "+mObj.id)
        holder.tvName?.setText(mObj.status)


        if (mObj.instructions.equals("null",ignoreCase = true))
        {
            holder.tvInstructions!!.visibility= View.GONE
        }
        else{
            holder.tvInstructions!!.visibility= View.VISIBLE
            holder.tvInstructions!!.setText(mObj.instructions)

        }

        if(mObj.type.equals("reservedOrder"))
        {
            holder.tvOrderType!!.visibility= View.VISIBLE
            holder.cc_reserve!!.visibility= View.VISIBLE
            holder.cc_prepare!!.visibility= View.GONE

            holder.tvOrderingType!!.setText(mObj.order_type)


            if (mObj.order_type.equals("pickup",ignoreCase = true))
            {
                holder.CCPickUp!!.visibility= View.VISIBLE
                holder.tvMealType!!.setText(mObj.meal_type)
                holder.tvPickupTime!!.setText(mObj.pickup_time)

            }
            else{
                holder.CCPickUp!!.visibility= View.GONE


            }


        }
        else{
            holder.tvOrderType!!.visibility= View.GONE
            holder.cc_reserve!!.visibility= View.GONE
            holder.cc_prepare!!.visibility= View.VISIBLE

        }

        holder.recyclerView.setLayoutManager(LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.VERTICAL, false))
        val cheldadapter = RequestItemAdapter(mObj.detail)
        holder.recyclerView.adapter=cheldadapter
        holder.cc_accept!!.setOnClickListener { listener!!.onAcceptClick(position)}
        holder.cc_reject!!.setOnClickListener { listener!!.onRejectClick(position)}
    }

    class RequestViewHolder(inflator: LayoutInflater, parent: ViewGroup, var context: Context) :
        RecyclerView.ViewHolder(inflator.inflate(
            R.layout.custom_home, parent, false))
    {
        var tvId: TextView? = null
        var tvInstructions: TextView? = null
        var tvOrderType: TextView? = null

        var tvName: TextView? = null
        var tvOrderingType: TextView? = null
        var tvMealType: TextView? = null
        var tvPickupTime: TextView? = null
        var recyclerView: RecyclerView
        var cc_accept: ConstraintLayout? = null
        var cc_reject: ConstraintLayout? = null
        var cc_prepare: ConstraintLayout? = null
        var CCPickUp: ConstraintLayout? = null
        var cc_reserve: ConstraintLayout? = null

        init {
            tvId = itemView.findViewById(R.id.id)
            tvInstructions = itemView.findViewById(R.id.tvInstructions)
            tvOrderType = itemView.findViewById(R.id.tvOrderType)
            tvName = itemView.findViewById(R.id.tvName)
            tvOrderingType = itemView.findViewById(R.id.tvOrderingType)
            tvMealType = itemView.findViewById(R.id.tvMealType)
            tvPickupTime = itemView.findViewById(R.id.tvPickupTime)
            recyclerView= itemView.findViewById(R.id.recycler_items)
            cc_accept= itemView.findViewById(R.id.cc_accept)
            cc_reject= itemView.findViewById(R.id.cc_reject)
            cc_prepare= itemView.findViewById(R.id.cc_prepare)
            cc_reserve= itemView.findViewById(R.id.cc_reserve)
            CCPickUp= itemView.findViewById(R.id.CCPickUp)
        }

    }

    internal var listener: onClickListener?=null

    interface onClickListener
    {
        fun onAcceptClick(layoutPosition: Int)
        fun onRejectClick(layoutPositin: Int)

    }

    fun onItemSelectedListener(clickListener: onClickListener)
    {
        listener = clickListener
    }
}