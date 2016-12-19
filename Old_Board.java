import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Sifniotis on 27/11/16.
 *
 * Holds the Sudoku game state.
 */
public class Old_Board
{
    public boolean notify_changes [];

    public static final int [] POSITION = { 0x1, 0x2, 0x4, 0x8, 0x10, 0x20, 0x40, 0x80, 0x100 };
    private static final int [] INVERSE_POSITION = {};

    private final int NUM_PIECES = 81;
    private final int BITMAP_DEFAULT = 0x1FF;

    public int [] piece_bitmaps;
    public int [] [] set_mapping;
    public int [] [] inverse_mapping;

//    public int [] [] rowCells = {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}, {4, 5, 6}, {4, 5, 6}, {4, 5, 6}, {7, 8, 9}, {7, 8, 9}, {7, 8, 9}};
    public int [] [] colCells = {{0, 3, 6}, {0, 3, 6}, {0, 3, 6}, {1, 4, 7}, {1, 4, 7}, {1, 4, 7}, {2, 5, 8}, {2, 5, 8}, {2, 5, 8}};

    public Old_Board()
    {
        notify_changes = new boolean[27];


        piece_bitmaps = new int[NUM_PIECES];
        for (int i = 0; i < 81; i ++)
            piece_bitmaps[i] = BITMAP_DEFAULT;

        set_mapping = new int[27][];
        for (int i = 0; i < 27; i ++)
            set_mapping[i] = new int [9];

        // rows
        for (int row = 0; row < 9; row ++)
            for (int innerIterator = 0; innerIterator < 9; innerIterator ++)
                set_mapping[row][innerIterator] = innerIterator + (9 * row);

        // cols
        for (int col = 0; col < 9; col ++)
            for (int innerIterator = 0; innerIterator < 9; innerIterator ++)
                set_mapping[col + 9][innerIterator] = col + (9 * innerIterator);

        // cells
        int set_id = 18;
        for (int xcell = 0; xcell < 3; xcell ++)
        {
            for (int ycell = 0; ycell < 3; ycell ++)
            {
                int counter = 0;
                for (int innerx = 0; innerx < 3; innerx ++)
                    for (int innery = 0; innery < 3; innery ++)
                    {
                        set_mapping[set_id][counter] = (innery + (9 * innerx)) + (3 * ycell) + (27 * xcell);
                        counter ++;
                    }

                set_id ++;
            }
        }

        // the reverse mapping, for notifying changes in a position to rows, cols and cells
        // Each 'piece' (81 on the sudoku board) is associated with a row, column and cell.
        // The inverse mapping array associates the rowId, colId and cellId for each of those
        // 81 positions
        inverse_mapping = new int[81][];
        for (int i = 0; i < 81; i ++)
            inverse_mapping[i] = new int[4];        // position 4 is a very very dodgy workaround

        for (int i = 0; i < 27; i ++)
            for (int j = 0; j < 9; j ++)
            {
                int v = set_mapping[i][j];
                inverse_mapping[v][inverse_mapping[v][3]] = i;
                inverse_mapping[v][3] ++;
            }
    }

    public void PrintBoardState()
    {
        for (int i = 0; i < 9; i ++)
        {
            for (int j = 0; j < 9; j ++)
            {
                int n = piece_bitmaps [set_mapping [i][j]];

                if (Utilities.CountHighs(n) == 1)
                {
                    for (int state = 0; state < 9; state ++)
                        if (Utilities.POSITION[state] == n)
                            System.out.printf ("%2d  ", state + 1);
                }
                else
                    System.out.print ("<" + Utilities.CountHighs(n) + "> ");
            }
            System.out.println();
        }
    }


    /**
     * Tricky stuff. From each element in set set_number, remove all entries from values if
     * the element does not appear in the partition set, and remove all entries not in values
     * if the element does appear in the partition set.
     *
     * @param set_number The set number (0 - 26) to apply this function to.
     * @param partition The partition bitmap. High values represent elements within the partition.
     * @param values The values bitmap. Partition elements may only contain these values; non-partition
     *               elements may contain anything but these values.
     */
    public void PartitionSet(int set_number, int partition, int values)
    {
        int inverse_values = ~values;
        for (int i = 0; i < 9; i ++)
        {
            int map_pos = set_mapping[set_number][i];
            int value;

            if ((partition & POSITION[i]) == 0)
            {
                // not in the partition, so remove values
                value = piece_bitmaps[map_pos] & inverse_values;
            }
            else
            {
                value = piece_bitmaps[map_pos] & values;
            }

            if (value != piece_bitmaps[map_pos])
            {
                // this means that a position value has changed, and that any sets that contain this position
                // (except the current state) need to be notified of a change.

                for (int j = 0; j < 3; j ++)
                {
                    int otherSetNumber = inverse_mapping[map_pos][j];
                    if (otherSetNumber != set_number && !notify_changes[otherSetNumber])
                    {
                        System.out.println("Noting changes to " + Utilities.SetToString(otherSetNumber));
                        notify_changes[otherSetNumber] = true;
                    }
                }
                piece_bitmaps[map_pos] = value;
            }
        }
    }


    /**
     * Generates the bitmap matrix used in the calculation of row/col and block asymmetry.
     *
     * @param isRow True if processing for rows, false if processing for cols.
     * @param firstRowCol The id number of the first row / column to process.
     * @return An int [3] [3] matrix containing the compound bitmaps for all cells covered in
     * the row/col and cells being considered.
     */
    public int [] [] GenerateAsymmetricalMatrix (boolean isRow, int firstRowCol)
    {
        int [] [] res = new int[3][];
        int [] rowCols = (isRow) ? Utilities.ROWS : Utilities.COLS;

        for (int i = 0; i < 3; i ++)
            res [i] = new int [3];

        for (int i = 0; i < 3; i ++)
        {
            int iterator = 0;
            for (int j = 0; j < 3; j ++)
            {
                System.out.println ("On i = " + i + " and j = " + j);
                for (int k = 0; k < 3; k ++)
                {
                    System.out.println ("Setting " + (firstRowCol + i) + ", " + iterator + " maps to " + set_mapping[rowCols[firstRowCol + i]][iterator] + " " + Utilities.IntToBinaryString(piece_bitmaps[set_mapping[rowCols[firstRowCol + i]][iterator]]));
                    res[j][i] |= piece_bitmaps[set_mapping[rowCols[firstRowCol + i]][iterator]];
                    iterator ++;
                }
            }
        }

        return res;
    }


    public List<Integer> GetPieces(int spaceId)
    {
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < 9; i ++)
            res.add(set_mapping[spaceId][i]);

        return res;
    }

    /**
     * Return a list of every piece ID that's found in both the given space
     * and the other space that intersects with it.
     *
     * @param spaceId The ID of the row, col or cell to interrogate.
     * @param intersectionId The ID of the other row, col or cell that intersects with spaceId
     * @return A list of piece IDs that are shared between both spaces.
     */
    public List<Integer> GetPieces(int spaceId, int intersectionId)
    {
        List<Integer> workingList = GetPieces(spaceId);
        List<Integer> res = new ArrayList<>();

        for (int value: workingList)
            for (int i = 0; i < 9; i ++)
                if (value == set_mapping[intersectionId][i])
                    res.add(value);
        return res;
    }
}
