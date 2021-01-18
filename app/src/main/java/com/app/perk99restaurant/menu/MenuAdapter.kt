package com.app.perk99restaurant.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.faq.FAQModel
import com.app.perk99restaurant.home.adapters.AcceptRequestItemAdapter
import com.app.perks99.faq.FAQAdapter

class MenuAdapter  (val mList: List<MenuModel>, val context: Context) :
    RecyclerView.Adapter<MenuAdapter.FAQViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return FAQViewHolder(inflater, parent, context)
    }

    override fun getItemCount(): Int
    {
        return mList.size
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int)
    {
//     holder.tvName!!.setText(child.name)
       val child = mList[position]
       holder.tvName!!.setText(child.category)
       holder.ivDrop!!.setOnClickListener {
       if (child.menus.size>0)
       {
                if (holder.recyclerItem!!.isShown)
                {
                    holder.ivDrop!!.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.ic_arrow_up))
                    holder.recyclerItem!!.visibility= View.GONE
                }
                else
                {
                    holder.recyclerItem!!.visibility= View.VISIBLE
                    holder.tvNoData!!.visibility= View.GONE

                    holder.recyclerItem!!.setLayoutManager(
                        LinearLayoutManager(
                            holder.recyclerItem!!.context,
                            LinearLayoutManager.VERTICAL,
                            false)
                    )


                    val cheldadapter = MenuItemAdapter(child.menus)
                    holder.recyclerItem!!.adapter = cheldadapter
                }
       }
       else
       {
                if (holder.tvNoData!!.isShown)
                {
                    holder.ivDrop!!.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.ic_arrow_up))
                    holder.tvNoData!!.visibility= View.GONE

                } else
                {
                    holder.ivDrop!!.setImageDrawable(holder.itemView.context.resources.getDrawable(R.drawable.ccp_down_arrow))
                    holder.tvNoData!!.visibility= View.VISIBLE

                }
            }
        }
    }

    class FAQViewHolder(inflator: LayoutInflater, parent: ViewGroup, var context: Context) :
        RecyclerView.ViewHolder(inflator.inflate(R.layout.custom_menu, parent, false))
    {
        var tvName: TextView? = null
        var tvNoData: TextView? = null
        var ivDrop: ImageView? = null
        var recyclerItem: RecyclerView? = null
        init
        {
            tvName = itemView.findViewById(R.id.tvName)
            tvNoData = itemView.findViewById(R.id.tvNoData)
            ivDrop = itemView.findViewById(R.id.ivDrop)
            recyclerItem = itemView.findViewById(R.id.recyclerItem)
        }
    }


}

