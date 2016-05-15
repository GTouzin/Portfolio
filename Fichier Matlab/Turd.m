function [ x,y,prediction ] = Turd( t )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
x=zeros(t,1);
x(1)=rand;
y=zeros(t,1);

for a=2:t
    %génère la variable d'état au temp t
    x(a)=random('normal',sin(t)*x(a-1),6);
    %génère la variable observée au temp t
    y(a)=random('normal',x(a),2);
    
end

prediction=zeros(t,1);
for a=2:t
    fmasse=estimation(y(1:a,1));
    fmax=max(fmasse(:,2));
    count=1;
    temp=1;
    while fmax~=fmasse(count,2)
        temp=fmasse(count,1);
        count=count+1;        
    end
    p=temp;
    prediction(a)=y(a-1,1)+p;
    
end
mse=0;
for a=1:t
    mse=mse+(prediction(a)-x(a))^2;
end
mse=mse/t

hold on
plot(x(1:end),'k','linewidth',2)
plot(y(1:end),':r*','linewidth',2)
plot(prediction(1:end),':go','linewidth',2)
xlabel('time')
ylabel('state')
h = legend('x','y','prediction');
end

