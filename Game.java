package gameclass;

import java.util.LinkedList;
import gamestate.*;

public class Game {
    public Game pai;
    public int h;                //Valor da heuristica
    public int d;                //Profundidade do nó
    public char lastChar;
    public int lastMove;

    char[][] data;
    int i0;               //Localização do zero
    boolean empty;

    public Game() {
        data = new char[6][7];
        for(int i=0; i<6; i++) {
            for(int j=0; j<7;j++) {
                data[i][j] = '-';
            }
        }
        lastChar = 'O';
        empty = true;
        h = 0;
        d = 0;
    }

    public Game(char[][] data, char lastChar, int depth) {
        this.data = new char[6][7];
        for(int i=0; i<6; i++) {
            for(int j=0; j<7;j++) {
                this.data[i][j] = data[i][j];
            }
        }
        this.lastChar = lastChar;
        this.empty = false;
        Eval();
        this.d = depth;
    }



    void SwitchChar() {
        lastChar = lastChar=='O'?'X':'O';
    }

    public void MakeMove(int move) { //D = [1-7]
        lastMove = move;

        move--;
        if(move > 6 || move <0 ) {
            System.out.println("Can't make move!");
            return;
        }

        if(data[5][move] != '-') {
            System.out.println("Can't make move!");
            return;
        }

        for(int i=0; i<6; i++) {
            if(data[i][move] == '-') {
                SwitchChar();
                data[i][move] = lastChar;
                break;
            }
        }


        Eval();
        this.d++;
        empty = false;
    }

    void Eval() {
        h = 0;
        for (int i=0; i<6; i++) {  // Linhas
            h += EvalLine(i, 0, 0, 1);
        }
        for (int i=0; i<7; i++) {  // Colunas
            h += EvalLine(0, i, 1, 0);
        }


        for (int i=1; i<=2; i++) {  // diagonais direitas
            h += EvalLine(i, 0, 1, 1);
        }
        for (int i=0; i<=3; i++) {
            h += EvalLine(0, i, 1, 1);
        }


        for (int i=1; i<=2; i++) {  // diagonais esquerdas
            h += EvalLine(i, 6, 1, -1);
        }
        for (int i=3; i<=6; i++) {
            h += EvalLine(0, i, 1, -1);
        }

        if(empty) {
            if(lastChar=='X') {
                h -= 16;
            }
            else {
                h += 16;
            }
            empty = false;
            return;
        }

        if(lastChar=='X') {
            h -= 16;
        }
        else {
            h += 16;
        }

    }

    static int EvalCount(int count, char ch) {
        int hh = 0;
        if(count == 0) return 0;
        if(count >= 4) hh = 512;
        else if(count == 1) hh = 1;
        else if(count == 2) hh = 10;
        else hh = 50; // count == 3
        int ans = (ch=='X')?hh:hh*(-1);
        return ans;
    }

    int EvalLine(int x, int y, int incx, int incy) {
        int count = 0;
        int deltaH = 0;
        char ch = '-';
        while(x >= 0 && y>= 0 && x <6 && y <7) {
            if(data[x][y] == '-') {
                if(count > 0) {
                    deltaH += EvalCount(count, ch);
                }
                ch ='-';
                count = 0;
            }
            else {
                if(ch == data[x][y]) count++;
                else {
                    deltaH += EvalCount(count, ch);
                    ch = data[x][y];
                    count = 1;
                }
            }
            if(count == 4) return EvalCount(count,ch);
            x += incx;
            y += incy;
            // System.out.println("h: " + h);
        }
        deltaH += EvalCount(count, ch);
        return deltaH;
    }

    char verify( int x, int y, int incx, int incy) {
        int count = 0;
        char ch = '-';
        while(x >= 0 && y>= 0 && x <6 && y <7) {
            if(data[x][y] == '-') {
                ch ='-';
                count = 0;
            }
            else {
                if(ch == data[x][y]) count ++;
                else {
                    ch = data[x][y];
                    count = 1;
                }
            }
            if(count == 4) return ch;
            x += incx;
            y += incy;
            // System.out.println("(" + x + "," + y + "); count = " +count);
        }
        return '-';
    }

    GameState CharToState(char ch) {
        switch (ch) {
            case 'X': return GameState.XWON;
            case 'O': return GameState.OWON;
            default: return GameState.ONGOING;  //dont use
        }
    }

    // Verifica o estado do jogo
    public GameState State() {
        for (int i=0; i<6; i++) {  // Linhas
            char ch = verify(i, 0, 0, 1);
            if(ch != '-') return CharToState(ch);
        }

        for (int i=0; i<7; i++) {  // Colunas
            char ch = verify(0, i, 1, 0);
            if(ch != '-') return CharToState(ch);
        }


        for (int i=1; i<=2; i++) {  // diagonais direitas
            char ch = verify(i, 0, 1, 1);
            if(ch != '-') return CharToState(ch);
        }
        for (int i=0; i<=3; i++) {
            char ch = verify(0, i, 1, 1);
            if(ch != '-') return CharToState(ch);
        }


        for (int i=1; i<=2; i++) {  // diagonais esquerdas
            char ch = verify(i, 6, 1, -1);
            if(ch != '-') return CharToState(ch);
        }
        for (int i=3; i<=6; i++) {
            char ch = verify(0, i, 1, -1);
            if(ch != '-') return CharToState(ch);
        }


        if(Full()) return GameState.STALEMATE;
        return GameState.ONGOING;
    }

    public boolean Full() { //Is the board full
        for(int i=0; i<7; i++) {
            if(data[5][i] == '-') return false;
        }
        return true;
    }

    public void End(GameState state) {
        if(state == GameState.ONGOING) {
            System.out.println("Tried to end with wrong state");
            return;
        }
        switch(state) {
            case STALEMATE: System.out.println("Game ended without winner!");
            break;
            case XWON: System.out.println("X won!");
            break;
            case OWON: System.out.println("O won!");
            break;
        }
    }

    public LinkedList<Integer> PossibleMoves() {
        LinkedList<Integer> moves = new LinkedList<Integer>();
        for(int i = 0; i<7; i++) {
            if(data[5][i] == '-') moves.add(i+1);
        }
        return moves;
    }

    public LinkedList<Game> Children() {
        LinkedList<Integer> moves = PossibleMoves();
        LinkedList<Game> childs = new LinkedList<Game>();
        for(int move : moves) {
            Game g = new Game(this.data, this.lastChar, this.d);
            g.pai = this;
            g.MakeMove(move);
            childs.add(g);
        }
        return childs;
    }


    public int RandomMove() {
        LinkedList<Integer> moves = PossibleMoves();
        return moves.get((int)(Math.random() * moves.size()));
    }

    public void PrintPath() {
        LinkedList<Integer> movesPath = new LinkedList<>();
        Game g = this;
        while(g.pai != null) {
            movesPath.add(g.lastMove);
            g = g.pai;
        }
        movesPath.add(lastMove);

        System.out.print("PATH:  ");
        for(int i=movesPath.size()-1; i>=0; i--) {
            System.out.print(movesPath.get(i) + "  ");
        }
        System.out.println();
    }



    // int AlfaBetaComputerMove() {
    //     Game bestOutcome = AlphaBeta_decision(this, 'O');
    //     bestOutcome.PrintPath();
    //     return FirstMove(bestOutcome);
    // }

    public int FirstMove(Game bestOutcome) {
        while(bestOutcome.pai != this) {
            bestOutcome = bestOutcome.pai;
        }
        return bestOutcome.lastMove;
    }

    public void PrintBoard() {
        // System.out.println("Heuristic value: " + h);
        for(int i=5; i>=0; i--) {
            for(int j=0; j<7;j++) {
                System.out.print(data[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        for(int j=1; j<=7;j++) System.out.print(j+" ");
        System.out.println();
        System.out.println();
    }



}
