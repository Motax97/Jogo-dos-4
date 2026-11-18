import java.util.Scanner;
import java.util.LinkedList;
import java.util.Timer;
import algs.*;
import gameclass.*;
import gamestate.*;


public class JogoDos4 {

    static Scanner sc;

    static int HumanMove() {
        int move = sc.nextInt();
        while(move <1 || move >7) {
            System.out.println("Can't make move!");
            move = sc.nextInt();
        }
        return move;
    }

    public static void MiniMax1v1() {
        Game g = new Game();

        g.PrintBoard();
        boolean playerTurn = true; //todo: change?
        while(true) {
            int move;
            if(playerTurn) {
                move = Algs.Player2MiniMax(g);
                playerTurn = false;
            }
            else {
                move = Algs.MiniMaxComputerMove(g);
                playerTurn = true;
            }
            g.MakeMove(move);
            GameState state = g.State();
            g.PrintBoard();
            if(state == GameState.XWON || state == GameState.OWON) {
                g.End(state);
                break;
            }
        }
    }

    public static void Alpha1v1() {
        Game g = new Game();
        Scanner sc = new Scanner(System.in);

        g.PrintBoard();
        boolean playerTurn = true; //todo: change?
        while(true) {
            int move;
            if(playerTurn) {
                move = Algs.Player2AlphaBeta(g); //g.RandomMove();
                playerTurn = false;
            }
            else {
                move = Algs.AlphaBetaComputerMove(g);
                playerTurn = true;
            }
            g.MakeMove(move);
            GameState state = g.State();
            g.PrintBoard();
            if(state == GameState.XWON || state == GameState.OWON) {
                g.End(state);
                break;
            }
        }
    }



    static int GetMoveFromPlayer(Game g, int player, boolean player1) {
        switch (player) {
            case 1:
            return HumanMove();
            case 2:
            return player1?Algs.Player2MiniMax(g):Algs.MiniMaxComputerMove(g);
            case 3:
            return player1?Algs.Player2AlphaBeta(g):Algs.AlphaBetaComputerMove(g);
        }
        return 0;
    }

    static void PlayGame(int p1, int p2) {
        Game g = new Game();

        g.PrintBoard();
        boolean playerTurn = true;
        while(true) {
            int move;
            if(playerTurn) {
                move = GetMoveFromPlayer(g, p1, true);
                playerTurn = false;
            }
            else {
                move = GetMoveFromPlayer(g, p2, false);
                playerTurn = true;
            }
            g.MakeMove(move);
            GameState state = g.State();
            g.PrintBoard();
            if(state == GameState.XWON || state == GameState.OWON) {
                g.End(state);
                break;
            }
        }
    }



    public static void main(String[] args) {
        sc = new Scanner(System.in);


        System.out.println("Welcome to Connect 4");
        System.out.println("");
        System.out.println("");
        System.out.println("  Please select Player1:");
        System.out.println("   1: Human");
        System.out.println("   2: MiniMax (Computer)");
        System.out.println("   3: AlphaBeta (Computer)");
        System.out.println("");

        int p1 = sc.nextInt();
        if(p1 <=0 || p1 >3) {
            System.out.println("You're messing with me!");
            System.out.println("");
            return;
        }


        System.out.println("");
        System.out.println("");
        System.out.println("  Please select Player2:");
        System.out.println("   1: Human");
        System.out.println("   2: MiniMax (Computer)");
        System.out.println("   3: AlphaBeta (Computer)");
        System.out.println("");
        int p2 = sc.nextInt();
        System.out.println("");
        System.out.println("");
        if(p2 <=0 || p2 >3) {
            System.out.println("You're messing with me!");
            System.out.println("");
            return;
        }

        PlayGame(p1, p2);

    }
}
