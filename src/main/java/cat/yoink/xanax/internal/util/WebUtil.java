package cat.yoink.xanax.internal.util;

import cat.yoink.xanax.internal.traits.interfaces.Minecraft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author yoink
 */
public final class WebUtil implements Minecraft
{
    public static String getContent(String url)
    {
        try
        {
            StringBuilder content = new StringBuilder();
            URL url1 = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url1.openStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) content.append(line).append("\n");
            return content.toString();
        }
        catch (Exception ignored) { }
        return "";
    }
}
