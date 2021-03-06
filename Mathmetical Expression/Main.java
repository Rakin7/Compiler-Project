import java.io.*;
import java.util.*;
public class Main 
{
    public static boolean compileError;
    public static StringBuffer postFixEquation = new StringBuffer();
    public static HashMap<Character, Integer> operatorMap = new HashMap<>();
    public static HashMap<Character, Integer> variableMap = new HashMap<>();
    public static Stack<String> stackMath = new Stack<>();
    public static String[] noOfEquations;
    public static int noOfVariables;
    public static int noOfInputEquations;
    public static BufferedReader bufferedReader;
    static 
    {
        try 
        {
            bufferedReader = new BufferedReader(new FileReader("input.txt"));
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException 
    {
        InitializeHashMap();
        takeInputFromFile();
        for(int j = 0; j< noOfEquations.length; j++)
        {
            System.out.println();
            compileError = false;
            postFixEquation.setLength(0);
            resetStack();
            char[] equationArray = noOfEquations[j].toCharArray();
            postFixEquation(equationArray);
            if(compileError)
            {
                continue;
            }
            char[] postFixArray = postFixEquation.toString().toCharArray();
            performCalclation(postFixArray);
            if(stackMath.size()!=0)
            {
                System.out.println("Answer is : "+stackMath.pop());
            }
        }
    }
    public static void performCalclation(char[] postFixArray)
    {
        for(int i = 0; i<postFixArray.length; i++)
        {
            if(operatorMap.containsKey(postFixArray[i]) )
            {
                compute(postFixArray[i]);
            }
            else
            {
                stackMath.push(String.valueOf(postFixArray[i]));
            }
        }
    }
    public static void resetStack()
    {
        while (stackMath.size() != 0) 
        {
            stackMath.pop();
        }
    }
    public static void postFixEquation(char[] equationArray)
    {
        Stack<Character> stack = new Stack<>();
        for(int i = 0;i<equationArray.length;i++){
            if(compileError == false){
                if(operatorMap.containsKey(equationArray[i]))
                {
                   if(equationArray[i] == 41 || equationArray[i] == 93 || equationArray[i] == 125)
                   {
                       boolean openingBracketFound = false;
                       while (openingBracketFound == false)
                       {
                           if(stack.isEmpty() && openingBracketFound == false)
                           {
                               System.err.println("Compilation Error : no opening bracket found for the given closing bracket");
                               compileError = true;
                               return;
                           }
                           else if(stack.peek() == 40 || stack.peek() == 91 || stack.peek() == 123)
                           {
                               stack.pop();
                               break;
                           }
                           else 
                           {
                               postFixEquation.append(String.valueOf(stack.pop()));
                           }
                       }
                   }
                    else
                    {
                        if(operatorMap.get(equationArray[i]) == 0)
                        {
                            stack.push(equationArray[i]);
                        }
                        else if(stack.isEmpty())
                        {
                            stack.push(equationArray[i]);
                        }
                        else if(equationArray[i] == 94 && stack.peek() == 94)
                        {
                            stack.push(equationArray[i]);
                        }
                        else if(operatorMap.get(equationArray[i]) <= operatorMap.get(stack.peek()))
                        {
                            boolean keepPopping = true;
                            while(keepPopping)
                            {
                                if(stack.size() == 0)
                                {
                                    keepPopping = false;
                                    stack.push(equationArray[i]);
                                }
                                else if(operatorMap.get(equationArray[i]) <= operatorMap.get(stack.peek()))
                                {
                                    postFixEquation.append(String.valueOf(stack.pop()));
                                }
                                else
                                {
                                    stack.push(equationArray[i]);
                                    keepPopping = false;
                                }
                            }
                        }
                        else 
                        {
                            stack.push(equationArray[i]);
                        }
                    }
                }
                else if(!variableMap.containsKey(equationArray[i]))
                {
                    System.err.println("Compilation Error : Symbol \"" + equationArray[i] + "\" has not been initialized.");
                    compileError = true;
                    return;
                }
                else
                {
                    postFixEquation.append(String.valueOf(equationArray[i]));
                }
            }
        }
        if(stack.isEmpty() == false)
        {
            while(!stack.isEmpty())
            {
                if(operatorMap.get(stack.peek()) == 0)
                {
                    stack.pop();
                    System.err.println("Compilation Error: Brackets Miss match");
                    compileError = true;
                    return;
                }
                else
                {
                    postFixEquation.append(String.valueOf(stack.pop()));
                }
            }
        }
        System.out.println("The Post fix equation is : "+postFixEquation);
    }
    public static void InitializeHashMap()
    {
        operatorMap.put(')', 0);
        operatorMap.put('(', 0);
        operatorMap.put('+', 1);
        operatorMap.put('-', 1);
        operatorMap.put('*', 2);
        operatorMap.put('/', 2);
        operatorMap.put('^', 9);
    }
    public static void compute(char postFixArray)
    {
        double right;
        double left;
        if(variableMap.containsKey(stackMath.peek().charAt(0)))
        {
            char temp = stackMath.pop().charAt(0);
            right = variableMap.get(temp);
        }
        else
        {
            right = Double.parseDouble(stackMath.pop());
        }
        if(variableMap.containsKey(stackMath.peek().charAt(0)))
        {
            char temp = stackMath.pop().charAt(0);
            left = variableMap.get(temp);
        }
        else
        {
            left = Double.parseDouble(stackMath.pop());
        }
        if(postFixArray == 43)
        {
            stackMath.push(String.valueOf(Double.parseDouble(String.valueOf(left)) + Double.parseDouble(String.valueOf(right))));
        }
        else if(postFixArray == 45)
        {
            stackMath.push(String.valueOf(Double.parseDouble(String.valueOf(left)) - Double.parseDouble(String.valueOf(right))));
        }
        else if(postFixArray == 47)
        {
            stackMath.push(String.valueOf(Double.parseDouble(String.valueOf(left)) / Double.parseDouble(String.valueOf(right))));
        }
        else if(postFixArray == 42)
        {
            stackMath.push(String.valueOf(Double.parseDouble(String.valueOf(left)) * Double.parseDouble(String.valueOf(right))));
        }
        else if(postFixArray == 94)
        {
            stackMath.push(String.valueOf(Math.pow(Double.parseDouble(String.valueOf(left)), Double.parseDouble(String.valueOf(right)))));
        }
    }
    public static void takeInputFromFile() throws IOException 
    {
        int counter = 1;
        int noOfEquationsIndex = 0;
        String fileInputLine;
        while((fileInputLine = bufferedReader.readLine()) != null)
        {
            if(counter == 1)
            {
                noOfVariables = Integer.parseInt(fileInputLine);
            }
            else if(noOfVariables >= 0)
            {
                String variables = fileInputLine;
                String[] line = variables.split("=");
                variableMap.put(line[0].trim().charAt(0), Integer.parseInt(line[1].trim()));
            }
            else if(noOfVariables == -1)
            {
                noOfInputEquations = Integer.parseInt(fileInputLine);
                noOfEquations = new String[noOfInputEquations];
            }
            else
            {
                noOfEquations[noOfEquationsIndex] = fileInputLine;
                noOfEquationsIndex++;
            }
            noOfVariables--;
            counter++;
        }
    }
}
