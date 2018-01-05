import java.awt.*;

public class ColorString
{
    public Color getColor ()
    {
        return color;
    }

    private String string;
    private Color color;

    public ColorString (String str, Color c)
    {
        string = str;
        color = c;
    }

    @Override
    public String toString ()
    {
        return string;
    }
}
