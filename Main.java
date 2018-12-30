import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;




public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        String start = "https://en.wikipedia.org/wiki/", finish = "https://en.wikipedia.org/wiki/";

        URL startURL = null, finishURL = null;


        if(args.length < 2)
        {
            Scanner reader = new Scanner(System.in);
            System.out.println("Please enter a start page:\n");
            start = start.concat(reader.nextLine());
            System.out.println("\nPlease enter a target page:\n");
            finish = finish.concat(reader.nextLine());

            reader.close();

            System.out.println("Start: " + start + " Finish: " + finish);
        }
        else {
            start.concat(args[0]);
            finish.concat(args[1]);
        }

        try {
            startURL = new URL(start);
            finishURL = new URL(finish);
        }catch(Exception e)
        {
            System.out.println("Need a real URL " + e);
        }
        try {
            sendGet(startURL);
        }catch(Exception e)
        {
            System.out.println("Exception " + e);
        }




    }

    private static void sendGet(URL getURL) throws Exception {
        HttpURLConnection con = (HttpURLConnection) getURL.openConnection();

        //Request header
        //con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + getURL);
        System.out.println("\nResponse code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        Pattern p = Pattern.compile("href=\"([^\"]*)\"");
        ArrayList<String> links = new ArrayList<String>();
        String temp;
        while((inputLine = in.readLine()) != null) {

            //response.append(inputLine);
            //response.append("\n");
            Matcher m = p.matcher(inputLine);
            while (m.find()) {
                temp = (m.group(1));
                if(!temp.contains(":") && !temp.contains("#") && temp.contains("/wiki/"))
                {
                    links.add(temp);
                }
                //System.out.println(m.group(1));

            }
        }
        in.close();

        //print the result
        for(int i = 0; i < links.size(); i++)
        {
            System.out.println(links.get(i));
        }
        System.out.println(response.toString());
    }
}
