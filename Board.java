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
     * Sets the initial configuration of the sudoku game.
     *
     * @param initial An array of strings each representing one row of the game.
     */
    public void InitialConfiguration(String[] initial)
    {
        for (int i = 0; i < NUM_CONTAINERS; i ++)
            _rows[i].SetInitialValues(initial[i]);
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


    /**
     * Iterates through all the containers on the board, and updates any that have
     * received a notification of a state change.
     *
     * @return True if any container had received a notification, false otherwise.
     */
    public boolean PartitionContainers()
    {
        boolean res;

        res  = _partition_containers(_rows);
        res |= _partition_containers(_cols);
        res |= _partition_containers(_blocks);

        return res;
    }


    /**
     * Iterates through every container in set, and updating them if they had been
     * notified of a change.
     *
     * @param set The set of containers to update.
     * @return True if one or more were updated, false if none were.
     */
    private boolean _partition_containers(Container[] set)
    {
        boolean res = false;

        for (int i = 0; i < NUM_CONTAINERS; i ++)
        {
            if (set[i].Notified())
            {
                res = true;
                set[i].Partition();
                set[i].ClearNotify();
            }
        }

        return res;
    }
}
