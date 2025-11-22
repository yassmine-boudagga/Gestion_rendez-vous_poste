package Basedonner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;

public class Base {
    protected Connection connection;
    private String basedonner;
    private String user;
    private  String password;
    public Base(String basedonner,String user,String password){
        this.basedonner=basedonner;
        this.user=user;
        this.password=password;
        this.connection =this.connection_DataBase(basedonner,user,password);
    }
    public Base(){}
    public String getBasedonner() {
        return basedonner;
    }
    public String getPassword() {
        return password;
    }
    public String getUser() {
        return user;
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public void setBasedonner(String basedonner) {
        this.basedonner = basedonner;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUser(String user) {
        this.user = user;
    }
    //fonction connection_BataBase permet de retourner le reference
    // de base de donner  en cas de connectiviter sinon return  null est afficher l'erreur
    public Connection connection_DataBase(String basedonner,String user,String password){
        try {
            this.connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+basedonner,user,password);
            System.out.println("vous ete connecter a la base:"+this.basedonner);
            return this.connection;
        } catch (SQLException e) {
            System.out.println("erreur de connectiviter");
            System.out.println("verifier de ecrire le nom de base de donner ou le user ou le password correct");
            System.out.println("Une erreur SQL s'est produite.");
            System.out.println("Message : " + e.getMessage());
            System.out.println("SQLState : " + e.getSQLState());
            System.out.println("Code d'erreur : " + e.getErrorCode());
            return null;
        }
    }
    //permette de deconnection de base de donner pour libérer le source
    public void deconnection(){
        if(this.connection==null){
            System.out.println("tu na pas connecter a la base de donner pour connecter");
        }
        else {
            try {
                connection.close();
                System.out.println("deconnection de la base:"+this.basedonner+" avec succes");
            } catch (SQLException e) {
                System.out.println("erreur de deconnecter");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
        }
    }
    public String[] getTables(){
        if(this.connection==null){
            System.out.println("vous ete n\'est pas connecter");
            return null;
        }else {
            try {
                DatabaseMetaData metaData = this.connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
                ArrayList<String> t = new ArrayList<String>();
                while (tables.next()) {
                    t.add(tables.getString("TABLE_NAME"));
                }
                String[] tab = new String[t.size()];
                for (int i = 0; i < tab.length; i++) {
                    tab[i] = t.get(i);
                }
                tables.close();
                return tab;
            } catch (SQLException e) {
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }

    }
    /*permette de insere des ligne dans le tableau il faux donner en parametre
     * reference de connection ,nom de tableau,matrice de valeur de case il vaux étre
     * détaille en saisire le matrice pour ne pas oublier oucun colone si tu veux pas insert un valeur
     * dans un case lasser le case chaine vide sétou*/
    public void insert(String tableau,String[][]valeur){
        if(connection==null){
            System.out.println("tu na pas connecter a la base de donner pour ajouter des ligne");
        }
        else{
            try{
                Statement statement=connection.createStatement();
                StringBuilder requet=new StringBuilder("INSERT INTO "+tableau+"(");
                ResultSet result=statement.executeQuery("SELECT * FROM "+tableau+";");
                ResultSetMetaData metaData=result.getMetaData();
                int nc=metaData.getColumnCount();
                for (int i = 0; i <valeur.length ; i++)
                {
                    if(valeur[i].length!=nc){
                        System.out.println("le nombre de valeur différent a nombre de colone de tableau sur le base de donner");
                        return;
                    }
                    i++;
                }
                for (int i = 1; i <=nc ; i++) {
                    if (i==nc){
                        requet.append(metaData.getColumnName(i));
                        break;
                    }
                    requet.append(metaData.getColumnName(i)+",");
                }
                requet.append(")VALUES");
                for (int i = 0; i < valeur.length; i++) {
                    requet.append("(");
                    for (int j = 0; j < nc; j++) {
                        if (j==valeur[i].length-1){
                            requet.append("?");
                            break;
                        }
                        requet.append("?,");
                    }
                    if(i==valeur.length-1){
                        requet.append(");");
                        break;
                    }
                    requet.append("),");
                }
                PreparedStatement prepare=connection.prepareStatement(requet.toString());
                for (int i = 0; i < valeur.length; i++) {
                    for (int j = 0; j < nc; j++) {
                        prepare.setObject(j+i*nc + 1, valeur[i][j]);
                    }
                }
                System.out.println(prepare);
                int rowaffecte=prepare.executeUpdate();
                System.out.println(rowaffecte+"lignes insérer");
                result.close();
                prepare.close();
                statement.close();
            }
            catch (SQLException e){
                System.out.println("verifier de les valeur il son de ordre correct et le méme forme de type et respecte le condition de saisir\ncomme le valeur ne repéte pas ou dotre condition");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());

            }
        }
    }
    /*permette de insere des ligne dans le tableau il faux donner en parametre
     * reference de connection ,nom de tableau,table des valeurs de case il faux étre
     * détaille en saisire le tableau pour ne pas oublier aucun colone si tu veux pas insert un valeur
     * dans un case lasser le case vide sétou*/
    public void requetAffichage(String requetSQL){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
        }
        else{
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(requetSQL);
                // 4. Récupération des métadonnées
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount(); // Nombre de colonnes
                while (resultSet.next()){
                    for (int i = 1; i <=columnCount ; i++) {
                        System.out.print("|"+metaData.getColumnName(i)+":"+resultSet.getString(i));
                    }
                    System.out.println();
                }
                resultSet.close();
                statement.close();
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
        }
    }
    public boolean insert(String tableau,String[]valeur){
        if(connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return false;
        }
        else{
            try{
                Statement statement=connection.createStatement();
                StringBuilder requet=new StringBuilder("INSERT INTO "+tableau+"(");
                ResultSet result=statement.executeQuery("SELECT * FROM "+tableau+";");
                ResultSetMetaData metaData=result.getMetaData();
                int nc=metaData.getColumnCount();
                if(valeur.length!=nc){
                    System.out.println("le nombre de valeur différent a nombre de colone de tableau sur le base de donner");
                    return false;
                }
                for (int i = 1; i <=nc ; i++) {
                    if (i==nc){
                        requet.append(metaData.getColumnName(i));
                        break;
                    }
                    requet.append(metaData.getColumnName(i)+",");
                }
                requet.append(")VALUES(");
                for (int i = 0; i < valeur.length; i++) {
                    if (i==valeur.length-1){
                        requet.append("?);");
                        break;
                    }
                    requet.append("?,");
                }
                PreparedStatement prepare=connection.prepareStatement(requet.toString());
                for (int i = 0; i < nc; i++) {
                    prepare.setObject(i+1,valeur[i]);
                }
                System.out.println(prepare);
                int rowaffecte=prepare.executeUpdate();
                System.out.println(rowaffecte+"lignes insérer");
                result.close();
                prepare.close();
                statement.close();
                return true;
            }
            catch (SQLException e){
                System.out.println("verifier de les valeur il son de ordre correct et le méme forme de type et respecte le condition de saisir\ncomme le valeur ne repéte pas ou dotre condition");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return false;

            }
        }
    }
    //permette de retourne le nombre de ligne d'un affichage
    public int nbr_ligne(String table,String[]colomeCondition,String[]valeurCondition,String[]operation) {
        if (connection == null) {
            System.out.println("tu n\'est pas connecter dans la base");
            return -1;
        } else if (colomeCondition.length != valeurCondition.length || operation.length != colomeCondition.length) {
            System.out.println("lensemble de valeur");
            return -1;
        } else {
            try {
                StringBuilder requet = new StringBuilder("SELECT COUNT(*) FROM " + table + " WHERE ");
                for (int i = 0; i < colomeCondition.length; i++) {
                    requet.append(colomeCondition[i]).append(" ").append(operation[i]).append(" ?");
                    if (i < colomeCondition.length - 1) {
                        requet.append(" AND ");
                    }
                }
                requet.append(";");
                PreparedStatement prepa = connection.prepareStatement(requet.toString());
                for (int i = 0; i < valeurCondition.length; i++) {
                    prepa.setObject(i + 1, valeurCondition[i]);
                }
                ResultSet resultSet = prepa.executeQuery();
                if (resultSet.next()) {
                    int c=resultSet.getInt(1);
                    resultSet.close();
                    prepa.close();
                    return c;
                }
            } catch (SQLException e) {
                System.out.println("erreure de calcule de nombre de ligne");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
            return -1;
        }
    }
    public int nbr_ligne(String table) {
        if (connection == null) {
            System.out.println("tu n\'est pas connecter dans la base");
            return -1;
        } else {
            try {
                StringBuilder requet = new StringBuilder("SELECT COUNT(*) FROM " + table +";");
                PreparedStatement prepa = connection.prepareStatement(requet.toString());
                ResultSet resultSet = prepa.executeQuery();
                if (resultSet.next()) {
                    int c=resultSet.getInt(1);
                    resultSet.close();
                    prepa.close();
                    return c;
                }
            } catch (SQLException e) {
                System.out.println("erreure de calcule de nombre de ligne");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
            return -1;
        }
    }
    public int nbl_requet(String requet){
        if (connection == null) {
            System.out.println("tu n\'est pas connecter dans la base");
            return -1;
        }else {
            try {
                PreparedStatement prepa = connection.prepareStatement(requet);
                ResultSet resultSet = prepa.executeQuery();
                int l=0;
                while(resultSet.next()) {
                    l++;
                }
                resultSet.close();
                prepa.close();
                return l;
            } catch (SQLException e) {
                System.out.println("erreure de calcule de nombre de ligne");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
            return -1;
        }
    }
    public void affichage(String table, String[] colomeCondition, String[] valeurCondition, String[] operation) {
        if (connection == null) {
            System.out.println("Vous n'êtes pas connecté à la base de données.");
            return;
        }
        if (colomeCondition.length != valeurCondition.length || operation.length != colomeCondition.length) {
            System.out.println("Les longueurs des tableaux de colonnes, valeurs et opérations ne correspondent pas.");
            return;
        }

        try {
            // Construction de la requête SQL avec placeholders
            StringBuilder requet = new StringBuilder("SELECT * FROM ").append(table).append(" WHERE ");
            for (int i = 0; i < colomeCondition.length; i++) {
                requet.append(colomeCondition[i]).append(" ").append(operation[i]).append(" ?");
                if (i < colomeCondition.length - 1) {
                    requet.append(" AND ");
                }
            }
            requet.append(";");

            PreparedStatement prepa = connection.prepareStatement(requet.toString());

            // Ajout des valeurs pour les placeholders
            for (int i = 0; i < valeurCondition.length; i++) {
                prepa.setObject(i + 1, valeurCondition[i]);
            }

            // Exécution de la requête
            ResultSet resultSet = prepa.executeQuery();

            // Récupération des métadonnées
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Affichage des résultats
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("|" + metaData.getColumnName(i) + ":" + resultSet.getString(i));
                }
                System.out.println();
            }
            resultSet.close();
            prepa.close();
        } catch (SQLException e) {
            System.out.println("Une erreur SQL s'est produite.");
            System.out.println("Message : " + e.getMessage());
            System.out.println("SQLState : " + e.getSQLState());
            System.out.println("Code d'erreur : " + e.getErrorCode());
        }
    }
    public void affichage(String tableau){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
        }
        else{
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT * FROM "+tableau+";");
                // 4. Récupération des métadonnées
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount(); // Nombre de colonnes
                while (resultSet.next()){
                    for (int i = 1; i <=columnCount ; i++) {
                        System.out.print("|"+metaData.getColumnName(i)+":"+resultSet.getString(i));
                    }
                    System.out.println();
                }
                resultSet.close();
                statement.close();
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
        }
    }
    public String[][] tableaffichage(String tableau){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        }
        else{
            try {
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT * FROM "+tableau+";");
                // 4. Récupération des métadonnées
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();// Nombre de colonnes
                int l=this.nbr_ligne(tableau);
                String [][]T=new String[l][columnCount];
                int j=0;
                while (resultSet.next()){
                    for (int i = 1; i <=columnCount ; i++) {
                        T[j][i-1]=resultSet.getString(i);
                    }
                    j++;
                }
                resultSet.close();
                statement.close();
                return T;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public String[][] tableaffichage(String tableau,String []colcondition,String[] value,String[]op){
        if (connection==null){
            System.out.println("tu na pas connecter a la base de donner pour afficher");
            return null;
        } else if (colcondition.length!=value.length) {
            System.out.println("erreur");
            return null;
        } else{
            try {
                StringBuilder requet = new StringBuilder("SELECT * FROM " + tableau + " WHERE ");
                for (int i = 0; i < colcondition.length; i++) {
                    requet.append(colcondition[i]).append(" ").append(op[i]).append(" ?");
                    if (i < colcondition.length - 1) {
                        requet.append(" AND ");
                    }
                }
                requet.append(";");
                Statement statement=connection.createStatement();
                PreparedStatement prepa = connection.prepareStatement(requet.toString());
                for (int i = 0; i < value.length; i++) {
                    prepa.setObject(i + 1, value[i]);
                }
                ResultSet resultSet = prepa.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();// Nombre de colonnes
                int l=this.nbr_ligne(tableau,colcondition,value,op);
                String [][]T=new String[l][columnCount];
                int j=0;
                while (resultSet.next()){
                    for (int i = 1; i <=columnCount ; i++) {
                        T[j][i-1]=resultSet.getString(i);
                    }
                    j++;
                }
                resultSet.close();
                statement.close();
                return T;
            }
            catch (SQLException e){
                System.out.println("erreur laffichage");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
                return null;
            }
        }
    }
    public void delete(String table,String []colomeCondition,String []valeurCondition,String[] operation){
        if(connection==null){
            System.out.println("vous n'etes pas connecté dans la base de donnée");
        }
        else{
            try {
                StringBuilder requet = new StringBuilder("DELETE FROM ").append(table).append(" WHERE ");
                for (int i = 0; i < colomeCondition.length; i++) {
                    requet.append(colomeCondition[i]).append(" ").append(operation[i]).append(" ?");
                    if (i < colomeCondition.length - 1) {
                        requet.append(" AND ");
                    }
                }
                requet.append(";");

                PreparedStatement prepa = connection.prepareStatement(requet.toString());

                // Ajout des valeurs pour les placeholders
                for (int i = 0; i < valeurCondition.length; i++) {
                    prepa.setObject(i + 1, valeurCondition[i]);
                }

                // Exécution de la requête
                System.out.println(prepa);
                int rowsDeleted = prepa.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Ligne supprimée avec succès.");
                } else {
                    System.out.println("Aucune ligne correspondante trouvée.");
                }

                // Fermer les ressources
                prepa.close();
            } catch (SQLException e) {
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
        }
    }
    public void update(String tableau,String[]colonecondition,String[]valeurcondition,String[]colone,String[]valeur){
        String[]op=new String[valeurcondition.length];
        for (int i = 0; i < op.length; i++) {
            op[i]="=";
        }
        if(this.connection==null){
            System.out.println("vous ete n\'est pas connecter en base de donner");
        }
        else if (nbr_ligne(tableau,colonecondition,valeurcondition,op)>1) {
            System.out.println("tu peux ajouter des notre condition pour vérifier quel est le ligne exacte tu va changer\nou donne un colone il posséde une cle primare");
        } else if (nbr_ligne(tableau,colonecondition,valeurcondition,op)==-1) {
            System.out.println("il na pas cette ligne sur le tableau");
        } else{
            try {
                StringBuilder requer=new StringBuilder("UPDATE "+tableau+" SET ");
                for (int i = 0; i < colone.length; i++) {
                    if(i==colone.length-1) {
                        requer.append(colone[i] + "=? WHERE ");
                        break;
                    }
                    requer.append(colone[i]+" =?,");
                }
                for (int i = 0; i < colonecondition.length; i++) {
                    if (i==colonecondition.length-1){
                        requer.append(colonecondition[i]+" = ?;");
                        break;
                    }
                    requer.append(colonecondition[i]+" =? AND ");
                }
                PreparedStatement prepa=connection.prepareStatement(requer.toString());
                for (int i = 0; i < valeur.length; i++) {
                    prepa.setObject(i+1,valeur[i]);
                }
                for (int i=0; i < valeurcondition.length; i++) {
                    prepa.setObject(i+1+valeur.length,valeurcondition[i]);
                }
                System.out.println(prepa);
                int a=prepa.executeUpdate();
                System.out.println("le resulta de laffichage:"+a);
            }
            catch (SQLException e){
                System.out.println("erreur update");
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
        }
    }
    public String[] getColomn(String table){
        if(this.connection==null){
            System.out.println("tu n\'est pas connecter a la base de donner");
            return null;
        }
        else{
            try{
                Statement statement=this.connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT * FROM "+table);
                ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
                int n=resultSetMetaData.getColumnCount();
                String []Col=new String[n];
                for (int i = 0; i < n; i++) {
                    Col[i]=resultSetMetaData.getColumnName(i+1);
                }
                resultSet.close();
                statement.close();
                return Col;
            }
            catch (SQLException e){
                System.out.println("erreur ddsd");
                System.out.println(e.getMessage());
                return null;
            }

        }
    }
    public void InformationColoment(String table){
        if(this.connection==null){
            System.out.println("tu n\'est pas connecter a la base de donner");
        }
        else{
            try{
                Statement statement=this.connection.createStatement();
                ResultSet resultSet=statement.executeQuery("SELECT * FROM "+table);
                ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
                int n=resultSetMetaData.getColumnCount();
                for (int i = 0; i < n; i++) {
                    System.out.println(resultSetMetaData.getColumnName(i+1)+":"+resultSetMetaData.getColumnTypeName(i+1)+"-");
                }
                resultSet.close();
                statement.close();
            }
            catch (SQLException e){
                System.out.println("erreur ddsd");
                System.out.println(e.getMessage());
            }

        }
    }
    public void Update_Delete(String RequetSql){
        if (this.connection==null){
            System.out.println("vous ete n\'est pas connecter a la base");
        }
        else{
            try {
                Statement statement=this.connection.createStatement();
                statement.executeUpdate(RequetSql);
                statement.close();
            }
            catch (SQLException e){
                System.out.println("verifier le nom de tableau ou verifier tu est dans le base de donner correct ou ton requet");
                System.out.println("Une erreur SQL s'est produite.");
                System.out.println("Message : " + e.getMessage());
                System.out.println("SQLState : " + e.getSQLState());
                System.out.println("Code d'erreur : " + e.getErrorCode());
            }
        }
    }
    public static void main(String []ags){
        Base proget=new Base("service_post","rejeb","");
        String[]col={"redevou"};
        String[]op={"<"};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d=new Date();
        String[]val={formatter.format(d)};
        System.out.println(formatter.format(d));
        String [][]t=proget.tableaffichage("redevou",col,val,op);
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                System.out.print(t[i][j]+" ");
            }
            System.out.println();
        }
        proget.deconnection();
    }
}