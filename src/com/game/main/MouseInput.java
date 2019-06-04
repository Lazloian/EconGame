package com.game.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
    public static boolean mouseClicked = false;

    public static int mouseX = 0;
    public static int mouseY = 0;

    public MouseInput()
    {

    }

    public void mousePressed(MouseEvent e)
    {

    }
    public void mouseClicked(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseClicked = true;
        System.out.println("(" + mouseX + ", " + mouseY + ")");
    }

    public void mouseReleased(MouseEvent e)
    {

    }

    public static boolean mouseOver(int x, int y, int width, int height)
    {
        if (mouseX > x && mouseX < x + width && mouseClicked)
        {
            if (mouseY > y && mouseY < y + height && mouseClicked)
            {
                mouseClicked = false;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}