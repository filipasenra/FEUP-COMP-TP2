/* Generated By:JJTree&JavaCC: Do not edit this line. Jmm.java */
public class Jmm/*@bgen(jjtree)*/implements JmmTreeConstants, JmmConstants {/*@bgen(jjtree)*/
  protected static JJTJmmState jjtree = new JJTJmmState();public static void main(String args[]) throws ParseException {

        System.out.println("Write an Java-- expression:");
        Jmm myParser = new Jmm(System.in);
        SimpleNode root = myParser.ParseExpression(); // returns reference to root node 

        root.dump(""); // prints the tree on the screen

        System.out.println("Finished Parsing");

        }

  static final public SimpleNode ParseExpression() throws ParseException {
                               /*@bgen(jjtree) ParseExpression */
  SimpleNode jjtn000 = new SimpleNode(JJTPARSEEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      ClassDeclaration();
      jj_consume_token(0);
                                jjtree.closeNodeScope(jjtn000, true);
                                jjtc000 = false;
                               {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public void ClassDeclaration() throws ParseException {
    jj_consume_token(CLASS);
    jj_consume_token(IDENTIFIER);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXTENDS:
      jj_consume_token(EXTENDS);
      jj_consume_token(IDENTIFIER);
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    jj_consume_token(LEFT_BRACE);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
      case BOOLEAN:
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      VarDeclaration();
    }
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PUBLIC:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      MethodDeclaration();
    }
    jj_consume_token(RIGHT_BRACE);
  }

  static final public void VarDeclaration() throws ParseException {
                        /*@bgen(jjtree) VarDeclaration */
  SimpleNode jjtn000 = new SimpleNode(JJTVARDECLARATION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      Type();
      jj_consume_token(IDENTIFIER);
      jj_consume_token(SEMICOLON);
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  static final public void Type() throws ParseException {
              /*@bgen(jjtree) Type */
  SimpleNode jjtn000 = new SimpleNode(JJTTYPE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
        jj_consume_token(INT);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LEFT_BRACKET:
          jj_consume_token(LEFT_BRACKET);
          jj_consume_token(RIGHT_BRACKET);
          break;
        default:
          jj_la1[3] = jj_gen;
          ;
        }
        break;
      case BOOLEAN:
        jj_consume_token(BOOLEAN);
        break;
      case IDENTIFIER:
        jj_consume_token(IDENTIFIER);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static final public void MethodDeclaration() throws ParseException {
                           /*@bgen(jjtree) MethodDeclaration */
  SimpleNode jjtn000 = new SimpleNode(JJTMETHODDECLARATION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PUBLIC);
      Type();
      jj_consume_token(IDENTIFIER);
      jj_consume_token(LEFT_PARENTESIS);
      Type();
      jj_consume_token(IDENTIFIER);
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COLON:
          ;
          break;
        default:
          jj_la1[5] = jj_gen;
          break label_3;
        }
        jj_consume_token(COLON);
        Type();
        jj_consume_token(IDENTIFIER);
      }
      jj_consume_token(RIGHT_PARENTESIS);
      jj_consume_token(LEFT_BRACE);
      label_4:
      while (true) {
        if (jj_2_1(2)) {
          ;
        } else {
          break label_4;
        }
        VarDeclaration();
      }
      label_5:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IF:
        case WHILE:
        case TRUE_:
        case FALSE_:
        case THIS:
        case NEW:
        case INTEGER_LITERAL:
        case IDENTIFIER:
        case LEFT_BRACKET:
        case LEFT_PARENTESIS:
        case NEGATION:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_5;
        }
        Statement();
      }
      jj_consume_token(RETURN);
      Expression();
      jj_consume_token(SEMICOLON);
      jj_consume_token(RIGHT_BRACE);
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  static final public void Statement() throws ParseException {
                   /*@bgen(jjtree) Statement */
  SimpleNode jjtn000 = new SimpleNode(JJTSTATEMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LEFT_BRACKET:
        jj_consume_token(LEFT_BRACKET);
        label_6:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case IF:
          case WHILE:
          case TRUE_:
          case FALSE_:
          case THIS:
          case NEW:
          case INTEGER_LITERAL:
          case IDENTIFIER:
          case LEFT_BRACKET:
          case LEFT_PARENTESIS:
          case NEGATION:
            ;
            break;
          default:
            jj_la1[7] = jj_gen;
            break label_6;
          }
          Statement();
        }
        jj_consume_token(RIGHT_BRACKET);
        break;
      case IF:
        jj_consume_token(IF);
        jj_consume_token(LEFT_PARENTESIS);
        Expression();
        jj_consume_token(RIGHT_PARENTESIS);
        Statement();
        jj_consume_token(ELSE);
        Statement();
        break;
      case WHILE:
        jj_consume_token(WHILE);
        jj_consume_token(LEFT_PARENTESIS);
        Expression();
        jj_consume_token(RIGHT_PARENTESIS);
        Statement();
        break;
      case TRUE_:
      case FALSE_:
      case THIS:
      case NEW:
      case INTEGER_LITERAL:
      case IDENTIFIER:
      case LEFT_PARENTESIS:
      case NEGATION:
        if (jj_2_2(2)) {
          Expression();
          jj_consume_token(SEMICOLON);
        } else {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case IDENTIFIER:
            jj_consume_token(IDENTIFIER);
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case LEFT_BRACKET:
              jj_consume_token(LEFT_BRACKET);
              Expression();
              jj_consume_token(RIGHT_BRACKET);
              break;
            default:
              jj_la1[8] = jj_gen;
              ;
            }
            jj_consume_token(ASSIGN);
            Expression();
            jj_consume_token(SEMICOLON);
            break;
          default:
            jj_la1[9] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  static final public void Expression() throws ParseException {
    AND();
  }

  static final public void AND() throws ParseException {
    LessThan();
    label_7:
    while (true) {
      if (jj_2_3(2)) {
        ;
      } else {
        break label_7;
      }
      jj_consume_token(AND);
                                      SimpleNode jjtn001 = new SimpleNode(JJTAND);
                                      boolean jjtc001 = true;
                                      jjtree.openNodeScope(jjtn001);
      try {
        LessThan();
      } catch (Throwable jjte001) {
                                      if (jjtc001) {
                                        jjtree.clearNodeScope(jjtn001);
                                        jjtc001 = false;
                                      } else {
                                        jjtree.popNode();
                                      }
                                      if (jjte001 instanceof RuntimeException) {
                                        {if (true) throw (RuntimeException)jjte001;}
                                      }
                                      if (jjte001 instanceof ParseException) {
                                        {if (true) throw (ParseException)jjte001;}
                                      }
                                      {if (true) throw (Error)jjte001;}
      } finally {
                                      if (jjtc001) {
                                        jjtree.closeNodeScope(jjtn001,  2);
                                      }
      }
    }
  }

  static final public void LessThan() throws ParseException {
    Sum();
    label_8:
    while (true) {
      if (jj_2_4(2)) {
        ;
      } else {
        break label_8;
      }
      jj_consume_token(LESS_THAN);
                                       SimpleNode jjtn001 = new SimpleNode(JJTLESSTHAN);
                                       boolean jjtc001 = true;
                                       jjtree.openNodeScope(jjtn001);
      try {
        Sum();
      } catch (Throwable jjte001) {
                                       if (jjtc001) {
                                         jjtree.clearNodeScope(jjtn001);
                                         jjtc001 = false;
                                       } else {
                                         jjtree.popNode();
                                       }
                                       if (jjte001 instanceof RuntimeException) {
                                         {if (true) throw (RuntimeException)jjte001;}
                                       }
                                       if (jjte001 instanceof ParseException) {
                                         {if (true) throw (ParseException)jjte001;}
                                       }
                                       {if (true) throw (Error)jjte001;}
      } finally {
                                       if (jjtc001) {
                                         jjtree.closeNodeScope(jjtn001,  2);
                                       }
      }
    }
  }

  static final public void Sum() throws ParseException {
    Mul();
    label_9:
    while (true) {
      if (jj_2_5(2)) {
        ;
      } else {
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SUM:
        jj_consume_token(SUM);
        break;
      case SUB:
        jj_consume_token(SUB);
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                                           SimpleNode jjtn001 = new SimpleNode(JJTSUM);
                                           boolean jjtc001 = true;
                                           jjtree.openNodeScope(jjtn001);
      try {
        Mul();
      } catch (Throwable jjte001) {
                                           if (jjtc001) {
                                             jjtree.clearNodeScope(jjtn001);
                                             jjtc001 = false;
                                           } else {
                                             jjtree.popNode();
                                           }
                                           if (jjte001 instanceof RuntimeException) {
                                             {if (true) throw (RuntimeException)jjte001;}
                                           }
                                           if (jjte001 instanceof ParseException) {
                                             {if (true) throw (ParseException)jjte001;}
                                           }
                                           {if (true) throw (Error)jjte001;}
      } finally {
                                           if (jjtc001) {
                                             jjtree.closeNodeScope(jjtn001,  2);
                                           }
      }
    }
  }

  static final public void Mul() throws ParseException {
    DotExpression();
    label_10:
    while (true) {
      if (jj_2_6(2)) {
        ;
      } else {
        break label_10;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MUL:
        jj_consume_token(MUL);
        break;
      case DIV:
        jj_consume_token(DIV);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                                                     SimpleNode jjtn001 = new SimpleNode(JJTMUL);
                                                     boolean jjtc001 = true;
                                                     jjtree.openNodeScope(jjtn001);
      try {
        DotExpression();
      } catch (Throwable jjte001) {
                                                     if (jjtc001) {
                                                       jjtree.clearNodeScope(jjtn001);
                                                       jjtc001 = false;
                                                     } else {
                                                       jjtree.popNode();
                                                     }
                                                     if (jjte001 instanceof RuntimeException) {
                                                       {if (true) throw (RuntimeException)jjte001;}
                                                     }
                                                     if (jjte001 instanceof ParseException) {
                                                       {if (true) throw (ParseException)jjte001;}
                                                     }
                                                     {if (true) throw (Error)jjte001;}
      } finally {
                                                     if (jjtc001) {
                                                       jjtree.closeNodeScope(jjtn001,  2);
                                                     }
      }
    }
  }

  static final public void DotExpression() throws ParseException {
    RestOfExpression();
  }

  static final public void RestOfExpression() throws ParseException {
                           /*@bgen(jjtree) RestOfExpression */
  SimpleNode jjtn000 = new SimpleNode(JJTRESTOFEXPRESSION);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INTEGER_LITERAL:
        jj_consume_token(INTEGER_LITERAL);
        break;
      case TRUE_:
        jj_consume_token(TRUE_);
        break;
      case FALSE_:
        jj_consume_token(FALSE_);
        break;
      case NEW:
        jj_consume_token(NEW);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INT:
          jj_consume_token(INT);
          jj_consume_token(LEFT_BRACKET);
          Expression();
          jj_consume_token(RIGHT_BRACKET);
          break;
        case IDENTIFIER:
          jj_consume_token(IDENTIFIER);
          jj_consume_token(LEFT_PARENTESIS);
          jj_consume_token(RIGHT_PARENTESIS);
          break;
        default:
          jj_la1[13] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      case NEGATION:
        jj_consume_token(NEGATION);
        Expression();
        break;
      case LEFT_PARENTESIS:
        jj_consume_token(LEFT_PARENTESIS);
        Expression();
        jj_consume_token(RIGHT_PARENTESIS);
        break;
      case THIS:
      case IDENTIFIER:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IDENTIFIER:
          jj_consume_token(IDENTIFIER);
          break;
        case THIS:
          jj_consume_token(THIS);
          break;
        default:
          jj_la1[14] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case DOT:
          jj_consume_token(DOT);
          jj_consume_token(IDENTIFIER);
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case LEFT_PARENTESIS:
            jj_consume_token(LEFT_PARENTESIS);
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case TRUE_:
            case FALSE_:
            case THIS:
            case NEW:
            case INTEGER_LITERAL:
            case IDENTIFIER:
            case LEFT_PARENTESIS:
            case NEGATION:
              Expression();
              label_11:
              while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case COLON:
                  ;
                  break;
                default:
                  jj_la1[15] = jj_gen;
                  break label_11;
                }
                jj_consume_token(COLON);
                Expression();
              }
              break;
            default:
              jj_la1[16] = jj_gen;
              ;
            }
            jj_consume_token(RIGHT_PARENTESIS);
            break;
          default:
            jj_la1[17] = jj_gen;
            ;
          }
          break;
        default:
          jj_la1[18] = jj_gen;
          ;
        }
        break;
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
      if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
      if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_2_5(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  static private boolean jj_2_6(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_6(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  static private boolean jj_3R_17() {
    if (jj_3R_20()) return true;
    return false;
  }

  static private boolean jj_3_3() {
    if (jj_scan_token(AND)) return true;
    if (jj_3R_14()) return true;
    return false;
  }

  static private boolean jj_3R_16() {
    if (jj_3R_17()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_6()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static private boolean jj_3_4() {
    if (jj_scan_token(LESS_THAN)) return true;
    if (jj_3R_15()) return true;
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_3R_12()) return true;
    return false;
  }

  static private boolean jj_3R_29() {
    if (jj_scan_token(DOT)) return true;
    return false;
  }

  static private boolean jj_3R_15() {
    if (jj_3R_16()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_5()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static private boolean jj_3R_14() {
    if (jj_3R_15()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_4()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static private boolean jj_3R_26() {
    if (jj_scan_token(LEFT_BRACKET)) return true;
    return false;
  }

  static private boolean jj_3R_19() {
    if (jj_3R_14()) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_3()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  static private boolean jj_3R_28() {
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3R_18() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_21()) {
    jj_scanpos = xsp;
    if (jj_scan_token(15)) {
    jj_scanpos = xsp;
    if (jj_scan_token(29)) return true;
    }
    }
    return false;
  }

  static private boolean jj_3R_21() {
    if (jj_scan_token(INT)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_26()) jj_scanpos = xsp;
    return false;
  }

  static private boolean jj_3R_13() {
    if (jj_3R_19()) return true;
    return false;
  }

  static private boolean jj_3R_12() {
    if (jj_3R_18()) return true;
    if (jj_scan_token(IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3R_27() {
    if (jj_scan_token(INT)) return true;
    return false;
  }

  static private boolean jj_3_2() {
    if (jj_3R_13()) return true;
    if (jj_scan_token(SEMICOLON)) return true;
    return false;
  }

  static private boolean jj_3R_25() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(29)) {
    jj_scanpos = xsp;
    if (jj_scan_token(22)) return true;
    }
    xsp = jj_scanpos;
    if (jj_3R_29()) jj_scanpos = xsp;
    return false;
  }

  static private boolean jj_3_6() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(43)) {
    jj_scanpos = xsp;
    if (jj_scan_token(44)) return true;
    }
    if (jj_3R_17()) return true;
    return false;
  }

  static private boolean jj_3R_24() {
    if (jj_scan_token(LEFT_PARENTESIS)) return true;
    if (jj_3R_13()) return true;
    return false;
  }

  static private boolean jj_3R_23() {
    if (jj_scan_token(NEGATION)) return true;
    if (jj_3R_13()) return true;
    return false;
  }

  static private boolean jj_3R_22() {
    if (jj_scan_token(NEW)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_27()) {
    jj_scanpos = xsp;
    if (jj_3R_28()) return true;
    }
    return false;
  }

  static private boolean jj_3R_20() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(24)) {
    jj_scanpos = xsp;
    if (jj_scan_token(20)) {
    jj_scanpos = xsp;
    if (jj_scan_token(21)) {
    jj_scanpos = xsp;
    if (jj_3R_22()) {
    jj_scanpos = xsp;
    if (jj_3R_23()) {
    jj_scanpos = xsp;
    if (jj_3R_24()) {
    jj_scanpos = xsp;
    if (jj_3R_25()) return true;
    }
    }
    }
    }
    }
    }
    return false;
  }

  static private boolean jj_3_5() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(41)) {
    jj_scanpos = xsp;
    if (jj_scan_token(42)) return true;
    }
    if (jj_3R_16()) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public JmmTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[20];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x80,0x2000c000,0x100,0x0,0x2000c000,0x0,0x21f50000,0x21f50000,0x0,0x20000000,0x21f50000,0x0,0x0,0x20004000,0x20400000,0x0,0x21f00000,0x0,0x0,0x21f00000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x10,0x0,0x2,0x8050,0x8050,0x10,0x0,0x8050,0x600,0x1800,0x0,0x0,0x2,0x8040,0x40,0x100,0x8040,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[6];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Jmm(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Jmm(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new JmmTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Jmm(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new JmmTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Jmm(JmmTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(JmmTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 20; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        exists = true;
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.add(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[49];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 20; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 49; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 6; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
            case 4: jj_3_5(); break;
            case 5: jj_3_6(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

    }
