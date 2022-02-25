package com.work.servlet;

import com.work.pojo.movie;
import com.work.service.getMovie;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class showMovie extends HttpServlet {
    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        getMovie gm = new getMovie();
        try {
            gm.saveMovie();
            ArrayList<movie> movies=gm.reamMovie();


            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>第四轮作业</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1 align=\"center\" name=top>推荐电影</h1>");
            int i=1;
            for(movie movie:movies){
                out.println("<hr/>");

                out.println("<h2 align=\"center\">第"+i+"部："+movie.getFileTitle()+"</h2>");

                out.println("<p style=\"text-align:center;\"><img src=\""+movie.getCoverPicture()+"\" title=\""+movie.getFileTitle()+"\" width=\"344\" height=\"477\"></p>");

                out.println("<h3 align=\"center\">电影类型："+movie.getType()+"</h3>");

                out.println("<h3 align=\"center\">放映时间："+movie.getReleaseTime()+"</h3>");

                out.println("<h3 align=\"center\">电影评分："+movie.getScore()+"分</h3>");

                out.println("<h3 align=\"center\">国籍："+movie.getNationality()+"</h3>");

                out.println("<h3 align=\"center\">时长："+movie.getDuration()+"</h3>");

                out.println("<h3 align=\"center\">导演："+movie.getDirectorName()+"</h3>");

                out.println("<p style=\"text-align:center;\"><img src=\""+movie.getDirectorPicture()+"\" title=\""+movie.getDirectorName()+"\" align=\"center\"></p>");

                i++;
            }

            out.println("<hr/>");

            out.println("<h1 align=\"center\">希望有几部适合你！</h1>");

            out.println("<a href=\"#top\" style=\"font-size:40px;\"><h3 align=\"center\" >回到顶部<h3/></a>");

            out.println("</body>");

            out.println("</html>");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
