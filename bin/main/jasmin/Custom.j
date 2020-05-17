.class public Custom
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public add(II)I
	.limit stack 99
	.limit locals 99

	iload_1
	iload_2
	iadd
	istore_3
	iload_3
	ireturn
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 99

	bipush 20
	istore_1
	bipush 10
	istore_2
	iload_2
	iload_1
	if_icmpge if1_else
	aload 4
	iload_1
	iload_2
	invokevirtual Custom/add(II)I
	istore_3
	goto if1_end
if1_else:
	iload_1
	iload_2
	if_icmpge if2_else
	bipush 11
	istore_2
	goto if2_end
if2_else:
if2_end:
if1_end:
	return
.end method


