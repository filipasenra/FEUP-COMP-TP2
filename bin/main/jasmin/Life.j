.class public Life
.super java/lang/Object
.field public UNDERPOP_LIM I
.field public OVERPOP_LIM I
.field public REPRODUCE_NUM I
.field public LOOPS_PER_MS I
.field public xMax I
.field public yMax I
.field public field_name [I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 3

	new Life
	dup
	invokespecial Life/<init>()V
	astore_1
	aload_1
	invokevirtual Life/init()B
while_1_begin:
	iconst_1
	ifeq while_1_end
	aload_1
	invokevirtual Life/printfield_name()B
	aload_1
	invokevirtual Life/update()B
	invokestatic Life/read()I
	istore_2
	goto while_1_begin
while_1_end:
	return
.end method


.method public init()B
	.limit stack 99
	.limit locals 3

	iconst_1
	newarray int
	astore_1
	iconst_2
	astore_0
	iconst_3
	astore_0
	iconst_3
	astore_0
	ldc 225000
	astore_0
	aload0
	aload_1
	invokevirtual Life/field_name([I)[I
	astore_0
	aload_1
	istore_2
	iload_2
	iconst_1
	isub
	astore_0
	aload_0
	arraylength
	iload_2
	idiv
	iconst_1
	isub
	astore_0
	ireturn
.end method

.method public field_name([I)[I
	.limit stack 99
	.limit locals 3

	bipush 100
	newarray int
	astore_2
	aload_1
	iconst_0
	bipush 10
	iastore
	aload_2
	iconst_0
	iconst_0
	iastore
	aload_2
	iconst_1
	iconst_0
	iastore
	aload_2
	iconst_2
	iconst_1
	iastore
	aload_2
	iconst_3
	iconst_0
	iastore
	aload_2
	iconst_4
	iconst_0
	iastore
	aload_2
	iconst_5
	iconst_0
	iastore
	aload_2
	bipush 6
	iconst_0
	iastore
	aload_2
	bipush 7
	iconst_0
	iastore
	aload_2
	bipush 8
	iconst_0
	iastore
	aload_2
	bipush 9
	iconst_0
	iastore
	aload_2
	bipush 10
	iconst_1
	iastore
	aload_2
	bipush 11
	iconst_0
	iastore
	aload_2
	bipush 12
	iconst_1
	iastore
	aload_2
	bipush 13
	iconst_0
	iastore
	aload_2
	bipush 14
	iconst_0
	iastore
	aload_2
	bipush 15
	iconst_0
	iastore
	aload_2
	bipush 16
	iconst_0
	iastore
	aload_2
	bipush 17
	iconst_0
	iastore
	aload_2
	bipush 18
	iconst_0
	iastore
	aload_2
	bipush 19
	iconst_0
	iastore
	aload_2
	bipush 20
	iconst_0
	iastore
	aload_2
	bipush 21
	iconst_1
	iastore
	aload_2
	bipush 22
	iconst_1
	iastore
	aload_2
	bipush 23
	iconst_0
	iastore
	aload_2
	bipush 24
	iconst_0
	iastore
	aload_2
	bipush 25
	iconst_0
	iastore
	aload_2
	bipush 26
	iconst_0
	iastore
	aload_2
	bipush 27
	iconst_0
	iastore
	aload_2
	bipush 28
	iconst_0
	iastore
	aload_2
	bipush 29
	iconst_0
	iastore
	aload_2
	bipush 30
	iconst_0
	iastore
	aload_2
	bipush 31
	iconst_0
	iastore
	aload_2
	bipush 32
	iconst_0
	iastore
	aload_2
	bipush 33
	iconst_0
	iastore
	aload_2
	bipush 34
	iconst_0
	iastore
	aload_2
	bipush 35
	iconst_0
	iastore
	aload_2
	bipush 36
	iconst_0
	iastore
	aload_2
	bipush 37
	iconst_0
	iastore
	aload_2
	bipush 38
	iconst_0
	iastore
	aload_2
	bipush 39
	iconst_0
	iastore
	aload_2
	bipush 40
	iconst_0
	iastore
	aload_2
	bipush 41
	iconst_0
	iastore
	aload_2
	bipush 42
	iconst_0
	iastore
	aload_2
	bipush 43
	iconst_0
	iastore
	aload_2
	bipush 44
	iconst_0
	iastore
	aload_2
	bipush 45
	iconst_0
	iastore
	aload_2
	bipush 46
	iconst_0
	iastore
	aload_2
	bipush 47
	iconst_0
	iastore
	aload_2
	bipush 48
	iconst_0
	iastore
	aload_2
	bipush 49
	iconst_0
	iastore
	aload_2
	bipush 50
	iconst_0
	iastore
	aload_2
	bipush 51
	iconst_0
	iastore
	aload_2
	bipush 52
	iconst_0
	iastore
	aload_2
	bipush 53
	iconst_0
	iastore
	aload_2
	bipush 54
	iconst_0
	iastore
	aload_2
	bipush 55
	iconst_0
	iastore
	aload_2
	bipush 56
	iconst_0
	iastore
	aload_2
	bipush 57
	iconst_0
	iastore
	aload_2
	bipush 58
	iconst_0
	iastore
	aload_2
	bipush 59
	iconst_0
	iastore
	aload_2
	bipush 60
	iconst_0
	iastore
	aload_2
	bipush 61
	iconst_0
	iastore
	aload_2
	bipush 62
	iconst_0
	iastore
	aload_2
	bipush 63
	iconst_0
	iastore
	aload_2
	bipush 64
	iconst_0
	iastore
	aload_2
	bipush 65
	iconst_0
	iastore
	aload_2
	bipush 66
	iconst_0
	iastore
	aload_2
	bipush 67
	iconst_0
	iastore
	aload_2
	bipush 68
	iconst_0
	iastore
	aload_2
	bipush 69
	iconst_0
	iastore
	aload_2
	bipush 70
	iconst_0
	iastore
	aload_2
	bipush 71
	iconst_0
	iastore
	aload_2
	bipush 72
	iconst_0
	iastore
	aload_2
	bipush 73
	iconst_0
	iastore
	aload_2
	bipush 74
	iconst_0
	iastore
	aload_2
	bipush 75
	iconst_0
	iastore
	aload_2
	bipush 76
	iconst_0
	iastore
	aload_2
	bipush 77
	iconst_0
	iastore
	aload_2
	bipush 78
	iconst_0
	iastore
	aload_2
	bipush 79
	iconst_0
	iastore
	aload_2
	bipush 80
	iconst_0
	iastore
	aload_2
	bipush 81
	iconst_0
	iastore
	aload_2
	bipush 82
	iconst_0
	iastore
	aload_2
	bipush 83
	iconst_0
	iastore
	aload_2
	bipush 84
	iconst_0
	iastore
	aload_2
	bipush 85
	iconst_0
	iastore
	aload_2
	bipush 86
	iconst_0
	iastore
	aload_2
	bipush 87
	iconst_0
	iastore
	aload_2
	bipush 88
	iconst_0
	iastore
	aload_2
	bipush 89
	iconst_0
	iastore
	aload_2
	bipush 90
	iconst_0
	iastore
	aload_2
	bipush 91
	iconst_0
	iastore
	aload_2
	bipush 92
	iconst_0
	iastore
	aload_2
	bipush 93
	iconst_0
	iastore
	aload_2
	bipush 94
	iconst_0
	iastore
	aload_2
	bipush 95
	iconst_0
	iastore
	aload_2
	bipush 96
	iconst_0
	iastore
	aload_2
	bipush 97
	iconst_0
	iastore
	aload_2
	bipush 98
	iconst_0
	iastore
	aload_2
	bipush 99
	iconst_0
	iastore
	aload_2
	areturn
.end method

.method public update()B
	.limit stack 99
	.limit locals 6

	newarray int
	astore 5
	iconst_0
	istore_1
while_2_begin:
	iload_1
	aload_0
	arraylength
	if_icmpge while_2_end
	aload_0
	istore_2
	aload0
	iload_1
	invokevirtual Life/getLiveNeighborN(I)I
	istore_3
	istore 4
	aload 5
	iconst_0
	iastore
	goto if_4_end
if_4_else:
	aload 5
	aload_0
	iastore
if_4_end:
	goto if_3_end
if_3_else:
	aload 5
	iconst_1
	iastore
	goto if_5_end
if_5_else:
	aload 5
	aload_0
	iastore
if_5_end:
if_3_end:
	iload_1
	iconst_1
	iadd
	istore_1
	goto while_2_begin
while_2_end:
	aload 5
	astore_0
	ireturn
.end method

.method public printfield_name()B
	.limit stack 99
	.limit locals 3

	iconst_0
	istore_1
	iconst_0
	istore_2
while_6_begin:
	iload_1
	aload_0
	arraylength
	if_icmpge while_6_end
	invokestatic Life/println()V
	iconst_0
	istore_2
	goto if_7_end
if_7_else:
if_7_end:
	aload_0
	invokestatic Life/print()
	iload_1
	iconst_1
	iadd
	istore_1
	iload_2
	iconst_1
	iadd
	istore_2
	goto while_6_begin
while_6_end:
	invokestatic Life/println()V
	invokestatic Life/println()V
	ireturn
.end method

.method public trIdx(II)I
	.limit stack 99
	.limit locals 3

	iload_1
	aload_0
	iconst_1
	iadd
	iload_2
	imul
	iadd
	ireturn
.end method

.method public cartIdx(I)[I
	.limit stack 99
	.limit locals 6

	aload_0
	iconst_1
	iadd
	istore 4
	iload_1
	iload 4
	idiv
	istore_3
	iload_1
	iload_3
	iload 4
	imul
	isub
	istore_2
	iconst_2
	newarray int
	astore 5
	aload 5
	iconst_0
	iload_2
	iastore
	aload 5
	iconst_1
	iload_3
	iastore
	aload 5
	areturn
.end method

.method public getNeighborCoords(I)[I
	.limit stack 99
	.limit locals 10

	aload0
	iload_1
	invokevirtual Life/cartIdx(I)[I
	astore 8
	aload 8
	istore_2
	aload 8
	istore_3
	iload_2
	aload_0
	if_icmpge if_8_else
	iload_2
	iconst_1
	iadd
	istore 6
	iload_2
	iconst_1
	isub
	istore 4
	goto if_9_end
if_9_else:
	aload_0
	istore 4
if_9_end:
	goto if_8_end
if_8_else:
	iconst_0
	istore 6
	iload_2
	iconst_1
	isub
	istore 4
if_8_end:
	iload_3
	aload_0
	if_icmpge if_10_else
	iload_3
	iconst_1
	iadd
	istore 7
	iload_3
	iconst_1
	isub
	istore 5
	goto if_11_end
if_11_else:
	aload_0
	istore 5
if_11_end:
	goto if_10_end
if_10_else:
	iconst_0
	istore 7
	iload_3
	iconst_1
	isub
	istore 5
if_10_end:
	bipush 8
	newarray int
	astore 9
	aload 9
	iconst_0
	aload0
	iload_2
	iload 5
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	iconst_1
	aload0
	iload 4
	iload 5
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	iconst_2
	aload0
	iload 4
	iload_3
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	iconst_3
	aload0
	iload 4
	iload 7
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	iconst_4
	aload0
	iload_2
	iload 7
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	iconst_5
	aload0
	iload 6
	iload 7
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	bipush 6
	aload0
	iload 6
	iload_3
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	bipush 7
	aload0
	iload 6
	iload 5
	invokevirtual Life/trIdx(II)I
	iastore
	aload 9
	areturn
.end method

.method public getLiveNeighborN(I)I
	.limit stack 99
	.limit locals 5

	iconst_0
	istore 4
	aload0
	iload_1
	invokevirtual Life/getNeighborCoords(I)[I
	astore_2
	iconst_0
	istore_3
while_12_begin:
	iload_3
	aload_2
	arraylength
	if_icmpge while_12_end
	iload 4
	iconst_1
	iadd
	istore 4
	goto if_13_end
if_13_else:
if_13_end:
	iload_3
	iconst_1
	iadd
	istore_3
	goto while_12_begin
while_12_end:
	iload 4
	ireturn
.end method

.method public busyWait(I)B
	.limit stack 99
	.limit locals 4

	iload_1
	aload_0
	imul
	istore_3
	iconst_0
	istore_2
while_14_begin:
	iload_2
	iload_3
	if_icmpge while_14_end
	iload_2
	iconst_1
	iadd
	istore_2
	goto while_14_begin
while_14_end:
	ireturn
.end method

.method public eq(II)B
	.limit stack 99
	.limit locals 3

	ireturn
.end method

.method public ne(II)B
	.limit stack 99
	.limit locals 3

	ireturn
.end method

.method public lt(II)B
	.limit stack 99
	.limit locals 3

	ireturn
.end method

.method public le(II)B
	.limit stack 99
	.limit locals 3

	ireturn
.end method

.method public gt(II)B
	.limit stack 99
	.limit locals 3

	ireturn
.end method

.method public ge(II)B
	.limit stack 99
	.limit locals 3

	ireturn
.end method

