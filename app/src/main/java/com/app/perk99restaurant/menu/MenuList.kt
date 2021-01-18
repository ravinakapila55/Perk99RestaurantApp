package com.app.perk99restaurant.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.faq.*
import kotlinx.android.synthetic.main.header_small.view.*
import org.json.JSONArray
import java.util.ArrayList
import java.util.HashMap

class MenuList :  Fragment(), View.OnClickListener {

    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onClick(v: View?)
    {
        when (v!!.id)
        {
           /* R.id.img_back_btn ->
            {
              onBackPressed()
            }*/
        }
    }


    private fun getDataAs(jsonArray: JSONArray? = null): ArrayList<MenuModel>
    {
        val len = jsonArray!!.length()
        val gson = Gson()
        val list = ArrayList<MenuModel>(len)
        for (i in 0 until jsonArray.length())
        {
            list.add(gson.fromJson<MenuModel>(jsonArray.getString(i), MenuModel::class.java))
        }
        return list
    }


    private fun callFAQ()
    {
        CommonMethod.showProgress(activity)
        val hashMap: HashMap<String, String> = HashMap<String, String>()
       /* hashMap.put("user_type", "merchant")
        Log.e("hash", "sss " + hashMap)*/
        disposable = api!!.getMenu("merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try
                {
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();
                    if (jsonObject.getString("status").equals("success"))
                    {
                        mList=getDataAs(jsonObject.getJSONArray("data"))
                        if (jsonObject.getJSONArray("data").length()==0)
                        {
                            recycler_faq.setVisibility(View.GONE)
                        }
                        else
                        {
                            recycler_faq.setVisibility(View.VISIBLE)

                            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
                            recycler_faq.layoutManager = layoutManager

                            val orderAdapter = this!!?.let{
                                activity?.let { it1 -> MenuAdapter(mList , it1) }
                            }
                            recycler_faq.setAdapter(orderAdapter)
                        }
                    }
                    else
                    {
                        CommonMethod.showToast(jsonObject.getString("message"))
                    }
                }
                catch (ex: Exception)
                {
                    CommonMethod.hideProgress()
                }
            }, { error ->
                CommonMethod.hideProgress()
                CommonMethod.showToast(getString(R.string.server_error))
            });
    }

    var api: InterfaceApi? = null
    var disposable: Disposable? = null
    private lateinit var mList: ArrayList<MenuModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view: View = inflater.inflate(R.layout.faq, container, false)
        view.txt_header.setText("Menu")
        api = Injector.provideApi()
        val recycler_faq=view.findViewById(R.id.recycler_faq) as RecyclerView
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        recycler_faq.layoutManager = layoutManager
        callFAQ()
        return view
    }

}