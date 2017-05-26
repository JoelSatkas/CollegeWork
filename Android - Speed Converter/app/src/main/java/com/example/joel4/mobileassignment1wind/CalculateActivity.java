package com.example.joel4.mobileassignment1wind;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class CalculateActivity extends ActionBarActivity
{

    private TextView ansView;
    private ConverterInterface convertType;
    private String ans;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        ansView = (TextView) findViewById(R.id.ans) ;

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            ans = savedInstanceState.getString("answer");
            ansView.setText(ans);

        } else {
            // Probably initialize members with default values for a new instance
        }

        Intent Selection = getIntent() ;
        final int POSITION = Selection.getIntExtra("Selection",3) ; // will use speed as default

        final Spinner field1 = (Spinner) findViewById(R.id.spinnerField1);
        final Spinner field2 = (Spinner) findViewById(R.id.spinnerField2);
        TextView title = (TextView) findViewById(R.id.title);

        Resources res = getResources() ;
        ArrayList<String> subUnitList = new ArrayList<>();
        ArrayAdapter adapter ;



        switch (POSITION) // same order as ConverterInterface
        {
            case 0:  // user has selected distance
                convertType = new Distance();
                for(Distance.units c : Distance.units.values())
                {
                    subUnitList.add(getResources().getString(c.getRescourceID())) ;
                }
                title.setText(getString(R.string.distance));
                break;
            case 1:  // user has selected speed
                convertType = new Speed();
                for(Speed.units c : Speed.units.values())
                {
                    subUnitList.add(getResources().getString(c.getRescourceID())) ;
                }
                title.setText(getString(R.string.speed));
                break;
            case 2:  // user has selected temperature
                convertType = new temperature();
                for(temperature.units c : temperature.units.values())
                {
                    subUnitList.add(getResources().getString(c.getRescourceID())) ;
                }
                title.setText(getString(R.string.temp));
                break ;
            case 3:  // user has selected weight
                convertType = new weight();
                for(weight.units c : weight.units.values())
                {
                    subUnitList.add(getResources().getString(c.getRescourceID())) ;
                }
                title.setText(getString(R.string.weight));
                break;
            default: // will use speed as default
                convertType = new Speed();
                for(Speed.units c : Speed.units.values())
                {
                    subUnitList.add(getResources().getString(c.getRescourceID())) ;
                }
                title.setText(getString(R.string.speed));
                break ;
        }



        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,subUnitList);

        field1.setAdapter(adapter) ;
        field2.setAdapter(adapter) ;



        final EditText txtField = (EditText) findViewById(R.id.editText);
        txtField.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {

                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    int value;

                    if(txtField.getText().toString().equals(""))
                    {
                        value = 0 ;
                    }
                    else
                    {
                        value = Integer.parseInt(txtField.getText().toString());
                    }

                    calculate(value, (field1.getSelectedItemPosition()+1), (field2.getSelectedItemPosition()+1)) ;

                    return true ;
                }
                // grab the text for use in the activity
                return false ;
            }
        }) ;

    }

    public void calculate( int value, int from, int to)
    {



        ans = "ANS: "+convertType.calcualte(from, to, value) ;


        ansView.setText(ans);
        drawGraphic(value);

    }

    private void drawGraphic(long value)
    {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#CD5C5C"));
        Bitmap bg = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);
        canvas.drawRect(value, 20, 30, 40, paint);
        LinearLayout ll = (LinearLayout) findViewById(R.id.rect);
        ll.setBackgroundDrawable(new BitmapDrawable(bg));
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString("answer", ans);


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculate, menu);
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
