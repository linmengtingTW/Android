package com.example.myapplication;

public class GasPower
{
    private float posX=50;
    private float posY=880;
    public Player powcnt;
    public Stage powloss;
    int gascnt=1000;
    int flashcnt=10;
    boolean flashFlg=false;

    /*public void SetPos(float px, float py)
    {
        posX=px;
        posY=py;
    }*/

    public void Update()
    {
        if(powcnt.GoFlg)
        {
            gascnt-=5;
            //----------------
            if(gascnt<300&&gascnt!=0)
            {
                if(!flashFlg)
                {
                    flashcnt--;
                    if(flashcnt==0)
                    {
                        powcnt.powcnt-=1;
                        flashcnt=10;
                        flashFlg=true;
                    }
                }
                if(flashFlg)
                {
                    flashcnt--;
                    if(flashcnt==0)
                    {
                        powcnt.powcnt+=1;
                        flashcnt=10;
                        flashFlg=false;
                    }
                }
            }
            //----------------
            if(gascnt==0)
            {
                powcnt.powcnt-=1;
                gascnt=1000;
            }
        }

    }

    public void Draw()
    {
        for(int gc=0;gc<powcnt.powcnt; gc++)
        {
            if (powcnt.GoFlg)
            {
                App.Get().ImageMgr().Draw("gas.png", posX, posY-gc*70);
            }
        }
    }
}
