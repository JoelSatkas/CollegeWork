package com.example.joel4.mobileassignment1wind;

import android.content.res.Resources;

/**
 * Created by joel4 on 23/10/2015.
 *
 * these classes currently can not be localized as they can not get string resources
 * a possible solution to this problem is to pass a context object to the calculate method.
 * further research needed.
 * (note: context object should not be saved in these classes as it would create a  memory leak)
 */
public class Speed implements ConverterInterface
{
    private convertType type;

    public Speed()
    {
        type = convertType.speed ;
    }

    public String calcualte(int firstPos, int secondPos, int value)
    {
        if(firstPos == 1 && secondPos == 1)
        {
            return value+" Mps "+mpsToBeaufort(value) ;
        }
        else if(firstPos == 1 && secondPos == 2)
        {
            return String.format("%.2f", mpsToKmh(value))+" Kmh "+mpsToBeaufort(value) ;
        }
        else if(firstPos == 1 && secondPos == 3)
        {
            return String.format("%.2f", mpsToMph(value))+" Mph "+mpsToBeaufort(value);
        }
        else if(firstPos == 1 && secondPos == 4)
        {
            return String.format("%.2f", mpsToKnots(value))+" Knots "+mpsToBeaufort(value);
        }
        else if(firstPos == 2 && secondPos == 1)
        {
            return String.format("%.2f", kmhToMps(value))+" Mps "+mpsToBeaufort(kmhToMps(value));
        }
        else if(firstPos == 2 && secondPos == 2)
        {
            return value+" Kmh " + mpsToBeaufort(kmhToMps(value));
        }
        else if(firstPos == 2 && secondPos == 3)
        {
            return String.format("%.2f", kmhToMph(value))+" Mph "+ mpsToBeaufort(kmhToMph(value));
        }
        else if(firstPos == 2 && secondPos == 4)
        {
            return String.format("%.2f", kmhToKnots(value))+" Knots "+mpsToBeaufort(kmhToMph(value));
        }
        else if(firstPos == 3 && secondPos == 1)
        {
            return String.format("%.2f", mphToMps(value))+" Mps "+mpsToBeaufort(mphToMps(value));
        }
        else if(firstPos == 3 && secondPos == 2)
        {
            return String.format("%.2f", mphToKmh(value))+" Kmh "+mpsToBeaufort(mphToMps(value));
        }
        else if(firstPos == 3 && secondPos == 3)
        {
            return value+" Mph "+mpsToBeaufort(mphToMps(value));
        }
        else if(firstPos == 3 && secondPos == 4)
        {
            return String.format("%.2f", mphToKnots(value))+" Knots "+mpsToBeaufort(mphToMps(value));
        }
        else if(firstPos == 4 && secondPos == 1)
        {
            return String.format("%.2f", knotsToMps(value))+" Knots "+mpsToBeaufort(knotsToMps(value));
        }
        else if(firstPos == 4 && secondPos == 2)
        {
            return String.format("%.2f", knotsToKmh(value))+" Knots"+mpsToBeaufort(knotsToMps(value));
        }
        else if(firstPos == 4 && secondPos == 3)
        {
            return String.format("%.2f", knotsToMph(value))+" Knots"+mpsToBeaufort(knotsToMps(value));
        }
        else if(firstPos == 4 && secondPos == 4)
        {
            return value+" Knots"+mpsToBeaufort(knotsToMps(value));
        }
        else
        {
            return "Did not meet if statements";
        }

    }

    private String mpsToBeaufort(double value)
    {

        /*
        isnt very well localized
        fixing this will need to make CalculateActivity pass a context to make this object be
        able to get the extrnal stirngs?.

         */

        if(value < 0.3)
        {
            return "\nBeaufort: 0 - Calm";
        }
        else if(value > 0.3 && value < 1.5)
        {
            return "\nBeaufort: 1 - Light Air";
        }
        else if(value > 1.5 && value < 3.3 )
        {
            return "\nBeaufort: 2 - Light Breeze";
        }
        else if(value > 3.3 && value < 5.5)
        {
            return "\nBeaufort: 3 - Gentle Breeze";
        }
        else if(value > 5.5 && value < 8)
        {
            return "\nBeaufort: 4 - Moderate Breeze";
        }
        else if(value > 8 && value < 10.8)
        {
            return"\nBeaufort: 5 - Fresh Breeze";
        }
        else if(value > 10.8 && value < 13.9)
        {
            return "\nBeaufort: 6 - Strong Breeze";
        }
        else if(value > 13.9 && value < 17.2)
        {
            return "\nBeaufort: 7 - High Wind";
        }
        else if(value > 17.2 && value < 20.7)
        {
            return "\nBeaufort: 8 - Gale";
        }
        else if(value > 20.7 && value < 24.5)
        {
            return "\nBeaufort: 9 - Strong Gale";
        }
        else if(value > 24.5 && value < 28.4)
        {
            return "\nBeaufort: 10 - Storm";
        }
        else if(value > 28.4 && value < 32.6)
        {
            return "\nBeaufort: 11 - Violent Storm";
        }
        else
        {
            return "\nBeaufort: 12 - Hurricane";
        }


    }

    private double mpsToKmh(int value)
    {
        return value*3.6 ;
    }

    private double mpsToMph(int value)
    {
        return value*2.23694;
    }

    private double mpsToKnots(int value)
    {
        return value*1.943845;
    }

    private double kmhToMps(int value)
    {
        return (value*0.277778);
    }

    private double kmhToMph(int value)
    {
        return (value*0.621371);
    }

    private double kmhToKnots(int value)
    {
        return (value*0.539957);
    }

    private double mphToMps(int value)
    {
        return value*0.44704;
    }

    private double mphToKmh(int value)
    {
        return value*1.60934;
    }

    private double mphToKnots(int value)
    {
        return value*0.868976;
    }

    private double knotsToMps(int value)
    {
        return value*0.514444;
    }

    private double knotsToKmh(int value)
    {
        return value*1.852;
    }

    private double knotsToMph(int value)
    {
        return value*1.15078;
    }





    public String getType()
    {
        return null;
    }

    enum units
    {
        mps(R.string.MPS_string),kmh(R.string.KMH_string),mph(R.string.Mph_string),knots(R.string.knots_string);

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
