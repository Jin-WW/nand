// Created by Xi Jin
// C_PUSH constant 10
@10
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP local 0
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@0
D=A
@1
D=M+D
@popSegmentAddress
M=D
@popSegmentValue
D=M
@popSegmentAddress
A=M
M=D
// C_PUSH constant 21
@21
D=A
@0
M=M+1;
A=M-1
M=D
// C_PUSH constant 22
@22
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP argument 2
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@2
D=A
@2
D=M+D
@popSegmentAddress
M=D
@popSegmentValue
D=M
@popSegmentAddress
A=M
M=D
// C_POP argument 1
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@1
D=A
@2
D=M+D
@popSegmentAddress
M=D
@popSegmentValue
D=M
@popSegmentAddress
A=M
M=D
// C_PUSH constant 36
@36
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP this 6
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@6
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
// C_PUSH constant 42
@42
D=A
@0
M=M+1;
A=M-1
M=D
// C_PUSH constant 45
@45
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP that 5
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@5
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
// C_POP that 2
@0
M=M-1;
A=M
D=M
@popSegmentValue
M=D
@2
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
// C_PUSH constant 510
@510
D=A
@0
M=M+1;
A=M-1
M=D
// C_POP temp 6
@0
M=M-1;
A=M
D=M
@11
M=D
// C_PUSH local 0
@0
D=A
@1
A=M+D
D=M
@0
M=M+1;
A=M-1
M=D
// C_PUSH that 5
@5
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
// C_PUSH argument 1
@1
D=A
@2
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
// C_PUSH this 6
@6
D=A
@3
A=M+D
D=M
@0
M=M+1;
A=M-1
M=D
// C_PUSH this 6
@6
D=A
@3
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
// arithmetic: sub
@0
M=M-1
A=M
D=M
@0
A=M-1
M=M-D
// C_PUSH temp 6
@11
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
