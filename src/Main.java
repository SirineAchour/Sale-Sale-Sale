import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Offer;
import models.Store;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main extends Application {

    public static List<Store> stores;
    public static List<Offer> offers;
    public static List<Offer> displayed_offers;
    static SimpleIntegerProperty sceneNumber = new SimpleIntegerProperty(0); // =1 for offers scene  =2 for details scene and =3 for form

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene offers_scene = new Scene(FXMLLoader.load(getClass().getResource("sales.fxml")));
        offers_scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Permanent+Marker&display=swap");
        offers_scene.getStylesheets().add(getClass().getResource("resources/stylesheets/sales.css").toExternalForm());

        Scene offer_details_scene = new Scene(FXMLLoader.load(getClass().getResource("sale.fxml")));
        offer_details_scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Permanent+Marker&display=swap");
        offer_details_scene.getStylesheets().add(getClass().getResource("resources/stylesheets/sales.css").toExternalForm());

        Scene cart_scene = new Scene(FXMLLoader.load(getClass().getResource("cart.fxml")));
        cart_scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Permanent+Marker&display=swap");
        cart_scene.getStylesheets().add(getClass().getResource("resources/stylesheets/sales.css").toExternalForm());

        // when we change the value of this.scene_number, the scene changes
        ChangeListener<Number> scene_changed_listener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observableValue, Number old_value, Number new_value) {
                switch (new_value.intValue()) {
                    case 1:
                        primaryStage.setScene(offers_scene);
                        break;
                    case 2:
                        primaryStage.setScene(offer_details_scene);
                        break;
                     case 3:
                         primaryStage.setScene(cart_scene);
                         break;
                }
            }
        };
        // initialisation of this.scene_number
        Main.sceneNumber.addListener(scene_changed_listener);
        Main.sceneNumber.setValue(1);

        //minHeight="463.0" minWidth="767.0" prefHeight="463.0" prefWidth="767.0"
        primaryStage.setHeight(610);
        primaryStage.setWidth(790);
        primaryStage.setMinWidth(790);
        primaryStage.setMinHeight(610);
        primaryStage.setTitle("Sale! Sale! Sale!");
        primaryStage.show();
    }

    static void fillStoresArray() {
        Main.stores = new ArrayList<>();
        Path pathToFile = Paths.get("src", "resources", "stores.txt").toAbsolutePath();

        // create an instance of BufferedReader
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            line = br.readLine();
            // loop until all lines are read
            while (line != null) {
                String[] attributes = line.split(",");
                Store store = new Store();
                store.id = Integer.parseInt(attributes[0]);
                store.name = attributes[1];
                store.location = attributes[2];
                // adding book into ArrayList
                stores.add(store);
                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }/*
        Collections.sort(Main.stores, new Comparator<Store>() {
            @Override
            public int compare(Store store, Store t1) {
                return store.name.compareTo(t1.name);
            }
        });*/
    }

    static void fillOffersArray() {
        Main.offers = new ArrayList<>();
        Path pathToFile = Paths.get("src", "resources", "offers.txt").toAbsolutePath();

        // create an instance of BufferedReader
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            line = br.readLine();
            // loop until all lines are read
            while (line != null) {
                String[] attributes = line.split(",");
                Offer offer = new Offer();
                offer.title = attributes[0];
                offer.description = attributes[1];
                offer.old_price = Float.parseFloat(attributes[2]);
                offer.percentage = Float.parseFloat(attributes[3]);
                offer.filename = attributes[4];
                offer.category = attributes[5];
                offer.store = Main.stores.get(Integer.parseInt(attributes[6]) - 1);
                offer.new_price = BigDecimal.valueOf(offer.old_price-(offer.old_price*offer.percentage))
                        .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                        .floatValue();
                // adding book into ArrayList
                Main.offers.add(offer);
                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Collections.sort(Main.offers, new Comparator<Offer>() {
            @Override
            public int compare(Offer offer, Offer t1) {
                return offer.title.compareTo(t1.title);
            }
        });
        Main.displayed_offers = new ArrayList<Offer>(Main.offers);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
