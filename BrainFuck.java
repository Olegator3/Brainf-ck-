package bf;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by ПК on 16.05.2015.
 */
public class BrainFuck {
   public static int var = 0;
   public static int j =0;
   public static int[] data = new int[30000];
    public static  Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        LinkedList<String> commands = new LinkedList<>();
        String s = sc.nextLine();

        for(Character command : s.toCharArray()){
            commands.add(command.toString().trim());
        }

        int ifop = Collections.frequency(commands,"(");
        int ifend = Collections.frequency(commands,")");
        int loopop = Collections.frequency(commands,"[");
        int loopend = Collections.frequency(commands,"]");
        if(ifop!= ifend || loopop!=loopend)System.exit(0);
            brainFuck(commands);


    }

    public static  void brainFuck(LinkedList<String> commands){
        for(int i =0;i<commands.size();i++){
            String com = commands.get(i);
            switch (com){
                case ">" : right();break;
                case "<" : left();break;
                case "|" : reset();break;
                case "," : read();break;
                case "." : print();break;
                case "=" : curSetPrev();break;
                case "0" : curSetZero();break;
                case "!" : copyToVar();break;
                case "?" : copyToData();break;
                case "*" : multPrev();break;
                case "/" : divPrev();break;
                case "+" : incData();break;
                case "-" : dicData();break;
                case "[":
                    int countOpenLoop = 1;
                    int countCloseLoop = 0;
                    String commLoop;
                    LinkedList<String> loopCmd = new LinkedList<>();
                    for(int z = i+1;countOpenLoop != countCloseLoop;z++,i++){
                        commLoop = commands.get(z);
                        if(commLoop.equals("["))countOpenLoop++;
                        if(commLoop.equals("]"))countCloseLoop++;
                        loopCmd.add(commLoop);
                    }

                    loopCmd.remove(loopCmd.size()-1);
                    while (data[j]>0){
                    brainFuck(loopCmd);
                    }
                    break;
                case "(":
                    int countOpenIf = 1;
                    int countCloseIf = 0;
                    String commIf;
                    LinkedList<String> ifCmd = new LinkedList<>();
                    for(int z = i+1;countOpenIf != countCloseIf ;z++,i++){
                        commIf = commands.get(z);
                        if(commIf.equals("("))countOpenIf++;
                        if(commIf.equals(")"))countCloseIf++;
                        ifCmd.add(commIf);
                    }
                    ifCmd.remove(ifCmd.size()-1);
                    if(data[j]>0)brainFuck(ifCmd);
                    break;
                case "^": printVar();break;
                case "$": indexToVar();break;
                case ")": break;
                case "]": break;

            }

        }

    }

    public static void read(){
        try {
            data[j] = (Integer.parseInt(sc.nextLine())) % 256;
        }catch (Exception e){
            data[j] = 0;
        }

    }
    private static void indexToVar() {
        var = j;

    }

    private static void printVar() {
        System.out.println(var);
    }

    private static void dicData() {
        if(data[j]!=0){
            data[j]-=1;
        }else data[j] = 0;
    }

    private static void incData() {
        if(data[j]==255)data[j] = 0;
        else data[j]+=1;
    }

    private static void divPrev() {
        int a = data[j];
        left();
        int b = data[j];
        right();
        int res =a/b;
        data[j] = res;
    }

    private static void multPrev() {
        left();
        int mult = data[j];
        right();
        int res = (data[j]*mult)%256;
        data[j]=res;

    }

    private static void copyToData() {
        data[j] = var%256;
    }

    private static void copyToVar() {
        var = data[j];
//        System.out.println(var);
    }

    private static void curSetZero() {
        data[j] = 0;
    }

    private static void curSetPrev() {
        left();
        int a = data[j];
        right();
        data[j]=a;
    }

    private static void print() {
        System.out.println(data[j]);
    }

    private static void reset() {
        j=0;
    }

    private static void left() {
        if(j == 0)j=29999;
        else j--;
    }

    public static void right(){
        if(j == 29999)j=0;
        else j++;
    }

}
