package com.app.perk99restaurant.home.adapters

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
import com.app.perk99restaurant.home.models.GetOrderList
import com.app.perk99restaurant.utils.SharedPrefUtil
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RequestAdapter(val mList: ArrayList<GetOrderList>, val context: Context) :RecyclerView.Adapter<RequestAdapter.RequestViewHolder>()
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

        try {
            val mObj: GetOrderList = mList[position]
            Log.e("Adapter<<<<< "," "+mObj)
            Log.e("type<<<<< "," "+mObj.type)

            holder.tvId?.setText("Order Id: "+mObj.id)
            holder.tvName?.setText(mObj.status)




            holder.tvOrderType!!.visibility=View.VISIBLE



            if(mObj.type.equals("instantOrder",ignoreCase = true))
            {
                Log.e("OrderType ","instant")
                holder.tvDate!!.visibility=View.GONE
                holder.tvTime!!.visibility=View.GONE
                holder.tvOrderType!!.setText("INSTANT")
                holder.tvOrderType!!.setTextColor(context.resources.getColor(R.color.main_green_color))


                holder.tvOrderType!!.visibility=View.VISIBLE
                holder.tvAddress!!.visibility=View.VISIBLE
                holder.cc_reserve!!.visibility=View.GONE
                holder.cc_prepare!!.visibility=View.GONE

                if (mObj.delivery_address.equals("null",ignoreCase = true))
                {
                    holder.tvAddress?.setText("NA")
                }
                else
                {
                    holder.tvAddress?.setText("Address:"+mObj.delivery_address)

                }
            }
            else
            {
                Log.e("OrderType ","reserved")

                holder.tvDate!!.visibility=View.GONE
                holder.tvTime!!.visibility=View.VISIBLE
                var time = ""

                holder.tvOrderType!!.setText("RESERVED")
                holder.tvTime!!.setText("Pick Up Info:"+mObj.pickup_time)
                holder.tvOrderType!!.setTextColor(context.resources.getColor(R.color.app_orange))


                holder.tvOrderType!!.visibility=View.VISIBLE
                holder.cc_reserve!!.visibility=View.VISIBLE
                holder.cc_prepare!!.visibility=View.GONE

                if(mObj.order_type.equals("pickup",ignoreCase=true))
                {
//                holder.tvOrderingType!!.setText(mObj.order_type)
                    holder.tvOrderingType!!.setText("Self Pickup")
                    holder.CCPickUp!!.visibility=View.VISIBLE
                    holder.tvAddress!!.visibility=View.GONE
                    holder.tvMealType!!.setText(mObj.meal_type)
                }
                else
                {
                    holder.tvOrderingType!!.setText("Delivery")
                    holder.CCPickUp!!.visibility=View.GONE
                    holder.tvAddress!!.visibility=View.VISIBLE

                    if (mObj.delivery_address.equals("null",ignoreCase = true))
                    {
                        holder.tvAddress?.setText("NA")
                    }
                    else
                    {
                        holder.tvAddress?.setText("Address:"+mObj.delivery_address)

                    }
                }

            }

            if (mObj.instructions.equals(null,ignoreCase = true))
            {
                Log.e("Instruction ","if")
                holder.tvInstructions!!.visibility=View.GONE
                holder.tvInstructionsLabel!!.visibility=View.GONE
            }
            else
            {
                Log.e("Instruction ","else")
                Log.e("InstructionValue ",mObj.instructions)

                holder.tvInstructions!!.visibility=View.VISIBLE
                holder.tvInstructionsLabel!!.visibility=View.VISIBLE
                holder.tvInstructions!!.setText(" "+mObj.instructions)

            }



            holder.recyclerView.setLayoutManager(LinearLayoutManager(holder.recyclerView.context, LinearLayoutManager.VERTICAL, false))
            val cheldadapter = RequestItemAdapter(mObj.detail)
            holder.recyclerView.adapter=cheldadapter
            holder.cc_accept!!.setOnClickListener { listener!!.onAcceptClick(position)}
            holder.cc_reject!!.setOnClickListener { listener!!.onRejectClick(position)}
        }

        catch (ex:Exception)
        {
            ex.printStackTrace()
        }


    }

    class RequestViewHolder(inflator:LayoutInflater,parent:ViewGroup,var context: Context) :
        RecyclerView.ViewHolder(inflator.inflate(
        R.layout.custom_home, parent, false))
    {
        var tvId: TextView? = null
        var tvInstructions: TextView? = null
        var tvInstructionsLabel: TextView? = null
        var tvTime: TextView? = null
        var tvOrderType: TextView? = null
        var tvDate: TextView? = null
        var tvAddress: TextView? = null

        var tvName: TextView? = null
        var tvOrderingType: TextView? = null
        var tvMealType: TextView? = null
        var tvPickupTime: TextView? = null
        var recyclerView:RecyclerView
        var cc_accept:ConstraintLayout? = null
        var cc_reject:ConstraintLayout? = null
        var cc_prepare:ConstraintLayout? = null
        var CCPickUp:ConstraintLayout? = null
        var cc_reserve:ConstraintLayout? = null

        init {
            tvId = itemView.findViewById(R.id.id)
            tvInstructions = itemView.findViewById(R.id.tvInstructions)
            tvInstructionsLabel = itemView.findViewById(R.id.tvInstructionsLabel)
            tvOrderType = itemView.findViewById(R.id.tvOrderType)
            tvTime = itemView.findViewById(R.id.tvTime)
            tvName = itemView.findViewById(R.id.tvName)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvAddress = itemView.findViewById(R.id.tvAddress)
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