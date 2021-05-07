package com.lsf;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WordAnalysis {
    //key_word is used to store the key words.
    private String key_word[] = {"return","void","main","break","continue","include","begin","end","if","else","switch","while"};
    private char special_characters[] = {'\n', '\t', ' ', '\r'};
    //Initialize the key_word and add the key words into key_word.
    public WordAnalysis(){
    }
    private boolean isSpecialCharacter(char character){
        for(int i = 0;i<special_characters.length;i++){
            if(character == special_characters[i])return true;
        }
        return false;
    }
    private boolean isDigit(char digit){
        if(digit>='0'&&digit<='9')return true;
        return false;
    }
    private boolean isKeyWord(String word){
        for(int i = 0;i<key_word.length;i++){
            if(key_word[i].equals(word))return true;
        }
        return false;
    }
    private boolean isLetter(char letter){
        if((letter>='a'&&letter<='z')||(letter>='A'&&letter<='Z'))return true;
        return false;
    }
    void analysis(char[] chars){
        String current_word = "";
        for(int i = 0;i<chars.length-1;i++){
            char current_char = chars[i];
            current_word = "";
            if(isSpecialCharacter(current_char)){}
            else if(isLetter(current_char)){
                while (isLetter(current_char)||isDigit(current_char)){
                    current_word += current_char;
                    current_char = chars[++i];
                }
                i--;
                //if the current_word is a key word
                if(isKeyWord(current_word)){
                    System.out.println(current_word+"\t4"+"\tkeyword");
                }
                //if the current_word is an identifier
                else System.out.println(current_word+"\t4"+"\tidentifier");
            }
            else if(isDigit(current_char)||current_char == '.'){
                while(isDigit(current_char)||(current_char == '.'&&isDigit(chars[++i]))){
                    if(current_char == '.')i--;
                    current_word+=current_char;
                    current_char = chars[++i];
                }
                i--;
                //the current_word is digit
                System.out.println(current_word+"\t5"+"\tnumber");
            }
            else switch(current_char){
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        System.out.println(current_char+"\t2"+"\toperator");
                        break;
                    case '(':
                    case ')':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        System.out.println(current_char+"\t3"+"\tdelimiter");
                        break;
                    case '=':{
                        current_char = chars[++i];
                        if(current_char == '=')System.out.println("==\t2\toperator");
                        else{
                            System.out.println("=\t2\toperator");
                            i--;
                        }
                    }break;
                    case ':':{
                        current_char = chars[++i];
                        if(current_char == '=')System.out.println(":=\t2\toperator");
                        else{
                            System.out.println(":\t2\toperator");
                            i--;
                        }
                    }break;
                    case '>':{
                        current_char = chars[++i];
                        if(current_char == '=')System.out.println(">=\t2\toperator");
                        else{
                            System.out.println(">\t2\toperator");
                            i--;
                        }
                    }break;
                    case '<':{
                        current_char = chars[++i];
                        if(current_char == '=')System.out.println("<=\t2\toperator");
                        else{
                            System.out.println("<\t2\toperator");
                            i--;
                        }
                    }break;
                    default:System.out.println(current_char+"\t6\tNo Identifier");
            }
        }
    }
}
