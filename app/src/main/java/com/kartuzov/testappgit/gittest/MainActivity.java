package com.kartuzov.testappgit.gittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alorma.github.sdk.bean.dto.response.search.UsersSearch;
import com.kartuzov.testappgit.gittest.MainListView.ListViewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName;
    Button btnSearch;
    ListView lvMain;
    SimpleAdapter sAdapter;
    ArrayList<Map<String, Object>> arRows;
    Map<String, Object> mRow;
    final String ATTRIBUTE_NAME_NICK = "nick";
    final String ATTRIBUTE_NAME_AVATAR = "avatar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.name);
        btnSearch = (Button) findViewById(R.id.search);
        btnSearch.setOnClickListener(this);

        arRows = new ArrayList<Map<String, Object>>();

        String[] from = { ATTRIBUTE_NAME_NICK, ATTRIBUTE_NAME_AVATAR};
        int[] to = { R.id.tvText, R.id.ivImg };

        sAdapter = new ListViewAdapter(this, arRows, R.layout.item_person, from, to);

        lvMain = (ListView) findViewById(R.id.main);
        lvMain.setAdapter(sAdapter);

    }

    @Override
    public void onClick(View view) {
        Call<UsersSearch> call = App.getApi().users(etName.getText().toString());
        call.enqueue(new Callback<UsersSearch>() {
            @Override
            public void onResponse(Call<UsersSearch> call, Response<UsersSearch> response) {
                arRows.clear();
                sAdapter.notifyDataSetChanged();
                if(response.body().items.size()>0) {
                    try {
                        MakeRow(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    MakeToast(R.string.noUser);
                }
            }
            @Override
            public void onFailure(Call<UsersSearch> call, Throwable t) {
                MakeToast(R.string.errConnect);
            }
        });
    }

    private void MakeRow(Response<UsersSearch> response) throws IOException {
        for(int i=0;i<response.body().items.size();i++) {
            final String nick = response.body().items.get(i).login;
            final String uri = response.body().items.get(i).avatar_url;
            new Thread(new Runnable() {
                public void run() {
                    lvMain.post(new Runnable() {
                        @Override
                        public void run() {
                            mRow = new HashMap<String, Object>();
                            mRow.put(ATTRIBUTE_NAME_NICK,nick );
                            mRow.put(ATTRIBUTE_NAME_AVATAR, uri);
                            arRows.add(mRow);
                            sAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }).start();


        }

    }

    private void MakeToast(int text){
        Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG);
        toast.setGravity(17, 0, 0);
        toast.show();
    }


}
