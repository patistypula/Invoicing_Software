package pl.invoicingsoftware.invoice;

import java.util.Scanner;

import static java.lang.Character.isDigit;

public class algo {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        //System.out.println(palindrom(s.nextLine()));
        int [] tab = {12, 2, 7, 9};
        int [] tab2 = {6, 3};
        System.out.println("ilu studentów: "+tableClass(tab));
        System.out.println("ilu studentów: "+tableClass(tab2));
    }

    public static String aaa(String str){
        char [] tab = str.toCharArray();
        int p =0;
        int s = tab.length;
        String result ="";
        while(p<s){
            char a = tab[p];
            if(!isDigit(a)) {
                int q = 0;
                for (int i = 0; i < s; i++) {
                    if (a == tab[i]) {
                        q++;
                        tab[i] = '0';
                    }
                }

                result += String.valueOf(q);
                result += a;
            }
            p++;
        }
        return result;
    }

    public static String palindrom(String str){
        int size = str.length();
        char [] tab = str.toCharArray();
        if(size<4){
            return "not posible";
        }
        if(size==4){
            if(tab[0]==tab[3]){
                return String.valueOf(tab[1]);
            } else{
                return "not posible";
            }
        }
        if(size>=5){
            int p = 0;
            while(p<tab.length){
                int half = Math.round(size/2);
                String cut = str.substring(0, p)+ str.substring(p+1);

                System.out.println("cut "+cut);
                String start = "";
                String end = "";
                for(int i=0;   i<half;   i++){
                    start += cut.charAt(i);
                    end += cut.charAt(cut.length()-1-i);
                }
                System.out.println("start "+ start);
                System.out.println("end "+ end);
                if(start.equals(end)){
                    return String.valueOf(tab[p]);
                }
                p++;
            }
            p=0;
            int k = 0;
            while(p<tab.length){
                int half = Math.round(size/2);
                String cut = str.substring(0, p)+ str.substring(p+1);
                for(int i=0;   i<cut.length();   i++){
                    String cut2 = cut.substring(0, i)+cut.substring((i+1));
                    System.out.println("cut2 "+cut2);
                    int half2 = Math.round(cut2.length()/2);
                    String start ="";
                    String end   = "";
                    for(int j=0;   j<half2;   j++){
                        start += cut2.charAt(j);
                        end   += cut2.charAt(cut2.length()-1-j);
                    }
                    System.out.println("start = "+start);
                    System.out.println("end "+end);
                    if(start.equals(end)){
                        return String.valueOf(tab[p])+","+cut.charAt(i);
                    }
                }
            }
        }
        return "not posible";
    }

    public static int tableClass(int [] table){
        int size = table[0];
        int [] number = new int [size];
        for(int i=0;   i<size;   i++){
            number[i] = i+1;
            for(int j=1;   j<table.length;   j++){
                if(number[i]==table[j]){
                    number[i] = -1;
                }
            }
        }
        int result = 0;
        for(int i=0;   i<size/2;   i++){
            if(number[2*i]>0){
                if(number[2*i+1]>0){
                    result++;
                }
                if(2*i+2<size){
                    if(number[2*i+2]>0){
                        result++;
                    }
                }
            }
        }
        return result;
    }
}
