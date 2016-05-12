#Définir le dossier contenant les fichiers de données
setwd("E:/Users/Guillaume/Documents/R/Project/Titanic/Data")
getwd()

#library à utiliser
library(boot) #Bootstrap
library(car) #Test dw et vif
library(fields) #Pour le calcul de la distance euclidienne
library(class) #knn()
library(knncat) #knn pour modèle comprenant des variables cathégoriques
library(gmodels) # Pour créer des crosstables pour évaluer la performance des algorithmes
library(plyr)  #Recoder les variables

#Lire les données 
train<-read.csv("train.csv", header = TRUE, sep = ",")
head(train)

test<-read.csv("test.csv", header = TRUE,na.strings = "NA", sep = ",")
str(test)

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

#Traiter les données pour knn_multivarie et knn()
train_knn<-train[complete.cases(train),]  	#Enlever les lignes contenant des NA
train_Survived_knn<-train_knn$Survived          #Enregistre les cathégories dans un vecteur indépendant
str(train_Survived_knn)
  	 
train_knn<-train_knn[,!names(train_knn) %in%c("id","PassengerId","Ticket","Cabin","Embarked", "Survived", "Name")]      #Enlever les variables qui nous intéressent pas
train_knn$Sex <-revalue(train_knn$Sex, c("female"="0","male"="1" )) 									#Recodage du facteur Sex
train_knn$Sex<-as.numeric(levels(train_knn$Sex))[train_knn$Sex]   									#Change le facteur Sex en variable numérique
str(train_knn)
sum(is.na(train_knn))

test_knn<-test[,!names(test) %in%c("id","PassengerId","Ticket","Cabin","Embarked", "Name")]
#####test_knn$Pclass<-as.numeric(levels(test_knn$Pclass))[test_knn$Pclass]   	#Change le facteur Pclass en variable numérique
str(test_knn)
test_knn$Sex <-revalue(test_knn$Sex, c("female"="0","male"="1" )) 			#Recodage du facteur Sex
test_knn$Sex<-as.numeric(levels(test_knn$Sex))[test_knn$Sex]   	#Change le facteur Sex en variable numérique

str(test_knn)
sum(is.na(test_knn))

#Estimer les NA dans les données à prévoir

sum(is.na(test_knn$Pclass))                                                             #Aucun NA dans Pclass de la banque de données test
sum(is.na(test_knn$Sex))                                                                #Aucun NA dans Sex
sum(is.na(test_knn$Age)) 										    #86 NA dans la colonne age, alors on devra estimer leurs valeurs

Cath_age<-test_knn
Cath_age$Child<-NA
Cath_age$Child[Cath_age$Age<12]<-1
Cath_age$Child[Cath_age$Age>=12]<-0
head(Cath_age)
est_Age<-glm(Child~SibSp+ Parch+Fare , data=Cath_age, family = binomial())
summary(est_Age)													#

logisticPseudoR2s <- function(LogModel) {
	dev <- LogModel$deviance 
	nullDev <- LogModel$null.deviance 
	modelN <-  length(LogModel$fitted.values)
	R.l <-  1 -  dev / nullDev
	R.cs <- 1- exp ( -(nullDev - dev) / modelN)
	R.n <- R.cs / ( 1 - ( exp (-(nullDev / modelN))))
	cat("Pseudo R^2 for logistic regression\n")
	cat("Hosmer and Lemeshow R^2  ", round(R.l, 3), "\n")
	cat("Cox and Snell R^2        ", round(R.cs, 3), "\n")
	cat("Nagelkerke R^2           ", round(R.n, 3),    "\n")
      }
logisticPseudoR2s(est_Age)
test_knn$Age[is.na(test_knn$Age)]<-predict(est_Age, test_knn[is.na(test_knn$Age),])

sum(is.na(test_knn$SibSp)) 											#0 NA dans la colonne SibSp 
sum(is.na(test_knn$Parch)) 											#0 NA dans la colonne Parch 
sum(is.na(test_knn$Fare)) 											#1 NA dans la colonne Fare, alors on devra estimer cette valeur manquante
est_Fare<-lm(Fare~Pclass+Sex+Age+SibSp+Parch , data=Cath_age,na.action=na.omit)
summary(est_Fare)
test_knn$Fare[is.na(test_knn$Fare)]<-predict(est_Fare, test_knn[is.na(test_knn$Fare),])
sum(is.na(test_knn$Fare)) 


#Prévisions avec knn_multivarie()
prev_test<-knn_multivarie (test_knn , train_knn,  train$Survived, 50)
str(prev_test)
head(prev_test, n=50)
sum(prev_test<=0.5)

#Prévisions avec knn() 
prev_test<-knn(train=train_knn, test=test_knn, cl= train_Survived_knn, k=100)
prev_test

my_solution <- data.frame(PassengerId = test$PassengerId,Survived = prev_test)
write.csv(my_solution, file = "Titanic_knn2.csv", row.names = FALSE)

#Prévision avec les variables cathégoriques

#Traiter les données
train_knn_cat<-train[complete.cases(train),]  	#Enlever les lignes contenant des NA
train_Survived_knn_cat<-train_knn_cat$Survived          #Enregistre les cathégories dans un vecteur indépendant
str(train_Survived_knn_cat)
sum(is.na(train_Survived_knn_cat))
train_knn_cat<-train_knn_cat[,!names(train_knn_cat) %in%c("id","PassengerId","Cabin","Ticket", "Name")]   #Enlever les variables qui nous intéressent pas
str(train_knn_cat)
str(train)
sum(is.na(train_knn_cat))


test_knn_cat<-test
test_knn_cat<-test_knn_cat[,!names(test) %in%c("id","PassengerId","Cabin","Ticket", "Name")]
test_knn_cat["Survived"]<-train_Survived_knn_cat[1:330,]
str(test_knn_cat)
sum(is.na(test_knn_cat))

#knncat() 
objet_knncat<-knncat(train=train_knn_cat, cl= 1, k=500)
objet_knncat

#Prévision 
prev_knncat<-predict(objet_knncat, train_knn_cat, test_knn_cat, train.classcol=1, newdata.classcol=0)

#Calcul de la rmse


my_solution <- data.frame(PassengerId = test$PassengerId,Survived = prev_knncat)
write.csv(my_solution, file = "Titanic_knn.csv", row.names = FALSE)



