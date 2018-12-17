package com.example.davidcabala.popularmoviesdbmv;

public class Movie {

    private String mPosterPath;
    private String mReleaseDate;
    private String mOriginalTitle;
    private String mOverview;
    private Double mVoteAverage;


    public String getPosterPath() {
        final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
        return TMDB_POSTER_BASE_URL + mPosterPath;
    }

    public void setPosterPath(String PosterPath) {
        this.mPosterPath = PosterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String ReleaseDate) {
        this.mReleaseDate = ReleaseDate;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String OriginalTitle) {
        this.mOriginalTitle = OriginalTitle;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setOverview(String Overview) {
        this.mOverview = Overview;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double VoteAverage) {
        this.mVoteAverage = VoteAverage;
    }
}
