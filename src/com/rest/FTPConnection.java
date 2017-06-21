/** This class is developed for connecting with FTP server and do the file upload and download tasks. **/
package com.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPConnection {

/** This function is used to establish the connection with FTP server and returns FTPClient object. **/
	public FTPClient ftpConnect(String host, String user, String pwd) throws Exception {
		System.out.println("FTP Connection Initialized");
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            //throw new Exception("Exception in connecting to FTP Server");
            return ftp;
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        System.out.println("FTP Connected");
        return ftp;
 	}

/** This function is used to download the file from FTP to app server. **/
 	public void downloadFile(String remoteFilePath, String localFilePath,FTPClient ftp) {
        try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
            ftp.retrieveFile(remoteFilePath, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 /** This function is used to upload the file from app server to FTP server **/
 	public boolean uploadFile(FTPClient ftp,String strSource, String strDestination,String strRecordId){
 		try {
			File file = new File(strSource+strRecordId);
			//String secondRemoteFile = "/salesforce_subfolder/Wildlife.wmv";
			FileInputStream inputStream = new FileInputStream(file);

			System.out.println("FTP Upload Started...");
			OutputStream outputStream = ftp.storeFileStream(strDestination+strRecordId);
			byte[] bytesIn = new byte[4096];
			int read = 0;

			while ((read = inputStream.read(bytesIn)) != -1) {
			    outputStream.write(bytesIn, 0, read);
			}
			inputStream.close();
			outputStream.close();

			boolean completed = ftp.completePendingCommand();
			if (completed) {
			    System.out.println("File Transferred to FTP Successfully : "+strRecordId);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
 		return true;
 	}

 /** This function is used to disconnect the connection established from app server.**/
    public void disconnect(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
                System.out.println("FTP server logged out successfully");
            } catch (IOException f) {
                // do nothing as file is already downloaded from FTP server
            }
        }
    }
}
