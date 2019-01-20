package com.example.afikshani.assests_informer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static int default_pos = 0;
    Spinner stocks_spinner;
    Button submit_btn;
    private Socket userSocket;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.getItemAtPosition(default_pos);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create socket wrapper and initialize socket.io
        MySocketWrapper socketWrapper = new MySocketWrapper();
        userSocket = socketWrapper.getSocket();
        userSocket.on("stock info", onStockInfoPush);
        userSocket.connect();


        // Setting up the stocks spinner
        stocks_spinner = findViewById(R.id.stocks_spinner);
        ArrayAdapter<CharSequence> stock_adapter = ArrayAdapter.createFromResource(this, R.array.stocks_list, android.R.layout.simple_spinner_item);
        stock_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stocks_spinner.setAdapter(stock_adapter);
        stocks_spinner.setOnItemSelectedListener(this);


        // Create the submit button to be working with
        submit_btn = findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockName = stocks_spinner.getSelectedItem().toString();
                userSocket.emit("stocks updates", stockName);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        userSocket.off("stock info", onStockInfoPush);
        userSocket.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userSocket.on("stock info", onStockInfoPush);
        userSocket.connect();
    }


    // Create an emitter listener for stock information
    private Emitter.Listener onStockInfoPush = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String stockVal;
                    try {
                        stockVal = data.getString("stockValue");
                    } catch (JSONException e) {
                        return;
                    }
                    ((TextView) findViewById(R.id.stock_name)).setText(stockVal);
                }
            });
        }
    };



}



