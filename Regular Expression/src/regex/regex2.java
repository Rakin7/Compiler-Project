/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regex;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author User
 */
public class regex2 {

    public boolean automata(String re, String text) {
        re = re + '$';
        text = text + '$';
        System.out.println(re + " " + text);
        char[] c = re.toCharArray();
        char[] ct = text.toCharArray();
        boolean matches = false;
        int j = 0;
        int i = 0;

        while (i < c.length) {
            if (Character.isLetter(c[i]) && (Character.isLetter(c[i + 1]) || c[i + 1] == '(' || c[i + 1] == '[')) {
                if (c[i] == ct[j]) {
                    System.out.println("Entering the if of char and char");
                    matches = true;
                    i++;
                    j++;
                } else {
                    System.out.println("Entering the else of char and char");
                    matches = false;
                }
            } else if (Character.isLetter(c[i]) && c[i + 1] == '$') {
                if (c[i] == ct[j]) {
                    System.out.println("Entering the if of char and $");
                    matches = true;
                    i = i + 1;
                    j = j + 1;
                } else {
                    System.out.println("Entering the else of char and $");
                    matches = false;
                }
            } else if (Character.isLetter(c[i]) && c[i + 1] == '*') {
                if (ct[j] == '$') {
                    matches = true;
                    i = i + 2;
                    System.out.println("Entering the if of char and *");
                } else if (c[i] == ct[j]) {
                    while (c[i] == ct[j]) {
                        System.out.println("Entering the while of char and *");
                        matches = true;
                        j++;
                    }
                    System.out.println("Entering the else if of char and *");
                    i = i + 2;
                } else {
                    System.out.println("Entering the else of char and *");
                    matches = true;
                    i = i + 2;
                }
            } else if (Character.isLetter(c[i]) && c[i + 1] == '+') {
                if (ct[j] == '$') {
                    matches = false;System.out.println("Entering the if of char and +");
                } else if (c[i] == ct[j]) {System.out.println("Entering the else if of char and +");
                    while (c[i] == ct[j]) {
                        matches = true;
                        j++;
                    }
                    i = i + 2;
                } else {System.out.println("Entering the else of char and +");
                    matches = false;
                }
            } else if (Character.isLetter(c[i]) && c[i + 1] == '?') {
                if (ct[j] == '$') {
                    matches = true;
                    i = i + 2;
                } else if (c[i] == ct[j]) {
                    matches = true;
                    i = i + 2;
                    j++;
                } else {
                    matches = true;
                    i = i + 2;
                }
            } else if (c[i] == '(')//starting of bracketing patterns
            {
                Queue q = new LinkedList();
                while (c[i + 1] != ')') {
                    i++;
                    q.add(c[i]);
                }
                i = i + 2;
                if (c[i] == '*') {
                    char temp = (Character) q.element();
                    if (temp != ct[j]) {
                        matches = true;
                        i++;
                    } else {
                        char[] array = new char[q.size()];
                        for (int k = 0; k < array.length; k++) {
                            array[k] = (Character) q.poll();
                        }
                        int m = 0;
                        while (m < array.length) {
                            if (array[m] == ct[j]) {
                                System.out.println("Comparing " + array[m] + " " + ct[j]);
                                matches = true;
                                System.out.println("Entering the array length while if");
                                j++;
                                m++;
                            } else if (array[m] != ct[j] && m != 0) {
                                System.out.println("Comparing " + array[m] + " " + ct[j] + m + array.length);
                                matches = false;
                                System.out.println("Entering the array length while else");
                                break;
                            } else if (array[m] != ct[j] && m == 0) {
                                matches = true;
                                System.out.println("Entering the array length while else 2");
                                break;
                            }
                            if (m == array.length) {
                                m = 0;
                            }

                        }
                        i++;

                    }
                } else if (c[i] == '+') {
                    char[] array = new char[q.size()];
                    for (int k = 0; k < array.length; k++) {
                        array[k] = (Character) q.poll();
                    }
                    int m = 0;
                    int n = 0;
                    while (m < array.length) {
                        if (array[m] == ct[j]) {
                            System.out.println("Comparing " + array[m] + " " + ct[j]);
                            matches = true;
                            System.out.println("Entering the + array length while if");
                            j++;
                            m++;
                            n++;
                        } else if (array[m] != ct[j] && m != 0 && n != 0) {
                            System.out.println("Comparing " + array[m] + " " + ct[j] + m + array.length);
                            matches = false;
                            System.out.println("Entering the + array length while else");
                            break;
                        } else if (array[m] != ct[j] && m == 0 && n != 0) {
                            matches = true;
                            System.out.println("Entering the + array length while else 2");
                            break;
                        } else if (array[m] != ct[j] && m == 0 && n == 0) {
                            matches = false;
                            System.out.println("Entering the + array length while else 3");
                            break;
                        }
                        if (m == array.length) {
                            m = 0;
                        }

                    }
                    i++;
                } else if (c[i] == '?') {
                    char[] array = new char[q.size()];
                    for (int k = 0; k < array.length; k++) {
                        array[k] = (Character) q.poll();
                    }
                    int m = 0;
                    while (m < array.length) {
                        if (array[m] == ct[j]) {
                            System.out.println("Comparing " + array[m] + " " + ct[j]);
                            matches = true;
                            System.out.println("Entering the ? array length while if");
                            j++;
                            m++;
                        } else if (array[m] != ct[j] && m != 0) {
                            System.out.println("Comparing " + array[m] + " " + ct[j] + m + array.length);
                            matches = false;
                            System.out.println("Entering the ? array length while else");
                            break;
                        } else if (array[m] != ct[j] && m == 0) {
                            matches = true;
                            System.out.println("Entering the ? array length while else 2");
                            break;
                        }

                    }
                    i++;
                }
            } else if (c[i] == '[' && c[i + 1] != '^') {
                Queue q = new LinkedList();
                while (c[i + 1] != ']') {
                    i++;
                    q.add(c[i]);
                }
                i = i + 2;
                char[] array = new char[q.size()];
                for (int k = 0; k < array.length; k++) {
                    array[k] = (Character) q.poll();
                }
                if (c[i] == '*') {
                    char r1 = array[0];
                    char r2 = array[2];
                    System.out.println("Entering * of [");
//                    System.out.println("Range 1 "+r1);
//                    System.out.println("Rnage 2"+r2);
                    while (ct[j] >= r1 && ct[j] <= r2) {
                        matches = true;
                        j++;
                    }
                    i++;
                    
                } else if (c[i] == '{') {
                    i++;
                    int max = Character.getNumericValue(c[i]);
                    System.out.println(max);
                    i++;
                    char r1 = array[0];
                    char r2 = array[2];
                    int counter = 0;
                    while (ct[j] >= r1 && ct[j] <= r2 && counter < max) {
                        System.out.println("Matching  " + ct[j]);
                        matches = true;
                        j++;
                        counter++;
                    }
                    if (counter < max) {
                        matches = false;
                        System.out.println("entering counter max " + counter + " " + max);
                    }
                    i++;
                }

            }
             else if (c[i] == '[' && c[i + 1] == '^'){//negation starts
                i++;
                Queue q = new LinkedList();
                while (c[i + 1] != ']') {
                    i++;
                    q.add(c[i]);
                }
                i=i+2;
                char[] array = new char[q.size()];
                for (int k = 0; k < array.length; k++) {
                    array[k] = (Character) q.poll();
                }
                for(int k=0;k<array.length;k++)
                    System.out.println(array[k]);
                if(c[i]=='*')
                {
                   int m=0;
                   while(m<array.length)
                   {
                       if(array[m]==ct[j])
                       {
                           matches=true;System.out.println("Entering negation if");
                           break;
                       }
                       else if(ct[j]=='$')
                       {
                           matches=true;
                           break;
                       }
                       else if(array[m]!=ct[j])
                       {
                           System.out.println("Comparing "+array[m]+" "+ct[j]);
                           matches=true;System.out.println("Entering negation else if");
                           m++;
                       }

                       if(m==array.length)
                       {
                           m=0;System.out.println("Entering negation m==array");
                           j++;
                       }
                   }
                   i++;
                }
                else if(c[i]=='{')
                {
                     i++;
                    int max = Character.getNumericValue(c[i]);
                    System.out.println(max);
                    i++;
                    int counter=0;
                    int m=0;
                    while(m<array.length)
                    {
                        if(counter==max)
                        {
                            System.out.println("entering");
                            break;
                        }
                        if(array[m]==ct[j])
                        {
                            System.out.println("comparing "+array[m]+" =="+ct[j]);
                            break;
                        }
                        
                        else if(ct[j]=='$')
                        {
                            break;
                        }
                        else if(array[m]!=ct[j])
                        {
                            System.out.println("comparing "+array[m]+ " "+ ct[j]);
                            matches=true;
                            m++;
                        }
                        
                        if(m==array.length)
                        {
                            m=0;
                            j++;
                            counter++;
                        }
                    }
                    if(counter<max)
                    {
                        System.out.println(counter+ " "+ max);
                        System.out.println("entering here girl");
                        matches=false;
                    }
                    else
                    {
                        System.out.println(counter+" "+max);
                        System.out.println("entering here girl else");
                        matches=true;
                    }
                   i++;
                }
             }
            

            if (matches == false || c[i] == '$') {
                if (ct[j] != '$') {
                    matches = false;System.out.println("entering previous to false loop break");
                }
                System.out.println("Entering the false loop break");
                break;
            }
        }

        return matches;
    }

}
