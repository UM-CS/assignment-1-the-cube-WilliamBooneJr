/* Billy Boone
 This program simulated a rubiks cube. 
 The user can input moves to rotate the cube and the program will display the cube after each move.
 The user can also input 'S' to display the solution to the moves they have made.
 The user can input 'RD' to randomize the cube.
 The user can input 'Q' to quit the program.
 The program will display the cube after each move and the user can input moves until they quit the program.
 */
package com.example.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Cube {

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        String input;

        // Initialize or reset the cube to the starting state if needed
        printTCube(numCube);

        while (true) {
            System.out.println("\nEnter your move (F, F', R, R', U, U', L, L', B, B', D, D', S for solution, RD to randomize) or 'Q' to quit:");
			input = sc.nextLine().toUpperCase();
            if ("Q".equals(input)) {
                System.out.println("Exiting game...");
                break;
            }
			if ("S".equals(input)) {
    		printSolution();
    		continue;}

 			else if ("RD".equals(input)) {
    		randomizeCube();
    		printTCube(numCube);
    		continue;}

            // Check if the input is a valid move
            if (isValidMove(input)) {
                makeMove(input);
				moveHistory.add(input);
				System.out.println();
                printTCube(numCube);
            } else {
                System.out.println("Invalid move. Please enter a valid move or 'Q' to quit.");
            }
        }

        sc.close();
    }

	
	// A Cube with numbers
	static String[][] numCube = {
		{null, null, null, "w1", "w2", "w3", null, null, null},//0
		{null, null, null, "w4", "w5", "w6", null, null, null},//1
		{null, null, null, "w7", "w8", "w9", null, null, null},//2
	
		{"o1", "o2", "o3", "b1", "b2", "b3", "r1", "r2", "r3"},//3
		{"o4", "o5", "o6", "b4", "b5", "b6", "r4", "r5", "r6"},//4
		{"o7", "o8", "o9", "b7", "b8", "b9", "r7", "r8", "r9"},//5
	
		{null, null, null, "y1", "y2", "y3", null, null, null},//6
		{null, null, null, "y4", "y5", "y6", null, null, null},//7
		{null, null, null, "y7", "y8", "y9", null, null, null},//8
	
		{null, null, null, "g1", "g2", "g3", null, null, null},//9    
		{null, null, null, "g4", "g5", "g6", null, null, null},//10    
		{null, null, null, "g7", "g8", "g9", null, null, null}//11     
	};

	static void printSolution() {
		Collections.reverse(moveHistory); // Reverse the order of moves
		for (String move : moveHistory) {
			// For each move, print its inverse
			System.out.print(invertMove(move) + " ");
		}
		System.out.println();
	}
	
	static String invertMove(String move) {
		if (move.endsWith("'")) return move.substring(0, move.length() - 1);
		else return move + "'";
		}
	
	// Define a global variable to track moves
	static List<String> moveHistory = new ArrayList<>();
		

	static void randomizeCube() {
		String[] possibleMoves = {"F", "F'", "B", "B'", "L", "L'", "R", "R'", "U", "U'", "D", "D'"};
		Random random = new Random();
		for (int i = 0; i < 20; i++) { // Make 20 random moves
			int moveIndex = random.nextInt(possibleMoves.length);
			String move = possibleMoves[moveIndex];
			makeMove(move);
			System.out.println("Random move: " + move);
			moveHistory.add(move); // Track the random move
		}}
		
	static int getFaceIndex(String move) {
		// Remove the ' from the move to get the face letter
		String faceLetter = move.replaceAll("[']", "");
		
		switch (faceLetter) {
			case "F":
				return 0; // Front face
			case "B":
				return 1; // Back face
			case "L":
				return 2; // Left face
			case "R":
				return 3; // Right face
			case "U":
				return 4; // Up face
			case "D":
				return 5; // Down face
			default:
				System.out.println("Invalid move: " + move);
				return -1; // Invalid index
		}
	}
	private static boolean isValidMove(String move) {
        // Implement a method to check if the given move is valid
        // Example check: return move.matches("^[FBLRUD]'?$");
        // This regex checks for single characters F,B,L,R,U,D optionally followed by a prime (')
        // Adjust according to your application's needs
        return move.matches("^[FBLRUD]'?$") || move.matches("^[FBLRUD]2$");
    }
	
	static boolean isClockwise(String move) {
		// If the move contains a ' character, it is clockwise
		return !move.contains("'");
	}

	static void printTCube(String[][] theCube) {
		for (int i = 0; i < theCube.length; i++) {
			for (int j = 0; j < theCube[i].length; j++) {
				if (theCube[i][j] == null) {
					System.out.print("   ");
				} else {
					System.out.print(theCube[i][j] + " ");
				}
			}
			System.out.println();
		}
	}

	static void rotateFaceClockwise(String[][] cube, int faceIndex) {
		// Define the starting row and column based on the faceIndex
		int startRow = 0, startCol = 0;
		switch (faceIndex) {
			case 0: startRow = 3; startCol = 3; break; // Front
			case 1: startRow = 9; startCol = 3; break; // Back
			case 2: startRow = 3; startCol = 0; break; // Left
			case 3: startRow = 3; startCol = 6; break; // Right
			case 4: startRow = 0; startCol = 3; break; // Up
			case 5: startRow = 6; startCol = 3; break; // Down
		}
	    if (faceIndex != 1) {
			// Rotate corners
			String temp = cube[startRow][startCol];
			cube[startRow][startCol] = cube[startRow + 2][startCol];
			cube[startRow + 2][startCol] = cube[startRow + 2][startCol + 2];
			cube[startRow + 2][startCol + 2] = cube[startRow][startCol + 2];
			cube[startRow][startCol + 2] = temp;
		
			// Rotate edges
			temp = cube[startRow][startCol + 1];
			cube[startRow][startCol + 1] = cube[startRow + 1][startCol];
			cube[startRow + 1][startCol] = cube[startRow + 2][startCol + 1];
			cube[startRow + 2][startCol + 1] = cube[startRow + 1][startCol + 2];
			cube[startRow + 1][startCol + 2] = temp;
		}
		// Adjust for back face rotation
		else if (faceIndex == 1) {
			// Temporary variables to hold the values that will be moved around
			String temp1, temp2;

			// Corners
			temp1 = cube[9][3]; // g1
			cube[9][3] = cube[11][3]; // Move g7 to g1
			cube[11][3] = cube[11][5]; // Move g9 to g7
			cube[11][5] = cube[9][5]; // Move g3 to g9
			cube[9][5] = temp1; // Move g1 to g3
		
			// Edges
			temp2 = cube[9][4]; // g2
			cube[9][4] = cube[10][3]; // Move g4 to g2
			cube[10][3] = cube[11][4]; // Move g8 to g4
			cube[11][4] = cube[10][5]; // Move g6 to g8
			cube[10][5] = temp2; // Move g2 to g6
		
			// Center remains the same (g5)
		}
	}

	static void rotateFaceCounterClockwise(String[][] cube, int faceIndex) {
		// Define the starting row and column based on the faceIndex
		int startRow = 0, startCol = 0;
		switch (faceIndex) {
			case 0: startRow = 3; startCol = 3; break; // Front
			case 1: startRow = 9; startCol = 3; break; // Back
			case 2: startRow = 3; startCol = 0; break; // Left
			case 3: startRow = 3; startCol = 6; break; // Right
			case 4: startRow = 0; startCol = 3; break; // Up
			case 5: startRow = 6; startCol = 3; break; // Down
		}
	
		// Rotate corners counter-clockwise
		String temp = cube[startRow][startCol];
		cube[startRow][startCol] = cube[startRow][startCol + 2];
		cube[startRow][startCol + 2] = cube[startRow + 2][startCol + 2];
		cube[startRow + 2][startCol + 2] = cube[startRow + 2][startCol];
		cube[startRow + 2][startCol] = temp;
	
		// Rotate edges counter-clockwise
		temp = cube[startRow][startCol + 1];
		cube[startRow][startCol + 1] = cube[startRow + 1][startCol + 2];
		cube[startRow + 1][startCol + 2] = cube[startRow + 2][startCol + 1];
		cube[startRow + 2][startCol + 1] = cube[startRow + 1][startCol];
		cube[startRow + 1][startCol] = temp;
	
		// Adjust for back face rotation
		if (faceIndex == 1) {
			// Invert corners
			temp = cube[9][3];
			cube[9][3] = cube[9][5];
			cube[9][5] = cube[11][5];
			cube[11][5] = cube[11][3];
			cube[11][3] = temp;
	
			// Invert edges
			temp = cube[9][4];
			cube[9][4] = cube[10][5];
			cube[10][5] = cube[11][4];
			cube[11][4] = cube[10][3];
			cube[10][3] = temp;
		}
	}

	static void shiftEdges(String[][] cube, String move) {
		switch (move) {
			case "U":
            // Temporarily store the top row of the front (blue) face
            String[] tempEdgeU = new String[]{cube[3][3], cube[3][4], cube[3][5]};
            
            // top blue row gets replaced by right top red row
            for (int i = 0; i < 3; i++) {
                cube[3][3 + i] = cube[3][6 + i]; // Blue to Red
                cube[3][6 + i] = cube[11][5 - i]; // Green to Blue
                cube[11][5 - i] = cube[3][0 + i]; // Orange to Green
                cube[3][0 + i] = tempEdgeU[i]; // Temp to Orange
            }
            break;
			
			case "U'":
            // Temporarily store the top row of the front (blue) face
            String[] tempEdgeUPrime = new String[]{cube[3][3], cube[3][4], cube[3][5]};
            
            // Front (blue)top row gets replaced by left (orange) top row
            for (int i = 0; i < 3; i++) {
                cube[3][3 + i] = cube[3][0 + i]; // Orange to Blue
                cube[3][0 + i] = cube[11][5 - i]; // Green to Orange
                cube[11][5 - i] = cube[3][6 + i]; // Red to Green
                cube[3][6 + i] = tempEdgeUPrime[i]; // Temp to Red
            }
            break;
			case "D":
			// Temporarily store the bottom row of the front (blue) face
			String[] tempEdgeD = new String[]{cube[5][3], cube[5][4], cube[5][5]};
			
			// Bottom blue row gets replaced by bottom red row
			for (int i = 0; i < 3; i++) {
				cube[5][3 + i] = cube[5][6 + i]; // Red to Blue
				cube[5][6 + i] = cube[9][5 - i]; // Green to Red
				cube[9][5 - i] = cube[5][0 + i]; // Orange to Green
				cube[5][0 + i] = tempEdgeD[i]; // Temp to Orange
			}
			break;

			case "D'":
			// Temporarily store the bottom row of the front (blue) face
			String[] tempEdgeDPrime = new String[]{cube[5][3], cube[5][4], cube[5][5]};
			
			// Bottom blue row gets replaced by bottom orange row
			for (int i = 0; i < 3; i++) {
				cube[5][3 + i] = cube[5][0 + i]; // Orange to Blue
				cube[5][0 + i] = cube[9][5 - i]; // Green to Orange
				cube[9][5 - i] = cube[5][6 + i]; // Red to Green
				cube[5][6 + i] = tempEdgeDPrime[i]; // Temp to Red
			}
			break;
			case "F":
			// Temporarily store the yellow edge
			String[] tempEdgeF = new String[]{cube[6][3], cube[6][4], cube[6][5]};
			
			// Shifts for F move
			for (int i = 0; i < 3; i++) {
				cube[6][5 - i] = cube[3 + i][6]; // Red to Yellow
				cube[3 + i][6] = cube[2][3 + i]; // White to Red <
				cube[2][3 + i] = cube[5 - i][2]; // Orange to White
				cube[5 - i][2] = tempEdgeF[i]; // Temp to Orange
			}
			break;

			case "F'":
			// Temporarily store the white edge
			String[] tempEdgeFPrime = new String[]{cube[2][3], cube[2][4], cube[2][5]};
			
			// Shifts for F' move
			for (int i = 0; i < 3; i++) {
				cube[2][3 + i] = cube[3 + i][6]; // Red to White
				cube[3 + i][6] = cube[6][5 - i]; // Yellow to Red
				cube[6][5 - i] = cube[5 - i][2]; // Orange to Yellow
				cube[5 - i][2] = tempEdgeFPrime[i]; // Temp (White) to Orange
			}
			break;

			case "B":
			// Temporarily store the top row of the white (up) face
			String[] tempEdgeB = new String[]{cube[0][3], cube[0][4], cube[0][5]};
			
			// Shifts for B move
			for (int i = 0; i < 3; i++) {
				cube[0][3 + i] = cube[3 + i][8]; // Red to White
				cube[3 + i][8] = cube[8][5 - i]; // Yellow to Red
				cube[8][5 - i] = cube[5 - i][0]; // Orange to Yellow
				cube[5 - i][0] = tempEdgeB[i]; // Temp (White) to Orange
			}
			break;

			case "B'":
			// Temporarily store the top row of the white (up) face for B' move
			String[] tempEdgeBPrime = new String[]{cube[0][3], cube[0][4], cube[0][5]};
			
			// Shifts for B' move
			for (int i = 0; i < 3; i++) {
				cube[0][3 + i] = cube[5 - i][0]; // Orange to White
				cube[5 - i][0] = cube[8][5 - i]; // Yellow to Orange
				cube[8][5 - i] = cube[3 + i][8]; // Red to Yellow
				cube[3 + i][8] = tempEdgeBPrime[i]; // Temp (White) to Red
			}
			break;

			case "L":
			// Temporarily store the top row of the white (up) face for L move
			String[] tempEdgeL = new String[]{cube[0][3], cube[1][3], cube[2][3]};

			// Shifts for L move
			for (int i = 0; i < 3; i++) {
				cube[0 + i][3] = cube[9 + i][3]; // Green to White
				cube[9 + i][3] = cube[6 + i][3]; // Yellow to Green
				cube[6 + i][3] = cube[3 + i][3]; // Blue to Yellow
				cube[3 + i][3] = tempEdgeL[i]; // Temp (White) to Blue
			}

			case "L'":
			// Temporarily store the top row of the white (up) face for L' move
			String[] tempEdgeLPrime = new String[]{cube[0][3], cube[1][3], cube[2][3]};

			// Shifts for L' move
			for (int i = 0; i < 3; i++) {
				cube[0 + i][3] = cube[3 + i][3]; // Blue to White
				cube[3 + i][3] = cube[6 + i][3]; // Yellow to Blue
				cube[6 + i][3] = cube[9 + i][3]; // Green to Yellow
				cube[9 + i][3] = tempEdgeLPrime[i]; // Temp (White) to Green
			}
			break;

			case "R":
			// Temporarily store the top row of the white (up) face for R move
			String[] tempEdgeR = new String[]{cube[0][5], cube[1][5], cube[2][5]};

			// Shifts for R move
			for (int i = 0; i < 3; i++) {
				cube[0 + i][5] = cube[3 + i][5]; // Blue to White
				cube[3 + i][5] = cube[6 + i][5]; // Yellow to Blue
				cube[6 + i][5] = cube[9 + i][5]; // Green to Yellow
				cube[9 + i][5] = tempEdgeR[i]; // Temp (White) to Green
			}

			case "R'":
			// Temporarily store the top row of the white (up) face for R' move
			String[] tempEdgeRPrime = new String[]{cube[0][5], cube[1][5], cube[2][5]};

			// Shifts for R' move
			for (int i = 0; i < 3; i++) {
				cube[0 + i][5] = cube[9 + i][5]; // Green to White
				cube[9 + i][5] = cube[6 + i][5]; // Yellow to Green
				cube[6 + i][5] = cube[3 + i][5]; // Blue to Yellow
				cube[3 + i][5] = tempEdgeRPrime[i]; // Temp (White) to Blue
			}
			break;


		}
	}
	static void makeMove(String move) {
        // Determine the face to rotate and the direction based on the move.
        int faceIndex = getFaceIndex(move);
        boolean clockwise = isClockwise(move);

        // Rotate the face.
        if (clockwise) {
            rotateFaceClockwise(numCube, faceIndex);
        } else {
            rotateFaceCounterClockwise(numCube, faceIndex);
        }

        // Shift rows/columns for the edges adjacent to the face.
        shiftEdges(numCube, move);
    }




}

