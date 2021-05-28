package mx.com.osda.ahorcado;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AhorcadoModel {

	private String palabra;
	private char[] letrasPalabra;
	private int numLetras;

	public ObservableList<String> obtenerLetras() {
		String palabra = new Coneccion().palabra();// Seleccionamos palabra del servidor
		letrasPalabra = palabra.toCharArray(); // Separamos los caracteres de la palabra
		char[] caracteres = palabra.toCharArray();
		
		String letra = "";
		ObservableList<String> lista = FXCollections.observableArrayList();

		// Revolvemos los carcteres de la palabra
		int index;
		Random random = new Random();
		for (int i = caracteres.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			if (index != i) {
				caracteres[index] ^= caracteres[i];
				caracteres[i] ^= caracteres[index];
				caracteres[index] ^= caracteres[i];
			}
		}
		//System.out.println("Palabra inicial " + letrasPalabra);
		//System.out.println("Palabra inicial " + caracteres);
		System.out.println(palabra);
		
		// Llenamos la lista para enviarla a la vista
		for (int i = 0; i < caracteres.length; i++) {
			letra = caracteres[i] + "";
			lista.add(letra);
		}
		System.out.println("Palabra revueta: " + lista);

		setPalabra(palabra); // Añadimos la palabra para que los demas la puedan ver
		setNumLetras(caracteres.length);
		
		return lista;
	}

	public char[] getLetrasPalabra() {
		return letrasPalabra;
	}

	public void setletrasPalabra(char[] letras) {
		this.letrasPalabra = letras;
	}
	
	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public int getNumLetras() {
		return numLetras;
	}

	public void setNumLetras(int numLetras) {
		this.numLetras = numLetras;
	}
}
