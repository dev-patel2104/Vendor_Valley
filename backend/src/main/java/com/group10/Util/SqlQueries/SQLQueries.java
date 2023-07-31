package com.group10.Util.SqlQueries;

public class SQLQueries {

        public static final String insertVendorQuery = "INSERT INTO vendors (user_id, user_role, company_city, company_country, company_email, company_mobile, company_name, company_province, company_registration_number, company_street) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?);";
        public static final String addUserQuery = "INSERT INTO users (first_name, last_name, street, city, province, country, email, mobile, is_vendor, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        public static final String getFeaturedCategoriesQuery = "select count(distinct service_id) as totalServices, category_id, category_name, category_description, category_image from service_category_association natural join service_categories group by category_id order by totalServices desc, category_id;";
        public static final String trendingServiceQuery = "select b.service_id,count(booking_id) as totalBookingsForService, service_name, service_description, service_price, image \n" +
                "from bookings as b join services as s on b.service_id = s.service_id left join service_images as si on s.service_id = si.service_id \n" +
                "where datediff(curdate(), booking_date) <= 30 \n" +
                "group by service_id \n" +
                "order by totalBookingsForService desc;";
        public static final String trendingServiceQueryDefault = "select * from services as s left join service_images as si on s.service_id = si.service_id group by s.service_id order by s.service_id desc;";
        public static final String checkIfBookingExistsQuery = "select * from bookings where booking_id = ? and service_id = ? and user_id = ?;";
        public static final String getUserByEmailID = "SELECT * FROM users WHERE email = ?";
        public static final String updateUserQuery = "UPDATE users SET first_name = ?, last_name = ?, street = ?, city = ?, province = ?, country = ?, email = ?, mobile = ?, is_vendor = ?, password = ?  WHERE (user_id = ?)";
        public static final String updateCompanyDetailsQuery = "update vendors set user_role = ?, company_name = ?, company_email = ?, company_registration_number = ?, company_mobile = ?, company_street = ?, company_city = ?, company_province = ?, company_country = ?\n" +
                "where user_id = ?;";
        public static final String updateService = "update services set service_name = ?, service_description = ?, service_price = ? where service_id = ?;";
        public static final String deleteAllServiceCategoryAssociation = "delete from service_category_association where service_id = ?;";
        public static final String deleteService = "delete from services where service_id = ?;";
        public static final String deleteAllServiceImages = "delete from service_images where service_id = ?;";
        public static final String getPasswordRestInfoByUserId = "select verification_code, created_at from user_password_reset where user_id = (select user_id from users where email = ?) order by created_at DESC limit 1";
        public static final String insertUserResetPasswordEntry = "insert into user_password_reset (user_id, verification_code, created_at) values(?,?,?)";
        public static final String searchServiceQuery = "select s.*,  group_concat(distinct c.category_name) as categories, v.company_street, v.company_city, v.company_province, v.company_country, avg(r.rating) as avgrating, count(distinct b.booking_id) as totalbookings from services as s left join service_category_association as sca on s.service_id = sca.service_id left join service_categories as c on sca.category_id = c.category_id left join vendors as v on s.user_id = v.user_id left join reviews as r on s.service_id = r.service_id left join bookings as b on s.service_id = b.service_id where service_name like concat('%', ?, '%') or service_description like concat('%', ?, '%') or service_price like concat('%', ?, '%') or c.category_name like concat('%', ?, '%') or c.category_description like concat('%', ?, '%') group by s.service_id order by totalbookings desc, s.service_id;";
        public static final String getImagesForService = "select s.service_id, si.image from services as s left join service_images as si on s.service_id = si.service_id left join service_category_association as sca on s.service_id = sca.service_id left join service_categories as c on sca.category_id = c.category_id where service_name like concat('%', ?, '%') or service_description like concat('%', ?, '%') or service_price like concat('%', ?, '%') or c.category_name like concat('%', ?, '%') or c.category_description like concat('%', ?, '%');";

        public static final String getImagesForServiceID = "select * from services as s Left join service_images as si on s.service_id = si.service_id where s.service_id = ?;";
        public static final String checkIfServiceExistsQuery = "select * from services where service_id = ?";
        public static final String getReviewsForServiceQuery = "select r.*, concat(u.first_name,' ',u.last_name) as name, u.city, u.country from reviews as r join users as u on r.user_id = u.user_id where service_id = ? order by review_id desc;";
        public static final String insertReviewQuery = "insert into reviews (booking_id, service_id, user_id, title, comment_text, review_date, rating) values(?, ?, ?, ?, ?, ?, ?);";
        public static final String getServiceDetailsQuery = "select s.*, v.company_email from services as s join vendors as v on v.user_id = s.user_id where service_id = ?;";
        public static final String getServiceDetailsByUser = "select s.* , group_concat(sc.category_name) as categories from services as s natural join service_category_association as sca natural join service_categories as sc  where s.user_id = ? group by s.service_id;";
        public static final String getUserByID = "select * from users Left join vendors on users.user_id = vendors.user_id where users.user_id = ?;";
        public static final String vendorDashboardInfoQuery = "select s.service_id, u.user_id, b.booking_id, b.booking_status, b.booking_date, b.start_date, b.end_date from services as s join bookings as b on b.service_id = s.service_id join users as u on u.user_id = b.user_id where s.user_id = ? group by s.service_id, b.booking_id, u.user_id order by u.user_id, b.booking_date;";
        public static final String getVendorBookings = "select s.service_name, b.*, u.* \n" +
                "from services as s join bookings as b on b.service_id = s.service_id join users as u on u.user_id = b.user_id " +
                "where s.user_id = ? \n" +
                "group by s.service_id, b.booking_id, u.user_id order by u.user_id, b.booking_date;";
        public static final String getCustomerBookings = "select b.booking_id, s.service_name, b.booking_date, b.start_date, b.end_date, b.booking_status " +
                "from bookings as b join services as s on b.service_id = s.service_id " +
                "where b.user_id = ?;";
        public static final String getCategories = "select category_id, category_name, category_description from service_categories";
        public static  final String insertService = "INSERT INTO services (user_id, service_name, service_description, service_price) " +
                "VALUES (?, ?, ?, ?);";
        public static final String insertServiceCategoryAssociation = "INSERT INTO service_category_association (service_id, category_id) VALUES (?, ?)";
        public static final String insertServiceImages = "Insert into service_images (service_id, image, time_stamp) values (?,?,NOW());";
        public static final String insertBookingEntry = "INSERT INTO bookings (user_id, service_id, booking_date, start_date, end_date, booking_status) VALUES (?, ?, ?, ?, ?, ?);";
        public static final String updateBookingEntry = "update bookings set booking_status = ? where booking_id = ?;";

}
