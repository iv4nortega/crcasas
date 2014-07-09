package cr.novatec.crcasas.server.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.RetryOptions;
import com.google.appengine.api.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class Queue4 extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {
			RetryOptions retry = RetryOptions.Builder.withTaskRetryLimit(2);

			Queue queue = QueueFactory.getQueue("default");
			queue.add(TaskOptions.Builder.withUrl("/tasks/setcount").retryOptions(retry));
			
			strCallResult = "Successfully created loadPrice queue";
			resp.getWriter().println(strCallResult);
		}
		catch (Exception ex) {
			strCallResult = "Fail: " + ex.getMessage();
			resp.getWriter().println(strCallResult);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		doGet(req, resp);
	}
}