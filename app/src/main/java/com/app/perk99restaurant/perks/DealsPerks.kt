package com.app.perk99restaurant.perks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.perk99restaurant.R
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.deals_perks.*
import kotlinx.android.synthetic.main.header_small.*
import kotlinx.android.synthetic.main.header_small.txt_header
import kotlinx.android.synthetic.main.profile_view.*

class DealsPerks : AppCompatActivity()
{

    var disposable: Disposable? = null
    var api: InterfaceApi? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deals_perks)
        txt_header.setText("My Deals")
        img_back_btn.visibility=View.VISIBLE
        api = Injector.provideApi()

        img_back_btn.setOnClickListener {
            onBackPressed()
        }


        btPerks!!.setOnClickListener {
            Log.e("ButtonCLick ","click")
            val it=Intent(this,AddDailyPerks::class.java)
            startActivity(it)
        }
    }

    override fun onResume() {
        super.onResume()
        getPerks()
    }


    private fun getPerks()
    {
        disposable = api!!.getPerks("merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try
                {
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();
                    Log.e("MerchantProfile ",jsonObject.toString())

                    if (jsonObject.getString("success").equals("true",ignoreCase = true))
                    {
                        tvNoDeal.visibility=View.GONE
                        ccDeal.visibility=View.VISIBLE
//                      val jsonObjectData = jsonObject.getJSONObject("data")
                        val jsonObjectData = jsonObject.getJSONObject("data")
                        tvDiscount.setText(jsonObjectData.getString("discount")+" %")
                        tvValid.setText("Valid For: "+jsonObjectData.getString("valid_days")+" days")
                        tvDesc.setText(jsonObjectData.getString("description"))
                    }
                    else
                    {
                        tvNoDeal.visibility=View.VISIBLE
                        ccDeal.visibility=View.GONE
                    }
                }
                catch (ex: java.lang.Exception)
                {
                    CommonMethod.hideProgress()
                    ex.printStackTrace()
                }
            }, {
                    error ->
                CommonMethod.hideProgress()
                CommonMethod.showToast(getString(R.string.server_error))
            })
    }
}