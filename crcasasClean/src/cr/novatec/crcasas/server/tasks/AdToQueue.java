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
public class AdToQueue extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {
			
			RetryOptions retry = RetryOptions.Builder.withTaskRetryLimit(2);

			Queue queue = QueueFactory.getQueue("default");
			queue.add(TaskOptions.Builder.withUrl("/tasks/mashuptask1").retryOptions(retry));
			queue.add(TaskOptions.Builder.withUrl("/tasks/mashuptask2").retryOptions(retry));

			//queue.add(TaskOptions.Builder.withUrl("/tasks/loaddivision3"));
			//queue.add(TaskOptions.Builder.withUrl("/tasks/loadprice"));
			
			strCallResult = "Successfully created 1 Task in the Queue";
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