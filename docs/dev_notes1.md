## ������Կ�������

> �����������ڼ�¼���ڿ����Լ�JVM���Թ����е�ѧϰ���̣�һ��Ϊ�˼�¼�Լ��ĳɳ�����������ܹ��������ⷽ��������ˣ��Ǿ��ٺò�����     - Falcon

###2013/12/24 ƽ��ҹ 
�ҵ�Ŀ��������JVM���ԡ�Ŀǰ�Ѽ�һЩ���ϣ�����JVM�����ֽ��뷽������Ϻ��١�

Ŀǰ���˵������� 

 http://stackoverflow.com/questions/3380498/create-a-jvm-programming-language
 
������������������JVM����Ҫ������

Ҫ����JVM���� Ŀǰ���ǵ� ʵ�ֲ���:

1. ������淶 �����£��������¡� ����ʵ�����������ʵ����������Ŀ���������� ���û��һ�������ֽ���ָ�ʵ�ּ򵥵�������ܣ�
2. �����﷨���� ��ʹ��ANTLR ���ɴʷ����﷨���������﷨�Ծ���Ϊ�����ֽ׶�ֻʵ�� if, for, �ͱ�����
3. �����﷨������
4. �����﷨���������ֽ��� ������ֱ�����ɵ��ڴ棬 Ҳ�������ɵ� class �ļ���

��ʵ��϶��ԵĻ����﷨���嵹�����ⲻ��

֮ǰ���ˡ����Ʊ�����ԡ��Ȿ�顣���Ǵ����ҵ���Ȥ֮�ţ������Ƚ��ź����ǣ����в���C������������������Java��ʵ�֡�

���һ�����Եļ������֣��ʷ����﷨�� ANTLR4 ��ɣ� ���� AST �����ɺͽ���Ŀǰ��֪����ô�������� http://www.antlr.org/wiki/display/ANTLR3/LLVM �н�������ʹ��ANTLR3 ���� LLVM�ֽ�������ӣ�����ͬ���ź�����û����ȫ�����ҵ�Ҫ��JVM�ֽ������ɵĻ���˵ASM����ʵ�֣�������Ҫ������

��������û���ҵ��ֳɵ����ӡ�

����������ᵽ����ҳ���һ��ڿ� ��Language Implementation Patterns��һ�顣

���п����� ��Lua implementation�� һ��С�ֲᣬ ���пտ�����

�ܶ���֮����Ҫ�����У�

1. ���˽���������漰����֪ʶ
2. ������������ֽ���
3. ANTLR4 �﷨��Ϥ

###2013/12/25 ʥ��

��������Ѹ�ٿ����� Lua 5.0 implementation�� ����˽��� Lua ��������һЩ��ơ� Lua �ǲ��û��ڼĴ�����ִ��ģ�ͣ�Lua 5.0 ���Ժ�֮ǰ�汾���û���ջ��ģ�ͣ��� Scanner �� �ݹ��½���Parser ������д����˵�������ܺ�Ч�ʻ��һЩ��������������������һ������Կ���LuaȷʵС�ɡ�Lua ������û�в��� �м���(IR)������ֱ���ڽ��������ʱ�����������ָ�

���ڼĴ����������ͨ��������������⣺ �����С����Ϊָ���к��в����룩�� ������Ѷȡ� ����������Ի��ڼĴ������ɵ�ָ��������٣�ӦΪ����ջ����ÿ��������������ջ����ջ�Ĳ�����

�Ա�Lua ǰ�� Stack-based Machine �� Register-based Machine ��ָ������

����ǼĴ����ģ�

    local a,t,i     1: LOADNIL 0 2 0
    a=a+i           2: ADD 0 0 2
    a=a+1           3: ADD 0 0 250 ; 1
    a=t[i]          4: GETTABLE 0 1 2

���������ջ�ģ�

    local a,t,i     1: PUSHNIL 3
    a=a+i           2: GETLOCAL 0 ; a
                    3: GETLOCAL 2 ; i
                    4: ADD
                    5: SETLOCAL 0 ; a
    a=a+1           6: GETLOCAL 0 ; a
                    7: ADDI 1
                    8: SETLOCAL 0 ; a
    a=t[i]          9: GETLOCAL 1 ; t
                    10: GETINDEXED 2 ; i
                    11: SETLOCAL 0 ; a

������� ���� Lua ֻ�� 35�������ָ�ȷʵ���٣��Ա����Ҵ�Java�������ᰡ����Ȼ��Ҳû�취�������ǲ�ͬ����������ƣ��r(������")�q��

a language is just a set of valid sentences

### 2013/12/27 

��Language Implementation Patterns��ȷʵ�ʺ����ֿ��������ϴ��룬����������

���������ᵽ�ķ����޸���֮ǰ����һ�� ������Lexer���ɶ��������ܶࡣ�����ʱ��ֱ�Ӳ������з�����δ����������кô���

��ʼ�¼һЩ����

LL(n)

- ��һ��L ����˼�� �� read the input from left to right
- �ڶ���L ����˼�� :  descend into parse tree children from left to right
- (n) ����˼��:  ��ʾ lookahead token �������� ������ǰ�࿴n��token

####һЩ�﷨�ʹ���֮��Ĺ�ϵ

1. (alt1 | alt2 | ... | altn)
 
   ![alt.png](imgs/implement language/alt.png "alt")
   
    ͬʱ�����ֿ�ѡ�ṹ��Ӧ�ĳ������ if-else, �����LL(1)������ʹ��switch-case
    
        if ( ?lookahead-predicts-alt1 ? ) { ?match-alt1 ? }
        else if ( ?lookahead-predicts-alt2 ? ) { ?match-alt2 ? }
        ...
        else if ( ?lookahead-predicts-altN ? ) { ?match-altN ? }
        else ?throw-exception ?

2.  (T)?

    ![optional.png](imgs/implement language/optional.png "optional")
    
    ����ṹҲ�Ͷ�ӦΪ
    
        if ( <lookahead-is-T ) { match(T);}    

3.  (T)+

    ![one_or_more.png](imgs/implement language/one_or_more.png "one_or_more")
    
    ���������ʹ�� do-while ѭ��
    
        do {
            <code-matching-alternatives>
        } while ( lookahead-predicts-an-alt-of-subrule> )

4.  (T)*

    ![zero_or_more.png](imgs/implement language/zero_or_more.png "zero_or_more")
    
    ������������� while ѭ��
    
        while ( lookahead-predicts-an-alt-of-subrule> ) {
            <code-matching-alternatives>
        }
    
    

�������е�LL(1) Lexer ��˼·д�˸�������Lexer �� Parser��

### 2013/12/28
��ʵ��Parser��ʱ�������˵㿲����֪�������֯Token����˵�﷨�������Ҫ�����������������ò������⵽����ô�����ѵ�һ���﷨�е�ÿ����������һ���ࣿ

�벻������ͷ�ֲο��ˡ����Ʊ�����ԡ�����crowbar��ʵ�֣��������潫��ͬ�ı��ʽ(expression)�����(statement) �ֳ��˲�ͬ�����ͣ���û�����ɺܶ��ࣨ��Ȼ����c����ʵ�ֵ�(-__-)b������ôһ���ֹ������ҵ������ϸ�µ����� expression �� statement ���������ʣ���google ֮��

**expression** ͨ�������һ��ֵ�����԰������������������Լ��������á�

> Mathematics a collection of symbols that jointly express a quantity : the expression for the circumference of a circle is 2��r.

**statement**

> In computer programming a statement can be thought of as the smallest standalone element of an imperative programming language. A program is formed by a sequence of one or more statements. A statement will have internal components (e.g., expressions).

statement ͨ�������� expression

�ҵ���Java����Ķ��壬���֮

**expression** -> An expression is a construct made up of variables, operators, and method invocations, which are constructed according to the syntax of the language, that evaluates to a single value

**statements** -> Statements are roughly equivalent to sentences in natural languages. A statement forms a complete unit of execution. The following types of expressions can be made into a statement by terminating the expression with a semicolon (;). ��Щ���ʽ���������Ϊ���(statement), ��Щ���ʽ��֮Ϊ expression statements. ���漸�����

- Assignment expressions
- Any use of ++ or --
- Method invocations
- Object creation expressions

����

    // assignment statement
    aValue = 8933.234;
    // increment statement
    aValue++;
    // method invocation statement
    System.out.println("Hello World!");
    // object creation statement
    Bicycle myBike = new Bicycle();
    
����֮�⻹������statement: **declaration statements** and **control flow statements**

����

    // declaration statement
    double aValue = 8933.234;
    
**block** -> A block is a group of zero or more statements between balanced braces and can be used anywhere a single statement is allowed.

�ã����ڶ�statement �ܽ����£�

    statements:
        | ---  expression statements
        | ---  declaration statements
        | ---  control flow statements
                    | ---  if-else statements
                    | ---  switch statements
                    | ---  while statements
                    | ---  do-while statements
                    | ---  for statements
                    | ---  branching statements
                               | --- break statements
                               | --- continue statements
                               | --- return statements
                               

������˼·�����ˡ�

���˵Lexer��������Token�Ļ�����ôParser�����ľ���Statement, ��֪���Բ��ԣ��д���֤������

�� ��Language Implementation Pattern�����п�����Parser��������֯ Token֮��ṹ���ϵ��

> LL(1) Recursive-Descent Parser

> This pattern analyzes the syntactic structure of the token sequence of a
phrase using a single lookahead token

���� **1** ����˼���ڵ�ǰ rule ��ֻ��һ��token������LL(1) ������������﷨������������

    expr: ID '++'
        | ID '--'
        
����ֻ��һ��Token��������ƥ��exprʱ��ǰ��һ��Token- ID, ���Ǵ�ʱ��������֧����ѡ��Ҫȷ���ĸ���֧��Ҫ�� ID ����ķ��Ųſ��ԣ��������ʱ����޷������ˡ�Ҫô���ø����ӵ� LL(k) �࿴�����ַ�, Ҫô�Ż�������������

    expr: ID ('++'|'--')
    
### 2013/12/30

Calc Parser �����ˣ�֮ǰ˼·���ԣ����Ǿ������������Statement���������ڲ������ˣ�ֱ�Ӱ����ķ������ӽ���ƥ��Ϳ����ˡ����������Ļ�ȷʵ����ǰ�˵���������ûʲô��������ͷ�ں�ˡ�

���쿪ʼ��LL(k) Parser�����ݡ� 

LL(k) Parser ����˼�壬ͨ����������� k ��Token�������﷨������

> The strength of a recursive-descent parser depends entirely on the
strength of its lookahead decisions.

����LL(k) Ҳ�ǹ̶��Ķ࿴ k ��Token��������һ�����Ƶ�ģʽ **Backtracing Pattern**, ������ǰ��������Token

LL(k) Parser��Ҫ�� LL(1) Parser �Ļ����϶� lookahead token ����������ԭ����token����Ϊ���飬���鳤��Ϊk��consume() ��ʱ��ʹ�� **%** ������֤�±겻���� k��
Ȼ�����������ķ��ĵط������жϣ��������Ҫ�����жϵڶ���Token���ܾ������������߼�

    if(LA(1) == TokenType.xxx && LA(2) == TokenType.yyy) {
        ...
    }
    

### 2014/1/1

�����һ�죬ͬ��������ĵ�һ�д����׸����Ʊ�����ԡ������ҵ�һ��С���룬Ҳ�ǽ���ĵ�һ��Ŀ�ꡣ

��ʵ�����Ʊ�����ԵĹ������ҽ��� ��Language Implementation Patterns�� ��Ϊѧϰָ���������������� **Enhanced Parsing Pattern** һ���֣����潫�漰�����ʵ�ָ�ǿ��� Parser �����ݡ�

��Ҫ�漰������ Parser��

- Backtracking Parser. 

  we use backtracking parsers only when we need to distinguish between similar language constructs.
- Memorizing Parser
- Predicated Parser


The parsers we��re working with in this book recognize context-free languages. A context-free language is a language whose constructs don��t
depend on the presence of other constructs.

#### Backtraking Parser
Syntactic predicates and speculative parsing are extremely useful when
parsing phrases that look the same from the left edge

**Backtraking Parser**: is to speculatively attempt the alternatives in order until we
find one that matches

�����֮���ǲ��ϳ��Ը��ַ�֧��ֱ���ҵ�ƥ���·��

ִ�й�����������

Upon success, the parser rewinds the input and
parses the alternative normally (we��ll see why we parse it twice when
we discuss actions). Upon failing to match an alternative, the parser
rewinds the input and tries the next one. If the parser can��t find any
matching alternative, it throws a ��no viable alternative�� exception

#### Memoizing Parser

This pattern records partial parsing results during backtracking to
guarantee linear parsing performance, at the cost of a small amount of
memory.

�����֮����� Parser ��Ҫ���þ��Ǽ�¼�Ѿ��������� Token�� ��������ݵ�ʱ������ظ�����

���Parserû���꣬(��o��) . z Z������������������

#### Predicated Parser

This pattern augments any top-down parser with arbitrary boolean expressions that help make parsing decisions.
These boolean expressions are called semantic predicates and specify
the semantic applicability of an alternative. Predicates that evaluate to
false effectively ��turn off�� a parser decision path. From a grammar point
of view, false predicates make alternatives invisible.


ͬ�������������



### 2014/1/2

������ֱ�ӿ����Է����Ĳ��֣�����AST��

֮ǰ���������Ĺ������Ǽ�������﷨���Լ���֤�����ǶԵġ����ڽ�����һ��ѧϰ��η��������﷨��

Ϊ���ܹ������ʶ����������еĲ������Ͳ�������������Ҫ����ĳ���м���ʽ��Intermediate Representation) IR��ͨ�����õ��� AST�� �ڹ���AST�Ĺ�����Ҳ��ʶ������������е��﷨�ṹ��

��Щת�������в���Ҫ IR�� �����Ĺ��̳�Ϊ Syntax-Directed�����罫 wiki ��ʽת��Ϊ Html ��ʽ����ת�������п������ַ��ķ����Html��Ӧ���롣

�� IR tree ��ص�ģʽ��

- Parse Tree. 

    Parse trees record how a parser recognizes an input sentence. The interior nodes are rule names, and the leaves are tokens. Although parse trees are less suitable than ASTs for most language applications, parsers can create them automatically.

- Homogeneous AST.

    all the nodes have the same type, we say that they are homogeneous. With a single node type, there can be no specialty fields to reference child subtrees. Nodes track children with lists of child pointers.

- Normalized Heterogeneous AST.

    Trees with a multitude of node types are called heterogeneous trees. Normalized heterogeneous trees use a normalized list of children like homogeneous trees.

- Irregular Heterogeneous AST.

    When we refer to an AST as heterogeneous, we also assume that the nodes have irregular children. Instead of a normalized child list, the nodes have named fields, one per child.
    
####�����е���
�ڿ����������Ĺ������漰����������һ���� Parse Tree����һ���� Abstract Syntax Tree

���IR Trees ��Ҫ�㣺
? Dense: No unnecessary nodes
? Convenient: Easy to walk
? Meaningful: Emphasize operators, operands, and the relationship
between them rather than artifacts from the grammar

The first two points imply that it should be easy and fast to identify patterns in the tree. The last point implies that the tree structure should be insensitive to changes in the grammar (at least those unrelated to language syntax)

#### AST ���ʵ�ֲ�����֮������ȼ�

To encode ��x happens before y,�� we make x lower than y in the tree. 

Operators with higher precedence appear lower in the AST. For example, in expression `3+4*5`, the * operator has higher precedence than +. The * subtree is a subtree of the + operator node. If we alter the precedence of the operators in the expression with parentheses, `(3+4)*5`, the + subtree becomes a child of the * node

![ast_opt_precendce.png](imgs/implement language/ast_opt_precendce.png "ast_opt_precendence")


#### Create AST for nonexecutable language statements

**Representing Pseudo-operations in ASTs**

��һЩ���ʽ��û�����ԵĲ�����������������һ���������ʱ�����ʹ��һ������token (imaginary token) ���䵱AST��root.

ͨ�� function declarations, class declarations, formal argument declarations ����Ҫ���ִ����ַ�

**Enforcing Tree Structure with the Type System**

��AST����������ϵͳ����֤���͵���ȷ��

### 2014/1/4 

�������е����ݣ�������Ϊֹ����Ϊ�Ѿ��˽��������дLexer �� Parser�Ļ�����ô�Ϳ��Կ���ʹ���Զ�����Lexer �� Parser�Ĺ����ˡ�

ANTLR4 �����ʱ���о���һ��ʱ�䣬���Ǻ���������³���ˡ�����ֻ������ѧһ�飬recall һ�£���Ȼѧ����Ӧ��һ����ѧ�꣬��Ȼ�ɱ��൱��( ��-�� )

���ڡ�Language Implementation Patterns�� ���õ���Antlr3�����Կ��� **Constructing ASTs with ANTLR Grammars** ��ʱ��������Antlr3������ι���AST�������ѵ���Ҫ�ϵ��ˣ���Ҫ��ѧAntlr3����ΪAntlr4���кܶ಻ͬ��4���Բ���visitor��listener��grammar����Ҫ�Ĳ���������Ӧ����֤���﷨�ļ��ĸɾ����������Ҫ�ص�3�����е�̫�Ǹ��ˡ�����

�������ã���������������ֻص����棬����������Java����ʵ��AST�����Զ�ʱ���˿�����

�ܽ���֮ǰ�ᵽ��IR���ڹ���IR��ʱ������������ѡ��Parse Tree �� AST Tree��������AST�������ַ�����Homogeneous(ͬ�ʵ�) AST�� Normalized Heterogeneous AST �� Irregular Heterogeneous(���ʵģ��ϳɵ�) AST�������ߵĲ��ص㶼��ͬ��������Ҫ������Ҫ����ѡ��

**IR**

- Parser Trees

        Pros: Parser generators can automatically build these for us. 
        Cons: Parse trees are full of noise (unnecessary nodes). They are sensitive to changes in  the grammar unrelated to syntax.
- AST Trees
    - Homogeneous AST
        
            Pros: Homogeneous trees are very simple.     
            Cons: It��s cumbersome to annotate AST nodes because the single node type has the union of all needed fields. There is no way to add methods specific to a particular kind of node.
            
      �����֮��Homogeneous AST �Ľ�㽫���е����ͻ�ϵ�һ������ζ�����е�ʵ���ϲ�ͬ�Ľڵ�ӵ����ͬ�Ľṹģ�ͣ�����Ҳ���޷����ر�Ľڵ�ʩ�Ӷ��صĲ�����
    - Normalized Heterogeneous AST
    
            Pros: It��s easy to add operator or operand-specific data and methods. 
            Cons: Large grammars like Java��s need about 200 class definitions to be fully heterogeneous.
            
        Normalized Heterogeneous AST �൱����������Ͳ�Σ����ڲ�ͬ�����Ͷ�Ϊ�佨����Ӧ��class�������Ļ�����﷨���ӣ���ôclass���ἱ��������
    - Irregular Heterogeneous AST

            Pros: It��s easy to add operator- or operand-specific data and methods. Building tree-walking methods for a small set of heterogeneous nodes is quick and easy.
            Cons: As with Normalized Heterogeneous AST, there are lots of AST classes to read and write. Having irregular children makes building external visitors difficult.
            
�������ע���Ľṹ��ôѡ Homogeneous AST �Ϳ����ˣ������Ҫ���ֽ�����ͣ���ôʹ�� Normalized Heterogeneous AST ���С����� Parse Tree�����������ַ��Ƚ϶࣬���Բ�̫���á�

####Parse Tree
Parse trees record the sequence of rules a parser applies as well as the tokens it matches. Parse trees describe sentence structure by grouping input symbols into subtrees.

But parse trees are extremely inconvenient to walk and transform. Parse trees are full of noise because of all the interior rule nodes. 

Nonetheless, parse trees are still very useful as intermediate representations for some tools and applications. For example, development environments use parse trees to good effect for syntax highlighting and error-checking. ����ǰ��˵����ô��Parser ���õĵط�������Parse Tree�� �﷨������ ������ȷ������п����ﾡ���õġ���Ϊ���ǿ��Զ�����е�һ�������ݽ��в�����

####AST
The key idea behind an AST is the operator-operand tree structure, not the node data type. (AST ��ע�������Ͳ�����) An AST contains the essence of the input token stream and the relationships between operator and operand tokens. We don��t need to use the type system of our implementation language to distinguish between nodes. Nodes in any AST derive from tokens,
so we can use the token type to identify nodes.

####Homogeneous AST

**A homogeneous tree implements an abstract syntax tree (AST) using a
single node data type and a normalized child list representation**

    public class AST {

        private Token token;
        private List<AST> children;
    
        public AST() {
    
        }
    
        public AST(Token token) {
            this.token = token;
        }
    
        public void addChild(AST child) {
            if (children == null) {
                children = new ArrayList<AST>();
            }
            children.add(child);
        }
    
    
        public boolean isNil() {
            return token == null;
        }
    
        public String toString() {
            return token == null ? "nil" : token.toString();
        }
    
        public String toStringTree() {
            if (children == null || children.size() == 0) {
                return this.toString();
            }
            StringBuilder treeToString = new StringBuilder();
            if (!isNil()) {
                treeToString.append("( ");
                treeToString.append(toString());
                treeToString.append(" ");
            }
    
    
            for (int i = 0; i < children.size(); i++) {
                AST subTree = children.get(i);
                if (i > 0) {
                    treeToString.append(" ");
                }
                treeToString.append(subTree.toStringTree());
            }
    
            if (!isNil()) {
    
                treeToString.append(" )");
            }
            return treeToString.toString();
        }
    
        public static void main(String[] args) {
            Token one = new Token("1", TokenType.NUM);
            Token two = new Token("2", TokenType.NUM);
            Token add = new Token("+", TokenType.OP_ADD_TOKEN);
    
            AST root = new AST(add);
            root.addChild(new AST(one));
            root.addChild(new AST(two));
    
            System.out.println("1 + 2: " + root.toStringTree());
    
            AST list= new AST();
            list.addChild(new AST(one));
            list.addChild(new AST(two));
    
            System.out.println("list: " + list.toStringTree());
    
        }
    }

#### Normalized Heterogeneous AST

**This pattern implements an abstract syntax tree (AST) using more than a single node data type but with a normalized child list representation.**

This pattern makes the most sense when we need to store node-specific
data and plan on using External Tree Visitor. The normalized child list makes it much easier to build external visitors.

#### Irregular Heterogeneous AST

**This pattern implements an abstract syntax tree (AST) using more than a single node data type and with an irregular child list representation.**

In the implementation of its child pointers. Instead of a uniform list of children, each node data type has specific (named) child fields. In this sense, the child pointers are irregular. In some cases, named fields lead to more readable code. For example, methods can refer to left and right instead of, say, `children[0]` and `children[1]`.

�ڽ����Ĺ����У�������˶���ѡ������ģʽ����Ϊ�ڲ�ͬ���͵�Class����Ӳ�ͬ���ֶο���ȥ������ô������

��������ڵ�����������ϸ����֣�����

    class AddNode extends ExprNode {
        ExprNode left, right;
        .....
    }
       
�� Normalized Heterogenous AST ��������

    class AddNode extends ExprNode {
        List<AST> children;
        ...
    }

###2014/1/7
����Ҫ��ʼ���� AST �ı����ˣ��ڱ��������п��ܻ�����еĽڵ�����޸ĺ͸��£�������г����۵���
#### Tree walking

���������������Ҫ���������ݽṹ����ѧ���ı�����������Ϊ���������״̬�����������ݽṹ�������������򵥣���������Ϊ�������ض���ͬ

- whether we have the source codefor our tree nodes,
- whether the trees have normalized children, 
- whether the trees are homogeneous or heterogeneous, 
- whether we need to rewrite trees while walking, and 
- even in which order we need to walk the nodes.

������Ҫѧϰ�Ĵ���ģʽ��

- Embedded Heterogeneous Tree Walker. 

    Heterogeneous AST node classes define walking methods that execute appropriate actions and walk any children. Tree walking code is distributed across potentially hundreds of class files.
    
    Ҳ���ǽ��������нڵ�ʱ�����߼�д�ڸ��ڵ���
- External Tree Visitor.

    This pattern encapsulates tree walking code (for both homogeneous and heterogeneous ASTs) into a single class definition. It allows us to alter tree-walking behavior without altering AST node definitions. Both WALKING TREES AND VISITATION ORDER 117 the visitor and embedded walker pattern are straightforward but tedious to implement manually.
    
    ʹ���ⲿvisitor ����Tree
- Tree Grammar.

    A tree grammar describes the structure of valid ASTs. Just as we can automate parser construction from grammars, we can automate tree visitor construction from tree grammars. Tree grammars work on homogeneous or heterogeneous trees because they rely on node token types rather than node types (like AddNode). Tree grammars explicitly dictate node visitation order like the embedded walker and visitor patterns.
- Tree Pattern Matcher.

    Instead of specifying an entire tree grammar, this pattern lets us focus on just those subtrees we care about. That��s useful because different phases of an application care about different parts of the tree. A tree pattern matcher also decouples the order in which we apply tree patterns from the tree patterns themselves.
    
    ����Pattern�ͱ�ʾ���ǿ���ֻ��עȫ���е�һ�������, ��Ȼ���Pattern�㹻���Ļ����ܺ���ȫ����
    
#### Walking Trees and Visitation Order

**Visit**

visiting a tree, we mean executing some actions on the nodes of a tree.  The order in which we traverse the nodes is important because that affects the order in which we execute the actions. 

�������ع�һ�¶����������ķ��������ڱ��ʽ 1 + 2, ���ݱ��������Ĳ�ͬ���Բ�����ͬ������

- ǰ����� (+ 1 2)���ȷ��ʸ��ڵ��ٷ����ӽڵ�
- ������� (1 + 2)���ȷ����ӽڵ�Ȼ�󸸽ڵ㣬�����ʣ�����ӽڵ�
- �������� (1 2 +)���ȱ������ӽڵ㣬Ȼ����Ƿ��ʸ��ڵ�

����ı���˳�򶼿���ʹ�� **������ȱ���(depth-first)** ʵ��

**Walk**

**Visiting a node means to execute an action somewhere between discovering and finishing that node.** But, that same discovery sequence can generate three different tree traversals (node visitation sequences). It all depends on where we put actions in walk( ).

**Difference between Tree Walking and Visiting**

// Page 120  

û������

**Embedded Walker VS External Tree Visitor**

Embedded Walker ��Ҫ�ڽڵ�����Ӷ�����������Ҳ�ܻ�����˵�֪���ڱ�������ڵ��ʱ������Щʲô�£����������������ṩ��ͬ��һ�׶��������ǲ��Ǿ͵���Դ��������нڵ㶼��һ���ˣ� **���ڹ۲죬������չ**

External Tree Visitor �������ı��������ŵ�ר�ŵ�visitor�У����������ͬһ�����ṩ��ͬ�ķ����㷨���������ú� Embedded �෴�������߼����ڽڵ���һ���̶���������ģ���ԣ����Ƽ��ξ����ˡ� **������չ, ���ڹ۲�**

#### Walking Trees and Rewriting Trees ʹ�õ�Patterns

Waling Tree �� Rewriting Tree ��Ŀ�Ķ��Ƕ�ϣ�������ϵĽڵ���в���������Ҳ���ж����нڵ�Ĳ�����Ҳ���жԲ��ֽڵ�Ĳ����������ҪGrammer����Pattern��ָ����Ҫ�����Ľڵ������

- **Embedded Heterogeneous Tree Walker**

    Embedding walking methods in tree nodes is the simplest mechanism for building a tree walker
- **External Tree Visitor**

     Visitors are useful for collecting information or doing some simple interpretation, such as expression evaluation. They are not well suited to tree pattern matching applications. Visitors simply walk the tree node by node.
     
    Visitor ֻ�ܱ������нڵ㣬���ǲ�������� tree�� pattern match
- **Tree Grammar**

    Tree grammars specify the structure of entire trees. They are most effective when we need to execute actions in many or most rules. For example, tree-walking code generators typically need to emit code for every subtree.
- **Tree Pattern Matcher**

     To operate only on a specific subset of an AST structures, we can use a tree pattern matcher.
     
#### Embeded Heterogeneous Tree Walker

�����нڵ����һЩ�ݹ���õķ�������ǰ����������������ڽڵ��ģС��ʱ���ֱ�ۣ�����Ҫ��̬�ı������������Щ�����Ļ����������ܡ�

#### External Tree Visitor

Visitors combine tree walking and action execution code outside the AST node definitions.we can change the functionality of the tree walker without having to change the AST class definitions and can even switch visitors on the fly. An external visitor can walk either heterogeneous or homogeneous AST nodes.

ʵ�ֵķ��������֣�

1. ���ݽڵ�������������������ǱȽϴ�ͳ��Visitor Pattern
2. ����Token������������

��������ͳ��Visior

**���ݽڵ����͵�Visitor**

> Visitor Pattern: relies on a ��double-dispatch�� method within each AST node. The double-dispatch method redirects `visit( )` calls on a node to an appropriate method in a visitor servicing that node type. The visitor is like a set of callback methods. 

���͵�visitor�����������ģ�

    for a Node, use a visitor  ---->  Node.visit(visitor) --- dispatch to ---> visitor.visit(Node)
    
��������

    /
    **
    A generic heterogeneous tree node used in our vector math trees
    *
    /
    public abstract class VecMathNode extends HeteroAST {
        public VecMathNode() {;}
        public VecMathNode(Token t) { this.token = t; }
        public abstract void visit(VecMathVisitor visitor); // dispatcher
    }
    
    Ȼ��Ҫ�����нڵ����
    
    public void visit(VecMathVisitor visitor) { visitor.visit(this); }
    
    Visitor�Ķ���
    
    public interface VecMathVisitor {
        void visit(AssignNode n);
        void visit(PrintNode n);
        void visit(StatListNode n);
        void visit(VarNode n);
        void visit(AddNode n);
        void visit(DotProductNode n);
        void visit(IntNode n);
        void visit(MultNode n);
        void visit(VectorNode n);
    }
    
�������ַ�����Ҫ���ⲿ Visitor �����ö�̬�����Թ������ÿ�����͵� visit �����������֪������ڵ����ͺܶ�Ļ����Ƕ����ĵ��£�(-��-)��visitor ת��תȥ��Ҳ�����Ρ�

���ַ���ͬ���޷���ȫ�� visitor ��AST����ֿ�����Ϊ��Ҫ�����Եļ���һ�� `visit()` ��ÿ���ڵ���

**����Token���͵�Visitor**

we can distinguish between tokens using the token type, we can also distinguish between AST nodes using the token type. By switching on the token type rather than the AST node type, we can avoid the `visit()` method in each AST node

���ǿ���ֻ��visitor�з�һ��visit�������ɣ�����Ҫ����Node�ڵ�

    public void print(VecMathNode n) {
        switch ( n.token.type ) {
        case Token.ID : print((VarNode)n); break;
        case Token.ASSIGN : print((AssignNode)n); break;
        case Token.PRINT : print((PrintNode)n); break;
        case Token.PLUS : print((AddNode)n); break;
        case Token.MULT : print((MultNode)n); break;
        case Token.DOT : print((DotProductNode)n); break;
        case Token.INT : print((IntNode)n); break;
        case Token.VEC : print((VectorNode)n); break;
        case Token.STAT_LIST : print((StatListNode)n); break;
        default :
        // catch unhandled node types
        throw new UnsupportedOperationException("Node " +
        n.getClass().getName()+ " not handled" );
        }
    }
    
#### Tree Grammar

Tree grammars are a terse and formal way of building an external visitor. Visitors generated from tree grammars are usually called tree parsers because they are the two-dimensional analog of conventional parsers.

Tree grammars do not care about the implementation language
classes used to represent AST nodes (they work with both homogeneous and heterogeneous AST nodes). Instead, they rely on token type differences between AST node token payloads to distinguish different kinds of nodes

Tree grammars ͬ���ǿ��Խ������ɹ����������

���н���ANTLR3 ������ʵ�֣�����ͬ�������׿����ģ�����

#### Tree Pattern Matcher

This pattern walks trees, triggering actions or tree rewrites as it encounters tree patterns of interest. The process of matching and rewriting trees is formally called term rewriting.

ͨ������Pattern -> action���ڱ�������ʱ�򣬶��ڷ���Pattern�ľͲ��ú�Pattern��Ӧ��acion

Tree Pattern Match �� Tree Grammar �Ĳ�֮ͬ����

1. We have to specify patterns only for the subtrees we care about.
2. We don��t need to direct the tree walk.

A tree pattern matcher is analogous to text rewriting tools such as awk, sed, and perl.

A tree grammar needs a pattern for each subtree. The grammar rules include traversal instructions just like a hand-built visitor 

���Ǹ���Ȥ���� **�����Pattern** �Լ� **ƥ��ʱ�Ķ���**��

�����֮��Tree Grammar ��ע����ÿ��subtree�Ľṹ������ Tree Pattern Matcher ��ע�ֲ��������������

��һ�ڹ���Rewriting �����ӻ��ǲ���ģ����油��

// TODO: Rewriting Examples


### 2014/1/13

ǰ���������¡��ų�֮Ұ������������Щѧϰʱ�䣬��������

���쿪ʼ�� **Tracking and Identifying Program Symbols**

���ⲿ�ֿ�ʼ���漰���������(semantic analysis)��������.

Semantic analysis is just a fancy way to say we��re ��sniffing a program to see whether it makes sense.�� The term semantics is synonymous with ��meaning.��

����˵���ǿ������Ƿ���ȷ���ó��ֵ��Ƿ��������ó��ֵĵط�������һ���ֻὲ�����ʵ�־�̬���ͼ�飬����Ҫʵ�����������ҪһЩ��������Ҫ����������ű�Ҫ��������������Ҫһ����¼ symbol (A symbol is just a name for a program entity like a variable or method.) �ĵط���

computer language sentences can define and reference symbols in code blocks. At the end of the code block, the symbols go out of scope

�������(symbol) ��Ϣ�����ݽṹ���� symbol table

### 2014/1/14

��һ�����漰����Pattern ��������һ��������������ģ���һ�������������

- Symbol Table for Monolithic Scope

    All symbols exist within a single scope (set of symbols). Simple property files and early BASIC are the best examples
- Symbol Table for Nested Scopes

    There are multiple scopes and scopes can nest inside other scopes. C is a typical language that has nested scopes. Scopes start and end at the start and end of language structures such as functions.
    
Ҫ�����������Pattern������Ҫ�˽⣺ **��α�ʾsymbol**, **��ν�symbol�����������ķ��ű�**, **��ν���symbol�Ķ���**

��������Ҿ�������Token��Symbol��������Ϊ����ȥ����ĳ�ֲ�������С��λ����������ǣ����������������ƣ��������ڲ�ͬ�Ľ׶Ρ�

#### �����֯�����г��ֵķ���

�����������ĳ���

    class T { ... }; // define class T
    T f() { ... } // define function f returning type T
    int x; // define variable x of type int 
    
��������Ҫ�����µ�symbol��ʾ

    Type c = new ClassSymbol("T" ); // define class
    MethodSymbol m = new MethodSymbol("f" , c); // define method
    Type intType = new BuiltInTypeSymbol("int" ); // define int type
    VariableSymbol v = new VariableSymbol("x" , intType); // define var x
    
Symbol������:

- Name: Symbols are usually identifiers like x, f, and T, but they can be operators too.
- Category: That is, what kind of thing the symbol is. Is it a class, method, variable, label, and so on.  ������Ҫ�ж�ֻ���� method �������ϲ��ܽ��з�������
- Type: To validate operation x+y, for example, we need to know the
types of x and y. ��̬���ԣ���Python)������ʱ��֪���������ͣ���̬������ Java�����ڱ����ʱ���֪����������

#### Symbol Table for Monolithic Scope

This pattern builds a symbol table for a language with a single, flat scope. This pattern is suitable for simple programming languages (without functions), configuration files, small graphics languages, and other small DSLs.

#### Symbol Table for Nested Scopes

This pattern tracks symbols and builds a **scope tree** for languages with multiple, possibly nested scopes.

Programming language functions are a good example of nested scopes.

Ҳ����˵Ҫʵ��function�����ʹ�����ģʽ

������

    int i = 9;
    float f(int x, float y)
    {
      float i;
      { float z = x+y; i = z; }
      { float z = i+1; i = z; }
      return i;
    }
    void g()
    {
      f(i,2);
    }
    
��������򵥵ĳ���������������

![symbol_nested_scopes.png](imgs/implement language/symbol_nested_scopes.png "symbo_nested_scopes")

���� MethodSymbol ͬʱ����Symbol ���� Scope.

Methods have two scopes actually: one for the parameters (the MethodSymbol itself) and one for its local variables (a LocalScope whose parent is the MethodSymbol).

��һ�����ڿ��������﷨�Ĺ�������ô��������

### 2014/1/15

�����ᵽ��push �� pop ��������һ��ʵ�ʵĲ���ջ��push��ͨ�� new һ���µ�Scope Ȼ�󽫵�ǰ��scope�ṩ���µ�scope��Ϊ������pop���Ƿ��ص�ǰscope�����scope�����Ƶ�ջ��������scopeҲ���ǵ�ʱ����scope����ȥ�Ĳ���

// TODO: Antlr4 ʵ�ִ���

�ɿ���Թ���

����Ϳ� Manageing Symbol Table for Data Aggregates

��Ҫ���ܵ��� ��δ������ݼ��ϣ�����Class�� structs�� ��һ���ֵĺ�����Ҫ����̬���ͼ���ʵ�֡������������־Ϳ��Զ���ʵ���µ�ĿǰΪֹ�����ݣ���Ҫ�����Ȱ�Tree�����������������������

### 2014/1/17

���췢���������ˣ�ûʱ�俴�����������

��̬���ͼ�����������۸��ţ����Ͽ�ʼANTLR 4 ��ѧϰ�������ټ��������ۿ�����Ҫ��;���ǣ�(-__-)b

���¿���The Definitely ANTLR 4 Reference������Ϊ֮ǰ���˺ܶ����֪ʶ���̵棬���������׶��ˣ�ѧϰ������Ȼ����Ҫѭ�򽥽�����������

��ͬ�� ANTLR 3 ���ǣ� 4 ����Ƹ�������չ����Ϊ֧�ֽ��� Tree �ķ��ʷŵ� Grammar �ļ����棬��֤���﷨�ļ��ĸɾ���ͬʱҲ������չ����ȻΪ����ǰ���ݣ�4 ����֧�����﷨�ļ���ʹ�ô���εġ�

���� 4 �����ﲢû�н���AST�����ݣ��������������ݲ������﷨���⡣�������ڿ��˵�һ�µĸ������������Ϊ���Խ�Grammar�ļ���Ĵ���Ƭ�λ���Actions �ŵ�Visitor��ȥ��

����һ�� �ݹ��½�(recursive-decent) �Ľ���

> descent: refers to the fact that parsing begins at the root of a parse tree and proceeds toward the leaves(tokens)