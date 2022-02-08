public class Test2 {

    public static void main(String[] args) {
//        a negatif, b positif
//        System.out.println(doSomething(-5, 7));

//        a ganjil positif, b negatif
        System.out.println(doSomething(7,-1));

//        a genap positif, b ganjil positif
//        System.out.println(doSomething(10,7));

//        a positif, b genap positif
//        System.out.println(doSomething(9,10));

//        a ganjil negatif, b negatif
//        System.out.println(doSomething(-7,-6));
    }

    static int doSomething (int a, int b) {
        if (a==0) {
            return b;
        } else {
            return (a>b) ? doSomething(a-2, b+1) : doSomething(a-1,b-1);
        }
    }
}
