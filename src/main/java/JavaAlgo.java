package main.java;

import java.io.File;

import main.java.method.BnB.BranchAndBound;
import main.java.method.LS.HillClimbing;

public class JavaAlgo {
    public static void main(String[] args) {
    	System.out.println("running on..." + System.getProperty("user.dir"));
        File file=new File("./output");
        if(!file.exists()){//if the dir not exist
            file.mkdir();//create the dir
        }
        try {
        	String filename = "";
        	String algoName = "";
            int time = 10;
            int seed = 0;
            
        	for (int i = 0; i < args.length; i+=2) {

        		switch (args[i]) {
				case "-inst":
					filename = args[i + 1];
					break;
				case "-alg":
					algoName = args[i + 1];
					break;
				case "-time":
					time = Integer.parseInt(args[i + 1]);
					break;
				case "-seed":
					seed = Integer.parseInt(args[i + 1]);
					break;
				default:
					System.out.println("ERROR: invalid command arguments");
					break;
				}
        	}
            System.out.println(algoName);
            Algo algo = null;
            
            String graphPrefix = filename.substring(0, filename.length() - 6);
            String inFileName = "./data/" + filename;
            String outSol = "./output/";
            String outTrace = "./output/";
            
            switch (algoName){
                case "BnB":
                    algo = new BranchAndBound();
                    outSol = outSol+graphPrefix+"_BnB_"+time+".sol";
                    outTrace = outTrace+graphPrefix+"_BnB_"+time+".trace";
                    break;
                case "LS":
                    algo = new HillClimbing();
                    outSol = outSol+graphPrefix+"_LS_"+time+"_"+seed+".sol";
                    outTrace = outTrace+graphPrefix+"_LS_"+time+"_"+seed+".trace";
                    break;
                default:
                    System.out.println("ERROR: unimplemented algorithm");
            }
            algo.run(inFileName, outSol, outTrace, time, seed);

        }catch (Exception e){
            System.out.println("Wrong arguments: " + e.toString());
        }
    }
}