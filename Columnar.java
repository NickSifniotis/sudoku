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

        for (int i = 0; i < 9; i ++)
        {
            if (!res.equals(""))
                res += ", ";
            res += _pieces.ToString(i);
        }

        return res;
    }
}
