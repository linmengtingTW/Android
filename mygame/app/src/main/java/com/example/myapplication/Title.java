package com.example.myapplication;

public class Title
{
    private float posXT=300;
    private float posYT=900;
    private float posX=300;
    private float posY=550;
    private int cnt=20;
    boolean SceneFlg=false;
    boolean cntFlg=true;

    public void TouchCtrl()
    {
        Pointer t=App.Get().TouchMgr().GetTouch();
        if(t==null){ return; }
        if(t.OnDown())
        {
            SceneFlg=true;
        }
    }

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
        App.Get().ImageMgr().Draw("title.png", posX, posY);
        if(cntFlg)
        {
            App.Get().ImageMgr().Draw("taptostart.png", posXT, posYT);
        }
    }
}
