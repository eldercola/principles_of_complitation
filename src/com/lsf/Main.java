package com.lsf;

import java.io.File;
import java.io.FileReader;

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
    }
}
