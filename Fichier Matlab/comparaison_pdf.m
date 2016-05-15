function [ m ] = comparaison_pdf( x1,x2 )

n=length(x1);
p=zeros(n,6);
x=sort(x1);
p(:,1)=x;
p(1,2)=x(1)-rand/2;
p(1,3)=2*x(1)-p(1,1);
for a=2:n-1
    interval=x(a+1)-x(a);
    p(a,2)=p(a-1,3);
    p(a,3)=x(a)+(interval/2);   
end
p=p(2:n-1,:);

for a=1:length(p);
    b=1;
    while(b<=length(x1))
        if p(a,1)<x1(b)
           b=b+1;
        else
           p(a,4)=p(a,4)+1;
           b=b+1;
        end  
    end
end
for a=1:length(p);
    b=1;
    while(b<=length(x2))
        if p(a,1)<x2(b)
           b=b+1;
        else
           p(a,5)=p(a,5)+1;
           b=b+1;
        end  
    end
end
for a=1:length(p);

    p(a,6)=p(a,5)/p(a,4);

end
m=p(:,2:6);

end

