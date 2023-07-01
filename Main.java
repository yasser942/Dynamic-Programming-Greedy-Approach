import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {


    public static void dynamicApproach(ArrayList<Player> pieces, int GOLD_AMOUNT,
                                       int TotalAttackPoints,
                                       int TotalSpentGold) {
        int AP[] = new int[pieces.size()];//This array has the attack points of selected pieces
        int G[] = new int[pieces.size()];//This array has the gold amount of each piece
        int GM = GOLD_AMOUNT;//assigning Gold amount to GM to make the code more readable and short
        int n = AP.length;//the same thing , assigning the the length  of the AP array to variable n

//this loop is to fill the above arrays with the appropriate values.
        for (int i = 0; i < pieces.size(); i++) {
            AP[i] = pieces.get(i).getAttackPoints();
            G[i] = pieces.get(i).getGold();

        }
        //the Table that has the possible solution of Problem using Dynamic Programming technique
        int T[][] = new int[n + 1][GM + 1];

        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= GM; j++) {
                T[i][j] = 0;
            }

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= GM; j++) {
                T[i][j] = T[i - 1][j];

                if ((j >= G[i - 1]) && (T[i][j] < T[i - 1][j - G[i - 1]] + AP[i - 1])) {
                   T[i][j] = T[i - 1][j - G[i - 1]] + AP[i - 1];
                }


            }

        }




        while (n != 0) {
            if (T[n][GM] != T[n - 1][GM]) {
                for (int i = 0; i < pieces.size(); i++) {
                    if (pieces.get(i).getGold() == G[n - 1] && pieces.get(i).getAttackPoints() == AP[n - 1]) {
                        pieces.get(i).printInfo();
                        TotalAttackPoints += pieces.get(i).getAttackPoints();
                        TotalSpentGold += pieces.get(i).getGold();
                    }
                }
                GM = GM - G[n - 1];
            }
            n--;
        }
        System.out.println("**********************************************");

        System.out.println("Gold Spent = " + TotalSpentGold);
        System.out.println("Total Attack Points  = " + TotalAttackPoints);
    }


    public static void greedyApproach(ArrayList<Player> pieces, int GOLD_AMOUNT,

                                      int NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL,
                                      int TotalSpentGold, int TotalAttackPoints) {

// this loop is to store ratios of each piece (ATTACK POINTS / Gold) it usd to perform
// operations of Greedy technique

        for (int i = 0; i < pieces.size(); i++) {
            float r = (float) pieces.get(i).getAttackPoints() / pieces.get(i).getGold();
            pieces.get(i).setRatio(r);
        }
 // sorting pieces depending on their ratios
        Collections.sort(pieces, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Float.compare(o1.getRatio(), o2.getRatio());
            }
        });
        Collections.reverse(pieces);//just reverse the list to have decreasing order

        //this , after sorting depending on ration , again I reordered them depending on type
        Collections.sort(pieces, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getPieceType().compareTo(o2.getPieceType());
            }
        });

        //the below steps just to have a one piece of each type
        ArrayList<Player> unique = new ArrayList<>();
        for (int i = 0; i < pieces.size(); i += NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL) {
            unique.add(pieces.get(i));

        }
        Collections.sort(unique, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return Float.compare(o1.getRatio(), o2.getRatio());
            }
        });
        Collections.reverse(unique);

//this the last list that has the possible of this approach
        ArrayList<Player> collectedPieces = new ArrayList<>();

        int CollectedGoldAmount = 0;
        for (int i = 0; i < unique.size(); i++) {

            if (CollectedGoldAmount <= GOLD_AMOUNT && unique.get(i).getGold() <= (GOLD_AMOUNT - CollectedGoldAmount)) {
                String type = pieces.get(i).getPieceType();


                collectedPieces.add(unique.get(i));
                CollectedGoldAmount += unique.get(i).getGold();
                TotalSpentGold += unique.get(i).getGold();
                TotalAttackPoints += unique.get(i).getAttackPoints();

            }
        }
//printing the possible collected pieces of Greedy approach
        for (int i = 0; i < collectedPieces.size(); i++) {
           collectedPieces.get(i).printInfo();

        }
        System.out.println("**********************************************");

        System.out.println("Gold Spent = " + TotalSpentGold);
        System.out.println("Total Attack Points  = " + TotalAttackPoints);


    }

    public static void randomApproach(ArrayList<Player> pieces,

                                      int GOLD_AMOUNT,
                                      int TotalSpentGold,
                                      int TotalAttackPoints) {

        Random rand = new Random();
        //This list will include the possible pieces in this approach.
        ArrayList<Player> collectedPieces = new ArrayList<>();

        //I use this to be sure that only on piece of each type will be included
        ArrayList<String> types = new ArrayList<>();

        while (pieces.size() != 0) {

            int randomChoice = rand.nextInt(pieces.size());//generates a random number in range of list size
           //in below steps , I store the type for each piece and depending on the condition
            // in IF statement I include the pieces, until to get the list empty.
            String type = pieces.get(randomChoice).getPieceType();
            if (GOLD_AMOUNT >= pieces.get(randomChoice).getGold()) {
                if (!types.contains(type)) {
                    collectedPieces.add(pieces.get(randomChoice));
                    GOLD_AMOUNT -= pieces.get(randomChoice).getGold();
                    types.add(type);
                }

            }
            pieces.remove(randomChoice);
        }
        //printing the collected pieces in this approach
        for (int i = 0; i < collectedPieces.size(); i++) {
            collectedPieces.get(i).printInfo();
            TotalAttackPoints += collectedPieces.get(i).getAttackPoints();
            TotalSpentGold += collectedPieces.get(i).getGold();

        }
        System.out.println("**********************************************");
        System.out.println("Gold Spent = " + TotalSpentGold);
        System.out.println("Total Attack Points  = " + TotalAttackPoints);


    }



    //this method to read data from file and fill them in data structure
    public static ArrayList<Player> readData() throws FileNotFoundException {
        File file = new File("src\\input_1.csv");
        Scanner scanner = new Scanner(file);
        ArrayList<Player> players = new ArrayList<>();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            Player player = new Player();
            String line = scanner.nextLine();
            String[] attributes = line.split(",");
            player.setHero(attributes[0]);
            player.setPieceType(attributes[1]);
            player.setGold(Integer.parseInt(attributes[2]));
            player.setAttackPoints(Integer.parseInt(attributes[3]));
            players.add(player);
        }
        return players;


    }

    //this method just to deal with files in case we have different files with different sizes
    public static ArrayList<Player> SelectedPieces(ArrayList<Player> data,
                                                   int NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL,
                                                   int MAX_LEVEL_ALLOWED) {
        ArrayList<Player> players = data;

        int index = 0;
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).getPieceType().equals(players.get(i + 1).getPieceType())) {
                index = i;
                index++;
                break;
            }
        }
        //System.out.println("Index = "+index);

        ArrayList<Player> pieces = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < players.size(); i += index) {
            for (int j = i; j < i + NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL; j++) {
                pieces.add(players.get(j));
            }
            counter += 1;
            if (counter == MAX_LEVEL_ALLOWED) {
                break;
            }
        }
        return pieces;

    }


    public static void main(String[] args) throws FileNotFoundException {
        int GOLD_AMOUNT = 0;
        int MAX_LEVEL_ALLOWED = 0;
        int NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL = 0;
        int TotalSpentGold = 0;
        int TotalAttackPoints = 0;

        long startTime;
        long totalTime;



        Scanner scanner1 = new Scanner(System.in);
        boolean flag = false;
        while (!flag) {
            if (!(GOLD_AMOUNT >= 5 && GOLD_AMOUNT <= 1200)) {
                System.out.println("Please, Enter the gold amount in range[5-1200] : ");
                GOLD_AMOUNT = Integer.parseInt(scanner1.nextLine());


            } else if (!(MAX_LEVEL_ALLOWED >= 1 && MAX_LEVEL_ALLOWED <= 9)) {
                System.out.println("Please, Enter the max level allowed in range[1-9] : ");
                MAX_LEVEL_ALLOWED = Integer.parseInt(scanner1.nextLine());

            } else if (!(NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL >= 1 && NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL <= 25)) {
                System.out.println("Please, Enter the number of available piece per level in range[1-25] : ");
                NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL = Integer.parseInt(scanner1.nextLine());

            } else {
                flag = true;
            }


        }
        // this list has the whole pieces after reading them from the file to process them later

        ArrayList<Player> pieces = SelectedPieces(readData(), NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL, MAX_LEVEL_ALLOWED);

        System.out.println("================== TRIAL #1 ==================");
        startTime = System.nanoTime();
        System.out.println("Computer's Greedy Approach results\n");
         greedyApproach(pieces, GOLD_AMOUNT, NUMBER_OF_AVAILABLE_PIECE_PER_LEVEL, TotalSpentGold, TotalAttackPoints);
        totalTime = System.nanoTime() - startTime;
        System.out.println("Greedy Approach time = "+totalTime+" nsec");

        System.out.println("----------------------------------------------");
        System.out.println("\n\nUser's Dynamic Programming results\n ");
        startTime = System.nanoTime();
        dynamicApproach(pieces, GOLD_AMOUNT, TotalSpentGold, TotalAttackPoints);
        totalTime = System.nanoTime() - startTime;
        System.out.println("Dynamic Approach time = "+totalTime+" nsec");
        System.out.println("\n\n");

        System.out.println("================== TRIAL #2 ==================");

        System.out.println("\n\nUser's Dynamic Programming \n ");
        startTime = System.nanoTime();
        dynamicApproach(pieces, GOLD_AMOUNT, TotalSpentGold, TotalAttackPoints);
        totalTime = System.nanoTime() - startTime;
        System.out.println("Dynamic Approach time = "+totalTime+" nsec");
        System.out.println("----------------------------------------------");
        System.out.println("\n\nComputer's Random Approach results\n");
        startTime = System.nanoTime();
        randomApproach(pieces, GOLD_AMOUNT,TotalSpentGold, TotalAttackPoints);
        totalTime = System.nanoTime() - startTime;
        System.out.println("Random Approach time = "+totalTime+" nsec");




    }
}
