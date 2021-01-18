package com.app.perk99restaurant.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.perk99restaurant.ChangePassword
import com.app.perk99restaurant.R
import com.app.perk99restaurant.earnings.EarningSection
import com.app.perk99restaurant.perks.DealsPerks
import com.app.perk99restaurant.profile.ProfileFragment
import com.app.perk99restaurant.reviews.RatingReviews
import com.app.perk99restaurant.ui.LoginActivity
import com.app.perk99restaurant.utils.CommonMethod
import com.app.perk99restaurant.utils.OnClickedOk
import com.app.perk99restaurant.utils.SharedPrefUtil
import com.app.perks99.faq.Faq
import kotlinx.android.synthetic.main.setting.view.*

class SettingFragment :  Fragment(), View.OnClickListener, OnClickedOk {


    override fun onYesClicked()
    {
        SharedPrefUtil.getInstance().onLogout();
        val intent= Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent);
    }

    override fun onClick(v: View?)
    {
        when (v!!.id)
        {
            R.id.ccLogout ->
            {
                CommonMethod.logout(activity, this)
            }

            R.id.ccProfile ->
            {
                val intent = Intent(activity, ProfileFragment::class.java)
                startActivity(intent)
            }

            R.id.ccEarning ->
            {
                val intent = Intent(activity, EarningSection::class.java)
                startActivity(intent)
            }

            R.id.ccPerks ->
            {
                val intent = Intent(activity, DealsPerks::class.java)
                startActivity(intent)
            }

            R.id.ccRevenueEarning ->
            {
              /* val intent = Intent(activity, ActualEarning::class.java)
                startActivity(intent)
               */
            }

            R.id.ccFAQ ->
            {
               val intent = Intent(activity, Faq::class.java)
               startActivity(intent)
            }

            R.id.ccPswd ->
            {
               val intent = Intent(activity, ChangePassword::class.java)
                startActivity(intent)
            }

            R.id.ccReview ->
            {
               val intent = Intent(activity, RatingReviews::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view: View = inflater.inflate(R.layout.setting, container, false)
        findsIds(view)
        return view
    }

    fun findsIds(view: View)
    {
        view.ccProfile.setOnClickListener(this)
        view.ccPerks.setOnClickListener(this)
        view.ccEarning.setOnClickListener(this)
        view.ccRevenueEarning.setOnClickListener(this)
        view.ccLogout.setOnClickListener(this)
        view.ccFAQ.setOnClickListener(this)
        view.ccPswd.setOnClickListener(this)
        view.ccReview.setOnClickListener(this)
    }

}
