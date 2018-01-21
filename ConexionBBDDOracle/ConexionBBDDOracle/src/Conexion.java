import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	public static void main(String[] args) {
	    
try {
	getConexionOracle();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

	}
	public static Connection getConexionOracle() throws SQLException
    {
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        String cadena;
        cadena = "jdbc:oracle:thin:@LOCALHOST:1521:XE";
        Connection cn
                    = DriverManager.getConnection(cadena, "dam2", "dam2");
        return cn;
    }
    

}
