package com.thramos.framework.util.image;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author James
 */
public class Example {
	
	private static final String DIRETORIO_FOTOS = "C:/Users/alysson/Desktop/";
	private static final String DIRETORIO_FOTOS_SAIDA = "C:/Users/alysson/Desktop/";
	
    @SuppressWarnings("unused")
	public static void main(String[] args) {
        //String imgLocation = (args.length == 1 ? args[0] : null);
        //String imgLocation = DIRETORIO_FOTOS+"ranking-default.png";
        String imgLocation = DIRETORIO_FOTOS+"103-1350866791970.png";
        
        if (imgLocation == null)
            throw new IllegalArgumentException("One argument required: path-to-image");
        try {
            Image img = null;
            if (imgLocation.startsWith("http")) {
                //read the image from a URL
                img = ImageLoader.fromUrl(new URL(imgLocation));
            }
            else {
                File f = new File(imgLocation);
                if (!f.exists() || !f.isFile())
                    throw new IllegalArgumentException("Invalid path to image");
                else {
                    //read the image from a file
                    img = ImageLoader.fromFile(f);
                }
            }

            //output source type
            System.out.println("Image source type: "+ img.getSourceType());
            //output dimensions
            System.out.println("Image dimensions: "+ img.getWidth() +"x"+ img.getHeight());

            //crop it
            /*Image cropped = img.crop(200, 200, 500, 350);
            cropped.writeToJPG(new File(DIRETORIO_FOTOS_SAIDA+"cropped.jpg"), 0.95f);
            cropped.dispose();*/

            //resize
            Image resized = img.getResizedToWidth(500);
            //save it with varying softness and quality
            /*softenAndSave(resized, 0.95f, 0f);
            softenAndSave(resized, 0.95f, 0.1f);
            softenAndSave(resized, 0.95f, 0.2f);
            softenAndSave(resized, 0.95f, 0.3f);
            softenAndSave(resized, 0.8f, 0.08f);
            softenAndSave(resized, 0.6f, 0.08f);
            softenAndSave(resized, 0.4f, 0.08f);*/
            resized.dispose();
            
            //write a 0.95 quality JPG without using Sun's JPG codec
            //resized.writeToFile(new File(DIRETORIO_FOTOS_SAIDA+"ranking-default-small.png"));
            resized.writeToFile(new File(DIRETORIO_FOTOS_SAIDA+"103-1350866791970-small.png"));

            //resize it to a square with different settings for edge cropping
            /*squareIt(img, 400, 0.0, 0.95f, 0.08f);
            squareIt(img, 400, 0.1, 0.95f, 0.08f);
            squareIt(img, 400, 0.2, 0.95f, 0.08f);*/

            //small thumbs
            squareIt(img, 500, 0.0, 0.95f, 0.08f);
            //squareIt(img, 50, 0.1, 0.95f, 0.08f);
            //squareIt(img, 50, 0.1, 0.5f, 0.08f);            
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    @SuppressWarnings("unused")
	private static void softenAndSave(Image img, float quality, float soften) throws IOException {
        img.soften(soften).writeToJPG(new File(DIRETORIO_FOTOS_SAIDA+"resized--q"+ quality +"--s"+ soften +".jpg"), quality);
    }

    private static void squareIt(Image img, int width, double cropEdges, float quality, float soften) throws IOException {
        Image square = img.getResizedToSquare(width, cropEdges).soften(soften);
        square.writeToJPG(new File(DIRETORIO_FOTOS_SAIDA+"square--w"+ width +"--e"+ cropEdges +"--q"+ quality +".jpg"), quality);
        square.dispose();
    }
    
    /*private static void squareIt(InputStream uploadedInputStream) throws IOException {
    	
    	InputStream is = ImageUtil.readAndConvertImage(uploadedInputStream);
    	int size; 
    	
    	System.out.println("Total Available Bytes: " +(size = is.available()) ); 
		int n = size/40; 
		System.out.println("First " + n + " bytes of the file one read() at a time"); 
    	//System.out.println();
    	
    }*/
    

}
