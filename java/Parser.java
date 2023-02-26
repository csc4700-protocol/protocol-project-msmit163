class Parser {

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
        }
    }

    public void add (String[] values, int currIndex) { 
        int indexPlus1 = currIndex + 1; 
        int indexPlus2 = currIndex + 2; 
        int indexPlus3 = currIndex + 3; //read this to figure out where to put the finalValue
        String finalValue; 

        finalValue = ((Integer)(Integer.parseInt(values[indexPlus1]) + Integer.parseInt(values[indexPlus2]))).toString();
        values[Integer.parseInt(values[indexPlus3])] = finalValue;
    }

    public void sub (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        int indexPlus2 = currIndex + 2;
        int indexPlus3 = currIndex + 3; //read this to figure out where to put the finalValue
        String finalValue;

        finalValue = ((Integer)(Integer.parseInt(values[indexPlus1]) - Integer.parseInt(values[indexPlus2]))).toString();
        values[Integer.parseInt(values[indexPlus3])] = finalValue;
    }

    public void out (String[] values, int currIndex){
        int indexPlus1 = currIndex + 1;
        String finalValue = values[Integer.parseInt(values[indexPlus1])]; 
        System.out.println(finalValue);
    }

}