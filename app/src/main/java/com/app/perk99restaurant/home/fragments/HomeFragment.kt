package com.app.perk99restaurant.home.fragments


import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.perk99restaurant.home.adapters.RequestAdapter
import com.app.perk99restaurant.home.models.GetOrderList
import com.app.perk99restaurant.networking.CommonResposneSetUp
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recycler_request
import kotlinx.android.synthetic.main.fragment_home.view.recycler_request
import kotlinx.android.synthetic.main.header_small.view.*
import java.util.HashMap
import com.app.perk99restaurant.R


/*
 A simple [Fragment] subclass.
 */

class HomeFragment : Fragment()
{
    var disposable: Disposable? = null
    var api: InterfaceApi? = null
    private lateinit var mList: ArrayList<GetOrderList>
    lateinit var mRequestAdapter: RequestAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        view.txt_header.setText("Requests")
        linearLayoutManager = LinearLayoutManager(activity)
        view.recycler_request.layoutManager = linearLayoutManager
        findsIds(view)
        api = Injector.provideApi()
        getRequestApi()
        return view
    }

    private fun acceptOrder(order_id:String)
    {
        CommonMethod.showProgress(activity)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("order_id",order_id)
        hashMap.put("provider","merchants")

        Log.e("AcceptParameters ",hashMap.toString())

        disposable = api!!.accept_order(hashMap)
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

    private fun rejectOrder(order_id:String,reason:String)
    {
        CommonMethod.showProgress(activity)
        val   helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("order_id",order_id)
        hashMap.put("provider","merchants")
        hashMap.put("reason",reason)

        Log.e("RejectParams ",hashMap.toString())

        disposable = api!!.reject_order(hashMap)
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
                        getRequestApi()


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

    private fun getRequestApi()
    {
        disposable = api!!.getOrders("merchants")
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
                            mList = mObh.getDataAs(jsonObject.getJSONArray("data"))
                            if (mList.size>0)
                            {
                                recycler_request!!.setVisibility(View.VISIBLE)
                                txt_no_data.setVisibility(View.GONE)

                                mRequestAdapter = RequestAdapter(mList,activity!!)
                                recycler_request.setAdapter(mRequestAdapter)

                                mRequestAdapter.onItemSelectedListener(object : RequestAdapter.onClickListener
                                {
                                    override fun onAcceptClick(layoutPosition: Int)
                                    {
                                        acceptOrder(jsonObject.getJSONArray("data").getJSONObject(layoutPosition).getString("id"))
                                    }
                                    override fun onRejectClick(layoutPositin: Int)
                                    {
                                        callSubmitReasonPopup(jsonObject.getJSONArray("data").getJSONObject(layoutPositin).getString("id"))
                                    }
                                })
                            }
                            else
                            {
                             recycler_request!!.setVisibility(View.GONE)
                             txt_no_data.setVisibility(View.VISIBLE)
                            }
                        }
                        else
                        {
                            CommonMethod.showToast(jsonObject.getString("message"))
                            recycler_request!!.setVisibility(View.GONE)
                            txt_no_data.setVisibility(View.VISIBLE)
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
    }


    private fun callSubmitReasonPopup(order_Id:String)
    {
        val inflator: LayoutInflater? =null
        val dialogBuilder = android.app.AlertDialog.Builder(activity)
        val inflater = layoutInflater
        val dialogView = inflater!!.inflate(R.layout.submit_reason, null)

        val tvSave = dialogView.findViewById(R.id.tvSave) as TextView
        val etReason = dialogView.findViewById(R.id.etReason) as EditText

        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        val alertDialog = dialogBuilder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        val height = WindowManager.LayoutParams.WRAP_CONTENT


        tvSave.setOnClickListener {

            alertDialog!!.dismiss()
            rejectOrder(order_Id,etReason!!.text.toString().trim())
        }



        alertDialog.show()
    }

    fun findsIds(view: View)
    {
      /*  view.recycler_request.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RequestAdapter(activity!!)
      }*/
    }

    companion object
    {
        const val TAG   = "HomeFragment"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
        fun newInstance()   = HomeFragment()
    }

}
