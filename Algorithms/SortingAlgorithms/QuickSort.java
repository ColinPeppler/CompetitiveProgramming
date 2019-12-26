public class QuickSort{
	public static void main(String[] args){
		int[] array = new int[]{1, 7, 5, 9, 2, 2, 0, 4};
		quick(array,0,array.length-1);
		for(int n : array)
			System.out.print(n + " ");
	}

	public static void quick(int[] arr, int left, int right){
		if(left < right){
			int pivot = sort(arr,left,right);
			quick(arr, left, pivot-1);
			quick(arr, pivot+1, right);			
		}
	}

	public static int sort(int[] arr, int left, int right){
		int i = left;
		for(int j = left; j < right; j++){
			if(arr[j] < arr[right]){
				swap(arr,i,j);
				i++;
			}
		}
		swap(arr, i, right);
		return i;
	}

	public static void swap(int[] arr, int i, int j){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
