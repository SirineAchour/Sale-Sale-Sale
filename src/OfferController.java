import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
    public static Property<Offer> offer;


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
                image.setImage(new Image("resources/categories/" +offer.category.toLowerCase()+"/"+offer.filename));
            }
        });
    }

    @FXML
    private void addToCart(ActionEvent event){
        System.out.println(CartController.cart_elements.getValue().size());
        ArrayList<Offer> newlist = new ArrayList<Offer>(CartController.cart_elements.getValue());
        newlist.add(offer.getValue());
        CartController.cart_elements.setValue(newlist);
        System.out.println(CartController.cart_elements.getValue().size());
    }
    @FXML
    private void backButtonClicked(ActionEvent event) {
        Main.sceneNumber.setValue(1);
    }

    public void goToCart(ActionEvent event) {
        Main.sceneNumber.setValue(3);
        CartController.ex_scene=2;
    }
}
