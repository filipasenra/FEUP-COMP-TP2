.class public Simple
.super java/lang/Object

.method public<init>()V
	aload_0
	invokenonvirtual java/lang/Object<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	istore_1
	bipush 30
	istore_2
	iconst_0
	bipush 10
	isub
	astore_3
	new Simple
	dup
	invokespecial Simple/<init>()V
	istore 4
	invokevirtual Simple/add(II)I
	return
.endMethod


.method public add(II)I
	.limit stack 99
	.limit locals 99

.endMethod

