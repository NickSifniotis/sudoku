/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 */
public class Board
{
    private final int NUM_CONTAINERS = 9;
    private Pieces _pieces;
    private Row[] _rows;
    private Column[] _cols;
    private Block[] _blocks;


    public Board()
    {
        _pieces = new Pieces();
        _rows = new Row[NUM_CONTAINERS];
        _cols = new Column[NUM_CONTAINERS];
        _blocks = new Block[NUM_CONTAINERS];

        for (int iterator = 0; iterator < 9; iterator ++)
        {
            _rows[iterator] = new Row(iterator, _pieces);
            _cols[iterator] = new Column(iterator, _pieces);
            _blocks[iterator] = new Block(iterator, _pieces);
        }
    }


    /**
     * Displays the board the traditional way - rows first.
     */
    public void DisplayBoard()
    {
        DisplayBoard(DisplayBoardOrientation.ROWS);
    }


    /**
     * Displays the current state of the board.
     *
     * @param orientation Whether to print by rows (the traditional view), cols or blocks.
     */
    public void DisplayBoard(DisplayBoardOrientation orientation)
    {
        Container[] displayMe = null;
        switch (orientation)
        {
            case COLUMNS:
                displayMe = _cols;
                break;
            case ROWS:
                displayMe = _rows;
                break;
            case BLOCKS:
                displayMe = _blocks;
                break;
        }

        for (Container c: displayMe)
            System.out.println (c.ToString());
    }
}
