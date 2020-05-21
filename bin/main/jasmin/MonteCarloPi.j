.class public MonteCarloPi
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public performSingleEstimate()B
	.limit stack 99
	.limit locals 5

	iconst_0
	bipush 100
	isub
	bipush 100
	invokestatic MonteCarloPi/random(I)
	istore_1
	iconst_0
	bipush 100
	isub
	bipush 100
	invokestatic MonteCarloPi/random(I)
	istore_2
	iload_1
	iload_1
	imul
	iload_2
	iload_2
	imul
	iadd
	bipush 100
	idiv
	istore 4
	iload 4
	bipush 100
	if_icmpge if_1_else
	istore_3
	goto if_1_end
if_1_else:
	istore_3
if_1_end:
	iload_3
	ireturn
.end method

.method public estimatePi100(I)I
	.limit stack 99
	.limit locals 5

	iconst_0
	istore_3
	iconst_0
	istore_2
while_2_begin:
	iload_3
	iload_1
	if_icmpge while_2_end
	iload_2
	iconst_1
	iadd
	istore_2
	goto if_3_end
if_3_else:
if_3_end:
	iload_3
	iconst_1
	iadd
	istore_3
	goto while_2_begin
while_2_end:
	sipush 400
	iload_2
	iload_1
	idiv
	imul
	istore 4
	iload 4
	ireturn
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 3

	invokestatic MonteCarloPi/requestNumber()I
	istore_2
	new MonteCarloPi
	dup
	invokespecial MonteCarloPi/<init>()V
	iload_2
	invokevirtual MonteCarloPi/estimatePi100(I)I
	istore_1
	iload_1
	invokestatic MonteCarloPi/printResult(I)V
	return
.end method


