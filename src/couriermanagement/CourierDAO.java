package couriermanagement;

import java.sql.*;
import java.util.ArrayList;

public class CourierDAO {

    // Add Courier
    public static boolean addCourier(Courier c) {

        String sql = "INSERT INTO couriers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.courierId);
            ps.setString(2, c.senderName);
            ps.setString(3, c.senderAddress);
            ps.setString(4, c.senderPhone);
            ps.setString(5, c.receiverName);
            ps.setString(6, c.receiverAddress);
            ps.setString(7, c.receiverPhone);
            ps.setString(8, c.courierType);
            ps.setDouble(9, Double.parseDouble(c.weight));
            ps.setDate(10, Date.valueOf(c.bookingDate));
            ps.setString(11, c.deliveryStatus);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // View All Couriers
    public static ArrayList<Courier> getAllCouriers() {

        ArrayList<Courier> list = new ArrayList<>();

        String sql = "SELECT * FROM couriers";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Courier c = new Courier(
                        rs.getString("courier_id"),
                        rs.getString("sender_name"),
                        rs.getString("sender_address"),
                        rs.getString("sender_phone"),
                        rs.getString("receiver_name"),
                        rs.getString("receiver_address"),
                        rs.getString("receiver_phone"),
                        rs.getString("courier_type"),
                        rs.getString("weight"),
                        rs.getDate("booking_date").toString(),
                        rs.getString("delivery_status")
                );

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Search Courier
    public static Courier getCourierById(String id) {

        String sql = "SELECT * FROM couriers WHERE courier_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Courier(
                        rs.getString("courier_id"),
                        rs.getString("sender_name"),
                        rs.getString("sender_address"),
                        rs.getString("sender_phone"),
                        rs.getString("receiver_name"),
                        rs.getString("receiver_address"),
                        rs.getString("receiver_phone"),
                        rs.getString("courier_type"),
                        rs.getString("weight"),
                        rs.getDate("booking_date").toString(),
                        rs.getString("delivery_status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update Courier
    public static boolean updateCourier(Courier c) {

        String sql = "UPDATE couriers SET sender_name=?, sender_address=?, sender_phone=?, receiver_name=?, receiver_address=?, receiver_phone=?, courier_type=?, weight=?, booking_date=?, delivery_status=? WHERE courier_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.senderName);
            ps.setString(2, c.senderAddress);
            ps.setString(3, c.senderPhone);
            ps.setString(4, c.receiverName);
            ps.setString(5, c.receiverAddress);
            ps.setString(6, c.receiverPhone);
            ps.setString(7, c.courierType);
            ps.setDouble(8, Double.parseDouble(c.weight));
            ps.setDate(9, Date.valueOf(c.bookingDate));
            ps.setString(10, c.deliveryStatus);
            ps.setString(11, c.courierId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Courier
    public static boolean deleteCourier(String id) {

        String sql = "DELETE FROM couriers WHERE courier_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String getNextCourierId() {

        String sql = "SELECT MAX(CAST(SUBSTRING(courier_id, 2) AS UNSIGNED)) FROM couriers";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                int lastId = rs.getInt(1);

                if (rs.wasNull()) {
                    return "C101";
                }

                return "C" + (lastId + 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static boolean verifyAdminPassword(String currentPassword) {

        String sql = "SELECT password FROM admins WHERE username = 'admin'";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getString("password").equals(currentPassword);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateAdminPassword(String newPassword) {

        String sql = "UPDATE admins SET password = ? WHERE username = 'admin'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
