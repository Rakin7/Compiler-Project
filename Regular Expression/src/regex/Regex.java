/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class Regex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc=new Scanner(System.in);
        Scanner sc2=new Scanner(System.in);
        System.out.println("Please input the number of regex");
        int n=sc.nextInt();
        String []re=new String[n];
        for(int i=0;i<n;i++){
        System.out.println("Please input your regular expression");
        re[i]=sc2.nextLine();
        }
        System.out.println("Please input the number of input text");
        int m=sc.nextInt();
        String []text=new String[m];
        for(int i=0;i<m;i++){
        System.out.println("Please input text string");
        text[i]=sc2.nextLine();
        }
        
        regex2 r=new regex2();
        boolean matches[][]=new boolean[m][n];
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
               matches[i][j]= r.automata(re[j], text[i]);
            }
        }
        System.out.println();
        System.out.println();
        System.out.println("=========================this is the output============================");
        boolean accept[]=new boolean[m];
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(matches[i][j]==true)
                {
                    accept[i]=true;
                    System.out.println("String : "+text[i]+" is accepted by REGEX: "+ re[j]);
                }
            }
        }
        for(int i=0;i<m;i++)
        {
            if(accept[i]==false)
            {
                System.out.println("String :"+text[i]+" is not accepted by any of the REGEX");
            }
        }
         System.out.println("=========================this is the output============================");

    }

}
