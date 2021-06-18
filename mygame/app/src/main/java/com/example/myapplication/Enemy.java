package com.example.myapplication;

import android.graphics.Rect;

public class Enemy
{
    private float posX=700;
    private float posY=100;
    public Stage estage;
    public Player eplayer;
    private float par=0;
    private float MovSpeedX;
    private float MovSpeedY;

    // -100~700
    public void SetPos(float px, float py)
    {
        posX=px;
        posY=py;
    }

    Rect rect = new Rect();

    protected enum MovState
    {
        balloon,
        plane,
        ufo
    };
    MovState movState= MovState.balloon;

    public void Update()
    {
        if(estage.posY>=-1550&&movState==MovState.balloon)
        {
            MovSpeedX=-2;
            MovSpeedY=5;
            posX += MovSpeedX;
            posY += MovSpeedY;
            if(posX<-32)
            {
                posX=632;
                if(estage.posY>200)
                {
                    movState=MovState.plane;
                }
            }
            if(posY>1032)
            {
                posY=-32;
            }
        }

        if(estage.posY>200&&movState==MovState.plane)
        {
            MovSpeedX=-10;
            MovSpeedY=5;
            posX += MovSpeedX;
            posY += MovSpeedY;
            if(posX<-32)
            {
                posX=632;
                if(estage.posY>1400)
                {
                    movState=MovState.ufo;
                }
            }
            if(posY>1032)
            {
                posY=-32;
            }
        }
        if(estage.posY>1400&&movState==MovState.ufo)
        {
            posX+=Math.sin((double)par)*5-10;
            posY+=Math.cos((double)par)*5+5;
            par-=0.1f;
            if(posX<-32)
            {
                posX=632;
            }
            if(posY>1032)
            {
                posY=-32;
            }
        }

        if(estage.posY>=2200)
        {
            posX=-100;
            posY=-100;
        }

        rect.left = (int)posX - 32;
        rect.top = (int)posY - 32;
        rect.right = (int)posX + 32;
        rect.bottom = (int)posY + 32;

    }

    public boolean CheckRect(Rect r)
    {
        if(rect.intersect(r))
        {
            return true;
        }
        return false;
    }

    public void Draw()
    {
        if(movState==MovState.balloon)
        {
            App.Get().ImageMgr().Draw("enemy001.png", posX, posY);
        }
        if(movState==MovState.plane)
        {
            App.Get().ImageMgr().Draw("enemy002.png",posX,posY);
        }
        if(movState==MovState.ufo)
        {
            App.Get().ImageMgr().Draw("enemy003.png",posX,posY);
        }
    }
}
