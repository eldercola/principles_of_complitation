package com.lsf;

import javafx.util.Pair;

import java.util.*;
import java.util.regex.Pattern;

public class WordAnalysis {
    public String pattern = "^([$|a-z|A-Z][$|a-z|A-Z|0-9|_]*)||(_[$|a-z|A-Z|0-9|_]*[$|a-z|A-Z|0-9]+)";
    //key_word is used to store the key words.
    private String key_word[] = {"String","int","double","char",
            "return","void","main","break","continue","include","begin","end","if","else","switch","while"};
    private char special_characters[] = {'\n', '\t', ' ', '\r'};
    Vector<Pair<String,String>> wordsClassify = new Vector<Pair<String,String>>();

    public Vector<Pair<String, String>> getWordsClassify() {
        return wordsClassify;
    }

    //Initialize the key_word and add the key words into key_word.
    public WordAnalysis(){
    }
    private boolean isSpecialCharacter(char character){
        for(int i = 0;i<special_characters.length;i++){
            if(character == special_characters[i])return true;
        }
        return false;
    }
    private boolean isIdentifier(String word){
        boolean isMatched = Pattern.matches(pattern,word);
        return isMatched;
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
        if((letter>='a'&&letter<='z')||(letter>='A'&&letter<='Z')||(letter == '$')||(letter == '_'))return true;
        return false;
    }
    private String getFiveChars(String word){
        if(word.length()>5){
            String newlyWord = "";
            for(int i = 0;i<5;i++){
                newlyWord+=word.charAt(i);
            }
            return newlyWord;
        }
        else return word;
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
                    System.out.println(current_word+"\t4"+"\tkeyword 关键字");
                    Pair<String,String> p = new Pair<>(current_word,"keyword");
                    wordsClassify.add(p);
                }
                //if the current_word is an identifier
                else if(isIdentifier(current_word)){
                    Pair<String,String> p = new Pair<>(current_word,"identifier");
                    wordsClassify.add(p);
                    System.out.println(getFiveChars(current_word)+"\t4"+"\tidentifier 标识符");
                }
                //not a legal identifier
                else{
                    System.out.println(current_word+"\t6\tNo Identifier 非标识符");
                    Pair<String,String> p = new Pair<>(current_word,"No Identifier");
                    wordsClassify.add(p);
                }
            }
            else if(isDigit(current_char)||current_char == '.'){
                while(isDigit(current_char)||(current_char == '.'&&isDigit(chars[++i]))){
                    if(current_char == '.')i--;
                    current_word+=current_char;
                    current_char = chars[++i];
                }
                i--;
                //the current_word is digit
                Pair<String,String> p = new Pair<>(current_word,"number");
                wordsClassify.add(p);
                System.out.println(current_word+"\t5"+"\tnumber 常数");
            }
            else switch(current_char){
                    case '+':
                    case '-':
                    case '*':
                    case '#':
                    case '/':
                        Pair<String,String> p = new Pair<>(String.valueOf(current_char),"operator");
                        wordsClassify.add(p);
                        System.out.println(current_char+"\t2"+"\toperator 操作符");
                        break;
                    case '(':
                    case ')':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                    case ';':
                    case '.':
                        p = new Pair<>(String.valueOf(current_char),"delimiter");
                        wordsClassify.add(p);
                        System.out.println(current_char+"\t3"+"\tdelimiter 分隔符");
                        break;
                    case '=':{
                        current_char = chars[++i];
                        if(current_char == '='){
                            p = new Pair<>("==","operator");
                            wordsClassify.add(p);
                            System.out.println("==\t2\toperator 操作符");
                        }
                        else{
                            p = new Pair<>("=","operator");
                            wordsClassify.add(p);
                            System.out.println("=\t2\toperator 操作符");
                            i--;
                        }
                    }break;
                    case ':':{
                        current_char = chars[++i];
                        if(current_char == '='){
                            p = new Pair<>(":=","operator");
                            wordsClassify.add(p);
                            System.out.println(":=\t2\toperator 操作符");
                        }
                        else{
                            p = new Pair<>(":","operator");
                            wordsClassify.add(p);
                            System.out.println(":\t2\toperator 操作符");
                            i--;
                        }
                    }break;
                    case '>':{
                        current_char = chars[++i];
                        if(current_char == '='){
                            p = new Pair<>(">=","operator");
                            wordsClassify.add(p);
                            System.out.println(">=\t2\toperator 操作符");
                        }
                        else{
                            p = new Pair<>(">","operator");
                            wordsClassify.add(p);
                            System.out.println(">\t2\toperator 操作符");
                            i--;
                        }
                    }break;
                    case '<':{
                        current_char = chars[++i];
                        if(current_char == '='){
                            p = new Pair<>("<=","operator");
                            wordsClassify.add(p);
                            System.out.println("<=\t2\toperator 操作符");
                        }
                        else{
                            p = new Pair<>("<","operator");
                            wordsClassify.add(p);
                            System.out.println("<\t2\toperator 操作符");
                            i--;
                        }
                    }break;
                    default:{
                        p = new Pair<>(String.valueOf(current_char),"No Identifier");
                        wordsClassify.add(p);
                        System.out.println(current_char+"\t6\tNo Identifier 非法字符");
                    }
            }
        }
    }
}
