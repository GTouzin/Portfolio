
public class Chromosome {
	
	public int[] array=new int[7];
	
	public Chromosome(int x1, int y1, int x2,int y2, int b1, int g1, int r1)
	{
		
		array[0]=GrayCode.binaryToGray(x1);
		array[1]=GrayCode.binaryToGray(y1);
		array[2]=GrayCode.binaryToGray(x2);
		array[3]=GrayCode.binaryToGray(y2);
		array[4]=GrayCode.binaryToGray(b1);
		array[5]=GrayCode.binaryToGray(g1);
		array[6]=GrayCode.binaryToGray(r1);
			
	}
	
	public int[] getArray()
	{
		return array;
	}
	
	
		
		    
	

}
