    package com.rest;  
    import java.io.File;  
    import javax.ws.rs.GET;  
    import javax.ws.rs.Path;  
    import javax.ws.rs.Produces;
    import javax.ws.rs.core.MediaType;
    import javax.ws.rs.core.Response;  
    import javax.ws.rs.core.Response.ResponseBuilder;  
    @Path("/files")  
    public class FileDownloadService {  
    	private static final String FILE_NAME = "AWS_MS_1.png";
        private static final String FILE_PATH = "E:\\"+FILE_NAME; 
        
        @GET  
        @Path("/image")  
        //@Produces("image/png")  
        @Produces(MediaType.APPLICATION_OCTET_STREAM)
        public Response getFile() {  
            File file = new File(FILE_PATH);  
            ResponseBuilder response = Response.ok((Object) file);  
           // response.header("Content-Disposition","attachment; filename=\"AWS_MS_1.png\"");  
            response.header("Content-Disposition","attachment; filename=\"AWS_MS_1.png\"");
            return response.build();  
       
        }  
     }  
