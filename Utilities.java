/**
 * Created by Nick Sifniotis on 3/12/16.
 *
 * Handy class containing a bunch of static functions that can be used across the entire application.
 */
public class Utilities
{
    public static final int NUM_POSITIONS = 9;
    public static final int [] POSITION = { 0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, 0x80, 0x100 };
    public static final int [] [] ROW_TO_CELL_MAP = { {18, 19, 20}, {21, 22, 23}, {24, 25, 26} };
    public static final int [] [] COL_TO_CELL_MAP = { {18, 21, 24}, {19, 22, 25}, {20, 23, 26} };

    public static final int [] ROWS  = {  0,  1,  2,  3,  4,  5,  6,  7,  8 };
    public static final int [] COLS  = {  9, 10, 11, 12, 13, 14, 15, 16, 17 };
    public static final int [] CELLS = { 18, 19, 20, 21, 22, 23, 24, 25, 26 };

    /**
     * Private static members, to enforce read-only access to this data.
     */
    private static boolean initialised = false;
    private static Position [] _positions;

    /**
     * Converts an integer n into a string of its binary representation.
     *
     * @param n The number to convert.
     * @return A string containing n expressed in base two.
     */
    public static String IntToBinaryString (int n)
    {
        String res = "";
        for (int position : POSITION)
            res += ((n & position) == 0 ? "0" : "1");

        return res;
    }


    /**
     * Counts the number of bits in value_to_check which are set.
     *
     * @param value_to_check The value to check.
     * @return An integer count of the number of high bits in value_to_check.
     */
    public static int CountHighs(int value_to_check)
    {
        int res = 0;

        for (int position: Utilities.POSITION)
            if ((value_to_check & position) != 0)
                res ++;

        return res;
    }


    /**
     * Converts a given set number (range 0 - 26) into a string representation
     * suitable for output to the console.
     *
     * @param set_num The set number to convert.
     * @return The stringy result.
     */
    public static String SetToString(int set_num)
    {
        String[] descriptors = {"row", "column", "block"};
        return descriptors[set_num / 9] + " " + ((set_num % 9) + 1);
    }


    public static void PrintAsymMatrix (int [] [] matrix)
    {
        for (int colCnt = 0; colCnt < 3; colCnt ++)
        {
            for (int cellCnt = 0; cellCnt < 3; cellCnt ++)
            {
                System.out.print (Utilities.IntToBinaryString(matrix [colCnt][cellCnt]) + " ");
            }
            System.out.println();
        }
    }


    /**
     * Accessor methods for the dynamically generated read only data.
     */
    public static Position Position(int positionNum)
    {
        _initialise();

        return _positions[positionNum];
    }


    /**
     * Private initialisation method to set the global read-only data.
     */
    private static void _initialise()
    {
        if (initialised)
            return;

        _positions = new Position[NUM_POSITIONS];
        for (int position = 0; position < NUM_POSITIONS; position ++)
            _positions[position] = new Position(0x1 << position, position + 1);

        initialised = true;
    }
}
