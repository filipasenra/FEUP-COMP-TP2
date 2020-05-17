.class public Quicksort
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
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
	new Quicksort
	dup
	invokespecial Quicksort/<init>()V
	astore_3
	aload_1
	invokevirtual Quicksort/quicksort([I)B
	aload_1
	invokevirtual Quicksort/printL([I)B
	return
.end method


.method public printL([I)B
	.limit stack 99
	.limit locals 99

	iconst_0
	istore_2
	ireturn
.end method

.method public quicksort([I)B
	.limit stack 99
	.limit locals 99

	aload_0
	ireturn
.end method

.method public quicksort([III)B
	.limit stack 99
	.limit locals 99

	iload_2
	iload_3
	if_icmpge if1_else
	aload_0
	istore 4
	goto if1_end
if1_else:
if1_end:
	ireturn
.end method

.method public partition([III)I
	.limit stack 99
	.limit locals 99

	aload_1
	istore 4
	iload_2
	istore 5
	iload_2
	istore 6
	aload_1
	istore 7
	aload_1
	aload_1
	iastore
	aload_1
	iload 7
	iastore
	iload 5
	ireturn
.end method

