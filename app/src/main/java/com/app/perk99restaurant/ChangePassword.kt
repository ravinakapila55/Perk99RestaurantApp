package com.app.perk99restaurant

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.app.perk99restaurant.home.HomeActivity
import com.app.perk99restaurant.networking.Injector
import com.app.perk99restaurant.networking.InterfaceApi
import com.app.perk99restaurant.ui.LoginActivity
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.GeneralResponse
import com.app.perk99restaurant.utils.MyApplication
import com.app.perk99restaurant.utils.SharedPrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.change_password.*
import kotlinx.android.synthetic.main.reset_password.*
import kotlinx.android.synthetic.main.reset_password.btSubmitReset
import kotlinx.android.synthetic.main.reset_password.etPassword
import kotlinx.android.synthetic.main.reset_password.rlSubmitReset
import java.util.HashMap

class ChangePassword : AppCompatActivity(), View.OnClickListener
{
    var disposable: Disposable? = null
    var api: InterfaceApi? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)



        api = Injector.provideApi()
        rlSubmitReset.setOnClickListener(this)
        btSubmitReset.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        when (v!!.id)
        {
            R.id.rlSubmitReset ->
            {
                Log.e("SubmitReset ","reset")
                if (validateCredentials())
                {
                    if (!MyApplication.hasNetwork())
                    {
                        CommonMethod.showToast(getString(R.string.no_internet))
                    } else
                    {
                        callChnage()
                    }
                }
            }

            R.id.btSubmitReset ->
            {
                Log.e("InsideButtonSubmit  ","submit")
                if (validateCredentials())
                {
                    callChnage()
                }
            }
        }
    }

    fun validateCredentials(): Boolean
    {
        if (etPassword!!.text.isEmpty())
        {
            CommonMethod.showToast(this, "Enter Old  Password")
            return false
        }else if (etNewPassword!!.text.isEmpty())
        {
            CommonMethod.showToast(this, "Enter New  Password")
            return false
        }
        else if (etPassword!!.text.length<6)
        {
            CommonMethod.showToast(this, "Enter atleast 6 digits password")
            return false
        }
        return true
    }

    var email:String=""

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun callChnage()
    {
        CommonMethod.showProgress(this)
        val helper = SharedPrefUtil.getInstance()

        val hashMap: HashMap<String, String> = HashMap<String, String>()
        hashMap.put("old_password", etPassword!!.text.toString())
        hashMap.put("new_password", etNewPassword!!.text.toString())
        hashMap.put("provider","merchants")


        Log.e("verifyOTPParams ",hashMap.toString())

        disposable = api!!.changepassword(hashMap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    CommonMethod.hideProgress()
                    try {
                        val generalResponse = GeneralResponse(result)
                        val jsonObject = generalResponse.getResponse_object();
                        Log.v("jsonObjectPassword ", "" + jsonObject)

                        if (jsonObject.getString("status").equals("200")) {
                            CommonMethod.showToast("Password Change successfully")
                            var intent = Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        } else
                        {
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

