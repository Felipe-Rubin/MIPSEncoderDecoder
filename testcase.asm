.text

.globl main

main:
	
	xori $t0, 	$t1,100
	
	lui 	$t3, 32
	
	addu $t5, 		$s1, $t3
	
	addiu $t4,  $t5, 8
Ex1:	
	addiu $t4, $t5, -4
	
	subu $t3, $t5, $1
	
	beq $t2, $t3, Ex1
	
	bne $t4, $v0, Ex2
	sltiu $t4, $t5, -32
	sltiu $t6, $s1, 32
	
			
Ex2:
	andi $t5, $t2, 1
	
	lw $t0, 0 ($t0)
	sw $t3, 0 ($t0)
	or $t2, $t5, $t1
	slt $t1, $t1, $t6
	sll $t5, $t1, 2
	srl $t5, $t1, 3
	j Ex1

.data

A: .word 0x0 0x1 0x2