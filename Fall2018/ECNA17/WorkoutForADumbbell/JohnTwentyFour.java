import java.util.*;

public class JohnTwentyFour
{
    public static final int MAXSIZE =8;

    public static final char LEAF = 'L';
    public static final int PARENCOST = 1;
    public static final int INVCOST = 2;
    public static final int IMPOSSIBLE = 1000000;

    public static Scanner in = new Scanner(System.in);

    public static int [] original = new int[MAXSIZE];

    public static int countParens(node tree)
    {
        if (tree.op == LEAF)
            return 0;
        else {
            int count = countParens(tree.left) + countParens(tree.right);
            if (tree.op == '-' && tree.right.op == '-')
                count++;
            else if (tree.op == '*' || tree.op == '/') {
                if (tree.left.op == '+' || tree.left.op == '-')
                    count++;
                if (tree.right.op == '+' || tree.right.op == '-')
                    count++;
                if (tree.op == '/' && tree.right.op == '/')
                    count++;
            }
            return count;
        }
    }

    public static int numParens(node tree)
    {
        int count = countParens(tree);
        return count;
    }

    public static int find24(node [] tlist, int numTerms)
    {
        if (numTerms == 1) {
            if (tlist[0].val == 24)
                return numParens(tlist[0]);
            else
                return IMPOSSIBLE;
        }
        else {
            int cost = IMPOSSIBLE;
            for(int i=1; i<numTerms; i++) {
                node [] newList = new node[numTerms-1];
                for(int j=0; j<i-1; j++) {
                    newList[j] = new node();
                    newList[j].val = tlist[j].val;
                    newList[j].op = tlist[j].op;
                    newList[j].left = tlist[j].left;
                    newList[j].right = tlist[j].right;
                }
                for(int j=i; j<numTerms-1; j++) {
                    newList[j] = new node();
                    newList[j].val = tlist[j+1].val;
                    newList[j].op = tlist[j+1].op;
                    newList[j].left = tlist[j+1].left;
                    newList[j].right = tlist[j+1].right;
                }
                newList[i-1] = new node();
                newList[i-1].left = tlist[i-1];
                newList[i-1].right = tlist[i];
                newList[i-1].op = '+';
                newList[i-1].val = tlist[i-1].val+tlist[i].val;
                int val = find24(newList, numTerms-1);
                if (val < cost)
                    cost = val;
                newList[i-1].op = '-';
                newList[i-1].val = tlist[i-1].val-tlist[i].val;
                val = find24(newList, numTerms-1);
                if (val < cost)
                    cost = val;
                newList[i-1].op = '*';
                newList[i-1].val = tlist[i-1].val*tlist[i].val;
                val = find24(newList, numTerms-1);
                if (val < cost)
                    cost = val;
                if (tlist[i].val != 0 && tlist[i-1].val%tlist[i].val == 0) {
                    newList[i-1].op = '/';
                    newList[i-1].val = tlist[i-1].val/tlist[i].val;
                    val = find24(newList, numTerms-1);
                    if (val < cost)
                        cost = val;
                }
            }
            return cost;
        }
    }

    public static int numInversions(int [] list, int n)
    {
        int count = 0;
        int [] buildList = new int[n];
        for(int i=0; i<n; i++)
            buildList[i] = original[i];
        for(int i=0; i<n-1; i++) {
            int j=i;
            while (buildList[j] != list[i])
                j++;
            count += j-i;
            int tmp = buildList[j];
            for(int k=j-1; k >= i; k--)
                buildList[k+1] = buildList[k];
            buildList[i] = tmp;
        }
        return count;
    }

    public static int permute(int [] list, int index, int n)
    {
        if (index == n) {
            int numInv = numInversions(list, n);
            node [] tlist = new node[n];
            for(int i=0; i<n; i++) {
                tlist[i] = new node();
                tlist[i].val = list[i];
                tlist[i].op = LEAF;
                tlist[i].left = tlist[i].right = null;
            }
            return find24(tlist, n)*PARENCOST + numInv*INVCOST;
        }
        int cost = permute(list, index+1, n);
        for(int i=index+1; i<n; i++) {
            int tmp = list[i];
            list[i] = list[index];
            list[index] = tmp;
            int val = permute(list, index+1, n);
            if (val < cost)
                cost = val;
            tmp = list[i];
            list[i] = list[index];
            list[index] = tmp;
        }
        return cost;
    }

    public static int permute(int [] list, int len)
    {
        return permute(list, 0, len);
    }

    public static void main(String [] args)
    {
        int [] list = new int[MAXSIZE];
        int n=4;

//        n = in.nextInt();
        for(int i=0; i<n; i++)
            list[i] = in.nextInt();
        for(int i=0; i<n; i++)
            original[i] = list[i];
        int ans = permute(list, n);
        if (ans >= IMPOSSIBLE)
            System.out.println("impossible");
        else
            System.out.println(ans);
    }
}

class node
{
    public int val;
    public char op;
    public node left, right;
}

