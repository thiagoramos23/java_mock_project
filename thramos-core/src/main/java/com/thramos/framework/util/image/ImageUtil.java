package com.thramos.framework.util.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

	public static InputStream readAndConvertImage(InputStream uploadedInputStream) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int read = 0;
			byte[] buf = new byte[4096];
			while ((read = uploadedInputStream.read(buf)) != -1) {
				baos.write(buf, 0, read);
			}

			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			baos.close();
			uploadedInputStream.close(); 

			return is;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}
}

