; 6 rejestrów 0, 1, 2, 3, 4, 5 == 32-bit
; 00 Z rA ; A = 0
; 01 S rA ; A += 1
; 10 T rA, rB ; rB = rA
; 20 I rA, rB, rC ; if rA == rB then go to rC
; 7f EXIT
; 6f IN

%define EXIT db 0x7f
%define IN(a) db 0x6f, a
%define OUT(a) db 0x5f, a
%define Z(a) db 0, a
%define S(a) db 1, a
%define T(a, b) db 0x10, a, b
; %define I(a, b, q) db 0x20, a, b, q

%macro I 3
db 0x20, %1, %2
db (%3 - ($ - 3))
%endmacro


IN(1)
IN(2)
start:
I 1, 2, end
S(2)
S(3)
I 1, 2, end
I 1, 1, start
end:
T(3, 0)
OUT(1)
OUT(2)
OUT(3)
EXIT
