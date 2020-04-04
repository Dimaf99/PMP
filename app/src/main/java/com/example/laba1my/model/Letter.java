package com.example.laba1my.model;

public class Letter {
    private int index;
    private String letter;

    public Letter( int index, String letter){
        this.index = index;
        this.letter = letter;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isLetter(){
        if ( this.index != -1 && !this.letter.equals(""))
            return true;
        else
            return false;
    }
}
