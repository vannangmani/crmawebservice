package com.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/files")
public class FileUploadService {
 	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	@Produces(MediaType.APPLICATION_JSON)
 	public Response uploadFile(
 			@FormDataParam("file") InputStream uploadedInputStream,
 			@FormDataParam("file") FormDataContentDisposition fileDetail) {
 			Success s = new Success();
 			String output="";
 			String fileLocation = "E:\\Absyz_Workspace\\crmawebservice\\storage\\" + fileDetail.getFileName();
 			//String fileLocation = "/app/apache-tomcat-7.0.78/webapps/crmawebservice/storage/" + fileDetail.getFileName();
 	      //saving file
 			try {
 				
 				FileOutputStream out = new FileOutputStream(new File(fileLocation));
 				int read = 0;
 				byte[] bytes = new byte[1024];
 	 
 				out = new FileOutputStream(new File(fileLocation));
 				while ((read = uploadedInputStream.read(bytes)) != -1) {
 					out.write(bytes, 0, read);
 				}
 				out.flush();
 				out.close();
 				//output = "File successfully uploaded to : " + fileLocation;
 				s.setSuccess("success");
 			 	 
 	 			
 			} catch (IOException e) {
 				
 				e.printStackTrace();
 				//output = "File Upload Failure : " + fileLocation;
 				s.setFailure("failure");
 				}
 			
 			//return Response.status(200).entity(output).build();
 			
 			return Response.status(200).entity(s).build();
 		}
  }