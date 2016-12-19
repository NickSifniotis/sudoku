import java.util.Arrays;

/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * Base class for the sudoku 'set' of nine positions.
 */
public abstract class Container
{
    protected boolean _notified;
    protected int _my_id;
    protected Pieces _pieces;
    protected int[] _piece_map;
    protected final int NUM_PIECES = 9;


    public Container()
    {
        _piece_map = new int[NUM_PIECES];
    }


    public Container(int[] piece_map, Pieces pieces, int id)
    {
        this(pieces, id);
        _piece_map = Arrays.copyOf(piece_map, NUM_PIECES);
    }

    public Container(Pieces pieces, int id)
    {
        this();
        _pieces = pieces;
        _my_id = id;
    }

    public abstract String ToString();

    public boolean Notified()
    {
        return _notified;
    }

    public void SetNotify()
    {
        _notified = true;
    }
    
    public void ClearNotify()
    {
        _notified = false;
    }


    /**
     * Returns a stringy representation of this container's ID within its container set.
     * Note the +1 because zero based indexing means nothing in the context of solving sudoku puzzles.
     *
     * @return MyId as a string.
     */
    public String MyId()
    {
        return String.valueOf(_my_id + 1);
    }
}
