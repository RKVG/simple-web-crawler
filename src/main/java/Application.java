import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Application {

  private static final String HREF = "href";

  public static void main(String[] args) {
    try {
      Document doc = Jsoup.connect("https://gocardless.com/").get();

      doc.select("a[href]").forEach(link ->
        System.out.println(link.attr(HREF) + " -> " + link.attr("abs:" + HREF))
      );

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
