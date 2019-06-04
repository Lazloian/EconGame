package com.game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.game.main.Game.STATE;

public class Menu
{
    private Game game;

    public Menu(Game game)
    {
        this.game = game;
    }

    public void tick()
    {
        if (MouseInput.mouseOver((int)(Game.WIDTH / 2) - 100, 400, 200, 64))
        {
            System.exit(1);
        }
        else if (MouseInput.mouseOver((int)(Game.WIDTH / 2) - 100, 300, 200, 64))
        {
            game.gameState = STATE.Help;
        }
        else if (MouseInput.mouseOver((int)(Game.WIDTH / 2) - 100, 200, 200, 64))
        {
            game.gameState = STATE.Firm;
        }
    }

    public void render(Graphics g)
    {
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);

        g.setColor(Color.white);
        g.fillRect((int)(Game.WIDTH / 2) - 100, 200, 200, 64);
        g.fillRect((int)(Game.WIDTH / 2) - 100, 300, 200, 64);
        g.fillRect((int)(Game.WIDTH / 2) - 100, 400, 200, 64);

        g.setFont(fnt);
        g.setColor(Color.black);
        g.drawString("Widgets", (int)(Game.WIDTH / 2) - 100, 100);

        g.setFont(fnt2);
        g.drawString("A  Game  Of  Markets  And  Brute  Force", 275, 150);
        g.drawString("Play", (int)(Game.WIDTH / 2) - 30, 240);
        g.drawString("Help", (int)(Game.WIDTH / 2) - 30, 340);
        g.drawString("Quit", (int)(Game.WIDTH / 2) - 30, 440);
        g.drawString("By Henry Silva", 50, 650);

        g.drawRect((int)(Game.WIDTH / 2) - 100, 200, 200, 64);
        g.drawRect((int)(Game.WIDTH / 2) - 100, 300, 200, 64);
        g.drawRect((int)(Game.WIDTH / 2) - 100, 400, 200, 64);
    }
}