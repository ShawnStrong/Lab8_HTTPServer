import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer {
	// a shared area where we get the POST data and then use it in the other
	// handler
	static String sharedResponse = "";
	static boolean gotMessageFlag = false;
	static MainDirectory s = new MainDirectory();

	public static void main(String[] args) throws Exception {

		// set up a simple HTTP server on our local host
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

		// create a context to get the request to add dudes to the server
		server.createContext("/submit", new SubmitHandler());

		// create a context to get the request to print the dudes from the
		// server
		server.createContext("/print", new PrintHandler());
		server.createContext("/clear", new ClearHandler());
		server.setExecutor(null); // creates a default executor

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}

	
	static class SubmitHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

			sharedResponse = "";
			
            // set up a stream to read the body of the request
            InputStream inputStr = t.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = t.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }
            sb.insert(0, "[");
            
            sharedResponse = sharedResponse+sb.toString()+"]";

            Gson g = new Gson();
            ArrayList<Employee> incoming = (g.fromJson(sharedResponse, new TypeToken<Collection<Employee>>() {
    		}.getType()));
    		for (Employee emp : incoming) {
    			emp.print();
    		}
    		
    		
            s.reconstruct(sharedResponse);
            
            
            System.out.println("Json string: " + sharedResponse);

            String postResponse = "Json received";
            t.sendResponseHeaders(300, postResponse.length());
            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();
		}
	}
	
	static class PrintHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			sharedResponse = "";
			
            // set up a stream to read the body of the request
            InputStream inputStr = t.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = t.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }
            
            
            s.print();
            
            
            

            String postResponse = "Json received";
            t.sendResponseHeaders(300, postResponse.length());
            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();
		}
	}
	
	static class ClearHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
sharedResponse = "";
			
            // set up a stream to read the body of the request
            InputStream inputStr = t.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = t.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }
            
            
            s.clear();
            
            
            

            String postResponse = "Json received";
            t.sendResponseHeaders(300, postResponse.length());
            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();
		}
	}
}
