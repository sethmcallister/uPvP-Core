package us.upvp.core.framework.user;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import us.upvp.api.framework.config.Config;
import us.upvp.api.framework.permission.Group;
import us.upvp.api.framework.user.OfflineUser;
import us.upvp.core.framework.config.UConfig;
import us.upvp.core.framework.permission.URank;
import us.upvp.core.framework.user.profile.UHCFProfile;
import us.upvp.core.framework.user.profile.UPracticeProfile;

import java.util.UUID;

/**
 * Created by Wout on 16/04/2017.
 */
public class UUserTest
{
    public static void main(String[] args)
    {
        Config config = new UConfig("server-name: test\n" +
                                    "environment: TEST\n" +
                                    "database:\n" +
                                    "   hostname: localhost\n" +
                                    "   port: 6379");
        config.load();

        UUser user = new UUser(UUID.randomUUID(), "_Wout",
                               Lists.newArrayList(new URank(Group.DEVELOPER, "this-server")), new UPracticeProfile(),
                               new UHCFProfile(),
                               Lists.newArrayList(), Lists.newArrayList(), "Today", "pwd");

        String json = new GsonBuilder().create().toJson(user);

        OfflineUser offlineUser = new GsonBuilder().create().fromJson(json, UOfflineUser.class);

        System.out.printf("User %s has group %s", offlineUser.getLastName(), offlineUser.getGroup());

        //System.out.println(new GsonBuilder().create().toJson(user));
    }
}