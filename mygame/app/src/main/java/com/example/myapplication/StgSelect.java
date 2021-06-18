package com.example.myapplication;

import android.graphics.Rect;

public class StgSelect
{
    private float posX=300;
    private float posY=550;
    private float posXstg1=300;
    private float posYstg1=200;
    private float posXstg2=300;
    private float posYstg2=400;
    public int stgslectFLg=0;

    Rect rect = new Rect();

    public void TouchCtrl()
    {
        Pointer s=App.Get().TouchMgr().GetTouch();
        if(s==null){ return; }
        if(s.OnDown())
        {
            if(posXstg1-135<s.GetDownPos().x&&s.GetDownPos().x<posXstg1+135&&
               posYstg1-30<s.GetDownPos().y&&s.GetDownPos().y<posXstg1+30)
            {
                stgslectFLg=1;
            }
        }
    }

    public void Update()
    {

    }

    public void Draw()
    {
        App.Get().ImageMgr().Draw("title.png", posX, posY);
        App.Get().ImageMgr().Draw("select1.png", posXstg1, posYstg1);
        App.Get().ImageMgr().Draw("select2.png", posXstg2, posYstg2);
    }
}
