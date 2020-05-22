.class public Lazysort
.super Quicksort

.method public <init>()V
	aload_0
	invokenonvirtual Quicksort/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 6
	.limit locals 5

	bipush 10
	newarray int
	astore_1
	iconst_0
	istore_2
while_1_begin:
	iload_2
	aload_1
	arraylength
	if_icmpge while_1_end
	aload_1
	aload_1
	arraylength
	iload_2
	isub
	iastore

	iload_2
	iconst_1
	iadd
	istore_2
	goto while_1_begin
while_1_end:
	new Lazysort
	dup
	invokespecial Lazysort/<init>()V
	astore 4
	aload 4
	aload_1
	invokevirtual Lazysort/quicksort([I)B
	aload 4
	aload_1
	invokevirtual Lazysort/printL([I)
	istore_3
	pop
	pop
	pop
	pop
	return
.end method


.method public quicksort([I)B
	.limit stack 3
	.limit locals 3

	iconst_0
	iconst_5
	invokestatic MathUtils/random(II)I
	iconst_4
	if_icmpge if_2_else
	aload_0
	aload_1
	invokevirtual Lazysort/beLazy([I)B
	iconst_1
	istore_2
	goto if_2_end
if_2_else:
	iconst_0
	istore_2
if_2_end:
	iload_2
	ireturn
	pop
.end method


.method public beLazy([I)B
	.limit stack 8
	.limit locals 4

	aload_1
	arraylength
	istore_2
	iconst_0
	istore_3
while_4_begin:
	iload_3
	iload_2
	iconst_2
	idiv
	if_icmpge while_4_end
	aload_1
	iconst_0
	bipush 10
	invokestatic MathUtils/random(II)I
	iastore

	iload_3
	iconst_1
	iadd
	istore_3
	goto while_4_begin
while_4_end:
while_5_begin:
	iload_3
	iload_2
	if_icmpge while_5_end
	aload_1
	iconst_0
	bipush 10
	invokestatic MathUtils/random(II)I
	iconst_1
	iadd
	iastore

	iload_3
	iconst_1
	iadd
	istore_3
	goto while_5_begin
while_5_end:
	iconst_1
	ireturn
	pop
	pop
	pop
	pop
	pop
	pop
.end method


.method public a(II)I
	.limit stack 1
	.limit locals 3

	iconst_1
	ireturn
	pop
.end method


.method public a(I)I
	.limit stack 1
	.limit locals 2

	iconst_1
	ireturn
	pop
.end method


