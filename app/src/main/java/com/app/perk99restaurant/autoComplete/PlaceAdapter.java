package com.app.perk99restaurant.autoComplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.app.perk99restaurant.R;

import java.util.ArrayList;

public class PlaceAdapter extends BaseAdapter {

    private ArrayList<PlaceAPIModel> resultList;
    private Context current;
    private LayoutInflater inflater;


    public PlaceAdapter(Context con, ArrayList<PlaceAPIModel> resultList) {
        this.current = con;
        inflater = (LayoutInflater) current.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resultList = resultList;
    }

    public void notifyList(ArrayList<PlaceAPIModel> mList){
        this.resultList=mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public PlaceAPIModel getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_places, parent, false);
            holder = new ViewHolder();
            holder.addressTextView = (TextView) convertView.findViewById(R.id.txt_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.addressTextView.setText(resultList.get(position).getDesc());
        return convertView;
    }

    private class ViewHolder {
        private TextView addressTextView;
    }

}