import java.io.*;
import java.util.*;

public class Test1 {
    public static void main(String[] args) {
        mystery(9);
    }

    public static void mystery(int data) {
        Stack<Integer> res = new Stack<Integer>();

        while(data > 0) {
            res.push(data%2);
            data = data/2;
        }

        while(!res.isEmpty()) {
            System.out.print(res.pop());
        }
    }
}

