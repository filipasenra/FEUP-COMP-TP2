# COMPILERS Project - Java Compiler

## Group and Self-evaluation

###  Group A3
* Cláudia Martins - up 
* Filipa Serna - up201704077
* Ana Teresa - up 
* Raul Viana - up2010208089

### Self Evaluation
* Cláudia Martins  Grade: , Contribution:
* Filipa Serna     Grade: , Contribution:
* Ana Teresa       Grade: , Contribution:
* Raul Viana       Grade: , Contribution:
<br><br>
Project Grade: 

## Summary
This project's main goal was to apply the theoretical principals of the course Compilers. This was achieved by building a compiler for programs written in the Java- language.
The main parts of the project are **Syntactic error controller**, **Semantic analysis**, and **Code generation**.

## Compile

To compile the program, run ``gradle build``. This will compile your classes to ``classes/main/java`` and copy the JAR file to the root directory. The JAR file will have the same name as the repository folder.

##Usage
```cmd
java Jmm <filename> -debug(-ast/-semantic) and/or -error and -o

java -jar comp2020-3a.jar <filename> -debug(-ast/-semantic) and/or -error and -o
```

Without the -error flag, non-initialized variables just trigger **warnings**.
With the -error flag, non-initialized variables trigger **errors**!
The -debug flag prints both the ASTTree and the Symbol Table.
The -ast flag prints the ASTTree.
The -semantic prints the Symbol Table.
The -o triggers some optimizations.

### Test

To test the program, run ``gradle test``. This will execute the build, and run the JUnit tests in the ``test`` folder. If you want to see output printed during the tests, use the flag ``-i`` (i.e., ``gradle test -i``).

## Syntactic Errors

As requested in the project assignment, we only recover from errors in the conditional statement of a while cycle.
If an error is found in the conditional statement of an while cycle, the compiler ignores every token until the next stable token, '{', showing an error message indicating which tokens were expected and the line and column where the error occurred. 
Our Program displays up to 10 errors in order to not overload the programmer and provide a better user experience.


## Semantic Analysis 
1. All semantic checks report errors except the variable initialization, which reports warning by default. 
When the -error flag is active, these warnings become errors.

2. Symbol Table

1. Variables
* Redefinition of local variables;
* Redefinition of global variables;
* Constructor with appropriate Parameters
* Checks if the Super Class was imported
* Checks if Default Constructor was Imported for the Super Class

3. Type Verification
    
* Operations between elements of the same type;
* Doesn't allow operations between arrays;
* Array access is only allowed to arrays;
* Only int values to index accessing array; 
* Only int values to initialize an array;
* Assignment between the same type;
* Boolean operation with booleans;
* Conditional Expression only excepts operations, variables or function calls that return boolean;
* Raises an error if variable has not been initialized, 
* Raises a warning if variable has been initialized in an if (only in a block) or while, or error when *-error* flag is active;
* Assumes Parameters as Initialized;

// TODO: outras cenas que me possa ter esquecido
 
4. Methods

* Allows overload of methods;
* Only allows calls to methods that exists with the correct signature;
* Checks if the method was imported or bellows to the current class
* Checks if method call is for the current class or if it is a method of the super class (when it applies)
* Verifies if the parameter types match the method signature;
* Verifies if the return type of method matches the assign variable (when it applies)
* Method can be declared after or before any other function calls it

## Intermediate Representations (IRs)

### AST

TODO: maybe

### Symbol Table

## Symbol

The Symbol Class serves has a base for all the Symbols in the Symbol Table.
It has the following fields:
* name: name of the symbol (for example, in SymbolClass holds the Class name)
* type: type of the symbol (for example, in SymbolMethod holds the return type)
* object_name: used when type is an Object: holds the type of Object
* index: unique index
* isStatic: where the method or variable is static

## SymbolClass

This class represents a Class (imported or the one we that is defined in the file) and holds all the information relative to a class.

The SymbolClass class extends the Symbol Class and has the following extra fields:
* symbolTableMethods: hashmap with its methods
* symbolTableFields: hashmap with its fields
* symbolTableConstructors: hashmaps with the constructors available
* superClass: name of super Class
* imported: flag that tells if the class was imported or not (for printing proposes)

## SymbolMethod

This Class represents a method and holds all of its information.

The SymbolMethod class extends the Symbol Class and has the following extra fields:
* symbolTable: hashmap with the local variables (including arguments)
* types: arraylist with the method signature
* num: unique id that represents the class

## SymbolVar

This Class represents a variable and all of its information.

The Symbolvar class extends the Symbol Class and has the following extra fields:
* initialized: whether the variable has been initialized, partially initialized (in an if or while) or non-initialized
* constant: if the variable is constant, then constant holds its value
* initiated: where the variable has been initiated before (used in code generation)
* changedInIfOrWile: whether the variable value has changed in a while or if (used with constant value)


## Code Generation 
The code is generated to a folder called jasmin. 

The best instructions are chosen in each situation. The code is not wrote directly to the file but to a buffer, in order to compute the *limit_stack* and the *limit_locals* values. 
After the limit of the stack and locals is computed, the instructors are wrote to the output file followed by the code present in the buffer.

We opted for an iterative approach: 
* Generates class structure with constructor;
* Generates the fields;
* Generates methods;
    * Generates the correct number for limit_stack and limit_locals;
    * Generates the variable assignments;
    * Generates arithmetic operations;
    * Generates method invocations;
    * Selects the best instruction to load and store variables;
    * Selects the best instruction when incrementing variable;

## Overview

In the developing of this program, we didn't use any third-party tools and/or packages.
All the algorithms implemented were developed by us.
All the program's structure was also developed by us.
The ASTree is analysed 2 times over the program’s execution, one for building the Symbol Table and the Semantic Analysis and one for Generating the Code.
We opted for building the Symbol Table and performing the Semantic Analysis at the same time since we felt it wasn't necessary to separate the two, decreasing the number of times we went through the ASTree.
Although this allowed for a faster run-time, it resulted in a Class with Many Methods. Although the developed classes are quite big, since we refractor the code quite a lot, we feel that it isn't confusisng.
We feel that we made a good trade: larger functions for code reusability. 

## Task Distribution
(Identify the set of tasks done by each member of the project. You can divide this by checkpoint it if helps)

* Cláudia Martins
* Filipa Senra - Parser, Syntactic Errors, Semantic Analysis with Cláudia, Code Generation With the Rest of the Group.
* Ana Teresa
* Raul Viana

## Pros

TODO

## Cons

TODO