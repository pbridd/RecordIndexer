package server;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class FileDownloadHandler implements HttpHandler {


	public FileDownloadHandler(){
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {

		Path path = Paths.get("data/" + exchange.getRequestURI());
		byte[] data = Files.readAllBytes(path);
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		OutputStream os = null;
		os = exchange.getResponseBody();
		os.write(data);
		os.close();
		exchange.getResponseBody().close();
	}

}
