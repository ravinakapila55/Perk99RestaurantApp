package com.app.perks99.faq

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.perk99restaurant.R
import com.app.perk99restaurant.faq.FAQModel
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse

import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.faq.*
import kotlinx.android.synthetic.main.header_small.*
import org.json.JSONArray
import java.net.InterfaceAddress
import java.util.*

class Faq : AppCompatActivity(), View.OnClickListener {

    private lateinit var layoutManager: RecyclerView.LayoutManager



    override fun onClick(v: View?)
    {
        when (v!!.id)
        {
            R.id.img_back_btn ->
            {
                onBackPressed()
            }
        }
    }


    private fun getDataAs(jsonArray: JSONArray? = null): ArrayList<FAQModel>
    {
        val len = jsonArray!!.length()
        val gson = Gson()
        val list = ArrayList<FAQModel>(len)
        for (i in 0 until jsonArray.length())
        {
            list.add(gson.fromJson<FAQModel>(jsonArray.getString(i), FAQModel::class.java))
        }
        return list
    }


    private fun callFAQ()
    {
        CommonMethod.showProgress(this)
        val hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("user_type", "merchant")
        Log.e("hash", "sss " + hashMap)
        disposable = api!!.FAQ(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                    CommonMethod.hideProgress()
                    try
                    {
                        val generalResponse = GeneralResponse(result)
                        val jsonObject = generalResponse.getResponse_object();
                        if (jsonObject.getString("success").equals("true"))
                        {
                            mList=getDataAs(jsonObject.getJSONArray("data"))
                            if (jsonObject.getJSONArray("data").length()==0)
                            {
                                recycler_faq.setVisibility(View.GONE)
                            }
                            else
                            {
                                recycler_faq.setVisibility(View.VISIBLE)

                                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
                                recycler_faq.layoutManager = layoutManager

                                val orderAdapter = this!!?.let{
                                    FAQAdapter(mList , it)
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
    private lateinit var mList: ArrayList<FAQModel>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faq)
        img_back_btn.visibility=View.VISIBLE
        img_back_btn.setOnClickListener(this)
        txt_header.setText("FAQ's")
        api = Injector.provideApi()
        val recycler_faq=findViewById(R.id.recycler_faq) as RecyclerView
        layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@Faq)
        recycler_faq.layoutManager = layoutManager
        callFAQ()
    }
}