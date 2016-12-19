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
        return "Row " + MyId() + ": " + super.ToString();
    }


    /**
     * Something that only those sneaky fucker rows can do - set up the initial configuration
     * of the board.
     *
     * @param values A string containing a sudoku configuration.
     */
    public void SetInitialValues(String values)
    {
        System.out.println ("Setting row " + MyId() + " to " + values);

        for (int i = 0; i < NUM_PIECES; i ++)
        {
            char c = values.charAt(i);
            if (c >= '0' && c <= '9')
            {
                int binary = 0;
                for (Position position: _positions)
                    if (position.DECIMAL.equals(String.valueOf(c)))
                        binary |= position.BINARY;

                _pieces.SetValue(_piece_map[i], binary);
                _pieces.Notify(_piece_map[i]);
                System.out.println (_piece_map[i]);
            }
        }
    }
}
