import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class vista extends JFrame implements ActionListener
{
    JCombosEMC combo1;
    JButton btn;
    public vista() 
    {
        super("mi app");
        interfaz();
        controller();
    }
	private void controller() 
    {
        btn.addActionListener(this);
	}
	public void interfaz() 
    {
        setSize(400, 400);
        setMinimumSize(new Dimension(400, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        Inicioseccion inicio1 = new Inicioseccion("empresa", this);
        lista lista1 = new lista("Estados", inicio1.getStmt());
        lista lista2 = new lista("Marcas", inicio1.getStmt());
        combo1 = new JCombosEMC(inicio1.getStmt());
        btn = new JButton("Regresa");
        add(lista1);
        add(lista2);
        add(combo1);
        add(btn);
        setVisible(true);
        validate();
    }
	@Override
	public void actionPerformed(ActionEvent e) 
    {
        System.out.println(combo1.getSelect()[0][0]);
        System.out.println(combo1.getSelect()[0][1]);
        System.out.println(combo1.getSelect()[1][0]);
        System.out.println(combo1.getSelect()[1][1]);
        System.out.println(combo1.getSelect()[2][0]);
        System.out.println(combo1.getSelect()[2][1]);
	}
}