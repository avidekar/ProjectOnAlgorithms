/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maze;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ajay
 */
public class Maze {

    String sh = "";
    String sv = "";

    private int N;                 // dimension of maze
    private boolean[][] north;     // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;
    private int[][] pathNo;
    private String[][] hashPath;
    private int count = 0;

    public Maze(int n) {
        N = n;

        /*Drawing maze with all walls*/
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < n; j++) {
                if (i % 2 == 0) {
                    sh = sh + "+-";
//                System.out.print(sh);

                    if (j == n - 1) {
                        sh = sh + "+";
//                    System.out.print("+");
                    }
                } else if (i % 2 == 1) {
                    sv = sv + "| ";
//                    System.out.print(sv);
                    if (j == n - 1) {
                        sv = sv + "|";
//                        System.out.print("|");
                    }
                }

            }
            if (i % 2 == 0) {
                System.out.println(sh);
                sh = "";
            } else if (i % 2 == 1) {
                System.out.println(sv);
                sv = "";
            }

            if (i == 2 * n - 1) {
                for (int j = 0; j < n; j++) {
                    System.out.print("+-");
                    if (j == n - 1) {
                        System.out.print("+");
                    }
                }
            }

        } //End of drawing maze
        System.out.println();

        init();
        generate();
//        displayArrays();
        displayMaze();
        solve();
        displayDFSNos();
        System.out.println("DFS Path");
        displayPath();

        BFSPath();
        System.out.println("BFS Path");
        displayBFSNos();
        displayPath();

    }

    public int checkPath(int x, int y) {

        //if node goes to border values
//        System.out.print("The (x)(Y) is" + x + " " + y + "\n");
        if (x < 1 || y < 1 || x > N || y > N) {
            return 2;
        }
//        System.out.print("The (x)(Y) is" + x + " " + y + "\n");

        if (x == N && y == N) {
            visited[x][y] = false;
            count++;
            pathNo[x - 1][y - 1] = count;
            hashPath[x - 1][y - 1] = "#";
            return 1;
        }
        
        if (!visited[x][y]) {
            return 2;
        }
        

//        System.out.print("The (x)(Y) is" + x + " " + y + "\n");
        //else:
        System.out.print("The count is" + count + "\n");
        visited[x][y] = false;
        count++;
        pathNo[x - 1][y - 1] = count;
        hashPath[x - 1][y - 1] = "#";
        //System.out.print("The (x)(Y) is"+x+" "+y+"\n");

//        hashPath[x - 1][y - 1] = "#";
        return 0;
    }

    public void displayBFSNos() {
        /*displaying dfs pathNo traversal without walls*/
        System.out.println("test");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                System.out.print(pathNo[i][j]);
            }
            System.out.println();
        }

        /*Displaying with walls*/
        for (int k = 0, i = 1; k < 2 * N + 1; k++) {

            if (k % 2 == 0) {
                sh = "";
                for (int j = 1; j < north.length - 1; j++) {
                    if (north[i][j] == true) {
                        sh = sh + "+-";

                    } else {
                        sh = sh + "+ ";
                    }
                    if (j == north.length - 2) {
                        sh = sh + "+";
                    }
                }

                System.out.println(sh);

            } /*display vertical lines*/ else {
                sv = "";
                for (int j = 1; j < west.length - 1; j++) {
                    if (west[i][j] == true) {
                        sv = sv + "|" + pathNo[i - 1][j - 1];

                    } else {
                        sv = sv + " " + pathNo[i - 1][j - 1];
                    }
                    if (j == west.length - 2) {
                        if (east[i][j] == true) {
                            sv = sv + "|";
                        } else if (east[i][j] == false) {
                            sv = sv + "";
                        }
                    }

                }
                System.out.println(sv);

                i++;
            }

        }

    }

    public void BFSPath() {

        count = 0;
        /*setting visited to false for all nodes*/
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                visited[i][j] = true;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                pathNo[i][j] = 0;
            }
        }

        List<Integer> l1x = new LinkedList<Integer>();
        List<Integer> l2y = new LinkedList<Integer>();

        l1x.add(1);
        l2y.add(1);
        visited[1][1] = false;

        count++;
        pathNo[1 - 1][1 - 1] = count;
        hashPath[1 - 1][1 - 1] = "#";

        //System.out.print("first1");
        while (!l1x.isEmpty()) {
            int x = l1x.get(0);
            int y = l2y.get(0);
//            System.out.print("first" + l1x.get(0) + "," + l2y.get(0) + "north east south west" + north[x][y] + east[x][y] + south[x][y] + west[x][y]);
            l1x.remove(0);
            l2y.remove(0);
            if(checkPath(x, y)==1){
                break;
            }

            if (north[x][y] == false) {
//                System.out.println("wall not present \n");
                if (checkPath(x - 1, y) == 0) {
                    l1x.add(x - 1);
                    l2y.add(y);
                } else if (checkPath(x - 1, y) == 1) {
                    break;
                }
            }
            if (west[x][y] == false) {
//                System.out.println("wall not present \n");
                if (checkPath(x, y - 1) == 0) {
                    l1x.add(x);
                    l2y.add(y - 1);
                } else if (checkPath(x, y - 1) == 1) {
                    break;
                }

            }
            if (east[x][y] == false) {
//                System.out.println("wall not present \n");
                if (checkPath(x, y + 1) == 0) {
                    l1x.add(x);
                    l2y.add(y + 1);
                    //System.out.print("worked");
                } else if (checkPath(x, y + 1) == 1) {
                    break;
                }

            }
            if (south[x][y] == false) {
//                System.out.println("wall not present \n");
                if (checkPath(x + 1, y) == 0) {
                    l1x.add(x + 1);
                    l2y.add(y);
                } else if (checkPath(x + 1, y) == 1) {
                    break;
                }

            }

        }
    }

    public void displayPath() {
        /*displaying hashPath that excludes unneeded nodes*/
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(hashPath[i][j]);
            }
            System.out.println();
        }

        /*Displaying with walls*/
        for (int k = 0, i = 1; k < 2 * N + 1; k++) {

            if (k % 2 == 0) {
                sh = "";
                for (int j = 1; j < north.length - 1; j++) {
                    if (north[i][j] == true) {
                        sh = sh + "+-";

                    } else {
                        sh = sh + "+ ";
                    }
                    if (j == north.length - 2) {
                        sh = sh + "+";
                    }
                }

                System.out.println(sh);

            } /*display vertical lines*/ else {
                sv = "";
                for (int j = 1; j < west.length - 1; j++) {
                    if (west[i][j] == true) {
                        sv = sv + "|" + hashPath[i - 1][j - 1];

                    } else {
                        sv = sv + " " + hashPath[i - 1][j - 1];
                    }
                    if (j == west.length - 2) {
                        if (east[i][j] == true) {
                            sv = sv + "|";
                        } else if (east[i][j] == false) {
                            sv = sv + "";
                        }
                    }

                }
                System.out.println(sv);

                i++;
            }

        }

    }

    public void displayDFSNos() {
        /*displaying dfs pathNo traversal without walls*/
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(pathNo[i][j]);
            }
            System.out.println();
        }

        /*Displaying with walls*/
        for (int k = 0, i = 1; k < 2 * N + 1; k++) {

            if (k % 2 == 0) {
                sh = "";
                for (int j = 1; j < north.length - 1; j++) {
                    if (north[i][j] == true) {
                        sh = sh + "+-";

                    } else {
                        sh = sh + "+ ";
                    }
                    if (j == north.length - 2) {
                        sh = sh + "+";
                    }
                }

                System.out.println(sh);

            } /*display vertical lines*/ else {
                sv = "";
                for (int j = 1; j < west.length - 1; j++) {
                    if (west[i][j] == true) {
                        sv = sv + "|" + pathNo[i - 1][j - 1];

                    } else {
                        sv = sv + " " + pathNo[i - 1][j - 1];
                    }
                    if (j == west.length - 2) {
                        if (east[i][j] == true) {
                            sv = sv + "|";
                        } else if (east[i][j] == false) {
                            sv = sv + "";
                        }
                    }

                }
                System.out.println(sv);

                i++;
            }

        }

    }

    /*Solving using DFS*/
    public void solve() {
        /*setting visited to false for all nodes*/
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                visited[i][j] = false;
            }
        }

        //Starting point
        solve(1, 1);
    }

    public void solve(int x, int y) {

        //if node goes to border values
        if (x == 0 || y == 0 || x == N + 1 || y == N + 1) {
            return;
        }

        if (done || visited[x][y]) {
            return;
        }

        //else:
        visited[x][y] = true;
        count++;
        pathNo[x - 1][y - 1] = count;
        hashPath[x - 1][y - 1] = "#";

        //reached final room
        if (x == N && y == N) {
            done = true;
        }

        //else:
        if (!north[x][y]) {
            solve(x - 1, y);
        }
        if (!east[x][y]) {
            solve(x, y + 1);
        }
        if (!south[x][y]) {
            solve(x + 1, y);
        }
        if (!west[x][y]) {
            solve(x, y - 1);
        }

        if (done) {
            return;
        }

        /*Coz you cannot go forward from this node and it is not the destination!!
         So need to retreat..*/
        hashPath[x - 1][y - 1] = " ";

    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[N + 2][N + 2];
        for (int x = 0; x < N + 2; x++) {
            visited[x][0] = visited[x][N + 1] = true;
        }
        for (int y = 0; y < N + 2; y++) {
            visited[0][y] = visited[N + 1][y] = true;
        }

        // initialze all walls as present
        north = new boolean[N + 2][N + 2];
        east = new boolean[N + 2][N + 2];
        south = new boolean[N + 2][N + 2];
        west = new boolean[N + 2][N + 2];
        for (int x = 0; x < N + 2; x++) {
            for (int y = 0; y < N + 2; y++) {
                north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
            }
        }

        pathNo = new int[N][N];
        hashPath = new String[N][N];

        /*setting all deafaut hasPath values to empty string*/
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                hashPath[i][j] = " ";
            }
        }

    }

    public void displayArrays() {
        /*Remove entry point walls*/
        north[1][1] = south[0][1] = false;

        /*Remove exit point walls*/
        south[N][N] = north[N + 1][N] = false;

        /*NorthArray*/
        System.out.println("north");
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                System.out.print(north[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        /*SouthArray*/
        System.out.println("south");
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                System.out.print(south[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        /*WestArray*/
        System.out.println("West");
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                System.out.print(west[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        /*EastArray*/
        System.out.println("East");
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                System.out.print(east[i][j]);
            }
            System.out.println();
        }

    }

    public void displayMaze() {

        for (int k = 0, i = 1; k < 2 * N + 1; k++) {

            /*display horizontal lines*/
//            for (int i = 1; i < north.length - 1; i++) {
            if (k % 2 == 0) {
                sh = "";
                for (int j = 1; j < north.length - 1; j++) {
                    if (north[i][j] == true) {
                        sh = sh + "+-";

                    } else {
                        sh = sh + "+ ";
                    }
                    if (j == north.length - 2) {
                        sh = sh + "+";
                    }
                }

                System.out.println(sh);

            } /*display vertical lines*/ //            for (int i = 1; i < west.length - 1; i++) {
            else {
                sv = "";
                for (int j = 1; j < west.length - 1; j++) {
                    if (west[i][j] == true) {
                        sv = sv + "| ";

                    } else {
                        sv = sv + "  ";
                    }
                    if (j == west.length - 2) {
                        if (east[i][j] == true) {
                            sv = sv + "|";
                        } else if (east[i][j] == false) {
                            sv = sv + "";
                        }
                    }

                }
                System.out.println(sv);

                i++;
            }


            /*displaying last horizontal line using south maze..nt needed*/
//            if (k == 2 * N) {
//                sh = "";
//                for (int m = 1; m < south.length - 1; m++) {
//                    if (south[south.length - 1][m] == true) {
//                        sh = sh + "+-";
//                    } else if (south[south.length - 1][m] == false) {
//                        sh = sh + "+ ";
//                    }
//                    if (m == south.length - 2) {
//                        sh = sh + "+";
//                    }
//                }
//                System.out.println(sh);
//            }
        }

    }
//    }

    public void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y + 1] || !visited[x + 1][y] || !visited[x][y - 1] || !visited[x - 1][y]) {
            // pick random neighbor 
            while (true) {
                double r = Math.random();
                if (r < 0.25 && !visited[x][y + 1]) {
                    east[x][y] = west[x][y + 1] = false; //break east wall
                    generate(x, y + 1);
                    break;
                } else if (r >= 0.25 && r < 0.50 && !visited[x + 1][y]) {
                    south[x][y] = north[x + 1][y] = false; //break south wall
                    generate(x + 1, y);
                    break;
                } else if (r >= 0.5 && r < 0.75 && !visited[x][y - 1]) {
                    west[x][y] = east[x][y - 1] = false; //break west wall
                    generate(x, y - 1);
                    break;
                } else if (r >= 0.75 && r < 1.00 && !visited[x - 1][y]) {
                    north[x][y] = south[x - 1][y] = false; //break north wall
                    generate(x - 1, y);
                    break;
                }
            }
        }
    }

    public void generate() {
        generate(1, 1);
    }

    public static void main(String[] args) {
        int n = 4;
        Maze maze = new Maze(n);
    }
}
