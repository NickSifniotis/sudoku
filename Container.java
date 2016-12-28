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
        _notified = false;
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


    /**
     * The values bitmap that is stored in the Pieces structure represents the possible candidates on any given
     * piece. This function inverts that, returning an array of bitmaps that represent which positions
     * each candidate could potentially be in.
     *
     * @return An array of length Positions.length() ints
     */
    private int[] _reverse_mapping()
    {
        int num_positions = Utilities.NUM_POSITIONS;

        int [] res = new int[num_positions];
        for (int i = 0; i < NUM_PIECES; i ++)
        {
            int v = _pieces.Value(_piece_map[i]);      // get the piece map
            for (int j = 0; j < num_positions; j++)
                if ((v & Utilities.Position(j).BINARY) != 0)
                    res[j] |= Utilities.Position(i).BINARY;
        }

        return res;
    }


    /**
     * Tricky stuff. From each element in this set, remove all entries from values if
     * the element does not appear in the partition set, and remove all entries not in values
     * if the element does appear in the partition set.
     *
     * @param partition The partition bitmap. High values represent elements within the partition.
     * @param values The values bitmap. Partition elements may only contain these values; non-partition
     *               elements may contain anything but these values.
     */
    private void _partition_set(int partition, int values)
    {
        int inverse_values = ~values;
        for (int i = 0; i < 9; i ++)
        {
            int map_pos = _piece_map[i];
            int original_value = _pieces.Value(map_pos);
            int value = original_value &
                    (((partition & Utilities.Position(i).BINARY) == 0)
                            ? inverse_values
                            : values);

            if (value != original_value)
                _pieces.Notify(map_pos, this);
        }
    }
}
