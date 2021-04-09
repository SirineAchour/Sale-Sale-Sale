import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class BankInfoController {
    public static SimpleStringProperty totalProperty;
    @FXML
    private Text total;

    @FXML
    private void initialize(){
        totalProperty = new SimpleStringProperty();
        totalProperty.setValue(total.getText());

        totalProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                total.setText(totalProperty.getValue());
            }
        });

    }
    public void cancelClicked(ActionEvent event) {
        Main.sceneNumber.setValue(FormController.ex_scene);
    }

    public void nextClicked(ActionEvent event) {
        Main.sceneNumber.setValue(1);
    }
}
