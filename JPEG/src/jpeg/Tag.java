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
public class Tag {
    int numOfZeroes;
    int numOfBits;
    String id;
    String code;    
    
    
    public Tag() {
    }

    public Tag(int numOfZeroes, int numOfBits, String id, String code) {
        this.numOfZeroes = numOfZeroes;
        this.numOfBits = numOfBits;
        this.id = id;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumOfBits() {
        return numOfBits;
    }

    public int getNumOfZeroes() {
        return numOfZeroes;
    }

    public void setNumOfBits(int numOfBits) {
        this.numOfBits = numOfBits;
    }

    public void setNumOfZeroes(int numOfZeroes) {
        this.numOfZeroes = numOfZeroes;
    }
    
}
