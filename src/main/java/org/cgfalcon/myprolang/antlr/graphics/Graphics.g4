grammar Graphics;

file: command+;

command: 'line' 'from' point 'to' point;

point: INT ',' INT;



// Lexcial Ruls

INT: '0'..'9'+;

WS: (' ' | '\t' | '\r' | '\n') {skip();};