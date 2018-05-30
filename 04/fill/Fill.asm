// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Toggle R0 to set color
@color
M=0 //initially white

(MAIN)
@24576
D=M;
@BLACK
D;JNE // select black
//select white
@new-color
M=0
(COLOR-SELECTED)

@new-color
D=M
@color
D=D-M
@RENDER
D;JNE

// infinite loop
@MAIN
0;JMP

(BLACK)
@new-color
M=-1
@COLOR-SELECTED
0;JMP

(RENDER)
@new-color
D=M
@color
M=D
@i
M=0
(RENDER-LOOP)
@i
D=M
@8192
D=D-A
@MAIN
D;JGE

@i
D=M
@SCREEN
D=D+A
@location
M=D
@color
D=M
@location
A=M
M=D
@i
M=M+1
@RENDER-LOOP
0;JMP



