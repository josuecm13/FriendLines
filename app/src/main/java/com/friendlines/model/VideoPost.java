package com.friendlines.model;

public class VideoPost extends Post
{
    private String youtubeLink;

    public VideoPost()
    {
        super();
    }

    private Boolean youtubeUrlValidator()
    {
        return youtubeLink.startsWith("https://www.youtube.com/");
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public Boolean setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
        return youtubeUrlValidator();
    }
}
