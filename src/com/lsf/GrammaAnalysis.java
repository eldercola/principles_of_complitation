package com.lsf;

import javafx.util.Pair;

import java.util.Vector;

public class GrammaAnalysis {
    public Vector<Pair<String,String>> v;
    public Vector<Vector<Pair<String,String>>> total;
    public int lookahead = 0;
    public int line = 1;
    public GrammaAnalysis(Vector<Pair<String,String>> v){
        this.v = v;
    }
    public void Start(){
        this.total = getLine();
        for(int i = 0;i<total.size();i++){
            lookahead = 0;
            line = i+1;
            this.v = total.elementAt(i);
            ParseS();
        }
        System.out.println("Accepted");
    }
    public Vector<Vector<Pair<String,String>>> getLine(){
        Vector<Vector<Pair<String,String>>> vs = new Vector<>();
        Vector<Pair<String,String>> temp = new Vector<>();
        for(int i = 0;i<v.size();i++){
            temp.addElement(v.elementAt(i));
            if(i==v.size()-1||v.elementAt(i).getKey().equals(";")){
                vs.addElement(temp);
                temp = new Vector<>();
            }
        }
        return vs;
    }
    public void MatchToken(String expected){
        if(QuickCheckType(expected)||QuickCheckKey(expected)){
            lookahead++;
            /*
            if(lookahead>=v.size()){
                System.out.println("Accepted!");
                System.exit(0);
            }
             */
        }
        else{
            QuickError("Match");
        }
    }
    public String QuickMap(String inWhich){
        if(inWhich.equals("Match")){
            return "匹配单个字符错误";
        }
        else if(inWhich.equals("S")){
            return "开头不为常数、标识符或(";
        }
        else if(inWhich.equals("A")){
            return "此处应为常数或标识符";
        }
        else if(inWhich.equals("T")||inWhich.equals("E")||inWhich.equals("F")){
            return "此处应为常数、标识符或(";
        }
        else if(inWhich.equals("F_")){
            return "此处应为*,/或),+,-,;";
        }
        else if(inWhich.equals("E_")){
            return "此处应为+,-,)或;";
        }
        else{
            return "未知错误";
        }
    }
    public boolean QuickCheckType(String expected){
        if(v.elementAt(lookahead).getValue().equals(expected))return true;
        return false;
    }
    public boolean QuickCheckKey(String expected){
        if(v.elementAt(lookahead).getKey().equals(expected))return true;
        return false;
    }
    public void QuickError(String inWhich){
        System.out.println("line: "+line+" position: "+(lookahead+1)+" 你的输入:"+total.elementAt(line-1).elementAt(lookahead).getKey()+" "+QuickMap(inWhich));
        for(int i = 0;i<total.elementAt(line-1).size();i++){
            System.out.print(total.elementAt(line-1).elementAt(i).getKey());
        }
        System.exit(1);
    }
    // S = E;
    // SELECT(S->E;) = FIRST(E)
    public void ParseS(){
        if(QuickCheckType("number")||QuickCheckType("identifier")||QuickCheckKey("(")){
            ParseE();
            MatchToken(";");
        }
        else{
            QuickError("S");
        }
    }
    // A=<digit>|identifier
    // SELECT(A-><digit>) = {<digit>}
    // SELECT(A->identifier) = {identifier}
    public void ParseA(){
        if(QuickCheckType("number")){
            MatchToken("number");
        }
        else if(QuickCheckType("identifier")){
            MatchToken("identifier");
        }
        else{
            QuickError("A");
        }
    }
    // T = A|(E)
    // SELECT(T->A) = {<digit>,identifier}
    // SELECT(T->(E))={"("}
    public void ParseT(){
        if(QuickCheckType("number")||QuickCheckType("identifier")){
            ParseA();
        }
        else if(QuickCheckKey("(")){
            MatchToken("(");
            ParseE();
            MatchToken(")");
        }
        else{
            QuickError("T");
        }
    }
    // E = FE'
    // SELECT(E->FE') = {<digit>,identifier,"("}
    public void ParseE(){
        if(QuickCheckType("number")||QuickCheckType("identifier")||QuickCheckKey("(")){
            ParseF();
            ParseE_();
        }
        else{
            QuickError("E");
        }
    }
    // F = TF'
    // SELECT(F->TF') = {<digit>,identifier,"("}
    public void ParseF(){
        if(QuickCheckType("number")||QuickCheckType("identifier")||QuickCheckKey("(")){
            ParseT();
            ParseF_();
        }
        else{
            QuickError("F");
        }
    }
    // F' = *TF' | /TF' | NULL
    // SELECT(F'->*TF') = {"*"}
    // SELECT(F'->/TF') = {"/"}
    // SELECT(F'->NULL) = FOLLOW(F') = {"+","-",";"}
    public void ParseF_(){
        if(QuickCheckKey("*")){
            MatchToken("*");
            ParseT();
            ParseF_();
        }
        else if(QuickCheckKey("/")){
            MatchToken("/");
            ParseT();
            ParseF_();
        }
        else if(QuickCheckKey(")")||QuickCheckKey("+")||QuickCheckKey("-")||QuickCheckKey(";")){

        }
        else{
            QuickError("F_");
        }
    }
    // E' = +FE' | -FE' | NULL
    // FIRST(E')={"+","-"}
    // FOLLOW(E')={";"}
    // SELECT(E'->+FE') = {"+"}
    // SELECT(E'->-FE') = {"-"}
    // SELECT(E'->NULL) = {";",")"}
    public void ParseE_(){
        if(QuickCheckKey("+")){
            MatchToken("+");
            ParseF();
            ParseE_();
        }
        else if(QuickCheckKey("-")){
            MatchToken("-");
            ParseF();
            ParseE_();
        }
        else if(QuickCheckKey(";")||QuickCheckKey(")")){

        }
        else{
            QuickError("E_");
        }
    }
}
