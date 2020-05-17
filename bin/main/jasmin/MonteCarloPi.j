.class public MonteCarloPi
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public performSingleEstimate()B
	.limit stack 99
	.limit locals 99

	aload_0
	invokestatic MathUtils/random(I)
	istore_1
	aload_0
	invokestatic MathUtils/random(I)
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
	if_icmpge if1_else
	istore_3
	goto if1_end
if1_else:
	istore_3
if1_end:
	iload_3
	ireturn
.end method

.method public estimatePi100(I)I
	.limit stack 99
	.limit locals 99

	iconst_0
	istore_3
	iconst_0
	istore_2
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
	.limit locals 99

	aload_0
	invokestatic ioPlus/requestNumber()I
	istore_2
	istore_1
	iload_1
	invokestatic ioPlus/printResult(I)V
	return
.end method


