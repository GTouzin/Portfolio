function [m]= Generation( fdp )
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
a=length(fdp);
b=factorial(a)/((factorial(a)-2)*2);
interval=zeros(b,3);
ref=zeros(a-1,3);
count=1;

for x=1:a
    for y=x+1:a
        interval(count,1)=fdp(x,1);
        interval(count,2)=fdp(y,1);
        interval(count,3)=fdp(y,2)-fdp(x,2);
        count=count+1;
    end
end

for x=1:a-1
    ref(x,1)=fdp(x+1,1);
    ref(x,2)=fdp(x,1);
    ref(x,3)=fdp(x+1,2)-fdp(x,2);    
end

m=interval;
end

