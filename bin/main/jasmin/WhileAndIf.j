.class public WhileAndIf
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 99

	bipush 20
	istore_1
	bipush 10
	istore_2
	bipush 10
	newarray int
	astore 4
	iload_1
	iload_2
	if_icmpge if1_else
	iload_1
	iconst_1
	isub
	istore_3
	goto if1_end
if1_else:
	iload_2
	iconst_1
	isub
	istore_3
if1_end:
	iconst_0
	istore_3
	return
.end method


