import java.awt.Color;
import java.awt.image.BufferedImage;
//import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;
import java.lang.Math;

public class Sharpener {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Sharpen Rate: ");
		float rate = sc.nextFloat();
		
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File("dog.jpg"));
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage OutputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BufferedImage OutputImg_sobel = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BufferedImage OutputImg_mean = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BufferedImage OutputImg_nor = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BufferedImage OutputImg_final = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		
		for (int w = 0 ; w < width ; w++) {
		      for (int h = 0 ; h < height ; h++) {
		    	  
		    	  if (w == 0 || w == width - 1 || h == 0 || h == height - 1){
		    		  //System.out.println("test");
		    		  Color Pass = new Color(img.getRGB(w, h));
                      int rgb = Pass.getRGB();
                      OutputImg.setRGB(w, h, rgb);
		    		  
		    	  }else{
		    		  int[] r = new int[9];
		    		  int[] g = new int[9];
		    		  int[] b = new int[9];
                      
                      float vR, vG, vB; //for laplacian
                      int cR, cG, cB;
                      
                      //左上
                      Color c1 = new Color(img.getRGB((w-1), (h-1)));
                      r[1] = c1.getRed();
                      g[1] = c1.getGreen();
                      b[1] = c1.getBlue();
		    		  
                      //正上
                      Color c2 = new Color(img.getRGB(w, (h-1)));
                      r[2] = c2.getRed();
                      g[2] = c2.getGreen();
                      b[2] = c2.getBlue();
                      
                      //右上
                      Color c3 = new Color(img.getRGB((w+1), (h-1)));
                      r[3] = c3.getRed();
                      g[3] = c3.getGreen();
                      b[3] = c3.getBlue();
                      
                      //正左
                      Color c4 = new Color(img.getRGB((w-1), h));
                      r[4] = c4.getRed();
                      g[4] = c4.getGreen();
                      b[4] = c4.getBlue();
                      
                      //正右
                      Color c5 = new Color(img.getRGB((w+1), h));
                      r[5] = c5.getRed();
                      g[5] = c5.getGreen();
                      b[5] = c5.getBlue();
                      
                      //左下
                      Color c6 = new Color(img.getRGB((w-1), (h+1)));
                      r[6] = c6.getRed();
                      g[6] = c6.getGreen();
                      b[6] = c6.getBlue();
                      
                      //正下
                      Color c7 = new Color(img.getRGB(w, (h+1)));
                      r[7] = c7.getRed();
                      g[7] = c7.getGreen();
                      b[7] = c7.getBlue();
                      
                      //右下
                      Color c8 = new Color(img.getRGB((w+1), (h+1)));
                      r[8] = c8.getRed();
                      g[8] = c8.getGreen();
                      b[8] = c8.getBlue();
                      
                      //原點
                      Color c0 = new Color(img.getRGB(w, h));
                      r[0] = c0.getRed();
                      g[0] = c0.getGreen();
                      b[0] = c0.getBlue();
                      
                      
                      vR = (float)r[0] - (float)(r[1] + r[2] + r[3] + r[4] + r[5] + r[6] + r[7] + r[8]) / 8 ;
                      vG = (float)g[0] - (float)(g[1] + g[2] + g[3] + g[4] + g[5] + g[6] + g[7] + g[8]) / 8 ;
                      vB = (float)b[0] - (float)(b[1] + b[2] + b[3] + b[4] + b[5] + b[6] + b[7] + b[8]) / 8 ;
                      
                      //加原圖
                      //vR = (float)r[0] + vR * rate ;
                      //vG = (float)g[0] + vG * rate;
                      //vB = (float)b[0] + vB * rate;
                      
                      if(vR > 0) vR = Math.min(255, vR);
                      else vR = Math.max(0, vR);
                      
                      if(vG > 0) vG = Math.min(255, vG);
                      else vG = Math.max(0, vG);
                      
                      if(vB > 0) vB = Math.min(255, vB);
                      else vB = Math.max(0, vB);
                      
                      //------------------------------------
                      
                      cR = Math.abs((r[6] + 2*r[7] + r[8]) - (r[1] + 2*r[2] + r[3])) + Math.abs((r[3] + 2*r[5] + r[8]) - (r[1] + 2*r[4] + r[6]));
                      cG = Math.abs((g[6] + 2*g[7] + g[8]) - (g[1] + 2*g[2] + g[3])) + Math.abs((g[3] + 2*g[5] + g[8]) - (g[1] + 2*g[4] + g[6]));
                      cB = Math.abs((b[6] + 2*b[7] + b[8]) - (b[1] + 2*b[2] + b[3])) + Math.abs((b[3] + 2*b[5] + b[8]) - (b[1] + 2*b[4] + b[6]));
                      
                      if(cR > 0) cR = Math.min(255, cR);
                      else cR = Math.max(0, cR);
                      
                      if(cG > 0) cG = Math.min(255, cG);
                      else cG = Math.max(0, cG);
                      
                      if(cB > 0) cB = Math.min(255, cB);
                      else cB = Math.max(0, cB);
                      
                      Color result = new Color((int)Math.round(vR), (int)Math.round(vG), (int)Math.round(vB));
                      int rgb = result.getRGB();
                      OutputImg.setRGB(w, h, rgb);
                      
                      Color result_sobel = new Color(cR, cG, cB);
                      int rgb_sobel = result_sobel.getRGB();
                      OutputImg_sobel.setRGB(w, h, rgb_sobel);
                      
                      
                      
		    	  } //end of else
		    	  
		      } //end of h 
		} //end of w
		
		float maxR = 0, minR = 255, maxG = 0, minG = 255, maxB = 0, minB = 255, stdR, stdG, stdB;
		//Mean Filter
		for (int w = 0 ; w < width ; w++) {
		      for (int h = 0 ; h < height ; h++) {
		    	  if (w == 0 || w == width - 1 || h == 0 || h == height - 1){
		    		  
		    		  Color Pass = new Color(OutputImg_sobel.getRGB(w, h));
                      int rgb = Pass.getRGB();
                      OutputImg_mean.setRGB(w, h, rgb);
		    		  
		    	  }else{
		    		  int[] r = new int[9];
		    		  int[] g = new int[9];
		    		  int[] b = new int[9];
                      
                      float mR, mG, mB;
                      
                      //左上
                      Color c1 = new Color(OutputImg_sobel.getRGB((w-1), (h-1)));
                      r[1] = c1.getRed();
                      g[1] = c1.getGreen();
                      b[1] = c1.getBlue();
		    		  
                      //正上
                      Color c2 = new Color(OutputImg_sobel.getRGB(w, (h-1)));
                      r[2] = c2.getRed();
                      g[2] = c2.getGreen();
                      b[2] = c2.getBlue();
                      
                      //右上
                      Color c3 = new Color(OutputImg_sobel.getRGB((w+1), (h-1)));
                      r[3] = c3.getRed();
                      g[3] = c3.getGreen();
                      b[3] = c3.getBlue();
                      
                      //正左
                      Color c4 = new Color(OutputImg_sobel.getRGB((w-1), h));
                      r[4] = c4.getRed();
                      g[4] = c4.getGreen();
                      b[4] = c4.getBlue();
                      
                      //正右
                      Color c5 = new Color(OutputImg_sobel.getRGB((w+1), h));
                      r[5] = c5.getRed();
                      g[5] = c5.getGreen();
                      b[5] = c5.getBlue();
                      
                      //左下
                      Color c6 = new Color(OutputImg_sobel.getRGB((w-1), (h+1)));
                      r[6] = c6.getRed();
                      g[6] = c6.getGreen();
                      b[6] = c6.getBlue();
                      
                      //正下
                      Color c7 = new Color(OutputImg_sobel.getRGB(w, (h+1)));
                      r[7] = c7.getRed();
                      g[7] = c7.getGreen();
                      b[7] = c7.getBlue();
                      
                      //右下
                      Color c8 = new Color(OutputImg_sobel.getRGB((w+1), (h+1)));
                      r[8] = c8.getRed();
                      g[8] = c8.getGreen();
                      b[8] = c8.getBlue();
                      
                      //原點
                      Color c0 = new Color(OutputImg_sobel.getRGB(w, h));
                      r[0] = c0.getRed();
                      g[0] = c0.getGreen();
                      b[0] = c0.getBlue();
                      
                      mR = (float)(r[1] + r[2] + r[3] + r[4] + r[5] + r[6] + r[7] + r[8] + r[0]) / 9;
                      mG = (float)(g[1] + g[2] + g[3] + g[4] + g[5] + g[6] + g[7] + g[8] + g[0]) / 9;
                      mB = (float)(b[1] + b[2] + b[3] + b[4] + b[5] + b[6] + b[7] + b[8] + b[0]) / 9;
                      
                      if(mR > 0) mR = Math.min(255, mR);
                      else mR = Math.max(0, mR);
                      
                      if(mG > 0) mG = Math.min(255, mG);
                      else mG = Math.max(0, mG);
                      
                      if(mB > 0) mB = Math.min(255, mB);
                      else mB = Math.max(0, mB);
                      
                      Color result_mean = new Color((int)mR, (int)mG, (int)mB);
                      int rgb = result_mean.getRGB();
                      OutputImg_mean.setRGB(w, h, rgb);
                      //System.out.println(mR + " " + mG + " " + mB);
                      
                      //System.out.println(mR + " " + mG + " " + mB);
                      
                      
                      //正規化
                      if(mR > maxR) maxR = mR;
                      if(mR < minR) minR = mR;
                      if(mG > maxG) maxG = mG;
                      if(mG < minG) minG = mG;
                      if(mB > maxB) maxB = mB;
                      if(mB < minB) minB = mB;
                      
                      stdR = maxR - minR;
                      stdG = maxG - minG;
                      stdB = maxB - minB;
                      
                      //System.out.println(stdR + " " + stdG + " " + stdB);
                      
                      float PtgR = mR / stdR;
                      float PtgG = mG / stdG;
                      float PtgB = mB / stdB;
                      
                      //System.out.println(PtgR + " " + PtgG + " " + PtgB);
                      
                      Color NorImage = new Color(OutputImg.getRGB(w, h));
                      Color OriImage = new Color(img.getRGB(w, h));
                      
                      float NorRed = (float)NorImage.getRed() * PtgR;
                      float NorGreen = (float)NorImage.getGreen() * PtgG;
                      float NorBlue = (float)NorImage.getBlue() * PtgB;
                      
                      
                      
                      if(NorRed > 0) NorRed = Math.min(255, NorRed);
                      else NorRed = Math.max(0, NorRed);
                      
                      if(NorGreen > 0) NorGreen = Math.min(255, NorGreen);
                      else NorGreen = Math.max(0, NorGreen);
                      
                      if(NorBlue > 0) NorBlue = Math.min(255, NorBlue);
                      else NorBlue = Math.max(0, NorBlue);
                      
                      
                      
                      Color result_nor = new Color((int)NorRed, (int)NorGreen, (int)NorBlue);
                      int rgb3 = result_nor.getRGB();
                      OutputImg_nor.setRGB(w, h, rgb3);
                      
                      
                      //System.out.println(NorRed + " " + NorGreen +" " + NorBlue);
                      
                      /*
                      if(NorRed > 0) NorRed = Math.min(255, NorRed);
                      else NorRed = Math.max(0, NorRed);
                      
                      if(NorGreen > 0) NorGreen = Math.min(255, NorGreen);
                      else NorGreen = Math.max(0, NorGreen);
                      
                      if(NorBlue > 0) NorBlue = Math.min(255, NorBlue);
                      else NorBlue = Math.max(0, NorBlue); */
                      
                      
                      
                      
                      float FinalRed = NorRed + (float)OriImage.getRed();
                      float FinalGreen = NorGreen + (float)OriImage.getGreen();
                      float FinalBlue = NorBlue + (float)OriImage.getBlue();
                      
                      
                      if(FinalRed > 0) FinalRed = Math.min(255, FinalRed);
                      else FinalRed = Math.max(0, FinalRed);
                      
                      if(FinalGreen > 0) FinalGreen = Math.min(255, FinalGreen);
                      else FinalGreen = Math.max(0, FinalGreen);
                      
                      if(FinalBlue > 0) FinalBlue = Math.min(255, FinalBlue);
                      else FinalBlue = Math.max(0, FinalBlue);
                      
                      
                      Color result_final = new Color((int)FinalRed, (int)FinalGreen, (int)FinalBlue);
                      int rgb4 = result_final.getRGB();
                      OutputImg_final.setRGB(w, h, rgb4);
                      
                      
                      
                      
                      
                      
                      
                      
		      }
		   }
		} //end of for
		
		
		
		
		
		
		try{
			File outputfile = new File("result.jpg");
			ImageIO.write(OutputImg, "jpg", outputfile);
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		try{
			File outputfile2 = new File("result_sobel.jpg");
			ImageIO.write(OutputImg_sobel, "jpg", outputfile2);
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		try{
			File outputfile3 = new File("result_mean.jpg");
			ImageIO.write(OutputImg_mean, "jpg", outputfile3);
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		try{
			File outputfile4 = new File("result_nor.jpg");
			ImageIO.write(OutputImg_nor, "jpg", outputfile4);
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		try{
			File outputfile5 = new File("result_final.jpg");
			ImageIO.write(OutputImg_final, "jpg", outputfile5);
		}catch(IOException e){
			System.out.println(e.toString());
		}

		
		
		
		
		
	}
}
