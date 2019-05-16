package com.example.myapplication.Etc;

public class ServerData {//필요하면 수정하되, 수정 전에 사용되고 있는 곳이 있는지 꼭 확인
    public static String Select_Daily_Analysis(String uid) {
        return getResultDB("select_daily_analysis_by_userid", "User_id", uid);
    }

    public static String Insert_Daily_Analysis(String uid, String ar, String ad) {
        return getResultDB("insert_daily_analysis", "User_id", uid, "Analysis_result", ar, "Analysis_date", ad);
    }

    public static String Update_Daily_Analysis(String uid, String ar, String ad) {
        return getResultDB("update_daily_analysis_by_userid", "User_id", uid, "Analysis_result", ar, "Analysis_date", ad);
    }

    public static String Delete_Daily_Analysis(String uid) {
        return getResultDB("delete_daily_analysis_by_userid", "User_id", uid);
    }

    public static String Select_Diet(String uid) {
        return getResultDB("select_diet_by_userid", "User_id", uid);
    }

    public static String Insert_Diet(String dietdate, String uid, String tid, String mt, String dietdisc) {
        return getResultDB("insert_diet", "Diet_date", dietdate,
                "User_id", uid, "Trainner_id", tid, "Meal_Type", mt, "Diet_discribe", dietdisc);
    }

    public static String Update_Diet(String uid, String dd) {
        return getResultDB("update_diet", "User_id", uid, "Diet_date", dd);
    }

    public static String Delete_Diet(String uid) {
        return getResultDB("delete_diet_by_userid", "User_id", uid);
    }

    public static String getResultDB(String phpname, String... strings) {
        String str = "";
        DBPHPTask task = new DBPHPTask(phpname);
        try {
            str = task.execute(strings).get();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /*

CREATE TABLE `daily_analysis` (
  `User_id` int(10) NOT NULL,
  `Analysis_result` varchar(255) NOT NULL,
  `Analysis_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `diet` (
  `Diet_id` int(10) NOT NULL,
  `Diet_date` date NOT NULL,
  `User_id` int(10) NOT NULL,
  `Trainner_id` int(10) DEFAULT NULL,
  `Meal_Type` varchar(20) NOT NULL,
  `Diet_discribe` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `exercise` (
  `Exercise_id` int(10) NOT NULL,
  `Exercise_name` varchar(255) NOT NULL,
  `Category` varchar(255) NOT NULL,
  `Detail_category` varchar(255) NOT NULL,
  `Difficulty` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `exercise_data` (
  `User_id` int(10) NOT NULL,
  `Weak_part` varchar(255) NOT NULL,
  `Exercise_time` datetime NOT NULL,
  `Weak_angle` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `monthly_analysis` (
  `User_id` int(10) NOT NULL,
  `Analysis_result` varchar(255) NOT NULL,
  `Analysis_month` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `my_routine` (
  `Routine_id` int(10) NOT NULL,
  `Routine_alias` varchar(255) NOT NULL,
  `Exercise_id` int(10) NOT NULL,
  `User_id` int(10) NOT NULL,
  `Related_trainner` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `trainner` (
  `Trainner_id` int(10) NOT NULL,
  `E-mail` varchar(2555) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Trainner_name` varchar(255) NOT NULL,
  `Gym_name` varchar(255) NOT NULL,
  `Gym_address` varchar(255) NOT NULL,
  `Phone_number` int(15) NOT NULL,
  `Token` varchar(255) NOT NULL,
  `Sign_in_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `User_id` int(10) NOT NULL,
  `E-mail` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `User_name` varchar(255) NOT NULL,
  `Home_address` varchar(255) NOT NULL,
  `Phone_number` int(15) NOT NULL,
  `Token` varchar(255) NOT NULL,
  `Sign_in_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `video` (
  `Video_id` int(10) NOT NULL,
  `URL` varchar(255) NOT NULL,
  `Upload_date` date NOT NULL,
  `Decription` varchar(255) NOT NULL,
  `Trainner_id` int(10) NOT NULL,
  `Related_exersice` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
     */
}
