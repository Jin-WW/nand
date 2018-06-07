// Created by Xi Jin
// C_PUSH constant 111
@111
D=A
@0
M=M+1;
A=M-1
M=D
// C_PUSH constant 333
@333
D=A
@0
M=M+1;
A=M-1
M=D
// C_PUSH constant 888
@888
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP static 8
@0
M=M-1;
A=M
D=M
@StaticTest.8
M=D
// C_POP static 3
@0
M=M-1;
A=M
D=M
@StaticTest.3
M=D
// C_POP static 1
@0
M=M-1;
A=M
D=M
@StaticTest.1
M=D
// C_PUSH static 3
@StaticTest.3
D=M
@0
M=M+1;
A=M-1
M=D
// C_PUSH static 1
@StaticTest.1
D=M
@0
M=M+1;
A=M-1
M=D
// arithmetic: sub
@0
M=M-1
A=M
D=M
@0
A=M-1
M=M-D
// C_PUSH static 8
@StaticTest.8
D=M
@0
M=M+1;
A=M-1
M=D
// arithmetic: add
@0
M=M-1
A=M
D=M
@0
A=M-1
M=M+D
