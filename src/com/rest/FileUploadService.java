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
import org.json.JSONException;
import org.json.JSONObject;

@Path("/files")
public class FileUploadService {
 	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
 	//@Produces("application/json")
 	//@Produces("plain/text")
 	public Response uploadFile(
 			@FormDataParam("file") InputStream uploadedInputStream,
 			@FormDataParam("file") FormDataContentDisposition fileDetail) throws JSONException {
 			//Success s = new Success();
 			//JSONObject response = new JSONObject();
 			String output="";
 			//String fileLocation = "E:\\Absyz_Workspace\\crmawebservice\\storage\\" + fileDetail.getFileName();
 			String fileLocation = "/app/apache-tomcat-7.0.78/webapps/crmawebservice/storage/" + fileDetail.getFileName();
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
 				output = "success";
 				//s.setSuccess("success");
 				//response.put("response", "success");
 			 	 
 	 			
 			} catch (IOException e) {
 				
 				e.printStackTrace();
 				//output = "File Upload Failure : " + fileLocation;
 				output = "failure";
 				//response.put("response", "failure");
 				//s.setFailure("failure");
 				} 
 			
 			return Response.status(200).entity(output).build();
 			
 			//return Response.status(200).entity(response(s.toString())).build();
 		}
  }