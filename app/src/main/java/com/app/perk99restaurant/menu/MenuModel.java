package com.app.perk99restaurant.menu;

import com.app.perk99restaurant.home.models.MenuItemsModel;
import com.app.perk99restaurant.home.models.MneuItems;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuModel implements Serializable {

    @SerializedName("category")
    @Expose
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    @SerializedName("menus")
    @Expose
    private List<MneuItems> menus = null;

    public List<MneuItems> getMenus() {
        return menus;
    }

    public void setMenus(List<MneuItems> menus) {
        this.menus = menus;
    }
}
