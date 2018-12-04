package hu.oe.szakdolgozat.n46god.ezerszigethorgszt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class IdopontFoglalasNap extends AppCompatActivity {
    String[] allasok = {"1. állás", "2. állás", "3. állás", "4. állás", "5. állás", "6. állás", "7. állás", "8. állás", "9. állás", "10. állás", "11. állás", "12. állás", "13. állás", "14. állás", "15. állás", "16. állás", "VIP"};
    String[] jegyTipus = {"12 órás", "24 órás", "72 órás", "egyedi"};
    String[] horgaszokSzama = {"1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idopont_foglalas_nap);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String datum = getIntent().getStringExtra("EV") + "-" + getIntent().getStringExtra("HONAP") + "-" + getIntent().getStringExtra("NAP");

        setTitle(getTitle() + ": " + datum);

        Spinner spinner = (Spinner) findViewById(R.id.allasok);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, allasok);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        Spinner spinnerJegy = (Spinner) findViewById(R.id.jegyTipus);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, jegyTipus);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerJegy.setAdapter(spinnerArrayAdapter2);

        Spinner spinnerHorgaszokSzama = (Spinner) findViewById(R.id.horgaszokSzama);
        ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, horgaszokSzama);
        spinnerArrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerHorgaszokSzama.setAdapter(spinnerArrayAdapter3);
        //tv.setText(datum);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
