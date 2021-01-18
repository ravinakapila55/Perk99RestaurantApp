package com.app.perk99restaurant.home.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.NearByDrivers
import com.app.perk99restaurant.home.adapters.AcceptedOrderFragmentAdapter
import com.app.perk99restaurant.home.models.MenuItemsModel
import com.app.perk99restaurant.home.models.ModelAcceptedOrders
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.SharedPrefUtil
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.accepted_orders.*
import kotlinx.android.synthetic.main.accepted_orders.view.*
import kotlinx.android.synthetic.main.fragment_home.recycler_request
import kotlinx.android.synthetic.main.fragment_home.view.recycler_request
import kotlinx.android.synthetic.main.header_small.view.txt_header
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap
import kotlin.collections.ArrayList

class AcceptedOrdersFragment: Fragment(),View.OnClickListener
{
    override fun onClick(v: View?)
    {
        when (v!!.id)
        {
            R.id.tvPreparing ->
            {

                tvPreparing.setTextColor(resources.getColor(R.color.red))
                tvPreparing.setBackgroundResource(R.drawable.red_background)

                tvReady.setTextColor(resources.getColor(R.color.app_black))
                tvReady.setBackgroundResource(R.drawable.gray_fill_background)

                tvPickup.setTextColor(resources.getColor(R.color.app_black))
                tvPickup.setBackgroundResource(R.drawable.gray_fill_background)

                if (mList!!.size>0)
                {
                    recycler_request!!.setVisibility(View.VISIBLE)
                    no_data.setVisibility(View.GONE)
                    tvPreparing.setText("PREPARE("+mList!!.size+")")
                    setAdapter("prepare")
                }
                else{
                    recycler_request!!.setVisibility(View.GONE)
                    no_data.setVisibility(View.VISIBLE)
                }
            }
            R.id.tvReady ->
            {

                tvReady.setTextColor(resources.getColor(R.color.red))
                tvReady.setBackgroundResource(R.drawable.red_background)

                tvPreparing.setTextColor(resources.getColor(R.color.app_black))
                tvPreparing.setBackgroundResource(R.drawable.gray_fill_background)

                tvPickup.setTextColor(resources.getColor(R.color.app_black))
                tvPickup.setBackgroundResource(R.drawable.gray_fill_background)


                if (mReady!!.size>0)
                {
                    recycler_request!!.setVisibility(View.VISIBLE)
                    no_data.setVisibility(View.GONE)
                    tvReady.setText("READY("+mReady!!.size+")")
                    setAdapter("ready")
                }
                else{
                    recycler_request!!.setVisibility(View.GONE)
                    no_data.setVisibility(View.VISIBLE)
                }
            }


            R.id.tvPickup ->
            {
                tvReady.setTextColor(resources.getColor(R.color.app_black))
                tvReady.setBackgroundResource(R.drawable.gray_fill_background)

                tvPreparing.setTextColor(resources.getColor(R.color.app_black))
                tvPreparing.setBackgroundResource(R.drawable.gray_fill_background)

                tvPickup.setTextColor(resources.getColor(R.color.red))
                tvPickup.setBackgroundResource(R.drawable.red_background)


                if (mPick!!.size>0)
                {
                    recycler_request!!.setVisibility(View.VISIBLE)
                    no_data.setVisibility(View.GONE)
                    tvPickup.setText("PICK("+mPick!!.size+")")
                    setAdapter("pick")
                }
                else{
                    recycler_request!!.setVisibility(View.GONE)
                    no_data.setVisibility(View.VISIBLE)
                }              }
        }
    }

    fun findsIds(view: View)
    {
        view.recycler_request.apply {
            layoutManager = LinearLayoutManager(activity)
//              adapter = RequestAdapter(!!)
        }
        view.tvPreparing.setOnClickListener(this)
        view.tvReady.setOnClickListener(this)
        view.tvPickup.setOnClickListener(this)
    }

    var disposable: Disposable? = null
    var api: InterfaceApi? = null
    private  var mList: ArrayList<ModelAcceptedOrders>? = null
    private  var mReady: ArrayList<ModelAcceptedOrders>? = null
    private  var mPick: ArrayList<ModelAcceptedOrders>? = null

    lateinit var mAcceptedOrderAdapter: AcceptedOrderFragmentAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var orderId:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View?
    {
        val view: View = inflater.inflate(R.layout.accepted_orders, container, false)
        view.txt_header.setText("Orders")
        linearLayoutManager = LinearLayoutManager(activity)
        view.recycler_request.layoutManager = linearLayoutManager
        findsIds(view)
        api = Injector.provideApi()
        return view
    }

    override fun onResume()
    {
        super.onResume()
        getAcceptedApi()
    }

    private fun setAdapter(type:String)
    {
        if (type.equals("prepare",ignoreCase = true))
        {
//            mAcceptedOrderAdapter = AcceptedOrderAdapter(mList!!,activity!!)
            mAcceptedOrderAdapter = AcceptedOrderFragmentAdapter(mList!!,activity!!)
            recycler_request.setAdapter(mAcceptedOrderAdapter)



            mAcceptedOrderAdapter.onItemSelectedListener(object : AcceptedOrderFragmentAdapter.onClickListener
            {
                override fun onNearByClick(layoutPosition: Int)
                {
                    Log.e("ClickId ","dd "+mList!!.get(layoutPosition).id)
                    val intent = Intent(activity, NearByDrivers::class.java)
                    intent.putExtra("id",mList!!.get(layoutPosition).id)
                    startActivity(intent)
                }
                override fun onOrderReady(layoutPositin: Int)
                {
                    orderReady(mList!!.get(layoutPositin).id)

                }
                override fun onOrderPickedUp(layoutPositin: Int)
                {

                }
            })
        }
        else if (type.equals("ready",ignoreCase = true))
        {
            mAcceptedOrderAdapter = AcceptedOrderFragmentAdapter(mReady!!,activity!!)
            recycler_request.setAdapter(mAcceptedOrderAdapter)


            mAcceptedOrderAdapter.onItemSelectedListener(object : AcceptedOrderFragmentAdapter.onClickListener
            {
                override fun onNearByClick(layoutPosition: Int)
                {

                }
                override fun onOrderReady(layoutPositin: Int)
                {

                }

             override fun onOrderPickedUp(layoutPositin: Int)
            {
                orderPickedUp(mReady!!.get(layoutPositin).id)

            }
            })
        }
        else
        {
          mAcceptedOrderAdapter = AcceptedOrderFragmentAdapter(mPick!!,activity!!)
          recycler_request.setAdapter(mAcceptedOrderAdapter)
        }
    }

    private fun orderReady(order_id:String)
    {
        Log.e("InsideorderReadyParam ",order_id)
        CommonMethod.showProgress(activity)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("order_id",order_id)
        hashMap.put("provider","merchants")

        Log.e("orderReadyParameters ",hashMap.toString())

        disposable = api!!.orderReady(hashMap)
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
//                        getRequestApi()

                        val fragment = AcceptedOrdersFragment()
                        val fragmentManager = getFragmentManager()
                        fragmentManager!!.beginTransaction()
                            .replace(com.app.perk99restaurant.R.id.container, fragment)
                            .addToBackStack(null)
                            .commitAllowingStateLoss()

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
            },  {
                    error ->
                CommonMethod.hideProgress()
                CommonMethod.showToast(getString(R.string.server_error))
            })
    }

    private fun orderPickedUp(order_id:String)
    {
        Log.e("InsideorderReadyParam ",order_id)

        CommonMethod.showProgress(activity)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("order_id",order_id)
        hashMap.put("provider","merchants")

        Log.e("orderReadyParameters ",hashMap.toString())

        disposable = api!!.orderPickedUpbyCustomer(hashMap)
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
//                        getRequestApi()

                        val fragment = AcceptedOrdersFragment()
                        val fragmentManager = getFragmentManager()
                        fragmentManager!!.beginTransaction()
                            .replace(com.app.perk99restaurant.R.id.container, fragment)
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
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
            },  {
                    error ->
                CommonMethod.hideProgress()
                CommonMethod.showToast(getString(R.string.server_error))
            })
    }

    private fun getAcceptedApi()
    {
        disposable = api!!.getAcceptedOrders("merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try {
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();

                    Log.e("AcceptedResponse ",jsonObject.toString())

                    if (jsonObject.getString("status").equals("success",ignoreCase = true))
                    {

                      mList= ArrayList<ModelAcceptedOrders>()
                      mList!!.clear()

                      mReady= ArrayList<ModelAcceptedOrders>()
                      mReady!!.clear()

                      mPick= ArrayList<ModelAcceptedOrders>()
                      mPick!!.clear()

                        val jsonArray = jsonObject.getJSONArray("data")
                        Log.e("lengthhhhh ",jsonArray.length().toString())

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject1 = jsonArray.getJSONObject(i)
                            Log.e("insidrJSON ",jsonObject1.toString())

                            val restaurantsList = ModelAcceptedOrders()
                            restaurantsList.id = jsonObject1.getString("id")
                            restaurantsList.total = jsonObject1.getString("total")
                            restaurantsList.is_assigned = jsonObject1.getString("is_assigned")
                            restaurantsList.status = jsonObject1.getString("status")
                            restaurantsList.user_name = jsonObject1.getString("user_name")
                            restaurantsList.type = jsonObject1.getString("type")

                            if (jsonObject1.getString("type").equals("reservedOrder",ignoreCase = true))
                            {
                                restaurantsList.order_type = jsonObject1.getString("order_type")
                                restaurantsList.meal_type = jsonObject1.getString("meal_type")
                                restaurantsList.pickup_time = jsonObject1.getString("pickup_time")
                            }

                            if (jsonObject1.has("driver_details"))
                            {
                                val o = jsonObject1.get("driver_details")
                                if (o is JSONObject)
                                {
                                    val delivery_boy = jsonObject1.getJSONObject("driver_details")

                                    restaurantsList.driver_id = delivery_boy.getString("id")
                                    restaurantsList.driver_name = delivery_boy.getString("name")
                                    restaurantsList.driver_email = delivery_boy.getString("email")
                                    restaurantsList.driver_phone = delivery_boy.getString("mobile")
                                    restaurantsList.driver_image = delivery_boy.getString("image")

                                }
                            }
                            val item = jsonObject1.getJSONArray("detail")

                            Log.e("MenuItemsArray ",item.length().toString())
                            val list = ArrayList<MenuItemsModel>()

                            if (item.length() > 0)
                            {
                                for (j in 0 until item.length())
                                {
                                    val jsonObject111 = item.getJSONObject(j)
                                    val foodItemModel = MenuItemsModel()
                                    foodItemModel.item_name = jsonObject111.getString("item_name")
                                    foodItemModel.qty = jsonObject111.getString("qty")
                                    list.add(foodItemModel)
                                }
                            }

                            restaurantsList.setList(list)

                            //delivered//pickup
                            if (jsonObject1.getString("status").equals("preparing", ignoreCase = true))
                            {

                                mList!!.add(restaurantsList)
                                tvPreparing.text = "PREPARING(" + mList!!.size + ")"
                            }
                              else if (jsonObject1.getString("status").equals("ready", ignoreCase = true))
                            {
                                   mReady!!.add(restaurantsList)
                                   tvReady.text = "READY(" + mReady!!.size + ")"
                             }
                             else if (jsonObject1.getString("status").equals("pickup", ignoreCase = true))
                            {
                                   mPick!!.add(restaurantsList)
                                   tvPickup.setText("PICK UP(" + mPick!!.size + ")")
                             }
                        }

                        Log.e("sizeList ",mList!!.size.toString())

                        if (mList!!.size > 0)
                        {
                            recycler_request!!.setVisibility(View.VISIBLE)
                            no_data.setVisibility(View.GONE)
                            setAdapter("prepare")
                        }

                        else {
                            recycler_request!!.setVisibility(View.GONE)
                            no_data.setVisibility(View.VISIBLE)
                        }

                    }
                    else
                    {
                        CommonMethod.showToast(jsonObject.getString("message"))
                        recycler_request!!.setVisibility(View.GONE)
                        no_data.setVisibility(View.VISIBLE)
                    }
                }
                catch (ex: java.lang.Exception)
                {
                    CommonMethod.hideProgress()
                    ex.printStackTrace()
                }
            },
                {
                    error ->
                    CommonMethod.hideProgress()
                    CommonMethod.showToast(getString(R.string.server_error))
                })
    }

    /* private fun getAcceptedApi()
    {
        disposable = api!!.getAcceptedOrders("merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try {
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();

                    if (jsonObject.getString("status").equals("success"))
                    {
                        //   mListCategory = generalResponse.getDataAsListDecoded(jsonObject,"categories",Category)
                        // val jsonObjectData = jsonObject.getJSONObject("data")
                        val mObh= CommonResposneSetUp()
                        Log.e("mObh"," "+mObh)
                        mList = getDataAs(jsonObject.getJSONArray("data"))
                        if (mList.size>0)
                        {
                            recycler_request!!.setVisibility(View.VISIBLE)
                            no_data.setVisibility(View.GONE)

                            setAdapter("prepare")

                        }
                        else
                        {
                            recycler_request!!.setVisibility(View.GONE)
                            no_data.setVisibility(View.VISIBLE)
                        }
                    }
                    else
                    {
                        CommonMethod.showToast(jsonObject.getString("message"))
                        recycler_request!!.setVisibility(View.GONE)
                        no_data.setVisibility(View.VISIBLE)
                    }
                }
                catch (ex: Exception)
                {
                    Log.e("dddd"," "+ex.printStackTrace())
                    CommonMethod.hideProgress()
                }
            },
                {
                        error ->
                    CommonMethod.hideProgress()
                    CommonMethod.showToast(getString(R.string.server_error))
                });
    }*/

    fun getDataAs(jsonArray: JSONArray? = null): ArrayList<ModelAcceptedOrders>
    {
        val len = jsonArray!!.length()
        val gson = Gson()
        val list = ArrayList<ModelAcceptedOrders>(len)

        for (i in 0 until jsonArray.length())
        {
            list.add(gson.fromJson<ModelAcceptedOrders>(jsonArray.getString(i),ModelAcceptedOrders::class.java))
        }

        return list
    }

    companion object
    {
        const val TAG       = "AcceptedOrder"
        fun newInstance()   = AcceptedOrdersFragment()
    }
}


