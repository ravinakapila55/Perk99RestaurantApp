package com.app.perk99restaurant.earnings

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.adapters.NearByDriversAdapter
import com.app.perk99restaurant.networking.CommonResposneSetUp
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.earning.*
import kotlinx.android.synthetic.main.header_small.*
import kotlinx.android.synthetic.main.header_small.txt_header
import kotlinx.android.synthetic.main.near_by_drivers.*
import kotlinx.android.synthetic.main.profile_view.*
import java.util.HashMap

class EarningSection :AppCompatActivity()
{
    var disposable: Disposable? = null
    var api: InterfaceApi? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earning)
        txt_header.setText("My Earnings")
        img_back_btn.visibility= View.VISIBLE
        api = Injector.provideApi()


        getEarning()

        img_back_btn.setOnClickListener {
            onBackPressed()
        }

        btSend!!.setOnClickListener {



            if (etAmount!!.text.toString().trim().isEmpty())
            {
                CommonMethod.showToast("Please enter some amount to transfer")
            }
            else
            {
                var value = 0
                var amount:String=""
                amount=etAmount!!.text.toString().trim()

                value=amount.toInt()
                Log.e("Value ",value.toString())
                if(value>availableBalnace)
                {
                    CommonMethod.showToast("Entered Amount should n't be more than available amount")
                }
                else
                {
                    sendEarning()
                }
            }

        }

    }
    var availableBalnace=0
    private fun getEarning()
    {
        CommonMethod.showProgress(this)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("provider","merchants")


        Log.e("getEarning ",hashMap.toString())

        disposable = api!!.getEarning(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try
                {
                    //{"status":"success","code":200,"message":"order accepted"}
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();
                    Log.e("jsonObjectAssign ", "" + jsonObject)
                    if (jsonObject.getString("success").equals("true"))
                    {
                        val dataObj = jsonObject.getJSONObject("data")
                        tvAvailable.setText("$ "+dataObj.getString("availabe_balance"))
                        tvPending.setText("$ "+dataObj.getString("pending_clearance"))
                        availableBalnace=dataObj.getString("availabe_balance").toInt()

                    }
                    else
                    {
                        availableBalnace=0
                        tvAvailable.setText("$ 0")
                        tvPending.setText("$ 0")
                    }
                }
                catch (ex: java.lang.Exception)
                {
                    CommonMethod.hideProgress()
                }
            },
                {
                    error ->
                    CommonMethod.hideProgress()
                    CommonMethod.showToast(getString(R.string.server_error))
                    tvAvailable.setText("$ 0")
                    tvPending.setText("$ 0")
                })
    }



    private fun sendEarning()
    {
        CommonMethod.showProgress(this)
        val   helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("amount",etAmount!!.text.toString().trim())

        Log.e("addEarning ",hashMap.toString())

        disposable = api!!.sendEarning(hashMap,"merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try
                {
                    //{"status":"success","code":200,"message":"order accepted"}
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();
                    Log.e("jsonObject", "" + jsonObject)

                    if (jsonObject.getString("status").equals("success"))
                    {
                        CommonMethod.showToast(jsonObject.getString("message"))
                        getEarning()
                    }
                    else
                    {
                        CommonMethod.showToast(jsonObject.getString("message"))
                    }
                }
                catch (ex: java.lang.Exception)
                {
                    CommonMethod.hideProgress()
                }
            }, {
                    error ->
                CommonMethod.hideProgress()
                CommonMethod.showToast(getString(R.string.server_error))
            })
    }
}