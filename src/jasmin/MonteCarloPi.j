.class public MonteCarloPi
.super java/lang/Object
.method public<init>()V
	aload_0
	invokenonvirtual java/lang/Object<init>()V
	return
.end method

.method public performSingleEstimate()B
	.limit stack 99
	.limit locals 99

	istore_1
	istore_2
	istore 4
	idiv
.endMethod

.method public estimatePi100(I)I
	.limit stack 99
	.limit locals 99

	istore_3
	istore_2
	istore 4
	imul
.endMethod

.method public static main([Ljava/lang/String;)V
	istore_2
	istore_1
.endMethod

