package com.app.perk99restaurant.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
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
import kotlinx.android.synthetic.main.activity_sign_up.*


import java.lang.Exception
import java.util.HashMap

//https://github.com/mmobin789?tab=overview&from=2020-04-01&to=2020-04-30
class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    var api: InterfaceApi? = null
    var disposable: Disposable? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        btnSignUp.setOnClickListener(this)
        txt_login.setOnClickListener(this)
        api = Injector.provideApi()

//        val font = Typeface.createFromAsset(assets, "Raleway-Regular.ttf")
//        cc.textView_selectedCountry.setTypeface(font)
    //    cc.textView_selectedCountry.setTextColor(R.color.app_orange)
//        radio_french.setTypeface(font)
    }
    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.txt_login -> {
                finish()
            }

            

            R.id.btnSignUp -> {

                if (validateCredentials())
                {
                    signUpApi()

                }
            }
        }
    }
    // login Api
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun signUpApi() {
        CommonMethod.showProgress(this)
        val helper = SharedPrefUtil.getInstance()
        val hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("rest_name", edt_fst_name.text.toString())
        hashMap.put("provider", "merchants")
            hashMap.put("address1",edtAddress.text.toString())
        hashMap.put("email", edtEmail!!.text.toString())
        hashMap.put("password", edtPassword!!.text.toString())
        hashMap.put("mobile", edt_number!!.text.toString())
        hashMap.put("password", edtPassword!!.text.toString())
       // hashMap.put("deviceId",CommonMethod.getDeviceId(this))
        hashMap.put("country_code", cc.selectedCountryCode)

        disposable = api!!.register(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    CommonMethod.hideProgress()
                    try {
                        val generalResponse = GeneralResponse(result)
                        val jsonObject = generalResponse.getResponse_object();
                        Log.v("jsonObject", "" + jsonObject)

                        if (jsonObject.getString("success").equals("true")) {
                      //      val jsonInnerObject= jsonObject.getJSONObject("user")
                            CommonMethod.showToast("sign up successfully")
                      val jsonObjectData = jsonObject.getJSONObject("data")

                            helper.saveBoolean(SharedPrefUtil.LOGIN, true)
                            helper.saveString(SharedPrefUtil.AUTH_TOKEN, jsonObjectData.getString("token"))
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        } else {
                            CommonMethod.showToast(jsonObject.getString("message"))
                        }

                    } catch (ex: Exception) {
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


    // validation method
    private fun validateCredentials(): Boolean {

        if(edt_fst_name!!.text.isEmpty()){

            CommonMethod.showToast(this, getString(R.string.please_enter_user_name))
            edt_fst_name.requestFocus()
            return false
        }

        else if(!CommonMethod.isValidName(edt_fst_name.text.toString())){
            CommonMethod.showToast(this, getString(R.string.please_enter_valid_user_name))
            edt_fst_name.requestFocus()
            return false
        }
       /* if(edt_last_name!!.text.isEmpty()){

            CommonMethod.showToast(this, getString(R.string.please_enter_last_name))
            edt_last_name.requestFocus()
            return false
        }*/
        else if (edtEmail!!.text.isEmpty()) {

            CommonMethod.showToast(this, getString(R.string.please_enter_email_address))
            edtEmail.requestFocus()
            return false
        } else if (!CommonMethod.isValidEmail(edtEmail.text.toString())) {
            CommonMethod.showToast(this, getString(R.string.enter_valid_email))
            edtEmail.requestFocus()
            return false
        }

        else if(edtAddress!!.text.isEmpty())
        {
            CommonMethod.showToast(getString(R.string.please_enter_address))
            edtAddress.requestFocus()
            return false
        }
        else if(edt_number!!.text.isEmpty()){
            CommonMethod.showToast(getString(R.string.please_enter_phone_number))
            edt_number.requestFocus()
            return false
        }
        else if(!CommonMethod.isValidMobile(edt_number.text.toString()))
        {
            CommonMethod.showToast(getString(R.string.please_enter_valid_number))
            edt_number.requestFocus()

            return false

        }
        else if(edtAddress!!.text.isEmpty())
        {
            CommonMethod.showToast(getString(R.string.please_enter_address))
            edtAddress.requestFocus()
            return false
        }

        else if (edtPassword!!.text.isEmpty()) {
            CommonMethod.showToast(this, getString(R.string.please_enter_password))
            edtPassword.requestFocus()
            return false
        } else if (edtPassword!!.text.length < 6) {
            CommonMethod.showToast(this, getString(R.string.val_min_6_char))
            edtPassword.requestFocus()
            return false
        }



        return true
    }
}
