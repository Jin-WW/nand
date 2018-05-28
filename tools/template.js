var fs = require('fs');
fs.writeFileSync('./output.txt', '');

function makeTemplate1(){
	for(var i = 0; i < 16; i++){
		var str = `Mux(a = a[${i}], b = b[${i}], sel = sel, out = out[${i}]);`;
		console.log(str);
	}
}

function makeTemplate2(){
	for(var i = 0; i < 16; i++){
		var str = `Mux(a = a[${i}], b = b[${i}], sel = sel, out = next-a-${i});
Mux(a = c[${i}], b = d[${i}], sel = sel, out = next-b-${i});
Mux(a = next-a-${i}, b = next-b-${i}, sel = sel, out = out[${i}]);\n`;
		fs.appendFileSync('./output.txt', str);
	}
}

makeTemplate2();