package com.friendlines.model;

public class ImagePost extends Post
{
    private Picture image;

    public ImagePost()
    {
        super();
    }

    public Picture getImage() {
        return image;
    }

    public void setImage(Picture image) {
        this.image = image;
    }
}
