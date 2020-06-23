
import Connection.ConnectionProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductList extends HttpServlet {

    private Connection con;
    private PreparedStatement ps;

    @Override
    public void init() {
        try {
            String sql = "SELECT pcode, pname FROM PRODUCTS where pcatg=?";
            con = ConnectionProvider.connect();
            ps = con.prepareStatement(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String s = request.getParameter("ct");
        PrintWriter out = response.getWriter();
        
        try {
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>E-CommerceWebApp</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Select Product</h1>");
            out.println("<hr>");
            while (rs.next()) {
                String s1 = rs.getString(1);
                String s2 = rs.getString(2);
                out.println("<a href=ProductDetails?code="+s1+">");
                out.println(s2);
                out.println("<a>");
                out.println("<br>");
            }
            out.println("<hr>");
            out.println("<a href=CategoryPage>Categories</a><br><br>");
            out.println("<a href=buyerpage.jsp>Go Back to Buyer Dashboard</a>");
            out.println("<hr>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
