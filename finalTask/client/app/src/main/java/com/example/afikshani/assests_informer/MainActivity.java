package com.example.afikshani.assests_informer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

        try {
            userSocket = IO.socket("http://localhost:8081");
            userSocket.connect();
            userSocket.on("stock info", onStockInfoPush);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        // Setting up the stocks spinner
        stocks_spinner = findViewById(R.id.stocks_spinner);
        ArrayAdapter<CharSequence> stock_adapter = ArrayAdapter.createFromResource(this, R.array.stocks_list, android.R.layout.simple_spinner_item);
        stock_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stocks_spinner.setAdapter(stock_adapter);
        stocks_spinner.setOnItemSelectedListener(this);

        submit_btn = findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stockName = stocks_spinner.getSelectedItem().toString();
                userSocket.emit("stocks updates", stockName);
            }
        });


    }


    private Emitter.Listener onStockInfoPush = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String StockName, stockVal;
                    try {
                        StockName = data.getString("Meta Data");
                        //stockVal = data.getString("username");
                    } catch (JSONException e) {
                        return;
                    }
                    Toast.makeText(MainActivity.this, StockName, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

}



