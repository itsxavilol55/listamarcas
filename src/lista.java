import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class lista extends JPanel implements KeyListener, ActionListener, ComponentListener
{
    private String tabla;
    private Statement stmt;
    private JTextField buscar;
    private JPanel panelista;
    private JButton btnLimpiar;
    private JScrollPane sp;
    private String NameColumn;
    public lista(
        String tabl,
        Statement stmt)
    {
        tabla = tabl;
        this.stmt = stmt;
        interfaz();
        controller();
    }
    private void interfaz()
    {
        setSize(500, 500);
        setLayout(null);
        buscar = new JTextField();
        panelista = new JPanel();
        btnLimpiar = new JButton("Limpiar");
        sp = new JScrollPane();
        panelista.setLayout(new BoxLayout(panelista, BoxLayout.Y_AXIS));
        buscar.setBounds(getAncho(this, 0.1), getAltoo(this, 0.05), getAncho(this, 0.8), 30);
        panelista.setBounds(getAncho(this, 0.1), getAltoo(this, 0.1) + 30, getAncho(this, 0.8), getAltoo(this, 0.6));
        sp.setBounds(getAncho(this, 0.1), getAltoo(this, 0.1) + 30, getAncho(this, 0.8), getAltoo(this, 0.6));
        btnLimpiar.setBounds(getAncho(this, 0.1), getAltoo(this, 0.7) + 30, getAncho(this, 0.4), 30);
        try
        {
            ResultSet resultSet = stmt.executeQuery("select * from " + tabla + " order by 2");
            NameColumn = resultSet.getMetaData().getColumnName(2);
            imprime(resultSet);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        sp.setViewportView(panelista);
        add(buscar);
        add(sp);
        add(btnLimpiar);
        setVisible(true);
    }
    private void imprime(ResultSet resultSet)
    {
        panelista.removeAll();
        try
        {
            while (resultSet.next())
            {
                JPanel panelaux = new JPanel();
                panelaux.setLayout(new BoxLayout(panelaux, BoxLayout.X_AXIS));
                panelaux.setAlignmentX(LEFT_ALIGNMENT);
                panelaux.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 10));
                panelaux.add(new JCheckBox());
                panelaux.add(new JLabel("" + resultSet.getString(2)));// siempre agarra la columna 2 de la tabla
                panelista.add(panelaux);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        panelista.revalidate();
        panelista.repaint();
        panelista.validate();
    }
    public ArrayList<String[]> getselect()
    {
        ArrayList<String[]> selects = new ArrayList<String[]>();
        int total = panelista.getComponentCount();
        for (int i = 0; i < total; i++)
        {
            JPanel aux = (JPanel) panelista.getComponent(i);
            JCheckBox cb = (JCheckBox) aux.getComponent(0);
            if (cb.isSelected())
            {
                String texto = ((JLabel) aux.getComponent(1)).getText();
                int ID = 0;
                try
                {
                    ResultSet resultSet = stmt.executeQuery("select * from " + tabla + " where " + NameColumn + " = '" + texto + "'");
                    resultSet.next();
                    ID = resultSet.getInt(1);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
                selects.add(new String[]
                { "" + ID, texto });
            }
        }
        return selects;
    }
    private int getAltoo(JPanel panel, double d)
    {
        return (int) (panel.getHeight() * d);
    }
    private int getAncho(JPanel panel, double d)
    {
        return (int) (panel.getWidth() * d);
    }
    private void controller()
    {
        buscar.addKeyListener(this);
        btnLimpiar.addActionListener(this);
        this.addComponentListener(this);
    }
    @Override public void keyReleased(KeyEvent e)
    {
        try
        {
            ResultSet resultSet = stmt.executeQuery("select * from " + tabla + " where " + NameColumn + " like '" + buscar.getText() + "%'" + " order by 2");
            imprime(resultSet);
        }
        catch (SQLException err)
        {
            err.printStackTrace();
        }
    }
    @Override public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnLimpiar)
        {
            buscar.setText("");
            int total = panelista.getComponentCount();
            if (total == 0)// en caso de que se quiera borrar y en la barra de busqueda haya una palabra
                           // que no de ningun resultado, vuelve poner todo
                try
            {
                ResultSet resultSet = stmt.executeQuery("select * from " + tabla + " order by 2");
                imprime(resultSet);
            }
                catch (SQLException err)
            {
                err.printStackTrace();
            }
            for (int i = 0; i < total; i++)
            {
                JPanel aux = (JPanel) panelista.getComponent(i);
                JCheckBox cb = (JCheckBox) aux.getComponent(0);
                cb.setSelected(false);
            }
        }
    }
    @Override public void componentResized(ComponentEvent e)
    {
        buscar.setBounds(getAncho(this, 0.1), getAltoo(this, 0.05), getAncho(this, 0.8), 30);
        panelista.setBounds(getAncho(this, 0.1), getAltoo(this, 0.1) + 30, getAncho(this, 0.8), getAltoo(this, 0.6));
        sp.setBounds(getAncho(this, 0.1), getAltoo(this, 0.1) + 30, getAncho(this, 0.8), getAltoo(this, 0.6));
        btnLimpiar.setBounds(getAncho(this, 0.1), getAltoo(this, 0.7) + 30, getAncho(this, 0.3), 30);
    }
    @Override public void componentMoved(ComponentEvent e)
    {
    }
    @Override public void componentShown(ComponentEvent e)
    {
    }
    @Override public void componentHidden(ComponentEvent e)
    {
    }
    @Override public void keyTyped(KeyEvent e)
    {
    }
    @Override public void keyPressed(KeyEvent e)
    {
    }
}