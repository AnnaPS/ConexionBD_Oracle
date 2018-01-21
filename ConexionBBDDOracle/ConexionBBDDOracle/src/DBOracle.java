import java.sql.Statement;
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBOracle {
	
private Connection conexion;

public Connection getConexion(){
	return conexion;
}
public void setConexion(Connection conexion){
	this.conexion=conexion;
}
	public Connection conectar(){
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String baseDeDatos="jdbc:oracle:thin:@LOCALHOST:1521:XE";
			
			conexion =DriverManager.getConnection(baseDeDatos,"dam2","dam2");
			
			if(conexion!=null){
				System.out.println("Conexion establecida");
			}else{
				System.out.println("Error al conectar");
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conexion;
	}
	
	
	
	public void consulta(){
		String ciudad="DALLAS";
		String cons="Select * from DEPT where LOC='"+ciudad+"'";
		
		try {
			Statement sentencia=conexion.createStatement();
			ResultSet resultado;
			resultado = sentencia.executeQuery(cons);
			while (resultado.next()) {
				System.out.println("numero departamento: "+resultado.getString(1));
				System.out.println("Departamento: "+resultado.getString(2));
				System.out.println("Localidad: "+resultado.getString(3));
			
			}
			resultado.close();
			sentencia.close();
			//conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ResultSetMetaData resultSetMetaData=resultado.getMetaData();
//		int nColumnas= resultSetMetaData.getColumnCount();
//		for (int i = 0; i < nColumnas; i++) {
//			System.out.println(resultSetMetaData+ge);
//		}
		
		
	}
	public void consultaMetadata(){
		
		String cons="Select * from DEPT";
		try {
			Statement sentencia=conexion.createStatement();
			ResultSet resultado;
			resultado = sentencia.executeQuery(cons);
			ResultSetMetaData resultSetMetaData=resultado.getMetaData();
			int nColumnas= resultSetMetaData.getColumnCount();
			for (int i = 1; i <= nColumnas; i++) {
				//obtiene el nombre de las columnas
				System.out.println("NOMBRE: "+resultSetMetaData.getColumnName(i));
				//obtiene el maximo de tamaño de cada columna
				System.out.println("Tamaño:"+ resultSetMetaData.getColumnDisplaySize(i));
				if (resultSetMetaData.isNullable(i)==0) {
					System.out.println("La columna puede ser nula: " +resultSetMetaData.getColumnName(i));
				}
			}
			
			resultado.close();
			sentencia.close();
			//conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	public void actualizar(){
		String consulta="UPDATE DEPT SET DNAME='VENTAS' WHERE LOC='CHICAGO'";
		try {
			Statement sentencia=conexion.createStatement();
			ResultSet resultado;
			resultado = sentencia.executeQuery(consulta);
			System.out.println("REGISTRO MODIFICADO");
			ResultSetMetaData resultSetMetaData=resultado.getMetaData();
			int nColumnas= resultSetMetaData.getColumnCount();
			for (int i = 1; i <= nColumnas; i++) {
				//obtiene el nombre de las columnas
				System.out.println("NOMBRE: "+resultSetMetaData.getColumnName(i));
				//obtiene el maximo de tamaño de cada columna
				System.out.println("Tamaño:"+ resultSetMetaData.getColumnDisplaySize(i));
				if (resultSetMetaData.isNullable(i)==0) {
					System.out.println("La columna puede ser nula: " +resultSetMetaData.getColumnName(i));
				}
			}
			
			resultado.close();
			sentencia.close();

			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public void consPreparada(){
		String sql ="select * from dept where dname= ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setString(1, "VENTAS");
			ResultSet res;
			res=sentencia.executeQuery();
			while (res.next()) {
				System.out.println(res.getString(1));
				System.out.println(res.getString(2));
				System.out.println(res.getString(3));
			}
			sentencia.close();
			res.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	public void actualizacionPreparada(){
		String sql ="update dept set loc= ? where deptno= ?";
		try {
			PreparedStatement sentencia=conexion.prepareStatement(sql);
			sentencia.setString(1,"PARLA");
			sentencia.setInt(2, 20);
			ResultSet res;
			res=sentencia.executeQuery();
			System.out.println("Actualizacion completada");
			res.close();
			res.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	public void pedirTipoDeSQLPorTeclado(String tipo){
		try {
			String sqlConsulta="SELECT * FROM DEPT WHERE DNAME= ? AND DEPTNO<? ";
			String sqlUpdate="UPDATE DEPT SET DNAME= ? WHERE DEPTNO= ?";
			ResultSet res = null;
			PreparedStatement sentencia=null;
			
			String sql="";
		
			
			if (tipo.equals("modificacion")){
				sql=sqlUpdate;
				sentencia = conexion.prepareStatement(sql);
				sentencia.setString(1,"VENTAS");
				sentencia.setInt(2,30);
				sentencia.executeUpdate();
				System.out.println("Modificacion realizada con exito");
			}else{
				sql=sqlConsulta;
				sentencia = conexion.prepareStatement(sql);
				sentencia.setString(1,"VENTAS");
				sentencia.setInt(2,40);
				res=sentencia.executeQuery();
				
				while (res.next()) {
					
					System.out.println(res.getString(1));
					System.out.println(res.getString(2));
					System.out.println(res.getString(3));
				}
				res.close();
			}
			
			sentencia.close();

			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void utilizarProcedimientoActLoc(){
		String sql="{call ACTUALIZARLOCALIZACION (?,?)}";
		CallableStatement sentencia;
		try {
			sentencia = conexion.prepareCall(sql);
			
			sentencia.setInt(1, 10);
			sentencia.setString(2, "GETAFE");
			ResultSet res=sentencia.executeQuery();
			System.out.println("CONSULTA REALIZADA");
			res.close();
			sentencia.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void utilizarFuncionPLSQL(){
		//DEVUELVE EL NUMERO DE DEPARTAMENTO POR LOCALIZACION
		String sql="{?=call DEVOLVERDEPTNO(?)}";
		CallableStatement sentencia;
		try {
		sentencia = conexion.prepareCall(sql);
		sentencia.registerOutParameter(1, Types.NUMERIC);//lo que devuelve, en este caso deptno
		sentencia.setString(2, "PARLA");//lo que mando, en este caso loc
		
			sentencia.executeQuery();
			int retorno=sentencia.getInt(1);
			System.out.println("-------------CONSULTA REALIZADA------------");
			System.out.println("El numero de departamento es: "+retorno);
			
		//	res.close();
			sentencia.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void utilizarFuncionPLSQL2(){
		//DEVUELVE 	LA LOCALIZACION POR EL NUMERO DE DEPARTAMENTO
		String sql="{?=call DEVOLVERLOCALIZACION(?)}";
		CallableStatement sentencia;
		try {
		sentencia = conexion.prepareCall(sql);
		sentencia.registerOutParameter(1, Types.VARCHAR);//lo que devuelve, en este caso deptno
		sentencia.setInt(2, 20);//lo que mando, en este caso loc
		
			sentencia.executeQuery();
			String retorno=sentencia.getString(1);
			System.out.println("-------------CONSULTA REALIZADA------------");
			System.out.println("La localizacion es: "+retorno);
			
		//	res.close();
			sentencia.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

	

