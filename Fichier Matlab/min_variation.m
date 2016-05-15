function [ min ] = min_variation( y )
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
min=0;
if length(y)>2
    n=length(y);
    min=abs(y(1)-y(2));
    for a=2:n
        temp=abs(y(n)-y(n-1));
        if temp<min
            min=temp;
        end
    end
end

