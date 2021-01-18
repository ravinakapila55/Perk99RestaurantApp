
package com.app.perk99restaurant.home.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail
{

    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("qty")
    @Expose
    private Integer qty;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

}
