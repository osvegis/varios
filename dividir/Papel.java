package dividir;

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Papel extends JComponent
{
private ArrayList<Figura> figuras = new ArrayList<>();

public void clear()
{
    figuras.clear();
}

public void add(Figura f)
{
    figuras.add(f);
}

@Override public Dimension getMinimumSize()
{
    return new Dimension(800, 600);
}

@Override public Dimension getPreferredSize()
{
    return getMinimumSize();
}

@Override protected void paintComponent(Graphics g)
{
    Graphics2D g2 = (Graphics2D)g;
    
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    limpiar(g);
    ajustarDibujo(g);
    dibujarFiguras(g);
}

private void limpiar(Graphics g)
{
    Rectangle r = getBounds();
    g.setColor(Color.white);
    g.fillRect(0, 0, r.width, r.height);
}

private void ajustarDibujo(Graphics g)
{
    if(figuras.isEmpty())
        return; //..................................................RETURN

    int x = Integer.MAX_VALUE,
        y = Integer.MAX_VALUE,
        w = Integer.MIN_VALUE,
        h = Integer.MIN_VALUE;

    for(Figura f : figuras)
    {
        x = Math.min(x, f.x);
        y = Math.min(y, f.y);
        w = Math.max(w, f.x + f.w - x);
        h = Math.max(h, f.y + f.h - y);
    }

    x -= 4;
    y -= 4;
    w += 8;
    h += 8;

    Rectangle r = getBounds();

    double s = Math.min((double)r.width / (double)w,
                        (double)r.height / (double)h);

    ((Graphics2D)g).translate(-x * s, -y * s);
    ((Graphics2D)g).scale(s, s);

    ((Graphics2D)g).translate((int)((r.width / s - w) / 2.),
                              (int)((r.height / s - h) / 2.));
}

private void dibujarFiguras(Graphics g)
{
    for(Figura f : figuras)
        f.paint(g);
}

} // Papel
