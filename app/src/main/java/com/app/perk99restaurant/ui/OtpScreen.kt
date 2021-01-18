package com.app.perks99.ui

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
import com.app.perk99restaurant.utils.MyApplication
import com.app.perk99restaurant.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.rlButtons
import kotlinx.android.synthetic.main.otp_screen.*
import java.util.HashMap

class OtpScreen :AppCompatActivity(), View.OnClickListener
{
    var disposable: Disposable? = null
    var api: InterfaceApi? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_screen)


        if (intent.hasExtra("email"))
        {
            email= intent.extras?.getString("email").toString()
            Log.e("Email ",email)
        }

        api = Injector.provideApi()
        rlButtonsOTP.setOnClickListener(this)
        btnSubmitOTP.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.rlButtonsOTP -> {
                Log.e("SubmitButtons ","submit")
                if (validateCredentials())
                {
                    if (!MyApplication.hasNetwork())
                    {
                        CommonMethod.showToast(getString(R.string.no_internet))
                    } else
                    {
                        callOTpVerify()
                    }
                }
            }
            R.id.btnSubmitOTP -> {
                Log.e("ButtonClick ","button")

                if (validateCredentials())
                {
                    callOTpVerify()

                }
            }
        }
    }


    fun validateCredentials(): Boolean {
        if (edtNumber!!.text.isEmpty())
        {
            CommonMethod.showToast(this, "Enter OTP Number")
            return false
        }
        return true
    }

    var email:String=""

    private fun callOTpVerify()
    {
        CommonMethod.showProgress(this)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("otp", edtNumber!!.text.toString())
        hashMap.put("email", email)


        Log.e("verifyOTPParams ",hashMap.toString())

        disposable = api!!.verifyOTP(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    CommonMethod.hideProgress()
                    try {
                        val generalResponse = GeneralResponse(result)
                        val jsonObject = generalResponse.getResponse_object();
                        Log.v("jsonObjectPassword ", "" + jsonObject)

                        if (jsonObject.getString("success").equals("true")) {
                              CommonMethod.showToast("OTP Verified successfully")


                              var intent = Intent(applicationContext, ResetPassword::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)
                              finishAffinity()
                        } else {
                            CommonMethod.showToast(jsonObject.getString("message"))
                        }

                    } catch (ex: java.lang.Exception) {
                        CommonMethod.hideProgress()
                    }

                },

                {
                        error ->

                    CommonMethod.hideProgress()
                    CommonMethod.showToast(getString(R.string.server_error))


                }
            )
    }


}