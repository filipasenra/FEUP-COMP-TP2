.class public TicTacToe
.super java/lang/Object
.field private row0 [I
.field private row1 [I
.field private row2 [I
.field private whoseturn I
.field private movesmade I
.field private pieces [I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public init()Z
	.limit stack 3
	.limit locals 1

	aload_0
	iconst_3
	newarray int
	putfield TicTacToe/row0 [I

	aload_0
	iconst_3
	newarray int
	putfield TicTacToe/row1 [I

	aload_0
	iconst_3
	newarray int
	putfield TicTacToe/row2 [I

	aload_0
	iconst_2
	newarray int
	putfield TicTacToe/pieces [I

	aload_0
	getfield TicTacToe/pieces [I
	iconst_0
	iconst_1
	iastore

	aload_0
	getfield TicTacToe/pieces [I
	iconst_1
	iconst_2
	iastore

	aload_0
	iconst_0
	putfield TicTacToe/whoseturn I

	aload_0
	iconst_0
	putfield TicTacToe/movesmade I

	iconst_1
	ireturn
.end method


.method public getRow0()[I
	.limit stack 2
	.limit locals 1

	aload_0
	getfield TicTacToe/row0 [I
	areturn
.end method


.method public getRow1()[I
	.limit stack 2
	.limit locals 1

	aload_0
	getfield TicTacToe/row1 [I
	areturn
.end method


.method public getRow2()[I
	.limit stack 2
	.limit locals 1

	aload_0
	getfield TicTacToe/row2 [I
	areturn
.end method


.method public MoveRow([II)Z
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
	iload_2
	iaload
	if_icmpge if_3_else
	iconst_0
	istore_3

	goto if_3_end
if_3_else:
	aload_1
	iload_2
	aload_0
	getfield TicTacToe/pieces [I
	aload_0
	getfield TicTacToe/whoseturn I
	iaload
	iastore

	aload_0
	aload_0
	getfield TicTacToe/movesmade I
	iconst_1
	iadd
	putfield TicTacToe/movesmade I

	iconst_1
	istore_3

if_3_end:
if_2_end:
if_1_end:
	iload_3
	ireturn
.end method


.method public Move(II)Z
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
	ifne negation_5
	iconst_1
	goto negation_5_end
negation_5:
	iconst_0
negation_5_end:
	ifeq if_4_else
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
	ifeq if_4_else
	aload_0
	aload_0
	getfield TicTacToe/row0 [I
	iload_2
	invokevirtual TicTacToe/MoveRow([II)Z

	istore_3

	goto if_4_end
if_4_else:
	iload_1
	iconst_1
	if_icmpge lessThan_11
	iconst_1
	goto lessThan_11_end
lessThan_11:
	iconst_0
lessThan_11_end:
	ifne negation_10
	iconst_1
	goto negation_10_end
negation_10:
	iconst_0
negation_10_end:
	ifeq if_9_else
	iconst_1
	iload_1
	if_icmpge lessThan_13
	iconst_1
	goto lessThan_13_end
lessThan_13:
	iconst_0
lessThan_13_end:
	ifne negation_12
	iconst_1
	goto negation_12_end
negation_12:
	iconst_0
negation_12_end:
	ifeq if_9_else
	aload_0
	aload_0
	getfield TicTacToe/row1 [I
	iload_2
	invokevirtual TicTacToe/MoveRow([II)Z

	istore_3

	goto if_9_end
if_9_else:
	iload_1
	iconst_2
	if_icmpge lessThan_16
	iconst_1
	goto lessThan_16_end
lessThan_16:
	iconst_0
lessThan_16_end:
	ifne negation_15
	iconst_1
	goto negation_15_end
negation_15:
	iconst_0
negation_15_end:
	ifeq if_14_else
	iconst_2
	iload_1
	if_icmpge lessThan_18
	iconst_1
	goto lessThan_18_end
lessThan_18:
	iconst_0
lessThan_18_end:
	ifne negation_17
	iconst_1
	goto negation_17_end
negation_17:
	iconst_0
negation_17_end:
	ifeq if_14_else
	aload_0
	aload_0
	getfield TicTacToe/row2 [I
	iload_2
	invokevirtual TicTacToe/MoveRow([II)Z

	istore_3

	goto if_14_end
if_14_else:
	iconst_0
	istore_3

if_14_end:
if_9_end:
if_4_end:
	iload_3
	ireturn
.end method


.method public inbounds(II)Z
	.limit stack 2
	.limit locals 4

	iload_1
	iconst_0
	if_icmpge if_19_else
	iconst_0
	istore_3

	goto if_19_end
if_19_else:
	iload_2
	iconst_0
	if_icmpge if_20_else
	iconst_0
	istore_3

	goto if_20_end
if_20_else:
	iconst_2
	iload_1
	if_icmpge if_21_else
	iconst_0
	istore_3

	goto if_21_end
if_21_else:
	iconst_2
	iload_2
	if_icmpge if_22_else
	iconst_0
	istore_3

	goto if_22_end
if_22_else:
	iconst_1
	istore_3

if_22_end:
if_21_end:
if_20_end:
if_19_end:
	iload_3
	ireturn
.end method


.method public changeturn()Z
	.limit stack 4
	.limit locals 1

	aload_0
	iconst_1
	aload_0
	getfield TicTacToe/whoseturn I
	isub
	putfield TicTacToe/whoseturn I

	iconst_1
	ireturn
.end method


.method public getCurrentPlayer()I
	.limit stack 2
	.limit locals 1

	aload_0
	getfield TicTacToe/whoseturn I
	iconst_1
	iadd
	ireturn
.end method


.method public winner()I
	.limit stack 7
	.limit locals 4

	iconst_0
	iconst_1
	isub
	istore_2

	iconst_3
	newarray int
	astore_1

	aload_0
	getfield TicTacToe/row0 [I
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_23_else
	iconst_0
	aload_0
	getfield TicTacToe/row0 [I
	iconst_0
	iaload
	if_icmpge lessThan_24
	iconst_1
	goto lessThan_24_end
lessThan_24:
	iconst_0
lessThan_24_end:
	ifeq if_23_else
	aload_0
	getfield TicTacToe/row0 [I
	iconst_0
	iaload
	istore_2

	goto if_23_end
if_23_else:
	aload_0
	getfield TicTacToe/row1 [I
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_25_else
	iconst_0
	aload_0
	getfield TicTacToe/row1 [I
	iconst_0
	iaload
	if_icmpge lessThan_26
	iconst_1
	goto lessThan_26_end
lessThan_26:
	iconst_0
lessThan_26_end:
	ifeq if_25_else
	aload_0
	getfield TicTacToe/row1 [I
	iconst_0
	iaload
	istore_2

	goto if_25_end
if_25_else:
	aload_0
	getfield TicTacToe/row2 [I
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_27_else
	iconst_0
	aload_0
	getfield TicTacToe/row2 [I
	iconst_0
	iaload
	if_icmpge lessThan_28
	iconst_1
	goto lessThan_28_end
lessThan_28:
	iconst_0
lessThan_28_end:
	ifeq if_27_else
	aload_0
	getfield TicTacToe/row2 [I
	iconst_0
	iaload
	istore_2

	goto if_27_end
if_27_else:
	iconst_0
	istore_3

while_29_begin:
	iload_2
	iconst_1
	if_icmpge lessThan_30
	iconst_1
	goto lessThan_30_end
lessThan_30:
	iconst_0
lessThan_30_end:
	ifeq while_29_end
	iload_3
	iconst_3
	if_icmpge lessThan_31
	iconst_1
	goto lessThan_31_end
lessThan_31:
	iconst_0
lessThan_31_end:
	ifeq while_29_end
	aload_1
	iconst_0
	aload_0
	getfield TicTacToe/row0 [I
	iload_3
	iaload
	iastore

	aload_1
	iconst_1
	aload_0
	getfield TicTacToe/row1 [I
	iload_3
	iaload
	iastore

	aload_1
	iconst_2
	aload_0
	getfield TicTacToe/row2 [I
	iload_3
	iaload
	iastore

	aload_1
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_32_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_33
	iconst_1
	goto lessThan_33_end
lessThan_33:
	iconst_0
lessThan_33_end:
	ifeq if_32_else
	aload_1
	iconst_0
	iaload
	istore_2

	goto if_32_end
if_32_else:
if_32_end:
	iload_3
	iconst_1
	iadd
	istore_3

	goto while_29_begin
while_29_end:
	iload_2
	iconst_1
	if_icmpge if_34_else
	aload_1
	iconst_0
	aload_0
	getfield TicTacToe/row0 [I
	iconst_0
	iaload
	iastore

	aload_1
	iconst_1
	aload_0
	getfield TicTacToe/row1 [I
	iconst_1
	iaload
	iastore

	aload_1
	iconst_2
	aload_0
	getfield TicTacToe/row2 [I
	iconst_2
	iaload
	iastore

	aload_1
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_35_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_36
	iconst_1
	goto lessThan_36_end
lessThan_36:
	iconst_0
lessThan_36_end:
	ifeq if_35_else
	aload_1
	iconst_0
	iaload
	istore_2

	goto if_35_end
if_35_else:
	aload_1
	iconst_0
	aload_0
	getfield TicTacToe/row0 [I
	iconst_2
	iaload
	iastore

	aload_1
	iconst_1
	aload_0
	getfield TicTacToe/row1 [I
	iconst_1
	iaload
	iastore

	aload_1
	iconst_2
	aload_0
	getfield TicTacToe/row2 [I
	iconst_0
	iaload
	iastore

	aload_1
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_37_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_38
	iconst_1
	goto lessThan_38_end
lessThan_38:
	iconst_0
lessThan_38_end:
	ifeq if_37_else
	aload_1
	iconst_0
	iaload
	istore_2

	goto if_37_end
if_37_else:
if_37_end:
if_35_end:
	goto if_34_end
if_34_else:
if_34_end:
if_27_end:
if_25_end:
if_23_end:
	iload_2
	iconst_1
	if_icmpge lessThan_41
	iconst_1
	goto lessThan_41_end
lessThan_41:
	iconst_0
lessThan_41_end:
	ifeq AND_40
	aload_0
	getfield TicTacToe/movesmade I
	bipush 9
	if_icmpge lessThan_43
	iconst_1
	goto lessThan_43_end
lessThan_43:
	iconst_0
lessThan_43_end:
	ifne negation_42
	iconst_1
	goto negation_42_end
negation_42:
	iconst_0
negation_42_end:
	ifeq AND_40
	iconst_1
	goto AND_40_end
AND_40:
	iconst_0
AND_40_end:
	ifeq if_39_else
	bipush 9
	aload_0
	getfield TicTacToe/movesmade I
	if_icmpge lessThan_45
	iconst_1
	goto lessThan_45_end
lessThan_45:
	iconst_0
lessThan_45_end:
	ifne negation_44
	iconst_1
	goto negation_44_end
negation_44:
	iconst_0
negation_44_end:
	ifeq if_39_else
	iconst_0
	istore_2

	goto if_39_end
if_39_else:
if_39_end:
	iload_2
	ireturn
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 5
	.limit locals 6

	new TicTacToe
	dup
	invokespecial TicTacToe/<init>()V
	astore_1

	aload_1
	invokevirtual TicTacToe/init()Z

	pop
while_46_begin:
	aload_1
	invokevirtual TicTacToe/winner()I

	iconst_0
	iconst_1
	isub
	if_icmpge lessThan_48
	iconst_1
	goto lessThan_48_end
lessThan_48:
	iconst_0
lessThan_48_end:
	ifne negation_47
	iconst_1
	goto negation_47_end
negation_47:
	iconst_0
negation_47_end:
	ifeq while_46_end
	iconst_0
	iconst_1
	isub
	aload_1
	invokevirtual TicTacToe/winner()I

	if_icmpge lessThan_50
	iconst_1
	goto lessThan_50_end
lessThan_50:
	iconst_0
lessThan_50_end:
	ifne negation_49
	iconst_1
	goto negation_49_end
negation_49:
	iconst_0
negation_49_end:
	ifeq while_46_end
	iconst_0
	istore_3

while_51_begin:
	iload_3
	ifne while_51_end
	aload_1
	invokevirtual TicTacToe/getRow0()[I

	aload_1
	invokevirtual TicTacToe/getRow1()[I

	aload_1
	invokevirtual TicTacToe/getRow2()[I

	invokestatic BoardBase/printBoard([I[I[I)V

	aload_1
	invokevirtual TicTacToe/getCurrentPlayer()I

	istore 5

	iload 5
	invokestatic BoardBase/playerTurn(I)[I

	astore 4

	aload_1
	aload 4
	iconst_0
	iaload
	aload 4
	iconst_1
	iaload
	invokevirtual TicTacToe/inbounds(II)Z

	ifne if_52_else
	invokestatic BoardBase/wrongMove()V

	goto if_52_end
if_52_else:
	aload_1
	aload 4
	iconst_0
	iaload
	aload 4
	iconst_1
	iaload
	invokevirtual TicTacToe/Move(II)Z

	ifne if_53_else
	invokestatic BoardBase/placeTaken()V

	goto if_53_end
if_53_else:
	iconst_1
	istore_3

if_53_end:
if_52_end:
	goto while_51_begin
while_51_end:
	aload_1
	invokevirtual TicTacToe/changeturn()Z

	pop
	goto while_46_begin
while_46_end:
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

	return
.end method


