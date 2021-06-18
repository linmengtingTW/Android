package com.example.myapplication;

public class Clear
{
    private float posX=300;
    private float posY=500;
    private int cnt=20;
    boolean cntFlg=true;

    public void Update()
    {
        if(cntFlg)
        {
            cnt--;
            if(cnt==0)
            {
                cnt=20;
                cntFlg=false;
            }
        }
        if(!cntFlg)
        {
            cnt--;
            if(cnt==0)
            {
                cnt=20;
                cntFlg=true;
            }
        }
    }

    public void Draw()
    {
        App.Get().ImageMgr().Draw("clear.png", posX, posY);
        if(cntFlg)
        {
            App.Get().ImageMgr().Draw("taptoback.png", posX, posY+300);
        }
    }
}
