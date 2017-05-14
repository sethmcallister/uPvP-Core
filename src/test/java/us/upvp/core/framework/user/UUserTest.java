package us.upvp.core.framework.user;

import net.hcfpvp.api.framework.config.Config;
import net.hcfpvp.core.framework.config.UConfig;

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

        /*UUser user = new UUser(UUID.randomUUID(), "_Wout",
                               Lists.newArrayList(new URank(Group.DEVELOPER, "this-server")), new UPracticeProfile(),
                               new UHCFProfile(),
                               Sets.newHashSet(), "", Sets.newHashSet(), "Today", "pwd");

        String json = new GsonBuilder().create().toJson(user);

        OfflineUser offlineUser = new GsonBuilder().create().fromJson(json, UOfflineUser.class);

        System.out.printf("User %s has group %s", offlineUser.getLastName(), offlineUser.getGroup());*/

        //System.out.println(new GsonBuilder().create().toJson(user));
    }
}