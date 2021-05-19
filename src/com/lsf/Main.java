package com.lsf;

import javafx.util.Pair;

import java.io.File;
import java.io.FileReader;
import java.util.Vector;

public class Main {

    public static void main(String[] args)throws Exception {
	// write your code here
        WordAnalysis boot = new WordAnalysis();
        File file = new File("/home/linshengfeng/Principles_of_Compilation/add.txt");
        FileReader reader = new FileReader(file);
        int length = (int)file.length();
        char buf[] = new char[length+1];
        reader.read(buf);
        reader.close();
        boot.analysis(buf);
        Vector<Pair<String,String>> words = boot.getWordsClassify();
        System.out.println("");
        GrammaAnalysis grammaBoot = new GrammaAnalysis(words);
        grammaBoot.Start();
    }
}
