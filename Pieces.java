import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * The data collection class that holds the state of a sudoku board.
 */
public class Pieces
{
    private static final int   NUM_PIECES = 81;
    private Piece[] _pieces;
    private List<Position> _positions;

    public Pieces()
    {
        _positions = new ArrayList<>();
        for (int i = 0; i < 9; i ++)
            _positions.add(new Position(0x1 << i, i + 1));

        _pieces = new Piece[NUM_PIECES];
        for (int i = 0; i < NUM_PIECES; i ++)
            _pieces[i] = new Piece(_positions);
    }

    public int Value(int pieceNum)
    {
        return _pieces[pieceNum].Value();
    }


    public void SetValue(int pieceNum, int value)
    {
        _pieces[pieceNum].SetValue(value);
    }


    public void Notify(int pieceNum)
    {
        _pieces[pieceNum].Notify();
    }


    public void RegisterContainer(Container container, int pieceNumber)
    {
        _pieces[pieceNumber].RegisterContainer(container);
    }


    public String ToString(int piece)
    {
        return _pieces[piece].ToString();
    }

    public List<Position> Positions()
    {
        return _positions;
    }
}
