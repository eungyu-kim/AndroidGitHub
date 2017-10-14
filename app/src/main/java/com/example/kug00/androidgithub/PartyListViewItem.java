package com.example.kug00.androidgithub;

import android.graphics.drawable.Drawable;

/**
 * Created by kug00 on 2017-10-12.
 */

public class PartyListViewItem {
    private String iconDrawable ;
    private String titleStr ;
    private String addrStr ;

    public void setIcon(String icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String addr) {
        addrStr = addr ;
    }

    public String getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.addrStr ;
    }
}
