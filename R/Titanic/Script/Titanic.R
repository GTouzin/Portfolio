#D�finir le dossier contenant les fichiers de donn�es
setwd("E:/Users/Guillaume/Documents/R/Project/Titanic/Data")
getwd()

#library � utiliser
library(boot) #Bootstrap
library(car) #Test dw et vif
library(fields) #Pour le calcul de la distance euclidienne
library(class) #knn()
library(knncat) #knn pour mod�le comprenant des variables cath�goriques
library(gmodels) # Pour cr�er des crosstables pour �valuer la performance des algorithmes
library(plyr)  #Recoder les variables

#Lire les donn�es 
train<-read.csv("train.csv", header = TRUE, sep = ",")
head(train)

test<-read.csv("test.csv", header = TRUE, sep = ",")
str(test)

#Cr�ation des facteurs dans le data.frame train
train$Pclass <-factor(train$Pclass, levels=c(3,2,1), ordered=TRUE)
str(train)
summary(train)
test$Pclass <-factor(test$Pclass, levels=c(3,2,1), ordered=TRUE)
str(train)

#################################################################################
#Regression lin�aire 
titanic_lm<- lm(Survived~ Pclass+Sex+Age+SibSp+Parch+Fare+Embarked  , data=train) 
summary(titanic_lm)

#Test pour l'autocorr�lation
vif(titanic_lm)
dwt(titanic_lm)

#Histogramme v�rifiant la normalit� des r�sidues
hist(rstudent(titanic_lm))


#Calcul des coefficients beta via bootstrap 

bootstrap_reg<-function(formula, data, y)
{
	t <- data[y,]
	modele <- lm(formula,data=t)
	return(coef(modele))
}

Res_boots_reg<-boot(statistic = bootstrap_reg, formula = Survived~Pclass+Sex+Age+SibSp+Parch+Fare , data = train, R = 10000)

boot.ci(Res_boots_reg, type = "bca", index = 1)
boot.ci(Res_boots_reg, type = "bca", index = 2)
boot.ci(Res_boots_reg, type = "bca", index = 3)
boot.ci(Res_boots_reg, type = "bca", index = 4)
boot.ci(Res_boots_reg, type = "bca", index = 5)
boot.ci(Res_boots_reg, type = "bca", index = 6)
boot.ci(Res_boots_reg, type = "bca", index = 7)

#Deuxi�me mod�le
titanic_lm2<- lm(Survived~ Pclass+Sex+Age+SibSp  , data=train) 
summary(titanic_lm2)


#Comparer les mod�les par anova
anova(titanic_lm, titanic_lm2)

#---Histogram-----
hist(rstudent( titanic_lm2))

#-----Confidence intervals-----
confint( titanic_lm2)

#Traiter les donn�es (selecter les colomnes et g�rer les donn�es abscentes)
train_knn<-train[,!names(train) %in%c("PassengerId","Survived", "Name")]
str(train_knn)
train_knn<-train[complete.cases(train_knn),]

test_knn<-test[,!names(test) %in%c("PassengerId", "Name")]
str(test_knn)
test_knn<-test[complete.cases(test_knn),]


#Pr�diction pour les valeurs du data.frame train
surviver_pred<-predict( titanic_lm2, train, interval="predict")
surviver_pred<-data.frame(surviver_pred)

#Arrondit pour avoir des variables binaires
surviver_pred$fit_arrondit<-round(surviver_pred$fit, digits=0)
str(surviver_pred)

#Calcul de la rmse
residues<- train$Survived - surviver_pred$fit_arrondit
head(residues)
rmse_lm<-sqrt((sum(residues^2))/(length(residues)))
rmse_lm


#Pr�diction pour les valeurs du data.frame test
surviver_pred<-predict( titanic_lm2, test, interval="predict")
surviver_pred<-data.frame(surviver_pred)


#R�gression logistique



#Calcul de la rmse

################################################################################################
#KNN (utilise library(fields))

knn_multivarie <- function(x_pred, x, y, k){
  m <- nrow(x_pred)
  n<- nrow(x)
  predict_knn <- rep(0, m)
  for (i in 1:m) {
	dist_vect <- rep(0, n)
	for(j in 1:n) {
         	dist_vect[j] <- rdist(x_pred[i,], x[j,])   
	}
	sort_index <- order(dist_vect)    
    	predict_knn[i] <- mean(y[sort_index[1:k]])
  }
  return(predict_knn)
}

#Traiter les donn�es pour knn_multivarie et knn()
train_knn<-train[complete.cases(train),]  	#Enlever les lignes contenant des NA
train_Survived_knn<-train_knn$Survived          #Enregistre les cath�gories dans un vecteur ind�pendant
str(train_Survived_knn)
  	 
train_knn<-train_knn[,!names(train_knn) %in%c("id","PassengerId","Ticket","Cabin","Embarked", "Survived", "Name")]   #Enlever les variables qui nous int�ressent pas
train_knn$Pclass<-as.numeric(levels(train_knn$Pclass))[train_knn$Pclass]   	#Change le facteur Pclass en variable num�rique
train_knn$Sex <-revalue(train_knn$Sex, c("female"="0","male"="1" )) 			#Recodage du facteur Sex
train_knn$Sex<-as.numeric(levels(train_knn$Sex))[train_knn$Sex]   	#Change le facteur Sex en variable num�rique
str(train_knn)
sum(is.na(train_knn))


test_knn<-test[complete.cases(test),]
test_knn<-test_knn[,!names(test) %in%c("id","PassengerId","Ticket","Cabin","Embarked", "Survived", "Name")]
test_knn$Pclass<-as.numeric(levels(test_knn$Pclass))[test_knn$Pclass]   	#Change le facteur Pclass en variable num�rique
test_knn$Sex <-revalue(test_knn$Sex, c("female"="0","male"="1" )) 			#Recodage du facteur Sex
test_knn$Sex<-as.numeric(levels(test_knn$Sex))[test_knn$Sex]   	#Change le facteur Sex en variable num�rique

str(test_knn)
sum(is.na(test_knn))

#Pr�visions avec knn_multivarie()
prev_test<-knn_multivarie (test_knn , train_knn,  train$Survived, 50)
str(prev_test)
head(prev_test, n=50)
sum(prev_test<=0.5)

#Pr�visions avec knn() 
prev_test<-knn(train=train_knn, test=test_knn, cl= train_Survived_knn, k=50)
prev_test

#Pr�vision avec les variables cath�goriques

#Traiter les donn�es
train_knn_cat<-train[complete.cases(train),]  	#Enlever les lignes contenant des NA
train_Survived_knn_cat<-train_knn_cat$Survived          #Enregistre les cath�gories dans un vecteur ind�pendant
str(train_Survived_knn_cat)
sum(is.na(train_Survived_knn_cat))
train_knn_cat<-train_knn_cat[,!names(train_knn_cat) %in%c("id","PassengerId","Cabin","Ticket", "Name")]   #Enlever les variables qui nous int�ressent pas
str(train_knn_cat)
str(train)
sum(is.na(train_knn_cat))


test_knn_cat<-test[complete.cases(test),]
test_knn_cat<-test_knn_cat[,!names(test) %in%c("id","PassengerId","Cabin","Ticket", "Name")]
test_knn_cat["Survived"]<-train_Survived_knn_cat[1:330,]
str(test_knn_cat)
sum(is.na(test_knn_cat))

#knncat() 
objet_knncat<-knncat(train=train_knn_cat, cl= 1, k=500)
objet_knncat

#Pr�vision 
prev_knncat<-predict(objet_knncat, train_knn_cat, test_knn_cat, train.classcol=1, newdata.classcol=0)

#Calcul de la rmse


######################################################################################################
#Kmeans

#Traitement des donn�es
train_kmean <-train[, !names(train)%in% c("id","Name","PassengerId","Survived","Ticket")]
str(train_kmean)
test_kmean <-test[, !names(test)%in% c("id","Name","PassengerId","Ticket")]
str(test_kmean)

test_knn$Pclass<-as.numeric(levels(test_knn$Pclass))[test_knn$Pclass]   	#Change le facteur Pclass en variable num�rique
test_knn$Sex <-revalue(test_knn$Sex, c("female"="0","male"="1" )) 			#Recodage du facteur Sex
test_knn$Sex<-as.numeric(levels(test_knn$Sex))[test_knn$Sex]   	#Change le facteur Sex en variable num�rique

train_kmean$

#Mise � l'�chelle

#


#Calcul de la rmse


#Clustering hierarchique


#Calcul de la rmse




