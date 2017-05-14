package net.hcfpvp.core.framework.data.model;

import net.hcfpvp.api.profiles.Profile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import net.hcfpvp.api.API;
import net.hcfpvp.api.framework.data.messaging.MessageListener;
import net.hcfpvp.api.framework.user.OfflineUser;
import net.hcfpvp.api.framework.user.User;
import net.hcfpvp.core.framework.profile.ProfileHandler;
import net.hcfpvp.core.framework.user.UOfflineUser;
import net.hcfpvp.core.framework.user.UUser;
import net.hcfpvp.core.framework.user.UUserManager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Wout on 15/04/2017.
 */
public class RedisUserDao extends RedisGenericDao<UOfflineUser>
{
    private final ProfileHandler profileHandler;

    public RedisUserDao(ProfileHandler handler, JedisPool pool)
    {
        super(pool, "uniqueId");

        this.profileHandler = handler;

        new Thread(() ->
                   {
                       try (Jedis jedis = pool.getResource())
                       {
                           UUserManager mng = (UUserManager) API.getUserManager();

                           jedis.subscribe(new MessageListener("user", (msg) ->
                           {
                               for (int i = 0; i < mng.getUsers().size(); i++)
                               {
                                   User u = mng.getUsers().get(i);

                                   if (u.getUniqueId().equals(msg.getId()))
                                   {
                                       mng.getUsers()
                                          .set(i, new UUser(u.getUniqueId(),
                                                            mng.getDao().find(msg.getId()).getProfiles().values()));
                                   }
                               }
                           }), "update");
                       }
                   }).start();
    }

    @Override
    public void insert(UOfflineUser user)
    {
        try (Jedis jedis = getConnection())
        {
            jedis.set(getKey(user), String.valueOf(1));

            user.getProfiles().forEach((k, v) ->
                                       {
                                           v.getValues().forEach((ek, ev) ->
                                                                 {
                                                                     jedis.hset(getKey(user) + ":" + k, ek,
                                                                                getGson().toJson(ev));
                                                                 });
                                       });
        }
    }

    @Override
    public void delete(UOfflineUser user)
    {
        try (Jedis jedis = getConnection())
        {
            jedis.del(getKey(user));

            user.getProfiles().forEach((k, v) ->
                                       {
                                           jedis.del(getKey(user) + ":" + k);
                                       });
        }
    }

    @Override
    public UOfflineUser find(Object id)
    {
        try (Jedis jedis = getConnection())
        {
            String basicIdentifier = getKeyWithoutIdentifier() + ":" + id.toString();

            if (!jedis.exists(basicIdentifier))
            {
                return null;
            }

            UOfflineUser user = new UOfflineUser(UUID.fromString(id.toString()));

            profileHandler.getProfiles().forEach((k, v) ->
                                                 {
                                                     String basicProfileIdentifier = basicIdentifier + ":" + k;

                                                     if (!jedis.exists(basicProfileIdentifier))
                                                     {
                                                         return;
                                                     }

                                                     if (profileHandler.getProfiles()
                                                                       .keySet()
                                                                       .stream()
                                                                       .noneMatch(s -> s.equalsIgnoreCase(k)))
                                                     {
                                                         return;
                                                     }

                                                     Profile profile = null;
                                                     try
                                                     {
                                                         profile = profileHandler.getProfiles()
                                                                                 .entrySet()
                                                                                 .stream()
                                                                                 .filter((e) -> e.getKey()
                                                                                                 .equalsIgnoreCase(k))
                                                                                 .findAny()
                                                                                 .get()
                                                                                 .getValue()
                                                                                 .getConstructor(OfflineUser.class)
                                                                                 .newInstance(user);
                                                     }
                                                     catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored)
                                                     {
                                                         ignored.printStackTrace();
                                                     }

                                                     if (profile != null)
                                                     {
                                                         final Profile finalProfile = profile;

                                                         jedis.hgetAll(basicProfileIdentifier).forEach((fk, fv) ->
                                                                                                       {
                                                                                                           if (finalProfile
                                                                                                                   .getFields()
                                                                                                                   .stream()
                                                                                                                   .noneMatch(
                                                                                                                           f -> f.getKey()
                                                                                                                                 .equalsIgnoreCase(
                                                                                                                                         fk)))
                                                                                                           {
                                                                                                               return;
                                                                                                           }

                                                                                                           finalProfile.getValues()
                                                                                                                       .put(fk,
                                                                                                                            getGson()
                                                                                                                                    .fromJson(
                                                                                                                                            fv,
                                                                                                                                            finalProfile
                                                                                                                                                    .getFields()
                                                                                                                                                    .stream()
                                                                                                                                                    .filter(f -> f
                                                                                                                                                            .getKey()
                                                                                                                                                            .equalsIgnoreCase(
                                                                                                                                                                    fk))
                                                                                                                                                    .findFirst()
                                                                                                                                                    .get()
                                                                                                                                                    .getToken()
                                                                                                                                                    .getType()));
                                                                                                       });

                                                         user.getProfiles().put(k, profile);
                                                     }
                                                 });

            return user;
        }
    }

    @Override
    public List<UOfflineUser> findAll()
    {
        try (Jedis jedis = getConnection())
        {
            Set<String> keys = jedis.keys(getKeyWithoutIdentifier());

            return keys.stream().map(this::find).collect(Collectors.toList());
        }
    }
}
