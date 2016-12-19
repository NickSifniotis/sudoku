/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * Represents a 'block' or 3x3 container on the sudoku board.
 */
public class Block extends Container
{
    public Block(int blockNumber, Pieces pieces)
    {
        super(pieces, blockNumber);

        int xCell = blockNumber / 3;
        int yCell = blockNumber % 3;

        int counter = 0;
        for (int innerX = 0; innerX < 3; innerX ++)
            for (int innerY = 0; innerY < 3; innerY ++)
            {
                int piece_number = (innerY + (9 * innerX)) + (3 * yCell) + (27 * xCell);
                _piece_map[counter] = piece_number;
                _pieces.RegisterContainer(this, piece_number);
                counter ++;
            }
    }


    @Override
    public String ToString()
    {
        String res = "";
        int counter = 0;

        for (int i = 0; i < 3; i ++)
        {
            String substring = "";
            for (int j = 0; j < 3; j ++)
            {
                if (!substring.equals(""))
                    substring += ", ";
                substring += _pieces.ToString(_piece_map[counter]);
                counter ++;
            }
            substring = "[ " + substring + " ]";

            if (!res.equals(""))
                res += " ";
            res += substring;
        }

        return "Block " + MyId() + ": " + res;
    }
}
