package mx.com.osda.ahorcado;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class AhorcadoController implements Initializable {
	public static boolean estado;
	private String palabra;
	private int numLetras;
	public int numLetrasAcertadas = 0;

	public char[] letrasPalabra;
	public String letra = "";
	public int indexPalabra = 0;

	public int numPiezaActivadas = 0; // LAs piezas que estan activadas del muñeco

	@FXML
	private Button btnNewGame;

	@FXML
	private Label lblEsperandoJugador;

	@FXML
	private Label lblGanador;

	@FXML
	private Label lblPalSelect;

	@FXML
	private Label lblPosSelect;

	@FXML
	private ImageView imgCabeza;

	@FXML
	private ImageView imgCuerpo;

	@FXML
	private ImageView imgManoDerecha;

	@FXML
	private ImageView imgManoIzq;

	@FXML
	private ImageView imgPieDerecho;

	@FXML
	private ImageView imgPieIzq;

	@FXML
	private Button btnSalir;

	@FXML
	private Button btnComprobarLetra;

	@FXML
	private ListView<String> listaLetras;

	@FXML
	private ListView<String> listaPalabras;

	ObservableList<String> palabraLista = FXCollections.observableArrayList();
	ObservableList<String> letraLista = FXCollections.observableArrayList();

	@FXML
	void btnNewGameOnAction(ActionEvent event) {
		llenarListaLetras();
		vaciarListaPalabra();
		reiniciarValores();
		new Coneccion().terminadoJuego();
		numLetrasAcertadas = 0;
		if (!actual.isAlive()) {
			// actual.start();
			estado = true;
		}
	}

	@FXML
	void btnComprobarOnAction(ActionEvent event) {
		comprobarListaPalabra();
	}

	@FXML
	void btnSalirOnAction(ActionEvent event) {
		String juga = lblEsperandoJugador.getText();
		if (juga.equals("Jugador 1"))
			new Coneccion().salirJugador(1);
		if (juga.equals("Jugador 2"))
			new Coneccion().salirJugador(2);

		matarHilo();

		System.exit(0);
	}

	Coneccion conex = new Coneccion();
	int ganador = 0;
	Runnable runable = new Runnable() {
		@Override
		public void run() {
			System.out.println("Estado " + estado);
			while (estado) {
				ganador = conex.quienGano();
				if (ganador == 1 && lblEsperandoJugador.getText().equals("Jugador 2")) {
					System.out.println("Gano el jugador 1, tu perdiste");
					cambiarGanador("Perdiste Gano el jugador 1");
					estado = false;
				} else if (ganador == 2 && lblEsperandoJugador.getText().equals("Jugador 1")) {
					System.out.println("Gano el jugador 2, tu perdiste");
					cambiarGanador("Perdiste, gano el jugador 2");
					estado = false;
				} else if (ganador == 1 && lblEsperandoJugador.getText().equals("Jugador 1")) {
					System.out.println("Ganaste");
					cambiarGanador("Ganaste");
					estado = false;
				} else if (ganador == 2 && lblEsperandoJugador.getText().equals("Jugador 2")) {
					System.out.println("Ganaste");
					cambiarGanador("Ganaste");
					estado = false;
				} else {
					cambiarGanador("Jugando");
				}

			}
		}
	};
	Thread actual = new Thread(runable);

	public void cambiarGanador(String texto) {
		Platform.runLater(() -> {
			lblGanador.setText(texto);
		});
	}

	public void comprobarListaPalabra() {
		if (indexPalabra >= 0) {
			if (palabraLista.get(indexPalabra).equals("__")) {
				// System.out.println("Si esta vacio el campo" + indexPalabra + " " +
				// letrasPalabra[indexPalabra] + " " + letra);
				char let = letra.charAt(0);
				char letPal = letrasPalabra[indexPalabra];

				if (let != letPal) {
					switch (numPiezaActivadas) {
					case 0:
						imgCabeza.setVisible(true);
						numPiezaActivadas++;
						break;
					case 1:
						imgCuerpo.setVisible(true);
						numPiezaActivadas++;
						break;
					case 2:
						imgManoDerecha.setVisible(true);
						numPiezaActivadas++;
						break;
					case 3:
						imgManoIzq.setVisible(true);
						numPiezaActivadas++;
						break;
					case 4:
						imgPieIzq.setVisible(true);
						numPiezaActivadas++;
						break;
					case 5:
						imgPieDerecho.setVisible(true);
						numPiezaActivadas++;
						System.out.println("Has perdido /n La palabra correcta era " + palabra);
						lblGanador.setText("Perdiste");
						if (lblEsperandoJugador.getText().equals("Jugador 1")) {
							new Coneccion().ganador(2);
						} else {
							new Coneccion().ganador(1);
						}
						break;
					}

				} else {
					palabraLista.add(indexPalabra, letra);
					palabraLista.remove(indexPalabra);
					numLetrasAcertadas++;
					if (numLetras == numLetrasAcertadas) {
						System.out.println("Son iguales ganaste");
						lblGanador.setText("Ganaste");
						numLetrasAcertadas = 0;
						if (lblEsperandoJugador.getText().equals("Jugador 1")) {
							new Coneccion().ganador(1);
						} else {
							new Coneccion().ganador(2);
						}
					}
					// System.out.println(numLetrasAcertadas + " de " + numLetras);
				}
			} else
				System.out.println("Seleccion otra posicion");
		}
	}

	public void vaciarListaPalabra() {
		palabraLista.clear();
		for (int i = 0; i < numLetras; i++) {
			palabraLista.add("__");
		}
		listaPalabras.setItems(palabraLista);
	}

	public void llenarListaLetras() {
		AhorcadoModel ahorcado = new AhorcadoModel();

		letraLista = ahorcado.obtenerLetras();
		listaLetras.setItems(letraLista);

		palabra = ahorcado.getPalabra();

		numLetras = ahorcado.getNumLetras();

		letrasPalabra = ahorcado.getLetrasPalabra();

	}

	public void listenerLetras() {
		listaLetras.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
				letra = ov.getValue();
				// System.out.println(new_val);
				lblPalSelect.setText(new_val);
			}
		});
	}

	public void listenerPalabra() {
		listaPalabras.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				indexPalabra = (int) observable.getValue();
				// System.out.println("Num pos selected " + indexPalabra);
				lblPosSelect.setText((indexPalabra + 1) + "");
				;
			}
		});
	}

	public void llenarJugadorEtiqueta() {
		lblEsperandoJugador.setText(new Coneccion().estadoJugador());
	}

	public void definirJugador() {
		Main main = new Main();
		String juga = lblEsperandoJugador.getText();
		if (juga.equals("Jugador 1"))
			main.setJugador(1);
		if (juga.equals("Jugador 2"))
			main.setJugador(2);
	}

	public void reiniciarValores() {
		imgCabeza.setVisible(false);
		imgCuerpo.setVisible(false);
		imgManoDerecha.setVisible(false);
		imgManoIzq.setVisible(false);
		imgPieDerecho.setVisible(false);
		imgPieIzq.setVisible(false);
		lblGanador.setText("");
	}

	@SuppressWarnings("static-access")
	public void encenderHilo() {
		this.estado = true;
		// System.out.println("Estado " + estado);
		actual.start();
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	public void matarHilo() {
		// System.out.println("Matando hilo");
		this.estado = false;
		actual.stop();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reiniciarValores();

		llenarJugadorEtiqueta();

		definirJugador();

		listenerPalabra();
		listenerLetras();

		llenarListaLetras();

		vaciarListaPalabra();

		new Coneccion().terminadoJuego();

		encenderHilo();

	}

}
