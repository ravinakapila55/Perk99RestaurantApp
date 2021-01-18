package com.app.perk99restaurant.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.adapters.NearByDriversAdapter
import com.app.perk99restaurant.home.fragments.HomeFragment
import com.app.perk99restaurant.home.models.NearByDriverModel
import com.app.perk99restaurant.networking.CommonResposneSetUp
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.SharedPrefUtil
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.header_small.*
import kotlinx.android.synthetic.main.near_by_drivers.*
import kotlinx.android.synthetic.main.near_by_drivers.recycler_request
import org.json.JSONArray
import java.util.HashMap

class NearByDrivers : AppCompatActivity(),View.OnClickListener
{
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


    var disposable: Disposable? = null
    var api: InterfaceApi? = null
    private lateinit var mList: ArrayList<NearByDriverModel>

    lateinit var mNearByDriversAdapter: NearByDriversAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


     var order_id:String = ""


    companion object
    {
        const val TAG       = "NearByDrivers"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        fun newInstance()   = HomeFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.near_by_drivers)
        txt_header.setText("Delivery Boy")
        img_back_btn.visibility=View.VISIBLE
        linearLayoutManager = LinearLayoutManager(this)
        recycler_request.layoutManager = linearLayoutManager

        if (intent.hasExtra("id"))
        {
            order_id = intent.extras!!.getString("id").toString()
            Log.e("OrderIdGet "," "+order_id)
        }

        img_back_btn.setOnClickListener(this)

        api = Injector.provideApi()
        callNearByDrivers()
    }

    private fun callNearByDrivers()
    {
        disposable = api!!.getNearByDrivers("merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result ->
                CommonMethod.hideProgress()
                try {
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();

                    if (jsonObject.getString("status").equals("success"))
                    {
                        //   mListCategory = generalResponse.getDataAsListDecoded(jsonObject,"categories",Category)
                        // val jsonObjectData = jsonObject.getJSONObject("data")
                        val mObh= CommonResposneSetUp()
                        mList = getNearDataAs(jsonObject.getJSONArray("data"))

                        if (mList.size>0)
                        {
                            recycler_request!!.setVisibility(View.VISIBLE)
                            tvNoData.setVisibility(View.GONE)
                            mNearByDriversAdapter = NearByDriversAdapter(mList,this!!)
                            recycler_request.setAdapter(mNearByDriversAdapter)
                        }
                        else
                        {
                            recycler_request!!.setVisibility(View.GONE)
                            tvNoData.setVisibility(View.VISIBLE)
                        }
                        mNearByDriversAdapter.onItemSelectedListener(object : NearByDriversAdapter.onClickListener
                        {
                            override fun onAssignDriver(layoutPosition: Int)
                            {
//                                assignDriver("35",jsonObject.getJSONArray("data").getJSONObject(layoutPosition).getString("id"))
                                assignDriver(order_id.toString(),jsonObject.getJSONArray("data").getJSONObject(layoutPosition).getString("id"))
                            }
                        })
                    }
                    else
                    {
                        CommonMethod.showToast(jsonObject.getString("message"))
                        recycler_request!!.setVisibility(View.GONE)
                        tvNoData.setVisibility(View.VISIBLE)
                    }
                }
                catch (ex: Exception)
                {
                    Log.e("dddd"," "+ex.printStackTrace())
                    CommonMethod.hideProgress()
                }},
                {
                    error ->
                    CommonMethod.hideProgress()
                    CommonMethod.showToast(getString(R.string.server_error))
                });
    }

    private fun assignDriver(order_id:String,driver_id:String)
    {
        CommonMethod.showProgress(this)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("order_id",order_id)
        hashMap.put("driver_id",driver_id)
        hashMap.put("provider","merchants")

        Log.e("AcceptParams ",hashMap.toString())

        disposable = api!!.assign_driver(hashMap)
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
                    if (jsonObject.getString("status").equals("success"))
                    {
                       onBackPressed()
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
            },
                {
                    error ->
                CommonMethod.hideProgress()
                CommonMethod.showToast(getString(R.string.server_error))
               })
    }

    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun getNearDataAs(jsonArray: JSONArray? = null): ArrayList<NearByDriverModel>
    {
        val len = jsonArray!!.length()
        val gson = Gson()
        val list = ArrayList<NearByDriverModel>(len)
        for (i in 0 until jsonArray.length())
        {
            list.add(gson.fromJson<NearByDriverModel>(jsonArray.getString(i), NearByDriverModel::class.java))
        }
        return list
    }
}