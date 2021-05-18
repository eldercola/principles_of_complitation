package com.lsf;

import javafx.util.Pair;

import java.util.Vector;

public class GrammaAnalysis {
    public Vector<Pair<String,String>> v;
    public int lookahead = 0;
    public GrammaAnalysis(Vector<Pair<String,String>> v){
        this.v = v;
    }
    public void Start(){
        ParseE();
    }
    public void MatchToken(String expected){
        if(QuickCheckType(expected)||QuickCheckKey(expected)){
            lookahead++;
            if(lookahead>=v.size())System.exit(0);
        }
        else{
            QuickError("Match");
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
        System.out.println(inWhich+" "+lookahead+" Syntax error!");
        System.exit(1);
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
            //MatchToken("#");
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
    // SELECT(F'->NULL) = FOLLOW(F') = {"+","-","#"}
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
        else if(QuickCheckKey("+")||QuickCheckKey("-")){

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
    // SELECT(E'->NULL) = {"#"}
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
        else if(QuickCheckKey(";")){

        }
        else{
            QuickError("E_");
        }
    }
}
