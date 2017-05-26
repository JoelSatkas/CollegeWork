
;
;       Author : Joel Satkauskas 
;
;       File   : PROJECT.asm
;       
;       This program is designed to illustrate simple 80x86
;       instructions : (i.e. that access registers).

       

TITLE   Example1
.286
SSEG    SEGMENT PARA STACK 'STACK'         ;Stack segment
        DB      64 DUP('STACK   ')


SSEG    ENDS


DSEG    SEGMENT PARA PUBLIC 'DATA'         ;Code segment

DATA1		DW	?
CLUSTER1	DW	50,50,36,157,88,96,112,59,220,150,300,187,220,150,300,187,420,134,530,195,25,250,125,200,345,30,380,110,287,42,324,86,606,32,571,61,446,62,195,80,510,123,00
CLUSTER2	DW	25,65,56,187,65,140,92,64,198,138,329,173,211,176,284,205,439,126,527,165,45,232,114,229,327,47,365,134,259,30,354,116,585,57,594,84,427,83,173,64,540,106,00

		
DSEG    ENDS


CSEG    SEGMENT PARA PUBLIC 'CODE'
        ASSUME CS:CSEG, DS:DSEG, SS:SSEG


MAIN    PROC    FAR

        PUSH    DS                         ;These 5 lines are
        MOV     AX,0                       ;required for all .ASM
        PUSH    AX                         ;programs.
        MOV     AX,DSEG                    ;
        MOV     DS,AX                      ;


                                           ;The program begins here.

        MOV     AL,18
        MOV     AH,00H
        INT     10H

		CALL    DRAWSKY
		
        MOV     DX,300    ; SCREEN LINE
        MOV     CX,0      ; SCREEN COLUMN
        MOV     AL,9      ; COLOR
        MOV     SI,10
        MOV     DI,10		
        CALL    DRAWBUILDING1

        ;MOV     DX,300    ; SCREEN LINE
        MOV     CX,200    ; SCREEN COLUMN
        CALL    DRAWBUILDING1
		
		MOV     CX,300    ; SCREEN COLUMN
        CALL    DRAWBUILDING1

		MOV		DX,350
		MOV     CX,100    ; SCREEN COLUMN
        CALL    DRAWBUILDING2

		MOV     CX,500    ; SCREEN COLUMN
        CALL    DRAWBUILDING2
		
		
		MOV		DATA1,OFFSET CLUSTER1
		MOV		AL,15
		CALL	DRAWSTARCLUST1
		
		CALL	DRAWCLOSEBUTTON
		
		MOV		AX,01H
		INT		33H
		
		
		
AGAIN_MOUSE:

;		CX: screen column -> x coordinate
;		DX: Screen Line   -> y coordinate
		MOV		AX,05H
		MOV		BX,0H
		INT		33H
		CMP		BX,1
		JNE		AGAIN_MOUSE
		
		
		
CLICKED1:
		
		CMP		DX,0
		JL		CLICKED2
		CMP		DX,220
		JGE		CLICKED2
		CMP		CX,0
		JL		CLICKED2
		CMP		CX,614
		JGE		CLICKED2
		
		CALL	ANIMATION1
		JMP		AGAIN_MOUSE

CLICKED2:
		

		CMP		DX,247
		JL		CLICKED3
		CMP		DX,255
		JGE		CLICKED3
		CMP		CX,68
		JL		CLICKED3
		CMP		CX,76
		JGE		CLICKED3
		
		CALL	ANIMATION2
		JMP		AGAIN_MOUSE

CLICKED3:


		CMP		DX,5
		JL		AGAIN_MOUSE
		CMP		DX,25
		JGE		AGAIN_MOUSE
		CMP		CX,615
		JL		AGAIN_MOUSE
		CMP		CX,635
		JGE		AGAIN_MOUSE
		
		CALL	ANIMATION3
		JMP		CLICKED_EXIT


CLICKED_EXIT:

        MOV     AL,2
        MOV     AH,00H
        INT     10H

        RET                                ;The program ends here.

MAIN    ENDP

;-----------------------------------------------------------------------------
;Name: ANIMATION1
;
;Description: prints pixal on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;
;Output: Pixal to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------


ANIMATION1   PROC

		PUSH	AX
		

		MOV		DATA1,OFFSET CLUSTER1
		MOV		AX,02H
		INT		33H
		MOV		AL,1
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		MOV		DATA1,OFFSET CLUSTER2
		MOV		AX,02H
		INT		33H
		MOV		AL,15
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		MOV		DATA1,OFFSET CLUSTER2
		MOV		AX,02H
		INT		33H
		MOV		AL,1
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		MOV		DATA1,OFFSET CLUSTER1
		MOV		AX,02H
		INT		33H
		MOV		AL,15
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		MOV		DATA1,OFFSET CLUSTER1
		MOV		AX,02H
		INT		33H
		MOV		AL,1
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		MOV		DATA1,OFFSET CLUSTER2
		MOV		AX,02H
		INT		33H
		MOV		AL,15
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		MOV		DATA1,OFFSET CLUSTER2
		MOV		AX,02H
		INT		33H
		MOV		AL,1
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		MOV		DATA1,OFFSET CLUSTER1
		MOV		AX,02H
		INT		33H
		MOV		AL,15
		CALL	DRAWSTARCLUST1
		MOV		AX,01H
		INT		33H
		CALL	DELAY3
		
		POP		AX
RET
ANIMATION1  ENDP

;-----------------------------------------------------------------------------
;Name: ANIMATION2
;
;Description: prints pixal on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;
;Output: Pixal to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------


PROC	ANIMATION2

		PUSH	AX
		PUSH	CX
		PUSH	DX
		PUSH 	DI
		PUSH 	SI
		
;		CX: screen column -> x coordinate
;		DX: Screen Line   -> y coordinate
		
		MOV 	CX,68
		MOV 	DX,247
		MOV		DI,8
		MOV 	SI,8
		MOV		AX,02H
		INT		33H
		MOV		AL,14 
		CALL	DRAWBLOCK
		MOV		AX,01H
		INT		33H
		
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		
		MOV		AX,02H
		INT		33H
		MOV		AL,9
		CALL	DRAWBLOCK
		MOV		AX,01H
		INT		33H
		
		POP		SI
		POP		DI
		POP 	DX
		POP 	CX 
		POP 	AX 

RET
ANIMATION2	ENDP

;-----------------------------------------------------------------------------
;Name: ANIMATION3
;
;Description: prints pixal on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;
;Output: Pixal to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------
PROC	ANIMATION3

		PUSH	CX
		PUSH	DX
		PUSH	DI
		PUSH	SI

;		CX: screen column -> x coordinate
;		DX: Screen Line   -> y coordinate

		
		MOV		DX,5
		MOV		CX,615
		MOV		DI,20
		MOV		SI,20
		
		MOV		AX,02H
		INT		33H
		
		MOV		AL,4
		CALL	DRAWBLOCK
		
		ADD		DX,13
		ADD		CX,6
		MOV		AL,15
		MOV		SI,8
		MOV		DI,8
		CALL	DRAWUP45DEGREELINE

		ADD		CX,7
		CALL	DRAWDOWN45DEGREELINE
		
		MOV		AX,01H
		INT		33H
		
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		
		MOV		DX,5
		MOV		CX,615
		MOV		DI,20
		MOV		SI,20
		
		MOV		AX,02H	
		MOV		AL,12
		
		MOV		AL,12
		CALL	DRAWBLOCK
		
		ADD		DX,13
		ADD		CX,6
		MOV		AL,15
		MOV		SI,8
		MOV		DI,8
		CALL	DRAWUP45DEGREELINE

		ADD		CX,7
		CALL	DRAWDOWN45DEGREELINE
		
		MOV		AX,01H
		INT		33H
		
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		CALL	DELAY3
		
		POP		SI
		POP		DI
		POP		DX
		POP		CX


RET
ANIMATION3	ENDP

;-----------------------------------------------------------------------------
;Name: DELAY1
;
;Description: prints pixal on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;
;Output: Pixal to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------



DELAY1   PROC

        PUSH    CX
        PUSH    DX
        MOV     CX,2000D

GOAGAINDELAY:
        MOV     DX,0FFFFH
WHATEVERDELAY:
       
        DEC     DX
        JE      WHATEVERDELAY          ; JUMP IF DX = 0
        DEC     CX
        JE      FINISHDELAY
        JMP     GOAGAINDELAY
FINISHDELAY:
        POP     DX
        POP     CX

RET
DELAY1  ENDP





;-----------------------------------------------------------------------------
;Name: DELAY2
;
;Description: CREATES A DELAY
;
;Input:
;
;Output: DELAY
;
;Registers Currupted: None
;-----------------------------------------------------------------------------



DELAY2   PROC

		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		CALL	DELAY1
		
	
		
RET
DELAY2   ENDP

;-----------------------------------------------------------------------------
;Name: DELAY
;
;Description: CREATES A DELAY
;
;Input:
;
;Output: DELAY
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

PROC	DELAY3
	
		CALL	DELAY2
		CALL	DELAY2
		CALL	DELAY2
		CALL	DELAY2
		CALL	DELAY2
		CALL	DELAY2
		CALL	DELAY2
RET
ENDP	DELAY3
;-----------------------------------------------------------------------------
;Name: WRITEPIXAL
;
;Description: prints pixal on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;
;Output: Pixal to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

WRITEPIXAL PROC

           PUSH    AX
           PUSH    BX

           MOV     AH,0CH
           MOV     BH,0
           INT     10H

           POP     BX
           POP     AX



RET
WRITEPIXAL ENDP

;-----------------------------------------------------------------------------
;Name: DRAWHLINE
;
;Description: prints a row of pixels on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      SI: Length of line
;
;Output: Row of pixels to screen
;
;Registers Corrupted: None
;-----------------------------------------------------------------------------


DRAWHLINE PROC

          PUSHA
AGAINHLINE:    
          CMP   SI,00
          JE    FINISH_DHL
          CALL  WRITEPIXAL
          DEC   SI
          INC   CX
          JMP   AGAINHLINE

FINISH_DHL:

               POPA
RET
DRAWHLINE ENDP
;-----------------------------------------------------------------------------
;Name: DRAWVLINE
;
;Description: Draws a vertical line of pixals on page 0 in a video mode of 18
;             up
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      DI: hight of line
;
;Output: Pixals to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

DRAWVLINE PROC


          PUSH  AX
          PUSH  BX
          PUSH  DX
          PUSH  DI

AGAINVLINE:    
          CMP   DI,00
          JE    FINISH_DVL
          CALL  WRITEPIXAL
          DEC   DI
          INC   DX
          JMP   AGAINVLINE

FINISH_DVL:
          POP   DI
          POP   DX
          POP   BX
          POP   AX


RET
DRAWVLINE ENDP

;-----------------------------------------------------------------------------
;Name: DRAWBOX
;
;Description: Draws an empty box on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      SI: length of box
;      DI: hieght of box
;
;Output: Empty box to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

DRAWBOX PROC
           PUSH AX
           PUSH BX
           PUSH CX
           PUSH DI
           PUSH SI

           CALL DRAWHLINE
           CALL DRAWVLINE

           ADD  DX,DI
           CALL DRAWHLINE

           SUB  DX,DI
           ADD  CX,SI

           CALL DRAWVLINE


           POP  SI
           POP  DI
           POP  CX
           POP  BX
           POP  AX

        RET
DRAWBOX ENDP


;-----------------------------------------------------------------------------
;Name: DRAWBLOCK
;
;Description: Draws a solid box on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      SI: length of box
;      DI: hieght of box
;
;Output: Solid box to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------


PROC    DRAWBLOCK

        PUSH    AX
        PUSH    BX
        PUSH    CX
        PUSH    DX
        PUSH    SI
        PUSH    DI

AGAIN_BLOCK:

        CMP     SI,0
        JE      FINISH_BLOCK
        DEC     SI
        CALL    DRAWVLINE
        INC     CX
        JMP     AGAIN_BLOCK

FINISH_BLOCK:
        POP     DI
        POP     SI
        POP     DX
        POP     CX
        POP     BX
        POP     AX

RET
DRAWBLOCK ENDP



;-----------------------------------------------------------------------------
;Name: DRAWDOWN45DEGREELINE
;
;Description: Draws a 45 degree down line on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      SI: length of line
;
;Output: 45 degree line down to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------


PROC    DRAWDOWN45DEGREELINE

                PUSH    AX
                PUSH    BX
                PUSH    CX
                PUSH    DX
                PUSH    SI

AGAIN_DOWN45:
                CMP     SI,0
                JE      FINISH_DOWN45
                DEC     SI
                CALL    WRITEPIXAL
                DEC     CX
                DEC     DX
                JMP     AGAIN_DOWN45


FINISH_DOWN45:
                POP     SI
                POP     DX
                POP     CX
                POP     BX
                POP     AX


RET
DRAWDOWN45DEGREELINE      ENDP

               

;-----------------------------------------------------------------------------
;Name: DRAWUP45DEGREELINE
;
;Description: Draws a 45 degree line up on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      SI: length of line
;
;Output: 45 degree line up to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

PROC    DRAWUP45DEGREELINE

                PUSH    AX
                PUSH    BX
                PUSH    CX
                PUSH    DX
                PUSH    SI

AGAIN_UP45:
                CMP     SI,0
                JE      FINISH_UP45
                DEC     SI
                CALL    WRITEPIXAL
                INC     CX
                DEC     DX
                JMP     AGAIN_UP45


FINISH_UP45:
                POP     SI
                POP     DX
                POP     CX
                POP     BX
                POP     AX


RET
DRAWUP45DEGREELINE      ENDP

;-----------------------------------------------------------------------------
;Name: DRAWSKY
;
;Description: fills screen with solid coloron page 0 in a video mode of 18
;
;Input:
;      DX: Screen Line ; how far to draw
;      AL: Color Value
;      
;
;Output: solid color to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

PROC    DRAWSKY

                PUSH    CX
                PUSH    AX
                PUSH    BX
                PUSH    DX
                MOV     CX,0
                MOV     BX,480
                MOV     DX,0
				MOV		AL,1

;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      SI: Length of line



SKY_AGAIN:    
                CMP     CX,640
                JE      SKY_NEXTROW
                CALL    WRITEPIXAL
                INC     CX
                JMP     SKY_AGAIN


SKY_NEXTROW:
                CMP     DX,BX
                JE      SKY_FINISH
                INC     DX
                MOV     CX,0
                JMP     SKY_AGAIN

SKY_FINISH:
                POP     DX
                POP     BX
                POP     AX
                POP     CX
RET
DRAWSKY ENDP

;-----------------------------------------------------------------------------
;Name: DRAWROAD
;
;Description: fills screen with solid color on page 0 in a video mode of 18
;
;Input:
;      DX: Screen Line ; from where to draw
;      AL: Color Value
;      
;
;Output: solid color to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

PROC    DRAWROAD

        PUSH    AX
        PUSH    BX
        PUSH    CX
        PUSH    DX
        PUSH    SI
        PUSH    DI
        MOV     BX,480
        MOV     CX,0
        MOV     DX,400

ROAD_AGAIN:

        CMP     CX,640
        JE      ROAD_NEXTROW
        CALL    WRITEPIXAL
        INC     CX
        JMP     ROAD_AGAIN


ROAD_NEXTROW:

        CMP     DX,BX
        JE      ROAD_MARK
        INC     DX
        MOV     CX,0
        JMP     ROAD_AGAIN

ROAD_MARK:

        MOV     AL,14     ; COLOR
        MOV     DX,432    ; LINE
        MOV     SI,30     ; LENGTH
        MOV     DI,8      ; HIEGHT
        MOV     CX,0
        JMP     ROAD_MARKAGAIN



;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;      SI: length of box
;      DI: hieght of box

ROAD_MARKAGAIN:

         CMP    CX,600
         JAE    ROAD_FINISH
         ADD    CX,40
         CALL   DRAWBLOCK
         JMP    ROAD_MARKAGAIN

ROAD_FINISH:

        POP     DI
        POP     SI
        POP     DX
        POP     CX
        POP     BX
        POP     AX

RET
DRAWROAD        ENDP


;-----------------------------------------------------------------------------
;Name: DRAWROMBUS
;
;Description: DRAWS A SOLID ROMBUS ON page 0 in a video mode of 18
;
;Input:CX: screen column
;      DX: Screen Line ; from where to draw
;      AL: Color Value
;      SI: length of 45 degree line
;      DI: length of Horizontal line
;
;Output: solid color to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------



PROC    DRAWROMBUS

        PUSH    AX
        PUSH    BX
        PUSH    CX
        PUSH    DX
        PUSH    SI
        PUSH    DI

ROMBUS_AGAIN:

;Input:CX: screen column
;      DX: Screen Line ; from where to draw
;      AL: Color Value
;      SI: length of 45 degree line
;      DI: length of horizontal line

                
        CALL    DRAWUP45DEGREELINE
        DEC     DI
        JE      ROMBUS_FINISH
        INC     CX
        JMP     ROMBUS_AGAIN


ROMBUS_FINISH:

        POP     DI
        POP     SI
        POP     DX
        POP     CX
        POP     BX
        POP     AX

RET
DRAWROMBUS      ENDP

;-----------------------------------------------------------------------------
;Name: DRAWROMBUS2
;
;Description: DRAWS A SOLID ROMBUS ON page 0 in a video mode of 18
;
;Input:CX: screen column
;      DX: Screen Line ; from where to draw
;      AL: Color Value
;      SI: length of 45 degree line
;      DI: length of Horizontal line
;
;Output: solid color to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------



PROC    DRAWROMBUS2

        PUSH    AX
        PUSH    BX
        PUSH    CX
        PUSH    DX
        PUSH    SI
        PUSH    DI

ROMBUS_AGAIN2:

;Input:CX: screen column
;      DX: Screen Line ; from where to draw
;      AL: Color Value
;      SI: length of 45 degree line
;      DI: length of horizontal line

                
        CALL    DRAWDOWN45DEGREELINE
        DEC     DI
        JE      ROMBUS_FINISH2
        INC     CX
        JMP     ROMBUS_AGAIN2


ROMBUS_FINISH2:

        POP     DI
        POP     SI
        POP     DX
        POP     CX
        POP     BX
        POP     AX

RET
DRAWROMBUS2      ENDP



;-----------------------------------------------------------------------------
;Name: DRAWBUILDING1
;
;Description: DRAWS BUILDING ON page 0 in a video mode of 18
;
;Input:CX: screen column
;      DX: Screen Line ; from where to draw
;      AL: Color Value
;      
;
;Output: solid color to screen
;
;Registers Corrupted: None
;-----------------------------------------------------------------------------


;DX = Y COOR
;CX = X COOR
;AX = COLOR
;BX = 

PROC    DRAWBUILDING1

        PUSH    DI
        PUSH    SI
        PUSH    DX
        PUSH    CX
        PUSH    BX
        PUSH    AX
		PUSH	DATA1

        MOV     AL,8
        MOV     SI,80
        MOV     DI,400
        CALL    DRAWBLOCK

        MOV     AL,7
        CALL    DRAWHLINE


        DEC     DX
        MOV     DI,80
        MOV     AL,8
        MOV     SI,80
        CALL    DRAWROMBUS


             
;DX = Y COOR
;CX = X COOR

        MOV     DI,80
		MOV		AH,80
        SUB     DX,40
        ADD     CX,79
        MOV     AL,7


        SUB     DX,40
        INC     CX
        MOV     AL,1
        CALL    DRAWBLOCK

		ADD		DX,8 ;-----> wIndows	
		SUB		CX,12
		MOV		AL,9
		MOV		SI,8
		MOV		DI,8
		MOV		BX,DX
		MOV		DATA1,CX
		SUB		DATA1,60
		
DRAW_WINDOW_AGAIN:

;DX = Y COOR
;CX = X COOR
		
		ADD		DX,20
		CALL	DRAWBLOCK
		CMP		DX,640	
		JB		DRAW_WINDOW_AGAIN
		
		ADD		BX,20
		MOV		DX,BX
		SUB		CX,20
		CMP		CX,DATA1
		JGE		DRAW_WINDOW_AGAIN
		
		
		
DRAW_WINDOW_FINISH:
		POP		DATA1
        POP     AX
        POP     BX
        POP     CX
        POP     DX
        POP     SI
        POP     DI

RET
DRAWBUILDING1   ENDP

;-----------------------------------------------------------------------------
;Name: DRAWBUILDING2
;
;Description: DRAWS BUILDING ON page 0 in a video mode of 18
;
;Input:CX: screen column
;      DX: Screen Line ; from where to draw
;      AL: Color Value
;      
;
;Output: solid color to screen
;
;Registers Corrupted: None
;-----------------------------------------------------------------------------

PROC	DRAWBUILDING2
	
		PUSH    DI
        PUSH    SI
        PUSH    DX
        PUSH    CX
        PUSH    BX
        PUSH    AX
		PUSH	DATA1
		
		MOV     AL,8
        MOV     SI,80
        MOV     DI,400
        CALL    DRAWBLOCK

        MOV     AL,7
        CALL    DRAWHLINE
		
						;-----> wIndows	
		SUB		DX,5
		ADD		CX,4
		MOV		AL,9
		MOV		SI,8
		MOV		DI,8
		MOV		BX,DX
		MOV		DATA1,CX
		ADD		DATA1,80
		
DRAW_WINDOW_AGAIN2:

;DX = Y COOR
;CX = X COOR
		
		ADD		DX,20
		CALL	DRAWBLOCK
		CMP		DX,640	
		JB		DRAW_WINDOW_AGAIN2
		
		MOV		DX,BX
		ADD		CX,20
		CMP		CX,DATA1
		JB	DRAW_WINDOW_AGAIN2
		
		
		
;DX = Y COOR
;CX = X COOR

		POP		DATA1
        POP     AX
        POP     BX
        POP     CX
		POP		DX
		
		PUSH    DX
        PUSH    CX
        PUSH    BX
        PUSH    AX
		PUSH	DATA1
		
		DEC		DX			; ---first layer	
		MOV		AL,8
		MOV		SI,5
		MOV		DI,60
		CALL	DRAWROMBUS
		
		ADD		CX,20
		CALL	DRAWROMBUS2
		
		MOV		AL,7			;--- second layer
		SUB		DX,5
		SUB		CX,15
		CALL	DRAWROMBUS
		
		ADD		CX,10
		CALL	DRAWROMBUS2
		
		SUB		DI,10
		MOV		AL,8				; --- third layer
		SUB		DX,5
		SUB		CX,5
		CALL	DRAWROMBUS
		
		ADD		CX,10
		CALL	DRAWROMBUS2
		
		SUB		DI,10
		MOV		AL,7				; --- fourth layer
		SUB		DX,5
		SUB		CX,5
		CALL	DRAWROMBUS
		
		ADD		CX,10
		CALL	DRAWROMBUS2
		
		SUB		DI,10
		MOV		AL,8				; --- 5th layer
		SUB		DX,5
		SUB		CX,5
		CALL	DRAWROMBUS
		
		ADD		CX,10
		CALL	DRAWROMBUS2
		
		SUB		DI,10
		MOV		AL,7				; --- 5th layer
		SUB		DX,5
		SUB		CX,5
		CALL	DRAWROMBUS
		
		ADD		CX,10
		CALL	DRAWROMBUS2

		
		
;DX = Y COOR
;CX = X COOR
		
		SUB		DX,74
		ADD		CX,3
		MOV		DI,70
		MOV		SI,6
		MOV		AL,8
		CALL	DRAWBLOCK
		
		
		POP		DATA1
        POP     AX
        POP     BX
        POP     CX
        POP     DX
        POP     SI
        POP     DI

RET
DRAWBUILDING2	ENDP



;-----------------------------------------------------------------------------
;Name: DRAWSTAR
;
;Description: DRAWS BUILDING ON page 0 in a video mode of 18
;
;Input:		CX: screen column -> x coordinate
;      		DX: Screen Line   -> y coordinate
;      		AL: Color Value
;      
;
;Output: solid color to screen
;
;Registers Corrupted: None
;-----------------------------------------------------------------------------

PROC DRAWSTAR

        PUSH    DI
        PUSH    SI
        PUSH    DX
        PUSH    CX
        PUSH    AX

;		CX: screen column -> x coordinate
;    	DX: Screen Line   -> y coordinate


		;MOV		AL,15
		MOV		SI,9
		MOV		DI,9
		
		CALL	DRAWVLINE
		
		ADD		DX,4
		SUB		CX,4
		CALL 	DRAWHLINE
		
		ADD		DX,4
		ADD		CX,8
		
		CALL	DRAWDOWN45DEGREELINE
		
		SUB		CX,8
		CALL	DRAWUP45DEGREELINE
			
        POP     AX
        POP     CX
        POP     DX
        POP     SI
        POP     DI

RET
DRAWSTAR		ENDP

;-----------------------------------------------------------------------------
;Name: DRAWSTARCLUST
;
;Description: DRAWS BUILDING ON page 0 in a video mode of 18
;
;Input:		CX: screen column -> x coordinate
;      		DX: Screen Line   -> y coordinate
;      		AL: Color Value
;      
;
;Output: solid color to screen
;
;Registers Corrupted: None
;-----------------------------------------------------------------------------

PROC	DRAWSTARCLUST1

		PUSH	BX
	    PUSH    DX
        PUSH    CX
		PUSH	DATA1
    

		MOV     BX, DATA1
		

DRAW_STAR_AGAIN:

		MOV		CX,[BX]
		INC		BX
		INC		BX
		MOV		DX,[BX]
		INC		BX
		INC		BX
		CALL	DRAWSTAR
		CMP		[BX],00
		JNE		DRAW_STAR_AGAIN
  
		POP		DATA1	
	    POP     CX
		POP     DX
		POP		BX
RET
DRAWSTARCLUST1	ENDP

;-----------------------------------------------------------------------------
;Name: DRAWCLOSEBUTTON
;
;Description: prints pixal on page 0 in a video mode of 18
;
;Input:DX: Screen Line
;      CX: Screen Column
;      AL: Color Value
;
;Output: Pixal to screen
;
;Registers Currupted: None
;-----------------------------------------------------------------------------

PROC	DRAWCLOSEBUTTON

		PUSH	CX
		PUSH	DX
		PUSH	DI
		PUSH	SI

;		CX: screen column -> x coordinate
;		DX: Screen Line   -> y coordinate

		
		MOV		DX,5
		MOV		CX,615
		MOV		DI,20
		MOV		SI,20
		MOV		AL,12
		CALL	DRAWBLOCK
		
		ADD		DX,13
		ADD		CX,6
		MOV		AL,15
		MOV		SI,8
		MOV		DI,8
		CALL	DRAWUP45DEGREELINE

		ADD		CX,7
		CALL	DRAWDOWN45DEGREELINE
		
		POP		SI
		POP		DI
		POP		DX
		POP		CX

RET
ENDP	DRAWCLOSEBUTTON

CSEG    ENDS
        END     MAIN
