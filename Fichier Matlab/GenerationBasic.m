function [m]= GenerationBasic( fdp, point )
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
a=length(fdp);
ref=fdp;
p=zeros(1,1);


for x=1:a
    n=floor(point/length(ref));
    borneinf=ref(x,1);
    bornesup=ref(x,2);
    x0=random('uniform',borneinf,bornesup,n,1);
    p=vertcat(p,x0); 
end
m=p(2:length(p),:);
end

