package com.app.perk99restaurant.reviews

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.adapters.RequestAdapter
import com.app.perk99restaurant.home.models.GetOrderList
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
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.header_small.*
import kotlinx.android.synthetic.main.rating_review.*
import kotlinx.android.synthetic.main.rating_review.txt_no_data
import org.json.JSONArray
import java.util.HashMap

class RatingReviews :AppCompatActivity()
{
    var disposable: Disposable? = null
    var api: InterfaceApi? = null
    private lateinit var mList: ArrayList<RatingReviewModel>
    lateinit var mRequestAdapter: REviewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating_review)
        img_back_btn.visibility=View.VISIBLE
        img_back_btn.setOnClickListener { onBackPressed() }

        txt_header.setText("Reviews")

        linearLayoutManager = LinearLayoutManager(this)
        recycler_review!!.layoutManager = linearLayoutManager
        api = Injector.provideApi()

        getReviewApi()


    }

    private fun getReviewApi()
    {
        disposable = api!!.getReviews("merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try {
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();

                    Log.e("REviewsResponse ",jsonObject.toString())

                    if (jsonObject.getString("success").equals("true"))
                    {
                        //   mListCategory = generalResponse.getDataAsListDecoded(jsonObject,"categories",Category)
                        // val jsonObjectData = jsonObject.getJSONObject("data")

                        mList = getDataAsReviews(jsonObject.getJSONArray("data"))
                        if (mList.size>0)
                        {
                            recycler_review!!.setVisibility(View.VISIBLE)
                            txt_no_data.setVisibility(View.GONE)

                            mRequestAdapter = REviewsAdapter(mList,this)
                            recycler_review.setAdapter(mRequestAdapter)


                            mRequestAdapter.onItemSelectedListener(object : REviewsAdapter.onClickListener
                            {
                                override fun onSubmitClick(layoutPosition: Int,review:String,rating_id:String)
                                {
                                    submitComment(rating_id,review)
                                }

                            })


                        }
                        else
                        {
                            recycler_review!!.setVisibility(View.GONE)
                            txt_no_data.setVisibility(View.VISIBLE)
                        }
                    }
                    else
                    {
                        CommonMethod.showToast(jsonObject.getString("message"))
                        recycler_review!!.setVisibility(View.GONE)
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

    private fun getDataAsReviews(jsonArray: JSONArray? = null): java.util.ArrayList<RatingReviewModel>
    {
        val len = jsonArray!!.length()
        val gson = Gson()
        val list = java.util.ArrayList<RatingReviewModel>(len)
        for (i in 0 until jsonArray.length()) {
            list.add(
                gson.fromJson<RatingReviewModel>(
                    jsonArray.getString(i),
                    RatingReviewModel::class.java
                )
            )
        }
        return list

    }

    private fun submitComment(ratin_id:String,comment:String)
    {
        CommonMethod.showProgress(this)
        val   helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("rating_id",ratin_id)
//        hashMap.put("provider","merchants")
        hashMap.put("rest_message",comment)

        Log.e("addCommentParams ",hashMap.toString())

        disposable = api!!.add_review(hashMap,"merchants")
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

                    if (jsonObject.getString("success").equals("true"))
                    {
                        getReviewApi()
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