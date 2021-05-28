package mx.com.osda.ahorcado;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static int jugador;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("AhorcadoView.fxml"));
			Scene scene = new Scene(root,700,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		    	if (jugador == 1) {
		    		new Coneccion().salirJugador(jugador);
		    		new AhorcadoController().matarHilo();
		    	}
		    	if (jugador == 2) {
		    		new AhorcadoController().matarHilo();
		    		new Coneccion().salirJugador(jugador);
		    	}
		    	new AhorcadoController().matarHilo();
		    }
		  } );
	}

	@SuppressWarnings("static-access")
	public void setJugador(int jugador) {
		this.jugador = jugador;
	}
}
