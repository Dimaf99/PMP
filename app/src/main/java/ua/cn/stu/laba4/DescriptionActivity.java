package ua.cn.stu.laba4;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ua.cn.stu.laba4.database.dao.MeteorDao;
import ua.cn.stu.laba4.database.models.Meteor;

public class DescriptionActivity extends AppCompatActivity {
    private Meteor meteor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        this.meteor = (Meteor) getIntent().getSerializableExtra("meteors");
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView textDescript = findViewById(R.id.text_descript);
        String res =    "ID: " + meteor.getId() + "\n" +
                        "Name: " + meteor.getName() + "\n" +
                        "Fall: " + meteor.getFall() + "\n" +
                        "Nametype: " + meteor.getNametype() + "\n" +
                        "Recclass: " + meteor.getRecclass() + "\n" +
                        "Year: " + meteor.getYear() + "\n" +
                        "Mass: " + meteor.getMass() + "\n" +
                        "Reclat: " + meteor.getReclat() + "\n" +
                        "Reclong: " + meteor.getReclong();
        textDescript.setText(res);
    }
}
