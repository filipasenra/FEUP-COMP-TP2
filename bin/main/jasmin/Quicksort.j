.class public Quicksort
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 4
	.limit locals 4

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
	iload_2
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
	new Quicksort
	dup
	invokespecial Quicksort/<init>()V
	astore_3

	aload_3
	aload_1
	invokevirtual Quicksort/quicksort([I)Z

	pop
	aload_3
	aload_1
	invokevirtual Quicksort/printL([I)Z

	pop
	return
.end method


.method public printL([I)Z
	.limit stack 3
	.limit locals 3

	iconst_0
	istore_2

while_2_begin:
	iload_2
	aload_1
	arraylength
	if_icmpge while_2_end
	aload_1
	iload_2
	iaload
	invokestatic io/println(I)V

	iload_2
	iconst_1
	iadd
	istore_2

	goto while_2_begin
while_2_end:
	iconst_1
	ireturn
.end method


.method public quicksort([I)Z
	.limit stack 5
	.limit locals 2

	aload_0
	aload_1
	iconst_0
	aload_1
	arraylength
	iconst_1
	isub
	invokevirtual Quicksort/quicksort([III)Z

	ireturn
.end method


.method public quicksort([III)Z
	.limit stack 5
	.limit locals 5

	iload_2
	iload_3
	if_icmpge if_3_else
	aload_0
	aload_1
	iload_2
	iload_3
	invokevirtual Quicksort/partition([III)I

	istore 4

	aload_0
	aload_1
	iload_2
	iload 4
	iconst_1
	isub
	invokevirtual Quicksort/quicksort([III)Z

	pop
	aload_0
	aload_1
	iload 4
	iconst_1
	iadd
	iload_3
	invokevirtual Quicksort/quicksort([III)Z

	pop
	goto if_3_end
if_3_else:
if_3_end:
	iconst_1
	ireturn
.end method


.method public partition([III)I
	.limit stack 4
	.limit locals 8

	aload_1
	iload_3
	iaload
	istore 4

	iload_2
	istore 5

	iload_2
	istore 6

while_4_begin:
	iload 6
	iload_3
	if_icmpge while_4_end
	aload_1
	iload 6
	iaload
	iload 4
	if_icmpge if_5_else
	aload_1
	iload 5
	iaload
	istore 7

	aload_1
	iload 5
	aload_1
	iload 6
	iaload
	iastore

	aload_1
	iload 6
	iload 7
	iastore

	iload 5
	iconst_1
	iadd
	istore 5

	goto if_5_end
if_5_else:
if_5_end:
	iload 6
	iconst_1
	iadd
	istore 6

	goto while_4_begin
while_4_end:
	aload_1
	iload 5
	iaload
	istore 7

	aload_1
	iload 5
	aload_1
	iload_3
	iaload
	iastore

	aload_1
	iload_3
	iload 7
	iastore

	iload 5
	ireturn
.end method


