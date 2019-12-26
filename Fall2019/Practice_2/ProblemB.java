import java.util.*;

public class ProblemB {
    static int currNumber = 1;
    static int numCards = 0;

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        numCards = in.nextInt();
        List<StringBuilder> cardList = new ArrayList<StringBuilder>();

        if(numCards == 0){
            return;
        }
        if (numCards == 1){
            System.out.println("1");
            return;
        }

        for(int i = 0; i < numCards; i++)
            cardList.add(new StringBuilder());

        // Add the same number to all cards
        for(StringBuilder card: cardList)
            card.append(currNumber + " ");
        currNumber++;

        //  finalPathLength=1 eg. 0         (add a number to card 0)
        //  finalPathLength=2 eg. 0->1      (add the same number to card 0 and 1)
        //  finalPathLength=3 eg. 0->1->3   (add the same number to card 0, 1 and 3)
        for (int finalPathLength = 1; finalPathLength < numCards; finalPathLength++){
            for(int index = 0; index < numCards; index++) {
                List<Integer> indexList = new ArrayList<Integer>();
                indexList.add(index);
                DFS(cardList, indexList, index, 1, finalPathLength);
            }
        }

        for(StringBuilder builder : cardList){
            System.out.println(builder);
        }
    }

    static void DFS(List<StringBuilder> cardList, List<Integer> indexList, int index, int currLength, int finalPathLength){
        if (currLength == finalPathLength) {
            for(int insertIndex : indexList)    // adds a number to all the indices in indexList
                cardList.get(insertIndex).append(currNumber + " ");
            currNumber++;                       // get a new number
            return;
        }

        for (int i = index+1; i < numCards; i++){
            indexList.add(i);                               // adds the current index
            DFS(cardList, indexList, i, currLength+1, finalPathLength);
            indexList.remove(indexList.size()-1);     // removes the last index added
        }
    }
}
