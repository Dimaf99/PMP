package ua.cn.stu.laba4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import ua.cn.stu.laba4.database.AppDatabase;
import ua.cn.stu.laba4.database.dao.MeteorDao;
import ua.cn.stu.laba4.database.models.Meteor;
import ua.cn.stu.laba4.retrofit.Data;
import ua.cn.stu.laba4.retrofit.JsonPlaceHolderApi;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private AppDatabase database = null;
    private MeteorDao meteorDao;
    private List<Meteor> _tempList;

    boolean useDataInDB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( database == null ) {
            database = Room.databaseBuilder(this, AppDatabase.class, "dbmeteor.db")
                    .allowMainThreadQueries()
                    .build();
            meteorDao = database.getMeteorDao();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.nasa.gov")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Data>> call =jsonPlaceHolderApi.getAll();
        call.enqueue( new Callback<List<Data>>(){
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if ( !response.isSuccessful()){
                    return;
                }

                // data recieved from API
                List<Data> meteorsData = response.body();
                // tranform data from API to array list Meteors objects
                List<Meteor> listMeteor = new ArrayList<>();
                for( Data data: meteorsData){
                    Meteor meteor = new Meteor(
                            data.getId(),
                            data.getName(),
                            data.getYear(),
                            data.getNametype(),
                            data.getMass(),
                            data.getReclat(),
                            data.getReclong(),
                            data.getRecclass(),
                            data.getFall()
                    );
                    listMeteor.add(meteor);
                }

                List<Meteor> listDatabase = meteorDao.getAll();
                int countData = listDatabase.size();
                if ( countData > 0 ){
                    if ( countData != meteorsData.size() ){
                        useDataInDB = false;
                        meteorDao.deleteAll();
                        meteorDao.insert(listMeteor);
                    }else{
                        useDataInDB = true;
                    }
                }else{
                    useDataInDB = false;
                    meteorDao.insert(listMeteor);
                }

                System.out.println(( useDataInDB ) ? "listDatabase" : "listMeteor");
                showList( ( useDataInDB ) ? listDatabase : listMeteor );
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                System.out.println("xxxxxx: "+ t );
            }
        });
    }

    private void showList(List<Meteor> list){
        this._tempList = list;
        RecyclerView listView = (RecyclerView) findViewById(R.id.list);


        LinearLayoutManager mRecentLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(mRecentLayoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());

        MeteorsListAdapter meteorsListAdapter = new MeteorsListAdapter(list, MainActivity.this);
        listView.setAdapter(meteorsListAdapter);

        meteorsListAdapter.setItemClickListener(this);

    }

    @Override
    public void onClick(View view, int position){
        int id = this._tempList.get(position).getId();
        Meteor meteor = meteorDao.findById(id);


        Intent intent = new Intent(this, DescriptionActivity.class );
        intent.putExtra("id",  String.valueOf( id ) );
        intent.putExtra("meteors", meteor);
        startActivity(intent);
    }

}
