package com.example.joel4.mobileassignment1wind;

/**
 * Created by joel4 on 23/10/2015.
 */
public interface ConverterInterface
{

    public String calcualte(int firstPos, int secondPos, int value);

    public String getType();

    enum convertType
    {
        distance(R.string.distance), speed(R.string.speed), temperature(R.string.temp), weight(R.string.weight) ;

        /*
            if someone wnt to add new things t convert, they must add it to this enum
         */
        private int resourceID ;

        convertType(int id)
        {
            resourceID = id ;
        }

        public int getID()
        {
            return resourceID;
        /*
            calling a static reference to context
         */
        }

    }

}
