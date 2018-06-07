// Created by Xi Jin
// C_PUSH constant 3030
@3030
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP pointer 0
@0
M=M-1;
A=M
D=M
@3
M=D
// C_PUSH constant 3040
@3040
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP pointer 1
@0
M=M-1;
A=M
D=M
@4
M=D
// C_PUSH constant 32
@32
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP this 2
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@2
D=A
@3
D=M+D
@popSegmentAddress
M=D
@popSegmentValue
D=M
@popSegmentAddress
A=M
M=D
// C_PUSH constant 46
@46
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP that 6
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@6
D=A
@4
D=M+D
@popSegmentAddress
M=D
@popSegmentValue
D=M
@popSegmentAddress
A=M
M=D
// C_PUSH pointer 0
@3
D=M
@0
M=M+1;
A=M-1
M=D
// C_PUSH pointer 1
@4
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
// C_PUSH this 2
@2
D=A
@3
A=M+D
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
// C_PUSH that 6
@6
D=A
@4
A=M+D
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
