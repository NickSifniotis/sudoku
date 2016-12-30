/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * An abstraction of a container class that extends across one diimension.
 * Concrete instantiations of these would be Rows and Columns.
 */
public abstract class Columnar extends Container
{
    public Columnar(int[] map, Pieces pieces, int id)
    {
        super(map, pieces, id);
    }

    public Columnar(Pieces pieces, int id)
    {
        super(pieces, id);
    }


    public String ToString()
    {
        String res = "";

        for (int i = 0; i < NUM_PIECES; i ++)
        {
            String colour = (Utilities.CountHighs(_pieces.Value(_piece_map[i])) == 1)
                    ? ColorCodes.YELLOW
                    : ColorCodes.GREEN;

            if (!res.equals(""))
                res += ", ";
            res += colour + _pieces.ToString(_piece_map[i]);
        }

        return ColorCodes.WHITE + "[ " + res + ColorCodes.WHITE + " ]";
    }
}
