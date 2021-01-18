package com.app.perk99restaurant.home


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.perk99restaurant.R
import com.app.perk99restaurant.home.fragments.AcceptedOrdersFragment
import com.app.perk99restaurant.home.fragments.HomeFragment
import com.app.perk99restaurant.menu.MenuList
import com.app.perk99restaurant.settings.SettingFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rel_request.setOnClickListener(this)
        rel_menu.setOnClickListener(this)
        rel_order.setOnClickListener(this)
        rel_profile.setOnClickListener(this)
      //  loadFragment()
    }

    private fun loadFragment()
    {
        setHomeSelected()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        val fragment = FindARideFragment()
//        replaceFragment(fragment)
        transaction.replace(R.id.container, HomeFragment.newInstance(), HomeFragment.TAG)
            // .addToBackStack("")
            .commit()

    }

    override fun onResume() {
        super.onResume()
        getNoti()


    }

    var key:String=""
    private fun getNoti()
    {
        if (intent.hasExtra("noti_type"))
        {
            key = intent.extras!!.getString("noti_type").toString()
            Log.e("KeyValues ", key)
            if (key.equals("new",ignoreCase = true))
            {
                loadFragment()
            }
            else
            {
                val fragment = AcceptedOrdersFragment()
                replaceFragment(fragment)
                setOrderSelected()
            }
        } else
        {
           loadFragment()
        }
    }

    fun setHomeSelected()
    {
        img_request.setImageResource(R.mipmap.ic_request_active)
        img_menu.setImageResource(R.mipmap.ic_menu)
        img_order.setImageResource(R.mipmap.ic_order)
        img_profile.setImageResource(R.drawable.ic_setting_tab)
        txt_request.setTextColor(ContextCompat.getColor(this, R.color.red));
        txt_menu.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_order.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_profile.setTextColor(ContextCompat.getColor(this, R.color.app_black));
    }

    fun setOrderSelected()
    {
        img_request.setImageResource(R.mipmap.ic_request)
        img_menu.setImageResource(R.mipmap.ic_menu)
        img_order.setImageResource(R.mipmap.ic_order_active)
        img_profile.setImageResource(R.drawable.ic_setting_tab)
        txt_request.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_menu.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_order.setTextColor(ContextCompat.getColor(this, R.color.red));
        txt_profile.setTextColor(ContextCompat.getColor(this, R.color.app_black));
    }

    fun setSettingsSelected()
    {
        img_request.setImageResource(R.mipmap.ic_request)
        img_menu.setImageResource(R.mipmap.ic_menu)
        img_order.setImageResource(R.mipmap.ic_order)
        img_profile.setImageResource(R.drawable.ic_setting_tab_sel)
        txt_request.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_menu.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_order.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_profile.setTextColor(ContextCompat.getColor(this, R.color.red));
    }

    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun setMenuSelected()
    {
        img_request.setImageResource(R.mipmap.ic_request)
        img_menu.setImageResource(R.mipmap.ic_menu_active)
        img_order.setImageResource(R.mipmap.ic_order)
        img_profile.setImageResource(R.drawable.ic_setting_tab)
        txt_request.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_menu.setTextColor(ContextCompat.getColor(this, R.color.red));
        txt_order.setTextColor(ContextCompat.getColor(this, R.color.app_black));
        txt_profile.setTextColor(ContextCompat.getColor(this, R.color.app_black));
    }

    override fun onClick(v: View?)
    {
        when (v!!.id)
        {
            R.id.rel_request ->
            {
                val fragment = HomeFragment()
                replaceFragment(fragment)
               setHomeSelected()
            }
            R.id.rel_menu ->
            {
                val fragment = MenuList()
                replaceFragment(fragment)
                setMenuSelected()
            }
            R.id.rel_order ->
            {
                val fragment = AcceptedOrdersFragment()
                replaceFragment(fragment)
                setOrderSelected()
            }
            R.id.rel_profile ->
            {
                val fragment = SettingFragment()
                replaceFragment(fragment)
                setSettingsSelected()
            }
        }
    }

}
