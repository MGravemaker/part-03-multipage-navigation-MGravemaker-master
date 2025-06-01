package org.adainf.javapro1uipart03.screens;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.adainf.javapro1uipart03.Application;
import org.adainf.javapro1uipart03.models.Film;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeScreen {

    private final Scene homeScene;
    private final FlowPane filmSection, films;
    private final ProgressIndicator pi;

    public HomeScreen() {
        Pane container = new Pane();
        container.setId("container");

        filmSection = new FlowPane();
        filmSection.setId("film-section");
        filmSection.setPrefSize(Application.applicationSize[0] - 165, Application.applicationSize[1] - 60);
        filmSection.setPadding(new Insets(40, 20, 20, 20));
        filmSection.relocate(165, 40);
        filmSection.setVgap(20);

        films = new FlowPane();
        films.setPrefSize(filmSection.getPrefWidth() - 40, filmSection.getPrefHeight());
        films.setHgap(40);
        films.setVgap(20);

        pi = new ProgressIndicator();
        pi.setMinWidth(1000);

        filmSection.getChildren().addAll(new Text("Christmas Films"), pi, films);

        container.getChildren().addAll(getHeader(), getNavBar(), filmSection);

        homeScene = new Scene(container);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;700;900");
        homeScene.getStylesheets().add(Application.class.getResource("stylesheets/homescreen.css").toString());

        Platform.runLater(this::getFilms);
    }

    /**
     * Get the home scene
     * @return Scene
     */
    public Scene getHomeScene() {
        return homeScene;
    }

    /**
     * Get the header
     * @return Pane
     */
    private Pane getHeader() {
        FlowPane header = new FlowPane();
        header.setId("header");
        header.setPrefSize(Application.applicationSize[0], 40);
        header.setOrientation(Orientation.VERTICAL);
        header.setAlignment(Pos.CENTER);

        FlowPane titlePane = new FlowPane();
        titlePane.setId("title-pane");
        titlePane.setPrefSize(165, 40);
        titlePane.setAlignment(Pos.CENTER);
        titlePane.setHgap(5);

        ImageView logo = new ImageView();
        logo.setFitHeight(15);
        logo.setPreserveRatio(true);
        logo.setImage(new Image(Application.class.getResource("images/icons/ic_video.png").toString()));

        titlePane.getChildren().addAll(logo, new Text("INF CINEMA"));

        FlowPane searchPane = new FlowPane();
        searchPane.setOrientation(Orientation.HORIZONTAL);
        searchPane.setPrefSize(Application.applicationSize[0] - titlePane.getPrefWidth(), 40);
        searchPane.setAlignment(Pos.CENTER_LEFT);
        searchPane.setPadding(new Insets(0, 0, 0, 20));
        searchPane.setHgap(5);

        TextField searchField = new TextField();
        searchField.setPromptText("Search for films...");
        searchField.setFocusTraversable(false);
        searchField.setMinWidth(Application.applicationSize[0] - titlePane.getPrefWidth() - 50);

        ImageView searchIcon = new ImageView();
        searchIcon.setFitWidth(15);
        searchIcon.setPreserveRatio(true);
        searchIcon.setImage(new Image(Application.class.getResource("images/icons/ic_search.png").toString()));

        searchPane.getChildren().addAll(searchIcon, searchField);
        header.getChildren().addAll(titlePane, searchPane);

        return header;
    }

    /**
     * Get the navigation bar
     * @return Pane
     */
    private Pane getNavBar() {
        FlowPane navBar = new FlowPane();
        navBar.setId("navbar");
        navBar.setOrientation(Orientation.HORIZONTAL);
        navBar.setPrefSize(165, Application.applicationSize[1] - 40);
        navBar.setPadding(new Insets(30, 0, 0, 0));
        navBar.relocate(0, 40);
        navBar.getChildren().addAll(
                generateNavItem("New Releases", true),
                generateNavItem("Trending", false),
                generateNavItem("Coming Soon", false),
                generateNavItem("Favourites", false),
                generateNavItem("Watch Later", false));

        return navBar;
    }

    /**
     * Generate a navigation item
     * @param title String
     * @param active boolean
     * @return FlowPane
     */
    private FlowPane generateNavItem(String title, boolean active) {
        FlowPane navItem = new FlowPane();
        navItem.setStyle(active ? "-fx-background-color: #212a31;" : "");
        navItem.setPadding(new Insets(0, 0, 0, 20));
        navItem.setAlignment(Pos.CENTER_LEFT);
        navItem.setPrefSize(165, 35);
        navItem.setHgap(40);

        Text navItemText = new Text(title);
        navItemText.setStyle("-fx-font-family: 'Montserrat'; " + (active ? "-fx-fill: #F83646; -fx-font-weight: 700;" : "-fx-fill: #797F84; -fx-font-weight: 400;") + "-fx-font-size: 11px;");
        navItem.getChildren().addAll(navItemText);

        if (active) {
            ImageView arrow = new ImageView();
            arrow.setFitWidth(15);
            arrow.setPreserveRatio(true);
            arrow.setImage(new Image(Application.class.getResource("images/icons/ic_arrow_right.png").toString()));
            navItem.getChildren().add(arrow);
        }

        return navItem;
    }

    /**
     * Generate a UI film item
     * @param film Film
     * @return FlowPane
     */
    private FlowPane generateFilmItem(Film film) {
        FlowPane filmItem = new FlowPane();
        filmItem.setId("film-item");
        filmItem.setOrientation(Orientation.HORIZONTAL);
        filmItem.setMinSize(130, 232);
        filmItem.setVgap(15);

        // TODO: Open the detail screen when the film item is clicked
        filmItem.setOnMouseClicked(event -> {
            new  FilmDetailScreen(film); // Opens the film detail screen
        });

        Pane filmPoster = new Pane();
        filmPoster.setPrefSize(120, 175);
        filmPoster.setStyle(
                "-fx-background-image: url(" + film.getPoster() + "); " +
                "-fx-background-size: contain;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 20, 0, 0.0, 0.0);"
        );

        FlowPane filmInfo = new FlowPane();
        filmInfo.setOrientation(Orientation.VERTICAL);
        filmItem.setPrefSize(120, 80);

        Text filmTitle = new Text(film.getTitle());
        filmTitle.setId("film-title");
        filmTitle.wrappingWidthProperty().set(120);

        Text filmReleaseDate = new Text(film.getReleaseDate());
        filmReleaseDate.setId("film-release-date");

        filmInfo.getChildren().addAll(filmTitle, filmReleaseDate);
        filmItem.getChildren().addAll(filmPoster, filmInfo);

        return filmItem;
    }

    /**
     * Get films from the database
     */
    private void getFilms() {
        try {
            ResultSet film = Application.connection.query("SELECT * FROM films ORDER BY title ASC");
            while (film.next()) films.getChildren().add(generateFilmItem(new Film(film)));
            filmSection.getChildren().remove(pi);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

