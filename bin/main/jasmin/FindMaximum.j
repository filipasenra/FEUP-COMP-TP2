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
	.limit locals 5

	iconst_1
	istore_2
	aload_1
	istore_3
while_1_begin:
	iload_2
	aload_1
	arraylength
	if_icmpge while_1_end
	aload_1
	istore 4
	iload_3
	iload 4
	if_icmpge if_2_else
	iload 4
	istore_3
	goto if_2_end
if_2_else:
if_2_end:
	iload_2
	iconst_1
	iadd
	istore_2
	goto while_1_begin
while_1_end:
	iload_3
	ireturn
.end method

.method public build_test_arr()I
	.limit stack 99
	.limit locals 1

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
	.limit locals 1

	aload0
	invokevirtual FindMaximum/tets()I
	aload_0
	areturn
.end method

.method public tets()I
	.limit stack 99
	.limit locals 1

	iconst_0
	ireturn
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 2

	new FindMaximum
	dup
	invokespecial FindMaximum/<init>()V
	astore_1
	new FindMaximum
	dup
	invokespecial FindMaximum/<init>()V
	invokevirtual FindMaximum/build_test_arr()I
	aload_1
	aload_1
	invokevirtual FindMaximum/get_array()[I
	invokevirtual FindMaximum/find_maximum([I)I
	invokestatic FindMaximum/printResult(I)V
	return
.end method


