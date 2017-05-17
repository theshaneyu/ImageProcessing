import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Math;

import javax.imageio.ImageIO;

public class NoiseReduction {
	static int iset = 0; // flag
	static float gset;
	static float StandardDeviation;
	
	public static float RanGenerator(){
		float fac, rsq, v1, v2;
		
		if(iset == 0){
			do{
//				System.out.println(Math.random());
				v1 = (float)(2.0 * Math.random() - 1.0);
				v2 = (float)(2.0 * Math.random() - 1.0);
				rsq = v1 * v1 + v2 * v2;
			}while(rsq >= 1.0 || rsq == 0.0);
			
			fac = (float)(Math.sqrt(-2.0 * Math.log(rsq) / rsq));
			gset = v1 * fac;
			iset = 1;
			
			return v2 * fac;
		}else{
			iset = 0;
			return gset;
			
		}		
	}
	
	public static int ValSecure(int val){
		if(val > 0) val = Math.min(255, val);
        else val = Math.max(0, val);
		
		return val;
	}

	public static BufferedImage AddNoise(BufferedImage img, BufferedImage OutputImg, int width, int height){
		for (int w = 0 ; w < width ; w++) {
		      for (int h = 0 ; h < height ; h++) {
		    	  Color c = new Color(img.getRGB(w, h));
		    	  float RanRed = Math.round(RanGenerator() * (float)Math.sqrt(1000));
		    	  float RanGreen = Math.round(RanGenerator() * (float)Math.sqrt(1000));
		    	  float RanBlue = Math.round(RanGenerator() * (float)Math.sqrt(1000));
		    	  
		    	  int ProcessedRed = c.getRed() + (int)RanRed;
		    	  int ProcessedGreen = c.getGreen() + (int)RanGreen;
		    	  int ProcessedBlue = c.getBlue() + (int)RanBlue;
		    	  
		    	  ProcessedRed = ValSecure(ProcessedRed);
		    	  ProcessedGreen = ValSecure(ProcessedGreen);
		    	  ProcessedBlue = ValSecure(ProcessedBlue);
		    	  
		    	  Color result = new Color(ProcessedRed, ProcessedGreen, ProcessedBlue);
		    	  int rgb = result.getRGB();
		    	  OutputImg.setRGB(w, h, rgb);
		      }
		}
		return OutputImg;
	}
	
	// ¤À¥À9®c®æªºÅÜ²§¼Æ­pºâ ª©¥»¤@
	public static float[] SquareVariance(int[] ArrRed, int[] ArrGreen, int[] ArrBlue){
		float SumRed = 0, SumGreen = 0, SumBlue = 0;
		float SquareSumR = 0, SquareSumG = 0, SquareSumB = 0;
		
		for(int i = 0 ; i < 9 ; i++){
			SumRed += ArrRed[i];
			SumGreen += ArrGreen[i];
			SumBlue += ArrBlue[i];	
		}
		
		float AvgRed = SumRed / 9;
		float AvgGreen = SumRed / 9;
		float AvgBlue = SumRed / 9;
		
		for(int i = 0 ; i < 9 ; i++){
			SquareSumR += ((float)ArrRed[i] - AvgRed) * ((float)ArrRed[i] - AvgRed);
			SquareSumG += ((float)ArrGreen[i] - AvgGreen) * ((float)ArrGreen[i] - AvgGreen);
			SquareSumB += ((float)ArrBlue[i] - AvgBlue) * ((float)ArrBlue[i] - AvgBlue);
		}
		
		float VarR = SquareSumR / 9;
		float VarG = SquareSumR / 9;
		float VarB = SquareSumR / 9;
		
		float[] VarRGB = {VarR, VarG, VarB};
		
		return VarRGB;
	}
	
	//¤À¥À9®c®æªºÅÜ²§¼Æ­pºâ ª©¥»¤G
	public static float[] SquareVariance2(int[] ArrRed, int[] ArrGreen, int[] ArrBlue){
		float SumRed = 0, SumGreen = 0, SumBlue = 0;
		float SumRed_sqr = 0, SumGreen_sqr = 0, SumBlue_sqr = 0;
		
		float AvgR_sqr, AvgG_sqr, AvgB_sqr;
		float AvgR, AvgG, AvgB;
		
		for(int i = 0 ; i < 9 ; i++){
			SumRed_sqr += ArrRed[i] * ArrRed[i];
			SumGreen_sqr += ArrGreen[i] * ArrGreen[i];
			SumBlue_sqr += ArrBlue[i] * ArrBlue[i];
			
			SumRed += ArrRed[i];
			SumGreen += ArrGreen[i];
			SumBlue += ArrBlue[i];
		}
		
		AvgR_sqr = SumRed_sqr / 9;  //¥­¤è©Mªº¥­§¡
		AvgG_sqr = SumGreen_sqr / 9;
		AvgB_sqr = SumBlue_sqr / 9;
		
		AvgR = SumRed / 9; //¥­§¡
		AvgG = SumGreen / 9;
		AvgB = SumBlue / 9;
		
		float ResultR = AvgR_sqr - AvgR*AvgR;
		float ResultG = AvgG_sqr - AvgG*AvgG;
		float ResultB = AvgB_sqr - AvgB*AvgB;
		
		float[] Result = {ResultR, ResultG, ResultB};
		
		return Result;
	}
	
	
	
	public static BufferedImage Reduction(BufferedImage img, BufferedImage OutputImg, int width, int height){
		int n = 0;
		for (int w = 0 ; w < width ; w++) {
		      for (int h = 0 ; h < height ; h++) {
		    	  
		    	  if (w == 0 || w == width - 1 || h == 0 || h == height - 1){
//		    		  System.out.println("test "+n); n++;
//		    		  System.out.println(w + "  " +h);
		    		  Color Pass = new Color(img.getRGB(w, h));
                      int rgb = Pass.getRGB();
                      OutputImg.setRGB(w, h, rgb);

		    	  }else{
		    		  int[] r = new int[9];
		    		  int[] g = new int[9];
		    		  int[] b = new int[9];
		    		  
		    	      //¥ª¤W
                      Color c1 = new Color(img.getRGB((w-1), (h-1)));
                      r[1] = c1.getRed();
                      g[1] = c1.getGreen();
                      b[1] = c1.getBlue();
		    		  
                      //¥¿¤W
                      Color c2 = new Color(img.getRGB(w, (h-1)));
                      r[2] = c2.getRed();
                      g[2] = c2.getGreen();
                      b[2] = c2.getBlue();
                      
                      //¥k¤W
                      Color c3 = new Color(img.getRGB((w+1), (h-1)));
                      r[3] = c3.getRed();
                      g[3] = c3.getGreen();
                      b[3] = c3.getBlue();
                      
                      //¥¿¥ª
                      Color c4 = new Color(img.getRGB((w-1), h));
                      r[4] = c4.getRed();
                      g[4] = c4.getGreen();
                      b[4] = c4.getBlue();
                      
                      //¥¿¥k³
                      Color c5 = new Color(img.getRGB((w+1), h));
                      r[5] = c5.getRed();
                      g[5] = c5.getGreen();
                      b[5] = c5.getBlue();
                      
                      //¥ª¤U
                      Color c6 = new Color(img.getRGB((w-1), (h+1)));
                      r[6] = c6.getRed();
                      g[6] = c6.getGreen();
                      b[6] = c6.getBlue();
                      
                      //¥¿¤U
                      Color c7 = new Color(img.getRGB(w, (h+1)));
                      r[7] = c7.getRed();
                      g[7] = c7.getGreen();
                      b[7] = c7.getBlue();
                      
                      //¥k¤U
                      Color c8 = new Color(img.getRGB((w+1), (h+1)));
                      r[8] = c8.getRed();
                      g[8] = c8.getGreen();
                      b[8] = c8.getBlue();
                      
                      //­ìÂI
                      Color c0 = new Color(img.getRGB(w, h));
                      r[0] = c0.getRed();
                      g[0] = c0.getGreen();
                      b[0] = c0.getBlue();
                      
                      int[] ArrRed = {r[1], r[2], r[3], r[4], r[5], r[6], r[7], r[8], r[0]}; //¬õ¦â ¾F©~+¦Û¤v
                      int[] ArrGreen = {g[1], g[2], g[3], g[4], g[5], g[6], g[7], g[8], g[0]};
                      int[] ArrBlue = {b[1], b[2], b[3], b[4], b[5], b[6], b[7], b[8], b[0]};
                      
                      float mR = (float)(r[1] + r[2] + r[3] + r[4] + r[5] + r[6] + r[7] + r[8] + r[0]) / (float)9;
                      float mG = (float)(g[1] + g[2] + g[3] + g[4] + g[5] + g[6] + g[7] + g[8] + g[0]) / (float)9;
                      float mB = (float)(b[1] + b[2] + b[3] + b[4] + b[5] + b[6] + b[7] + b[8] + b[0]) / (float)9;
                      
//                      System.out.println(mR+" "+mG+" "+mB);
//                      mR = ValSecure((int)Math.round(mR));
//                      mG = ValSecure((int)Math.round(mG));
//                      mB = ValSecure((int)Math.round(mB));
//                      System.out.println(mR+" "+mG+" "+mB);
//                      Color result_reduction = new Color((int)mR, (int)mG, (int)mB);
//                      int rgb2 = result_reduction.getRGB();
//                      OutputImg.setRGB(w, h, rgb2);
                      

                      float[] VarRGB = SquareVariance2(ArrRed, ArrGreen, ArrBlue);
                      int ResultR = (int)Math.round(((float)r[0] - (1000 * ((float)r[0] - mR) / VarRGB[0])));
                      int ResultG = (int)Math.round(((float)g[0] - (1000 * ((float)g[0] - mG) / VarRGB[1])));
                      int ResultB = (int)Math.round(((float)b[0] - (1000 * ((float)b[0] - mB) / VarRGB[2])));
                      
                      ResultR = ValSecure(ResultR);
                      ResultG = ValSecure(ResultG);
                      ResultB = ValSecure(ResultB);
                    
                      Color result_reduction = new Color(ResultR, ResultG, ResultB);
                      int rgb2 = result_reduction.getRGB();
                      OutputImg.setRGB(w, h, rgb2);
               
		    	  }//end of else
		      }
		}
		return OutputImg;
	}
	
	
	
	public static void main(String[] args) {
//		for(;;){System.out.println(RanGenerator());}
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File("lina.jpg"));
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage OutputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		OutputImg = AddNoise(img, OutputImg, width, height); //¥[¤W°ª´µÂø°Tªºµ²ªG
		
		BufferedImage OutputImg_Reduction = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		OutputImg_Reduction = Reduction(OutputImg, OutputImg_Reduction, width, height); //¥hÂø°Tªºµ²ªG
		
		
		try{
			File outputfile = new File("NoisyResult.jpg");
			ImageIO.write(OutputImg, "jpg", outputfile);
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		try{
			File outputfile2 = new File("NoiseReduction.jpg");
			ImageIO.write(OutputImg_Reduction, "jpg", outputfile2);
		}catch(IOException e){
			System.out.println(e.toString());
		}
		

	}
}
