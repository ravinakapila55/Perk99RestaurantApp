package com.app.perk99restaurant.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.models.Detail
import com.app.perk99restaurant.home.models.MenuItemsModel
import kotlinx.android.synthetic.main.request_item_row.view.*


class RequestItemAdapter(val mList: List<Detail>) : RecyclerView.Adapter<RequestItemAdapter.ViewHolder>()
{
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

    override fun onBindViewHolder(holder: RequestItemAdapter.ViewHolder, position: Int)
    {
        val child = mList[position]
        // holder.imageView.setImageResource(child.image)

        val str1 = child.qty
        val str2 = "X"
        val str3 = child.itemName

        // string interpolation
        val str4 = "$str1 $str2 $str3"


        holder.textView?.setText(str4)




    }
}


