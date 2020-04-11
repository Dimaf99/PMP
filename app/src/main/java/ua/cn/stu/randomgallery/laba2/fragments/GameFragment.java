package ua.cn.stu.randomgallery.laba2.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import ua.cn.stu.randomgallery.laba2.R;
import ua.cn.stu.randomgallery.laba2.model.Letter;
import ua.cn.stu.randomgallery.laba2.services.MainService;



public class GameFragment extends BaseFragment {

    // settings
    private int min;
    private int max;
    private int time;
    private String[] arrWords;

    // temp
    private ArrayList<String> _arrWords = new ArrayList<String>();

    // ui
    private View myview;
    private TextView labelTimer;
    private CountDownTimer tmr;

    // info
    private int countRight = 0;
    private String rightAnswer;
    private ArrayList<Letter> words = new ArrayList<Letter>();
    private ArrayList<Letter> wordsAnswer = new ArrayList<Letter>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.myview = view;

        newWord();

        labelTimer = (TextView) view.findViewById(R.id.time);
        this.tmr = new CountDownTimer(this.time * 60000, 1000) {
            public void onTick(long millisUntilFinished) {
                time = (int)millisUntilFinished;
                labelTimer.setText( getResources().getString(R.string.time_left) + millisUntilFinished / 1000);
            }

            public void onFinish() {
                showDlg();
            }
        }.start();

        Button btn = (Button) view.findViewById(R.id.button );
        btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String res = "";
                for ( Letter lett : wordsAnswer ){
                    res += lett.getLetter();
                }

                System.out.println(res);
                System.out.println( rightAnswer );

                if ( res.equals(rightAnswer) ){
                    Toast.makeText( getActivity(), getResources().getString(R.string.guessed), Toast.LENGTH_SHORT).show();
                    countRight++;
                    TextView label = view.findViewById(R.id.countAnswer);
                    label.setText(getResources().getString(R.string.correct) + countRight );
                    newWord();
                }
            }
        });

        System.out.println( ((MainService) getAppContract().getServices() ).getSetting().getMin() );
    }

    @Override
    public void onStop() {
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
            LinearLayout lay1 = (LinearLayout) this.myview.findViewById(R.id.lay1);
            lay1.removeAllViews();

            for( int i =0; i<this.words.size(); i++){
                TextView label = new TextView(getActivity());
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
            LinearLayout lay2 = (LinearLayout) this.myview.findViewById(R.id.lay2);
            lay2.removeAllViews();

            int id = 0;
            for( int i =0; i<this.wordsAnswer.size(); i++){
                id = 100 + i;
                TextView label = new TextView(getActivity());
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
     * @return random generated word
     */
    private String generateWord(){
        this.arrWords = getAppContract().getServices().getStrings();
        this.min =  getAppContract().getServices().getSetting().getMin();
        this.max = getAppContract().getServices().getSetting().getMax();
        this.time = getAppContract().getServices().getSetting().getTime();

        for ( int i=0; i<this.arrWords.length; i++){
            if ( this.arrWords[i].length() >= this.min && this.arrWords[i].length() <= this.max ){
                this._arrWords.add(this.arrWords[i]);
            }
        }
        int rnd = new Random().nextInt(this._arrWords.size());
        return this._arrWords.get(rnd).toUpperCase();
    }


    private void showDlg() {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle(getResources().getString(R.string.time_end));
        adb.setMessage( getResources().getString(R.string.count_right) + this.countRight );
        adb.setIcon(android.R.drawable.ic_dialog_info);
        adb.setPositiveButton(getResources().getString(R.string.btn_close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        getAppContract().cancel();
                        break;
                }
            }
        });

        adb.create().show();
    }


    // events
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

    }


    // getter and setters
    private String getRightAnswer(){
        return this.rightAnswer;
    }

    private void setRightAnswer( String answer){
        this.rightAnswer = answer;
    }


    // functjon random letter in word
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
}
