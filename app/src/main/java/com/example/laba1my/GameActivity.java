package com.example.laba1my;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laba1my.model.Letter;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private int time;
    private int min;
    private int max;

    private String[] arrWords  = new String[] {"Январь", "Февраль", "Апрель", "Июль", "Июнь", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь12"};
    private ArrayList<String> _arrWords = new ArrayList<String>();
    private String rightAnswer;
    private int countRight = 0;

    private ArrayList<Letter> words = new ArrayList<Letter>();
    private ArrayList<Letter> wordsAnswer = new ArrayList<Letter>();

    private TextView labelTimer;
    private CountDownTimer tmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        Intent intent = this.getIntent();
        this.min = Integer.parseInt( intent.getStringExtra("min") );
        this.max = Integer.parseInt(  intent.getStringExtra("max") );
        if ( savedInstanceState != null)
            this.time = savedInstanceState.getInt("time");
        else
            this.time = Integer.parseInt( intent.getStringExtra("time") ) *1000;

        newWord();

        labelTimer = (TextView) findViewById(R.id.time);
        this.tmr = new CountDownTimer(this.time, 1000) {
            public void onTick(long millisUntilFinished) {
                time = (int)millisUntilFinished;
                labelTimer.setText("Осталось: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                showDlg();
            }
        }.start();
    }

    @Override
    protected void onStop() {
        this.tmr.cancel();
        super.onStop();
    }

    private void newWord(){
        this.rightAnswer = "";
        this.words.clear();
        this.wordsAnswer.clear();

        // генерация слова
        this.setRightAnswer( this.generateWord() ) ;
        // конфигурация букв
        configurateWords();
        // буквы слова
        drawLetters(0);
        // буквы ответа
        drawLetters(1);
    }

    private void configurateWords(){
        // перемешиваем
        char [] words_temp = shuffle( this.getRightAnswer() ).toUpperCase().toCharArray();
        // массив
        for(int i = 0; i<words_temp.length; i++){
            Letter letter = new Letter(i, Character.toString( words_temp[i] ));
            this.words.add( letter );
        }

        for( int i = 0; i<this.words.size(); i++){
            Letter letter = new Letter(-1, "");
            this.wordsAnswer.add(letter);
        }
    }


    /**
     * Draw letters
     * @param param Where draw letter( 0 - top or 1 - down )
     */
    private void drawLetters(int param){
        if ( param == 0){ // word
            LinearLayout lay1 = (LinearLayout)findViewById(R.id.lay1);
            lay1.removeAllViews();

            for( int i =0; i<this.words.size(); i++){
                TextView label = new TextView(this);
                label.setId(200 + i);
                label.setTextSize(30);
                label.setWidth(100);
                label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                label.setTypeface(null, Typeface.BOLD);
                label.setText( ((Letter)this.words.get(i)).getLetter() );
                label.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickLetter(v.getId());
                    }
                });
                lay1.addView(label);
            }
        }else{ // answer
            LinearLayout lay2 = (LinearLayout)findViewById(R.id.lay2);
            lay2.removeAllViews();

            int id = 0;
            for( int i =0; i<this.wordsAnswer.size(); i++){
                id = 100 + i;
                TextView label = new TextView(this);
                label.setId(id);
                label.setTextSize(30);
                label.setWidth(100);
                label.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                label.setTypeface(null, Typeface.BOLD);
                Letter _letter = ((Letter)this.wordsAnswer.get(i));
                label.setText( (_letter.isLetter() ) ? _letter.getLetter() : "_" );
                label.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        onClickLetter(v.getId());
                    }
                });
                lay2.addView(label);
            }
        }
    }

    /**
     * Events for click letter
     * @param id
     */
    private void onClickLetter(int id){
        int _id = 0;

        if ( id >= 100 & id <200){ // ответ
            _id = id - 100;
            Letter currLetter = ((Letter)this.wordsAnswer.get(_id));

            if ( currLetter.isLetter() ) {
                this.words.set(currLetter.getIndex(), currLetter);
                int size = this.wordsAnswer.size();
                for( int i = _id; i<size; i++){
                    if ( i + 1 != size)
                        this.wordsAnswer.set(i, this.wordsAnswer.get(i+1));
                }
                this.wordsAnswer.set(size-1, new Letter(-1, ""));
                //this.wordsAnswer.set(_id, new Letter(-1, ""));
            }
        }else if( id >= 200 & id <300){ // буква
            _id = id - 200;

            if ( !((Letter)this.words.get(_id)).getLetter().equals(" ") ) {
                for (int i = 0; i < this.wordsAnswer.size(); i++) {
                    Letter currLetter = ((Letter) this.wordsAnswer.get(i));

                    if (!currLetter.isLetter()) {


                        this.wordsAnswer.set(i, (Letter) this.words.get(_id));
                        this.words.set(_id, new Letter(_id, " "));
                        break;
                    }
                }
            }
        }
        drawLetters(0);
        drawLetters(1);
    }

    /**
     * Event on click button
     * @param v
     */
    public void onClickButton(View v){
        String res = "";
        for ( Letter lett : this.wordsAnswer ){
            res += lett.getLetter();
        }

        System.out.println(res);
        System.out.println( rightAnswer );

        if ( res.equals(rightAnswer) ){
            Toast.makeText( GameActivity.this, "Угадал!", Toast.LENGTH_SHORT).show();
            this.countRight++;
            TextView label = findViewById(R.id.countAnswer);
            label.setText("Правильных: " + this.countRight );
            newWord();
        }
    }

    private String getRightAnswer(){
        return this.rightAnswer;
    }

    private void setRightAnswer( String answer){
        this.rightAnswer = answer;
    }

    /**
     * @return random generated word
     */
    private String generateWord(){
        for ( int i=0; i<this.arrWords.length; i++){
            if ( this.arrWords[i].length() >= this.min && this.arrWords[i].length() <= this.max ){
                this._arrWords.add(this.arrWords[i]);
            }
        }
        int rnd = new Random().nextInt(this._arrWords.size());
        return this._arrWords.get(rnd).toUpperCase();
    }

    private static String shuffle(String text) {
        char[] characters = text.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = (int)(Math.random() * characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    private void showDlg() {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Время вышло!");
            adb.setMessage("Всего правильных ответов:" + this.countRight );
            adb.setIcon(android.R.drawable.ic_dialog_info);
            adb.setPositiveButton("Закрыть", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case Dialog.BUTTON_POSITIVE:
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        break;
                    }
                }
            });

            adb.create().show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("time", this.time );
    }
}
