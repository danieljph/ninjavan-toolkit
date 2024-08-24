package com.karyasarma.toolkit.doku.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Joi Partogi Hutapea
 */
@SuppressWarnings("unused")
public class SimpleMenu
{
    private String name;
    private MenuShortcut menuShortcut;
    private List<SimpleMenu> children;

    public SimpleMenu()
    {
    }

    public SimpleMenu(String name)
    {
        this.name = name;
    }

    public SimpleMenu(String name, MenuShortcut menuShortcut)
    {
        this.name = name;
        this.menuShortcut = menuShortcut;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public MenuShortcut getMenuShortcut()
    {
        return menuShortcut;
    }

    public void setMenuShortcut(MenuShortcut menuShortcut)
    {
        this.menuShortcut = menuShortcut;
    }

    public List<SimpleMenu> getChildren()
    {
        return children;
    }

    public void addChild(SimpleMenu simpleMenuChild)
    {
        if(children==null)
        {
            children = new ArrayList<>();
        }

        children.add(simpleMenuChild);
    }
}
