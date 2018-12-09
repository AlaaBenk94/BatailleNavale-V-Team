package batailleNavale.DaoSauvegarde;

import batailleNavale.Model.jeu.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class SaveLoadXML extends DAOSaveLoad {
    private static DAOSaveLoad daoSaveLoadxml;
    private Element racine;
    private Document document;
    private String nom="jeu.xml";
    private SaveLoadXML(){}

    public static DAOSaveLoad getInstence(){
        if(daoSaveLoadxml==null)daoSaveLoadxml=new SaveLoadXML();
        return daoSaveLoadxml;
    }


    @Override
    public void sauvegarder(Jeu jeu, String chemin) {

    }

    @Override
    public Jeu charger(String chemin) {
        return null;
    }

    private void ouvrire(String lien) throws IOException, SAXException, ParserConfigurationException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(new File(lien+nom));
        this.racine=document.getDocumentElement();
    }
    private void creation(String lien) throws ParserConfigurationException{
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        document = builder.newDocument();
        Element racine = document.createElement(lien+nom);
        document.appendChild(racine);
        this.racine=racine;
    }
    private void enregistrer(String lien ) throws TransformerException {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        final DOMSource source = new DOMSource(document);
        final StreamResult sortie = new StreamResult(new File(lien));
        transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(source, sortie);
    }
    private void fermer(String lien) throws TransformerConfigurationException, TransformerException{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(lien));
        transformer.transform(source, result);
    }
    private Element ajouter_dans(String balise,String sousbalise,String contenu){
        Element balise_mere;

        balise_mere=(Element)document.getElementsByTagName(balise).item(0);
        Element nouvellebalise=document.createElement(sousbalise);
        balise_mere.appendChild(nouvellebalise);

        if(contenu!=""){ nouvellebalise.appendChild(document.createTextNode(contenu));
        }

        return nouvellebalise;

    }
    private void aj_bal_att(String balise,String sousbalise,String contenu,String att){
        LinkedList<String> listatt = new LinkedList<>() ;
        LinkedList<String> val= new LinkedList<>() ;

        int ver=0;
        String s="";


        for(int i=0;i<att.length();i++){


            char c=att.charAt(i);
            if(c=='='){
                if(s==""){ver=1;}
                listatt.add(s);
                ver=1;
                s="";

            }
            if(c==';'){

                val.add(s);
                ver=0;
                s="";

            }
            if(c!=';'&&c!='=')s+=c;


        }
        Element e= ajouter_dans( balise, sousbalise, contenu);

        if(e!=null && ver==0 && listatt.size()==val.size()){

            for(int i=0;i<listatt.size();i++){
                e.setAttribute(listatt.get(i),val.get(i));
            }

        }

    }


}