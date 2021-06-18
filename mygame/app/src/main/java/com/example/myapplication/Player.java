package com.example.myapplication;

import android.graphics.Rect;

public class Player
{
    private float posX=300;
    public float posY=1000;
    private float powpx=300;
    private float powpy=1050;
    private float srlpx=300;
    private float srlpy=1050;
    boolean srlcase=true;   // 右：true　左：false
    private boolean pick=false;
    private boolean fireFlg=false;
    int powcnt=0;
    int MoveCase=0;
    public Stage pstage;
    public Life plife;
    boolean GoFlg=false;
    boolean InvFlg=false;
    boolean ClrFlg=false;
    boolean ClrTouchFlg=false;
    boolean GoalFlg=false;
    int Invcnt=50;

    Rect rect = new Rect();

    public void TouchCtrl()
    {
        Pointer p=App.Get().TouchMgr().GetTouch();
        if(p==null)
        {
            pick=false;
            return;
        }
        if(p.OnDown())
        {
            GoFlg=true;
            pick=true;
        }
        if(pick)
        {
            if(p.GetNowPos().x>posX)
            {
                posX+=10;
            }
            if(p.GetNowPos().x<posX)
            {
                posX-=10;
            }
            if(MoveCase==1)
            {
                if(p.GetNowPos().y>posY)
                {
                    posY+=10;
                }
                if(p.GetNowPos().y<posY)
                {
                    posY-=10;
                }
            }
        }

        if(ClrFlg)
        {
            if(p.OnDown())
            {
                ClrTouchFlg=true;
            }
        }
    }

    public void Update()
    {
        rect.left = (int)posX - 16;
        rect.top = (int)posY - 32;
        rect.right = (int)posX + 16;
        rect.bottom = (int)posY + 32;

        if(GoFlg)
        {
            switch (MoveCase)
            {
                case 0:
                    if(posY>=500)
                    {
                        posY-=5;
                    }
                    if(posY==500)
                    {
                        MoveCase=1;
                    }
                    break;
                case 1:
                    if(pstage.MoveFlg)
                    {
                        MoveCase=2;
                    }
                    break;
                case 2:
                    if(!GoalFlg)
                    {
                        if(posY<500)
                        {
                            posY+=5;
                        }
                        if(posY>500)
                        {
                            posY-=5;
                        }
                        if(posY==500)
                        {
                            GoalFlg = true;
                        }
                    }
                    if(GoalFlg)
                    {
                        if (posY>=200)
                        {
                            posY-=5;
                            if(posY==200)
                            {
                                ClrFlg=true;
                            }
                        }
                    }
                    break;
            }

            fireFlg=true;
        }

        if(!GoFlg)
        {
            if(srlcase)
            {
                srlpx+=25;
                if(srlpx==550)
                {
                    srlcase=false;
                }
            }
            if(!srlcase)
            {
                srlpx-=25;
                if(srlpx==50)
                {
                    srlcase=true;
                }
            }
        }

        // 50~550
        if(!GoFlg)
        {
            if ((150>srlpx&&srlpx>=50)||(srlpx<=550&&srlpx>450))
            {
                powcnt=1;
            }
            if ((250>srlpx&&srlpx>=150)||(srlpx<=450&&srlpx>350))
            {
                powcnt=2;
            }
            if (250<srlpx&&srlpx<350)
            {
                powcnt=3;
            }
        }

        Enemy[] es = App.Get().GetEnemies();
        for(int eCnt = 0; eCnt < es.length; eCnt++)
        {
            if(es[eCnt].CheckRect(rect)&&!InvFlg)
            {
                plife.lifecnt-=1;
                InvFlg=true;
            }
        }

        if(InvFlg)
        {
            Invcnt--;
            if(Invcnt==0)
            {
                InvFlg=false;
                Invcnt=50;
            }
        }
    }

    String[] useImage={"fire001.png","fire002.png","fire001.png","fire003.png"};
    int useIdx=0;
    int wait=0;

    public void Draw()
    {

        if(wait++>=10)
        {
            useIdx++;
            useIdx=useIdx%useImage.length;
            wait=0;
        }

        if(!InvFlg)
        {
            App.Get().ImageMgr().Draw("player.png", posX, posY);
        }
        if(InvFlg)
        {
            App.Get().ImageMgr().SetAlpha(100);
            App.Get().ImageMgr().Draw("player.png", posX, posY);
            App.Get().ImageMgr().SetAlpha(255);
        }
        if(fireFlg)
        {
            App.Get().ImageMgr().Draw(useImage[useIdx],posX,posY+50);
        }

        if(!GoFlg)
        {
            App.Get().ImageMgr().SetAlpha(170);
            App.Get().ImageMgr().Draw("powerbar.png", powpx, powpy);
            App.Get().ImageMgr().SetAlpha(255);
            App.Get().ImageMgr().Draw("scrollbar.png", srlpx, srlpy);
        }
    }
}
