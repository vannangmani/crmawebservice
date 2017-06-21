/** This class is developed for receiving the File from HTML / VF pages 
 * or from client application which supports Multipart Form data for upload and 
 * stores in a specified folder within the application and transfer the file to FTP Server later**/

package com.rest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


/** Setting up the REST API Path **/ 
@Path("/files")

public class FileUploadService extends Thread{
	FTPClient ftp= null;
	public String str_recordId = "";
/** Setting up HTTP Method for REST API Call **/ 
 	@POST
 	
/** Setting up REST API Path for Upload Service **/
 	@Path("/upload")
 	
/** Setting up Consuming the data format from Client App **/
	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	
/** Setting up data return format. Right now it is commented as it returns only String  **/ 	
 	//@Produces("application/json")
 	//@Produces("plain/text")
 	
/** Function for uploading the file and returns success or failure message to client app **/
/** FormDataparam with annotation is used to capture the filename and data **/
/** QueryParam with annotation is used to decide the parameters that should be received as Request **/
 	
 	public Response uploadFile(
 			@FormDataParam("filename") InputStream uploadedInputStream,
 			@FormDataParam("filename") FormDataContentDisposition fileDetail,
 			@QueryParam("recordid") String strRecordId) {
 			String output="";
 			str_recordId = strRecordId;
/** Getting the name of the file **/
 			String strFilename = fileDetail.getFileName();
 			System.out.println("Filename is : "+strFilename);
 			
/** Getting the Extension (file format) of the file **/ 			
 			String strFileExtn =  FilenameUtils.getExtension(strFilename);
 			System.out.println(fileDetail.getParameters());
 			System.out.println(strRecordId);
 			
/** Uploading the file to app server and returns the success or failure message **/
 			try {
 				System.out.println(FileConstants.strSource);
  				FileOutputStream out = new FileOutputStream(new File(FileConstants.strSource+strRecordId));
 				int read = 0;
 				byte[] bytes = new byte[1024];
 	 
 				//out = new FileOutputStream(new File(FileConstants.strSource));
 				while ((read = uploadedInputStream.read(bytes)) != -1) {
 					out.write(bytes, 0, read);
 				}
 				out.flush();
 				out.close();
/** Initiating File upload to FTP Server from app server **/
 				
 				thread.start();
 				System.out.println("Thread Started");
 				output = "success";
 			} catch (IOException e) {
  				e.printStackTrace();
 				output = "failure";
 				} 
 			System.out.println("Sending Response");
 			return Response.status(200).entity(output).build();
 			//return Response.status(200).entity(response(s.toString())).build();
 		}
 	
 	
 	Thread thread = new Thread(new Runnable()
 	{
 		public void run()
 		{
 			FTPConnection f = new FTPConnection();
 			
 			try {
/** Initiating the connection to FTP Server **/
				ftp = f.ftpConnect(FileConstants.strFTPHost,FileConstants.strUsername,FileConstants.strPassword);
				if(ftp != null)
				{
					
/** Initiating the File Upload to FTP server if FTP Connection is established **/
					boolean bool = f.uploadFile(ftp, FileConstants.strSource, FileConstants.strFtpDestination,str_recordId);
					f.disconnect(ftp);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			finally{
 				//thread.destroy();
 				ftp = null;
 			}
 		}
 	});
  }