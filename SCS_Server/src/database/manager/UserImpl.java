package database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import database.dao.UserInterface;
import database.utils.JDBCUtils;
import entity.Message;
import entity.Student;

public class UserImpl implements UserInterface{
    
    private static UserImpl u_instance = null;
    private UserImpl(){}
    
    public static UserImpl getInstance() {
        if (u_instance == null) {
            u_instance = new UserImpl();
        }
        return u_instance;
    }
    
    @Override
    public Student getStudentById(String phoneNumber) {
        Student stu = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "select * from user where phone_number = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                String stuName = rs.getString("username");
                stu = new Student(phoneNumber, stuName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            JDBCUtils.free(null, ps, con);
        }
        return stu;
    }
    
    @Override
    public boolean haveUser(String phoneNumber) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "select * from user where phone_number = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally
        {
            JDBCUtils.free(null, ps, con);
        }
    }
    
    @Override
    public boolean setUser(String phoneNumber, String name, String password) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "insert into user (phone_number,username,password)"
                    + " value (?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            ps.setString(2, name);
            ps.setString(3, password);
            int i = ps.executeUpdate();
            if (0 == i) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally
        {
            JDBCUtils.free(null, ps, con);
        }
        return true;
    }
    
    @Override
    public ArrayList<Message> getMessage() {
        Message ms = null;
        ArrayList<Message> retList = new ArrayList<Message>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "select * from message";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ms = new Message();
                ms.setMsId(rs.getInt(1));
                ms.setOwnerId(rs.getString(2));
                ms.setMsTitle(rs.getString(3));
                ms.setMsBody(rs.getString(4));
                retList.add(ms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            JDBCUtils.free(null, ps, con);
        }
        return retList;
    }
    
    @Override
    public ArrayList<Message> getMessage(String phoneNumber) {
        Message ms = null;
        ArrayList<Message> retList = new ArrayList<Message>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "select * from message where owner = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                ms = new Message();
                ms.setMsId(rs.getInt(1));
                ms.setOwnerId(rs.getString(2));
                ms.setMsTitle(rs.getString(3));
                ms.setMsBody(rs.getString(4));
                retList.add(ms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            JDBCUtils.free(null, ps, con);
        }
        return retList;
    }
    
    @Override
    public boolean setMessage(String owner, String msTitle, String msBody) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "insert into message (owner,ms_title,ms_body)"
                    + " value (?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, owner);
            ps.setString(2, msTitle);
            ps.setString(3, msBody);
            int i = ps.executeUpdate();
            if (0 == i) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally
        {
            JDBCUtils.free(null, ps, con);
        }
        return true;
    }
    
    @Override
    public boolean deleteMessage(int msId) {
        // TODO Auto-generated method stub
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "delete from message where ms_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, msId);
            int i = ps.executeUpdate();
            if (0 == i) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally
        {
            JDBCUtils.free(null, ps, con);
        }
        return true;
    }
}
