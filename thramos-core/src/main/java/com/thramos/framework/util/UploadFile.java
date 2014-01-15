package com.thramos.framework.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class UploadFile implements Runnable {
	
	/**
	 * Separador de arquivos Linux, quando o servidor de imagens seja Windows isso deve ser alterado
	 */
	public static final String SEPARATOR = "/";
	
	private String sftpHost = "IP_DO_SERVIDOR";
	private int sftpPort = 22;
	private String sftpUser = "root";
	private String sftpPassword = "password_servidor";
	private String sftpDir = "/var/www/imagens/thramos/";
	private InputStream is = null;
	private String path = null;
	private String fileName = null;

	public UploadFile() {}

	public UploadFile(String sftpHost, int sftpPort, String sftpUser,
			String sftpPassword, String sftpDir) {
		super();
		this.sftpHost = sftpHost;
		this.sftpPort = sftpPort;
		this.sftpUser = sftpUser;
		this.sftpPassword = sftpPassword;
		this.sftpDir = sftpDir;
	}
	
	public UploadFile(InputStream is, String path, String fileName) {
		super();
		this.is = is;
		this.path = path;
		this.fileName = fileName;
	}

	public String getSftpHost() {
		return sftpHost;
	}

	public void setSftpHost(String sftpHost) {
		this.sftpHost = sftpHost;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public void setSftpPort(int sftpPort) {
		this.sftpPort = sftpPort;
	}

	public String getSftpUser() {
		return sftpUser;
	}

	public void setSftpUser(String sftpUser) {
		this.sftpUser = sftpUser;
	}

	public String getSftpPassword() {
		return sftpPassword;
	}

	public void setSftpPassword(String sftpPassword) {
		this.sftpPassword = sftpPassword;
	}

	public String getSftpDir() {
		return sftpDir;
	}

	public void setSftpDir(String sftpDir) {
		this.sftpDir = sftpDir;
	}

	public boolean moveFileToDir(String localFilePath) {
		return moveFileToDir(localFilePath, null, null, true);
	}

	public boolean moveFileToDir(String localFilePath, String remoteDirPath) {
		return moveFileToDir(localFilePath, remoteDirPath, null, true);
	}

	public boolean moveFileToDir(String localFilePath, String remoteDirPath,
			String remoteFileName) {
		return moveFileToDir(localFilePath, remoteDirPath, remoteFileName, true);
	}

	public boolean copyFileToDir(String localFilePath) {
		return moveFileToDir(localFilePath, null, null, false);
	}

	public boolean copyFileToDir(String localFilePath, String remoteDirPath) {
		return moveFileToDir(localFilePath, remoteDirPath, null, false);
	}

	public boolean copyFileToDir(String localFilePath, String remoteDirPath,
			String remoteFileName) {
		return moveFileToDir(localFilePath, remoteDirPath, remoteFileName,
				false);
	}

	public boolean moveFileToDir(String localFilePath, String remoteDirPath, String remoteFileName, boolean isDelete) {
		
		boolean returnResult = false;
		boolean deleteSuccess = false;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			
			JSch jsch = new JSch();
			session = jsch.getSession(this.sftpUser, this.sftpHost, this.sftpPort);
			session.setPassword(this.sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			
			if (null != remoteDirPath)
				channelSftp.cd(remoteDirPath);
			else
				channelSftp.cd(this.sftpDir);

			File f = new File(localFilePath);
			String fileName = f.getName();
			
			if (null != remoteFileName && remoteFileName.length() > 0)
				fileName = remoteFileName;

			channelSftp.put(new FileInputStream(f), fileName);
			
			if (isDelete) {
				deleteSuccess = f.delete();
			} else {
				deleteSuccess = true;
			}
			
			returnResult = deleteSuccess;
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			// Disconnecting the channel
			channel.disconnect();
			// Disconnecting the session
			session.disconnect();
		}

		return returnResult;
	}
	
	public boolean moveFileToDir(File arquivoTemporario) {
		
		boolean returnResult = false;
		boolean deleteSuccess = false;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		
		try {
			
			JSch jsch = new JSch();
			session = jsch.getSession(this.sftpUser, this.sftpHost, this.sftpPort);
			session.setPassword(this.sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(this.sftpDir);
			
			//channelSftp.put(new FileInputStream(arquivoTemporario), arquivoTemporario.getName());
			channelSftp.put(new FileInputStream(arquivoTemporario), arquivoTemporario.getName());
			//arquivoTemporario.deleteOnExit();
			
			deleteSuccess = arquivoTemporario.delete();
			
			returnResult = deleteSuccess;
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			// Disconnecting the channel
			channel.disconnect();
			// Disconnecting the session
			session.disconnect();
		}
		
		return returnResult;
	}
	
	
	public boolean moveFileToDir(InputStream is, String path, String fileName) {
		
		boolean returnResult = false;
		boolean deleteSuccess = false;
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		
		try {
			
			JSch jsch = new JSch();
			session = jsch.getSession(this.sftpUser, this.sftpHost, this.sftpPort);
			session.setPassword(this.sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			
			String pathCompleto = this.sftpDir + path;
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(pathCompleto);
			
			channelSftp.put(is,pathCompleto+fileName);
			returnResult = deleteSuccess;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			// Disconnecting the channel
			channel.disconnect();
			// Disconnecting the session
			session.disconnect();
		}
		
		return returnResult;
	}
	
	private void criaPastaCasoNaoTenha(String path){
		
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		
		String[] split = path.split(SEPARATOR);
		
		try {
			
			JSch jsch = new JSch();
			session = jsch.getSession(this.sftpUser, this.sftpHost, this.sftpPort);
			session.setPassword(this.sftpPassword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			
			channelSftp = (ChannelSftp) channel;
		
			criaPastaCasoNaoTenha(split, 0, session, channel, channelSftp);
		
		} catch (JSchException e) {
			e.printStackTrace();
		}finally{
			// Disconnecting the channel
			channel.disconnect();
			// Disconnecting the session
			session.disconnect();
		}
	}
	
	private void criaPastaCasoNaoTenha(String[] split, int index, Session session, Channel channel,
			ChannelSftp channelSftp) /*throws Exception*/ {
		
		try {
			
			/*Vector ls = */channelSftp.ls(this.sftpDir+split[index]);
			index++;
			if( split.length > index ){
				criaPastaCasoNaoTenha(split, index, session, channel, channelSftp);
			}
			
		}catch (SftpException ex) {
			if(ex.getMessage().indexOf("No such file") != -1){
				System.out.println("Criar pasta");
				
				String pathQuebrado = this.sftpDir + ImageAndTokenUtil.concatenaString(SEPARATOR, index, split);
				Boolean criou = criaPasta(pathQuebrado, session, channel, channelSftp);
				//Após criar a pasta pula para o próxima pasta..
				index++;
				if(criou && split.length > index ){
					criaPastaCasoNaoTenha(split, index, session, channel, channelSftp);
				}/*else{
					Exception e = new Exception("Problema ao criar pasta: "+pathQuebrado);
					throw e;
				}*/
			}
		} 
		
	}
	
	private Boolean criaPasta(String path, Session session, Channel channel, ChannelSftp channelSftp){
		
		Boolean criou = false;
		try {
			
			channelSftp.mkdir(path);
			criou = true;
			
		}catch (SftpException ex) {
			//ex.printStackTrace();
			criou = false;
		} 
		
		return criou;
	}
			
	@Override
	public void run() {
		
		criaPastaCasoNaoTenha(path);
		moveFileToDir(is, path, fileName);
	}

}
