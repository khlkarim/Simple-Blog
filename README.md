7. Simple Blog API 
Permet de publier et consulter des articles de blog.
Fonctionnalités : CRUD des articles, commentaires en lecture seule.

CRUD compte
CRUD poste


internaute :
 - pas de clé personelle
 - consulte les posts + filtrage (par utilisateur/par topic) (peut consulter un seul blog par titre)
 - consulter les utilisateur (peut consulter un seul utilisateur par son username)
 - créer un compte

user inputs username (+info) the api makes sure username is unique + valid = if succesful api returns unique key that the user uses to post/modify acc etcetc

user : (extends internaute) KEY CANT BE ACCESSED ONLY BY ADMIN
 - posts a post (unique id given by api + title + content + tags + comments (originally empty))
 - crud posts (patch put delete)
 - crud compte (change username)
 - cant delete other's posts and other's accounts

admin : special key hardcoded for all admins
 - see all the keys of users, posts etc
 - delete any user any post change anything
has a key that always works

http://localhost:8080/api/blog

user:
api_key (unique private)
username (unique)
nom et prenom
email
tel

GET /api/blog/users -> get all users (user key masked)
GET /api/blog/users/?username={username} -> get information on a certain user (user key masked)
POST /api/blog/users/{username} -> creates a new user, username must be unique, api gives auto generated key
GET /api/blog/users/{userkey} -> get information on a certain user (user key unmasked)
PATCH /api/blog/users/{userkey} -> modify user info (cannot modify key, cannot modify username)
DELETE /api/blog/users/{userkey} -> delete user

post:
id (unique)
creator username
title
content
tags
datetime

GET /api/blog/posts -> get all posts
GET /api/blog/posts/{id} -> get post by id
GET /api/blog/posts/title/{title} -> get posts by title
GET /api/blog/posts/author/{author} -> get all posts made by that user
GET /api/blog/posts/tags/{tags} -> filter posts by tags, they must have the tag to appear (ex: news,technology)
POST /api/blog/posts/{userkey} -> create a new post made by the user
PATCH /api/blog/posts/{userkey}/{id} -> modify post information (cannot modify id, cannot modify creator username, cannot modify datetime)
DELETE /api/blog/posts/{userkey}/{id} -> delete post

comment:
creator username
post id
content
datetime

GET /api/blog/posts/comment/{id} -> get comments of post by id
POST /api/blog/posts/comment/{id}/{userkey} -> post a comment
PATCH /api/blog/posts/comment/{id}/{userkey} -> modify a comment (can only modify content)
DELETE /api/blog/posts/comment/{id}/{userkey} -> delete a comment