package org.adainf.javapro1uipart03.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Film {
    private final String title, releaseDate, poster, genre, runtime, rated, metascore, imdbRating, plot;

    /**
     * Film constructor
     * @param title String
     * @param releaseDate String
     * @param poster String
     */
    public Film(String title, String releaseDate, String poster, String genre, String runtime, String rated, String metascore, String imdbRating, String plot) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.genre = genre;
        this.runtime = runtime;
        this.rated = rated;
        this.metascore = metascore;
        this.imdbRating = imdbRating;
        this.plot = plot;
       // this.genre//
        // this.runtime

        // this.rated
        // this.metascore
        // this.imdbRating

        // this.plot
        // this.website
    }

    /**
     * Film constructor
     * @param result ResultSet
     * @throws SQLException SQLException
     */
    public Film(ResultSet result) throws SQLException {
        this.title = result.getString("title");
        this.releaseDate = result.getString("year");
        this.poster = result.getString("poster");
        this.genre = result.getString("genre");
        this.runtime = result.getString("runtime");
        this.rated = result.getString("rated");
        this.metascore = result.getString("metascore");
        this.imdbRating = result.getString("imdbRating");
        this.plot = result.getString("plot");
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPoster() {
        return this.poster;
    }

    public String getGenre() {return this.genre; }

    public String getRuntime() {return this.runtime; }

    public String getRated() {return this.rated; }

    public String getMetascore() {return this.metascore; }

    public String getImdbRating() {return this.imdbRating; }

    public String getPlot() {return this.plot; }


}
