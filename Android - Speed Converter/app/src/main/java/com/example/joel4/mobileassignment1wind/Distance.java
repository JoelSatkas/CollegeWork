package com.example.joel4.mobileassignment1wind;

/**
 * Created by joel4 on 23/10/2015.
 */
public class Distance implements ConverterInterface
{

    private convertType type;


    public Distance()
    {
        type = convertType.distance ;
    }


    public String calcualte(int firstPos, int secondPos, int value)

    {
        return " this is just to demonstrate\nthat this project could be extended and made to use\n different classes to convert different units";
    }

    public String getType()
    {
        return null;
    }

    enum units
    {
        Km(R.string.km_string),miles(R.string.miles_string),feet(R.string.foot_string),inches(R.string.inches_string),meters(R.string.meters_string)
        ,Cm(R.string.cm_string),Mm(R.string.mm_string),yards(R.string.yards_string),Ly(R.string.ly_string);

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
