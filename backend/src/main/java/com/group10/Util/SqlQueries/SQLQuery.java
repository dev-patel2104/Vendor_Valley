package com.group10.Util.SqlQueries;

public class SQLQuery {

        public static final String insertVendorQuery = "INSERT INTO vendors (user_id, user_role, company_city, company_country, company_email, company_mobile, company_name, company_province, company_registration_number, company_street) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?);";
        public static final String addUserQuery = "INSERT INTO users (first_name, last_name, street, city, " +
                "province, country, email, mobile, is_vendor, Password)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        public static final String getFeaturedCategoriesQuery = "select count(distinct service_id) as totalServices, category_id, category_name, category_description " +
                "from service_category_association natural join service_categories " +
                "group by category_id " +
                "order by totalServices desc, category_id;";
        public static final String trendingServiceQuery = "select b.service_id,count(booking_id) as totalBookingsForService, service_name, service_description, service_price " +
                "from bookings as b join services as s on b.service_id = s.service_id " +
                "where datediff(curdate(), booking_date) <= 30 " +
                "group by service_id " +
                "order by totalBookingsForService desc;";
        public static final String trendingServiceQueryDefault = "select * from services order by service_id desc;";
        public static final String getUserByEmailID = "SELECT * FROM users WHERE email = ?";
        public static final String updateUserQuery = "UPDATE users SET first_name = ?, last_name = ?, street = ?, city = ?, province = ?, country = ?, email = ?, mobile = ?, is_vendor = ?, password = ?  WHERE (user_id = ?)";
        public static final String getPasswordRestInfoByUserId = "select verification_code, created_at from user_password_reset where user_id = (select user_id from users where email = ?) order by created_at DESC limit 1";
        public static final String insertUserResetPasswordEntry = "insert into user_password_reset (user_id, verification_code, created_at) values(?,?,?)";
        public static final String searchServiceQuery = "select s.*,  GROUP_CONCAT(DISTINCT c.category_name) AS categories, v.company_street, v.company_city, v.company_province, v.company_country, avg(r.rating) as avgRating, count(distinct b.booking_id) as totalBookings from services as s left join service_category_association as sca on s.service_id = sca.service_id left join service_categories as c on sca.category_id = c.category_id left join vendors as v on s.user_id = v.user_id left join reviews as r on s.service_id = r.service_id left join bookings as b on s.service_id = b.service_id where service_name like concat('%', ?, '%') or service_description like concat('%', ?, '%') or service_price like concat('%', ?, '%') or c.category_name like concat('%', ?, '%') or c.category_description like concat('%', ?, '%') group by s.service_id order by totalBookings desc, s.service_id;";
        public static final String getImagesForService = "SELECT s.service_id, si.image FROM services AS s LEFT JOIN service_images AS si ON s.service_id = si.service_id LEFT JOIN service_category_association AS sca ON s.service_id = sca.service_id LEFT JOIN service_categories AS c ON sca.category_id = c.category_id WHERE service_name LIKE CONCAT('%', ?, '%') OR service_description LIKE CONCAT('%', ?, '%') OR service_price LIKE CONCAT('%', ?, '%') OR c.category_name LIKE CONCAT('%', ?, '%') OR c.category_description LIKE CONCAT('%', ?, '%');";

}
