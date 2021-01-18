package com.app.perk99restaurant.perks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.perk99restaurant.R
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.add_perks.*
import kotlinx.android.synthetic.main.earning.*
import kotlinx.android.synthetic.main.header_small.*
import java.util.HashMap

class AddDailyPerks : AppCompatActivity()
{

    var disposable: Disposable? = null
    var api: InterfaceApi? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_perks)
        txt_header.setText("Add Perks")
        img_back_btn.visibility= View.VISIBLE
        api = Injector.provideApi()

        img_back_btn.setOnClickListener {
            onBackPressed()
        }

        rlButtons.setOnClickListener {
            savePerks()
        }
    }


    private fun savePerks()
    {
        CommonMethod.showProgress(this)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()

        hashMap.put("provider","merchants")
        hashMap.put("discount",etDiscount.text.toString().trim())
        hashMap.put("valid_days",etValid.text.toString().trim())
        hashMap.put("description",etDescription.text.toString().trim())


        Log.e("savePerks ",hashMap.toString())

        disposable = api!!.savePerks(hashMap)
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
                       CommonMethod.showToast(jsonObject.getString("message"))
                        onBackPressed()
                    }
                    else
                    {
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
}