.class public VarNotInit
.super java/lang/Object

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 4
	.limit locals 5

	iconst_2
	istore 4
	iconst_2
	istore_3
while_1_begin:
	iload 4
	iconst_1
	if_icmpge while_1_end
	bipush 9
	istore_2
	iload 4
	iconst_1
	iadd
	istore 4
	goto while_1_begin
while_1_end:
	iload_2
	iload_3
	iadd
	istore_1
	pop
	pop
	return
.end method


