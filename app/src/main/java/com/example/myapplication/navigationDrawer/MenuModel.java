package com.example.myapplication.navigationDrawer;

public class MenuModel {

    public String menuName;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren) {

        this.menuName = menuName;

        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
