import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import models.Offer;

import java.util.ArrayList;

public class OfferController {
    @FXML
    private Button backButton;
    @FXML
    private Button cartButton;
    @FXML
    private ImageView image;
    @FXML
    private Text name;
    @FXML
    private Text store_name;
    @FXML
    private Text store_location;
    @FXML
    private Text description;
    @FXML
    private Text old_price;
    @FXML
    private Text new_price;
    @FXML
    private Text reduction;
    @FXML
    private Button addToCartButton;
    public static Property<Offer> offer;

    public OfferController(){

    }
    @FXML
    public void initialize(){

        OfferController.offer = new SimpleObjectProperty<Offer>();
        OfferController.offer.addListener(new ChangeListener<Offer>() {
            @Override
            public void changed(ObservableValue<? extends Offer> observableValue, Offer old, Offer offer) {
                /*if(!offer_set)
                    return;*/
                description.setText(offer.description);
                name.setText(offer.title);
                store_name.setText(offer.store.name);
                store_location.setText(offer.store.location);
                old_price.setText(String.valueOf(offer.old_price+"$"));
                new_price.setText(String.valueOf(offer.new_price)+"$");
                reduction.setText(String.valueOf((int)(offer.percentage*100))+"%");
                //playing the transition
                image.setImage(new Image("resources/categories/" +offer.category.toLowerCase()+"/"+offer.filename));
            }
        });
    }

    @FXML
    private void addToCart(ActionEvent event){
        RotateTransition rotate = new RotateTransition();
        rotate.setAxis(Rotate.Z_AXIS);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        //rotate.setByAngle(20);
        rotate.setCycleCount(2); //100
        rotate.setDuration(Duration.millis(500));
        rotate.setAutoReverse(true);
        rotate.setNode(addToCartButton);
        rotate.play();


        ArrayList<Offer> newlist = new ArrayList<Offer>(CartController.cart_elements.getValue());
        newlist.add(offer.getValue());
        CartController.cart_elements.setValue(newlist);
    }
    @FXML
    private void backButtonClicked(ActionEvent event) {
        Main.sceneNumber.setValue(1);
    }

    public void goToCart(ActionEvent event) {
        Main.sceneNumber.setValue(3);
        CartController.ex_scene=2;
    }

    public void buyClicked(ActionEvent event) {
        Main.sceneNumber.setValue(4);
        BankInfoController.totalProperty.setValue("TOTAL: "+String.valueOf(offer.getValue().new_price)+"$");
        FormController.ex_scene = 2;
    }
}
