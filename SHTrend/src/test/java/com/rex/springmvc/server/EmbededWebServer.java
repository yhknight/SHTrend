package com.rex.springmvc.server;

import org.apache.catalina.startup.Tomcat;

public class EmbededWebServer  {

	private static final String catalina_home = "embeded_tomcat";
	private static final String contextPath = "/";
	private Tomcat tomcat;
	private int port = 8081;

	public EmbededWebServer(/*int... port*/) {
/*		if (port.length > 0)
			this.port = port[0];*/

		this.tomcat = new Tomcat();
	}

	public void startServer() throws Exception {

		String docBase = System.getProperty("user.dir");
		tomcat.setPort(port);
		tomcat.setBaseDir(catalina_home);
		tomcat.addWebapp(contextPath, docBase);
		tomcat.start(); 
		//tomcat.getServer().await();

	}

	public void stopServer() throws Exception {
		tomcat.stop();
		tomcat.destroy();
		//FileUtils.deleteDirectory(new File("work"));

	}

	public static void main(String... args) throws Exception {
		EmbededWebServer server = new EmbededWebServer();
		server.startServer();

	}

}
