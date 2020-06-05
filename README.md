# COMPILERS Project - Java Compiler

## Group and Self-evaluation

###  Group A3
* Cláudia Martins - up 
* Filipa Serna - up 
* Ana Teresa - up 
* Raul Viana - up 2010208089
### Self Evaluation
* Cláudia Martins  Grade: , Contribution:
* Filipa Serna     Grade: , Contribution:
* Ana Teresa       Grade: , Contribution:
* Raul Viana       Grade: , Contribution:
<br><br>
Project Grade: 

## Summary
This project's main goal was to apply the theoretical principals of the course Compilers practically. This was achieved by building a compiler for programs written in the yalm language.
The main parts of the project are **Syntactic error controller**, **Semantic analysis**, and **Code generation**.

## Compile

To compile the program, run ``gradle build``. This will compile your classes to ``classes/main/java`` and copy the JAR file to the root directory. The JAR file will have the same name as the repository folder.

### Run

To run you have two options: Run the ``.class`` files or run the JAR.

##Usage
```cmd
java Jmm <filename> -debug(-ast/-semantic) and/or -error
```

Without the -error flag, non-initialized variables just trigger **warnings**.
With the -error flag, non-initialized variables trigger **errors**!
The -debug flag prints both the ASTTree and the Symbol Table.
The -ast flag prints the ASTTree.
The -semantic prints the Symbol Table.

### Run ``.class``

To run the ``.class`` files, do the following:

```cmd
java -cp "./build/classes/java/main/" <class_name> <arguments>
```

Where ``<class_name>`` is the name of the class you want to run and ``<arguments>`` are the arguments to be passed to ``main()``.

### Run ``.jar``

To run the JAR, do the following command:

```cmd
java -jar <jar filename> <arguments>
```

Where ``<jar filename>`` is the name of the JAR file that has been copied to the root folder, and ``<arguments>`` are the arguments to be passed to ``main()``.

### Test

To test the program, run ``gradle test``. This will execute the build, and run the JUnit tests in the ``test`` folder. If you want to see output printed during the tests, use the flag ``-i`` (i.e., ``gradle test -i``).


## Semantic Analysis 
1. All semantic checks report errors except the variable initialization, wich reports warning. 
2. Symbol Table

* global: it has been included info about imports and declared class;
* class-specific: was included info about extends, fields and methods;
* method-specific: included info about arguments and local variables;
* allows method overload;
* allows access from semantic analysis and code generation;
* allows turn it on/off by arguments.

3. Type Verification
    
* verify between same type operations only;
* doesn't allow operations between arrays;
* allows array access to arrays only;
* allows assignment type equals assigned type only;
* allows int values to index accessing array only; 
* allows boolean operation with booleans only;
* allows conditional expression result to be a boolean only;
* raises a warning if variable not initialized, not an error;
* assumes parameters as initialized;
* raises warning of possibly variable not initialized if initialized inside if or else block;
 
			
4. Function Verification

* verifies if method target exists and if it has the called method;
* verifies if the type is of the declared class (this) and if so verifies if the method is extended from another class;
* if the method isn't from the declared class verifies if it was imported;
* verifies if the number of invoked arguments equals the number of declared ones;
* verifies if the parameter types match the argument types;
allows method oveloading.


#### Code Generation 
* [DONE] estrutura básica de classe (incluindo construtor init)
* [DONE] estrutura básica de fields
* [DONE] estrutura básica de métodos (podem desconsiderar os limites neste checkpoint: limit_stack 99, limit_locals 99)
* [DONE] assignments
* [DONE] operações aritméticas (com prioridade de operações correta)
* [DONE]invocação de métodos



**DEALING WITH SYNTACTIC ERRORS: (Describe how the syntactic error recovery of your tool does work. Does it exit after the first error?)
**SEMANTIC ANALYSIS: (Refer the semantic rules implemented by your tool.)
**INTERMEDIATE REPRESENTATIONS (IRs): (for example, when applicable, briefly describe the HLIR (high-level IR) and the LLIR (low-level IR) used, if your tool includes an LLIR with structure different from the HLIR)
**CODE GENERATION: (describe how the code generation of your tool works and identify the possible problems your tool has regarding code generation.)
**OVERVIEW: (refer the approach used in your tool, the main algorithms, the third-party tools and/or packages, etc.)
**TASK DISTRIBUTION: (Identify the set of tasks done by each member of the project. You can divide this by checkpoint it if helps)
**PROS: (Identify the most positive aspects of your tool)
**CONS: (Identify the most negative aspects of your tool)