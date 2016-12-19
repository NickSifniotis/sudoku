/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * A sudoku column of nine.
 */
public class Column extends Columnar
{
    public Column (int colNumber, Pieces pieces)
    {
        super (pieces, colNumber);
        for (int iterator = 0; iterator < 9; iterator ++)
        {
            int piece_number = (9 * iterator) + colNumber;
            _piece_map[iterator] = piece_number;
            _pieces.RegisterContainer(this, piece_number);
        }
    }

    @Override
    public String ToString()
    {
        return "Col " + String.valueOf(_my_id) + ": " + super.ToString();
    }
}
