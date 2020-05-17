.class public TicTacToe
.super java/lang/Object
.field public row0 [I
.field public row1 [I
.field public row2 [I
.field public whoseturn I
.field public movesmade I
.field public pieces [I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public init()B
	.limit stack 99
	.limit locals 99

	iconst_3
	newarray int
	astore_0
	iconst_3
	newarray int
	astore_0
	iconst_3
	newarray int
	astore_0
	iconst_2
	newarray int
	astore_0
	aload_0
	iconst_0
	iconst_1
	iastore
	aload_0
	iconst_1
	iconst_2
	iastore
	iconst_0
	astore_0
	iconst_0
	astore_0
	ireturn
.end method

.method public getRow0()[I
	.limit stack 99
	.limit locals 99

	aload_0
	areturn
.end method

.method public getRow1()[I
	.limit stack 99
	.limit locals 99

	aload_0
	areturn
.end method

.method public getRow2()[I
	.limit stack 99
	.limit locals 99

	aload_0
	areturn
.end method

.method public MoveRow([II)B
	.limit stack 99
	.limit locals 99

	iload_2
	iconst_0
	if_icmpge if1_else
	istore_3
	goto if1_end
if1_else:
	iconst_0
	aload_1
	if_icmpge if2_else
	istore_3
	goto if2_end
if2_else:
	aload_1
	aload_0
	iastore
	aload_0
	iconst_1
	iadd
	astore_0
	istore_3
if2_end:
if1_end:
	iload_3
	ireturn
.end method

.method public Move(II)B
	.limit stack 99
	.limit locals 99

	aload_0
	istore_3
	goto if3_end
if3_else:
	aload_0
	istore_3
	goto if4_end
if4_else:
	istore_3
if4_end:
if3_end:
	iload_3
	ireturn
.end method

.method public inbounds(II)B
	.limit stack 99
	.limit locals 99

	iload_1
	iconst_0
	if_icmpge if5_else
	goto if5_end
if5_else:
	istore_3
	iconst_2
	iload_1
	if_icmpge if6_else
	goto if6_end
if6_else:
	istore_3
	istore_3
if6_end:
if5_end:
	iload_3
	ireturn
.end method

.method public changeturn()B
	.limit stack 99
	.limit locals 99

	iconst_1
	aload_0
	isub
	astore_0
	ireturn
.end method

.method public getCurrentPlayer()I
	.limit stack 99
	.limit locals 99

	aload_0
	iconst_1
	iadd
	ireturn
.end method

.method public winner()I
	.limit stack 99
	.limit locals 99

	iconst_0
	iconst_1
	isub
	istore_2
	iconst_3
	newarray int
	astore_1
	aload_0
	istore_2
	goto if7_end
if7_else:
	aload_0
	istore_2
	goto if8_end
if8_else:
	iconst_0
	istore_3
	iload_2
	iconst_1
	if_icmpge if9_else
	aload_1
	iconst_0
	aload_0
	iastore
	aload_1
	iconst_1
	aload_0
	iastore
	aload_1
	iconst_2
	aload_0
	iastore
	aload_1
	istore_2
	goto if10_end
if10_else:
	aload_1
	iconst_0
	aload_0
	iastore
	aload_1
	iconst_1
	aload_0
	iastore
	aload_1
	iconst_2
	aload_0
	iastore
	aload_1
	istore_2
	goto if11_end
if11_else:
if11_end:
if10_end:
	goto if9_end
if9_else:
if9_end:
if8_end:
if7_end:
	goto if12_end
if12_else:
if12_end:
	iload_2
	ireturn
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 99

	new TicTacToe
	dup
	invokespecial TicTacToe/<init>()V
	astore_1
	invokevirtual TicTacToe/init()B
	invokestatic BoardBase/printBoard()
	aload_1
	invokevirtual TicTacToe/winner()I
	istore_2
	iload_2
	invokestatic BoardBase/printWinner(I)V
	return
.end method


