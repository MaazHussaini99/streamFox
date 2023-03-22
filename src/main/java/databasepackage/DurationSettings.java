package databasepackage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 *
 */
public class DurationSettings {


    //private Disney DisneyWatchtime;
    //private Collection<Disney> DisneyWatchtimeInfo;

    //private Netflix NetflixWatchTime;
    //private Youtube YotubeWatchTimeInfo;
    //private Collection<Youtube> YoutubeWatchtimeInfo;
    /*
    public Disney getDisneyWatchtime() {
        return DisneyWatchtime;
    }

    public void setDisneyWatchtime(Disney disneyWatchtime) {
        DisneyWatchtime = disneyWatchtime;
    }
    */

    private String TotalCurrentWeekWatchTime;
    private String TotalCurrentDailyWatchTime;
    private float SetLimit;





    public DurationSettings(String TotalCurrentWeekWatchTime, String TotalCurrentDailyWatchTime, String DailyWatchTime, String WeeklyWatchTime) {
        this.TotalCurrentWeekWatchTime = TotalCurrentWeekWatchTime;
        this.TotalCurrentDailyWatchTime = TotalCurrentDailyWatchTime;


    }







    public String getTotalCurrentWeekWatchTime() {
        return TotalCurrentWeekWatchTime;
    }

    public void setTotalCurrentWeekWatchTime(String TotalCurrentWeekWatchTime) {
        this.TotalCurrentWeekWatchTime = TotalCurrentWeekWatchTime;
    }

    public String getTotalCurrentDailyWatchTime() {
        return TotalCurrentDailyWatchTime;
    }

    public void setTotalCurrentDailyWatchTime(String TotalCurrentDailyWatchTime) {
        this.TotalCurrentDailyWatchTime = TotalCurrentDailyWatchTime;
    }

    public float getSetLimit() {
        return SetLimit;
    }

    public void setSetLimit(float SetLimit) {
        this.SetLimit = SetLimit;
    }

    @Override
    public String toString() {
        return "DurationSettings{" +
                "TotalCurrentWeekWatchTime='" + TotalCurrentWeekWatchTime + '\'' +
                ", TotalCurrentDailyWatchTime='" + TotalCurrentDailyWatchTime + '\'' +
                ", SetLimit=" + SetLimit +
                '}';
    }
}
