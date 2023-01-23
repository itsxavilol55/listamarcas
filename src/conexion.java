import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.SQLException;
//esta clase crea un objeto Connection y regresa el Statement
public class conexion {
    private String namebd;
    private String userbd;
    private String password;
    private Statement stmt;
    public conexion(String name, String user, String password) {
        namebd = name;
        userbd = user;
        this.password = password;
        try {
            conecta();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void conecta() throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;encrypt=true;databaseName=" + namebd + ";TrustServerCertificate=true;",userbd, password);
            stmt = con.createStatement();
            System.out.println("conectado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public Statement getStmt() {
        return stmt;
    }
}
