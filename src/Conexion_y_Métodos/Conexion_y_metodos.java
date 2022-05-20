package Conexion_y_Métodos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;


public class Conexion_y_metodos {
	private static String bd = "XE";
	private static String login="alumno";
	private static String password="alumno";
	private static String url="jdbc:oracle:thin:@localhost:1521:"+bd;
	static Connection conexion =null;
	static Statement st =null;
	static ResultSet rs =null;
	static CallableStatement cs =null;

	public static void conectar() {
		System.out.println("------------------------------------------");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion =DriverManager.getConnection(url,login,password);
			if(conexion !=null) {
				System.out.println("conexion realizada correctamente");
				System.out.println("------------------------------------------");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void ver_todo(int num) {
		try {
			st = conexion.createStatement();
			switch (num) {
			case 0:
				rs = st.executeQuery("select * from videojuego");
				break;
			case 1:
				rs = st.executeQuery("select * from videojuego where categoria = 'Acción'");
				break;
			case 2:
				rs = st.executeQuery("select * from videojuego where categoria = 'Aventura'");
				break;
			case 3:
				rs = st.executeQuery("select * from videojuego where categoria = 'Carrera'");
				break;
			case 4:
				rs = st.executeQuery("select * from videojuego where categoria = 'Deporte'");
				break;
			case 5:
				rs = st.executeQuery("select * from videojuego where categoria = 'Rol'");
				break;
			case 6:
				rs = st.executeQuery("select * from videojuego where categoria = 'Terror'");
				break;
			}
			while(rs.next()) {
				System.out.println(rs.getString("COD_VIDEOJUEGO") + "---" + rs.getString("TITULO") +"--" + rs.getString("CATEGORIA") + "--" + rs.getDouble("PRECIO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static void Registro(int num) {
		Scanner sc = new Scanner(System.in);
		String email = null;
		String contra =null;
		int aux = -1;
		try {
			if(num == 0) {
				while(aux ==-1) {
					System.out.println("introduce el email");
					email = sc.nextLine();
					System.out.println("introduce la contraseña");
					contra = sc.nextLine();
					cs = conexion.prepareCall("{ ? = call REGISTRO(?,?)}");
					cs.setString(2, email);
					cs.setString(3,contra);
					cs.registerOutParameter(1, Types.INTEGER);
					cs.execute();
					aux = cs.getInt(1);
					if(aux==-1) {
						System.out.println("datos mal introducidos o no existentes");
					}
				}
			}
			else if(num ==1) {
				System.out.println("introduce el email");
				email = sc.nextLine();
				System.out.println("introduce la contraseña");
				contra = sc.nextLine();
				st = conexion.createStatement();
				st.executeUpdate("insert into CLIENTES values ('"+email+"','" + contra+"')");
				
			}
		} 
		catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Este email ya está registrado");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	
					
		

	}



	public static void main(String[] args) {
		conectar();
		Registro(1);
	}



}
