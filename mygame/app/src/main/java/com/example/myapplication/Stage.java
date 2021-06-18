package com.example.myapplication;

public class Stage
{
    private float posX=300;
    public float posY=-1600;
    public Player splayer;
    boolean MoveFlg=false;

    public void Update()
    {
        if(posY<=2700)
        {
            if(splayer.MoveCase!=0)
            {
                posY+=5;
            }
        }
        if(posY>=2200)
        {
            MoveFlg=true;
        }
    }

    public void Draw()
    {
        App.Get().ImageMgr().Draw("stage.png", posX, posY);
    }
}
