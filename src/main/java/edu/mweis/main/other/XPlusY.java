package edu.mweis.main.other;

import java.util.Arrays;

public class XPlusY {
    public static void main(String[] args) {

        final int[] X = new int[] {1, 5, 3, 2, 4, 9, 12};
        final int[] Y = new int[] {1, -1, 0, 5, 3, 2};
        final int M = X.length;
        final int N = Y.length;

        final int[] cp = cartesianProduct(X, Y, M, N);

        final int[] g1Mirror = getListSortedByIndicies(getGroup(cp, 0, N));
//        System.out.println(Arrays.toString(g1Mirror));
//        System.out.println(Arrays.toString(getGroup(cp, 0, N)));
//        final int[] orderOfGroupsMirror = new int[M];
        final int[] orderOfGroups = new int[M];
        for (int g=0; g < M; g++) {
//            orderOfGroupsMirror[g] = g;
            orderOfGroups[g] = getElement(cp, g, g1Mirror[0], N);
        }
        final int[] orderOfGroupsMirror = getListSortedByIndicies(orderOfGroups);

        // 0
        // 0 1 (REVERSE ALL LINES THO)
        // 0 1 2 (at N now)
        // 1 2 3
        // 2 3 4
        // ...
        // (M-2) (M-1) M
        // (M-1) M
        // M

        final int[] answer = new int[cp.length];

        answer[0] = getElement(cp, orderOfGroupsMirror[0], 0, N);
        answer[1] = getElement(cp, orderOfGroupsMirror[1], 0, N);
        answer[2] = getElement(cp, orderOfGroupsMirror[0], 1, N);

        int ans = 3;
        int[] groupCounter = new int[M];
//        for (int i=0; i < M; i++) {
//            for (int j=0; j < N; j++) {
//                answer[ans++] = getElement(cp, i, )
//            }
//        }
        int group = 0;
        while (ans < cp.length - 3) {
            for (int i=0; i < N; i++) {
//                answer[ans++] = getElement(cp, orderOfGroupsMirror[group+i], groupCounter[orderOfGroupsMirror[group+i]], N);
                groupCounter[group+i]++;
            }
            group++;
        }

//        for (int i=0; i < M; i++) {
//            int smallest = grou
//        }
        boolean running = true;
        while (running) {
            int smallest = Integer.MAX_VALUE;
        }

        answer[cp.length-3] = getElement(cp, orderOfGroupsMirror[M], N-2, N);
        answer[cp.length-2] = getElement(cp, orderOfGroupsMirror[M-1], N-2, N);
        answer[cp.length-1] = getElement(cp, orderOfGroupsMirror[M], N-1, N);

        System.out.println(Arrays.toString(answer));
    }

    static int[] cartesianProduct(int[] X, int[] Y, int M, int N) {
        int[] ret = new int[X.length*Y.length];

        for (int i=0; i < M; i++) {
            for (int j=0; j < N; j++) {
                ret[i*N + j] = X[i] + Y[j];
            }
        }

        return ret;
    }

    static int getElement(int[] list, int group, int index, int N) {
        return list[group*N + index];
    }

    static int[] getGroup(int[] list, int group, int N) {
        int[] ret = new int[N];
        for (int i=0; i < N; i++) {
            ret[i] = list[group*N + i];
        }
        return ret;
    }

    static int getIndex(int group, int index, int N) {
        return group*N + index;
    }

    static int[] getListSortedByIndicies(int[] list) {

        int[] indexListMirror = new int[list.length];
        for (int i=0; i < indexListMirror.length; i++) {
            indexListMirror[i] = i;
        }

        heapSort(list, indexListMirror);

        return indexListMirror;
    }

    public static void buildheap(int []arr, int[] mirror) {

        /*
         * As last non leaf node will be at (arr.length-1)/2
         * so we will start from this location for heapifying the elements
         * */
        for(int i=(arr.length-1)/2; i>=0; i--){
            heapify(arr,i,arr.length-1, mirror);
        }
    }

    static void heapify(int[] arr, int i,int size, int[] mirror) {
        int left = 2*i+1;
        int right = 2*i+2;
        int max;
        if(left <= size && arr[left] > arr[i]){
            max=left;
        } else {
            max=i;
        }

        if(right <= size && arr[right] > arr[max]) {
            max=right;
        }
        // If max is not current node, swap it with max of left and right child
        if(max!=i) {
            swap(arr,i, max, mirror);
            heapify(arr, max,size, mirror);
        }
    }

    static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    static void swap(int[] arr, int i, int j, int[] mirror) {
        swap(arr, i, j);
        swap(mirror, i, j);
    }

    static void heapSort(int[] arr, int[] mirror) {

        buildheap(arr, mirror);
        int sizeOfHeap=arr.length-1;
        for(int i=sizeOfHeap; i>0; i--) {
            swap(arr,0, i, mirror);
            sizeOfHeap=sizeOfHeap-1;
            heapify(arr, 0,sizeOfHeap, mirror);
        }
    }
}
