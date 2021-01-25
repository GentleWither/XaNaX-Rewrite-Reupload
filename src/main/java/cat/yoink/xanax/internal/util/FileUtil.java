package cat.yoink.xanax.internal.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author yoink
 */
public final class FileUtil
{
    public static boolean saveFile(File file, String content)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static String getContents(File file)
    {
        try
        {
            if (!file.exists()) return "";
            StringBuilder builder = new StringBuilder();
            FileInputStream stream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(stream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = br.readLine()) != null) builder.append(line);

            br.close();

            return builder.toString();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static boolean deleteFolder(File folder)
    {
        try
        {
            Files.walk(folder.toPath()).map(Path::toFile).forEach(File::delete);
            return folder.delete();
        }
        catch (Exception ignored)
        {
            return false;
        }
    }
}
