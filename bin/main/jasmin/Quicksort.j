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
	new Quicksort
	dup
	invokespecial Quicksort/<init>()V
	astore_3
	aload_3
	aload_1
	invokevirtual Quicksort/quicksort([I)B
	aload_3
	aload_1
	invokevirtual Quicksort/printL([I)B
	return
.end method


.method public printL([I)B
	.limit stack 99
	.limit locals 99

	iconst_0
	istore_2
while_2_begin:
	iload_2
	aload_1
	arraylength
	if_icmpge while_2_end
	aload_1
	invokestatic Quicksort/println([I)
	iload_2
	iconst_1
	iadd
	istore_2
	goto while_2_begin
while_2_end:
	ireturn
.end method

.method public quicksort([I)B
	.limit stack 99
	.limit locals 99

	aload0
	aload_1
	iconst_0
	aload_1
	arraylength
	iconst_1
	isub
	invokevirtual Quicksort/quicksort([II)
	ireturn
.end method

.method public quicksort([III)B
	.limit stack 99
	.limit locals 99

	iload_2
	iload_3
	if_icmpge if_3_else
	aload0
	aload_1
	iload_2
	iload_3
	invokevirtual Quicksort/partition([III)I
	istore 4
	aload0
	aload_1
	iload_2
	iload 4
	iconst_1
	isub
	invokevirtual Quicksort/quicksort([II)
	aload0
	aload_1
	iload 4
	iconst_1
	iadd
	iload_3
	invokevirtual Quicksort/quicksort([III)B
	goto if_3_end
if_3_else:
if_3_end:
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
while_4_begin:
	iload 6
	iload_3
	if_icmpge while_4_end
	aload_1
	iload 4
	if_icmpge if_5_else
	aload_1
	istore 7
	aload_1
	aload_1
	iastore
	aload_1
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

