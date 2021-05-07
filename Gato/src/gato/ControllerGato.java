package gato;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ControllerGato implements Initializable {

	@FXML
	private Label jugador;

	@FXML
	private JFXButton btn1;

	@FXML
	private JFXButton btn2;

	@FXML
	private JFXButton btn3;

	@FXML
	private JFXButton btn4;

	@FXML
	private JFXButton btn5;

	@FXML
	private JFXButton btn6;

	@FXML
	private JFXButton btn7;

	@FXML
	private JFXButton btn8;

	@FXML
	private JFXButton btn9;

	@FXML
	private RadioButton rbX;

	@FXML
	private ToggleGroup tiro;

	@FXML
	private RadioButton rbO;

	@FXML
	private JFXButton reset;

	ModelGato mg = new ModelGato();

	@FXML
	void btn1OnAction(ActionEvent event) {
		if (btn1.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "1", jugador.getText()))
				btn1.setText(jugador.getText());
		}
	}

	@FXML
	void btn2OnAction(ActionEvent event) {
		if (btn2.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "2", jugador.getText()))
				btn2.setText(jugador.getText());
		}
	}

	@FXML
	void btn3OnAction(ActionEvent event) {
		if (btn3.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "3", jugador.getText()))
				btn3.setText(jugador.getText());
		}
	}

	@FXML
	void btn4OnAction(ActionEvent event) {
		if (btn4.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "4", jugador.getText()))
				btn4.setText(jugador.getText());
		}
	}

	@FXML
	void btn5OnAction(ActionEvent event) {
		if (btn5.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "5", jugador.getText()))
				btn5.setText(jugador.getText());
		}
	}

	@FXML
	void btn6OnAction(ActionEvent event) {
		if (btn6.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "6", jugador.getText()))
				btn6.setText(jugador.getText());
		}
	}

	@FXML
	void btn7OnAction(ActionEvent event) {
		if (btn7.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "7", jugador.getText()))
				btn7.setText(jugador.getText());
		}
	}

	@FXML
	void btn8OnAction(ActionEvent event) {
		if (btn8.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "8", jugador.getText()))
				btn8.setText(jugador.getText());
		}
	}

	@FXML
	void btn9OnAction(ActionEvent event) {
		if (btn9.getText().equals("")) {
			if (tirar(rbX.isSelected() ? "X" : "O", "9", jugador.getText()))
				btn9.setText(jugador.getText());
		}
	}

	@FXML
	void rbOOnAction(ActionEvent event) {
		asignarTiro(rbX.isSelected() ? "X" : "O");
	}

	@FXML
	void rbXOnAction(ActionEvent event) {
		asignarTiro(rbX.isSelected() ? "X" : "O");
	}

	@FXML
	void resetOnAction(ActionEvent event) {
		reiniciarJuego();
	}

	public void cambiarDatos(String jug, String b1, String b2, String b3, String b4, String b5, String b6, String b7, String b8, String b9) {
		Platform.runLater(() -> {
			if (!jug.equals(jugador.getText())) 
				jugador.setText(jug);
				
			btn1.setText(b1);
			btn2.setText(b2);
			btn3.setText(b3);
			btn4.setText(b4);
			btn5.setText(b5);
			btn6.setText(b6);
			btn7.setText(b7);
			btn8.setText(b8);
			btn9.setText(b9);
		});
	}

	Runnable runable = new Runnable() {
		@Override
		public void run() {
			String jug = "";
			while (true) {
				try {
					Thread.sleep(500);
					// cambiarDatos();
					jug = mg.getTiros("tiro");

					cambiarDatos(jug,
							mg.getTiros("1"),
							mg.getTiros("2"),
							mg.getTiros("3"),
							mg.getTiros("4"),
							mg.getTiros("5"),
							mg.getTiros("6"),
							mg.getTiros("7"),
							mg.getTiros("8"),
							mg.getTiros("9"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mg.conectarDB();
		reiniciarJuego();

		Thread actual = new Thread(runable);
		actual.start();
		
	}

	public boolean tirar(String tiro, String posicion, String lugar) {
		return (mg.tirar(tiro, posicion, lugar));

	}

	public void reiniciarJuego() {
		mg.cambiarTiro();
		mg.iniciarJuego();
		btn1.setText("");
		btn2.setText("");
		btn3.setText("");
		btn4.setText("");
		btn5.setText("");
		btn6.setText("");
		btn7.setText("");
		btn8.setText("");
		btn9.setText("");
		// jugador.setText(mg.getTiro());

		// asignarTiro(rbX.isSelected() ? "X" : "O");
	}

	public void asignarTiro(String tiro) {
		// Selecciona tiro

		// mg.asignarTiro(tiro.equals("X") ? "X" : "O");
	}

}
