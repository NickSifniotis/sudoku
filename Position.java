/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * A class of data that associates the binary representation of a candidate under consideration
 * with the stringy equivalent for display purposes.
 */
public class Position
{
    public int BINARY;
    public String DECIMAL;

    public Position(int binary, int decimal)
    {
        BINARY  = binary;
        DECIMAL = String.valueOf(decimal);
    }
}
