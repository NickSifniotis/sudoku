import java.util.List;

/**
 * Created by Nick Sifiotis on 27/11/16.
 *
 * Sudoku solver!
 */
public class Sudoku
{
    public static void main(String[] args)
    {
        Board board = new Board();

        String [] initialBoard = {
                "...4..6.2",
                "..6...1..",
                ".9.5...8.",
                ".5.3.....",
                "3.12.64.5",
                ".....7.2.",
                ".3...2.6.",
                "..4...9..",
                "5.7..9..."
        };

        board.InitialConfiguration(initialBoard);

        board.DisplayBoard();

        System.out.println("Executing loop.");

        while (board.PartitionContainers());

        board.DisplayBoard();


//
//        for (int colTriplet = 0; colTriplet < 9; colTriplet += 3)
//        {
//            System.out.println ("\nGenerating asymmetrical matrix for columns starting on " + (1 + colTriplet));
//
//            int [] [] matrix = board.GenerateAsymmetricalMatrix(false, colTriplet);
//            Utilities.PrintAsymMatrix(matrix);
//
//            for (int piece = 0; piece < 9; piece ++)
//            {
//                for (int cellConsideration = 0; cellConsideration < 3; cellConsideration ++)
//                {
//                    for (int columnConsideration = 0; columnConsideration < 3; columnConsideration++)
//                    {
//                        if ((matrix[columnConsideration][cellConsideration] & Utilities.POSITION[piece]) > 0)
//                        {
//                            int horizontalOccurences = 0;
//                            int verticalOccurences = 0;
//                            for (int iterator = 0; iterator < 3; iterator++)
//                            {
//                                horizontalOccurences += (matrix[columnConsideration][iterator] & Utilities.POSITION[piece]) > 0 ? 1 : 0;
//                                verticalOccurences += (matrix[iterator][cellConsideration] & Utilities.POSITION[piece]) > 0 ? 1 : 0;
//                            }
//                            System.out.println ("hor: " + horizontalOccurences + " vert: " + verticalOccurences + " at piece " + (piece + 1) + " where " + Utilities.SetToString(Utilities.COLS[cellConsideration + colTriplet]) + " " + Utilities.SetToString(Utilities.CELLS[board.colCells[cellConsideration + colTriplet][columnConsideration]]));
//                            if (horizontalOccurences == 1 && verticalOccurences > 1)
//                            {
//                                System.out.println("(Horizontal) Investigating piece " + (piece + 1) + " where " + Utilities.SetToString(Utilities.COLS[cellConsideration + colTriplet]) + " " + Utilities.SetToString(Utilities.CELLS[board.colCells[cellConsideration + colTriplet][columnConsideration]]));
//
//                                // so how do we do this thing? Need to set all instances of piece to false in every (col, cell) combination
//                                // that intersects with colUnderConsideration, cellUnderConsideration
//
//                                // each (col, cell) combination corresponds to three pieces (the double use of the word piece is starting to annoy)
//                                // so we need a function that, given a (col, cell) combination, returns a triple of three piece
//                                // ids that correspond to the pieces that share the col, cell space.
//
//                                for (int iterator = 0; iterator < 3; iterator ++)
//                                {
//                                    int columnNumber = Utilities.COLS[cellConsideration + colTriplet];
//                                    int cellNumber = Utilities.CELLS[board.colCells[cellConsideration + colTriplet][iterator]];
//                                    if (iterator != columnConsideration)
//                                    {
//                                        List<Integer> pieces = board.GetPieces(columnNumber, cellNumber);
//
//                                        System.out.println ("Printing out a data dump for the following: ");
//                                        System.out.println (Utilities.SetToString(columnNumber) + " and " + Utilities.SetToString(cellNumber));
//                                        for (int i: pieces)
//                                            System.out.print ((i + 1) + ", ");
//                                        System.out.println ();
//
//                                        // ok, so set to low the piece for all these pieces
//                                        // geez, so much work to do to clean this shit up
//                                        int inverse = ~Old_Board.POSITION[piece];
//                                        for (int p: pieces)
//                                        {
//                                            System.out.println ("P is " + (p + 1));
//                                            System.out.println ("Old value: " + Utilities.IntToBinaryString(board.piece_bitmaps[p]));
//
//                                            board.piece_bitmaps[p] &= inverse;
//                                            board.notify_changes[columnNumber] = true;
//                                            board.notify_changes[cellNumber] = true;
//
//                                            System.out.println ("New value: " + Utilities.IntToBinaryString(board.piece_bitmaps[p]));
//                                        }
//                                    }
//                                }
//                            }
//                            else if (verticalOccurences == 1 && horizontalOccurences > 1)
//                            {
//                                System.out.println("(Vertical) Investigate piece " + (piece + 1) + " where col = " + (cellConsideration + colTriplet + 1) + " cell = " + (columnConsideration + 1));
//                            }
//                        }
//                    }
//                }
//            }
//        }
////
////        for (int rowTriplet = 0; rowTriplet < 9; rowTriplet += 3)
////        {
////            System.out.println ("\nGenerating asymmetrical matrix for rows starting on " + (1 + rowTriplet));
////
////            int [] [] matrix = board.GenerateAsymmetricalMatrix(true, rowTriplet);
////            Utilities.PrintAsymMatrix(matrix);
////
////            for (int piece = 0; piece < 9; piece ++)
////            {
////                for (int rowUnderConsideration = 0; rowUnderConsideration < 3; rowUnderConsideration ++)
////                {
////                    for (int cellUnderConsideration = 0; cellUnderConsideration < 3; cellUnderConsideration++)
////                    {
////                        if ((matrix[cellUnderConsideration][rowUnderConsideration] & Utilities.POSITION[piece]) > 0)
////                        {
////                            int h = 0;
////                            int v = 0;
////                            for (int k = 0; k < 3; k++)
////                            {
////                                h += (matrix[cellUnderConsideration][k] & Utilities.POSITION[piece]) > 0 ? 1 : 0;
////                                v += (matrix[k][rowUnderConsideration] & Utilities.POSITION[piece]) > 0 ? 1 : 0;
////                            }
////
////                            if ((h == 1 && v > 1) || (v == 1 && h > 1))
////                                System.out.println("Investigate piece " + (piece + 1) + " where row = " + (rowUnderConsideration + rowTriplet + 1) + " cell = " + (cellUnderConsideration + 1) + " v = " + v + " h = " + h);
////                        }
////                    }
////                }
////            }
////        }
//
//        System.out.println("\nFinal final Solution");
//        board.PrintBoardState();
//
//        System.out.println("\nProcessing ..");
//        main_loop(board);
//
//        System.out.println("\nFinal final final Solution");
//        board.PrintBoardState();
    }
}
