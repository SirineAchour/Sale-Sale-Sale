import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class FormController {
    public static int ex_scene=0;


    public void backButtonClicked(ActionEvent event) {
        Main.sceneNumber.setValue(ex_scene);
    }

    public void nextButtonClicked(ActionEvent event) {
        Main.sceneNumber.setValue(5);
    }
}
