package com.cwtcn.kmlib.data;

/***
 * 游戏数据的数据
 * @author Allen
 *
 */
public class GameData {
    //"duration":15,"enable":true,"game":0,"hand":1,"imei":"860861000000011"
    private String imei;
    /**
     * 游戏类别0：手枪，1：击鼓,2:魔法棒
     */
    private int game;
    private boolean enable;
    /**
     * 0:左手1：右手
     */
    private int hand;

    public GameData(String imei, int game, boolean enable, int hand) {
        this.imei = imei;
        this.game = game;
        this.enable = enable;
        this.hand = hand;
    }

    public String getImei() {
        return imei;
    }

    public int getGame() {
        return game;
    }

    public boolean isEnable() {
        return enable;
    }

    public int getHand() {
        return hand;
    }
}
