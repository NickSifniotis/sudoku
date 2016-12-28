/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * The data collection class that holds the state of a sudoku board.
 */
public class Pieces
{
    private static final int   NUM_PIECES = 81;
    private Piece[] _pieces;

    public Pieces()
    {
        _pieces = new Piece[NUM_PIECES];
        for (int i = 0; i < NUM_PIECES; i ++)
            _pieces[i] = new Piece();
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


    public void Notify(int pieceNum, Container container)
    {
        _pieces[pieceNum].Notify(container);
    }

    public void RegisterContainer(Container container, int pieceNumber)
    {
        _pieces[pieceNumber].RegisterContainer(container);
    }


    public String ToString(int piece)
    {
        return _pieces[piece].ToString();
    }
}
