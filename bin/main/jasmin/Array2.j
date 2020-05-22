.class public Array2
.super java/lang/Object
.field public a [I
.field public c I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public test()I
	.limit stack 0
	.limit locals 1

	aload_0
	getfield c:I
	ireturn
.end method


.method public test(I)I
	.limit stack 4
	.limit locals 2

	iconst_1
	aload_0
	putfield c:I

	bipush 8
	newarray int
	aload_0
	putfield a:[I

	aload_0
	getfield a:[I
	iconst_0
	iconst_2
	iastore

	aload_0
	getfield a:[I
	invokestatic io/printArray([I)V
	aload_0
	getfield a:[I
	iconst_0
	iaload
	invokestatic io/print([I)
	iconst_1
	ireturn
	pop
	pop
	pop
.end method


.method public static main([Ljava/lang/String;)V
	.limit stack 5
	.limit locals 3

	bipush 8
	newarray int
	astore_1
	aload_1
	iconst_0
	iconst_2
	iastore

	aload_1
	invokestatic io/printArray([I)V
	aload_1
	iconst_0
	iaload
	invokestatic io/print([I)
	new Map
	dup
	iconst_1
	iconst_1
	invokespecial Map/<init>(II)V
	astore_2
	pop
	pop
	pop
	pop
	return
.end method


