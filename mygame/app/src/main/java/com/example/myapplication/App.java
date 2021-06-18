package com.example.myapplication;


import android.graphics.Matrix;
import android.icu.text.CaseMap;
import android.util.Log;
import android.view.SoundEffectConstants;

import java.util.Random;

/*
* App
* リソース管理などのシステム面全般
* シングルトン
*
*/
public class App
{

    // ゲームの実装------------------------------------------------>
    // 特殊な事をしない限りはこの間を編集するだけのはず
    int se1 = 0;
    int se2 = 0;

    private Title title=null;
    private Player player=null;
    private Stage stage=null;
    private GasPower gaspower=null;
    private Enemy[] enemies=null;
    public Enemy[] GetEnemies() { return enemies; }
    private Life life=null;
    private StgSelect stgselect=null;
    private Clear clear=null;

    protected enum Scene
    {
        Title,
        Select,
        Game,
    };
    Scene scene=Scene.Game;

    //public Enemy[] GetEnemies() { return enemies; }
    // アプリケーションが開始された時
    // 諸々の初期化は終わっているので、ここでロードをかけてもOK
    public void Start()
    {
        /*soundManager.PlayBGM("bgm.mp3");
        se1 = soundManager.LoadSE("se1.mp3");
        se2 = soundManager.LoadSE("se2.mp3");*/
        title=new Title();
        player=new Player();
        stage=new Stage();
        gaspower=new GasPower();
        life=new Life();
        stgselect=new StgSelect();
        clear=new Clear();

        enemies=new Enemy[9];
        for(int ec=0; ec<enemies.length; ec++)
        {
            enemies[ec] = new Enemy();
            //enemies[ec].SetPos(632+ec*128, -100+ec*64);
            enemies[ec].SetPos(GetRand(632,782), -100-ec*64);
            //GetRand(50, 100), 100 * ec
            enemies[ec].estage=stage;
            enemies[ec].eplayer=player;
        }

        stage.splayer=player;
        player.pstage=stage;
        gaspower.powcnt=player;
        gaspower.powloss=stage;
        player.plife=life;

    }

    private Random rand=new Random();
    public int GetRand(int min, int max) {return rand.nextInt(max) + 1;}

    // 毎回呼び出される関数(30fps)
    Vector2 vp = new Vector2();
    Vector2 vm = new Vector2();
    public boolean Update()
    {
        switch(scene)
        {
            case Title:
                title.Update();
                title.TouchCtrl();
                if(title.SceneFlg)
                {
                    scene=Scene.Select;
                }
                break;
            case Select:
                stgselect.Update();
                stgselect.TouchCtrl();
                if(stgselect.stgslectFLg==1)
                {
                    //ゲーム初期化
                    player.ClrFlg=false;
                    scene=Scene.Game;
                }
                break;
            case Game:
                player.Update();
                player.TouchCtrl();
                if(player.ClrFlg)
                {
                    clear.Update();
                    if(player.ClrTouchFlg)
                    {
                        scene=Scene.Select;
                    }
                }
                stage.Update();
                gaspower.Update();
                life.Update();
                for(int ec=0; ec<enemies.length; ec++)
                {
                    enemies[ec].Update();
                }
                break;
        }

        TouchProc();
        return true;
    }

    private void TouchProc()
    {
        touchManager.Update();
    }

    // Androidから再描画命令を受けた時
    // ここ以外での描画は無視されます。
    // 早くシステムに返さないと行けないので、描画以外の余計なことはしない。
    // 30fpsで再描画以来はかけているが、呼び出される頻度は端末によります。
    // ここが安定して動いているとは思わないでください。
    public void Draw()
    {
        switch (scene)
        {
            case Title:
                title.Draw();
                break;
            case Select:
                stgselect.Draw();
                break;
            case Game:
                stage.Draw();
                player.Draw();
                gaspower.Draw();
                life.Draw();
                for(int ec=0; ec<enemies.length; ec++)
                {
                    enemies[ec].Draw();
                }
                if(player.ClrFlg)
                {
                    clear.Draw();
                }
                break;
        }
    }

    // ホームボタンなどを押して裏側へ回った時
    public void Suspend()
    {
        soundManager.StopBGM();
    }

    // 再度アクティブになった時
    public void Resume()
    {
        soundManager.PlayBGM("bgm.mp3");
    }

    // <--------------------------------------------------ゲームの実装

    // アプリケーション大本
    private OriginalView view = null;
    public OriginalView GetView(){return view;}
    public void SetView(OriginalView _ov)
    {
        view = _ov;

        // viewがないと初期化出来ないもののインスタンス化
        touchManager = new TouchManager();


        Start();
    }

    // 解像度対応
    private float sdPar = 0; // システム座標→ディスプレイ座標変換用
    private float dsPar = 0; // ディスプレイ座標→システム座標変換用
    public float SD(){return sdPar;}
    public float DS(){return dsPar;}

    // システム画面サイズと実機画面サイズが出揃った段階で比率を計算
    public void SetSDPar(float _dp, float _sp)
    {
        sdPar = _dp/_sp;
        dsPar = _sp/_dp;
    }

    // リソース管理
    private ImageManager imageManage = new ImageManager();
    public ImageManager ImageMgr(){return imageManage;}
    private SoundManager soundManager = new SoundManager();
    public SoundManager SoundMgr(){return soundManager;}

    // タッチ管理
    private TouchManager touchManager = null;
    public TouchManager TouchMgr(){return touchManager;}

    //シングルトン実装
    private App() { }
    private static App app = new App();
    public static App Get()
    {
        return app;
    }
}
