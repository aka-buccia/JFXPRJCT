package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {			
			Parent root = FXMLLoader.load(getClass().getResource("/application/Scenes/EnterScene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("SPT - JFXPRJCT");
			Image logo = new Image("/application/Images/logo.png");
			stage.getIcons().add(logo);
			stage.setResizable(false);
			
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}


