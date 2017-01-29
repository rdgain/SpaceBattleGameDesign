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
		
		int line = 7;
		int reEvaluate = 100;
		if(args.length>0)
		{
			line = Integer.parseInt(args[0]);
			
			if(args.length>1)
				reEvaluate = Integer.parseInt(args[1]);
		}
		
		try {
			int index=0;
			BufferedReader b = new BufferedReader(new FileReader(new File("finalNTuple.txt")));
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
		
//		 try {
//			 String line, prev = "";
//			 double max1 = 0, max2 = 0;
//			 String maxs1 = "", maxs2 = "";
//			 
//	            BufferedReader br = new BufferedReader(new FileReader("file.txt"));
//	            while ((line = br.readLine()) != null) {
//	                if (Objects.equals(prev, "")) prev = line;
//	                if (line.contains("Best fitness:")) {
//	                //	System.out.println(prev.split(":")[1].replace("[", "").replace("]", "").replaceAll(" ", ""));
//	                    double fitness = Double.parseDouble(line.split(":")[1]);
//	              //      ss1.add(fitness);
//	                    if (fitness > max1) {
//	                        max1 = fitness;
//	                        maxs1 = prev.split(":")[1];
//	                    }
//	                }
//	                if (line.contains("Final fitness:")) {
//	                	System.out.println(prev.split(":")[1].replace("[", "").replace("]", "").replaceAll(" ", ""));
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
