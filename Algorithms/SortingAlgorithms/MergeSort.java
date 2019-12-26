public class MergeSort{
	public static void main(String[] args){
		int[] array = new int[]{9, 5, 2, 7, 6, 7, 3, 4};
		merge(array,0,array.length-1);
		for(int n : array)
			System.out.print(n + " ");
	}

	public static void merge(int[] arr, int left, int right){
		if(left < right){
			int	mid = (left+right)/2;
			merge(arr, left, mid);
			merge(arr, mid+1, right);
			sort(arr, left, mid, right);	
		}
	}

	public static int[] sort(int[] arr, int left, int mid, int right){
		int[] leftArray = new int[1+mid-left];
		int[] rightArray = new int[right-mid];
		
		for(int i = 0; i < leftArray.length; i++)
			leftArray[i] = arr[left+i];
		for(int i = 0; i < rightArray.length; i++)
			rightArray[i] = arr[mid+1+i];	

		int i = 0;
		int j = 0;
		int k = left;
		while(i < leftArray.length && j < rightArray.length && k <= right){
			if(leftArray[i] <= rightArray[j])
				arr[k++] = leftArray[i++];	
			else if(rightArray[j] < leftArray[i]) 
				arr[k++] = rightArray[j++];
		}
		while(i<leftArray.length)
			arr[k++] = leftArray[i++];
		while(j<rightArray.length)
			arr[k++] = rightArray[j++];
		return arr;
	}
}
