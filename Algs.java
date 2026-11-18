package algs;

import gameclass.*;
import gamestate.*;
import java.util.LinkedList;

public class Algs {
    public static int totalCount;
    public static int count;
    public static int turn;
    //MINIMAX

    public static int MiniMaxComputerMove(Game g) {
        Game bestOutcome = MiniMax_decision(g, 'O');
        // bestOutcome.PrintPath();
        return g.FirstMove(bestOutcome);
    }

    public static int Player2MiniMax(Game g) {
        Game bestOutcome = Algs.MiniMax_decision(g, 'X');
        // bestOutcome.PrintPath();
        return g.FirstMove(bestOutcome);
    }

    public static Game MiniMax_decision(Game n, char playerChar) {
        count = 0;
        int depthMax = 7 + n.d;
        n.pai = null;
        LinkedList<Game> childrens = n.Children();
        //n.pai = null
        Game best = null;
        for(Game child : childrens) {
            Game g = MiniMax_value(child, depthMax, playerChar);
            // System.out.println("  DEBUG: child " + child.lastMove + " bestfromchild.h: " + g.h);
            if(best == null) {
                best = g;
            }
            if(playerChar == 'O') {
                if(best.h > g.h) best = g;
            }
            else {
                if(best.h < g.h) best = g;
            }
        }

        turn++;
        count ++;
        count ++;
        totalCount += count;
        return best;
    }

    static Game MiniMax_value(Game n, int maxDepth, char playerChar) {
        count ++;
        if(n.d > maxDepth) return n;

        LinkedList<Game> childrens = n.Children();

        if(childrens.size() < 2) return n;

        Game best = childrens.pollLast();
        GameState state = best.State();
        if(state == GameState.OWON || state == GameState.XWON) return best;
        for(Game child : childrens) {
            state = child.State();
            if(state == GameState.OWON || state == GameState.XWON) return child;
            if(n.lastChar != playerChar) {
                //min
                Game bestSubtreeChild = MiniMax_value(child, maxDepth, playerChar);
                if(best.h >= bestSubtreeChild.h) {
                    best = bestSubtreeChild;
                }
            }
            else {
                //max
                Game bestSubtreeChild = MiniMax_value(child, maxDepth, playerChar);
                if(best.h <=  bestSubtreeChild.h) {
                    best = bestSubtreeChild;
                }
            }
        }
        return best;
    }




    //ALPHABETA
    public static int AlphaBetaComputerMove(Game g) {
        Game bestOutcome = AlphaBeta_decision(g, 'O');
        // bestOutcome.PrintPath();
        return g.FirstMove(bestOutcome);
    }

    public static int Player2AlphaBeta(Game g) {
        Game bestOutcome = AlphaBeta_decision(g, 'X');
        // bestOutcome.PrintPath();
        return g.FirstMove(bestOutcome);
    }

    public static Game AlphaBeta_decision(Game n, char playerChar) {
        count = 0;
        int depthMax = 7 + n.d;
        n.pai = null;
        LinkedList<Game> childrens = n.Children();
        //n.pai = null
        Game best = null;
        for(Game child : childrens) {
            Game g = AlphaBeta_value(child, depthMax, Integer.MIN_VALUE, Integer.MAX_VALUE, playerChar);
            // System.out.println("  DEBUG: child " + child.lastMove + " bestfromchild.h: " + g.h);
            if(best == null) {
                best = g;
            }
            if(playerChar == 'O') {
                if(best.h > g.h) best = g;
            }
            else {
                if(best.h < g.h) best = g;
            }
        }
        turn++;
        count ++;
        totalCount += count;
        return best;
    }

    static Game AlphaBeta_value(Game n, int maxDepth, int alpha, int beta, char playerChar) {
        count ++;
        if(n.d > maxDepth) return n;

        LinkedList<Game> childrens = n.Children();

        if(childrens.size() < 2) return n;

        Game best = childrens.pollLast();
        GameState state = best.State();
        if(state == GameState.OWON || state == GameState.XWON) return best;
        for(Game child : childrens) {
            state = child.State();
            if(state == GameState.OWON || state == GameState.XWON) return child;
            if(n.lastChar != playerChar) {
                //min
                Game bestSubtreeChild = AlphaBeta_value(child, maxDepth, alpha, beta, playerChar);
                if(best.h >= bestSubtreeChild.h) {
                    best = bestSubtreeChild;
                }
                beta = Math.min(beta, bestSubtreeChild.h);
                if(beta <= alpha ) {
                    break;
                }
            }
            else {
                //max
                Game bestSubtreeChild = AlphaBeta_value(child, maxDepth, alpha, beta, playerChar);
                if(best.h <= bestSubtreeChild.h) {
                    best = bestSubtreeChild;
                }
                alpha = Math.max(alpha, bestSubtreeChild.h);
                if(alpha >= beta) {
                    break;
                }
            }
        }
        return best;
    }


}
