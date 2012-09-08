package org.jboss.as.quickstarts.greeter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.Cache;

import java.util.logging.Logger;

@SuppressWarnings("serial")
@WebServlet("/TestServletPut")
public class TestServletPut extends HttpServlet {
	@Inject
	private Logger log;

	static String PAGE_HEADER = "<html><head /><body>";

	static String PAGE_FOOTER = "</body></html>";

	@Inject
	DefaultCacheManager m;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("putting hello-world");
		Cache<String, String> c = m.getCache();
		c.put("hello", "world");

		PrintWriter writer = resp.getWriter();
		writer.println(PAGE_HEADER);
		writer.println("<h1>" + "Put Infinispan: " + c.get("hello") + "</h1>");
		writer.println(PAGE_FOOTER);
		writer.close();
	}

}
