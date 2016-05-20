
public class GrayCode {
	
	
	/*
    The purpose of this function is to convert an unsigned
    binary number to reflected binary Gray code.

    The operator >> is shift right. The operator ^ is exclusive or.
*/
public static int binaryToGray(int n)
{
	return n ^ (n >>> 1);
}

/*
    The purpose of this function is to convert a reflected binary
    Gray code number to a binary number.
*/
public static int grayToBinary(int n)
{	int p = n;
	while ((n >>>= 1) != 0)
		p ^= n;
	return p;
}
	
	 // append reverse of order n gray code to prefix string, and print
    public static void yarg(String prefix, int n) {
        if (n == 0) System.out.println(prefix);
        else {
            gray(prefix + "1", n - 1);
            yarg(prefix + "0", n - 1);
        }
    }  

    // append order n gray code to end of prefix string, and print
    public static void gray(String prefix, int n) {
        if (n == 0) System.out.println(prefix);
        else {
            gray(prefix + "0", n - 1);
            yarg(prefix + "1", n - 1);
        }
    }  

}
