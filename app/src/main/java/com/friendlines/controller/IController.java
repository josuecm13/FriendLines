package com.friendlines.controller;

public interface IController
{
    Boolean registerUser();
    Boolean deleteUser();
    Boolean updateUser();
    Boolean getUser();
    Boolean logout();
    Boolean recoverPassword();
    Boolean createComment();
    Boolean getPost(int idPost);
    Boolean createPost();
    Boolean deletePost();
    Boolean likePost(int idPost);
    Boolean dislikePost(int idPost);
}
