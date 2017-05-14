package net.hcfpvp.core.util;

import net.hcfpvp.api.API;

import java.util.Date;

/**
 * Created by Wout on 23/04/2017.
 */
public class MessageUtil
{
    public static String getReasonFormatted(String reason)
    {
        if (reason.trim().equalsIgnoreCase(""))
        {
            return "     &cNo reason specified.\n\n";
        }
        else
        {
            return "     &cReason: &6" + reason + "\n\n";
        }
    }

    public static String getServerDetails()
    {
        String message = "";

        message += "&eTS: &ats.upvp.us\n";
        message += "&eWebsite: &aupvp.us";

        return message;
    }

    public static String getExpireDateFormatted(Date expireDate)
    {
        return "     &cExpire Date: &6" + API.getTimeFormatter().getFormatted(expireDate.toInstant().toEpochMilli()) +
               "\n\n";
    }
}
