package com.pennapps.vnd.ffling;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class FacebookIntegration extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
