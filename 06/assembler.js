const path = require('path');
const fs = require('fs');

const lineSeparator = '\r\n';
const symbols = {
	'SCREEN': 16384,
	'KBD': 24576,
	'SP': 0,
	'LCL': 1,
	'ARG': 2,
	'THIS': 3,
	'THAT': 4,
	'WRITE': 18,
	'END': 22
}

const COMP = {
	'':      '1111111',
	'0':     '0101010',
	'1':     '0111111',
	'-1':    '0111010',
	'D':     '0001100',
	'A':     '0110000',
	'M':     '1110000',
	'!D':    '0001101',
	'!A':    '0110001',
	'!M':    '1110001',
	'D+1':   '0011111',
	'A+1':   '0110111',
	'M+1':   '1110111',
	'D-1':   '0001110',
	'A-1':   '0110010',
	'M-1':   '1110010',
	'D+A':   '0000010',
	'D+M':   '1000010',
	'D-A':   '0010011',
	'D-M':   '1010011',
	'A-D':   '0000111',
	'M-D':   '1000111',
	'D&A':   '0000000',
	'D&M':   '1000000',
	'D|A':   '0010101',
	'D|M':   '1010101'
};

const DEST = {
	'': '000',
	'M': '001',
	'D': '010',
	'MD': '011',
	'A': '100',
	'AM': '101',
	'AD': '110',
	'AMD': '111'
};

const JUMP = {
	'': '000',
	'JGT': '001',
	'JEQ': '010',
	'JGE': '011',
	'JLT': '100',
	'JNE': '101',
	'JLE': '110',
	'JMP': '111'
};

const reserved = {};

for(var i = 0; i <= 15; i++){
	symbols['R' + i] = i;
}

const fileName = process.argv[2];
if(!fileName.match(/.*\.asm$/g)){
	throw new Error('Wrong file postfix!!!');
}

const absolutePath = path.join(__dirname, fileName);

const file = fs.readFileSync(absolutePath).toString();
const lines = file.split(lineSeparator);

let output = [];
// console.log(lines)
let count = 0;
for(let i = 0; i < lines.length; i++){
	const line = lines[i];
	let trimmed = trimComment(line);

	if(trimmed.match(/^\s*$/)){
		continue;
	}
	else if(trimmed.match(/^\s*@\s*[_\.\w\d\$]+\s*$/)){ // A instruction
		const variableName = trimmed.replace(/[\s@]/g, '');
		if(variableName.match(/^[\d]+$/)){
			output.push('0' + toBinary(parseInt(variableName), 15));
			reserved[variableName] = true;
		}
		else if(symbols.hasOwnProperty(variableName)){
			// console.log(variableName, symbols[variableName])
			output.push('0' + toBinary(symbols[variableName], 15));
			reserved[symbols[variableName]] = true;
		}
		else {
			// noop on pre defined names
			output.push('@' + variableName);
		}
	}
	else if(trimmed.match(/^\s*\([\w\d\._\$]+\)\s*$/)){ // label
		const labelName = trimmed.replace(/[\s()]/g, '');
		symbols[labelName] = count;
		continue;
	}
	else if(trimmed.match(/^\s*([AMD]+=)?[AMD01\+\-!|&]+(;[A-Z]{3})?\s*$/)){
		// console.log(trimmed)
		trimmed = trimmed.replace(/\s/g, '');
		const equalSignLoc = find(trimmed, '=');
		const semicolonSignLoc = find(trimmed, ';');
		const dest = trimmed.slice(0, equalSignLoc == -1 ? 0 : equalSignLoc);
		const comp = trimmed.slice(equalSignLoc == -1 ? 0 : equalSignLoc + 1, semicolonSignLoc == -1 ? trimmed.length : semicolonSignLoc);
		const jump = trimmed.slice(semicolonSignLoc == -1 ? trimmed.length : semicolonSignLoc + 1, trimmed.length);
		output.push('111' + COMP[comp] + DEST[dest] + JUMP[jump]);
	}
	else{
		throw new Error(`Cannot parse ${trimmed}`);
	}
	count++;
}

let variableCounter = 16;
output = output.map(line => {
	if(find(line, '@') != -1){
		while(reserved[variableCounter]){
			variableCounter++;
		}
		const variableName = line.substring(1, line.length);
		let num = symbols[variableName];
		if(!num){//zero is taken anyway
			symbols[variableName] = variableCounter;
			num = variableCounter
		}

		return '0' + toBinary(num, 15);
	}
	return line;
})

//write back to the file location
// console.log(lines)
// console.log(output)
const outputPath = absolutePath.replace('.asm', '.hack');
// console.log(outputPath)
fs.writeFileSync(outputPath, output.join(lineSeparator));

function find(source, target){
	const len = target.length;
	let location = -1;
	for(let i = 0; i < source.length - len + 1; i++){
		let subStr = source.substring(i, i + len);
		if(subStr == target){
			location = i;
			break;
		}
	}
	return location;
}

function trimComment(line){
	let location = find(line, '//')
	if(location >= 0){
		return line.substring(0, location);
	}
	return line;
}

// convert binary
function toBinary(digits, length){
	let binary = '';
	while(digits > 0){
		binary = (digits % 2) + binary;
		digits /= 2;
		digits = Math.floor(digits);
	}

	if(length && digits.length > length){
		throw new Error('binary conversion overflows');
	}

	for(let l = binary.length; l < length; l++){
		binary = '0' + binary;
	}
	return binary;
}