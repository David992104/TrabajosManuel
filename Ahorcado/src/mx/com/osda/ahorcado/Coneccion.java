package mx.com.osda.ahorcado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.CallableStatement;

public class Coneccion {

	public Connection conn = null;

	public Coneccion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:8889/ahorcado?useSSL=false", "root",
					"root");
//			if (conn.isValid(0))
//				//System.out.println("Conexion Mysql");
//			else
//				System.out.println("Conexion fallida");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void terminadoJuego() {
		try {
			CallableStatement call = (CallableStatement) conn
					.prepareCall("Update juego set terminado = 0;");
			call.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int quienGano() {
		int jugador = 0;
		try {
			CallableStatement call = (CallableStatement) conn.prepareCall("select terminado, jugador from juego");
			call.execute();
			while (call.getResultSet().next()) {
				if (call.getResultSet().getInt(1) == 1) {
					if (call.getResultSet().getInt(2) == 1) {
						jugador = 1;
					} else {
						jugador = 2;
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jugador;
	}

	public void ganador(int jugador) {
		try {
			CallableStatement call = (CallableStatement) conn
					.prepareCall("Update juego set terminado = 1 where jugador = ?");
			call.setInt(1, jugador);
			call.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int numAleatorio() {
		int numPal = 0;
		try {
			CallableStatement call = (CallableStatement) conn.prepareCall("SELECT COUNT(*) FROM palabra");
			call.execute();
			while (call.getResultSet().next()) {
				numPal = call.getResultSet().getInt(1);
			}
			call.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (int) (Math.random() * numPal + 1);
	}

	public String palabra() {
		String palabra = "";
		try {
			CallableStatement call = (CallableStatement) conn
					.prepareCall("select palabra from palabra where idpalabra = ?");
			call.setInt(1, numAleatorio());
			call.execute();
			while (call.getResultSet().next()) {
				palabra = call.getResultSet().getString(1);
			}
			call.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palabra;
	}

	public String estadoJugador() {// Metodo para asignar el jugador su turno o su numero de jugador, solo se puede
									// de 2
		String jugador = "";
		CallableStatement jug1 = null;
		CallableStatement jug2 = null;
		int juga1 = 0;
		int juga2 = 0;

		try {
			CallableStatement call = (CallableStatement) conn.prepareCall("LOCK TABLES juego WRITE");
			call.execute();

			jug1 = (CallableStatement) conn.prepareCall("select status from juego where jugador = 1");
			jug1.execute();
			jug2 = (CallableStatement) conn.prepareCall("select status from juego where jugador = 2");
			jug2.execute();

			if (jug1.getResultSet().next())
				juga1 = jug1.getResultSet().getInt(1);
			if (jug2.getResultSet().next())
				juga2 = jug2.getResultSet().getInt(1);

			jug1.close();
			jug2.close();

			if (juga1 == 0) {
				call = (CallableStatement) conn.prepareCall("update juego set status = 1 where jugador = 1");
				jugador = "Jugador 1";
			} else if (juga2 == 0 && juga1 == 1) {
				call = (CallableStatement) conn.prepareCall("update juego set status = 1 where jugador = 2");
				jugador = "Jugador 2";
			} else
				jugador = "Espere turno";

			call.execute();
			call = (CallableStatement) conn.prepareCall("UNLOCK TABLES");
			call.execute();

			call.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jugador;
	}

	// Metodo para cerrar todas las coneccionesy volver a su estado del jugador a 0
	public void salirJugador(int jugador) {
		try {
			CallableStatement call = (CallableStatement) conn
					.prepareCall("update juego set status = 0 where jugador = ?");
			call.setInt(1, jugador);
			call.execute();

			call.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
