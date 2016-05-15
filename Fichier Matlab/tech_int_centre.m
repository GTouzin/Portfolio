function [ m ] = tech_int_centre( y )
%cas ou point<length(y)
%rajouter des points milieu dans les intervals pour lisser le graph lorsque
%point>length?
n=length(y);
p=zeros(n,4);
x=sort(y);
p(:,1)=x;
p(1,2)=x(1)-rand/2;
p(1,3)=2*x(1)-p(1,1);
for a=2:n-1
    interval=x(a+1)-x(a);
    p(a,2)=p(a-1,3);
    p(a,3)=x(a)+(interval/2);   
end
interval=x(n)-x(n-1);
    p(n,2)=p(n-1,3);
    p(n,3)=x(n)+(interval/2);  

for a=1:length(p);
    b=1;
    while(b<=length(y))
        if p(a,1)<y(b)
           b=b+1;
        else
           p(a,4)=p(a,4)+1;
           b=b+1;
        end  
    end
end

m=p(:,2:4);
m(:,3)=m(:,3)/length(m);

end

