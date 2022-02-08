/*
Nama : Romi Fadhurrohman Nabil
NPM : 2006535016
Kelas : D
*/

import java.util.*;

public class Week4 {
    public static void main(String[] args) {
        int[] arr1 = { 4, 5, 9 };
        int[] arr2 = { 1, 3, 7, 8 };
        int[] arr3 = { 2, 6 };
        int[] result = merge3(arr1, arr2, arr3);
        System.out.println(Arrays.toString(result));
    }

    static int[] merge3(int[] arr1, int[] arr2, int[] arr3) {
        // TODO
        int size1 = arr1.length, size2 = arr2.length, size3 = arr3.length;
        int[] res = new int[size1+size2+size3];

        int p1 = 0, p2 = 0, p3 = 0, index = 0;

        while(p1 < size1 && p2 < size2 && p3 < size3) {
            if (arr1[p1] < arr2[p2] && arr1[p1] < arr3[p3]) {
                res[index] = arr1[p1];
                p1++;
            } else if (arr2[p2] < arr3[p3]) {
                res[index] = arr2[p2];
                p2++;
            } else if (arr1[p1] < arr3[p3]) {
                res[index] = arr1[p1];
                p1++;
            } else if (arr1[p1] < arr2[p2]) {
                res[index] = arr1[p1];
                p1++;
            } else {
                res[index] = arr3[p3];
                p3++;
            }
            index++;
        }

        while(p1<size1 && p2<size2) {
            if(arr1[p1] < arr2[p2]) {
                res[index] = arr1[p1];
                p1++;
            } else {
                res[index] = arr2[p2];
                p2++;
            }
            index++;
        }

        while(p1<size1 && p3<size3) {
            if(arr1[p1] < arr3[p3]) {
                res[index] = arr1[p1];
                p1++;
            } else {
                res[index] = arr3[p3];
                p3++;
            }
            index++;
        }

        while(p2<size2 && p3<size3) {
            if(arr2[p2] < arr3[p3]) {
                res[index] = arr2[p2];
                p2++;
            } else {
                res[index] = arr3[p3];
                p3++;
            }
            index++;
        }

        while(p1<size1) {
            res[index] = arr1[p1];
            p1++;
            index++;
        }

        while(p2<size2) {
            res[index] = arr2[p2];
            p2++;
            index++;
        }

        while(p3<size3) {
            res[index] = arr3[p3];
            p3++;
            index++;
        }

        return res;
    }
}
