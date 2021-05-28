package gato;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ConeccionDB {

	public Statement statement;
	public Connection connection = null;

	public void conectar() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/catdavidosornio", "root","qazplm10");
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.100.7:3306/catdavidosornio/?user=root", "root", "qazplm10");
			if (connection.isValid(0))
				System.out.println("Conexion Mysql");
			else
				System.out.println("Conexion fallida");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getInfo(String position) {
		String result = "";
		try {
			CallableStatement call = (CallableStatement) connection.prepareCall("select value from tablerodavid where position = ?");
			call.setString(1, position);
			call.execute();
			
			while (call.getResultSet().next()) {
				result = call.getResultSet().getString(1);
			}
			call.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public boolean cambiarTiro() {
		boolean status = false;
		try {
			CallableStatement call;
			String anterior = obtenerTiro();
			if (anterior.equals("O"))
				call = (CallableStatement) connection.prepareCall("update tablerodavid set value = 'X' where position = 'tiro'");
			else
				call = (CallableStatement) connection.prepareCall("update tablerodavid set value = 'O' where position = 'tiro'");
			
			if (call.execute())
				status = true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public String obtenerTiro() {
		String result = "";
		try {
			CallableStatement call = (CallableStatement) connection.prepareCall("select value from tablerodavid where position = 'tiro'");
			call.execute();
			
			while (call.getResultSet().next()) {
				result = call.getResultSet().getString(1);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean iniciarJuego(String posicion) {
		boolean status = false;
		try {
			CallableStatement call = (CallableStatement) connection.prepareCall("update tablerodavid set value = '' where position = ?");
			call.setString(1, posicion);
			//call.execute();

			if (call.execute())
				status = true;
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean tirar(String tiro, String position) {
		boolean status = false;
		
		try {
			CallableStatement call = (CallableStatement) connection.prepareCall("update tablerodavid set value = ? where position = ?");
			call.setString(1, tiro);
			call.setString(2, position);
			
			if (call.execute())
				status = true;
			
		}catch(SQLException e) { 
			e.printStackTrace();
		}
		return status;
	}

}
