import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import models.Offer;

import java.util.ArrayList;

public class CartController {

    @FXML
    public Text total_price;
    @FXML
    public Button buyButton;
    @FXML
    private VBox cartItems;
    static public Property<ArrayList<Offer>> cart_elements;
    static public int ex_scene;

    public CartController(){
            }
    @FXML
    public void initialize(){
        ex_scene = 1;
        cart_elements = new SimpleObjectProperty<ArrayList<Offer>>();
        cart_elements.setValue(new ArrayList<Offer>());

        cart_elements.addListener(new ChangeListener<ArrayList<Offer>>() {
            @Override
            public void changed(ObservableValue<? extends ArrayList<Offer>> observableValue, ArrayList<Offer> offers, ArrayList<Offer> t1) {
                for(int i=0; i!=cartItems.getChildren().size(); i++){
                    Node nod=cartItems.getChildren().get(i);
                    System.out.println(nod.getId()!=null);
                    if(nod.getId()!=null && nod.getId().equals("total")){
                        break;
                    }
                    cartItems.getChildren().remove(i);
                    i--;

                }
                float total = 0;
                ArrayList<Offer> cart_elementsValue = cart_elements.getValue();
                for(int i=0; i!=cart_elements.getValue().size(); i++){
                    Offer element = cart_elementsValue.get(i);
                    total+= element.old_price*element.percentage;
                    cartItems.getChildren().add(i,addElementToCart(element));
                }
                total_price.setText(String.valueOf(total+" $"));
                if(total == 0){
                    buyButton.setDisable(true);
                }else{
                    buyButton.setDisable(false);
                }
            }
        });
    }

    public static AnchorPane addElementToCart(Offer offer){
        AnchorPane ap = new AnchorPane();
        ap.prefHeight(34);
        ap.prefWidth(653);
        ap.setStyle("-fx-border-color: white;");

        ImageView iv = new ImageView();
        iv.setFitHeight(19);
        iv.setFitWidth(11);
        iv.setLayoutX(21);
        iv.setLayoutY(9);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        AnchorPane.setLeftAnchor(iv,21.0);
        iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Offer> newlist = new ArrayList<>(cart_elements.getValue());
                newlist.remove(offer);
                CartController.cart_elements.setValue(newlist);
                System.out.println();
            }
        });
        Image im= new Image("resources/x.png");
        iv.setImage(im);

        Text item_name = new Text();
        item_name.setStrokeType(StrokeType.OUTSIDE);
        item_name.setStrokeWidth(0);
        item_name.setText(offer.title);
        item_name.setLayoutX(39);
        item_name.setLayoutY(19);
        AnchorPane.setLeftAnchor(item_name, 39.0);

        Text item_price = new Text();
        item_price.setStrokeType(StrokeType.OUTSIDE);
        item_price.setStrokeWidth(0);
        item_price.setText(String.valueOf(offer.percentage*offer.old_price));
        item_price.setLayoutX(606);
        item_price.setLayoutY(21);
        AnchorPane.setRightAnchor(item_price, 23.59375);

        ap.getChildren().add(iv);
        ap.getChildren().add(item_name);
        ap.getChildren().add(item_price);
return ap;

    }

    public void leaveCart(ActionEvent event) {
        Main.sceneNumber.setValue(ex_scene);
    }
}
