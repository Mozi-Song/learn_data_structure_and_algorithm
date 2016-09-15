import java.lang.Comparable;
import java.util.ArrayList;

public class Sort<Type extends Comparable<? super Type>> {

	public static <Type extends Comparable<? super Type>> void insertionSort(Type[] arr){

		for(int i=1; i<arr.length; i++){

			Type temp = arr[i];
			
			int j;
			for(j=i-1; j>=0 && arr[j].compareTo(temp)>0; j--)
				arr[j+1] = arr[j];
			
			arr[j+1] = temp;
		}
	}

	public static <Type extends Comparable<? super Type>> void shellSort(Type[] arr){

		int length = arr.length;
		ArrayList<Integer> shellSeq = new ArrayList<>();
		
		for(; length>0; length/=2)
			shellSeq.add(length);

		for(int offset : shellSeq){

			for(int i=offset; i<arr.length; i++){

				Type temp = arr[i];
				int j;
				for(j=i-offset; j>=0 && arr[j].compareTo(temp)>0; j-=offset)
					arr[j+offset] = arr[j];

				arr[j+offset] = temp;

			}
		}
	}

	public static <Type extends Comparable<? super Type>> void heapSort(Type[] arr) throws Exception{

		buildHeap(arr);
		int size = arr.length;
		while(size > 0){

			Type max = deleteMax(arr, size);
			arr[size-1] = max;
			size--;	
		}

	}

	private static <Type extends Comparable<? super Type>> void buildHeap(Type[] arr){
	
		for(int i=arr.length/2-1; i>=0; i--)
			percDown(arr, i, arr.length);
	
	}

	private static <Type extends Comparable<? super Type>> void percDown(Type[] arr, int hole, int size){
		
		Type tmp = arr[hole];
		int child = 2*hole+1;

		while(child <= size-1){
		
			if(child != size-1
				&& arr[child+1].compareTo(arr[child])>0)
				child++;

			if(arr[child].compareTo(tmp)>0){

				arr[hole] = arr[child];
				hole = child;	
				child = 2*hole+1;
			}
			else{
				break;
			}
			
		}
		arr[hole] = tmp;
			

	}
	
	private static <Type extends Comparable<? super Type>> Type deleteMax(Type[] arr, int size) throws Exception{
		
		if(arr.length == 0) throw new Exception();
		
		Type max = arr[0];
		arr[0] = arr[size-1];				
		percDown(arr, 0, --size);

		return max;

	}

	@SuppressWarnings("unchecked")
	public static <Type extends Comparable<? super Type>> void mergeSort(Type[] arr) throws Exception{

		Type[] tempArr = (Type []) new Comparable[arr.length];
		mergeSort(arr, 0, arr.length-1, tempArr);

	}

	private static <Type extends Comparable<? super Type>> void mergeSort(Type[] arr, int begin, int end, Type[] tempArr) throws Exception{
		
		if(begin > end)
			throw new Exception();
	
		if(begin == end)	
			return;
		
		int mid = (begin + end) / 2;
		mergeSort(arr, begin, mid, tempArr);
		mergeSort(arr, mid+1, end, tempArr);
		merge(arr, begin, mid, mid+1, end, tempArr);

		arr = tempArr;
	}
	
	private static <Type extends Comparable<? super Type>> void merge(Type[] arr, int begin1, int end1, 
		int begin2, int end2, Type[] tempArr){
		
		int numElements = end2 - begin1 + 1;
		int index = begin1;
		while(begin1 <= end1 && begin2 <= end2){
	
			tempArr[index++] = (arr[begin1].compareTo(arr[begin2])<0? arr[begin1++] : arr[begin2++]);

		}

		while(begin1 <= end1)
			tempArr[index++] = arr[begin1++];
		while(begin2 <= end2)
			tempArr[index++] = arr[begin2++];

		for(int i=0; i<numElements; i++,end2--)
			arr[end2] = tempArr[end2];

		
	}

	private static final int CUTOFF = 3;

	public static <Type extends Comparable<? super Type>> void quickSort(Type[] arr){

		quickSort(arr, 0, arr.length-1);
	
	}
	
	private static <Type extends Comparable<? super Type>> void quickSort(Type[] arr, int left, int right){

		if(left + CUTOFF <= right){

			Type pivot = medianInPlace(arr, left, right);

			int i = left, j = right-1;
			while(j > i){

				while(arr[++i].compareTo(pivot) < 0) {}
				while(arr[--j].compareTo(pivot) > 0) {}
				
				if(i < j)
					switchIndexes(arr, i, j);

			}
			switchIndexes(arr, i, right-1);
			quickSort(arr, left, i-1);
			quickSort(arr, i+1, right);

		}
		
		else{
			insertionSort(arr, left, right);
		}
		

	}

	private static <Type extends Comparable<? super Type>> void insertionSort(Type[] arr, int left, int right){

		for(int i=left+1; i<=right; i++){

			Type temp = arr[i];
			
			int j;
			for(j=i-1; j>=left && arr[j].compareTo(temp)>0; j--)
				arr[j+1] = arr[j];
			
			arr[j+1] = temp;
		}

	}

	private static <Type extends Comparable<? super Type>> Type medianInPlace(Type[] arr, int left, int right){

		int mid = (left + right) / 2;
		if(arr[mid].compareTo(arr[left])<0)
			switchIndexes(arr, mid, left);
		if(arr[right].compareTo(arr[mid])<0)
			switchIndexes(arr, right, mid);
		if(arr[mid].compareTo(arr[left])<0)
			switchIndexes(arr, mid, left);	
		switchIndexes(arr, mid, right-1);
		return arr[right-1];
	}

	private static <Type extends Comparable<? super Type>> void switchIndexes(Type[] arr, int i, int j){

		Type tmp = arr[i];	
		arr[i] = arr[j];
		arr[j] = tmp;

	}
	
	public static <Type extends Comparable<? super Type>> void quickSelect(Type[] arr, int k) throws Exception{
		
		if(k > arr.length)
			throw new Exception();

		quickSelect(arr, 0, arr.length-1, k);		
	}

	private static <Type extends Comparable<? super Type>> void quickSelect(Type[] arr, int left, int right, int k){

		if(left + CUTOFF > right){
			insertionSort(arr, left, right);
		}
		else{
			Type pivot = medianInPlace(arr, left, right);
			int i = left, j = right-1;
			while(j > i){
	
				while(arr[++i].compareTo(pivot) < 0) {}
				while(arr[--j].compareTo(pivot) > 0) {}
				
				if(i < j)
					switchIndexes(arr, i, j);

			}
			switchIndexes(arr, i, right-1);

			if(k < i+1)	quickSelect(arr, left, i-1, k);
		
			if(k > i+1)	quickSelect(arr, i+1, right, k);

			}
		
		}
	
	private static void checkSort( Integer [ ] a )
    {
        for( int i = 0; i < a.length; i++ )
            if( a[ i ] != i ) 
                System.out.println( "Error at " + i );
        System.out.println( "Finished checksort" );
    }

	private static final int NUM_ITEMS = 1000;
    private static int theSeed = 1;
    
    private static void permute(Integer[] a){
    	// shuffle
    	
        for (int i = 0; i < a.length; i++) {
            int r = (int) (Math.random() * (i+1));     // int between 0 and i
            int swap = a[r];
            a[r] = a[i];
            a[i] = swap;
        }
    }
	
    public static void main( String [ ] args ) throws Exception
    {
        Integer [ ] a = new Integer[ NUM_ITEMS ];
        for( int i = 0; i < a.length; i++ )
            a[ i ] = i;

        for( theSeed = 0; theSeed < 20; theSeed++ )
        {
        	int i = 0;
        	System.out.println(i++);
            permute( a );
            Sort.insertionSort( a );
            checkSort( a );

        	System.out.println(i++);
            permute( a );
            Sort.heapSort( a );
            checkSort( a );

        	System.out.println(i++);
            permute( a );
            Sort.shellSort( a );
            checkSort( a );

        	System.out.println(i++);
            permute( a );
            Sort.mergeSort( a );
            checkSort( a );

            //4
        	System.out.println(i++);
            permute( a );
            Sort.quickSort( a );
            checkSort( a );

            //5
        	System.out.println(i++);
            permute( a );
            Sort.quickSelect( a, NUM_ITEMS / 2 );
            System.out.println( a[ NUM_ITEMS / 2 - 1 ] + " " + NUM_ITEMS / 2 );
        }
        
        
        Integer [ ] b = new Integer[ 10_000_000 ];
        for( int i = 0; i < b.length; i++ )
            b[ i ] = i;
        
        permute( b );
        long start = System.currentTimeMillis( );
        Sort.quickSelect( b, b.length  / 2 );
        long end = System.currentTimeMillis( );
        System.out.println( "Timing for Section 1.1 example: " );
        System.out.println( "Selection for N = " + b.length + " takes " + 
                             ( end - start ) + "ms." );
        System.out.println( b[ b.length / 2 - 1 ] + " " + b.length / 2 );
    }

}
