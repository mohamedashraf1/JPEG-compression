/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpeg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author Elliot
 */
public class JPEG{
    public static Vector<Integer> numbers = new Vector<>();
    public static Vector<Tag> tags = new Vector<>();
    public static Vector <Node> chars = new Vector();
    public static Vector <Node> newchars = new Vector();
    public static Vector <Double> Freqs = new Vector();
    
    public static void storeTags(String seq){
        String []tmp = seq.split(",");
        int tmp2[] = new int[tmp.length];
        for(int i = 0 ; i < tmp.length ; i++){
            tmp2[i] = Integer.valueOf(tmp[i]);
        }
        
        int counter = 0;
        int ascii = 97;
        boolean found = false;
        
        for(int i = 0 ; i < tmp2.length ; i++){
            if(tmp2[i] == 0){
                counter++;
            }
            else{
                numbers.add(tmp2[i]);
                Tag temp = new Tag();
                
                int numberOfBits = 0;
                if(tmp2[i] < 0)
                    numberOfBits = (int)(Math.log(tmp2[i] * -1) / Math.log(2) + 1);
                else
                    numberOfBits = (int)(Math.log(tmp2[i]) / Math.log(2) + 1);
                
                for(int j = 0 ; j < tags.size() ; j++){
                    if(numberOfBits == tags.get(j).getNumOfBits() && counter == tags.get(j).getNumOfZeroes()){
                        temp.setId(tags.get(j).getId());
                        found = true;
                        break;
                    }
                }
                if(found == false){
                    temp.setId((char)ascii + "");
                    ascii++;
                }
                temp.setNumOfZeroes(counter);
                temp.setNumOfBits(numberOfBits);
                tags.add(temp);
                counter = 0;
                found = false;
            }
        }
        Tag eob = new Tag();
        eob.setId("*");
        tags.add(eob);
        numbers.add(0);
    }
    public static void setFreq (String  text)
    {
        for(int i=0 ; i<text.length() ; i++)
        {
            Node curr = new Node();
            boolean flag = true;
            String s = text.charAt(i) + "";
            for(int j=0 ; j<chars.size() ; j++)
            {
                if(chars.get(j).Symbol.equals(s))
                {
                    flag = false;
                    Freqs.set(j, Freqs.get(j) + 1);
                    break;
                }
            }
            if(flag == true)
            {
                curr.Symbol = s;
                chars.add(curr);
                Freqs.add(1.0);
            }
        }
        double m = text.length();
        for(int i=0 ; i<chars.size() ; i++)
        {
            chars.get(i).Freq = Freqs.get(i) / m ;
        }

    }
    public static void sort()
    {
          for(int i=0 ; i<chars.size() ; i++)
        {
            for(int j=i+1 ; j<chars.size() ; j++)
            {
                if(chars.get(i).Freq > chars.get(j).Freq)
                {
                    Node temp = new Node();
                    temp = chars.get(j);
                    chars.set(j, chars.get(i));
                    chars.set(i, temp);
                }
            }
            
        }
        
    }
    public static void update(Node r)
    {
        if(r.right == null || r.left == null)
        {
            return;
        }
        else{
            r.right.NodeCode = r.NodeCode + "1";
            r.left.NodeCode = r.NodeCode + "0";
            update(r.right);
            update(r.left);
        }
    }
    public static void setTree()
    {
        Node root = new Node();
        sort();
        while(true)
        {
            if(chars.size() == 1)
            {
                root = chars.get(0);
                update(root);
                
                break;
            }
            Node temp = new Node();
            temp.right = new Node();
            temp.left  = new Node();
            temp.right = chars.get(0);
            chars.remove(0);
            temp.left = chars.get(0);
            chars.remove(0);
            temp.Freq = temp.right.Freq + temp.left.Freq;
            temp.Symbol = temp.right.Symbol + temp.left.Symbol;
            chars.add(temp);
            sort();

        }
    }
    public static void storeCodes(Node n)
    {
        if(n == null)
        {
            return;
        }
        
        if(n.getSymbol().length() == 1){
            newchars.add(n);
        }
        
        storeCodes(n.right);
        storeCodes(n.left);
    }
    
    public static void getCodes(){
        String STR = "";
        for(int i = 0 ; i < tags.size() ; i++){
            STR += tags.get(i).getId();
        }
        STR += "*";
        setFreq(STR);
        setTree();
        storeCodes(chars.get(0));
        
        for(int i = 0 ; i < tags.size() ; i++){
            for(int j = 0 ; j < newchars.size() ; j++){
                if(tags.get(i).getId().equals(newchars.get(j).getSymbol())){
                    tags.get(i).setCode(newchars.get(j).getNodeCode());
                    break;
                }
            }
        }
    }
    public static String compliment(String str){
        String str2 = "";
        for(int j = 0 ; j < str.length() ; j++){
            if((str.charAt(j) + "").equals("0"))
                str2 += "1";
            else
                str2 += "0";
        }
        return str2;
    }
    public static String[] compress(String seq) throws FileNotFoundException, IOException{
        String output = "";
        storeTags(seq);
        getCodes();
        
        for(int i = 0 ; i < tags.size() ; i++){
            if(tags.get(i).getId().equals("*")){
                output += tags.get(i).getCode();
            }
            else{
                output += tags.get(i).getCode() + ",";
                if(numbers.get(i) < 0){
                    String str = Integer.toBinaryString(numbers.get(i) * -1);
                    
                    output +=  compliment(str) + " ";
                }
                else
                    output += Integer.toBinaryString(numbers.get(i)) + " ";
                }
        }
        
        String table = "";
        for(int i = 0 ; i < newchars.size() ; i++){
            for(int j = 0 ; j < tags.size() ; j++){
                if(newchars.get(i).getSymbol().equals(tags.get(j).getId())){
                    table += tags.get(j).getNumOfZeroes() + "/" + tags.get(j).getNumOfBits() + " " + tags.get(j).getCode() + "\n";
                    break;
                }
            }
        }
        
        String finaloutput[] = new String[2];
        finaloutput[0] = output;
        finaloutput[1] = table;
                
        return finaloutput;
    }
    public static String decompress(String seq){
        String tmp[] = seq.split(" ");
        String output = "";
        for(int i = 0 ; i < tmp.length ; i++){
            String tmp2[]= tmp[i].split(",");
            if(i == tmp.length -1){//for EOB
                output += "EOB";
            }
            else{
                for(int j = 0 ; j < tags.size() ; j++){
                    if(tmp2[0].equals(tags.get(j).getCode())){
                        for(int k = 0 ; k < tags.get(j).getNumOfZeroes() ; k++){
                            output += "0";
                        }
                        int value;
                        if(tmp2[1].startsWith("0")){
                            String str = compliment(tmp2[1]);
                            value = Integer.parseInt(str,2) * -1;
                            output += String.valueOf(value) +" ";
                        }
                        else{
                            value = Integer.parseInt(tmp2[1],2);
                            output += String.valueOf(value)+" ";
                        }
                        break;
                    }
                }
            }
        }
        return output;
    }
    
}
