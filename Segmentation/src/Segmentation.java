import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;  


public class Segmentation {
	
	static Color c255 = new Color(255, 255, 255);
    static Color c0 = new Color(0, 0, 0);
    static int rgb_white = c255.getRGB();
    static int rgb_black = c0.getRGB();
	
	public static BufferedImage Binary(BufferedImage img, int width, int height){
		int[][] imgArr = new int[width][height];
		
		Raster raster = img.getData();
		
		for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {
	            imgArr[w][h] = raster.getSample(w, h, 0);
	        }
	    }
		
		BufferedImage OutputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int count = 0;
	    for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {
	        	if(imgArr[w][h] < 90){
	        		OutputImg.setRGB(w, h, rgb_black);
	        	}
	        	else{
	        		OutputImg.setRGB(w, h, rgb_white);
	        	}
	        }
	    }
	    	    
		return OutputImg;
	}
	
	
	public static boolean StructureElement(BufferedImage img,int x,int y,int size, int[][] image, int ColorSetting){
        if(size % 2 == 0){
            System.out.println("please input odd number");
            System.exit(0);
        }
        int width = img.getWidth();
        int height = img.getHeight();
        
        int x_upperbound = ((x+(size-1)/2) < width) ? (x+(size-1)/2) : width-1;
        int x_lowerbound = ((x-(size-1)/2) > 0) ? (x-(size-1)/2) : 0;
 
        int y_upperbound = ((y+(size-1)/2) < height) ? (y+(size-1)/2) : height-1;
        int y_lowerbound = ((y-(size-1)/2) > 0) ? (y-(size-1)/2) : 0;
            
        boolean boo=false;
        
        for(int i = x_lowerbound ; i < x_upperbound ; i++){
            for(int j = y_lowerbound ; j < y_upperbound ; j++){
            	
            		if(image[i][j] == ColorSetting){
            			return true;
            		}else{
            			continue;
            		}
            }
        }
        return false;
    }
	
	
	public static BufferedImage Dilation(BufferedImage img, int width, int height, int size){
		
		int[][] imgArr = new int[width][height];
		
		Raster raster = img.getData();
		
		for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {
	            imgArr[w][h] = raster.getSample(w, h, 0);
	        }
	    }
		
		
		BufferedImage OutputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x = 0 ; x <width ; x++){
			for(int y = 0 ; y <width ; y++){
				Color result = new Color(img.getRGB(x, y));
				int rgb = result.getRGB();
				OutputImg.setRGB(x, y, rgb);
			}
		}
		
		for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {	
        		if(StructureElement(img, w, h, size, imgArr, 255)){
        			OutputImg.setRGB(w, h, rgb_white);
        		}	        	
	        }
	    }
		
		return OutputImg;
	}
	
	
	
	public static BufferedImage Erosion(BufferedImage img, int width, int height, int size){
		int[][] imgArr = new int[width][height];
		
		Raster raster = img.getData();
		
		for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {
	            imgArr[w][h] = raster.getSample(w, h, 0);
	        }
	    }
		
		
		BufferedImage OutputImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x = 0 ; x <width ; x++){
			for(int y = 0 ; y <width ; y++){
				Color result = new Color(img.getRGB(x, y));
				int rgb = result.getRGB();
				OutputImg.setRGB(x, y, rgb);
			}
		}
		
		
		for (int w = 0; w < width; w++) {
	        for (int h = 0; h < height; h++) {	
        		if(StructureElement(img, w, h, size, imgArr, 0)){
        			OutputImg.setRGB(w, h, rgb_black);
        		}	        	
	        }
	    }
		
		return OutputImg;
	}
	
	
	
	public static void main(String[] args) throws IOException {  
		  
		BufferedImage img = null;
		 
		try{
			img = ImageIO.read(new File("HW5.jpg"));
		}catch(IOException e){
			System.out.println(e.toString());
		}
		
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage BinaryImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BinaryImg = Binary(img, width, height);
		
		BufferedImage DilationImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		DilationImage = Dilation(BinaryImg, width, height, 39);
		
		BufferedImage ErosionImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		ErosionImage = Erosion(DilationImage, width, height, 39);
		
		BufferedImage ErosionImage_sec = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		ErosionImage_sec = Erosion(ErosionImage, width, height, 91);
		
		BufferedImage DilationImage_sec = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		DilationImage_sec = Dilation(ErosionImage_sec, width, height, 91);
		
		
		
		
		
//		System.out.println(width +"  "+height); //คุคo 600 x 600
			    
	    try{
			File outputfile = new File("Binary.jpg");
			ImageIO.write(BinaryImg, "jpg", outputfile);
		}catch(IOException e){
			System.out.println(e.toString());
		}

	    try{
			File outputfile2 = new File("Dilation.jpg");
			ImageIO.write(DilationImage, "jpg", outputfile2);
		}catch(IOException e){
			System.out.println(e.toString());
		}
	    
	    try{
			File outputfile3 = new File("Erosion.jpg");
			ImageIO.write(ErosionImage, "jpg", outputfile3);
		}catch(IOException e){
			System.out.println(e.toString());
		}
	    
	    try{
			File outputfile4 = new File("Erosion_sec.jpg");
			ImageIO.write(ErosionImage_sec, "jpg", outputfile4);
		}catch(IOException e){
			System.out.println(e.toString());
		}
	    
	    try{
			File outputfile5 = new File("Dilation_sec.jpg");
			ImageIO.write(DilationImage_sec, "jpg", outputfile5);
		}catch(IOException e){
			System.out.println(e.toString());
		}
	    
	 }	
}