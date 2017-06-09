#R (English version, version française à la suite)
* In this folder you will find a sample of data science projects I worked on and coded in R. The projects are the following:
  * R-exercises
    * I am a collaborator to the website  <a href="R-exercises.com">R-exercises.com</a> since April 2017. In this repertory, you will find a preview of the exercises sets I wrote for this website made with Jupyter Notebook. The
      exercises sets have for subjects:
        * Time series forecasting
        * Copulas
        * Importing data set with R
        * Error metrics
        * Neural networks
  * IMDB  
     * In this project I wanted to predict the profitability of a movie using information available during pre-production. To do so, I gather data from IMDB.com and the-number.com on 5000 movies,  cleaned the raw data, did a
       exploratory analysis and use the XGBoost and SVM algorithms to predict box-office number of each movie in the data set. The notebooks are organized as follow:
        * IMDB_data_cleaning.ipynb and IMDB_data_cleaning2.ipynb are where I show in detail how I process the dataset.
        * IMDB_data_exploration.ipynb  show how I explored the dataset.
        * IMDB_scrapping.ipynb has been used to find additional data about the 5000 movies, historical inflation rate and exchange rate.
        * IMDB_model.ipynb show how I used the XGBoost and SVM algorithms to make prediction about the profitability of each movies.

     * GGG (Annotations in french)
       * I made those Jupyter Notebook or the Kaggle competition "Ghouls, Goblins, and Ghosts... Boo!" in october 2016. The competition had for goal to create a classification model for a linearly non-separable dataset.
         I made two notebooks for this projects:
          * Kaggle_GGG_exploration.ipynb where I clean and explore the dataset.
          * Kaggle_GGG_Modèle.ipynb where I made my classification model.

     * Titanic (Annotations in french)
      * This is the first Kaggle competition in which I participated in. The challenge was to predict if a passenger of the Titanic survive the catastrophe by looking at some of their characteristics like their name, their ticket class, or
         their sex. This was a classification problem and this competition help me learn a lot about variable engineering.    



#R (Version française)
* Ce dossier contient des programmes codés en R que j'ai utilisés pour analyser des jeux de données et des textes où j'explique ma démarche.
  * R-exercises
    * Je suis un collaborateur au site web  <a href="R-exercises.com">R-exercises.com</a> depuis avril 2017 où j'écris des articles servant d'introduction à un sujet relatif aux statistiques et d'exercices de programmation en R.
      Dans ce dossier, vous trouverez l'introduction de ces articles, quelques exercices ainsi que le lien vers l'article sur <a href="R-exercises.com">R-exercises.com</a>.  Les sujets abordés sont:
        * Prévision d'une série chronologique
        * Copules
        * Importer des jeux de données avec R
        * Mesures de performance d'un modèle
        * Réseau de neurones
  * IMDB  
     * J'ai utilisé l'information recueillie sur les sites IMDB.com et the-number.com pour modéliser la profitabilité de 5000 films. Le but du projet était de savoir s'il était possible d'estimer la profitabilité d'un film en utilisant seulement des informations disponibles lors de la préproduction. Pour ce faire, j'ai dû nettoyer les données, faire de l'exploration de donnée, puis j'ai utilisé les algorithmes XGBoost et SVM pour prédire les résultats au box-office par régression et classer les films selon leur profitabilité. Les fichiers sont les suivants:
       * IMDB_data_cleaning.ipynb and IMDB_data_cleaning2.ipynb dans lesquel je prépare les données avant l'analyse.
       * IMDB_data_exploration.ipynb  est le notebook dans lequel j'analyse chacune des variables du jeu de données.
       * IMDB_scrapping.ipynb montre comment j'ai été chercher les données sur les films, les taux de changes et d'inflation historiques pour ajuster les données financières des films.
       * IMDB_model.ipynb est le notebook où j'utilise les algorithmes XGBoost et SVM pour prédire les revenus associés à chaque film.

  *  Titanic
     * Ce dossier contient des scripts que j'ai utilisés pour analyser un jeu de données nommé Titanic. Comme son nom l'indique, ce jeu de données contient des informations sur des passagers du Titanic, ainsi que s'ils ont survécu au naufrage ou non. Ces scripts utilisent ces informations, ainsi que des méthodes statistiques pour déterminer la probabilité que chaque passager ait survécu.

  * GGG
    * Ce dossier contient des Jupyter Notebook où je décris les étapes que j'ai suivies lors de la compétition "Ghouls, Goblins, and Ghosts... Boo!" à laquelle  j'ai participé. J'ai divisé le script en deux notebook: Kaggle_GGG_exploration.ipynb où j'explore les données et Kaggle_GGG_Modèle.ipynb où je teste différent algorithme de classification.
