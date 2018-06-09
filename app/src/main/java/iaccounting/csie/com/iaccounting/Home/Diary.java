package iaccounting.csie.com.iaccounting.Home;

import iaccounting.csie.com.iaccounting.Utils.Tools;

/**
 * Created by Zixuan Zhao on 2/19/17.
 */

public class Diary {
    private String createDateTime;
    private String lastDateTime;
    private String weather;
    private String emotion;
    private String diaryText;
    private int imgID;

    public Diary(String createDateTime, String lastDateTime, String weather, String emotion, String diaryText) {
        this.createDateTime = createDateTime;
        this.lastDateTime = "ddd";
        this.weather = weather;
        this.emotion = emotion;
        this.diaryText = diaryText;
        this.imgID = -1;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getLastDateTime() {
        return lastDateTime;
    }

    public void setLastDateTime(String lastDateTime) {
        this.lastDateTime = lastDateTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getDiaryText() {
        return diaryText;
    }

    public void setDiaryText(String diaryText) {
        this.diaryText = diaryText;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public String[] diaryInfo(){
        String[] diary = {createDateTime, lastDateTime, weather, emotion, diaryText};
        return diary;
    }
}
