import java.sql.Connection;
import java.sql.SQLException;

public class MainConexionOracle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DBOracle oracle=new DBOracle();
		Connection con;
		
		con=oracle.conectar();
//		oracle.consulta();
//		System.out.println("\n\tConsulta metadata");
//		oracle.consultaMetadata();
//		
//		oracle.actualizar();
//		System.out.println("\n\tConsulta Preparada");
//		oracle.consPreparada();
	//	System.out.println("Actualizacion preparada");
		
		//oracle.actualizacionPreparada();
		//oracle.pedirTipoDeSQLPorTeclado(args[0]);
		//oracle.utilizarProcedimientoActLoc();
		//oracle.utilizarFuncionPLSQL();
		oracle.utilizarFuncionPLSQL2();
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
