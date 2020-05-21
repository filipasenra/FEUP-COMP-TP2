.class public Array2
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 2

	bipush 8
	newarray int
	astore_1
	aload_1
	iconst_0
	iconst_2
	iastore
	aload_1
	invokestatic Array2/printArray([I)V
	aload_1
	invokestatic Array2/print([I)
	return
.end method


