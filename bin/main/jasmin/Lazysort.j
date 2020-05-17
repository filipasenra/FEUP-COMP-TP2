.class public Lazysort
.super Quicksort

.method public <init>()V
	aload_0
	invokenonvirtual Quicksort/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 99

	bipush 10
	newarray int
	astore_1
	iconst_0
	istore_2
	new Lazysort
	dup
	invokespecial Lazysort/<init>()V
	astore 4
	aload_1
	invokevirtual Lazysort/quicksort([I)B
	aload 4
	aload_1
	invokevirtual Lazysort/printL([I)
	istore_3
	return
.end method


.method public quicksort([I)B
	.limit stack 99
	.limit locals 99

	aload_0
	invokestatic MathUtils/random(II)I
	iconst_4
	if_icmpge if1_else
	istore_2
	goto if1_end
if1_else:
	istore_2
if1_end:
	istore_2
	goto if2_end
if2_else:
	aload_0
	istore_2
if2_end:
	iload_2
	ireturn
.end method

.method public beLazy([I)B
	.limit stack 99
	.limit locals 99

	aload_1
	aload_1
	arraylength
	istore_2
	iconst_0
	istore_3
	ireturn
.end method

.method public a(II)I
	.limit stack 99
	.limit locals 99

	iconst_1
	ireturn
.end method

.method public a(I)I
	.limit stack 99
	.limit locals 99

	iconst_1
	ireturn
.end method

