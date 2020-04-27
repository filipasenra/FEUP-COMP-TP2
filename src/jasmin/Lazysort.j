.class public Lazysort
.super Quicksort
.method public<init>()V
	aload_0
	invokenonvirtual java/lang/Object<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
	astore_1
	istore_2
	iconst_0
	astore 4
	istore_3
.endMethod

.method public quicksort([I)B
	.limit stack 99
	.limit locals 99

.endMethod

.method public beLazy([I)B
	.limit stack 99
	.limit locals 99

	istore_2
	istore_3
	iconst_0
.endMethod

