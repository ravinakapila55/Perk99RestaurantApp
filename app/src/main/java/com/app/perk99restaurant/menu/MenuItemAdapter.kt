package com.app.perk99restaurant.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.models.MneuItems
import kotlinx.android.synthetic.main.request_item_row.view.*

class MenuItemAdapter (val mList: List<MneuItems>) : RecyclerView.Adapter<MenuItemAdapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val child = mList[position]
        // holder.imageView.setImageResource(child.image)

        val str1 = child.title
        val str2 = "-"
        val str3 = "$"
        val str4 = child.amount
//sweets - $ 80
        // string interpolation
        val str5 = "$str1 $str2 $str3 $str4"


        holder.textView?.setText(str5)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textView: TextView = itemView.tvMenu1
        // val txt_brand=itemView.findViewById(R.id.txt_brand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.request_item_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int
    {
        return mList.size
    }


}
