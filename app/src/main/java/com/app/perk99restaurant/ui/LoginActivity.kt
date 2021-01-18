package com.app.perk99restaurant.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.HomeActivity
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.MyApplication
import com.app.perk99restaurant.utils.SharedPrefUtil
import com.app.perks99.ui.ForgetPass
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.HashMap

class LoginActivity : AppCompatActivity(), View.OnClickListener
{
    // var api: InterfaceApi? = null
    internal var fcmToken: String? = null
    internal var socialName: String? = null
    internal var socialid: String? = null
    internal var loginType: String? = null
    internal var socialEmail: String? = null

    var disposable: Disposable? = null
    var api: InterfaceApi? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txt_sign_up.setOnClickListener(this)
        txt_forgot_password.setOnClickListener(this)
        btnlogin.setOnClickListener(this)
        api = Injector.provideApi()
    }

    override fun onClick(v: View?)
    {
        when (v!!.id)
        {
            R.id.txt_sign_up ->
            {
                val intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)
            }

            R.id.txt_forgot_password ->
            {
                Log.e("ForgetClick ","")
                val intent = Intent(this,ForgetPass::class.java)
                startActivity(intent)
            }

            R.id.btnlogin ->
            {
                if (validateCredentials())
                {
                    loginApi()

                }
            }
        }
    }

    // login Api
    private fun loginApi()
    {
        CommonMethod.showProgress(this)
        val helper = SharedPrefUtil.getInstance()

         if(fcmToken==null)
        {
            FirebaseApp.initializeApp(this@LoginActivity)

            //fcmToken="123456sdfgfgfhgghgf"
            fcmToken = FirebaseInstanceId.getInstance().token
        }

        Log.e("sss ","sss"+fcmToken)

        val hashMap: HashMap<String, String> = HashMap<String, String>()
//        hashMap.put("email", edtEmail!!.text.toString())
//        hashMap.put("password", edtPassword!!.text.toString())
//        hashMap.put("fb_token", fcmToken!!)
//        hashMap.put("deviceId",CommonMethod.getDeviceId(this))
//        hashMap.put("role","user")

        hashMap.put("email", edtEmail!!.text.toString())
        hashMap.put("password", edtPassword!!.text.toString())
        hashMap.put("fcm_token", fcmToken!!)
        hashMap.put("deviceId",CommonMethod.getDeviceId(this))
        hashMap.put("provider", "merchants")
//      hashMap.put("lang",helper!!.getString(SharedPrefUtil.Language))
        Log.e("RestaurantApp ","sss"+hashMap.toString())

        disposable = api!!.login(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                    CommonMethod.hideProgress()
                    try {
                        val generalResponse = GeneralResponse(result)
                        val jsonObject = generalResponse.getResponse_object();
                        Log.v("jsonObject", "" + jsonObject)

                        if (jsonObject.getString("status").equals("success"))
                        {
                            // val jsonInnerObject= jsonObject.getJSONObject("user")
                            CommonMethod.showToast(jsonObject.getString("message"))
//                            val jsonObjectData = jsonObject.getJSONObject("data")
                            val jsonObjectData = jsonObject.getJSONObject("result")

                            helper.saveBoolean(SharedPrefUtil.LOGIN, true)
                            helper.saveString(SharedPrefUtil.AUTH_TOKEN, jsonObjectData.getString("access_token"))
                            helper.saveString(SharedPrefUtil.NAME, jsonObjectData.getString("rest_name"))
                            helper.saveString(SharedPrefUtil.EMAIL, jsonObjectData.getString("email"))
                            helper.saveString(SharedPrefUtil.PHONE_NUMBER, jsonObjectData.getString("mobile"))
                            helper.saveString(SharedPrefUtil.AADRESS, jsonObjectData.getString("address"))
                            helper.saveString(SharedPrefUtil.DELIVERY_TIME, jsonObjectData.getString("estimate_delivery_time"))

                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity()
                            }
                            // helper.saveString(SharedPrefUtil.NAME, jsonInnerObject.getString("username"))
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

    // validation method
    fun validateCredentials(): Boolean
    {
        if (edtEmail!!.text.isEmpty())
        {
            CommonMethod.showToast(this, getString(R.string.please_enter_email_address))
            return false
        } else if (!CommonMethod.isValidEmail(edtEmail.text.toString()))
        {
            CommonMethod.showToast(this, getString(R.string.enter_valid_email))
            return false
        } else if (edtPassword!!.text.isEmpty()) {

            CommonMethod.showToast(this, getString(R.string.please_enter_password))
            return false
        } else if (edtPassword!!.text.length < 6) {
            CommonMethod.showToast(this, getString(R.string.val_min_6_char))
            return false
        }
        return true
    }
}

