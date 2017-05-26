package com.example.joel4.mobileassignment1wind;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


// Author: Joel Satkasukas
// R00116315



public class MainActivity extends ActionBarActivity {


    private TextView buttonsClickedText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonsClickedText = (TextView) findViewById(R.id.returnedTextView);
        ArrayList<String> list = new ArrayList<>();

        for(ConverterInterface.convertType c : ConverterInterface.convertType.values())
        {
            list.add(getResources().getString(c.getID())) ;
        }



        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);



        ListView listView = (ListView) findViewById(R.id.MainListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //Toast.makeText(getApplicationContext(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();

                Intent selection = new Intent(MainActivity.this, CalculateActivity.class) ;

                selection.putExtra("Selection" ,position) ; // dont think this needs to be localized as its purpose is for program and not for user to see

                startActivity(selection) ;
            }
        });


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, activity3.class) ;
                startActivityForResult(intent, 0);


            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case (0) :
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    buttonsClickedText.setText(getResources().getString(R.string.mainTimesClicked)+" "+data.getIntExtra("buttonsClicked",0));

                }
                break;
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
