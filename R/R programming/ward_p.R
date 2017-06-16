#Parameters and variable used


#function which return the centers for a given exponent p. The format is row= observation and column=feature.
New_cmt<-function(Data,p){
N<-dim(Data)[1]
M<-dim(Data)[2]  

    
if (p==1){
    DataCenter<-matrix(apply(Data,2, median),nrow=1,ncol=M)
 } else if (p==2){
    DataCenter<-matrix(apply(Data,2, mean),nrow=1,ncol=M)
} else if (N==1){
    DataCenter<-Data
}else{

        Gradient<-matrix(rep(0.001,M),nrow=1,ncol=M)
        DataCenter <- matrix(colSums(Data, na.rm = TRUE)/N,nrow=1,ncol=M)
        DistanceToDataCenter<-matrix(colSums(abs(Data - matrix(DataCenter,nrow=dim(Data)[1],ncol=dim(DataCenter)[2],byrow=TRUE))^p,na.rm=TRUE),nrow=1,ncol=M)
        NewDataCenter<-DataCenter+Gradient
        DistanceToNewDataCenter<-matrix(colSums(abs(Data - matrix(NewDataCenter,nrow=dim(Data)[1],ncol=dim(NewDataCenter)[2],byrow=TRUE))^p,na.rm=TRUE),nrow=1,ncol=M)
        Gradient[1,DistanceToDataCenter < DistanceToNewDataCenter] <- Gradient[1,DistanceToDataCenter < DistanceToNewDataCenter]*(-1)

        while (sum(abs(Gradient)>0.0001)!=0){           
            NewDataCenter <-DataCenter+ Gradient
            DistanceToNewDataCenter<-matrix(colSums(abs(Data - matrix(NewDataCenter,nrow=dim(Data)[1],ncol=dim(NewDataCenter)[2],byrow=TRUE))^p),nrow=1,ncol=M)
            Gradient[1,DistanceToNewDataCenter >= DistanceToDataCenter] <- Gradient[1,DistanceToNewDataCenter>=DistanceToDataCenter]*0.9
            DataCenter[1,DistanceToNewDataCenter<DistanceToDataCenter]<-NewDataCenter[1,DistanceToNewDataCenter<DistanceToDataCenter]
            DistanceToDataCenter[1,DistanceToNewDataCenter<DistanceToDataCenter]<-DistanceToNewDataCenter[1,DistanceToNewDataCenter<DistanceToDataCenter] 
            }   
    }

    return  (DataCenter)
}

#Update the number of entities per cluster.
Merge.NK<-function(k1Min, k2Min, Nk){
    Nk[k1Min,1]<-Nk[k1Min,1]+Nk[k2Min,1]
    Nk[k2Min,1]<-0
return(Nk)
}

#Update the labels of the cluster whose been merge after an iteration
Merge.U<-function(k1Min, k2Min, U){
    U[U==k2Min]<-k1Min
    temp<-matrix(U,nrow=length(U),ncol=1)
return(temp)
}

#Update the centroid of each cluster 
Merge.Z<-function(k1Min, k2Min, U, Data, M, Z,p){
    Z[k1Min,] <- New_cmt(Data[U==k1Min,],p)
    Z[k2Min,] <- matrix(Inf,nrow=1,ncol=M)
return(Z)
}

#Update the counter K after each iteration
Merge.K<-function(K){
    K <- K-1
return(K)
}

#Function which calculate the new weigths
GetNewW<-function(Data,W, U, Z, K, M,p){
    D<-matrix(0,nrow=K,ncol=M)
    for (l in 1 : K){
    for (j in 1 : M){
        D[l,j] <- sum((abs(Data[U==l,j]- Z[l,j]))^p,na.rm=TRUE) 
      }
    }
    D <- D + 0.0001
    #Calculate the actual Weight for each column
        
    if (p!=1){
        exponent <- 1/(p-1)
        for (l in 1 : K){
            for (j in 1 : M){
                 tmp<-D[l,j]
                 W[l,j]<- 1/sum((matrix(tmp,nrow=1,ncol=M)/D[l,])^exponent,na.rm=TRUE)
                
            }
        }
    } else{
    for (l in 1 : K){
        MinIndex <- which(D[l,]==min(D[l,],na.rm=TRUE), arr.ind = TRUE)[1] 
        W[l,1:M] <- rep(0,M)     #necessary to zero all others
        W[l,MinIndex]<- 1
        }
    }
 return (W)       
}

#Function which calculate the between each element and the new centroid
Update_AllDistances<-function(Z,W,p,Nk,AllDistances, k1Min, k2Min, InitialK){
#remove k2 from Z
AllDistances[k2Min,]<-NA
AllDistances[,k2Min]<-NA 

#update the distances related to z1
for (k in 1 : InitialK){
    if (k!=k1Min&&Nk[k,1]!=0&&k!=k2Min){        
    avg_W <- ((W[k1Min,] + W[k,])/2)^p
        AllDistances[k1Min,k] <- ((Nk[k1Min,1] * Nk[k,1])/(Nk[k1Min,1] + Nk[k,1])) * sum((abs(Z[k1Min,] - Z[k,])^p)*avg_W,na.rm=TRUE)
        AllDistances[k,k1Min]<-AllDistances[k1Min,k]
    }
   } 
return (AllDistances)
}



#Core function of the Wardp algorithm
MW_WardFast<-function(Data,requiredK, p){
  
#Standardization of the features  
#Data<-scale(Data)
#Data<-as.data.frame(Data)

#Get the dimension of the data
N<-dim(Data)[1]
M<-dim(Data)[2]

#Labels
U <- matrix(1:N, nrow=N,ncol=1)

#Number of iteration for the complete algorithm 
K<-N

#Cluster size
Nk<-matrix(1,nrow=N,ncol=1)  

#Centroid
Z <- Data    

#Weigth
W<-matrix(1/M, nrow=K,ncol=M)
    
InitialK<-K
AllDistances <- matrix(Inf,nrow=InitialK,ncol=InitialK)

#Set the initial distances between the observations
for (k1 in 1 : (InitialK-1)){    
    for (k2 in (k1+1) : InitialK){
        AllDistances[k1,k2] <- 0.5 * sum((abs(Z[k1,] - Z[k2,])^p)*(W[1,]^p),na.rm=TRUE); 
        AllDistances[k2, k1] <- AllDistances[k1,k2]
    }
}


UIndex<-1
mark<-TRUE
while (K>requiredK){  

    #look for clusters to merge
    k2Min<-which(AllDistances==min(AllDistances,na.rm=TRUE), arr.ind = TRUE)[1,1]
    k1Min<-which(AllDistances==min(AllDistances,na.rm=TRUE), arr.ind = TRUE)[1,2]
 
    #Merge clusters k1Min and k2Min
    UIndex <- UIndex+1
    
    Nk<-Merge.NK(k1Min, k2Min, Nk)
    temp.U<-Merge.U(k1Min, k2Min, U[,UIndex-1])
    Z<-Merge.Z(k1Min, k2Min, U[,UIndex-1], Data, M, Z,p)
    U<-cbind(U,temp.U)
    K<-Merge.K(K)   
    W <- GetNewW(Data,W, U[,UIndex], Z, InitialK, M, p)
    
    #Update the distances
    AllDistances<-Update_AllDistances(Z,W,p,Nk,AllDistances, k1Min, k2Min, InitialK)    
    
}
 
#Can return the weigth, the centroid, the number of element by cluster or the label depending on your need   
#return (U, Z, Nk, W) 

    return (U)
}

#Wardp algorithm which take a dissimilarity matrix as input
MW_WardFast_diss<-function(diss.mat,Data,requiredK, p){
 
  #Get the dimension of the data
  N<-dim(Data)[1]
  M<-dim(Data)[2]
  
  #Labels
  U <- matrix(1:N, nrow=N,ncol=1)
  
  #Number of iteration for the complete algorithm 
  K<-N
  
  #Cluster size
  Nk<-matrix(1,nrow=N,ncol=1)  
  
  #Centroid
  Z <- Data    
  
  #Weigth
  W<-matrix(1/M, nrow=K,ncol=M)
  
  InitialK<-K
  AllDistances <- diss.mat
  AllDistances[row(AllDistances)==col(AllDistances)]<-Inf
  
  
  UIndex<-1
  mark<-TRUE
  while (K>requiredK){  
    
    #look for clusters to merge
    k2Min<-which(AllDistances==min(AllDistances,na.rm=TRUE), arr.ind = TRUE)[1,1]
    print(k2Min)
    k1Min<-which(AllDistances==min(AllDistances,na.rm=TRUE), arr.ind = TRUE)[1,2]
    print(k1Min)
    
    #Merge clusters k1Min and k2Min
    UIndex <- UIndex+1
    
    Nk<-Merge.NK(k1Min, k2Min, Nk)
    temp.U<-Merge.U(k1Min, k2Min, U[,UIndex-1])
    Z<-Merge.Z(k1Min, k2Min, U[,UIndex-1], Data, M, Z,p)
    U<-cbind(U,temp.U)
    K<-Merge.K(K)   
    W <- GetNewW(Data,W, U[,UIndex], Z, InitialK, M, p)
    
    #Update the distances
    AllDistances<-Update_AllDistances(Z,W,p,Nk,AllDistances, k1Min, k2Min, InitialK)    
    
  }
  
  #Can return the weigth, the centroid, the number of element by cluster or the label depending on your need   
  #return (U, Z, Nk, W) 
  
  return (U)
}
















