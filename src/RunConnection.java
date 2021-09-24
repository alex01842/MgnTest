import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;

public class RunConnection {

    private static String url;
    private static String user;
    private static String password;
    private static int n;

    public RunConnection(String url, String user, String password, int n) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public RunConnection() {
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void main(String[] args) throws SQLException, XMLStreamException, FileNotFoundException {

        RunConnection ping = new RunConnection();
        ping.setUrl("jdbc:postgresql://localhost:5432/bd_test");
        ping.setUser("postgres");
        ping.setPassword("Alex745553");
        ping.setN(20);

//  "jdbc:postgresql://localhost:5432/bd_test"
        ArrayList<Integer> volums = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) {
            volums.add(i);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < volums.size(); i++) {
            if (i != 0)
                sb.append(" ");
            sb.append(volums.get(i).toString());
        }
        String volumsToString = sb.toString();

        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statementDel = connection.prepareStatement ("DELETE FROM TEST");
        statementDel.execute();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO TEST values (?)");
        for (int i = 1; i < volums.size(); i++) {
            statement.setInt((1), volums.get(i));
            statement.execute();
        }

        try {
            StringWriter stringWriter = new StringWriter();

            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(new FileOutputStream("1.xml"));

            xMLStreamWriter.writeStartDocument();
            xMLStreamWriter.writeCharacters("\n");
            xMLStreamWriter.writeStartElement("entries");
            xMLStreamWriter.writeCharacters("\n");

            xMLStreamWriter.writeStartElement("entry");
            xMLStreamWriter.writeCharacters("\n");

            xMLStreamWriter.writeStartElement("field");
            xMLStreamWriter.writeCharacters("\n");


            xMLStreamWriter.writeCharacters(volumsToString);
            xMLStreamWriter.writeCharacters("\n");
            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.writeCharacters("\n");

            xMLStreamWriter.writeEndElement();
            xMLStreamWriter.writeCharacters("\n");
            xMLStreamWriter.writeEndDocument();

            xMLStreamWriter.flush();
            xMLStreamWriter.close();

             stringWriter.close();

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.close();
    }
}

