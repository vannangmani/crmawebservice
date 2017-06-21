/** This Class is developed to download the file from App server or from FTP server based on the availability **/
package com.rest;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/** Setting up the REST API Path **/
@Path("/files")
public class FileDownloadService {
	FTPClient ftp= null;
	public String str_recordId = "";
	FTPConnection f = new FTPConnection();

/** Setting up HTTP Method for REST API Call **/ 
	@GET 
	
/** Setting up REST API Path for Upload Service **/
    @Path("/download")  
   
/** Setting up data return format. If File is available then it returns the file otherwise it returns failure message   **/ 	
    @Produces({MediaType.APPLICATION_OCTET_STREAM,MediaType.TEXT_PLAIN })
	
/** Function for Download the file and returns the file if it is available or returns failure message to client app **/
/** QueryParam with annotation is used to decide the parameters that should be received as Request **/
    public Response getFile(@QueryParam("filename") String strFilename,@QueryParam("recordid") String strRecordId)  {
    	
    	ResponseBuilder response = null;
    	String output="";
		try {
			System.out.println("Filename is : "+strFilename);
			System.out.println("Record Id : "+strRecordId);
			File file = new File(FileConstants.strSource+strRecordId);

/** Checking for the file availability in App server **/
			if(file.exists() && !file.isDirectory()) 
			{ 
				System.out.println("File Available in App server itself");
			    response = Response.ok((Object) file);  
				response.header("Content-Disposition","attachment; filename=\"" + strFilename + "\"");
				return response.build();
			}
			else
			{
/** Initiating the FTP Connection **/
				System.out.println("Initiating FTP Connection");
				ftp = f.ftpConnect(FileConstants.strFTPHost,FileConstants.strUsername,FileConstants.strPassword);
				
				if(ftp != null)
				{
					
/** Initiating the file transfer file transfer from FTP to App server **/
					f.downloadFile(FileConstants.strFtpSource+strRecordId, FileConstants.strSource+strRecordId,ftp);
		            System.out.println("FTP File downloaded to App server successfully");
		            
/** Initiating disconnecting FTP server **/
		            f.disconnect(ftp);
		           
/** Initiating the File Download from App server to Client **/
		            if(file.exists() && !file.isDirectory()) 
					{ 
					    response = Response.ok((Object) file);  
						response.header("Content-Disposition","attachment; filename=\"" + strFilename + "\"");
						System.out.println("File Downloaded successfully");
						return response.build();
					}
		            else
					{
						output = "failure";
						return Response.status(200).entity(output).build();
					}
				}
				else
				{
					output = "failure";
					return Response.status(200).entity(output).build();
				}
			}
		}
		catch (NullPointerException e)
		{
			//e.printStackTrace();
			output = "failure";
			return Response.status(200).entity(output).build();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			output = "failure";
			return Response.status(200).entity(output).build();
		}
		
     }
}
