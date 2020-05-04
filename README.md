# COMP - Project 1

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

## Test

To test the program, run ``gradle test``. This will execute the build, and run the JUnit tests in the ``test`` folder. If you want to see output printed during the tests, use the flag ``-i`` (i.e., ``gradle test -i``).

### Check List

#### Semantic Analysis 
1. [DONE] Todas as verificações feitas na análise semantica devem reportar erro excepto a verificação de inicialização de variáveis que deverá apenas dar um warning:
2. Symbol Table

    . [DONE] global: inclui info de imports e a classe declarada 
    
    . [DONE] classe-specific: inclui info de extends, fields e methods
    
    . [DONE] method-specific: inclui info dos arguments e local variables
    
    . sub topics:
       + [DONE] tem de permitir method overload (i.e. métodos com mesmo nome mas assinatura de parâmetros diferente)
       + [DONE] tem de permitir consulta da tabela por parte da análise semantica (e geração de código)
       + [DONE] tem de permitir ligar e desligar a sua impressão para fins de debug (neste caso para fins de avaliação)

3. Type Verification
    
    . [DONE] verificar se operações são efetuadas com o mesmo tipo (e.g. int + boolean tem de dar erro)

    . [DONE] não é possível utilizar arrays diretamente para operações aritmeticas (e.g. array1 + array2)
    
    . [DONE] verificar se um array access é de facto feito sobre um array
    
    . [DONE] verificar se o indice do array access é um inteiro 
    
    . [DONE] verificar se valor do assignee é igual ao do assigned (a_int = b_boolean não é permitido!)
    
    . [DONE] verificar se operação booleana é efetuada só com booleanos
    
    . [DONE] verificar se conditional expressions (if e while) resulta num booleano
    
    . [DONE] verificar se variáveis são inicializadas, dando um WARNING em vez de ERRO
    
       + [DONE] parametros são assumidos como inicializados
       + [DONE] devem fazer uma análise através do control flow, i.e., se há um if e a variável só é inicializada dentro de ou o then ou o else, deve-se dar um warning a indicar que poderá não estar inicializada
       + [DONE] será considerado bónus a quem resolver esta verificação usando erros em vez de warning.
            - cuidado que se a analise não estiver bem feita os erros vão fazer com que o vosso compilador não passe para a geração de código!
			- caso pretendam fazer esta abordagem com erros adicionem uma forma de ativar/desativar o erro para facilitar no caso de haver problemas.
			
4. Function Verification
	* [DONE] verificar se o "target" do método existe, e se este contém o método (e.g. a.foo, ver se 'a' existe e se tem um método 'foo')
	    - [DONE] caso seja do tipo da classe declarada (e.g. a usar o this), verificar se é método do extends olhando para o que foi importado (isto se a classe fizer extends de outra classe importada)
	* [DONE] caso o método não seja da classe declarada, isto é importada, verificar se método foi importado
	* [DONE] verificar se o número de argumentos na invocação é igual ao número de par âmetros da declaração
	* [DONE] verificar se o tipo dos parâmetros coincide com o tipo dos argumentos
	    - [DONE] não esquecer que existe method overloading

#### Code Generation 

* [DONE] estrutura básica de classe (incluindo construtor <init>)
* [DONE] estrutura básica de fields
* [DONE] estrutura básica de métodos (podem desconsiderar os limites neste checkpoint: limit_stack 99, limit_locals 99)
* [DONE] assignments
* operações aritméticas (com prioridade de operações correta)
	- neste checkpoint não é necessário a seleção das operações mais eficientes mas isto será considerado no CP3 e versão final
* invocação de métodos
