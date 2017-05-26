package com.example.joel4.mobileassignment1wind;

/**
 * Created by joel4 on 23/10/2015.
 */
public class weight implements ConverterInterface
{

    public String calcualte(int firstPos, int secondPos, int value)
    {
        return "this is just to demonstrate\nthat this project could be extended and made to use\n different classes to convert different units";
    }

    public String getType() {
        return null;
    }

    enum units
    {
        grams(R.string.grams_string),Kg(R.string.kg_string),ounces(R.string.ounces_string),pounds(R.string.pounds_string),stone(R.string.stones_string);

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
