https://github.com/csc4700-protocol/protocol-project-msmit163

# Compiling

From this directory, compile all files using the command:

```
javac *.java
```

# Running

From this directory, there are two options to run the parser:

Specifying the list of input values to the executable:

```
java Main 1 2 3 4 5
```

Specifying the relative path to an examples file:

```
java Main -f examples/example-1.txt
```

Input values in a test file are delimited by whitespace.

# Notes

* Do not change any code in the `Main.java` file.
* You have free reign on the rest of the application, you may create other methods, classes, or files as necessary. For simplicity, please keep new files in the same directory.
* Do not delete the existing `example-1.txt` file.
* Other test files may be added to the `examples` directory and committed to your repository.