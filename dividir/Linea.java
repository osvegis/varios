package dividir;

import java.awt.*;

public class Linea extends Figura
{
protected int x1, y1, x2, y2;

public Linea(int x1, int y1, int x2, int y2)
{
    if(x1 < x2)
    {
        x = x1;
        w = x2 - x1 + 1;
    }
    else
    {
        x = x2;
        w = x1 - x2 + 1;
    }
    
    if(y1 < y2)
    {
        y = y1;
        h = y2 - y1 + 1;
    }
    else
    {
        y = y2;
        h = y1 - y2 + 1;
    }

    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
}

@Override public void paint(Graphics g)
{
    Graphics2D g2 = (Graphics2D)g;
    g2.setStroke(new BasicStroke(1f));
    
    g.setColor(Color.black);
    g.drawLine(x1, y1, x2, y2);
}

} // Linea
