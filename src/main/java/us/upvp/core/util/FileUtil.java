package us.upvp.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Wout on 15/04/2017.
 */
public class FileUtil
{
    public static void createDirIfNotExists(Path dir)
    {
        if (!Files.exists(dir))
        {
            try
            {
                Files.createDirectory(dir);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
