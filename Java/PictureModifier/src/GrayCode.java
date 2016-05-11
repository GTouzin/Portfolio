
public class GrayCode {
	
	
public static int binaryToGray(int n)
{
	return n ^ (n >>> 1);
}


public static int grayToBinary(int n)
{	int p = n;
	while ((n >>>= 1) != 0)
		p ^= n;
	return p;
}
	
	
    public static void yarg(String prefix, int n) {
        if (n == 0) System.out.println(prefix);
        else {
            gray(prefix + "1", n - 1);
            yarg(prefix + "0", n - 1);
        }
    }  


    public static void gray(String prefix, int n) {
        if (n == 0) System.out.println(prefix);
        else {
            gray(prefix + "0", n - 1);
            yarg(prefix + "1", n - 1);
        }
    }  

}
