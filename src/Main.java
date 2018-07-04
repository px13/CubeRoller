
import java.io.File;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Hlavná trieda, zabezpeèuje spustenie aplikácie.
 */
public class Main extends Application {
	
	private double X;
	private double Y;
	
	public static void main(String[] args) { launch(args); }
  
	@Override 
	public void start(Stage stage) throws Exception {

		Plocha plocha = new Plocha();
		Button buttonNew = new Button("Nová hra");
		buttonNew.setStyle("-fx-background-color: #000000;");
		
		Button buttonSave = new Button("Save");
		buttonSave.setStyle("-fx-background-color: #000000;");
		
		Button buttonLoad = new Button("Load");
		buttonLoad.setStyle("-fx-background-color: #000000;");
		
		buttonNew.setOnAction(event-> {
			plocha.nova();
			});
		
		buttonSave.setOnAction(event-> {
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Save game");
		    File file = fileChooser.showSaveDialog(stage);
	        if (file != null) {
	            try {
	                plocha.save(file.getAbsolutePath());
	            } catch (Exception e) {}
	        }});
        
	    buttonLoad.setOnAction(event-> {
		    FileChooser fileChooser = new FileChooser();
		    fileChooser.setTitle("Load game");
		    File file = fileChooser.showOpenDialog(stage);
	        if (file != null) {
	            try {
	                plocha.load(file.getAbsolutePath());
	            } catch (Exception e) {}
	        }});
		
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: #000000;");
		pane.setCenter(plocha);
		
		BorderPane tlacitka = new BorderPane();
		tlacitka.setLeft(buttonNew);
		tlacitka.setCenter(buttonSave);
		tlacitka.setRight(buttonLoad);
		
		pane.setBottom(tlacitka);
		
		pane.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.RIGHT) {
				plocha.posunKocku(1,0);
			}
			if (event.getCode() == KeyCode.LEFT) {
				plocha.posunKocku(-1,0);
			}
			if (event.getCode() == KeyCode.DOWN) {
				plocha.posunKocku(0,-1);
			}
			if (event.getCode() == KeyCode.UP) {
				plocha.posunKocku(0,1);
			}
		});

		plocha.setOnMousePressed(event -> {
			X = event.getSceneX();
			Y = event.getSceneY();
		});
		
		plocha.setOnMouseDragged(event -> {
			if (Math.abs(event.getSceneX()-X) < 5 && Math.abs(event.getSceneY()-Y) < 5) return;
			double x = event.getSceneX()-X;
			double y = event.getSceneY()-Y;
			X = event.getSceneX();
			Y = event.getSceneY();
			plocha.otoc(y,x,0);
		});		
		Scene scene = new Scene(pane);
		stage.setTitle("CubeRoller");
		stage.setScene(scene);
		stage.show(); 	
	}
}