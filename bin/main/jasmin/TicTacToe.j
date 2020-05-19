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
	if_icmpge if_1_else
	istore_3
	goto if_1_end
if_1_else:
	iconst_2
	iload_2
	if_icmpge if_2_else
	istore_3
	goto if_2_end
if_2_else:
	iconst_0
	aload_1
	if_icmpge if_3_else
	istore_3
	goto if_3_end
if_3_else:
	aload_1
	aload_0
	iastore
	aload_0
	iconst_1
	iadd
	astore_0
	istore_3
if_3_end:
if_2_end:
if_1_end:
	iload_3
	ireturn
.end method

.method public Move(II)B
	.limit stack 99
	.limit locals 99

	aload0
	aload_0
	iload_2
	invokevirtual TicTacToe/MoveRow(I)
	istore_3
	goto if_4_end
if_4_else:
	aload0
	aload_0
	iload_2
	invokevirtual TicTacToe/MoveRow(I)
	istore_3
	goto if_5_end
if_5_else:
	aload0
	aload_0
	iload_2
	invokevirtual TicTacToe/MoveRow(I)
	istore_3
	goto if_6_end
if_6_else:
	istore_3
if_6_end:
if_5_end:
if_4_end:
	iload_3
	ireturn
.end method

.method public inbounds(II)B
	.limit stack 99
	.limit locals 99

	iload_1
	iconst_0
	if_icmpge if_7_else
	istore_3
	goto if_7_end
if_7_else:
	iload_2
	iconst_0
	if_icmpge if_8_else
	istore_3
	goto if_8_end
if_8_else:
	iconst_2
	iload_1
	if_icmpge if_9_else
	istore_3
	goto if_9_end
if_9_else:
	iconst_2
	iload_2
	if_icmpge if_10_else
	istore_3
	goto if_10_end
if_10_else:
	istore_3
if_10_end:
if_9_end:
if_8_end:
if_7_end:
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
	goto if_11_end
if_11_else:
	aload_0
	istore_2
	goto if_12_end
if_12_else:
	aload_0
	istore_2
	goto if_13_end
if_13_else:
	iconst_0
	istore_3
while_14_begin:
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
	goto if_15_end
if_15_else:
if_15_end:
	iload_3
	iconst_1
	iadd
	istore_3
	goto while_14_begin
while_14_end:
	iload_2
	iconst_1
	if_icmpge if_16_else
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
	goto if_17_end
if_17_else:
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
	goto if_18_end
if_18_else:
if_18_end:
if_17_end:
	goto if_16_end
if_16_else:
if_16_end:
if_13_end:
if_12_end:
if_11_end:
	iconst_0
	istore_2
	goto if_19_end
if_19_else:
if_19_end:
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
	aload_1
	invokevirtual TicTacToe/init()B
while_20_begin:
	istore_3
while_21_begin:
	aload_1
	invokevirtual TicTacToe/getRow0()[I
	aload_1
	invokevirtual TicTacToe/getRow1()[I
	aload_1
	invokevirtual TicTacToe/getRow2()[I
	invokestatic TicTacToe/printBoard([I[I[I)V
	aload_1
	invokevirtual TicTacToe/getCurrentPlayer()I
	istore 5
	iload 5
	invokestatic TicTacToe/playerTurn(I)[I
	astore 4
	invokestatic TicTacToe/wrongMove()V
	goto if_22_end
if_22_else:
	invokestatic TicTacToe/placeTaken()V
	goto if_23_end
if_23_else:
	istore_3
if_23_end:
if_22_end:
	goto while_21_begin
while_21_end:
	aload_1
	invokevirtual TicTacToe/changeturn()B
	goto while_20_begin
while_20_end:
	aload_1
	invokevirtual TicTacToe/getRow0()[I
	aload_1
	invokevirtual TicTacToe/getRow1()[I
	aload_1
	invokevirtual TicTacToe/getRow2()[I
	invokestatic TicTacToe/printBoard([I[I[I)V
	aload_1
	invokevirtual TicTacToe/winner()I
	istore_2
	iload_2
	invokestatic TicTacToe/printWinner(I)V
	return
.end method


