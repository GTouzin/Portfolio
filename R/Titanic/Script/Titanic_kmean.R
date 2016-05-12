#Finir cross-validation
#Définir le dossier contenant les fichiers de données
setwd("E:/Users/Guillaume/Documents/R/Project/Titanic/Data")
getwd()

#library à utiliser
library(gmodels) # Pour créer des crosstables pour évaluer la performance des algorithmes
library(plyr)  #Recoder les variables
library(car)
library(mlogit)
library("clValid") #Dunn index
library(DeducerExtras) #predict.kmeans
library(rpart)
library(class)

#Lire les données 
train<-read.csv("train.csv", header = TRUE, sep = ",")
str(train)

test<-read.csv("test.csv", header = TRUE, sep = ",")
str(test)

#Kmeans

#Traitement des données
train_kmean <-train[, !names(train)%in% c("id","Name","PassengerId","Cabin","Survived","Ticket")]
train_type<-train[, !names(train_kmean)%in% c("id","Name","PassengerId","Cabin","Ticket")]                  #Data.frame qui désigne dans quel cluster les passager sont classés
train_kmean[train_kmean==""]<-NA
train_type[train_type==""]<-NA
#train_kmean<-train_kmean[complete.cases(train_kmean),]  								#Enlève les NA
#train_type<-train_type[complete.cases(train_type),]

test_kmean <-test[, !names(test)%in% c("id","Name","PassengerId","Cabin","Ticket")]
str(test_kmean)

train_kmean$Sex <-revalue(train_kmean$Sex, c("female"="0","male"="1" )) 					#Recodage du facteur Sex
train_kmean$Sex<-as.numeric(levels(train_kmean$Sex))[train_kmean$Sex]   					#Change le facteur Sex en variable numérique
test_kmean$Sex <-revalue(test_kmean$Sex, c("female"="0","male"="1" )) 				
test_kmean$Sex<-as.numeric(levels(test_kmean$Sex))[test_kmean$Sex]   				

levels(train_kmean$Embarked)
train_kmean$Embarked <-revalue(train_kmean$Embarked, c("C"="0","Q"="1", "S"=2)) 				#Recodage du facteur Embarked
train_kmean$Embarked<-as.numeric(levels(train_kmean$Embarked))[train_kmean$Embarked]   			#Change le facteur Sex en variable numérique
test_kmean$Embarked <-revalue(test_kmean$Embarked, c("C"="0","Q"="1", "S"=2)) 				
test_kmean$Embarked<-as.numeric(levels(test_kmean$Embarked))[test_kmean$Embarked] 

sum(is.na(test_kmean$Pclass))                                                             #Aucun NA dans Pclass de la banque de données test
sum(is.na(test_kmean$Sex))                                                                #Aucun NA dans Sex
sum(is.na(test_kmean$Age)) 											#86 NA dans la colonne age, alors on devra estimer leurs valeurs
sum(is.na(test_kmean$SibSp)) 											#0 NA dans la colonne SibSp 
sum(is.na(test_kmean$Parch)) 											#0 NA dans la colonne Parch 
sum(is.na(test_kmean$Fare)) 											#1 NA dans la colonne Fare, alors on devra estimer cette valeur manquante

#Estimation par régression logistique
Cath_age<-test_kmean
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
logisticPseudoR2s(est_Age)									#Nagelkerke R^2            0.267 et Cox and Snell R^2         0.106 
test_kmean$Age[is.na(test_kmean$Age)]<-predict(est_Age, test_kmean[is.na(test_kmean$Age),])

#Estimation de la variable Age avec lm
est_Fare<-lm(Fare~Pclass+Sex+Age+SibSp+Parch+Embarked , data=Cath_age,na.action=na.omit)
summary(est_Fare)
test_kmean$Fare[is.na(test_kmean$Fare)]<-predict(est_Fare, test_kmean[is.na(test_kmean$Fare),])
sum(is.na(test_kmean$Fare)) 


#Estimation des valeur de la variable Age avec knn
cl_knn<-Cath_age[!is.na(Cath_age$Age),names(Cath_age)%in%c("Age") ]
tableau_train_knn<-Cath_age[!is.na(Cath_age$Age),!names(Cath_age)%in%c("Child", "Age") ]
tableau_train_knn$Fare[is.na(tableau_train_knn$Fare)]<-mean(tableau_train_knn$Fare, na.rm=TRUE)   #REmplace la valeur manquante par la moyenne
str(tableau_train_knn)
tableau_test_knn<-Cath_age[is.na(Cath_age$Age),!names(Cath_age)%in%c("Age", "Child") ]
str(tableau_test_knn)

tableau_test_knn$Age<-knn(train=tableau_train_knn, test=tableau_test_knn, cl= cl_knn, k=50)
test_kmean$Age[is.na(test_kmean$Age)]<-as.numeric(tableau_test_knn$Age)

#Mise à l'échelle pour kmeans
train_kmean_scale<-train_kmean
train_kmean_scale<-scale(train_kmean_scale)
train_kmean_scale<-data.frame(train_kmean_scale)
train_kmean_scale$type<-train_type$Survived
str(train_kmean_scale)
sum(is.na(train_kmean))
test_kmean_scale<-scale(test_kmean)

#Kmean
#Cross-validation
	n <- nrow(train_kmean_scale)
	shuffled <- train_kmean_scale[sample(n),]
	accs<-rep(0,10)
str(train_kmean_scale)
	for (i in 1:10) {
 		 # These indices indicate the interval of the test set
 		 indices <- (((i-1) * round((1/10)*nrow(shuffled))) + 1):((i*round((1/10) * nrow(shuffled))))
  
  		# Exclude them from the train set
  		train_temp <- shuffled[-indices,]
  			
  		# Include them in the test set
 		 test_temp <- shuffled[indices,]
  
  		# A model is learned using each training set
  		model_km <- kmeans(train_temp, center=2, nstart=5)
  
  		# Make a prediction on the test set using tree
  		pred<-predict.kmeans(model_km, test_temp)
		
		
  		# Assign the confusion matrix to conf
  		conf<-table(test_temp$type,pred)
  
  		# Assign the accuracy of this model to the ith index in accs
  		 accs[i]<-sum(diag(conf))/sum(conf)
	}

# Print out the mean of accs
mean(accs)             #accuracy = 0.59



train_km<-kmeans(train_kmean_scale, center=2, nstart=5000)
str(train_type)
table(train_km$cluster, train_type$Survived)
dunn_km<-dunn(cluster=train_km$cluster, Data=train_kmean_scale)
dunn_km
prev_kmean<-predict.kmeans(train_km,test_kmean_scale)
sum(prev_kmean==2)
sum(prev_kmean==1)

prev_kmean[prev_kmean==1]<-0
prev_kmean[prev_kmean==2]<-1
prev_kmean

#Kmean
#Cross-validation
	n <- nrow(train_kmean_scale)
	shuffled <- train_kmean_scale[sample(n),]
	accs<-rep(0,10)
str(train_kmean_scale)
	for (i in 1:10) {
 		 # These indices indicate the interval of the test set
 		 indices <- (((i-1) * round((1/10)*nrow(shuffled))) + 1):((i*round((1/10) * nrow(shuffled))))
  
  		# Exclude them from the train set
  		train_temp <- shuffled[-indices,]
  			
  		# Include them in the test set
 		 test_temp <- shuffled[indices,]
  
  		# A model is learned using each training set
  		tree <- rpart(Survived~., train_temp, method = "class")
  
  		# Make a prediction on the test set using tree
  		pred<-predict(tree, test_temp, type="class")
  
  		# Assign the confusion matrix to conf
  		conf<-table(test_temp$type,pred)
  
  		# Assign the accuracy of this model to the ith index in accs
  		 accs[i]<-sum(diag(conf))/sum(conf)
	}

# Print out the mean of accs
mean(accs)


my_solution <- data.frame(PassengerId = test$PassengerId,Survived = prev_kmean)
write.csv(my_solution, file = "Titanic_kmeans2.csv", row.names = FALSE)

