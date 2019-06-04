package com.game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.game.main.Game.STATE;

public class Help
{
    private Game game;

    public Help(Game game)
    {
        this.game = game;
    }

    public void tick()
    {
        if (MouseInput.mouseOver((int)(Game.WIDTH / 2) - 90, 460, 200, 64))
        {
            game.gameState = STATE.Menu;
        }
    }

    public void render(Graphics g)
    {
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);

        g.setFont(fnt);
        g.setColor(Color.black);
        g.drawString("Help", (int)(Game.WIDTH / 2) - 50, 100);
        g.setFont(fnt2);
        g.drawString("This is a game about economics.", 150, 200);
        g.drawString("Back", (int)(Game.WIDTH / 2) - 25, 500);

        g.drawRect((int)(Game.WIDTH / 2) - 90, 460, 200, 64);
    }
}