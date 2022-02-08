import java.util.Arrays;

public class CP1 {
    static void funcA(int a[]) throws Exception

    {

        for (int i = 0; i < a.length; i++) {

            int max = i;



            for (int j = i + 1; j < a.length; j++)

                if (a[max] < a[j])

                    max = j;


            int T = a[i];

            a[i] = a[max];

            a[max] = T;

        }

    }

    public static void main(String[] args) {
        int[] arr = new int[] {3,5,2,4,1};
        System.out.println(Arrays.toString(arr));
        try {
            funcA(arr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(arr));
    }
}
