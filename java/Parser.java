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
        
        while (currIndex < values.length){
            String inputValue = getValue(values, currIndex); 

            if (inputValue.equals("20")) {  //if 20, run the add command
                this.add(values, currIndex); 
                currIndex = currIndex + 4;
            }
            else if (inputValue.equals("21")){  //if 21, run sub command
                this.sub(values, currIndex);
                currIndex = currIndex+4;
            }
            else if (inputValue.equals("11")){  //if 11, run out command
                this.out(values, currIndex);
                currIndex = currIndex+2;
            }
            else if (inputValue.equals("19")){  //if 19, run end command **NEED LOGGING STATEMENT
                this.end(values, currIndex);
                break;
            }
            else if (inputValue.equals("30")){  //if input value is 30, run the push method on appropriate stack
                this.push(values, currIndex);
                currIndex = currIndex+3;
            }
            //if input value is 31, run the pop method on appropriate stack
            else if (inputValue.equals("31")){
                this.pop(values, currIndex);
                currIndex = currIndex +3;
            }
            //if input value is 32, delete all values currently in the indicated stack
            else if (inputValue.equals("32")){
                this.clear(values, currIndex);
                currIndex = currIndex + 2;
            }
            else if (inputValue.equals("33")){//if input value is 33, print (DUMP) list of each stack (Last In First Out)
                this.dump(values, currIndex);
                currIndex = currIndex+1;
            } 
            //MODE if statement 
            else if (inputValue.equals("40")){
              this.mode(values, currIndex);
              currIndex = currIndex+2; 
             }
            else{
                //INVALIDCOMMANDEXCEPTION
                throw new InvalidCommandException();
            }
            //INCOMPLETEPROTOCOLEXCEPTION
            if (currIndex == values.length){ //don't need to check if we're hitting 19, becase we would have already exited the loop 
                throw new IncompleteProtocolException();
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

        //LOGGING
        String parameters = getValue(values, indexPlus1) + " " + getValue(values, indexPlus2) + " " + getValue(values, indexPlus3);
        this.logging("ADD", parameters); 
        
        //REFERENCE ON 
        if (reference == true){
            char referenceKey1 = getValue(values, Integer.parseInt(getValue(values, indexPlus1))).charAt(0); //inception layer to get first value to add
            char referenceKey2 = getValue(values, Integer.parseInt(getValue(values, indexPlus2))).charAt(0); //inception layer to get first value to add 
            int referenceIndexPlus3 = Integer.parseInt(getValue(values, Integer.parseInt(getValue(values, indexPlus3)))); //accessing location/inception layer to save eventual output 

            if (map.containsKey(referenceKey1)){                //checking if value matches with map
                Stack<Integer> stack = map.get(referenceKey1);  //if it does, get the associated value(stack)
                value1 = popCheck(stack);                           //pop that value and save it in value1
            }
            else if (String.valueOf(referenceKey1).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value1 = Integer.parseInt(this.reference(values, indexPlus1)); //if it's not in the map, get the value of the reference index and save it in value1
            }
            if (map.containsKey(referenceKey2)){                //checking if the value matches with the map
                Stack<Integer> stack = map.get(referenceKey2);  //if it does, get the associated value(stack)
                value2 = popCheck(stack);                           //pop that value and save it in value2
            }
            else if (String.valueOf(referenceKey1).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value2 = Integer.parseInt(this.reference(values, indexPlus2)); //if it's not on the map, get the value of the reference index and save it in value2
            }
            finalValue = ((Integer)(value1 + value2)).toString(); //add the two values, save it in finalValue
            setValue(values, referenceIndexPlus3, finalValue);//save finalValue into the reference index
        }
        //REFERENCE OFF 
        else{
            char key1 = getValue(values, indexPlus1).charAt(0); 
            char key2 = getValue(values, indexPlus2).charAt(0);    

            if (map.containsKey(key1)){
                Stack<Integer> stack = map.get(key1);
                value1 = popCheck(stack);
            }
            else if (String.valueOf(key1).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value1 = Integer.parseInt(getValue(values, indexPlus1));
            }
            if(map.containsKey(key2)){
                Stack<Integer> stack = map.get(key2);
                value2 = popCheck(stack);
            }
            else if (String.valueOf(key2).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value2 = Integer.parseInt(getValue(values, indexPlus2));
            }
                     //need parenthases around both Integer and values1 and 2 in order to convert toString
        finalValue = ((Integer)(value1 + value2)).toString(); 
        setValue(values, Integer.parseInt(getValue(values, indexPlus3)), finalValue);
        }
    }
//SUB 
    public void sub (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        int indexPlus2 = currIndex + 2;
        int indexPlus3 = currIndex + 3; //read this to figure out where to put the finalValue
        Integer value1;
        Integer value2;
        String finalValue;

        //LOGGING
        String parameters = getValue(values, indexPlus1) + " " + getValue(values, indexPlus2) + " " + getValue(values, indexPlus3);
        this.logging("SUB", parameters); 

        //REFERENCE ON
        if (reference == true){
            char referenceKey1 = getValue(values, Integer.parseInt(getValue(values, indexPlus1))).charAt(0); //inception layer to get first value to subtract
            char referenceKey2 = getValue(values, Integer.parseInt(getValue(values, indexPlus2))).charAt(0); //inception layer to get second value to subtract
            int referenceIndexPlus3 = Integer.parseInt(getValue(values, Integer.parseInt(getValue(values, indexPlus3)))); //accessing location/inception layer to save eventual output 
            
            if(map.containsKey(referenceKey1)){
                Stack<Integer> stack = map.get(referenceKey1);
                value1 = popCheck(stack);
            }
            else if (String.valueOf(referenceKey1).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value1 = Integer.parseInt(this.reference(values, indexPlus1));
            }
            if(map.containsKey(referenceKey2)){
                Stack<Integer> stack = map.get(referenceKey2);
                value2 = popCheck(stack);
            }
            else if (String.valueOf(referenceKey1).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value2 = Integer.parseInt(this.reference(values, indexPlus2));
            }
            
            finalValue = ((Integer)(value1 - value2)).toString(); 
            setValue(values, referenceIndexPlus3, finalValue);
        }
        //REFERENCE OFF 
        else{ 
            char key1 = getValue(values, indexPlus1).charAt(0);
            char key2 = getValue(values, indexPlus2).charAt(0);

            if (map.containsKey(key1)){
                Stack<Integer> stack = map.get(key1);
                value1 = popCheck(stack);
            }
            else if (String.valueOf(key1).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value1 = Integer.parseInt(getValue(values, indexPlus1));
            }
            
            if(map.containsKey(key2)){
                Stack<Integer> stack = map.get(key2);
                value2 = popCheck(stack);
            }
            else if (String.valueOf(key2).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
                throw new NonExistentStackException(); 
            }
            else{
                value2 = Integer.parseInt(getValue(values, indexPlus2));
            }
            finalValue = ((Integer)(value1 - value2)).toString();
            setValue(values, indexPlus3, finalValue);
        }
    }
//OUT
public void out (String[] values, int currIndex){
    int indexPlus1 = currIndex + 1;
    char key = getValue(values, indexPlus1).charAt(0);

    //LOGGING
    String parameters = getValue(values, indexPlus1);
    this.logging("OUT", parameters); 

    //REFERENCE ON
    if (reference == true){
    char referenceKey = this.reference(values, indexPlus1).charAt(0); //using this.reference to going into second inception layer, returning the value there

        if(map.containsKey(referenceKey)){ //check if referenceKey is found in map 
            Stack<Integer> stack = map.get(referenceKey); //if it is, get associated stack in order to iterate
            ListIterator<Integer> listIterator = stack.listIterator(stack.size()); //iterate through the stack to "out" it
        while (listIterator.hasPrevious()){
            Integer value = listIterator.previous();
            System.out.print(value + " ");
        }
        System.out.println();
        }
        else if (String.valueOf(referenceKey).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
            throw new NonExistentStackException(); 
        }
        else{
            String finalValue = getValue(values, Integer.parseInt(this.reference(values, indexPlus1))); //if the referenceValue is not a stack
            System.out.println(finalValue);                                                   //"out" it
        }
    }
    //REFERENCE OFF
    else{
        if (map.containsKey(key)){
                Stack<Integer> stack = map.get(key);
                ListIterator<Integer> listIterator = stack.listIterator(stack.size()); //CREATING THE ITERATOR, SAVING IT OFF 
                while (listIterator.hasPrevious()){                                    //CHECKS TO SEE IF IT'S SAFE TO HOP
                    Integer value = listIterator.previous();                           //DOES THE HOPPING
                    System.out.print(value + " " );
                }
                System.out.println();    
        }
        else if (String.valueOf(key).matches("[a-z]")){ //validating whether the value is a character, we will know whether it is an invalid stack name
            throw new NonExistentStackException(); 
        }
        else{
            String finalValue = getValue(values, Integer.parseInt(getValue(values, indexPlus1))); 
            System.out.println(finalValue);
        }
    }
}
//PUSH 
    Map<Character, Stack> map = new HashMap<>();
    public void push (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        int indexPlus2 = currIndex + 2;
        Stack<Integer> stack = null; 

        //LOGGING 
        String parameters = getValue(values, indexPlus1) + " " + getValue(values, indexPlus2);
        this.logging("PUSH", parameters);         
        
        //REFERENCE ON
        if (reference == true){
            char referenceKey = getValue(values, Integer.parseInt(getValue(values, indexPlus1))).charAt(0); //second inception layer to get stack
            int referenceStackIndexPlus2 = Integer.parseInt(this.reference(values, indexPlus2)); //this.reference will go to the second inception layer where the stack is sitting
            
            if (!map.containsKey(referenceKey)){       //if stack does not exist
                stack = new Stack<Integer>();           //create stack
                stack.push(referenceStackIndexPlus2);   //push onto that stack
                map.put(referenceKey, stack);                    //add to map
            }
            else{                                       //if stack already exists
                stack = map.get(referenceKey);         //get the existing stack
                stack.push(referenceStackIndexPlus2);   //push 
            }
        }
        //REFERENCE OFF
        else{
            char key = getValue(values, indexPlus1).charAt(0);
            int stackIndexPlus2 = Integer.parseInt(getValue(values, indexPlus2));

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
    }
//POP
    public void pop (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        int indexPlus2 = currIndex + 2;
        String popValue;
        
        //LOGGING
        String parameters = getValue(values, indexPlus1) + " " + getValue(values, indexPlus2);
        this.logging("POP", parameters); 
        

        //REFERENCE ON
        if(reference == true){
            char referenceKey = getValue(values, Integer.parseInt(getValue(values, indexPlus1))).charAt(0); //second inception layer to get stack
            int referenceStackIndexPlus2 = Integer.parseInt(this.reference(values, indexPlus2)); //this.reference will go to the second inception layer where the stack is sitting
            
            if (!map.containsKey(referenceKey)){
                throw new NonExistentStackException();
            }
            else{
            Stack<Integer> stack = map.get(referenceKey);
            popValue = popCheck(stack).toString();
            setValue(values, referenceStackIndexPlus2, popValue);
            }
        }
        //REFERENCE OFF
        else{
            char key = getValue(values, indexPlus1).charAt(0);
            
            if (!map.containsKey(key)){
                throw new NonExistentStackException();
            }
            Stack<Integer> stack = map.get(key);
            popValue = popCheck(stack).toString();
            setValue(values, Integer.parseInt(getValue(values,indexPlus2)), popValue);
        }
    }
//CLEAR
    public void clear (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        
        //LOGGING
        String parameters = getValue(values, indexPlus1);
        this.logging("CLEAR", parameters); 

        //REFERENCE ON
        if (reference == true){
            char referenceKey = getValue(values, Integer.parseInt(getValue(values, indexPlus1))).charAt(0);
            if(!map.containsKey(referenceKey)){
                throw new NonExistentStackException();
            }
            Stack<Integer> stack = map.get(referenceKey);
            stack.clear();
        }
        //REFERENCE OFF
        else {
            char key = getValue(values, indexPlus1).charAt(0);
            
            if (!map.containsKey(key)){
                throw new NonExistentStackException();
            }
            Stack<Integer> stack = map.get(key);
            stack.clear();
        }
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

        switch (getValue(values, indexPlus1)) {
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
            default : //INVALIDMODEEXCEPTION
                throw new InvalidModeException();
        }
    }
//END
    public void end (String[] values, int currIndex){
        //LOGGING 
        String parameters = " ";
        this.logging("END", parameters);
    }
//LOGGING 
    public void logging (String commandName, String parameters){
        if (logging == true){
            System.out.println(counter + ": " + commandName + " " + parameters);
            counter++;
        }
    }
//REFERENCE
    public String reference (String[] values, int parameterIndex){
        String x = getValue(values, Integer.parseInt(getValue(values, parameterIndex)));
        return x;        
    }
//INDEXOUTOFBOUNDSEXCEPTION METHOD
    public String getValue (String[] values, int currIndex){
        if (currIndex >= values.length){
            throw new IndexOutOfBoundsException();
        }
        else{
            return values[currIndex];
        }
    }
    public void setValue (String[] values, int currIndex, String value){
        if (currIndex >= values.length){
            throw new IndexOutOfBoundsException();
        }
        else{
            values[currIndex] = value;
        }
    }
//EMPTYSTACKEXCEPTION
    public Integer popCheck (Stack<Integer> stack){
        if(stack.isEmpty()){
            throw new EmptyStackException();            
        }
        else{
            return stack.pop();
        }
    }
}






