# Portfolio

I'm working on a web site that I will use as a portfolio showing some coding projects I completed in the past. Before choosing a host server and doing all the minutia associated with having a website, I will implement a basic version of it on GitHub. That repository is where I'll dump all the files that could be usefull for that website, but that are useless for now.

So feel free to look at my code and comments on my old projects while I'm building that web site and if you learn something while browsing my files or have a good idea for my site, let me know! 

#Portfolio

Vous trouverez dans ce répertoire une série de projets sur lesquels j'ai travaillé. Ces projets sont classés selon le langage utilisé. 

##R
* Ce dossier contient des scripts que j'ai utilisés pour analyser des données et des textes où j'explique ma démarche. 
  * IMDB  
     * J'ai utilisé l'information recueillie sur les sites IMDB.com et the-number.com pour modéliser la profitabilité de 5000 films. Le but du projet était de savoir s'il était possible d'estimer la profitabilité d'un film en utilisant seulement des informations disponibles lors de la préproduction. Pour ce faire, j'ai dû nettoyer les données, faire de l'exploration de donnée, puis j'ai utilisé les algorithmes XGBoost et SVM pour prédire les résultats au box-office par régression et classer les films selon leur profitabilité.
  *  Titanic
     * Ce dossier contient des scripts que j'ai utilisés pour analyser un jeu de données nommé Titanic. Comme son nom l'indique, ce jeu de données contient des informations sur des passagers du Titanic, ainsi que s'ils ont survécu au naufrage ou non. Ces scripts utilisent ces informations, ainsi que des méthodes statistiques pour déterminer la probabilité que chaque passager ait survécu.
     
  * GGG
    * Ce dossier contient des Jupyter Notebook où je décris les étapes que j'ai suivi lors de la compétition "Ghouls, Goblins, and Ghosts... Boo!" à laquelle  j'ai participé. J'ai divisé le script en deux notebook: Kaggle_GGG_exploration.ipynb où j'explore les données et Kaggle_GGG_Modèle.ipynb où je teste différent algorithme de classification.


##Java
* Bubble_Level
  * Prototype d'une application faite pour un client avec Android studio. L'application comporte deux types de niveau, un rapporteur d'angle et un détecteur de métal servant de détecteur de vis, de tuyaux et de fils. 
  
* CompressionTest
  * Ce programme génère plusieurs séries de nombres pseudo-aléatoires à l'aide d'un générateur congruentiel linéaire, puis crée un dictionnaire où sont notés les séries de nombres et les paramètres du générateur. Puisque le nombre de paramètres est beaucoup moins important que le nombre pouvant être généré dans les séries, en récrivant des séries de nombres sous forme de ses paramètres, nous pouvons économiser beaucoup d'espace disque. Le programme teste cette hypothèse en utilisant le dictionnaire sur une liste de nombres aléatoires de taille choisie par l'utilisateur.

* PictureModifier
  * Le but de ce projet est de faire du traitement d'image à l'aide d'algorithme d'apprentissage automatique. Dans celui-ci, une image est lue, puis un certain nombre de rectangles de tailles et de couleurs différentes sont générés de manière aléatoire. Ensuite, un algorithme génétique modifie les rectangles, deux à la fois, jusqu'à ce que l'ensemble des rectangles forme une approximation acceptable de l'image de départ.

##C++
* snes_test
  * Ce programme charge une image, puis la pixelise selon certains paramètres spécifiés par l'utilisateur.
* Background
  * J'ai travaillé dans mes temps libres sur la production d'un jeu vidéo et ce programme fût utilisé pour générer rapidement des arrière plan aléatoires utilisés, temporairement, durant la programmation du "gameplay". Le programme lit des fichiers .jpg, crée des éléments formant l'arrière-plan, les place sur des calques selon les paramètres spécifiés par l'utilisateur et retourne une image où tous les calques sont fusionnés. La position des éléments, ainsi que leur grandeur est déterminée aléatoirement de manière à créer de la diversité dans l'image finale.

##C#
* Tableau
  * Une variante du programme CompressionTest, mais implémentée en C# et utilisant un autre algorithme pour générer les suites de nombres pseudo-aléatoires.
* Test_Compression
  * Petit programme que j'ai écrit pour gérer une base de données à l'aide de C#. Ce programme permet de se connecter à une base de données, puis d'écrire et de lire sur des tables spécifiques. C'est le modèle que j'utilise lorsque j'ai à utiliser une base de données dans un projet.




























