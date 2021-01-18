package com.app.perk99restaurant.home.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.app.perk99restaurant.R
import com.example.expandablelistviewkotlin.ExpandableAdapter
import kotlinx.android.synthetic.main.header_small.view.*


class MenuFragment : Fragment()
{

    private var expandableAdapter: ExpandableAdapter? = null
    private var expList: ExpandableListView? = null
    private val parents = arrayOf("Starter", "Main Cource", "Desserts")
    private var Action_Movies: ArrayList<String>? = null
    private var Romantic_Movies: ArrayList<String>? = null
    private var Comedy_Movies: ArrayList<String>? = null
//https://vipankumar.in/food_app/public/api/get_menus?provider=merchants
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_menu, container, false)
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)
        view.txt_header.setText("Menu")
        expList = view.findViewById(R.id.exp_list) as ExpandableListView
        setChildMovies()
        expandableAdapter = ExpandableAdapter(activity!!, childList, parents)
        expList!!.setAdapter(expandableAdapter)
       // findsIds(view)
        return view
    }

    private fun setChildMovies()
    {
        Action_Movies = ArrayList()
        Romantic_Movies = ArrayList()
        Comedy_Movies = ArrayList()

        Action_Movies!!.add("Sandwish")
        Action_Movies!!.add("Kabab")
        Action_Movies!!.add("Paneer")

        Romantic_Movies!!.add("Roti")
        Romantic_Movies!!.add("Dal")
        Romantic_Movies!!.add("Panner")

        Comedy_Movies!!.add("Ice Cream")
        Comedy_Movies!!.add("Gulab Jamun")

        childList = ArrayList()

        childList.add(Action_Movies!!)
        childList.add(Romantic_Movies!!)
        childList.add(Comedy_Movies!!)
    }

    companion object
    {
        lateinit var childList: ArrayList<ArrayList<String>>
    }
}
