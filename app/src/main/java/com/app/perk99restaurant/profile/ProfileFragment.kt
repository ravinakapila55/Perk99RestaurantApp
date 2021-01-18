package com.app.perk99restaurant.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.HomeActivity
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.ui.LoginActivity
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.OnClickedOk
import com.app.perk99restaurant.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.profile_view.*
import kotlinx.android.synthetic.main.profile_view.view.*

class ProfileFragment :AppCompatActivity(), View.OnClickListener
{
    var disposable: Disposable? = null
    var api: InterfaceApi? = null



    override fun onClick(v: View?)
    {

    }

    private fun getProfile()
    {
        disposable = api!!.getMerchantProfile("merchants")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                CommonMethod.hideProgress()
                try {
                    val generalResponse = GeneralResponse(result)
                    val jsonObject = generalResponse.getResponse_object();
                    Log.e("MerchantProfile ",jsonObject.toString())

                    if (jsonObject.getString("status").equals("success",ignoreCase = true))
                    {
//                      val jsonObjectData = jsonObject.getJSONObject("data")
                        val jsonObjectData = jsonObject.getJSONObject("data")
                        edt_fst_name.setText(jsonObjectData.getString("rest_name"))
                        edtEmail.setText(jsonObjectData.getString("email"))
                        edt_number.setText(jsonObjectData.getString("mobile"))
                        edtAddress.setText(jsonObjectData.getString("address"))

                     /* helper.saveString(SharedPrefUtil.AUTH_TOKEN, jsonObjectData.getString("access_token"))
                        helper.saveString(SharedPrefUtil.NAME, jsonObjectData.getString("rest_name"))
                        helper.saveString(SharedPrefUtil.EMAIL, jsonObjectData.getString("email"))
                        helper.saveString(SharedPrefUtil.PHONE_NUMBER, jsonObjectData.getString("mobile"))
                        helper.saveString(SharedPrefUtil.AADRESS, jsonObjectData.getString("address"))*/
                    }
                    else
                    {

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


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_view)
        api = Injector.provideApi()
        getProfile()
        findsIds()
    }



    fun findsIds()
    {

    }
}