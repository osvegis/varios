package dividir;

import java.awt.*;
import java.math.*;
import javax.swing.*;
import javax.swing.border.*;

public class Main extends JFrame
{
private JTextField campoDividendo = new JTextField(60),
                   campoDivisor   = new JTextField(60);
private JButton    botonCalcular  = new JButton("  Calcular  ");

private Papel papel = new Papel();

public Main()
{
    super("Dividir");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel north = new JPanel(new BorderLayout());
    north.setBorder(new EmptyBorder(8,8,8,8));
    north.add(dibujarDatos(), BorderLayout.CENTER);
    north.add(botonCalcular,  BorderLayout.EAST);

    JPanel root = new JPanel(new BorderLayout());
    root.add(north, BorderLayout.NORTH);
    root.add(papel, BorderLayout.CENTER);
    getContentPane().add(root);

    pack();
    setMinimumSize(getSize());

    botonCalcular.addActionListener(e -> run());
}

private JPanel dibujarDatos()
{
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel p = new JPanel(layout);

    JComponent c = new JLabel("Dividendo: ", SwingConstants.RIGHT);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.BOTH;
    layout.setConstraints(c, gbc);
    p.add(c);

    gbc.gridx++;
    layout.setConstraints(campoDividendo, gbc);
    p.add(campoDividendo);

    c = new JLabel("Divisor: ", SwingConstants.RIGHT);
    gbc.gridx = 0;
    gbc.gridy++;
    layout.setConstraints(c, gbc);
    p.add(c);

    gbc.gridx++;
    layout.setConstraints(campoDivisor, gbc);
    p.add(campoDivisor);

    gbc.gridx++;
    layout.setConstraints(botonCalcular, gbc);
    p.add(botonCalcular);
    
    return p;
}

private void run()
{
    papel.clear();

    Numero dividendo = null,
           divisor   = null;
    try
    {
        dividendo = getNumero(campoDividendo);
        divisor   = getNumero(campoDivisor);
        dividir(dividendo, divisor);
    }
    catch(AssertionError | Exception ex)
    {
        ex.printStackTrace();

        if(dividendo == null)
            error("Dividendo erróneo");
        else if(divisor == null)
            error("Divisor erróneo");
        else
            error(ex.toString());
    }
    finally
    {
        papel.repaint();
    }
}

private Numero getNumero(JTextField field)
{
    BigInteger n = new BigInteger(field.getText().trim());
    
    if(n.signum() <= 0)
        throw new RuntimeException("Sólo se admiten números positivos.");

    return new Numero(n.toString());
}

private void dividir(Numero dividendo, Numero divisor)
{
    dividendo.derecha(divisor, 2);

    papel.add(dividendo);
    papel.add(divisor);
    papel.add(divisor.lineaIzquierda());
    Linea lineaInferior = divisor.lineaDebajo();
    papel.add(lineaInferior);

    int dn = dividendo.length(),
        sn = divisor.length();

    Numero resto    = sn == 1 ? new Numero("0") : dividendo.sub(sn),
           cociente = new Numero("");

    Numero cursor = new Numero("0");
    dividendo.debajo(cursor, 0);

    divisor.debajo(cociente, 0);
    papel.add(cociente);

    for(int i = sn; i <= dn; i++)
    {
        resto.append(dividendo.big(i));
        int c;

        for(c = 9; c >= 0; c--)
        {
            BigInteger r = resto.val().subtract(BigInteger.valueOf(c)
                           .multiply(divisor.val()));

            if(r.signum() >= 0 && (c > 0 || cociente.length() > 0))
            {
                cociente.append(c);
                resto = new Numero(r.toString());
                
                while(resto.length() < sn)
                    resto.insert(0);

                resto.x = cursor.x;
                resto.y = cursor.y;
                papel.add(resto);
                cursor.debajo(cursor, 1);
                break;
            }
        }
        
        if(c < 0)
            cursor.derecha(cursor, 0);
    }

    if(cociente.length() == 0)
        cociente.append(0);

    int r = resto.w / resto.length();

    papel.add(new Linea(resto.x + resto.w - r,
                        resto.y + resto.h * 3 / 2,
                        resto.x + resto.w + r,
                        resto.y + resto.h - r));
    
    if(lineaInferior.w < cociente.x + cociente.w - lineaInferior.x)
    {
        lineaInferior.w = cociente.x + cociente.w - lineaInferior.x;
        lineaInferior.x2 = lineaInferior.x1 + lineaInferior.w - 1;
    }

    if(0 != dividendo.val().compareTo(
        cociente.val().multiply(divisor.val()).add(resto.val())))
    {
        throw new AssertionError("La prueba ha fallado.");
    }
}

private void error(String mensaje)
{
    JOptionPane.showMessageDialog(this, mensaje, "Dividir",
                                  JOptionPane.ERROR_MESSAGE);
}

private static void cambiarPropiedades()
{
    Font labelFont = new Font("Dialog",     Font.PLAIN, 18),
         textFont  = new Font("Monospaced", Font.PLAIN, 18);
    
    UIManager.put("Button.font",              labelFont);
    UIManager.put("Label.font",               labelFont);
    UIManager.put("TextArea.font",            textFont);
    UIManager.put("TextField.font",           textFont);
    UIManager.put("TextPane.font",            labelFont);
}

public static void main(String[] args)
{
    EventQueue.invokeLater(() ->
    {
        cambiarPropiedades();
        Main dividir = new Main();
        dividir.setVisible(true);
    });
}

} // Dividir
