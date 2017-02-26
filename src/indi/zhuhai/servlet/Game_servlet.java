package indi.zhuhai.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import indi.zhuhai.sql.Respond_game;

@WebServlet("/gameservlet")
public class Game_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Respond_game respond_game;

	public void init(ServletConfig config) throws ServletException {
		respond_game = new Respond_game();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream ins = request.getInputStream();
		String result = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
		StringBuffer sb = new StringBuffer();
		
		while((result = reader.readLine()) != null){
			sb.append(result);
		}
		String data = respond_game.getRespond(sb.toString());
		
		OutputStream ous = response.getOutputStream();
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		byte[] dataByteArr = data.getBytes("UTF-8");
		ous.write(dataByteArr);
	}

}
