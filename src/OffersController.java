import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import models.Offer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OffersController {
    @FXML
    private Button cartButton;
    @FXML
    public FlowPane offers;
    @FXML
    private ComboBox categories;
    @FXML
    private ComboBox sortBy;

    @FXML
    private void initialize() {
        Main.fillStoresArray();
        Main.fillOffersArray();
        this.fillSortByList();
        this.fillCategories(null);
        // display offers
        for (Offer offer : Main.offers) {
            this.offers.getChildren().add(getOffer(offer));
        }
    }

    public void fillCategories(String category) {
        this.categories.getItems().add("All");

        File folder = new File("src/resources/categories");
        File[] listOfFiles = folder.listFiles();

        for (File fileOrFolder : listOfFiles) {
            String name = fileOrFolder.getName().toUpperCase().charAt(0) + fileOrFolder.getName().substring(1);
            if (fileOrFolder.isDirectory()) {
                if ((category == null) || (category != null && name.equals(category)))
                    this.categories.getItems().add(name);
            }
        }
    }

    public void fillSortByList() {
        this.sortBy.getItems().addAll(
                "A to Z",
                "Z to A",
                "Most popular to least",
                "Price high to low",
                "Price low to high"
        );
    }

    public VBox getOffer(Offer offer) {
        VBox vb = new VBox();
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setPrefHeight(200);
        vb.setPrefWidth(140);

        ImageView iv = new ImageView();
        iv.setFitHeight(200);
        iv.setFitWidth(140);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                offer.visits++;
                //go to details scene
                Main.sceneNumber.setValue(2);
                //set offer for details
                //OfferController.offer_set = true;
                OfferController.offer.setValue(offer);
            }
        });
        Image im = new Image("resources/categories/" + offer.category.toLowerCase() + "/" + offer.filename);
        iv.setImage(im);
        Text txt = new Text();
        txt.setStrokeType(StrokeType.OUTSIDE);
        txt.setStrokeWidth(0);
        txt.setText(offer.title);

        HBox hb = new HBox();
        hb.setAlignment(Pos.TOP_CENTER);
        hb.setPrefHeight(0);
        hb.setPrefWidth(146);
        Text txt_old_price = new Text();
        txt_old_price.setStrokeType(StrokeType.OUTSIDE);
        txt_old_price.setStrokeWidth(0);
        txt_old_price.setText(String.valueOf(offer.old_price));

        Text txt_new_price = new Text();
        txt_new_price.setStrokeType(StrokeType.OUTSIDE);
        txt_new_price.setStrokeWidth(0);
        txt_new_price.setText(String.valueOf(offer.old_price * offer.percentage));

        hb.getChildren().add(txt_old_price);
        hb.getChildren().add(txt_new_price);

        vb.getChildren().add(iv);
        vb.getChildren().add(txt);
        vb.getChildren().add(hb);
        offers.setMargin(vb, new Insets(0, 20, 50, 0));

        return vb;
    }

    public void categorySelected(ActionEvent actionEvent) {
        this.offers.getChildren().clear();
        Main.displayed_offers.clear();
        for (Offer offer : Main.offers) {
            if (offer.category.equals(categories.getValue()) || (categories.getValue().equals("All"))) {
                Main.displayed_offers.add(offer);
                this.offers.getChildren().add(getOffer(offer));
            }
        }
        //sort
        this.sortBySelected(null);
    }

    public void sortBySelected(ActionEvent actionEvent) {
        if (this.sortBy.getValue() == null) {
            return;
        }
        this.offers.getChildren().clear();
        if (this.sortBy.getValue().equals("A to Z")) {
            for (Offer offer : Main.displayed_offers) {
                this.offers.getChildren().add(getOffer(offer));
            }
        } else if (this.sortBy.getValue().equals("Z to A")) {
            for (int i = (Main.displayed_offers.size() - 1); i >= 0; i--) {
                this.offers.getChildren().add(getOffer(Main.displayed_offers.get(i)));
            }
        } else if (this.sortBy.getValue().equals("Most popular to least")) {
            ArrayList<Offer> sorted = new ArrayList<Offer>(Main.displayed_offers);
            Collections.sort(sorted);
            for (Offer offer : sorted) {
                this.offers.getChildren().add(getOffer(offer));
            }
        } else if (this.sortBy.getValue().equals("Price high to low")) {
            ArrayList<Offer> sorted = new ArrayList<Offer>(Main.displayed_offers);
            Collections.sort(sorted, new Comparator<Offer>() {
                @Override
                public int compare(Offer offer, Offer t1) {
                    float o_price = offer.old_price * offer.percentage;
                    float t_price = t1.old_price * t1.percentage;
                    return (o_price > t_price) ? -1 : (o_price == t_price) ? 0 : 1;
                }
            });
            for (Offer offer : sorted) {
                this.offers.getChildren().add(getOffer(offer));
            }
        } else if (this.sortBy.getValue().equals("Price low to high")) {
            ArrayList<Offer> sorted = new ArrayList<Offer>(Main.displayed_offers);
            Collections.sort(sorted, new Comparator<Offer>() {
                @Override
                public int compare(Offer offer, Offer t1) {
                    float o_price = offer.old_price * offer.percentage;
                    float t_price = t1.old_price * t1.percentage;
                    return (o_price < t_price) ? -1 : (o_price == t_price) ? 0 : 1;
                }
            });
            for (Offer offer : sorted) {
                this.offers.getChildren().add(getOffer(offer));
            }
        }
    }

    public void goToCart(ActionEvent event) {
        Main.sceneNumber.setValue(3);
        CartController.ex_scene=1;
    }
}
