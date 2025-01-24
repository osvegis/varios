package dividir;

import java.math.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;

public class Numero extends Figura
{
private String str;
public static final Font font = new Font("Monospaced", Font.PLAIN, 16);

private static final FontRenderContext
        frc = new FontRenderContext(null, true, true);

public Numero(String s)
{
    str = s;
    Rectangle2D r = font.getStringBounds(str, frc);
    w = (int)r.getWidth();
    h = (int)r.getHeight();
}

public Numero(Numero n, int digito)
{
    this(n.str + digito);
    x = n.x;
    y = n.y;
}

public void append(int digito)
{
    str += digito;
    Rectangle2D r = font.getStringBounds(str, frc);
    w = (int)r.getWidth();
    h = (int)r.getHeight();
}

public void insert(int digito)
{
    str = digito + str;
    Rectangle2D r = font.getStringBounds(str, frc);
    w = (int)r.getWidth();
    h = (int)r.getHeight();
}

public Numero sub(int n)
{
    return new Numero(str.substring(0, n - 1));
}

public int big(int i)
{
    return str.charAt(i - 1) - '0';
}

public int get(int i)
{
    return str.charAt(str.length() - i - 1) - '0';
}

public BigInteger val()
{
    return new BigInteger(str);
}

public int length()
{
    return str.length();
}

public void derecha(Numero n, int espacios)
{
    n.x = x + w + (w * espacios) / length();
    n.y = y;
}

public void debajo(Numero n, int espacios)
{
    n.x = x + (w * espacios) / length();
    n.y = y + h;
}

public Linea lineaIzquierda()
{
    return new Linea(x - 5, y, x - 5, y + h);
}

public Linea lineaDebajo()
{
    return new Linea(x - 5, y + h, x + w + 10, y + h);
}

@Override public void paint(Graphics g)
{
    g.setColor(Color.red);
    g.setColor(Color.black);
    g.setFont(font);

    int d = g.getFontMetrics().getDescent() - 1;
    GlyphVector v = g.getFont().createGlyphVector(frc, str);
    ((Graphics2D)g).drawGlyphVector(v, x, y + h - d);
}

@Override public String toString()
{
    return str;
}

} // Numero
