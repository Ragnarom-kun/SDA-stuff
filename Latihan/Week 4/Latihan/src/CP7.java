import java.util.Arrays;

public class CP7 {

    public static void main(String[] args) {
        int[] arr = new int[] {3,2,8,1,23,5,99,-1};
        System.out.println(Arrays.toString(arr));
        MySort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void MySort(int[] a)

    {

        for (int ii = 1; ii < a.length; ii++) {

            int temp = a[ii];

            int jj = ii - 1;

            while (jj > 0 &&  a[jj] < temp) {

                a[jj+1] = a[jj];

                jj--;

            }

            a[jj + 1] = temp;

        }

    }
}
