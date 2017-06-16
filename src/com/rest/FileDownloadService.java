    package com.rest;  
    import java.io.File;
    import java.io.FileNotFoundException;
    import javax.ws.rs.GET;  
    import javax.ws.rs.Path;  
    import javax.ws.rs.Produces;
    import javax.ws.rs.QueryParam;
    import javax.ws.rs.core.MediaType;
    import javax.ws.rs.core.Response;  
    import javax.ws.rs.core.Response.ResponseBuilder;
    import javax.ws.rs.core.Request;
    @Path("/files")  
    public class FileDownloadService {  
    	//private static final String FILE_NAME = "Wildlife.wmv";
        //private static final String FILE_PATH = "E:\\"; 
        private static final String FILE_PATH = "/app/apache-tomcat-7.0.78/webapps/crmawebservice/storage/";
        
        @GET  
        @Path("/download")  
        @QueryParam("filename")
        //@Produces("image/png")  
        @Produces({MediaType.APPLICATION_OCTET_STREAM,MediaType.TEXT_PLAIN })
        public Response getFile(@QueryParam("filename") String FILE_NAME)  {
        	
        	ResponseBuilder response = null;
        	String output="";
			try {
				System.out.println("File name is : "+FILE_NAME);
				File file = new File(FILE_PATH+FILE_NAME);
				if(file.exists() && !file.isDirectory()) { 
				    // do something
					response = Response.ok((Object) file);  
					String filename = "filename="+FILE_NAME;
					System.out.println(filename);
					// response.header("Content-Disposition","attachment; filename=\"AWS_MS_1.png\"");  
					response.header("Content-Disposition","attachment;"+filename);
					 return response.build();
				}
				else
				{
					output = "failure";
					return Response.status(200).entity(output).build();
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
