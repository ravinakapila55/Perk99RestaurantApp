package com.app.perk99restaurant.networking


import com.app.perk99restaurant.home.models.GetOrderList
import com.google.gson.Gson
import org.json.JSONArray

class CommonResposneSetUp {


     fun getDataAs(jsonArray: JSONArray? = null): ArrayList<GetOrderList> {

        val len = jsonArray!!.length()
        val gson = Gson()
        val list = ArrayList<GetOrderList>(len)
        for (i in 0 until jsonArray.length()) {
            list.add(
                gson.fromJson<GetOrderList>(
                    jsonArray.getString(i),
                    GetOrderList::class.java
                )
            )
        }
        return list

    }






}