import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;


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
     * Split my self up into as many partitions as possible.
     */
    public void Partition()
    {
        if (!_notified)
            return;

        _find_partition(candidate -> _reverse_mapping(candidate),
                (combo, clear_value) ->_partition_set(clear_value, combo));

        _find_partition(position -> _pieces.Value(_piece_map[position]),
                (combo, clear_value) -> _partition_set(combo, clear_value));
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
     * @return A bitmap of potential pieces that candidate could be in, within this container.
     */
    private int _reverse_mapping(int candidate)
    {
        int res = 0;
        for (int i = 0; i < NUM_PIECES; i ++)
        {
            int v = _pieces.Value(_piece_map[i]);      // get the piece map
            if ((v & Utilities.Position(candidate).BINARY) != 0)
                res |= Utilities.Position(i).BINARY;
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

            _pieces.SetValue(map_pos, value);
            if (value != original_value)
                _pieces.Notify(map_pos, this);
        }
    }


    /**
     * Datasource could point to Utilities.Position(), or to the reverse mapping, or
     * to any other function that accepts an int between 1 and 9 and returns some
     * other int.
     *
     * @param dataSource The function that provides the bitmap data to process.
     * @param partitionFunction The function call to _partition_set
     */
    private void _find_partition(IntFunction<Integer> dataSource, BiConsumer<Integer, Integer> partitionFunction)
    {
        for (int combo = 1; combo < 511; combo ++)
        {
            int clear_value = 0;
            for (int position = 0; position < Utilities.NUM_POSITIONS; position ++)
                if ((combo & Utilities.Position(position).BINARY) != 0)
                    clear_value = clear_value | dataSource.apply(position);

            int num_elements = Utilities.CountHighs(combo);
            int num_numbers = Utilities.CountHighs(clear_value);

            if (num_elements == num_numbers)
                partitionFunction.accept(combo, clear_value);
            else if (num_numbers < num_elements)
                System.out.println ("Inconsistent state found.");
        }
    }
}
