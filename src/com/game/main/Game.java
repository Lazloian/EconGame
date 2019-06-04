package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import com.game.main.MouseInput;

public class Game extends Canvas implements Runnable
{
    private static final long serialVersionUID = 2L;

    public static final float WIDTH = 1100, HEIGHT = 700;
    private Thread thread;
    private boolean running = false;

    private Menu menu;
    private Help help;
    private UI ui;

    public enum STATE
    {
        Menu,
        Help,
        Firm,
        Production,
        Market,
        Data,
        GameOver,
        End;
    }

    public STATE gameState = STATE.Menu;

    public Game()
    {
        menu = new Menu(this);
        help = new Help(this);
        ui = new UI(this);
        this.addMouseListener(new MouseInput());
        new Window((int)WIDTH, (int)HEIGHT, "Now You're Thinking Like an Economist!", this);
    }

    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1)
            {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick()
    {
        if (gameState == STATE.Menu)
        {
            menu.tick();
        }
        else if (gameState == STATE.Help)
        {
            help.tick();
        }
        else if (gameState == STATE.Firm || gameState == STATE.Production || gameState == STATE.Market || gameState == STATE.Data || gameState == STATE.GameOver)
        {
            ui.tick();
        }
    }

    private void render()
    {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null)
        {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        Color backgroundColor = new Color(238, 239, 189);

        g.setColor(backgroundColor);
        g.fillRect(0, 0, (int)WIDTH, (int)HEIGHT);

        if (gameState == STATE.Menu)
        {
            menu.render(g);
        }
        else if (gameState == STATE.Help)
        {
            help.render(g);
        }
        else if (gameState == STATE.Firm || gameState == STATE.Production || gameState == STATE.Market || gameState == STATE.Data || gameState == STATE.GameOver)
        { 
            ui.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args)
    {
        System.setProperty("sun.java2d.opengl", "True");
        new Game();
    }
}