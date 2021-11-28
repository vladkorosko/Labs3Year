package org.example.lab3;

public class Virtual2Physical
{
    public static int pageNum ( long memoryAddress , int numberOfPages , long block )
    {
        int i;
        long high;
        long low;

        for (i = 0; i <= numberOfPages; i++)
        {
            low = block * i;
            high = block * ( i + 1 );
            if ( low <= memoryAddress && memoryAddress < high )
            {
                return i;
            }
        }
        return -1;
    }
}
