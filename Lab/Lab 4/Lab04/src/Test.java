//import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//public class Test {
//    public static void main(String[] args) {
//        Lantai test1 = new Lantai("First",null,null);
//        // This below is by reference
////        Lantai head = test1; Lantai tail = test1;
//
//        // Test pass by object
//        Lantai head = new Lantai(test1.getValue(), test1.next,test1.prev);
//        Lantai tail = new Lantai(test1.getValue(), test1.next,test1.prev);
//
//        System.out.println(test1.getValue());
//        System.out.println(head.getValue());
//        System.out.println(tail.getValue());
//        System.out.println("--------------------------------------------");
//
//        head.setValue("Kimochiiiii");
//        tail.setValue("Ara-ara~~~~");
//        System.out.println(test1.getValue());
//        System.out.println(head.getValue());
//        System.out.println(tail.getValue());
//        System.out.println(test1.getValue());
//    }
//}
//
//class Lantai {
//
//    Lantai next;
//    Lantai prev;
//    String namaLantai;
//
//    public Lantai(String nama, Lantai next, Lantai prev){
//        this.namaLantai = nama;
//        this.next = next;
//        this.prev = prev;
//    }
//
//    public String getValue(){
//        return namaLantai;
//    }
//
//    public void setValue(String namaLantai) {
//        this.namaLantai = namaLantai;
//    }
//}
//
//// TODO - class untuk Gedung
//class Gedung {
//    Lantai head;          // head of lantai's node
//    Lantai pointerAgen;
//    Lantai tail;
//
//    public Gedung() {
//        this.head = null;
//    }
//
//    public void bangun(String input){
//        // TODO - handle BANGUN
//        if (head==null) { // head == null or the first element
//            head = new Lantai(input,null,null);
//            pointerAgen = head; tail = head;
//        } else if (pointerAgen.next==null)   { // adding in the last current element    // ada kemungkinan bug index
//            Lantai temp = pointerAgen;
//            pointerAgen = new Lantai(input,null,temp);
//            temp.next = pointerAgen;
////            while (temp.next != null) {
////                temp = temp.next;
////            }
//            temp.next = new Lantai(input,null,temp);
//        } else {    // inserting lantai
//
//        }
//    }
//
//    public void lift(String input){
//        // TODO - handle LIFT
//    }
//
//    public void hancurkan(){
//        // TODO - handle HANCURKAN
//    }
//
//    public void timpa(String input){
//        // TODO - handle TIMPA
//    }
//
//    public String sketsa(){
//        // TODO - handle SKETSA
//        return "";
//    }
//
//
//}
//
