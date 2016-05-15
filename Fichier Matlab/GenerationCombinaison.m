function [m]= GenerationCombinaison( fdp,point )
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
a=length(fdp);
t=fdp;
r=a+1;
p=zeros(1,1);

%Interval et probabilité associées
ref=zeros((a*(a-1))/2,6);
enum=zeros(r,2);

for x=1:a
    enum(x,1)=t(x,1);
    enum(x,2)=x;
end
enum(r,1)=t(a,2);
enum(r,2)=r;

count=1;
for x=1:a
    temp=t(x,3);
    ref(count,1)=t(x,1);
    ref(count,2)=t(x,2);
    ref(count,3)=temp;
    
    count=count+1;
    for y=x+1:a
        ref(count,1)=t(x,1);
        ref(count,2)=t(y,2);
        temp=temp+t(y,3);
        ref(count,3)=temp;
        count=count+1;
    end
end 
%position de la borne inférieure
for x=1:length(ref)
    temp=ref(x,1);
    count=1;
    while enum(count,1)~=temp
        count=count+1;
    end
    ref(x,4)=enum(count,2);
end
%position de la borne supérieure
for x=1:length(ref)
    temp=ref(x,2);
    count=1;
    while enum(count,1)~=temp
        count=count+1;
    end
    ref(x,5)=enum(count,2);
end
%calcul de la surreprésentation
for x=1:length(ref)
    ref(x,6)=(r-ref(x,5)+1)*ref(x,4);
end
if floor(point/length(ref))>0
   n=floor(point/length(fdp));
else
   n=1;
end

%génération des points
for x=1:length(ref)
    borneinf=ref(x,1);
    bornesup=ref(x,2);
    x0=random('uniform',borneinf,bornesup,n,1);
    p=vertcat(p,x0); 
end
m=p(2:length(p),:);
ref
m=sort(m)

%élimination des points

while length(m)>point
for x=1:length(ref)
    borneinf=ref(x,1);
    bornesup=ref(x,2);
    count=1;
    while m(count,1)<borneinf
        count=count+1;
    end
    tempinf=count;
    
    count=1;
    while count<length(m)&&m(count,1)<bornesup
        count=count+1;
    end
    tempsup=count;
    
    facteur=ref(x,6);
    
    count=1;
    while count<=facteur
        x0=floor(random('uniform',tempinf,tempsup,1,1));
        if tempsup-tempinf<=0
            tempinf
            tempsup
            borneinf
            bornesup
            count=facteur+3;
        else
            c=x0(1,1);
            m=vertcat(m(1:(c-1),1),m((c+1):length(m),1));
            tempsup=tempsup-1;
            count=count+1;
        end
    end
end

end
end

