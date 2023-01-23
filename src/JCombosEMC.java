import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import java.awt.event.*;
public class JCombosEMC extends JPanel implements ActionListener, ComponentListener
{
    private Statement stmt;
    private JComboBox combo1,combo2, combo3;
    public JCombosEMC(Statement stmt) 
    {
        this.stmt = stmt;
        interfaz();
        controller();
    }
    private void interfaz() 
    {
        setSize(400, 400);
        setLayout(null);
        combo1=new JComboBox();
        combo2=new JComboBox();
        combo3=new JComboBox();
        combo1.setBounds(getAncho(this,0.3), getAltoo(this,0.40)-40, getAncho(this,0.4), 30);
        combo2.setBounds(getAncho(this,0.3), getAltoo(this,0.50)-40, getAncho(this,0.4), 30);
        combo3.setBounds(getAncho(this,0.3), getAltoo(this,0.60)-40, getAncho(this,0.4), 30);
        combo2.setEnabled(false);
        combo3.setEnabled(false);
        add(combo1);
        add(combo2);
        add(combo3);
        imprime(combo1,2);
        setVisible(true);
    }
    private void controller() 
    {
        combo1.addActionListener(this);
        combo2.addActionListener(this);
        combo3.addActionListener(this);
        this.addComponentListener(this);
    }
    private void imprime(JComboBox combo, int column) 
    {
        combo.addItem("Seleccionar");
        ResultSet resultSet;
        try {
            String instruc=""; 
            if(column==2)
                instruc = "select distinct(NombreEstado) from vw_estados order by Nombreestado ";
            else if(column==3)
                instruc = "select distinct(NombreMunicipio) from vw_estados where nombreEstado ='"+combo1.getSelectedItem()+"'"; 
            else 
                instruc = "select distinct(NombreCiudad) from vw_estados where NombreMunicipio ='"+combo2.getSelectedItem()+"'"; 
            resultSet = stmt.executeQuery(instruc);
            while (resultSet.next()) 
            {
                combo.addItem(""+resultSet.getString(1));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int getAltoo(JPanel panel, double d) 
    {
        return (int) (panel.getHeight()*d);
    }
    private int getAncho(JPanel panel, double d) 
    {
        return (int) (panel.getWidth()*d);
    }
    /**
     * It returns a two-dimensional array of strings, which contains the ID of the selected city, the
     * name of the selected state, the name of the selected municipality and the name of the selected
     * city
     * 
     * @return A 2D array of Strings.
     */
    public String[][] getSelect() 
    {
        if(combo3.getSelectedItem()!=null)
        {
            String Estado = ""+combo1.getSelectedItem();
            String Municipio  = ""+combo2.getSelectedItem();
            String ciudad = ""+combo3.getSelectedItem(); 
            try {
                ResultSet resultSet = stmt.executeQuery("select * from ciudades where Nombreciudad = '"+ciudad+"'");
                resultSet.next();
                return new String[][]
                {
                    new String[]{resultSet.getString(1),Estado},
                    new String[]{resultSet.getString(2),Municipio},
                    new String[]{resultSet.getString(3),ciudad}
                };
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return null;
	}
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==combo1)
        {
            combo2.setEnabled(true);
            combo3.setEnabled(false);
            combo2.removeAllItems();
            combo3.removeAllItems();
            imprime(combo2, 3);
            return;
        }
        if(e.getSource()==combo2)
        {
            combo3.setEnabled(true);
            combo3.removeAllItems();
            imprime(combo3, 4);
            return;
        }
        if(e.getSource()==combo3)
            return;
    }
    @Override
    public void componentResized(ComponentEvent e) 
    {
        combo1.setBounds(getAncho(this,0.3), getAltoo(this,0.40), getAncho(this,0.4), 30);
        combo2.setBounds(getAncho(this,0.3), getAltoo(this,0.50)+20, getAncho(this,0.4), 30);
        combo3.setBounds(getAncho(this,0.3), getAltoo(this,0.60)+40, getAncho(this,0.4), 30);
        revalidate();
    }
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
}