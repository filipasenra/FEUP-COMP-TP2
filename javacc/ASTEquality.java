/* Generated By:JJTree: Do not edit this line. ASTEquality.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTEquality extends SimpleNode {

  public String name;
  public boolean isArray = false;

  public ASTEquality(int id) {
    super(id);
  }

  public ASTEquality(Jmm p, int id) {
    super(p, id);
  }

  @Override
  public String toString(String prefix) {
    String isArrayString = (this.isArray) ? "is an array" : "";
    return super.toString(prefix) + ": " + this.name +  isArrayString; }

}
/* JavaCC - OriginalChecksum=18bf3b796762994a872ed19e1067c294 (do not edit this line) */