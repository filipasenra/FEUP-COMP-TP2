.class public Simple
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public add(II)I
	.limit stack 2
	.limit locals 4

	iload_1
	iload_2
	iadd
	istore_3
	iload_3
	ireturn
	pop
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 2
	.limit locals 5

	bipush 20
	istore_1
	bipush 10
	istore_2
	new Simple
	dup
	invokespecial Simple/<init>()V
	astore 4
	aload 4
	iload_1
	iload_2
	invokevirtual Simple/add(II)I
	istore_3
	iload_3
	invokestatic io/println(I)V
	return
.end method


.method public test(II)I
	.limit stack 6
	.limit locals 3

	iload_2
	bipush 10
	iconst_3
	aload_0
	iconst_3
	iconst_4
	invokevirtual Simple/add(II)I
	iadd
	imul
	iadd
	istore_1
	iload_1
	ireturn
	pop
.end method


.method public test2()I
	.limit stack 3
	.limit locals 2

	aload_0
	iconst_3
	iconst_4
	invokevirtual Simple/add(II)I
	istore_1
	iload_1
	ireturn
	pop
.end method


