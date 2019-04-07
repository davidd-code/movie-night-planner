package me.daviddoan.planner.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import me.daviddoan.planner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ExampleItem> exampleList = new ArrayList<>();
        exampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        exampleList.add(new ExampleItem(R.drawable.ic_av_timer, "Line 3", "Line 4"));
        exampleList.add(new ExampleItem(R.drawable.ic_battery_90, "Line 5", "Line 6"));
    }
}
