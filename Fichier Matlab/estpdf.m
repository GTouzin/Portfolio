function [ p ] = estpdf( y,it )

    step=(max(y,[],1)-min(y,[],1))/it;
    p=zeros(it+1,2);
    borne=min(y);
    a=1;

    while a<=length(p)
        p(a,1)=borne;
        borne=borne+step;
        a=a+1;
    end

    for b=1:length(y)
        a=1;
        while(a<=length(p))
            if p(a,1)<y(b)
               a=a+1;
            else
               p(a,2)=p(a,2)+1;
               a=a+1;
            end  
         end
    end
    
    temp=0;
    while temp==0
        mattemp=zeros(1,2);
        temp=1;
     if p(2,2)-p(1,2)>1
             t=zeros((p(a+1,2)-p(a,2))-1,2);
             te=(p(2,1)-p(1,1))/(p(2,2)-p(1,2));                
             for b=1:(p(2,2)-p(1,2)-1)
                 t(b,1)=p(1,1)+b*te;
             end
             mattemp=t;
             temp=0;
         else
             mattemp=p(1,:);
      end

    for a=2:length(p)-1
        if p(a+1,2)-p(a,2)>1
             t=zeros((p(a+1,2)-p(a,2))-1,2);
             te=(p(a+1,1)-p(a,1))/(p(a+1,2)-p(a,2));                
             for b=1:((p(a+1,2)-p(a,2))-1)
                 t(b,1)=p(a,1)+b*te;
             end
             mattemp=vertcat(mattemp,t);
             temp=0;
        else
             mattemp=vertcat(mattemp,p(a,:));
        end
    end
    
    p=zeros(length(mattemp),2);
    p=mattemp;
    p(:,2)=zeros(length(p),1);
    for b=1:length(y)
        if length(y)>1
            a=1;
            while(a<=length(p))
                if p(a,1)<y(b)
                    a=a+1;
                else
                    p(a,2)=p(a,2)+1;
                    a=a+1;
                end  
            end
        end
    end
    end
    
     p(:,2)=p(:,2)/100;
    
end


        


