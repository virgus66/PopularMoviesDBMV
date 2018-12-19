package com.example.davidcabala.popularmoviesdbmv;

public class Movie {

    private String Id;
    private String Title;
    private String VoteCount;
    private String Video;
    private String VoteAverage;
    private String Popularity;
    private String PosterPath;
    private String OriginalLan;
    private String OriginalTit;
    private String Overview;
    private String ReleaseDate;
    static final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

    public Movie(String id, String title, String voteCount, String video, String voteAverage, String popularity, String posterPath, String originalLan, String originalTit, String overview, String releaseDate) {
        Id = id;
        Title = title;
        VoteCount = voteCount;
        Video = video;
        VoteAverage = voteAverage;
        Popularity = popularity;
        PosterPath = posterPath;
        OriginalLan = originalLan;
        OriginalTit = originalTit;
        Overview = overview;
        ReleaseDate = releaseDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getVoteCount() {
        return VoteCount;
    }

    public void setVoteCount(String voteCount) {
        VoteCount = voteCount;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        VoteAverage = voteAverage;
    }

    public String getPopularity() {
        return Popularity;
    }

    public void setPopularity(String popularity) {
        Popularity = popularity;
    }

    public String getPosterPath() {
        return TMDB_POSTER_BASE_URL + PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    public String getOriginalLan() {
        return OriginalLan;
    }

    public void setOriginalLan(String originalLan) {
        OriginalLan = originalLan;
    }

    public String getOriginalTit() {
        return OriginalTit;
    }

    public void setOriginalTit(String originalTit) {
        OriginalTit = originalTit;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

}
