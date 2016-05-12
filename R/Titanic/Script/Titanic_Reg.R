#Améliorer la prédiction des age manquant dans test
#Tester les différents seuil de Survie (présentement 0.5)
#Faire la regression log
#Tester sur le train




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

test<-read.csv("test.csv", header = TRUE, sep = ",")
str(test)

#Création des facteurs dans le data.frame train
train$Pclass <-factor(train$Pclass, levels=c(3,2,1), ordered=TRUE)
str(train)
summary(train)
test$Pclass <-factor(test$Pclass, levels=c(3,2,1), ordered=TRUE)
str(train)


Cath_age<-test
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
test$Age[is.na(test$Age)]<-predict(est_Age, test[is.na(test$Age),])

sum(is.na(test$SibSp)) 											#0 NA dans la colonne SibSp 
sum(is.na(test$Parch)) 											#0 NA dans la colonne Parch 
sum(is.na(test$Fare)) 											#1 NA dans la colonne Fare, alors on devra estimer cette valeur manquante
est_Fare<-lm(Fare~Pclass+Sex+Age+SibSp+Parch+Embarked , data=Cath_age,na.action=na.omit)
summary(est_Fare)
test$Fare[is.na(test$Fare)]<-predict(est_Fare, test[is.na(test$Fare),])
sum(is.na(test$Fare)) 

#################################################################################
#Regression linéaire 
titanic_lm<- lm(Survived~ Pclass+Sex+Age+SibSp+Parch+Fare+Embarked  , data=train) 
summary(titanic_lm)
prev_lm<-predict(object=titanic_lm, newdata=test)
str(prev_lm)
head(prev_lm)
prev_lm[1]
my_solution<-rep(NA, nrow(test))
my_solution[prev_lm>0.5]<-1
my_solution[prev_lm<=0.5]<-0
sum(is.na(my_solution))
head(my_solution)
tableau_final<-data.frame(PassengerId =test$PassengerId, Survived=my_solution)
write.csv(tableau_final, "Titanic_LM.csv", row.names=FALSE)

#Test pour l'autocorrélation
vif(titanic_lm)
dwt(titanic_lm)

#Histogramme vérifiant la normalité des résidues
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


#Deuxième modèle
titanic_lm2<- lm(Survived~ Pclass+Sex+Age+SibSp  , data=train) 
summary(titanic_lm2)


#Comparer les modèles par anova
anova(titanic_lm, titanic_lm2)

#---Histogram-----
hist(rstudent( titanic_lm2))

#-----Confidence intervals-----
confint( titanic_lm2)

#Traiter les données (selecter les colomnes et gérer les données abscentes)
train_knn<-train[,!names(train) %in%c("PassengerId","Survived", "Name")]
str(train_knn)
train_knn<-train[complete.cases(train_knn),]

test_knn<-test[,!names(test) %in%c("PassengerId", "Name")]
str(test_knn)
test_knn<-test[complete.cases(test_knn),]


#Prédiction pour les valeurs du data.frame train
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


#Prédiction pour les valeurs du data.frame test
surviver_pred<-predict( titanic_lm2, test, interval="predict")
surviver_pred<-data.frame(surviver_pred)


#Calcul de la rmse




