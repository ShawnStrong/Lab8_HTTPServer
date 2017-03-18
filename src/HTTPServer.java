import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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
		server.setExecutor(null); // creates a default executor

		// get it going
		System.out.println("Starting Server...");
		server.start();
	}

	
	static class SubmitHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			if(!sharedResponse.isEmpty()){
				s.reconstruct(sharedResponse);
			}
		}
	}
	
	static class PrintHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			s.print();
		}
	}
}
