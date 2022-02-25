package com.work.service;

import com.work.dao.BaseDao;
import com.work.pojo.movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class getMovie {


    public void saveMovie() throws Exception{
        String url = "https://ssr1.scrape.center";

        BaseDao baseDao=new BaseDao();

        Connection conn = baseDao.getConnection();

        PreparedStatement st;

        String sql="SELECT COUNT(*) AS 'amount' FROM `movie_list`";

        st=conn.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        int amount=0;

        if(rs.next()){
            amount=rs.getInt("amount");
        }

        if(amount!=100) {
            for (int i = 1; i <= 10; i++) {

                Document document = Jsoup.parse(new URL(url + "/page/" + i), 100000);

                Elements elements = document.getElementsByClass("el-card item m-t is-hover-shadow");

                int j = 1;

                for (Element element : elements) {
                    String fileTitle = element.getElementsByClass("name").eq(0).text();

                    String coverPicture = element.getElementsByTag("img").eq(0).attr("src");

                    String type = element.getElementsByClass("categories").eq(0).text();

                    String releaseTime = element.getElementsByClass("m-v-sm info").eq(1).text();

                    String score = element.getElementsByClass("score m-t-md m-b-n-sm").eq(0).text();

                    Document document2 = Jsoup.parse(new URL(url + "/detail/" + (j + (i - 1) * 10)), 1000000);

                    String directorName = document2.getElementsByClass("el-card__body").eq(1).text();

                    String directorPicture = document2.getElementsByTag("img").eq(2).attr("src");

                    Elements elements1 = element.getElementsByClass("m-v-sm info").eq(0);

                    Element element1 = elements1.get(0);

                    String duration = element1.getElementsByTag("span").eq(2).text();

                    String nationality = element1.getElementsByTag("span").eq(0).text();

                    sql = "INSERT INTO `movie_list`(`file_title`,`cover_picture`,`type`,`release_time`,`score`,`nationality`,`duration`,`director_name`,`director_picture`) VALUE(?,?,?,?,?,?,?,?,?)";

                    st = conn.prepareStatement(sql);

                    st.setString(1, fileTitle);

                    st.setString(2, coverPicture);

                    st.setString(3, type);

                    st.setString(4, releaseTime);

                    st.setString(5, score);

                    st.setString(6, nationality);

                    st.setString(7, duration);

                    st.setString(8, directorName);

                    st.setString(9, directorPicture);

                    st.executeUpdate();

                    j++;
                }

            }
        }
        baseDao.release(conn, st, rs);
    }

    public ArrayList<movie> readMovie() throws Exception{

        BaseDao baseDao=new BaseDao();

        Connection conn = baseDao.getConnection();

        PreparedStatement st;

        String sql="SELECT * FROM `movie_list`";

        st=conn.prepareStatement(sql);

        ResultSet rs = st.executeQuery();

        ArrayList<movie> movies=new ArrayList<movie>();
        while (rs.next())
        {
             movie movie=new movie();

             movie.setFileTitle(rs.getString("file_title"));

             movie.setCoverPicture(rs.getString("cover_picture"));

             movie.setType(rs.getString("type"));

             movie.setReleaseTime(rs.getString("release_time"));

             movie.setScore(rs.getString("score"));

             movie.setNationality(rs.getString("nationality"));

             movie.setDuration(rs.getString("duration"));

             movie.setDirectorName(rs.getString("director_name"));

             movie.setDirectorPicture(rs.getString("director_picture"));

             movies.add(movie);
        }
        return movies;
    }

}
