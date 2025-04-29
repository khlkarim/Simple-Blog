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

todo:
    add comments and link them with posts
    add users and links them with posts