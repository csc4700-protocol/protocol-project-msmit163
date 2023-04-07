import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;


class Parser {
private boolean logging;
private boolean reference;
private int counter = 1;

    public void parse(String[] values) {
        int currIndex = 0; 
        for (int i=0; i<values.length; i++){
            String inputValue = values[currIndex]; 

            if (inputValue.equals("20")) {  //if 20, run the add command
                this.add(values, currIndex); 
                currIndex = currIndex+4;
            }

            if (inputValue.equals("21")){  //if 21, run sub command
                this.sub(values, currIndex);
                currIndex = currIndex+4;
            }

            if (inputValue.equals("11")){  //if 11, run out command
                this.out(values, currIndex);
                currIndex = currIndex+2;
            }
            
            if (inputValue.equals("19")){  //if 19, run end command 
                break;
            }
            
            if (inputValue.equals("30")){  //if input value is 30, run the push method on appropriate stack
                this.push(values, currIndex);
                currIndex = currIndex+3;
            }

            //if input value is 31, run the pop method on appropriate stack
            if (inputValue.equals("31")){
                this.pop(values, currIndex);
                currIndex = currIndex +3;
            }
            //if input value is 32, delete all values currently in the indicated stack
            if (inputValue.equals("32")){
                this.clear(values, currIndex);
                currIndex = currIndex + 2;
            }

            if (inputValue.equals("33")){//if input value is 33, print (DUMP) list of each stack (Last In First Out)
                this.dump(values, currIndex);
                currIndex = currIndex+1;
            } 

            //MODE if statement 
             if (inputValue.equals("40")){
              this.mode(values, currIndex);
              currIndex = currIndex+2; 
             }

        }
    }
    //ADD 
    public void add (String[] values, int currIndex) { 
        int indexPlus1 = currIndex + 1; 
        int indexPlus2 = currIndex + 2; 
        int indexPlus3 = currIndex + 3; //read this to figure out where to put the finalValue 
        String finalValue; 
        Integer value1;
        Integer value2;
        char key1 = values[indexPlus1].charAt(0);
        char key2 = values[indexPlus2].charAt(0);

        //LOGGING
        String parameters = values[indexPlus1] + " " + values[indexPlus2] + " " + values[indexPlus3];
        this.logging("ADD", parameters); 

        if (map.containsKey(key1)){
            Stack<Integer> stack = map.get(key1);
            value1 = stack.pop();
        }
        else{
            value1 = Integer.parseInt(values[indexPlus1]);
        }
        //value1 = map.containsKey(key1) ? map.get(key1).pop() : Integer.parseInt(values[indexPlus1])
        //         IF PART                                       ELSE PART

        if(map.containsKey(key2)){
            Stack<Integer> stack = map.get(key2);
            value2 = stack.pop();
        }
        else{
            value2 = Integer.parseInt(values[indexPlus2]);
        }

                     //need parenthases around both Integer and values1 and 2 in order to convert toString
        finalValue = ((Integer)(value1 + value2)).toString(); 
        values[Integer.parseInt(values[indexPlus3])] = finalValue;
    }
//SUB 
    public void sub (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        int indexPlus2 = currIndex + 2;
        int indexPlus3 = currIndex + 3; //read this to figure out where to put the finalValue
        Integer value1;
        Integer value2;
        String finalValue;
        char key1 = values[indexPlus1].charAt(0);
        char key2 = values[indexPlus2].charAt(0);

        //LOGGING
        String parameters = values[indexPlus1] + " " + values[indexPlus2] + " " + values[indexPlus3];
        this.logging("SUB", parameters); 

        if (map.containsKey(key1)){
            Stack<Integer> stack = map.get(key1);
            value1 = stack.pop();
        }
        else{
            value1 = Integer.parseInt(values[indexPlus1]);
        }
        
        if(map.containsKey(key2)){
            Stack<Integer> stack = map.get(key2);
            value2 = stack.pop();
        }
        else{
            value2 = Integer.parseInt(values[indexPlus2]);
        }

        finalValue = ((Integer)(value1 - value2)).toString(); 
        values[Integer.parseInt(values[indexPlus3])] = finalValue;
    }
//OUT
public void out (String[] values, int currIndex){
    int indexPlus1 = currIndex + 1;
    char key = values[indexPlus1].charAt(0);

    //LOGGING
    String parameters = values[indexPlus1];
    this.logging("OUT", parameters); 

    if (map.containsKey(key)){
            Stack<Integer> stack = map.get(key);
            ListIterator<Integer> listIterator = stack.listIterator(stack.size()); //CREATING THE ITERATOR, SAVING IT OFF 
            while (listIterator.hasPrevious()){                                    //CHECKS TO SEE IF IT'S SAFE TO HOP
                Integer value = listIterator.previous();                           //DOES THE HOPPING
                System.out.print(value + " " );
            }
            System.out.println();    
    }
    else{
        String finalValue = values[Integer.parseInt(values[indexPlus1])]; 
        System.out.println(finalValue);
    }
}
//PUSH 
    Map<Character, Stack> map = new HashMap<>();
    public void push (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        int indexPlus2 = currIndex + 2;

        //LOGGING 
        String parameters = values[indexPlus1] + " " + values[indexPlus2];
        this.logging("PUSH", parameters); 

        char key = values[indexPlus1].charAt(0);
        int stackIndexPlus2 = Integer.parseInt(values[indexPlus2]);
        Stack<Integer> stack = null; 

        if (!map.containsKey(key)){       //IF THE STACK DOES NOT EXIST 
            stack = new Stack<Integer>(); //CRREATE STACK
            stack.push(stackIndexPlus2);  //PUSH VALUE ONTO STACK
            map.put(key, stack);          //ADD TO MAP => INDEXPLUS1 TO KEY AND STACK TO VALUE
        }
        else{                             //IF THE STACK ALREADY EXISTS
            stack = map.get(key);         //GET THE EXISTING STACK
            stack.push(stackIndexPlus2);  //PUSH ONTO THE STACK 
        }
        //map.computeIfAbsent(key, mapKey -> new Stack<Integer>()).push(stackIndexPlus2);
    }
//POP
    public void pop (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        int indexPlus2 = currIndex + 2;

        //LOGGING
        String parameters = values[indexPlus1] + " " + values[indexPlus2];
        this.logging("POP", parameters); 

        char key = values[indexPlus1].charAt(0);
        Stack<Integer> stack = map.get(key);
        String popValue = stack.pop().toString();
        values[Integer.parseInt(values[indexPlus2])] = popValue; 
    }
//CLEAR
    public void clear (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        
        //LOGGING
        String parameters = values[indexPlus1];
        this.logging("CLEAR", parameters); 

        char key = values[indexPlus1].charAt(0);
        Stack<Integer> stack = map.get(key);
        stack.clear();
    }
//DUMP
    public void dump (String[] values, int currIndex){
    
        //LOGGING
        String parameters = " ";
        this.logging("DUMP", parameters); 
    
        for (Entry<Character, Stack> entry : map.entrySet()){
            Stack<Integer> stack = entry.getValue();
            System.out.print(entry.getKey() + ": "); 
            ListIterator<Integer> listIterator = stack.listIterator(stack.size()); //CREATING THE ITERATOR, SAVING IT OFF 
            while (listIterator.hasPrevious()){                                    //CHECKS TO SEE IF IT'S SAFE TO HOP
                Integer value = listIterator.previous();                           //DOES THE HOPPING
                System.out.print(value + " " );
            }
            System.out.println();
        }
    }
//MODE
    public void mode (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;

        switch (values[indexPlus1]) {
            case "RL" : case "LR" : 
                reference = true; 
                logging = true;
                break;
            case "R" :
                reference = true;
                break; 
            case "L" :    
                logging = true;
                break;
        }
    }
//LOGGING 
    public void logging (String commandName, String parameters){
        if (logging == true){
            System.out.println(counter + ": " + commandName + " " + parameters);
            counter++;
        }
    }
//REFERENCE


}







