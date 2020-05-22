.class public ImportStressTest
.super java/lang/Object
.field public i I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	.limit stack 0
	.limit locals 3

	aload_0
	getfield i:I
	istore_2
	new List
	dup
	invokespecial List/<init>()V
	astore_1
	new List
	dup
	iconst_1
	invokespecial List/<init>(I)V
	invokevirtual List/size()I
	istore_2
	aload_1
	invokevirtual List/size()I
	istore_2
	iconst_1
	istore_2
	new Map
	dup
	iload_2
	iload_2
	invokespecial Map/<init>(II)V
	new ImportStressTest
	dup
	invokespecial ImportStressTest/<init>()V
	return
.end method


