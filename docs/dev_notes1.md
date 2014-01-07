## 编程语言开发札记

> 以下文字用于记录我在开发自己JVM语言过程中的学习历程，一是为了记录自己的成长，而是如果能够帮助有这方面需求的人，那就再好不过了     - Falcon

###2013/12/24 平安夜 
我的目标是制作JVM语言。目前搜集一些资料，但是JVM生成字节码方面的资料很少。

目前看了的资料有 

 http://stackoverflow.com/questions/3380498/create-a-jvm-programming-language
 
从里面整理了下生成JVM语言要做的事

要制作JVM语言 目前考虑的 实现步骤:

1. 虚拟机规范 第四章，及第六章。 （其实虚拟机类似真实机器，所以目标首先是像 运用汇编一样运用字节码指令，实现简单的输出功能）
2. 程序语法定义 （使用ANTLR 生成词法和语法解析器，语法以精简为主，现阶段只实现 if, for, 和变量）
3. 抽象语法树生成
4. 解析语法树，生成字节码 （可以直接生成到内存， 也可以生成到 class 文件）

其实相较而言的话，语法定义倒是问题不大。

之前买了《自制编程语言》这本书。算是打开了我的兴趣之门，不过比较遗憾的是，书中采用C语言来描述。我想用Java来实现。

针对一门语言的几个部分，词法和语法用 ANTLR4 完成， 但是 AST 的生成和解析目前不知道怎么做，看到 http://www.antlr.org/wiki/display/ANTLR3/LLVM 有介绍生成使用ANTLR3 生成 LLVM字节码的例子，不过同样遗憾的是没能完全满足我的要求。JVM字节码生成的话据说ASM可以实现，所以需要看看。

看来还是没能找到现成的例子。

除了上面的提到的网页，我还在看 《Language Implementation Patterns》一书。

其中看到了 《Lua implementation》 一个小手册， 等有空看看。

总而言之，主要任务有：

1. 先了解编译语言涉及到的知识
2. 看看如何生成字节码
3. ANTLR4 语法熟悉

###2013/12/25 圣诞

早上起来迅速看了下 Lua 5.0 implementation， 大概了解下 Lua 编译器的一些设计。 Lua 是采用基于寄存器的执行模型（Lua 5.0 及以后，之前版本采用基于栈的模型）， Scanner 和 递归下降的Parser 都是手写，据说这样性能和效率会高一些，不明觉厉，不过从另一方面可以看出Lua确实小巧。Lua 编译器没有采用 中间表达(IR)，而是直接在解析程序的时候生成虚拟机指令。

基于寄存器，虚拟机通常会设计两个问题： 编码大小（因为指令中含有操作码）和 解码的难度。 但是总体而言基于寄存器生成的指令数会更少（应为基于栈，对每个操作数都有入栈，弹栈的操作）

对比Lua 前后 Stack-based Machine 和 Register-based Machine 的指令区别：

这个是寄存器的：

    local a,t,i     1: LOADNIL 0 2 0
    a=a+i           2: ADD 0 0 2
    a=a+1           3: ADD 0 0 250 ; 1
    a=t[i]          4: GETTABLE 0 1 2

下面这个是栈的：

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

看到最后 发现 Lua 只有 35个虚拟机指令，确实好少，对比下我大Java，都是泪啊，当然咱也没办法，两个是不同的虚拟机机制，╮(￣▽￣")╭。

a language is just a set of valid sentences

### 2013/12/27 

《Language Implementation Patterns》确实适合新手看，概念结合代码，容易消化。

按照书里提到的方法修改了之前做的一个 计算器Lexer，可读性提升很多。如果当时我直接采用书中方法，未必能体会其中好处。

简笔记录一些概念

LL(n)

- 第一个L 的意思是 ： read the input from left to right
- 第二个L 的意思是 :  descend into parse tree children from left to right
- (n) 的意思是:  表示 lookahead token 的数量， 就是往前多看n个token

####一些语法和代码之间的关系

1. (alt1 | alt2 | ... | altn)
 
   ![alt.png](imgs/implement language/alt.png "alt")
   
    同时，这种可选结构对应的程序就是 if-else, 如果是LL(1)还可以使用switch-case
    
        if ( ?lookahead-predicts-alt1 ? ) { ?match-alt1 ? }
        else if ( ?lookahead-predicts-alt2 ? ) { ?match-alt2 ? }
        ...
        else if ( ?lookahead-predicts-altN ? ) { ?match-altN ? }
        else ?throw-exception ?

2.  (T)?

    ![optional.png](imgs/implement language/optional.png "optional")
    
    程序结构也就对应为
    
        if ( <lookahead-is-T ) { match(T);}    

3.  (T)+

    ![one_or_more.png](imgs/implement language/one_or_more.png "one_or_more")
    
    这种情况下使用 do-while 循环
    
        do {
            <code-matching-alternatives>
        } while ( lookahead-predicts-an-alt-of-subrule> )

4.  (T)*

    ![zero_or_more.png](imgs/implement language/zero_or_more.png "zero_or_more")
    
    零个或多个，果断 while 循环
    
        while ( lookahead-predicts-an-alt-of-subrule> ) {
            <code-matching-alternatives>
        }
    
    

根据书中的LL(1) Lexer 的思路写了个计算器Lexer 和 Parser。

### 2013/12/28
在实现Parser的时候遇到了点坎，不知道如何组织Token，都说语法分析最后要生成树，不过总是拿不定主意到底怎么做，难道一个语法中的每个规则都生成一个类？

想不出来回头又参考了《自制编程语言》里面crowbar的实现，发现里面将不同的表达式(expression)和语句(statement) 分成了不同的类型，并没有生成很多类（虽然它是c语言实现的(-__-)b）。这么一看又勾起了我到底如何细致的区分 expression 和 statement 产生了疑问，遂google 之。

**expression** 通常会产生一个值。可以包含变量，操作符，以及方法调用。

> Mathematics a collection of symbols that jointly express a quantity : the expression for the circumference of a circle is 2πr.

**statement**

> In computer programming a statement can be thought of as the smallest standalone element of an imperative programming language. A program is formed by a sequence of one or more statements. A statement will have internal components (e.g., expressions).

statement 通常包含了 expression

找到了Java里面的定义，借鉴之

**expression** -> An expression is a construct made up of variables, operators, and method invocations, which are constructed according to the syntax of the language, that evaluates to a single value

**statements** -> Statements are roughly equivalent to sentences in natural languages. A statement forms a complete unit of execution. The following types of expressions can be made into a statement by terminating the expression with a semicolon (;). 有些表达式本身可以作为语句(statement), 这些表达式称之为 expression statements. 下面几类就是

- Assignment expressions
- Any use of ++ or --
- Method invocations
- Object creation expressions

例如

    // assignment statement
    aValue = 8933.234;
    // increment statement
    aValue++;
    // method invocation statement
    System.out.println("Hello World!");
    // object creation statement
    Bicycle myBike = new Bicycle();
    
除此之外还有两类statement: **declaration statements** and **control flow statements**

例如

    // declaration statement
    double aValue = 8933.234;
    
**block** -> A block is a group of zero or more statements between balanced braces and can be used anywhere a single statement is allowed.

好，现在对statement 总结如下：

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
                               

恩恩，思路清晰了。

如果说Lexer产生的是Token的话，那么Parser产生的就是Statement, 不知道对不对，有待验证。。。

从 《Language Implementation Pattern》书中看到，Parser是用来组织 Token之间结构或关系的

> LL(1) Recursive-Descent Parser

> This pattern analyzes the syntactic structure of the token sequence of a
phrase using a single lookahead token

这里 **1** 的意思是在当前 rule 中只看一个token，但是LL(1) 对于有歧义的语法很无力，比如

    expr: ID '++'
        | ID '--'
        
由于只看一个Token，所以在匹配expr时往前看一个Token- ID, 但是此时又两个分支可以选，要确定哪个分支需要看 ID 后面的符号才可以，所以这个时候就无法处理了。要么采用更复杂的 LL(k) 多看几个字符, 要么优化下像下面这样

    expr: ID ('++'|'--')
    
### 2013/12/30

Calc Parser 做好了，之前思路不对，总是纠结于如何生成Statement，不过现在不纠结了，直接按照文法的样子进行匹配就可以了。从这样看的话确实编译前端的内容真是没什么，看来大头在后端。

今天开始看LL(k) Parser的内容。 

LL(k) Parser 顾名思义，通过分析后面的 k 个Token来进行语法分析。

> The strength of a recursive-descent parser depends entirely on the
strength of its lookahead decisions.

不过LL(k) 也是固定的多看 k 个Token，另外有一种相似的模式 **Backtracing Pattern**, 可以往前看任意多个Token

LL(k) Parser需要在 LL(1) Parser 的基础上对 lookahead token 做调整。将原来的token改造为数组，数组长度为k。consume() 的时候使用 **%** 操作保证下标不超过 k。
然后再有歧义文法的地方进行判断，比如如果要连续判断第二个Token才能决定采用哪种逻辑

    if(LA(1) == TokenType.xxx && LA(2) == TokenType.yyy) {
        ...
    }
    

### 2014/1/1

新年第一天，同样将新年的第一行代码献给自制编程语言。这是我的一个小梦想，也是今年的第一个目标。

在实现自制编程语言的过程中我将用 《Language Implementation Patterns》 作为学习指导。现在我来到的 **Enhanced Parsing Pattern** 一部分，里面将涉及到如何实现更强大的 Parser 的内容。

主要涉及到三个 Parser：

- Backtracking Parser. 

  we use backtracking parsers only when we need to distinguish between similar language constructs.
- Memorizing Parser
- Predicated Parser


The parsers we’re working with in this book recognize context-free languages. A context-free language is a language whose constructs don’t
depend on the presence of other constructs.

#### Backtraking Parser
Syntactic predicates and speculative parsing are extremely useful when
parsing phrases that look the same from the left edge

**Backtraking Parser**: is to speculatively attempt the alternatives in order until we
find one that matches

简而言之就是不断尝试各种分支，直到找到匹配的路径

执行过程是这样：

Upon success, the parser rewinds the input and
parses the alternative normally (we’ll see why we parse it twice when
we discuss actions). Upon failing to match an alternative, the parser
rewinds the input and tries the next one. If the parser can’t find any
matching alternative, it throws a “no viable alternative” exception

#### Memoizing Parser

This pattern records partial parsing results during backtracking to
guarantee linear parsing performance, at the cost of a small amount of
memory.

简而言之，这个 Parser 主要作用就是记录已经解析过的 Token， 当后面回溯的时候避免重复解析

这个Parser没看完，(￣o￣) . z Z，看着困。打算跳过

#### Predicated Parser

This pattern augments any top-down parser with arbitrary boolean expressions that help make parsing decisions.
These boolean expressions are called semantic predicates and specify
the semantic applicability of an alternative. Predicates that evaluate to
false effectively “turn off” a parser decision path. From a grammar point
of view, false predicates make alternatives invisible.


同样，这个先跳过



### 2014/1/2

接下来直接看语言分析的部分，包括AST等

之前我们所做的工作都是检验语句语法，以及验证它们是对的。现在将更进一步学习如何分析语句的语法。

为了能够方便的识别出输入流中的操作符和操作数，我们需要构建某种中间表达式（Intermediate Representation) IR。通常采用的是 AST， 在构建AST的过程中也就识别出了输入流中的语法结构。

有些转换过程中不需要 IR， 这样的过程称为 Syntax-Directed。比如将 wiki 格式转化为 Html 格式，在转化过程中可以逐字符的翻译成Html对应编码。

和 IR tree 相关的模式：

- Parse Tree. 

    Parse trees record how a parser recognizes an input sentence. The interior nodes are rule names, and the leaves are tokens. Although parse trees are less suitable than ASTs for most language applications, parsers can create them automatically.

- Homogeneous AST.

    all the nodes have the same type, we say that they are homogeneous. With a single node type, there can be no specialty fields to reference child subtrees. Nodes track children with lists of child pointers.

- Normalized Heterogeneous AST.

    Trees with a multitude of node types are called heterogeneous trees. Normalized heterogeneous trees use a normalized list of children like homogeneous trees.

- Irregular Heterogeneous AST.

    When we refer to an AST as heterogeneous, we also assume that the nodes have irregular children. Instead of a normalized child list, the nodes have named fields, one per child.
    
####编译中的树
在开发编译器的过程中涉及到两个树，一个是 Parse Tree，另一个是 Abstract Syntax Tree

设计IR Trees 的要点：
? Dense: No unnecessary nodes
? Convenient: Easy to walk
? Meaningful: Emphasize operators, operands, and the relationship
between them rather than artifacts from the grammar

The first two points imply that it should be easy and fast to identify patterns in the tree. The last point implies that the tree structure should be insensitive to changes in the grammar (at least those unrelated to language syntax)

#### AST 如何实现操作符之间的优先级

To encode “x happens before y,” we make x lower than y in the tree. 

Operators with higher precedence appear lower in the AST. For example, in expression `3+4*5`, the * operator has higher precedence than +. The * subtree is a subtree of the + operator node. If we alter the precedence of the operators in the expression with parentheses, `(3+4)*5`, the + subtree becomes a child of the * node

![ast_opt_precendce.png](imgs/implement language/ast_opt_precendce.png "ast_opt_precendence")


#### Create AST for nonexecutable language statements

**Representing Pseudo-operations in ASTs**

有一些表达式并没有明显的操作符可以用来构建一颗树，这个时候可以使用一个虚拟token (imaginary token) 来充当AST的root.

通常 function declarations, class declarations, formal argument declarations 都需要这种处理手法

**Enforcing Tree Structure with the Type System**

在AST中引入类型系统来保证类型的正确性

### 2014/1/4 

按照书中的内容，到现在为止，因为已经了解了如何手写Lexer 和 Parser的话，那么就可以考虑使用自动生成Lexer 和 Parser的工具了。

ANTLR4 年初的时候研究过一段时间，但是后来被别的事冲掉了。现在只能重新学一遍，recall 一下，果然学东西应该一次性学完，不然成本相当高( ▼-▼ )

由于《Language Implementation Patterns》 采用的是Antlr3，所以看到 **Constructing ASTs with ANTLR Grammars** 的时候里面用Antlr3描述如何构建AST，心想难道又要断档了？又要重学Antlr3？因为Antlr4和有很多不同，4可以采用visitor和listener对grammar中需要的操作进行响应，保证了语法文件的干净。所以如果要回到3，就有点太那个了。。。

不过还好，看到书后面作者又回到正规，采用正常的Java代码实现AST，所以顿时松了口气。

总结下之前提到的IR，在构造IR的时候我们有两种选择：Parse Tree 和 AST Tree。而构造AST又有三种方法：Homogeneous(同质的) AST， Normalized Heterogeneous AST ， Irregular Heterogeneous(异质的，合成的) AST。这三者的侧重点都不同，所以需要根据需要进行选择

**IR**

- Parser Trees

        Pros: Parser generators can automatically build these for us. 
        Cons: Parse trees are full of noise (unnecessary nodes). They are sensitive to changes in  the grammar unrelated to syntax.
- AST Trees
    - Homogeneous AST
        
            Pros: Homogeneous trees are very simple.     
            Cons: It’s cumbersome to annotate AST nodes because the single node type has the union of all needed fields. There is no way to add methods specific to a particular kind of node.
            
      简而言之，Homogeneous AST 的结点将所有的类型混合到一起，这意味着所有的实质上不同的节点拥有相同的结构模型，所以也就无法对特别的节点施加独特的操作。
    - Normalized Heterogeneous AST
    
            Pros: It’s easy to add operator or operand-specific data and methods. 
            Cons: Large grammars like Java’s need about 200 class definitions to be fully heterogeneous.
            
        Normalized Heterogeneous AST 相当于添加了类型层次，对于不同的类型都为其建立对应的class，这样的话如果语法复杂，那么class将会急剧增长。
    - Irregular Heterogeneous AST

            Pros: It’s easy to add operator- or operand-specific data and methods. Building tree-walking methods for a small set of heterogeneous nodes is quick and easy.
            Cons: As with Normalized Heterogeneous AST, there are lots of AST classes to read and write. Having irregular children makes building external visitors difficult.
            
如果更关注树的结构那么选 Homogeneous AST 就可以了，如果需要区分结点类型，那么使用 Normalized Heterogeneous AST 就行。对于 Parse Tree，由于噪音字符比较多，所以不太采用。

####Parse Tree
Parse trees record the sequence of rules a parser applies as well as the tokens it matches. Parse trees describe sentence structure by grouping input symbols into subtrees.

But parse trees are extremely inconvenient to walk and transform. Parse trees are full of noise because of all the interior rule nodes. 

Nonetheless, parse trees are still very useful as intermediate representations for some tools and applications. For example, development environments use parse trees to good effect for syntax highlighting and error-checking. 尽管前面说了这么多Parser 不好的地方，但是Parse Tree在 语法高亮和 错误检查等方面上市可以物尽其用的。因为我们可以对语句中的一部分内容进行操作。

####AST
The key idea behind an AST is the operator-operand tree structure, not the node data type. (AST 关注操作符和操作数) An AST contains the essence of the input token stream and the relationships between operator and operand tokens. We don’t need to use the type system of our implementation language to distinguish between nodes. Nodes in any AST derive from tokens,
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



###2014/1/7
下面要开始看对 AST 的遍历了，在遍历过程中可能会对树中的节点进行修改和更新，例如进行常量折叠等
#### Tree walking

这里的树遍历可能要难于在数据结构课上学到的遍历方法，因为这里的树的状态不可能像数据结构课里那样单纯简单，它可能因为以下因素而不同

- whether we have the source codefor our tree nodes,
- whether the trees have normalized children, 
- whether the trees are homogeneous or heterogeneous, 
- whether we need to rewrite trees while walking, and 
- even in which order we need to walk the nodes.

这里需要学习的处理模式有

- Embedded Heterogeneous Tree Walker. 

    Heterogeneous AST node classes define walking methods that execute appropriate actions and walk any children. Tree walking code is distributed across potentially hundreds of class files.
    
    也就是将遍历树中节点时处理逻辑写在各节点里
- External Tree Visitor.

    This pattern encapsulates tree walking code (for both homogeneous and heterogeneous ASTs) into a single class definition. It allows us to alter tree-walking behavior without altering AST node definitions. Both WALKING TREES AND VISITATION ORDER 117 the visitor and embedded walker pattern are straightforward but tedious to implement manually.
    
    使用外部visitor 遍历Tree
- Tree Grammar.

    A tree grammar describes the structure of valid ASTs. Just as we can automate parser construction from grammars, we can automate tree visitor construction from tree grammars. Tree grammars work on homogeneous or heterogeneous trees because they rely on node token types rather than node types (like AddNode). Tree grammars explicitly dictate node visitation order like the embedded walker and visitor patterns.
- Tree Pattern Matcher.

    Instead of specifying an entire tree grammar, this pattern lets us focus on just those subtrees we care about. That’s useful because different phases of an application care about different parts of the tree. A tree pattern matcher also decouples the order in which we apply tree patterns from the tree patterns themselves.
    
    用了Pattern就表示我们可能只关注全集中的一部分情况, 当然如果Pattern足够宽泛的话是能涵盖全集的
    
#### Walking Trees and Visitation Order

**Visit**

visiting a tree, we mean executing some actions on the nodes of a tree.  The order in which we traverse the nodes is important because that affects the order in which we execute the actions. 

下面来回顾一下树遍历的方法，对于表达式 1 + 2, 根据遍历方法的不同可以产生不同的序列

- 前序遍历 (+ 1 2)，先访问父节点再访问子节点
- 中序遍历 (1 + 2)，先访问子节点然后父节点，最后是剩下右子节点
- 后续遍历 (1 2 +)，先遍历完子节点，然后才是访问父节点

上面的遍历顺序都可以使用 **深度优先遍历(depth-first)** 实现

**Walk**

**Visiting a node means to execute an action somewhere between discovering and finishing that node.** But, that same discovery sequence can generate three different tree traversals (node visitation sequences). It all depends on where we put actions in walk( ).

**Difference between Tree Walking and Visiting**