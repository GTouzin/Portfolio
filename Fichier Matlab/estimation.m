function [ p ] = estimation( y )
interval=max(y)-min(y);
if (interval==0||min_variation(y)==0)
    p=zeros(1,2);
else
    step=min_variation(y)/2;
    n=2*(floor(interval/step))+1;
    p=zeros(n,2);
    borne=interval*(-1);
    a=1;

    while a<=n
        p(a,1)=borne;
        borne=borne+step;
        a=a+1;
    end

    for b=2:length(y)
        if length(y)>2
            dif=y(b)-y(b-1);
            a=1;
            temp=1;
            while(temp==1&&a<n)
                if p(a,1)<dif
                    a=a+1;
                else
                    temp=0;
                    p(a,2)=p(a,2)+1;
                end  
            end
        end
    end
end


        

