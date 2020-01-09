/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpeg;

/**
 *
 * @author Elliot
 */
public class Node {
    
    String NodeCode;
    double Freq;
    String Symbol;
    Node right;
    Node left;
    
    Node()
    {
        NodeCode = "";
        Freq = 0;
        Symbol = "";
        left = null;
        right =  null;
    }

    public Node(String NodeCode, double Freq, String Symbol, Node right, Node left) {
        this.NodeCode = NodeCode;
        this.Freq = Freq;
        this.Symbol = Symbol;
        this.right = right;
        this.left = left;
    }

    public double getFreq() {
        return Freq;
    }

    public String getNodeCode() {
        return NodeCode;
    }

    public String getSymbol() {
        return Symbol;
    }

    public void setFreq(double Freq) {
        this.Freq = Freq;
    }

    public void setNodeCode(String NodeCode) {
        this.NodeCode = NodeCode;
    }

    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }
    
    
}
