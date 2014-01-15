package com.thramos.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.thramos.framework.util.image.Image;
import com.thramos.framework.util.image.ImageLoader;

public class ImageAndTokenUtil {
	
	//private static final String DIRETORIO_FOTOS_SAIDA = "D:/User/Fotos/teste/";
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String DIRETORIO_BASE = System.getProperty("user.dir");
	public static final String DIRETORIO_FOTOS = FILE_SEPARATOR+"src"+FILE_SEPARATOR+"main"+
			FILE_SEPARATOR+"webapp"+FILE_SEPARATOR+"imagens";
	/*public static final String DIRETORIO_SERVIDOR_Imagens = FILE_SEPARATOR+"src"+FILE_SEPARATOR+"main"+
			FILE_SEPARATOR+"webapp"+FILE_SEPARATOR+"imagens";*/
	
	/*public static final Integer TAMANHO_400X400 = 0;
    public static final Integer TAMANHO_50X50 = 1;*/
	
	/**
     * Gera uma senha randômica (string com seis dígitos) para ser utilizado na
     * geração e troca de senha. Os intervalos utilizados são os mesmos do CNCT,
     * a exclusao do 108 (l - ele minúsculo), do 48 (número zero), 49 (número
     * um), 73 (i maiúsculo) e 79 (o maiúsculo).
     *
     * @return Uma senha randômica
     *
     */
    public static String geracaoToken() {
        int passwordLength = 50;
        StringBuffer pwd = new StringBuffer();
        int auxNumber = 73; // esse valor é atribuído para que seja executado
        // while
        int maxNumeros = 2;
        int qtdNumeros = 0;

        for (int i = 0; i < passwordLength; ) {
            // este laço verifica se foi gerado algum caracter que pode causar
            // confusão de legibilidade.
            do {
                auxNumber = geraNumeroRandomico(50, 122);
            } while ((auxNumber == 73) || (auxNumber == 79) || 
                     (auxNumber == 108));

            // o numero gerado tem que estar nos intervalos [48,57], [65,90] e
            // [97,122] - 108
            if (auxNumber >= 48 && auxNumber <= 57 && 
                qtdNumeros < maxNumeros) { // só
                // números
                pwd.append((char)auxNumber);
                i++;
                qtdNumeros++;
            } // só alfa
            else if (((auxNumber >= 65 && auxNumber <= 90) || 
                      (auxNumber >= 97 && auxNumber <= 122)) && 
                     (i - qtdNumeros + 1 <= passwordLength - maxNumeros)) {
                pwd.append((char)auxNumber);
                i++;
            }
        }

        return pwd.toString();
    }
    
    /**
     * Retorna um numero inteiro gerado randomicamente entre os possíveis
     * especificados dentro da faixa 0 ... 1000
     *
     * @param valorMinimo
     *            Valor mínimo
     * @param valorMaximo
     *            Valor máximo
     *
     * @return número inteiro gerado randomicamente
     *
     */
    public static int geraNumeroRandomico(int valorMinimo, int valorMaximo) {
        int aux;

        do {
            aux = (int)(Math.random() * 1000);
            aux = (int)(Math.random() * 1000);
        } while (aux < valorMinimo || aux > valorMaximo);

        return aux;
    }
    
    public static void main(String[] args) {
    	System.out.println(geracaoToken());
	}
    
    public static void diminuirImagem(InputStream uploadedInputStream, String uploadedFileLocation, String nomeArquivo){
    	Image img = diminuirImagem(null, uploadedInputStream);
    	try {
    		//Criar as pastas caso não existam
    		File pastes = new File(uploadedFileLocation);
    		boolean mkdirs = pastes.mkdirs();
    		System.out.println("Criou pasta : " + mkdirs);
    		//Transforma para o tamanho de 50X50
    		//squareIt(img, 50, 0.0, 0.95f, 0.08f, uploadedFileLocation+"small-"+nomeArquivo);
    		//Transforma para o tamanho de 400X400
			img.writeToFile(new File(uploadedFileLocation+nomeArquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void diminuirImagem(String imgLocation, String uploadedFileLocation, String nomeArquivo){
    	Image img = diminuirImagem(imgLocation, null);
    	try{
    		//Criar as pastas caso não existam
    		File pastes = new File(uploadedFileLocation);
    		pastes.mkdirs();
    		//Transforma para o tamanho de 50X50
    		squareIt(img, 50, 0.0, 0.95f, 0.08f, uploadedFileLocation+"small-"+nomeArquivo);
    		//Transforma para o tamanho de 400X400
    		img.writeToFile(new File(uploadedFileLocation+nomeArquivo));
	    } catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 
     * @param caminhoFoto - InputStream ou url
     * @param tamanho
     * @return
     */
    private static Image diminuirImagem(String imgLocation, InputStream uploadedInputStream){
    	
    	/*if (imgLocation == null)
            throw new IllegalArgumentException("Argumento requerido: caminho-da-imagem");*/
        try {
            Image img = null;
            
            if(imgLocation != null){
            	
	            if (imgLocation.startsWith("http")) {
	                //read the image from a URL
	                img = ImageLoader.fromUrl(new URL(imgLocation));
	                //Só descomentar se for passar um caminha fisíco que na aplicação dificilmente ou nunca acontecerá
	            } /*else {
	                File f = new File(imgLocation);
	                if (!f.exists() || !f.isFile())
	                    throw new IllegalArgumentException("Caminha inválido da imagem");
	                else {
	                    //read the image from a file
	                    img = ImageLoader.fromFile(f);
	                }
	            }*/
            }else if(uploadedInputStream != null){
            	img = ImageLoader.fromStream(uploadedInputStream);
            }
            
            //Tratamento para diminuir Imagem sem perder qualidade
            Image resized = img.getResizedToWidth(400);
			resized.dispose();
            
            return resized;
            
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
		return null;
    	
    }
    
    private static void squareIt(Image img, int width, double cropEdges, float quality, float soften, String uploadedFileLocation) throws IOException {
        Image square = img.getResizedToSquare(width, cropEdges).soften(soften);
        File arquivo = new File(uploadedFileLocation);
        square.writeToJPG(arquivo, quality);
        square.dispose();
    }
    
    
    /**
     * Realiza o upload das imagens para o servidor 
     * filePathName é pasta junto com o nome do arquivo
     * Ex: app/teste.jpg
     */
    public static void uploadImages(InputStream is, String filePathName, String fileName){
    	Thread roda = new Thread(new UploadFile(is, filePathName, fileName) );
		roda.start();
    }
    
    public static String concatenaString(String separator, int index, String... list){
    	
    	if(list == null || list.length == 0){
    		return null;
    	}
    	
    	Boolean useSeparator = (separator != null && !separator.trim().equals(""));
    	StringBuilder temp = new StringBuilder();
    	
    	//for (String string : list) {
    	for (int i = 0; i < list.length; i++) {
    		if(i > index){
    			break;
    		}
    		temp.append(list[i]);
    		if(useSeparator){
    			temp.append(separator);
    		}
		}
    	
    	return temp.toString();
    	
    }
    
    // save uploaded file to new location
 	/*@SuppressWarnings("unused")
	private static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
  
 		try {
 			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
 			int read = 0;
 			byte[] bytes = new byte[1024];
  
 			out = new FileOutputStream(new File(uploadedFileLocation));
 			while ((read = uploadedInputStream.read(bytes)) != -1) {
 				out.write(bytes, 0, read);
 			}
 			out.flush();
 			out.close();
 		} catch (IOException e) {
  
 			e.printStackTrace();
 		}
  
 	}*/
    
    public static List<String> criarListaDeStringAhPartirDeUmaString(String codigos){
    	String[] arrayDeCodigos = StringUtils.split(codigos, ",");
    	List<String> listaCodigos = Arrays.asList(arrayDeCodigos);
    	return listaCodigos;
    }
    
     public static String montaCodigoString(List<?> listaCodigos){
		return montaCodigoString(listaCodigos, true);
	}
	
	public static String montaCodigoString(List<?> listaCodigos, boolean colocaAspa){
		
		StringBuilder temp = new StringBuilder();
		if(listaCodigos != null && !listaCodigos.isEmpty()){
			for (Object obj : listaCodigos) {
				String codigo = (obj == null)?null:obj.toString();
				if(codigo != null && !codigo.trim().equals("") && !codigo.trim().equals("0")){
					if(colocaAspa){
						temp.append("'");
					}
					temp.append(codigo);
					if(colocaAspa){
						temp.append("'");
					}
					temp.append(",");
				}
			}
		}
		String codigoConcatenado = temp.toString();
		if(codigoConcatenado.lastIndexOf(",") != -1){
			codigoConcatenado = codigoConcatenado.substring(0,codigoConcatenado.lastIndexOf(","));
		}
		
		return codigoConcatenado;
	}
	

	public static String strip_tags(String text, String allowedTags) {
	    String[] tag_list = allowedTags.split(",");
	           Arrays.sort(tag_list);
	    
	           final Pattern p = Pattern.compile("<[/!]?([^\\\\s>]*)\\\\s*[^>]*>",
	                   Pattern.CASE_INSENSITIVE);
	           Matcher m = p.matcher(text);
	    
	           StringBuffer out = new StringBuffer();
	          int lastPos = 0;
	          while (m.find()) {
	              String tag = m.group(1);
	              // if tag not allowed: skip it
	              if (Arrays.binarySearch(tag_list, tag) < 0) {
	                  out.append(text.substring(lastPos, m.start())).append(" ");
	   
	              } else {
	                  out.append(text.substring(lastPos, m.end()));
	              }
	              lastPos = m.end();
	          }
	          if (lastPos > 0) {
	              out.append(text.substring(lastPos));
	              return out.toString().trim();
	          } else {
	              return text;
	          }
	      }


}
