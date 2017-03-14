package com.kartuzov.testappgit.gittest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alorma.github.sdk.bean.dto.response.search.UsersSearch;

import java.io.IOException;
import java.net.URL;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText Name;
    LinearLayout Table;
    Button Search;
    LinearLayout.LayoutParams l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText) findViewById(R.id.name);
        Table = (LinearLayout) findViewById(R.id.table);
        Search = (Button) findViewById(R.id.search);


        Search.setOnClickListener(this);

        l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {
        Call<UsersSearch> call = App.getApi().users(Name.getText().toString());
        call.enqueue(new Callback<UsersSearch>() {
            @Override
            public void onResponse(Call<UsersSearch> call, Response<UsersSearch> response) {
                Table.removeAllViews();
                if(response.body().items.size()>0) {
                    try {
                        MakeRow(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    TextView err = new TextView(MainActivity.this);
                    err.setText(R.string.noUser);
                    Table.addView(err, l);
                }


            }
            @Override
            public void onFailure(Call<UsersSearch> call, Throwable t) {

            }
        });
    }

    private void MakeRow(Response<UsersSearch> response) throws IOException {
        for(int i=0;i<response.body().items.size();i++) {
            final TextView nick = new TextView(MainActivity.this);
            nick.setText(response.body().items.get(i).login);
            final URL url = new URL(response.body().items.get(i).avatar_url);

            new Thread(new Runnable() {
                public void run() {
                    Bitmap bmp = null;
                    try {
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final ImageView avatar = new ImageView(MainActivity.this);
                    avatar.setImageBitmap(bmp);
                    Table.post(new Runnable() {
                        @Override
                        public void run() {
                            Table.addView(nick, l);
                            Table.addView(avatar, l);
                        }
                    });

                }
            }).start();


        }

    }
}
