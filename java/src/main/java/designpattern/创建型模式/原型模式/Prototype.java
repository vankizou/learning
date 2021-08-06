package designpattern.创建型模式.原型模式;

/**
 * @author: ZOUFANQI
 * @create: 2021-08-04 18:25
 **/
public class Prototype {
    public static void main(String[] args) throws CloneNotSupportedException {
        UserInfo userInfo = new UserInfo("张三很帅");
        User userProp = new User("zhangsan", userInfo);
        User user2 = userProp.clone();

        System.out.println(
                String.format(
                        "原型对象 - 对象地址: %s, username: %s, userInfo地址: %s, alias: %s",
                        userProp,
                        userProp.getUsername(),
                        userProp.getUserInfo(),
                        userProp.getUserInfo().getAlias()
                )
        );
        System.out.println(
                String.format(
                        "克隆对象 - 对象地址: %s, username: %s, userInfo地址: %s, alias: %s",
                        user2,
                        user2.getUsername(),
                        user2.getUserInfo(),
                        user2.getUserInfo().getAlias()
                )
        );
    }
}

class User implements Cloneable {
    private final String username;
    private UserInfo userInfo;

    public User(String username, UserInfo userInfo) {
        this.username = username;
        this.userInfo = userInfo;
        System.out.println("创建原型对象User");
    }

    public String getUsername() {
        return username;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    protected User clone() throws CloneNotSupportedException {
        System.out.println("拷贝原型对象User");
        User clone = (User) super.clone();

        // 深克隆
        // 该行代码注释则浅克隆
        clone.userInfo = clone.userInfo.clone();

        return clone;
    }
}

class UserInfo implements Cloneable {
    private final String alias;

    public UserInfo(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    protected UserInfo clone() throws CloneNotSupportedException {
        System.out.println("拷贝对象UserInfo");
        return (UserInfo) super.clone();
    }
}
