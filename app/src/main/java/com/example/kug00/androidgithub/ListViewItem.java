package com.example.kug00.androidgithub;

/**
 * Created by soslt on 2017-10-14.
 */

public class ListViewItem {
    private String hourStr ;
    private String minuteStr ;
    private String todoStr ;
    public void setHoure(String hour) {
        hourStr = hour ;
    }
    public void setMinute(String minute) {minuteStr = minute ;}
    public void setTodo(String todo) {
        todoStr = todo ;
    }
    public String getHour() {
        return this.hourStr ;
    }
    public String getMinute() {
        return this.minuteStr ;
    }
    public String getTodo() { return this.todoStr ;}
}