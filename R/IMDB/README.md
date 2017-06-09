#IMDB  (English version, version française à la suite)
     * In this project I wanted to predict the profitability of a movie using information available during pre-production. To do so, I gather data from IMDB.com and the-number.com on 5000 movies,  cleaned the raw data, did a
       exploratory analysis and use the XGBoost and SVM algorithms to predict box-office number of each movie in the data set. The notebooks are organized as follow:
        * IMDB_data_cleaning.ipynb and IMDB_data_cleaning2.ipynb are where I show in detail how I process the dataset.
        * IMDB_data_exploration.ipynb  show how I explored the dataset.
        * IMDB_scrapping.ipynb has been used to find additional data about the 5000 movies, historical inflation rate and exchange rate.
        * IMDB_model.ipynb show how I used the XGBoost and SVM algorithms to make prediction about the profitability of each movies.


#IMDB  (Version française)
     * J'ai utilisé l'information recueillie sur les sites IMDB.com et the-number.com pour modéliser la profitabilité de 5000 films. Le but du projet était de savoir s'il était possible d'estimer la profitabilité d'un film en utilisant seulement des informations disponibles lors de la préproduction. Pour ce faire, j'ai dû nettoyer les données, faire de l'exploration de donnée, puis j'ai utilisé les algorithmes XGBoost et SVM pour prédire les résultats au box-office par régression et classer les films selon leur profitabilité. Les fichiers sont les suivants:
       * IMDB_data_cleaning.ipynb and IMDB_data_cleaning2.ipynb dans lesquel je prépare les données avant l'analyse.
       * IMDB_data_exploration.ipynb  est le notebook dans lequel j'analyse chacune des variables du jeu de données.
       * IMDB_scrapping.ipynb montre comment j'ai été chercher les données sur les films, les taux de changes et d'inflation historiques pour ajuster les données financières des films.
       * IMDB_model.ipynb est le notebook où j'utilise les algorithmes XGBoost et SVM pour prédire les revenus associés à chaque film.
