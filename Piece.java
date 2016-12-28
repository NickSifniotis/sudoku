import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nick Sifniotis on 19/12/16.
 *
 * A single piece on the sudoku board.
 */
public class Piece
{
    private int _value;
    private List<Container> _containers;


    public Piece()
    {
        // Generate the initial value
        _value = 0;
        for (int position = 0; position < Utilities.NUM_POSITIONS; position ++)
            _value |= Utilities.Position(position).BINARY;

        _containers = new ArrayList<>();
    }


    public int Value()
    {
        return _value;
    }

    public void SetValue(int value)
    {
        _value = value;
    }

    public void RegisterContainer (Container container)
    {
        _containers.add(container);
    }


    /**
     * Notify all the containers associated with this piece that there has been an update (or something)
     * However, do not notify the container that has caused this notification.
     *
     * @param notifier The container causing the notification.
     */
    public void Notify(Container notifier)
    {
        _containers.forEach(c ->
        {
            if (c != notifier)
                c.SetNotify();
        });
    }


    /**
     * Notify all containers associated with this piece that they need to update themselves.
     */
    public void Notify()
    {
        this.Notify(null);
    }


    public String ToString()
    {
        String res = "";
        for (int position = 0; position < Utilities.NUM_POSITIONS; position ++)
            res += ((_value & Utilities.Position(position).BINARY) == 0 ? "." : Utilities.Position(position).DECIMAL);

        return res;
    }
}
