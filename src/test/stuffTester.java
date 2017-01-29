package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import evodef.SearchSpace;
import evodef.SolutionEvaluator;
import test.ntuple.TenFitnessEval;
import tools.StatSummary;
import tools.Utils;

public class stuffTester {

	public static SearchSpace searchSpace = new SpaceBattleGameSearchSpace();
	public static void main(String[] args) {
		
		//fileName = finalNTuple.txt, bestRMHC.txt, bestRMHC_MUT.txt
		String fileName = "finalNTuple.txt";
		int line = 7;
		int reEvaluate = 100;
		if(args.length>0)
		{
			fileName = args[0];
			if(args.length>1)
			line = Integer.parseInt(args[1]);
			
			if(args.length>2)
				reEvaluate = Integer.parseInt(args[2]);
		}
		System.out.println("re-evaluating: "+fileName+", line = "+line+", "+reEvaluate+" times");
		
		try {
			int index=0;
			BufferedReader b = new BufferedReader(new FileReader(new File(fileName)));
			String st = "";
			while(index++<=line)
			{
				st = b.readLine();
			}
			System.out.println("params = "+st);
			String[] split =  st.split(",");
			
			int[] params = new int[split.length];
			
			for(int i=0;i<params.length;i++)
			{
				params[i] = Integer.parseInt(split[i]);
			}
			
			StatSummary ss = new StatSummary();
			for(int i=0;i<reEvaluate;i++)
			{
				double fitness = getFitness(0,3,5,params,1);
				System.out.println("fitness = "+fitness);
				ss.add(fitness);
			}
			
			System.out.println(ss);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * Second chunk
		 * */
		
//		 try {
//			 String line, prev = "";
//			 double max1 = 0, max2 = 0;
//			 String maxs1 = "", maxs2 = "";
//			 
//	            BufferedReader br = new BufferedReader(new FileReader("results/results-Ntuple-3.txt"));
//	            while ((line = br.readLine()) != null) {
//	                if (Objects.equals(prev, "")) prev = line;
//	                if (line.contains("Best fitness:")) {
//	                	System.out.println(prev.split(":")[1].replace("[", "").replace("]", "").replaceAll(" ", ""));
//	                    double fitness = Double.parseDouble(line.split(":")[1]);
//	              //      ss1.add(fitness);
//	                    if (fitness > max1) {
//	                        max1 = fitness;
//	                        maxs1 = prev.split(":")[1];
//	                    }
//	                }
//	                if (line.contains("Final fitness:")) {
//	               // 	System.out.println(prev.split(":")[1].replace("[", "").replace("]", "").replaceAll(" ", ""));
//	                    double fitness = Double.parseDouble(line.split(":")[1]);
//	                //    ss2.add(fitness);
//	                    if (fitness > max2) {
//	                        max2 = fitness;
//	                        maxs2 = prev.split(":")[1];
//	                    }
//	                }
//	                prev = line;
//	            }
//
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
		
//			 String line, prev = "";
//			 double max1 = 0, max2 = 0;
//			 String maxs1 = "", maxs2 = "";
//		 
//	            BufferedReader br;
//				try {
//					br = new BufferedReader(new FileReader("results/results-RMHC-MUT-2.txt"));
//				
//					while ((line = br.readLine()) != null) {
//					    if (Objects.equals(prev, "")) prev = line;
////	                if (line.contains("Initialising")) {
////	                    if (!first)
////	                        ss1.add(max1);
////	                    else first = false;
////
////	                    if(max1 > max2) {
////	                        max2 = max1;
////	                        maxs2 = maxs1;
////	                    }
////	                    max1 = 0;
////	                }
//					    if (line.contains("50 (BestFitness:")) {
//					        double fitness = Double.parseDouble(line.split(":")[1].split("\\)")[0]);
//					     //   ss1.add(max1);
//					        System.out.println(prev.split(":")[1].replace("[", "").replace("]", "").replaceAll(" ", ""));
//					        if (fitness > max1) {
//					            max1 = fitness;
//					            maxs1 = prev.split(":")[1];
//					        }
//					    }
////	                if (line.contains("Final fitness")) {
////	                    double fitness = Double.parseDouble(line.split(":")[1]);
////	                    System.out.println(prev.split(":")[1].replace("[", "").replace("]", "").replaceAll(" ", ""));
////	                   // ss2.add(fitness);
////	                    if (fitness > max2) {
////	                        max2 = fitness;
////	                        maxs2 = prev.split(":")[1];
////	                    }
////	                }
//					    prev = line;
//					}
//				} catch (NumberFormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	            
	}
	
	private static double getFitness(int ai1, int ai2, int ai3, int[] params, int resample) {
        double[] res1 = GameDesign.playNWithParams(ai1, params, resample);
        double[] res2 = GameDesign.playNWithParams(ai2, params, resample);
        double[] res3 = GameDesign.playNWithParams(ai3, params, resample);

        int lowBound = 0, highBound = 100, bonus = 1000;

        double newPoints1 = (Utils.normalise(res1[1], lowBound, highBound) + res1[0] * bonus) - (Utils.normalise(res1[3], lowBound, highBound) + res1[2] * bonus);
        double newPoints2 = (Utils.normalise(res2[1], lowBound, highBound) + res2[0] * bonus) - (Utils.normalise(res2[3], lowBound, highBound) + res2[2] * bonus);
        double newPoints3 = (Utils.normalise(res3[1], lowBound, highBound) + res3[0] * bonus) - (Utils.normalise(res3[3], lowBound, highBound) + res3[2] * bonus);

        double compare32 = newPoints3 - newPoints2;
        double compare21 = newPoints2 - newPoints1;
        return Math.min(compare32, compare21);
    }

}
