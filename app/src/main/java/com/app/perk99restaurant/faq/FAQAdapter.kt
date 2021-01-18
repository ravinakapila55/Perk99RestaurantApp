package com.app.perks99.faq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.faq.FAQModel


class FAQAdapter (val mList: List<FAQModel>, val context: Context) :
    RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FAQViewHolder(inflater, parent, context)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {

//        holder.tvName!!.setText(child.name)

        val child = mList[position]

        holder.tvName!!.setText(child.qus)
        holder.tvDesc!!.setText(child.ans)

        holder.ivDrop!!.setOnClickListener {

            if (holder.tvDesc!!.isShown)
            {
                holder.ivDrop!!.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.ccp_down_arrow))
                holder.tvDesc!!.visibility=View.GONE
            }else{
                holder.tvDesc!!.visibility=View.VISIBLE
                holder.ivDrop!!.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.ccp_down_arrow))

            }
        }




    }

    class FAQViewHolder(
        inflator: LayoutInflater,
        parent: ViewGroup,
        var context: Context
    ) :
        RecyclerView.ViewHolder(
            inflator.inflate(
                R.layout.custom_faq, parent, false
            )
        ) {
        var tvName: TextView? = null
        var ivDrop: ImageView? = null
        var tvDesc: TextView? = null

        init {
            tvName = itemView.findViewById(R.id.tvName)
            ivDrop = itemView.findViewById(R.id.ivDrop)
            tvDesc = itemView.findViewById(R.id.tvDesc)
        }
    }


}

