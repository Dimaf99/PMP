package ua.cn.stu.randomgallery.laba3.model;

import java.util.Set;

public class Settings {
    private int min;
    private int max;
    private int time;
    private Set<String> arrWords;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setWords( Set<String> words ){ this.arrWords = words; }

    public Set<String> getWords(){ return this.arrWords; }
}
