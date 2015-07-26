package com.fatjoni.droid.maturaassist;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "Intent Message";
    @Bind(R.id.mesatarja) EditText m;
    @Bind(R.id.nota_letersise) EditText d1;
    @Bind(R.id.nota_matematikes) EditText d2;
    @Bind(R.id.nota_z1) EditText z1;
    @Bind(R.id.nota_z2) EditText z2;
    @Bind(R.id.spinner_koeficienti_shkolles) Spinner k;
    @Bind(R.id.spinner_koeficienti_z1) Spinner f1;
    @Bind(R.id.spinner_koeficienti_z2) Spinner f2;
    @Bind(R.id.adView) AdView mAdView;

    public static final String DEVICE_ID = "605C9D109BD6BC5D68A431986B59BBD3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner koefShkolla = (Spinner) findViewById(R.id.spinner_koeficienti_shkolles);
        Spinner koefZ1 = (Spinner) findViewById(R.id.spinner_koeficienti_z1);
        Spinner koefZ2 = (Spinner) findViewById(R.id.spinner_koeficienti_z2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.koeficientet_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        koefShkolla.setAdapter(adapter);
        koefZ1.setAdapter(adapter);
        koefZ2.setAdapter(adapter);

        ButterKnife.bind(this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder().addTestDevice(DEVICE_ID).build();
                mAdView.loadAd(adRequest);
                mAdView.bringToFront();
            }
        }, 500);

    }

    @OnClick(R.id.btn_llogarit)
    public void llogarit(View v){
        Double dm, dd1, dd2, dz1, dz2;

        try{
            dm = Double.parseDouble(String.valueOf(m.getText()));
            dd1 = Double.parseDouble(String.valueOf(d1.getText()));
            dd2 = Double.parseDouble(String.valueOf(d2.getText()));
            dz1 = Double.parseDouble(String.valueOf(z1.getText()));
            dz2 = Double.parseDouble(String.valueOf(z2.getText()));

            if(dm < 4.5 || dd1 < 4.5 || dd2 < 4.5 || dz1 < 4.5 || dz2 < 4.5){
                Toast.makeText(this,"Me note me te vogel se 4.5 kot i llogarit piket! :(",Toast.LENGTH_SHORT).show();
            }else{
                if (dm <= 10.5 && dd1 <= 10.0 && dd2 <= 10.0 && dz1 <= 10.0 && dz2 <= 10.0){
                    Double dk = Double.parseDouble(String.valueOf(k.getSelectedItem().toString()));
                    Double df1 = Double.parseDouble(String.valueOf(f1.getSelectedItem().toString()));
                    Double df2 = Double.parseDouble(String.valueOf(f2.getSelectedItem().toString()));

                    Double s1 = (26*dm + 20*(dd1+dd2))*dk;
                    Double s2 = 17*(dz1*df1 + dz2*df2);

                    Double shuma = 0.0;
                    shuma = 5*(s1+s2);

                    String str = String.format("%.2f", shuma);
                    str = str + " pike";

                    Intent intent = new Intent(this, ResultActivity.class);
                    intent.putExtra(INTENT_MESSAGE, str);
                    startActivity(intent);

                }else {
                    Toast.makeText(this,"Note me e madhe se 10.0! :P Ja ke fut kot!",Toast.LENGTH_SHORT).show();
                }

            }

        }catch (Exception e){
            Toast.makeText(this,"Nuk llogariten piket po nuk i plotesove te gjitha inputet!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.fatjoni.com"));
            startActivity(browserIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
