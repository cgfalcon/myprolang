grammar VectorMath;

statlist: stat+;
stat: ID '=' expr
    | 'print' expr
    ;

exprt returns [AST tr]
    :  a= multiExpr {$tr = $a.tr;}
        ( '+' b = multiExpr {$tr = new AddNode($tr, $b.tr);} )*
    ;