/* Generated By:JJTree: Do not edit this line. ASTArg.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTArg extends SimpleNode {

  public String val;

  public ASTArg(int id) {
    super(id);
  }

  public ASTArg(ParserAST p, int id) {
    super(p, id);
  }

  @Override
  public String toString(String prefix) { return super.toString(prefix) + ": " + this.val; }

}
/* JavaCC - OriginalChecksum=295bcd65a40c7fac15747ce41a2b3eba (do not edit this line) */
