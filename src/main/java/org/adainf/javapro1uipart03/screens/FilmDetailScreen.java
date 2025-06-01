package org.adainf.javapro1uipart03.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.adainf.javapro1uipart03.Application;
import org.adainf.javapro1uipart03.models.Film;

public class FilmDetailScreen {

    private final Scene filmDetailScene;

    public FilmDetailScreen(Film film) {
        // Sidebar (50px breed, arrow-box 40px hoog)
        VBox sidebar = new VBox();
        sidebar.setId("sidebar");
        sidebar.setPrefWidth(50);

        StackPane arrowBox = new StackPane();
        arrowBox.setId("arrow-box");
        arrowBox.setPrefSize(50, 40); // Precies als header
        arrowBox.setAlignment(Pos.CENTER);

        ImageView backArrow = new ImageView(
                new Image(getClass().getResourceAsStream("/org/adainf/javapro1uipart03/images/icons/ic_arrow_left.png"))
        );
        backArrow.setId("back-arrow");
        backArrow.setFitHeight(18);
        backArrow.setFitWidth(18);
        backArrow.setPreserveRatio(true);
        backArrow.setOnMouseClicked(e -> {
            Stage stage = (Stage) backArrow.getScene().getWindow();
            stage.close();
        });
        arrowBox.getChildren().add(backArrow);
        sidebar.getChildren().add(arrowBox);

        // Header (rode balk)
        FlowPane header = new FlowPane();
        header.setId("header");
        header.setPrefSize(Application.applicationSize[0] - 50, 40);
        header.setAlignment(Pos.CENTER_LEFT);

        HBox titlePane = new HBox();
        titlePane.setId("header-title-pane");
        titlePane.setAlignment(Pos.CENTER_LEFT);
        titlePane.setSpacing(10);

        Text appTitle = new Text("INF CINEMA");
        appTitle.setStyle("-fx-fill: white; -fx-font-size: 17px; -fx-font-family: 'Montserrat'; -fx-font-weight: 900;");
        titlePane.getChildren().add(appTitle);
        header.getChildren().add(titlePane);

        // Poster
        ImageView poster = new ImageView();
        try {
            poster.setImage(new Image(film.getPoster(), 210, 290, true, true));
        } catch (Exception e) {}
        poster.setFitHeight(290);
        poster.setFitWidth(210);
        poster.setId("film-poster");

        VBox infoSection = new VBox();
        infoSection.setId("info-section");
        infoSection.setSpacing(16);
        infoSection.setAlignment(Pos.TOP_LEFT);
        infoSection.setMaxWidth(700);

        // TITEL
        Text title = new Text(film.getTitle());
        title.setId("film-title");

        // SUBINFO
        Text subInfo = new Text(film.getReleaseDate() + "   -   " + film.getGenre() + "   -   " + film.getRuntime());
        subInfo.setId("film-subinfo");

        // SCORES (PG, METASCORE, IMDB)
        HBox scoresBox = new HBox();
        scoresBox.setId("scores-row");
        scoresBox.setSpacing(32);
        scoresBox.setAlignment(Pos.CENTER_LEFT);

        VBox ratedBox = new VBox();
        ratedBox.setAlignment(Pos.CENTER_LEFT);
        Text ratedText = new Text(film.getRated());
        ratedText.getStyleClass().add("film-score-value");
        Text ratedLabel = new Text("RATED");
        ratedLabel.getStyleClass().add("film-score-label");
        ratedBox.getChildren().addAll(ratedText, ratedLabel);

        VBox metaBox = new VBox();
        metaBox.setAlignment(Pos.CENTER_LEFT);
        Text metaText = new Text(film.getMetascore() + "%");
        metaText.getStyleClass().add("film-score-value");
        Text metaLabel = new Text("METASCORE");
        metaLabel.getStyleClass().add("film-score-label");
        metaBox.getChildren().addAll(metaText, metaLabel);

        VBox imdbBox = new VBox();
        imdbBox.setAlignment(Pos.CENTER_LEFT);
        Text imdbText = new Text(film.getImdbRating().replace('.', ','));
        imdbText.getStyleClass().add("film-score-value");
        Text imdbLabel = new Text("IMDB");
        imdbLabel.getStyleClass().add("film-score-label");
        imdbBox.getChildren().addAll(imdbText, imdbLabel);

        scoresBox.getChildren().addAll(ratedBox, metaBox, imdbBox);

        // PLOT
        Text plot = new Text(film.getPlot());
        plot.setId("film-plot");
        plot.setWrappingWidth(520);

        // VIEW ONLINE BUTTON MET ICON
        ImageView discoveryIcon = new ImageView(
                new Image(getClass().getResourceAsStream("/org/adainf/javapro1uipart03/images/icons/ic_discovery.png"))
        );
        discoveryIcon.setFitHeight(18);
        discoveryIcon.setFitWidth(18);
        discoveryIcon.setPreserveRatio(true);

        Button viewOnline = new Button("VIEW ONLINE", discoveryIcon);
        viewOnline.setId("view-online-btn");
        viewOnline.setGraphicTextGap(10); // ruimte tussen icon en tekst

        // Voeg alles toe in volgorde:
        infoSection.getChildren().addAll(title, subInfo, scoresBox, plot, viewOnline);

        HBox mainContent = new HBox(poster, infoSection);
        mainContent.setId("main-content");
        mainContent.setSpacing(40);
        mainContent.setPadding(new Insets(44, 0, 0, 44));
        mainContent.setAlignment(Pos.TOP_LEFT);

        VBox rightContent = new VBox(header, mainContent);
        rightContent.setId("container");
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        HBox root = new HBox(sidebar, rightContent);
        root.setPrefWidth(Application.applicationSize[0]);
        root.setPrefHeight(Application.applicationSize[1]);
        HBox.setHgrow(rightContent, Priority.ALWAYS);

        filmDetailScene = new Scene(root, Application.applicationSize[0], Application.applicationSize[1]);
        filmDetailScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;700;900");
        filmDetailScene.getStylesheets().add(Application.class.getResource("/org/adainf/javapro1uipart03/stylesheets/filmdetailscreen.css").toExternalForm());

        Stage detailStage = new Stage();
        detailStage.setScene(filmDetailScene);
        detailStage.setTitle(film.getTitle());
        detailStage.show();
    }
}
