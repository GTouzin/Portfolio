

function X= Uniforme(Taille)
n=Taille; V=1:n;

for i=1:n
rng('shuffle');
V(i)=rand(0,1);
end;

X=V;
