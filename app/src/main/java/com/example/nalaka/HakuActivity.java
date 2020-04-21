package com.example.nalaka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HakuActivity extends AppCompatActivity implements View.OnClickListener {

    AutoCompleteTextView atv;
    String[] kaupungit = new String[]{"Helsinki", "Turku", "Tampere", "Oulu", "Vaasa", "Pori", "Rovaniemi", "Espoo", "Vantaa", "Lappeenranta", "Tornio", "Kemi", "Oulunsalo"};
    String[] ruokalajit = new String[]{"Pizza","Kebab","Porkkanalaatikko","Hapurilainen","Hummus","Keitto","Pihvi","Kalaa"};
    ArrayList<String> haettavatTagi = new ArrayList<>();
    TextView textviewTagit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haku);
        textviewTagit = findViewById(R.id.TextViewTagit);

        findViewById(R.id.BtnTaginLisays).setOnClickListener(this);
        findViewById(R.id.BtnHaku).setOnClickListener(this);
        findViewById(R.id.logo_btn).setOnClickListener(this);
        findViewById(R.id.menu_img_btn).setOnClickListener(this);
        atv = findViewById(R.id.AutoHaku);
        atv.setAdapter(new ArrayAdapter<String>(HakuActivity.this, android.R.layout.simple_list_item_1, kaupungit));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.BtnTaginLisays)
        {

            if(atv.getText().toString() != null)
            {
                textviewTagit.setText("Tagit: ");
                haettavatTagi.add(atv.getText().toString());
                for (int i = 0; i < haettavatTagi.size(); i++ )
                {
                    textviewTagit.setText(textviewTagit.getText().toString() + " " + haettavatTagi.get(i) + ",");
                }

            }
            atv.setText("");
        }
        if (v.getId() == R.id.BtnHaku)
        {
            haettavatTagi.clear();
        }
        if (v.getId() == R.id.logo_btn){
            Intent intentPaasivu = new Intent (this, Paasivu.class);
            startActivity(intentPaasivu);
        }
        if (v.getId() == R.id.menu_img_btn) {
            PopupMenu popup = new PopupMenu(HakuActivity.this, findViewById(R.id.menu_img_btn));
            popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId()) {
                        case R.id.three:
                            Toast.makeText(HakuActivity.this, "Farewell", Toast.LENGTH_SHORT).show();
                            finish();
                            return true;
                        case R.id.two:
                            Toast.makeText(HakuActivity.this, "Nothing here sorry", Toast.LENGTH_LONG).show();
                            return true;
                        case R.id.one:
                            Intent intentPaasivu = new Intent (HakuActivity.this, Paasivu.class);
                            startActivity(intentPaasivu);
                            Toast.makeText(HakuActivity.this, "Pääsivu", Toast.LENGTH_SHORT).show();
                            return true;
                    }
                    return false;
                }
            });
            popup.show();
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioKaupunki:
                if (checked)
                    atv.setText("");
                    atv.setAdapter(new ArrayAdapter<String>(HakuActivity.this, android.R.layout.simple_list_item_1, kaupungit));
                    break;
            case R.id.radioRuokalaji:
                if (checked)
                    atv.setText("");
                    atv.setAdapter(new ArrayAdapter<String>(HakuActivity.this, android.R.layout.simple_list_item_1, ruokalajit));
                    break;
        }
    }

}