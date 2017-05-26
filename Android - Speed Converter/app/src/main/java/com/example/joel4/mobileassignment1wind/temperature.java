package com.example.joel4.mobileassignment1wind;

/**
 * Created by joel4 on 23/10/2015.
 */
public class temperature implements ConverterInterface
{

    public String calcualte(int firstPos, int secondPos, int value)
    {
        return "this is just to demonstrate\nthat this project could be extended and made to use\n different classes to convert different units";
    }

    public String getType()
    {
        return null ;
    }

    enum units
    {
        kelvin(R.string.kelvin_string),celsius(R.string.celsius_string),Fahrenheit(R.string.fahren_string);

        private int rescourceID;

        units(int id)
        {
            rescourceID = id;
        }

        public int getRescourceID()
        {
            return rescourceID;
        }
    }
}
