package gato;

public class ModelGato {
	
	ConeccionDB conexion = new ConeccionDB();
	
	public void conectarDB() {
		conexion.conectar();
	}

	public String getTiros(String position) {
		return conexion.getInfo(position);
	}
	
	public boolean tirar(String tiro, String position, String jugador) {
		boolean status = false;
		if (jugador.equals(tiro)) {
			conexion.tirar(tiro, position);
			conexion.cambiarTiro();
			status = true;
		}
		return status;
	}
	
	
	public void iniciarJuego() {
		for (int i = 1; i <= 9 ; i++) {
			conexion.iniciarJuego(i+"");
		}
	}
	
	public void cambiarTiro() {
		conexion.cambiarTiro();
	}
	public String getTiro() {
		return conexion.obtenerTiro();
	}
	
}
