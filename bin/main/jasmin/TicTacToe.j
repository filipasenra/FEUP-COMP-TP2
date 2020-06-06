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
	.limit locals 5

	iload_2
	ifge lessThan_1
	iconst_1
	goto lessThan_1_end
lessThan_1:
	iconst_0
lessThan_1_end:
	istore 4

	iload_2
	ifge if_2_else
	iconst_0
	istore_3

	goto if_2_end
if_2_else:
	iconst_2
	iload_2
	if_icmpge if_3_else
	iconst_0
	istore_3

	goto if_3_end
if_3_else:
	iconst_0
	aload_1
	iload_2
	iaload
	if_icmpge if_4_else
	iconst_0
	istore_3

	goto if_4_end
if_4_else:
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

if_4_end:
if_3_end:
if_2_end:
	iload_3
	ireturn
.end method


.method public Move(II)Z
	.limit stack 3
	.limit locals 4

	iload_1
	ifge lessThan_7
	iconst_1
	goto lessThan_7_end
lessThan_7:
	iconst_0
lessThan_7_end:
	ifne negation_6
	iconst_1
	goto negation_6_end
negation_6:
	iconst_0
negation_6_end:
	ifeq if_5_else
	iconst_0
	iload_1
	if_icmpge lessThan_9
	iconst_1
	goto lessThan_9_end
lessThan_9:
	iconst_0
lessThan_9_end:
	ifne negation_8
	iconst_1
	goto negation_8_end
negation_8:
	iconst_0
negation_8_end:
	ifeq if_5_else
	aload_0
	aload_0
	getfield TicTacToe/row0 [I
	iload_2
	invokevirtual TicTacToe/MoveRow([II)Z

	istore_3

	goto if_5_end
if_5_else:
	iload_1
	iconst_1
	if_icmpge lessThan_12
	iconst_1
	goto lessThan_12_end
lessThan_12:
	iconst_0
lessThan_12_end:
	ifne negation_11
	iconst_1
	goto negation_11_end
negation_11:
	iconst_0
negation_11_end:
	ifeq if_10_else
	iconst_1
	iload_1
	if_icmpge lessThan_14
	iconst_1
	goto lessThan_14_end
lessThan_14:
	iconst_0
lessThan_14_end:
	ifne negation_13
	iconst_1
	goto negation_13_end
negation_13:
	iconst_0
negation_13_end:
	ifeq if_10_else
	aload_0
	aload_0
	getfield TicTacToe/row1 [I
	iload_2
	invokevirtual TicTacToe/MoveRow([II)Z

	istore_3

	goto if_10_end
if_10_else:
	iload_1
	iconst_2
	if_icmpge lessThan_17
	iconst_1
	goto lessThan_17_end
lessThan_17:
	iconst_0
lessThan_17_end:
	ifne negation_16
	iconst_1
	goto negation_16_end
negation_16:
	iconst_0
negation_16_end:
	ifeq if_15_else
	iconst_2
	iload_1
	if_icmpge lessThan_19
	iconst_1
	goto lessThan_19_end
lessThan_19:
	iconst_0
lessThan_19_end:
	ifne negation_18
	iconst_1
	goto negation_18_end
negation_18:
	iconst_0
negation_18_end:
	ifeq if_15_else
	aload_0
	aload_0
	getfield TicTacToe/row2 [I
	iload_2
	invokevirtual TicTacToe/MoveRow([II)Z

	istore_3

	goto if_15_end
if_15_else:
	iconst_0
	istore_3

if_15_end:
if_10_end:
if_5_end:
	iload_3
	ireturn
.end method


.method public inbounds(II)Z
	.limit stack 2
	.limit locals 4

	iload_1
	ifge if_20_else
	iconst_0
	istore_3

	goto if_20_end
if_20_else:
	iload_2
	ifge if_21_else
	iconst_0
	istore_3

	goto if_21_end
if_21_else:
	iconst_2
	iload_1
	if_icmpge if_22_else
	iconst_0
	istore_3

	goto if_22_end
if_22_else:
	iconst_2
	iload_2
	if_icmpge if_23_else
	iconst_0
	istore_3

	goto if_23_end
if_23_else:
	iconst_1
	istore_3

if_23_end:
if_22_end:
if_21_end:
if_20_end:
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
	.limit stack 4
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

	ifeq if_24_else
	iconst_0
	aload_0
	getfield TicTacToe/row0 [I
	iconst_0
	iaload
	if_icmpge lessThan_25
	iconst_1
	goto lessThan_25_end
lessThan_25:
	iconst_0
lessThan_25_end:
	ifeq if_24_else
	aload_0
	getfield TicTacToe/row0 [I
	iconst_0
	iaload
	istore_2

	goto if_24_end
if_24_else:
	aload_0
	getfield TicTacToe/row1 [I
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_26_else
	iconst_0
	aload_0
	getfield TicTacToe/row1 [I
	iconst_0
	iaload
	if_icmpge lessThan_27
	iconst_1
	goto lessThan_27_end
lessThan_27:
	iconst_0
lessThan_27_end:
	ifeq if_26_else
	aload_0
	getfield TicTacToe/row1 [I
	iconst_0
	iaload
	istore_2

	goto if_26_end
if_26_else:
	aload_0
	getfield TicTacToe/row2 [I
	invokestatic BoardBase/sameArray([I)Z

	ifeq if_28_else
	iconst_0
	aload_0
	getfield TicTacToe/row2 [I
	iconst_0
	iaload
	if_icmpge lessThan_29
	iconst_1
	goto lessThan_29_end
lessThan_29:
	iconst_0
lessThan_29_end:
	ifeq if_28_else
	aload_0
	getfield TicTacToe/row2 [I
	iconst_0
	iaload
	istore_2

	goto if_28_end
if_28_else:
	iconst_0
	istore_3

	iload_2
	iconst_1
	if_icmpge lessThan_31
	iconst_1
	goto lessThan_31_end
lessThan_31:
	iconst_0
lessThan_31_end:
	ifeq while_30_end
	iload_3
	iconst_3
	if_icmpge lessThan_32
	iconst_1
	goto lessThan_32_end
lessThan_32:
	iconst_0
lessThan_32_end:
	ifeq while_30_end
while_30_begin:
	iload_2
	iconst_1
	if_icmpge lessThan_33
	iconst_1
	goto lessThan_33_end
lessThan_33:
	iconst_0
lessThan_33_end:
	ifeq while_30_end
	iload_3
	iconst_3
	if_icmpge lessThan_34
	iconst_1
	goto lessThan_34_end
lessThan_34:
	iconst_0
lessThan_34_end:
	ifeq while_30_end
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
if_35_end:
	iinc 3 1

	goto while_30_begin
while_30_end:
	iload_2
	iconst_1
	if_icmpge if_37_else
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

	ifeq if_38_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_39
	iconst_1
	goto lessThan_39_end
lessThan_39:
	iconst_0
lessThan_39_end:
	ifeq if_38_else
	aload_1
	iconst_0
	iaload
	istore_2

	goto if_38_end
if_38_else:
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

	ifeq if_40_else
	iconst_0
	aload_1
	iconst_0
	iaload
	if_icmpge lessThan_41
	iconst_1
	goto lessThan_41_end
lessThan_41:
	iconst_0
lessThan_41_end:
	ifeq if_40_else
	aload_1
	iconst_0
	iaload
	istore_2

	goto if_40_end
if_40_else:
if_40_end:
if_38_end:
	goto if_37_end
if_37_else:
if_37_end:
if_28_end:
if_26_end:
if_24_end:
	iload_2
	iconst_1
	if_icmpge lessThan_44
	iconst_1
	goto lessThan_44_end
lessThan_44:
	iconst_0
lessThan_44_end:
	ifeq AND_43
	aload_0
	getfield TicTacToe/movesmade I
	bipush 9
	if_icmpge lessThan_46
	iconst_1
	goto lessThan_46_end
lessThan_46:
	iconst_0
lessThan_46_end:
	ifne negation_45
	iconst_1
	goto negation_45_end
negation_45:
	iconst_0
negation_45_end:
	ifeq AND_43
	iconst_1
	goto AND_43_end
AND_43:
	iconst_0
AND_43_end:
	ifeq if_42_else
	bipush 9
	aload_0
	getfield TicTacToe/movesmade I
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
	ifeq if_42_else
	iconst_0
	istore_2

	goto if_42_end
if_42_else:
if_42_end:
	iload_2
	ireturn
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 4
	.limit locals 6

	new TicTacToe
	dup
	invokespecial TicTacToe/<init>()V
	astore_1

	aload_1
	invokevirtual TicTacToe/init()Z

	pop
	aload_1
	invokevirtual TicTacToe/winner()I

	iconst_0
	iconst_1
	isub
	if_icmpge lessThan_51
	iconst_1
	goto lessThan_51_end
lessThan_51:
	iconst_0
lessThan_51_end:
	ifne negation_50
	iconst_1
	goto negation_50_end
negation_50:
	iconst_0
negation_50_end:
	ifeq while_49_end
	iconst_0
	iconst_1
	isub
	aload_1
	invokevirtual TicTacToe/winner()I

	if_icmpge lessThan_53
	iconst_1
	goto lessThan_53_end
lessThan_53:
	iconst_0
lessThan_53_end:
	ifne negation_52
	iconst_1
	goto negation_52_end
negation_52:
	iconst_0
negation_52_end:
	ifeq while_49_end
while_49_begin:
	aload_1
	invokevirtual TicTacToe/winner()I

	iconst_0
	iconst_1
	isub
	if_icmpge lessThan_55
	iconst_1
	goto lessThan_55_end
lessThan_55:
	iconst_0
lessThan_55_end:
	ifne negation_54
	iconst_1
	goto negation_54_end
negation_54:
	iconst_0
negation_54_end:
	ifeq while_49_end
	iconst_0
	iconst_1
	isub
	aload_1
	invokevirtual TicTacToe/winner()I

	if_icmpge lessThan_57
	iconst_1
	goto lessThan_57_end
lessThan_57:
	iconst_0
lessThan_57_end:
	ifne negation_56
	iconst_1
	goto negation_56_end
negation_56:
	iconst_0
negation_56_end:
	ifeq while_49_end
	iconst_0
	istore_3

	iload_3
	ifne while_58_end
while_58_begin:
	iload_3
	ifne while_58_end
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

	ifne if_59_else
	invokestatic BoardBase/wrongMove()V

	goto if_59_end
if_59_else:
	aload_1
	aload 4
	iconst_0
	iaload
	aload 4
	iconst_1
	iaload
	invokevirtual TicTacToe/Move(II)Z

	ifne if_60_else
	invokestatic BoardBase/placeTaken()V

	goto if_60_end
if_60_else:
	iconst_1
	istore_3

if_60_end:
if_59_end:
	goto while_58_begin
while_58_end:
	aload_1
	invokevirtual TicTacToe/changeturn()Z

	pop
	goto while_49_begin
while_49_end:
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


