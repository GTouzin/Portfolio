function [ output_args ] = distribuer( bornesup,borneinf,ref,matrice,point )

x0=random('uniform',borneinf,bornesup,point,1);
p=vertcat(x0, matrice);
p=sort(p);



end

