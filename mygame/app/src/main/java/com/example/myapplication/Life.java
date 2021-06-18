package com.example.myapplication;

public class Life
{
    private float posX=550;
    private float posY=880;
    public int lifecnt=3;

    public void Update()
    {

    }

    public void Draw()
    {
        for(int lc=0;lc<lifecnt;lc++)
        {
            App.Get().ImageMgr().Draw("heart.png", posX, posY-lc*70);
        }
    }
}