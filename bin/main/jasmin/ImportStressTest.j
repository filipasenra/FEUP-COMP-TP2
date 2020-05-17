.class public ImportStressTest
.super java/lang/Object
.field public i I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 99
	.limit locals 99

	aload_0
	istore_2
	new List
	dup
	invokespecial List/<init>()V
	astore_1
	istore_2
	aload_1
	invokevirtual List/size()I
	istore_2
	iconst_1
	istore_2
	return
.end method


