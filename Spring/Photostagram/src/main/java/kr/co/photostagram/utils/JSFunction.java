package kr.co.photostagram.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * 날짜 : 2023/03/08
 * 이름 : 조주영
 * 내용 : 도우미 클래스
 *
 */
public class JSFunction {

    /*** 메시지 알림창만 띄우기 ***/
    public static void alert(HttpServletResponse resp, String msg){

        try {
            resp.setContentType("text/html; charset=utf-8");
            PrintWriter writer = resp.getWriter();

            String script = ""
                    + "<script>"
                    + " alert ('" + msg + "');"
                    + "</script>";
            writer.print(script);
        } catch (IOException e){
            e.printStackTrace();
        }

    }


    public static void test(HttpServletResponse resp){

    }

    /*** 메시지 알림창을 띄운 후 새로고침 ***/
    public static void alertReload(HttpServletResponse resp, String msg){

        try {
            resp.setContentType("text/html; charset=utf-8");
            PrintWriter writer = resp.getWriter();

            String script = ""
                    + "<script>"
                    + " alert ('" + msg + "');"
                    + " location.reload();"
                    + "</script>";
            writer.print(script);
        } catch (IOException e){
            e.printStackTrace();
        }

    }


    /*** 메시지 알림창을 띄운 후 명시한 url로 이동 ***/
    public static void alertLocation(HttpServletResponse resp, String msg, String url){

        try {
            resp.setContentType("text/html; charset=utf-8");
            PrintWriter writer = resp.getWriter();

            String script = ""
                    + "<script>"
                    + " alert ('" + msg + "');"
                    + "      location.href='" + url + "';"
                    + "</script>";
            writer.print(script);
        } catch (IOException e){
            e.printStackTrace();
        }

    }


    /*** 메시지 알림창을 띄운 후 이전 페이지로 이동 ***/
    public static void alertBack(HttpServletResponse resp, String msg){

        try {
            resp.setContentType("text/html; charset=utf-8");
            PrintWriter writer = resp.getWriter();
            String script = ""
                    + "<script>"
                    + " alert('" + msg +"');"
                    + "   history.back();"
                    + "</script>";
            writer.print(script);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
