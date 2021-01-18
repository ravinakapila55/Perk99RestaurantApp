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
import com.app.perk99restaurant.home.models.ModelAcceptedOrders
import com.app.perk99restaurant.utils.CommonMethod
import java.util.ArrayList

class AcceptedOrderFragmentAdapter(val mList: ArrayList<ModelAcceptedOrders>, val context: Context):
    RecyclerView.Adapter<AcceptedOrderFragmentAdapter.AcceptedHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): AcceptedOrderFragmentAdapter.AcceptedHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AcceptedOrderFragmentAdapter.AcceptedHolder(inflater, parent, context)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: AcceptedHolder, position: Int)
    {
        val mObj: ModelAcceptedOrders = mList[position]
        Log.e("Adapter<<<<< ", " " + mObj)

        holder.tvId?.setText("Order Id : " + mObj.id)
        holder.tvName?.setText(mObj.user_name)

        holder.tvStatus?.setText(mObj.status)
        holder.tvTotal?.setText("$" + mObj.total)

        holder.recyclerView.setLayoutManager(LinearLayoutManager(
                holder.recyclerView.context,
                LinearLayoutManager.VERTICAL,
                false))
        val cheldadapter = AcceptRequestItemAdapter(mObj.list)
        holder.recyclerView.adapter = cheldadapter

        Log.e("AssignedValue ", mObj.is_assigned.toString())

        if (mObj.status.equals("preparing", ignoreCase = true))
        {
            if (mObj.type.equals("reservedOrder")) {
                holder.tvOrderType!!.visibility = View.VISIBLE
                holder.tvPaymentStatus!!.visibility = View.GONE

             //   holder.tvOrderingType!!.setText(mObj.order_type)

                if(mObj.order_type.equals("pickup",ignoreCase=true))
                {
//                    holder.tvOrderingType!!.setText(mObj.order_type)
                    holder.tvOrderingType!!.setText("Self Pickup")

                }
                else
                {
                    holder.tvOrderingType!!.setText("Delivery")
                }

                holder.cc_ready!!.visibility = View.VISIBLE

                if (mObj.order_type.equals("pickup", ignoreCase = true)) {
                    holder.CCPickUp!!.visibility = View.VISIBLE
                    holder.tvMealType!!.setText(mObj.meal_type)
                    holder.tvPickupTime!!.setText(mObj.pickup_time)
                    holder.cc_Assign!!.visibility = View.GONE
                    holder.cc_prepare!!.visibility = View.GONE
                    holder.cc_reserve!!.visibility = View.VISIBLE


                }
                //for deliver
                else {
                    holder.CCPickUp!!.visibility = View.GONE
                    holder.cc_reserve!!.visibility = View.GONE


                    if (mObj.is_assigned.toString().equals("1")) {
                        holder.cc_Assign!!.setVisibility(View.GONE)
                        holder.cc_prepare!!.setVisibility(View.VISIBLE)
                    } else {
                        holder.cc_Assign!!.setVisibility(View.VISIBLE)
                        holder.cc_prepare!!.setVisibility(View.GONE)

                        holder.cc_Assign!!.setOnClickListener {
                            listener!!.onNearByClick(position)
                        }

                    }
                }
                holder.cc_ready!!.setOnClickListener {
                    listener!!.onOrderReady(position)
                }

                holder.tvAccept!!.setOnClickListener {
                    Log.e("ReadyClickReserve ", "ready")

                    listener!!.onOrderReady(position)
                }

            }

            else {
                holder.tvOrderType!!.visibility = View.GONE
                holder.cc_reserve!!.visibility = View.GONE

                holder.cc_ready!!.visibility = View.VISIBLE
                holder.tvPaymentStatus!!.visibility = View.VISIBLE

                if (mObj.is_assigned.toString().equals("1"))
                {
                    holder.cc_Assign!!.setVisibility(View.GONE)
                    holder.cc_prepare!!.setVisibility(View.VISIBLE)
                }
                else
                {
                    holder.cc_Assign!!.setVisibility(View.VISIBLE)
                    holder.cc_prepare!!.setVisibility(View.GONE)
                }

                holder.cc_Assign!!.setOnClickListener {
                    listener!!.onNearByClick(position)
                }

                holder.cc_ready!!.setOnClickListener {
                    if (mObj.is_assigned.toString().equals("0")) {
                        CommonMethod.showToast("Firstly Assign Driver")
                    } else {
                        listener!!.onOrderReady(position)
                    }
                }

                holder.tvAccept!!.setOnClickListener {

                    if (mObj.is_assigned.toString().equals("0")) {
                        CommonMethod.showToast("Firstly Assign Driver")
                    } else {
                        listener!!.onOrderReady(position)
                    }
                }

            }
        }
        else {
            if (mObj.type.equals("reservedOrder")) {
                holder.tvOrderType!!.visibility = View.VISIBLE
                holder.cc_Assign!!.visibility = View.GONE
                holder.tvPaymentStatus!!.visibility = View.GONE


//                holder.tvOrderingType!!.setText(mObj.order_type)

                if(mObj.order_type.equals("pickup",ignoreCase=true))
                {
//                    holder.tvOrderingType!!.setText(mObj.order_type)
                    holder.tvOrderingType!!.setText("Self Pickup")

                }
                else
                {
                    holder.tvOrderingType!!.setText("Delivery")
                }


                if (mObj.order_type.equals("pickup", ignoreCase = true)) {


                    holder.CCPickUp!!.visibility = View.VISIBLE
                    holder.tvMealType!!.setText(mObj.meal_type)
                    holder.tvPickupTime!!.setText(mObj.pickup_time)
                    holder.cc_prepare!!.visibility = View.GONE
                    holder.cc_reserve!!.visibility = View.VISIBLE



                    if (mObj.status.equals("ready", ignoreCase = true)) {
                        holder.cc_ready!!.visibility = View.VISIBLE
                        holder.tvAccept!!.setText("ORDER PICKEDUP")

                        holder.tvAccept!!.setOnClickListener {
                            Log.e("orderPickedUpReserve ", "ready")
                            listener!!.onOrderPickedUp(position)
                        }
                    } else {
                        holder.cc_ready!!.visibility = View.GONE
                    }

                } else {
                    holder.CCPickUp!!.visibility = View.GONE
                    holder.cc_reserve!!.visibility = View.GONE
                    holder.cc_prepare!!.visibility = View.VISIBLE
                    holder.cc_ready!!.visibility = View.GONE
                }


            }
            else {
                holder.tvOrderType!!.visibility = View.GONE
                holder.cc_reserve!!.visibility = View.GONE

                holder.cc_ready!!.visibility = View.GONE
                holder.cc_Assign!!.setVisibility(View.GONE)
                holder.cc_prepare!!.setVisibility(View.VISIBLE)
                holder.tvPaymentStatus!!.setVisibility(View.VISIBLE)
            }
        }

        if (mObj.instructions.equals(null, ignoreCase = true))
        {
            holder.tvInstructions!!.visibility = View.GONE
            holder.tvInstructionsLabel!!.visibility = View.GONE
        }
        else
        {
            holder.tvInstructions!!.visibility = View.VISIBLE
            holder.tvInstructionsLabel!!.visibility = View.VISIBLE
            holder.tvInstructions!!.setText(" "+mObj.instructions)
        }

    }

    class AcceptedHolder(inflator: LayoutInflater, parent: ViewGroup,var context: Context) :
        RecyclerView.ViewHolder(inflator.inflate(R.layout.custom_accepted_orders, parent, false)) {
        var tvId: TextView? = null
        var tvStatus: TextView? = null
        var tvName: TextView? = null
        var tvTotal: TextView? = null
        var tvPaymentStatus: TextView? = null
        var tvInstructions: TextView? = null
        var tvInstructionsLabel: TextView? = null
        var tvAccept: TextView? = null

        var tvOrderType: TextView? = null
        var tvOrderingType: TextView? = null
        var tvMealType: TextView? = null
        var tvPickupTime: TextView? = null

        var CCPickUp: ConstraintLayout? = null
        var cc_reserve: ConstraintLayout? = null

        var recyclerView: RecyclerView
        var cc_prepare: ConstraintLayout? = null
        var cc_Assign: ConstraintLayout? = null
        var cc_ready: ConstraintLayout? = null


        init {
            tvId = itemView.findViewById(R.id.id)
            tvStatus = itemView.findViewById(R.id.tvStatus)
            tvName = itemView.findViewById(R.id.tvName)
            tvAccept = itemView.findViewById(R.id.tvAccept)
            tvTotal = itemView.findViewById(R.id.tvTotal)
            tvPaymentStatus = itemView.findViewById(R.id.tvPaymentStatus)
            tvInstructions = itemView.findViewById(R.id.tvInstructions)
            tvInstructionsLabel = itemView.findViewById(R.id.tvInstructionsLabel)
            recyclerView = itemView.findViewById(R.id.recycler)
            cc_prepare = itemView.findViewById(R.id.cc_prepare)
            cc_Assign = itemView.findViewById(R.id.cc_Assign)
            cc_ready = itemView.findViewById(R.id.cc_ready)

            tvOrderType = itemView.findViewById(R.id.tvOrderType)
            tvOrderingType = itemView.findViewById(R.id.tvOrderingType)
            tvMealType = itemView.findViewById(R.id.tvMealType)
            tvPickupTime = itemView.findViewById(R.id.tvPickupTime)
            cc_reserve = itemView.findViewById(R.id.cc_reserve)
            CCPickUp = itemView.findViewById(R.id.CCPickUp)
        }
    }

    internal var listener: onClickListener? = null

    interface onClickListener {
        fun onNearByClick(layoutPosition: Int)
        fun onOrderReady(layoutPositin: Int)
        fun onOrderPickedUp(layoutPositin: Int)
    }

    fun onItemSelectedListener(clickListener: onClickListener) {
        listener = clickListener
    }
}