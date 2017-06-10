%Méthode de Box-Muller pour générer une variable distibuée selon N(0,1) 


function X= inversion(Taille,moyenne,variance)
n=Taille; u=moyenne; var=variance;
if mod(n,2)==0
   m=n/2;
else 
    m=(n+1)/2;
end

%Génère une loi N(0,1)
U1= Uniforme(m); U2= Uniforme(m); V=1:n;

for i=1:m
temp1= sqrt(-2*ln(U1(i)))*cos(2*pi*U2);
temp2= sqrt(-2*ln(U1(i)))*sin(2*pi*U2);
V(i)=temp1;
if (m+i)<=n
    V(m+i)=temp2;
end
end

%Transforme la N(0,1) en une loi N(u,var)
if var~=1
    for i=1:n
        V(i)=V(i)*var;
    end
end
if u~=0
    for i=1:n
        V(i)=V(i)+u;
    end
end





