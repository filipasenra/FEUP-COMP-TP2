.class public Array
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 4

	bipush 100
	istore_2
	bipush 12
	istore_3
	newarray int
	astore_1
	aload_1
	bipush 10
	iload_3
	iastore
	aload_1
	istore_3
	aload_1
	invokestatic Array/println([I)V
	return
.end method


