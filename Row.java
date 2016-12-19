/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * A sudoku row of nine.
 */
public class Row extends Columnar
{
    public Row(int rowNumber, Pieces pieces)
    {
        super (pieces, rowNumber);
        for (int iterator = 0; iterator < 9; iterator ++)
        {
            int piece_number = iterator + (9 * rowNumber);
            _piece_map[iterator] = piece_number;
            _pieces.RegisterContainer(this, piece_number);
        }
    }

    @Override
    public String ToString()
    {
        return "Row " + String.valueOf(_my_id) + ": " + super.ToString();
    }
}
