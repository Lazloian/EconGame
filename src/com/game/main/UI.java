package com.game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.game.main.Game.STATE;

public class UI
{
    private Game game;

    private float money = 100000000;
    private float month = 1;
    private float marketState = 0;
    private float danger = 0; // 0 - 12
    private float blackmailPrice = 5000;
    private float totalCost = 0;
    private float variableCost = 0;
    private float revenue = 0;
    private float profit = 0;
    private boolean dataCheck = false;
    private float priceDemand = 25;     //Market Prices
    private float priceFirm = 0;
    private float priceSupply = 1;
    private float firms = 100;
    private float firmrate = 0;
    private float quantitySupply = 1;   //Market Quantities
    private float quantityMarket = 1000;
    private float quantityFirm = 0;
    private float quantitySold = 0;
    private float factories = 1;        //Factories
    private float totalRent = 500;
    private float rent = 500;
    private float factoryBuy = 5000;
    private float factorySell = 2000;
    private float factoryMax = 5;
    private float labor = 0;            //Labor
    private float laborUsed = 0;
    private float laborAvailable = 0;
    private float laborTotal = 0;
    private float wage = 0;
    private float machines = 0;        //Machines
    private float machinesUsed = 0;
    private float machineMax = 20;
    private float machineBuy = 1000;
    private float machineSell = 500;
    private float machineEfficiency = 2;
    private float components = 0;      //Components
    private float componentsUnit = 5;
    private float componentBuy = 50;
    private float componentSell = 25;
    private float resourceMax = 0;
    private float productionMax = 0;
    private float maxOutput = 0;

    public UI(Game game)
    {
        this.game = game;
    }

    public void tick()
    {
        if (game.gameState != STATE.Data)
        {
            if (MouseInput.mouseOver(30, 15, 125, 50))
            {
                game.gameState = STATE.Firm;
            }
            else if (MouseInput.mouseOver(155, 15, 250, 50))
            {
                game.gameState = STATE.Production;
            }
            else if (MouseInput.mouseOver(405, 15, 175, 50))
            {
                game.gameState = STATE.Market;
            }
            else if (MouseInput.mouseOver(890, 15, 165, 50))
            {
                game.gameState = STATE.Data;
            }

            if (game.gameState == STATE.Firm)
            {
                if (danger > 12)
                {
                    game.gameState = STATE.GameOver;
                }
                if (firms >= 50)
                {
                    priceFirm = quantityMarket / 50;
                }
                else if (firms < 50 && firms >= 25)
                {
                    priceFirm = quantityMarket / 30;
                }
                else if (firms < 25 && firms >= 10)
                {
                    priceFirm = quantityMarket / 20;
                }
                else if (firms < 10 && firms >= 2)
                {
                    priceFirm = quantityMarket / 10;
                }
                else if (firms == 1)
                {
                    priceFirm = quantityMarket / 5;
                }
                if (machines > factories * factoryMax)
                {
                    machinesUsed = factories * factoryMax;
                }
                else
                {
                    machinesUsed = machines;
                }
                
                if (labor >= machinesUsed * machineEfficiency)
                {
                    laborUsed = machinesUsed * machineEfficiency;
                }
                else
                {
                    laborUsed = labor;
                }
                productionMax = (laborUsed / machineEfficiency) * machineMax + (laborUsed % machineEfficiency) * (machineMax / 2);
        
                if (productionMax > components / componentsUnit)
                {
                    maxOutput = components / componentsUnit;
                }
                else
                {
                    maxOutput = productionMax;
                }

                if (quantitySupply > maxOutput)
                {
                    quantitySupply = maxOutput;
                }

                if (MouseInput.mouseOver(325, 100, 75, 30))
                {
                    if (quantitySupply < maxOutput)
                    {
                        quantitySupply += 1;
                    }
                }
                else if (MouseInput.mouseOver(325, 145, 75, 30))
                {
                    if (quantitySupply > 0)
                    {
                        quantitySupply -= 1;
                    }
                }
                else if (MouseInput.mouseOver(675, 100, 75, 30))
                {
                    priceSupply += 1;
                }
                else if (MouseInput.mouseOver(675, 145, 75, 30))
                {
                    if (priceSupply > 0)
                    {
                        priceSupply -= 1;
                    }
                }
            }

            if (game.gameState == STATE.Production)
            {
                if (MouseInput.mouseOver(410, 100, 75, 30)) //Factories
                {
                    if (money >= factoryBuy)
                    {
                        money -= factoryBuy;
                        totalRent += rent;
                        factories += 1;
                    }
                }
                else if (MouseInput.mouseOver(410, 145, 75, 30))
                {
                    if (factories > 0)
                    {
                        money += factorySell;
                        totalRent -= rent;
                        factories -= 1;
                    }
                }
                else if (MouseInput.mouseOver(410, 200, 75, 30)) //Labor
                {
                    if (laborAvailable > 0)
                    {
                        labor += 1;
                        laborAvailable -= 1;
                    }
                }
                else if (MouseInput.mouseOver(410, 245, 75, 30))
                {
                    if (labor > 0)
                    {
                        labor -= 1;
                        laborAvailable += 1;
                    }
                }
                else if (MouseInput.mouseOver(955, 200, 75, 30)) //Wage
                {
                    wage += 1;
                    laborTotal = (wage * wage) / 8;
                    laborAvailable = laborTotal - labor;
                }
                else if (MouseInput.mouseOver(955, 245, 75, 30))
                {
                    if (wage > 0)
                    {
                        wage -= 1;
                    }
                    laborTotal = (wage * wage) / 8;
                    laborAvailable = laborTotal - labor;

                    if (laborAvailable < 0)
                    {
                        labor -= 1;
                        laborAvailable = 0;
                    }
                }
                else if (MouseInput.mouseOver(410, 300, 75, 30)) //Machines
                {
                    if (money > machineBuy)
                    {
                        money -= machineBuy;
                        machines += 1;
                    }
                }
                else if (MouseInput.mouseOver(410, 345, 75, 30))
                {
                    if (machines > 0)
                    {
                        money += machineSell;
                        machines -= 1;
                    }
                }
                else if (MouseInput.mouseOver(410, 400, 75, 30)) //Components
                {
                    if (money > componentBuy * componentsUnit)
                    {
                        money -= componentBuy * componentsUnit;
                        components += componentsUnit;
                    }
                }
                else if (MouseInput.mouseOver(410, 445, 75, 30))
                {
                    if (components > componentsUnit)
                    {
                        money += componentSell * componentsUnit;
                        components -= componentsUnit;
                    }
                }
                else if (MouseInput.mouseOver(290, 500, 280, 50))
                {
                    if (money >= 5000)
                    {
                        machineMax += 2;
                        money -= 5000;
                    }
                }
                else if (MouseInput.mouseOver(290, 575, 280, 50))
                {
                    if (money >= 10000 && componentsUnit > 0)
                    {
                        componentsUnit -= 1;
                        money -= 10000;
                    }
                }
                else if (MouseInput.mouseOver(630, 500, 280, 50))
                {
                    if (money >= 25000 && machineEfficiency > 0)
                    {
                        machineEfficiency -= 1;
                        money -= 25000;
                    }
                }
                else if (MouseInput.mouseOver(630, 575, 280, 50))
                {
                    if (money >= 15000)
                    {
                        factoryMax += 1;
                        money -= 15000;
                    }
                }
            }

            else if (game.gameState == STATE.Market)
            {
                if (MouseInput.mouseOver(100, 200, 250, 75))
                {
                    if (money >= 7500)
                    {
                        money -= 7500;
                        quantityMarket += 50;
                    }
                }
                else if (MouseInput.mouseOver(400, 200, 250, 75))
                {
                    if (money >= 15000)
                    {
                        money -= 15000;
                        quantityMarket += 200;
                    }
                }
                else if (MouseInput.mouseOver(700, 200, 250, 75))
                {
                    if (money >= 1000000)
                    {
                        money = 0;
                        danger += 64;
                    }
                }
                else if (MouseInput.mouseOver(100, 350, 250, 75))
                {
                    if (money >= blackmailPrice)
                    {
                        money -= blackmailPrice;
                        firms -= 1;
                        blackmailPrice *= 2;
                        danger += 2;
                    }
                }
                else if (MouseInput.mouseOver(400, 350, 250, 75))
                {
                    if (money >= 20000)
                    {
                        money -= 20000;
                        firmrate -= 5;
                        danger += 4;
                    }
                }
                else if (MouseInput.mouseOver(700, 350, 250, 75))
                {
                    if (money >= 50000)
                    {
                        money -= 50000;
                        firmrate -= 15;
                        danger += 6;
                    }
                }
                else if (MouseInput.mouseOver(750, 500, 250, 75))
                {
                    if (money >= 7500)
                    {
                        money -= 7500;
                        danger -= 1;
                    }
                }
            }
        }
        else if (game.gameState == STATE.Data)
        {
            if (MouseInput.mouseOver(865, 590, 190, 50))
            {
                month += 1;
                dataCheck = false;
                game.gameState = STATE.Firm;
            }
            if (!dataCheck)
            {
                quantityFirm = quantityMarket / firms;
                if (firms >= 50)
                {
                    priceFirm = quantityMarket / 50;

                    if (priceSupply > quantityMarket / 50)
                    {
                        quantitySold = 0;
                    }
                    else if (priceSupply <= quantityMarket / 50)
                    {
                        quantitySold = quantityFirm;
                    }
                }
                else if (firms < 50 && firms >= 25)
                {
                    priceFirm = quantityMarket / 30;

                    if (priceSupply > priceFirm)
                    {
                        quantitySold = quantitySold * (priceFirm / (priceSupply + (priceSupply / 3)));
                    }
                    else if (priceSupply == priceFirm)
                    {
                        quantitySold = quantityFirm;
                    }
                    else if (priceSupply < priceFirm)
                    {
                        quantitySold = quantityFirm + (quantityFirm / 4) / (1 / (priceFirm - priceSupply));
                    }
                }
                else if (firms < 25 && firms >= 10)
                {
                    priceFirm = quantityMarket / 20;

                    if (priceSupply > priceFirm)
                    {
                        quantitySold = quantitySold * (priceFirm / (priceSupply + (priceSupply / 2)));
                    }
                    else if (priceSupply == priceFirm)
                    {
                        quantitySold = quantityFirm;
                    }
                    else if (priceSupply < priceFirm)
                    {
                        quantitySold = quantityFirm + (quantityFirm / 3) / (1 / (priceFirm - priceSupply));
                    }
                }
                else if (firms < 10 && firms >= 2)
                {
                    priceFirm = quantityMarket / 10;

                    if (priceSupply > priceFirm)
                    {
                        quantitySold = quantitySold * (priceFirm / priceSupply);
                    }
                    else if (priceSupply == priceFirm)
                    {
                        quantitySold = quantityFirm;
                    }
                    else if (priceSupply < priceFirm)
                    {
                        quantitySold = quantityFirm + (quantityFirm / 2) / (1 / (priceFirm - priceSupply));
                    }
                }
                else if (firms == 1)
                {
                    priceFirm = quantityMarket / 5;

                    if (priceSupply > priceFirm)
                    {
                        quantitySold = quantityFirm;
                    }
                    else if (priceSupply == priceFirm)
                    {
                        quantitySold = quantityFirm;
                    }
                    else if (priceSupply < priceFirm)
                    {
                        quantitySold = quantityFirm + (quantityFirm / 4) / (1 / (priceFirm - priceSupply));
                    }
                }
                if (quantitySold > quantitySupply)
                {
                    quantitySold = quantitySupply;
                }
                revenue = quantitySold * priceSupply;
                totalCost = totalRent + (wage * labor);
                variableCost = wage * labor;
                System.out.println(revenue);
                profit = revenue - totalCost;
                System.out.println(profit);

                money += profit;
                components -= quantitySupply * componentsUnit;
                firms += firmrate;
                dataCheck = true;
                if (firms <= 0)
                {
                    firms = 1;
                }
            }
        }
        if (game.gameState == STATE.GameOver)
        {
            if (MouseInput.mouseOver(450, 450, 100, 75))
            {
                System.exit(1);
            }
        }
    }

    public void render(Graphics g)
    {
        Font fnt = new Font("arial", 1, 35);
        Font fnt2 = new Font("arial", 1, 20);
        Font fnt3 = new Font("arial", 1, 25);
        Color lightGray = new Color(210, 210, 210);
        Color darkGreen = new Color(0, 200, 0);

        g.setFont(fnt);

        g.setColor(Color.white);
        g.fillRect(30, 65, 1025, 575);

        if (game.gameState != STATE.Data && game.gameState != STATE.GameOver)
        {
            if (game.gameState == STATE.Firm)
            {
                g.fillRect(30, 15, 125, 50);
                g.setColor(lightGray);
                g.fillRect(155, 15, 250, 50);
                g.fillRect(405, 15, 175, 50);
            }
            else if (game.gameState == STATE.Production)
            {
                g.fillRect(155, 15, 250, 50);
                g.setColor(lightGray);
                g.fillRect(30, 15, 125, 50);
                g.fillRect(405, 15, 175, 50);
            }
            else if (game.gameState == STATE.Market)
            {
                g.fillRect(405, 15, 175, 50);
                g.setColor(lightGray);
                g.fillRect(30, 15, 125, 50);
                g.fillRect(155, 15, 250, 50);
            }

            g.setColor(Color.black);
            g.drawRect(30, 15, 125, 50);
            g.drawRect(155, 15, 250, 50);
            g.drawRect(405, 15, 175, 50);
            g.drawRect(30, 65, 1025, 575);

            g.drawString("Firm", 50, 50);
            g.drawString("Production", 190, 50);
            g.drawString("Market", 435, 50);

            if (game.gameState == STATE.Firm)
            {
                g.setFont(fnt);
                g.drawString("Quantity", 50, 150);
                g.drawRect(200, 100, 100, 75);
                g.drawString("Price", 450, 150);
                g.drawRect(550, 100, 100, 75);
                g.setFont(fnt2);
                g.drawRect(325, 100, 75, 30);
                g.drawRect(325, 145, 75, 30);
                g.drawString("+1", 350, 125);
                g.drawString("-1", 350, 170);
                g.drawRect(675, 100, 75, 30);
                g.drawRect(675, 145, 75, 30);
                g.drawString("+$1", 695, 125);
                g.drawString("-$1", 695, 170);
                g.setFont(fnt);
                g.drawString(Integer.toString((int)quantitySupply), 245, 150);
                g.drawString("$" + ((int)priceSupply), 580, 150);

                g.setFont(fnt2);
                g.drawString("Max Output:  " + ((int)maxOutput), 45, 225);
                g.drawString("Factories:  " + ((int)factories), 70, 275);
                g.drawString("Labor:  " + ((int)labor), 105, 325);
                g.drawString("Machines:  " + ((int)machines), 70, 375);
                g.drawString("Components:  " + ((int)components), 40, 425);

                g.drawString("Max Revenue: $" + ((int)quantitySupply * (int)priceSupply), 450, 225);
                g.drawString("Total Cost: $" + (((int)labor * (int)wage) + (int)totalRent), 450, 275);
                g.drawString("Labor Cost: $" + ((int)labor * (int)wage), 450, 325);
                g.drawString("Rent: $" + ((int)totalRent), 450, 375);
                g.drawString("Components Needed: " + ((int)quantitySupply * (int)componentsUnit), 450, 425);

                g.drawString("Market Price: $ " + (int)priceFirm, 450, 475);
            }

            if (game.gameState == STATE.Production)
            {
                g.setFont(fnt);
                g.drawString("Factories", 110, 150);
                g.drawString("Labor", 160, 250);
                g.drawString("Machines", 100, 350);
                g.drawString("Components", 50, 450);
                g.drawString("Research", 100, 550);

                g.drawRect(290, 100, 100, 75);
                g.drawRect(290, 200, 100, 75);
                g.drawRect(290, 300, 100, 75);
                g.drawRect(290, 400, 100, 75);

                g.setFont(fnt2);
                g.drawRect(410, 100, 75, 30); //Factories
                g.drawRect(410, 145, 75, 30);
                g.drawString("Buy 1", 420, 122);
                g.drawString("Sell 1", 420, 168);
                g.drawString("Buy For: ", 515, 122);
                g.drawString("Sell For: ", 515, 168);
                g.drawString("$" + ((int)factoryBuy), 605, 122);
                g.drawString("$" + ((int)factorySell), 605, 167);
                g.drawString("Rent: $" + ((int)rent), 775, 122);
                g.drawString("Total Rent: $" + ((int)totalRent), 720, 168);
                g.setFont(fnt);
                g.drawString(Integer.toString((int)factories), 330, 150);

                g.setFont(fnt2);
                g.drawRect(410, 200, 75, 30); //Labor
                g.drawRect(410, 245, 75, 30);
                g.drawString("Hire 1", 420, 222);
                g.drawString("Fire 1", 420, 268);
                g.drawString("Hired: ", 535, 222);
                g.drawString("Available: ", 500, 268);
                g.drawString(Integer.toString((int)labor), 605, 222);
                g.drawString(Integer.toString((int)laborAvailable), 605, 267);
                g.setFont(fnt);
                g.drawString("Wage", 715, 250);
                g.drawRect(835, 200, 100, 75);
                g.setFont(fnt2);
                g.drawRect(955, 200, 75, 30);
                g.drawRect(955, 245, 75, 30);
                g.drawString("+$1", 975, 222);
                g.drawString("-$1", 975, 267);
                g.setFont(fnt);
                g.drawString(Integer.toString((int)labor), 330, 250);
                g.drawString("$" + ((int)wage), 860, 250);

                g.setFont(fnt2);
                g.drawRect(410, 300, 75, 30); //Machines
                g.drawRect(410, 345, 75, 30);
                g.drawString("Buy 1", 420, 322);
                g.drawString("Sell 1", 420, 368);
                g.drawString("Buy For: ", 515, 322);
                g.drawString("Sell For: ", 515, 368);
                g.drawString("$" + ((int)machineBuy), 605, 322);
                g.drawString("$" + ((int)machineSell), 605, 367);
                g.drawString("Max Output Per Machine: " + (int)machineMax, 700, 322);
                g.drawString("Workers Needed Per Machine: " + (int)machineEfficiency, 700, 367);
                g.setFont(fnt);
                g.drawString(Integer.toString((int)machines), 330, 350);

                g.setFont(fnt2);
                g.drawRect(410, 400, 75, 30); //Components
                g.drawRect(410, 445, 75, 30);
                g.drawString("Buy " + ((int)componentsUnit), 420, 422);
                g.drawString("Sell " + ((int)componentsUnit), 420, 468);
                g.drawString("Buy For: ", 515, 422);
                g.drawString("Sell For: ", 515, 468);
                g.drawString("$" + ((int)componentBuy), 605, 422);
                g.drawString("$" + ((int)componentSell), 605, 467);
                g.setFont(fnt);
                g.drawString(Integer.toString((int)components), 330, 450);

                g.setFont(fnt2);
                g.drawRect(290, 500, 280, 50);
                g.drawString("+2 Machine Max Output", 315, 520);
                g.drawString("$5000", 410, 540);
                g.drawRect(290, 575, 280, 50);
                g.drawString("-1 Component Per Unit", 315, 595);
                g.drawString("$10000", 410, 620);
                g.drawRect(630, 500, 280, 50);
                g.drawString("-1 Worker Per Machine", 660, 520);
                g.drawString("$25000", 740, 540);
                g.drawRect(630, 575, 280, 50);
                g.drawString("+1 Machine Per Factory", 660, 595);
                g.drawString("$15000", 740, 615);
            }
            else if (game.gameState == STATE.Market)
            {
                g.setFont(fnt2);
                g.drawString("Firms In Market: " + (int)firms, 50, 100);
                if (firms > 50)
                {
                    g.drawString("Market State: Perfectly Competitive", 300, 100);
                }
                else if (firms > 1)
                {
                    g.drawString("Market State: Oligopoly", 300, 100);
                }
                else
                {
                    g.drawString("Market State: Monopoly", 300, 100);
                }

                g.setFont(fnt);
                g.drawString("Market Influencing", 100, 175);
                g.setFont(fnt2);
                g.drawRect(100, 200, 250, 75);
                g.drawString("Increase Advertising", 125, 225);
                g.drawString("$7500", 200, 250);
                g.drawRect(400, 200, 250, 75);
                g.drawString("Increase Product Quality", 410, 225);
                g.drawString("$15000", 500, 250);
                g.drawRect(700, 200, 250, 75);
                g.drawString("Donate to Charity", 750, 225);
                g.drawString("$1000000", 775, 250);
                g.setFont(fnt);
                g.drawString("Illegal Activity", 100, 325);
                g.setFont(fnt2);
                g.drawRect(100, 350, 250, 75);
                g.drawString("Blackmail Firm", 150, 375);
                g.drawString("$" + (int)blackmailPrice, 180, 400);
                g.drawRect(400, 350, 250, 75);
                g.drawString("Increase Startup Costs", 425, 375);
                g.drawString("$20000", 490, 400);
                g.drawRect(700, 350, 250, 75);
                g.drawString("Pay The Mafia", 750, 375);
                g.drawString("$50000", 790, 400);
                g.setFont(fnt);
                g.drawString("Danger Meter", 100, 475);
                g.fillRect(100, 500, 10, 50);
                g.fillRect(710, 500, 10, 50);
                if (danger <= 4)
                {
                    g.setColor(Color.green);
                }
                else if (danger <= 8)
                {
                    g.setColor(Color.yellow);
                }
                else
                {
                    g.setColor(Color.red);
                }
                g.fillRect(110, 500, ((int)(danger) * 50), 50);
                g.setColor(Color.black);
                g.drawRect(110, 500, 600, 49);

                g.setFont(fnt2);
                g.drawRect(750, 500, 250, 75);
                g.drawString("Bribe Government", 790, 525);
                g.drawString("$7500", 850, 550);
            }
            g.setFont(fnt3);
            g.drawString("Money: $" + ((int)money), 600, 50);
            g.setColor(Color.white);
            g.fillRect(890, 15, 165, 50);
            g.setColor(Color.black);
            g.drawRect(890, 15, 165, 50);
            g.setFont(fnt);
            g.setColor(darkGreen);
            g.drawString("Continue", 900, 50);
        }

        else if (game.gameState == STATE.Data)
        {
            g.setColor(Color.black);
            g.drawRect(30, 65, 1025, 575);

            g.setFont(fnt);
            g.drawString("Month " + (int)month + " Results", 75, 100);
            g.setFont(fnt3);
            g.drawString("Units Sold: " + (int)quantitySold, 75, 150);
            g.drawString("Price: $ " + (int)priceSupply, 300, 150);
            g.drawString("Total Revenue: $ " + (int)revenue, 75, 200);
            g.drawString("Total Cost: $ " + (int)totalCost, 75, 250);
            g.drawString("Total Profit: $ " + (int)profit, 75, 300);
            g.drawString("Money: $ " + (int)money, 75, 350);
            if (quantitySupply != 0)
            {
                g.drawString("Average Total Cost: $ " + (totalCost / quantitySupply), 650, 150);
                g.drawString("Average Variable Cost: $ " + (variableCost / quantitySupply), 650, 200);
            }
            else
            {
                g.drawString("Average Total Cost: N/A", 650, 150);
                g.drawString("Average Variable Cost: N/A", 650, 200);
            }
            g.drawString("Labor Expenses: $ " + (int)(wage * labor), 650, 250);
            g.drawString("Total Rent: $ " + (int)totalRent, 650, 300);
            g.drawString("Components Used: " + (int)(componentsUnit * quantitySupply), 650, 350);

            g.drawRect(865, 590, 190, 50);
            g.setFont(fnt);
            g.setColor(darkGreen);
            g.drawString("Continue", 890, 625);
        }

        else if (game.gameState == STATE.GameOver)
        {
            g.setColor(Color.black);
            g.drawRect(30, 65, 1025, 575);
            g.setFont(fnt);
            g.drawString("Game Over", 400, 150);
            g.setFont(fnt3);
            g.drawString("You have been arrested for fraudulent business practices", 200, 250);
            g.drawString("Score: 0", 450, 400);
            g.drawRect(450, 450, 100, 75);
            g.drawString("Ok", 485, 495);
        }
    }
}