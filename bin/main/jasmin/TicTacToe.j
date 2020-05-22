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
	.limit stack 10
	.limit locals 1

	iconst_3
	newarray int
	aload_0
	putfield row0:[I

	iconst_3
	newarray int
	aload_0
	putfield row1:[I

	iconst_3
	newarray int
	aload_0
	putfield row2:[I

	iconst_2
	newarray int
	aload_0
	putfield pieces:[I

	aload_0
	getfield pieces:[I
	iconst_0
	iconst_1
	iastore

	aload_0
	getfield pieces:[I
	iconst_1
	iconst_2
	iastore

	iconst_0
	aload_0
	putfield whoseturn:I

	iconst_0
	aload_0
	putfield movesmade:I

	iconst_1
	ireturn
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
.end method


.method public getRow0()[I
	.limit stack 0
	.limit locals 1

	aload_0
	getfield row0:[I
	areturn
.end method


.method public getRow1()[I
	.limit stack 0
	.limit locals 1

	aload_0
	getfield row1:[I
	areturn
.end method


.method public getRow2()[I
	.limit stack 0
	.limit locals 1

	aload_0
	getfield row2:[I
	areturn
.end method


.method public MoveRow([II)B
	.limit stack 5
	.limit locals 4

	iload_2
	iconst_0
	if_icmpge if_1_else
	iconst_0
	istore_3
	goto if_1_end
if_1_else:
	iconst_2
	iload_2
	if_icmpge if_2_else
	iconst_0
	istore_3
	goto if_2_end
if_2_else:
	iconst_0
	aload_1
	iaload
	if_icmpge if_3_else
	iconst_0
	istore_3
	goto if_3_end
if_3_else:
	aload_1
	aload_0
	getfield pieces:[I
	iaload
	iastore

	aload_0
	getfield movesmade:I
	iconst_1
	iadd
	aload_0
	putfield movesmade:I

	iconst_1
	istore_3
if_3_end:
if_2_end:
if_1_end:
	iload_3
	ireturn
	pop
	pop
	pop
	pop
.end method


.method public Move(II)B
	.limit stack 5
	.limit locals 4

	iload_1
	iconst_0
	if_icmpge lessThan_6
	iconst_1
	goto lessThan_6_end
lessThan_6:
	iconst_0
lessThan_6_end:
	if_eq AND_5
	iconst_0
	iload_1
	if_icmpge lessThan_8
	iconst_1
	goto lessThan_8_end
lessThan_8:
	iconst_0
lessThan_8_end:
	ifne negation_7
	iconst_1
	goto negation_7_end
negation_7:
	iconst_0
negation_7_end:
	if_eq AND_5
	iconst_1
	goto AND_5_end
AND_5:
	iconst_0
AND_5_end:
	ifne if_4_else
	iload_3
	ireturn
	pop
	pop
	pop
	pop
	pop
.end method


.method public inbounds(II)B
	.limit stack 5
	.limit locals 4

	iload_1
	iconst_0
	if_icmpge if_9_else
	iconst_0
	istore_3
	goto if_9_end
if_9_else:
	iload_2
	iconst_0
	if_icmpge if_10_else
	iconst_0
	istore_3
	goto if_10_end
if_10_else:
	iconst_2
	iload_1
	if_icmpge if_11_else
	iconst_0
	istore_3
	goto if_11_end
if_11_else:
	iconst_2
	iload_2
	if_icmpge if_12_else
	iconst_0
	istore_3
	goto if_12_end
if_12_else:
	iconst_1
	istore_3
if_12_end:
if_11_end:
if_10_end:
if_9_end:
	iload_3
	ireturn
	pop
	pop
	pop
	pop
.end method


.method public changeturn()B
	.limit stack 1
	.limit locals 1

	iconst_1
	aload_0
	getfield whoseturn:I
	isub
	aload_0
	putfield whoseturn:I

	iconst_1
	ireturn
.end method


.method public getCurrentPlayer()I
	.limit stack 1
	.limit locals 1

	aload_0
	getfield whoseturn:I
	iconst_1
	iadd
	ireturn
.end method


.method public winner()I
	.limit stack 50
	.limit locals 4

	iconst_0
	iconst_1
	isub
	istore_2
	iconst_3
	newarray int
	astore_1
	aload_0
	getfield row0:[I
	invokestatic BoardBase/sameArray([I)B
	if_eq if_13_else
	iconst_0
	aload_0
	getfield row0:[I
	iconst_0
	iaload
	if_icmpge lessThan_14
	iconst_1
	goto lessThan_14_end
lessThan_14:
	iconst_0
lessThan_14_end:
	if_eq if_13_else	aload_0
	getfield row0:[I
	iconst_0
	iaload
	istore_2
	goto if_13_end
if_13_else:
	aload_0
	getfield row1:[I
	invokestatic BoardBase/sameArray([I)B
	if_eq if_15_else
	iconst_0
	aload_0
	getfield row1:[I
	iconst_0
	iaload
	if_icmpge lessThan_16
	iconst_1
	goto lessThan_16_end
lessThan_16:
	iconst_0
lessThan_16_end:
	if_eq if_15_else	aload_0
	getfield row1:[I
	iconst_0
	iaload
	istore_2
	goto if_15_end
if_15_else:
	aload_0
	getfield row2:[I
	invokestatic BoardBase/sameArray([I)B
	if_eq if_17_else
	iconst_0
	aload_0
	getfield row2:[I
	iconst_0
	iaload
	if_icmpge lessThan_18
	iconst_1
	goto lessThan_18_end
lessThan_18:
	iconst_0
lessThan_18_end:
	if_eq if_17_else	aload_0
	getfield row2:[I
	iconst_0
	iaload
	istore_2
	goto if_17_end
if_17_else:
	iconst_0
	istore_3
while_19_begin:
	iload_2
	iconst_1
	if_icmpge lessThan_20
	iconst_1
	goto lessThan_20_end
lessThan_20:
	iconst_0
lessThan_20_end:
	if_eq while_19_end
	iload_3
	iconst_3
	if_icmpge lessThan_21
	iconst_1
	goto lessThan_21_end
lessThan_21:
	iconst_0
lessThan_21_end:
	if_eq while_19_end	aload_1
	iconst_0
	aload_0
	getfield row0:[I
	iaload
	iastore

	aload_1
	iconst_1
	aload_0
	getfield row1:[I
	iaload
	iastore

	aload_1
	iconst_2
	aload_0
	getfield row2:[I
	iaload
	iastore

	aload_1
	invokestatic BoardBase/sameArray([I)B
	if_eq if_22_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_23
	iconst_1
	goto lessThan_23_end
lessThan_23:
	iconst_0
lessThan_23_end:
	if_eq if_22_else	aload_1
	iconst_0
	iaload
	istore_2
	goto if_22_end
if_22_else:
if_22_end:
	iload_3
	iconst_1
	iadd
	istore_3
	goto while_19_begin
while_19_end:
	iload_2
	iconst_1
	if_icmpge if_24_else
	aload_1
	iconst_0
	aload_0
	getfield row0:[I
	iconst_0
	iaload
	iastore

	aload_1
	iconst_1
	aload_0
	getfield row1:[I
	iconst_1
	iaload
	iastore

	aload_1
	iconst_2
	aload_0
	getfield row2:[I
	iconst_2
	iaload
	iastore

	aload_1
	invokestatic BoardBase/sameArray([I)B
	if_eq if_25_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_26
	iconst_1
	goto lessThan_26_end
lessThan_26:
	iconst_0
lessThan_26_end:
	if_eq if_25_else	aload_1
	iconst_0
	iaload
	istore_2
	goto if_25_end
if_25_else:
	aload_1
	iconst_0
	aload_0
	getfield row0:[I
	iconst_2
	iaload
	iastore

	aload_1
	iconst_1
	aload_0
	getfield row1:[I
	iconst_1
	iaload
	iastore

	aload_1
	iconst_2
	aload_0
	getfield row2:[I
	iconst_0
	iaload
	iastore

	aload_1
	invokestatic BoardBase/sameArray([I)B
	if_eq if_27_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_28
	iconst_1
	goto lessThan_28_end
lessThan_28:
	iconst_0
lessThan_28_end:
	if_eq if_27_else	aload_1
	iconst_0
	iaload
	istore_2
	goto if_27_end
if_27_else:
if_27_end:
if_25_end:
	goto if_24_end
if_24_else:
if_24_end:
if_17_end:
if_15_end:
if_13_end:
	iload_2
	iconst_1
	if_icmpge lessThan_30
	iconst_1
	goto lessThan_30_end
lessThan_30:
	iconst_0
lessThan_30_end:
	if_eq if_29_else
	aload_0
	getfield movesmade:I
	bipush 9
	if_icmpge lessThan_33
	iconst_1
	goto lessThan_33_end
lessThan_33:
	iconst_0
lessThan_33_end:
	if_eq AND_32
	bipush 9
	aload_0
	getfield movesmade:I
	if_icmpge lessThan_35
	iconst_1
	goto lessThan_35_end
lessThan_35:
	iconst_0
lessThan_35_end:
	ifne negation_34
	iconst_1
	goto negation_34_end
negation_34:
	iconst_0
negation_34_end:
	if_eq AND_32
	iconst_1
	goto AND_32_end
AND_32:
	iconst_0
AND_32_end:
	ifne negation_31
	iconst_1
	goto negation_31_end
negation_31:
	iconst_0
negation_31_end:
	if_eq if_29_else	iconst_0
	istore_2
	goto if_29_end
if_29_else:
if_29_end:
	iload_2
	ireturn
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
	pop
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 7
	.limit locals 6

	new TicTacToe
	dup
	invokespecial TicTacToe/<init>()V
	astore_1
	aload_1
	invokevirtual TicTacToe/init()B
while_36_begin:
	aload_1
	invokevirtual TicTacToe/winner()I
	iconst_0
	iconst_1
	isub
	if_icmpge lessThan_38
	iconst_1
	goto lessThan_38_end
lessThan_38:
	iconst_0
lessThan_38_end:
	if_eq AND_37
	iconst_0
	iconst_1
	isub
	aload_1
	invokevirtual TicTacToe/winner()I
	if_icmpge lessThan_40
	iconst_1
	goto lessThan_40_end
lessThan_40:
	iconst_0
lessThan_40_end:
	ifne negation_39
	iconst_1
	goto negation_39_end
negation_39:
	iconst_0
negation_39_end:
	if_eq AND_37
	iconst_1
	goto AND_37_end
AND_37:
	iconst_0
AND_37_end:
	ifne if_36_else
	aload_1
	invokevirtual TicTacToe/getRow0()[I
	aload_1
	invokevirtual TicTacToe/getRow1()[I
	aload_1
	invokevirtual TicTacToe/getRow2()[I
	invokestatic BoardBase/printBoard([I[I[I)V
	aload_1
	invokevirtual TicTacToe/winner()I
	istore_2
	iload_2
	invokestatic BoardBase/printWinner(I)V
	pop
	pop
	return
.end method


