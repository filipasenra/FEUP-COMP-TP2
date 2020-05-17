.class public FindMaximum
.super java/lang/Object
.field public test_arr [I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public find_maximum([I)I
	.limit stack 99
	.limit locals 99

	iconst_1
	istore_2
	aload_1
	istore_3
	iload_3
	ireturn
.end method

.method public build_test_arr()I
	.limit stack 99
	.limit locals 99

	iconst_5
	newarray int
	astore_0
	aload_0
	iconst_0
	bipush 14
	iastore
	aload_0
	iconst_1
	bipush 28
	iastore
	aload_0
	iconst_2
	iconst_0
	iastore
	aload_0
	iconst_3
	iconst_0
	iconst_5
	isub
	iastore
	aload_0
	iconst_4
	bipush 12
	iastore
	iconst_0
	ireturn
.end method

.method public get_array()[I
	.limit stack 99
	.limit locals 99

	aload_0
	areturn
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 99

	new FindMaximum
	dup
	invokespecial FindMaximum/<init>()V
	astore_1
	invokevirtual FindMaximum/build_test_arr()I
	invokestatic ioPlus/printResult()
	return
.end method


