#Séparer les données en deux



#Nécéssite library(rpart)
cross_validation<- function(data, nseg, type)
{

	n <- nrow(data)
	shuffled <- data[sample(n),]
	accs<-rep(0,nseg)

	for (i in 1:nseg) {
 		 # These indices indicate the interval of the test set
 		 indices <- (((i-1) * round((1/nseg)*nrow(shuffled))) + 1):((i*round((1/nseg) * nrow(shuffled))))
  
  		# Exclude them from the train set
  		train <- shuffled[-indices,]
  
  		# Include them in the test set
 		 test <- shuffled[indices,]
  
  		# A model is learned using each training set
  		tree <- rpart(type~., train, method = "class")
  
  		# Make a prediction on the test set using tree
  		pred<-predict(tree, test, type="class")
  
  		# Assign the confusion matrix to conf
  		conf<-table(test$type,pred)
  
  		# Assign the accuracy of this model to the ith index in accs
  		 accs[i]<-sum(diag(conf))/sum(conf)
	}

# Print out the mean of accs
mean(accs)

}