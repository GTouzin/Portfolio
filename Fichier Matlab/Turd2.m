function [ output_args ] = Turd2( t )
x=zeros(t,1);
x(1)=rand;
y=zeros(t,1);

for a=2:t
    %g�n�re la variable d'�tat au temp t
    x(a)=random('normal',sin(t)*x(a-1),6);
    %g�n�re la variable observ�e au temp t
    y(a)=random('normal',x(a),2);
    
end

prediction=zeros(t,1);






mse=mse/t

hold on
plot(x(1:end),'k','linewidth',2)
plot(y(1:end),':r*','linewidth',2)
plot(prediction(1:end),':go','linewidth',2)
xlabel('time')
ylabel('state')
h = legend('x','y','prediction');
end


