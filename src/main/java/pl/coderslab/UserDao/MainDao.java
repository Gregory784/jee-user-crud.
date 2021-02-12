package pl.coderslab.UserDao;

import pl.coderslab.utils.DbUtil;

import java.sql.Connection;

public class MainDao {

    public static void main(String[] args) {

        UserDao userDao = new UserDao();

        // creat user
        User user = new User();

        user.setUserName("Władek");
        user.setEmail("wladek.kot@wp.pl");
        user.setPassword("haslo");

        userDao.create(user);
        System.out.println(user.toString());
/*
        // search user
        User read = userDao.read(1);

        System.out.println(read);

        // check user - null
        User readNotExist = userDao.read(1);

        System.out.println(readNotExist);
    }*/
}}