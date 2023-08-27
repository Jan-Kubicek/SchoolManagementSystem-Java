package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PrintWriter;

public class MainFrame extends JFrame {
    //Komponenty
    JPanel pWest, pEast, pNorth, pSouth, pCenter;
    JLabel lbNorth,lbSouth;
    JTable table;
    JTextField tfJmeno, tfEmail, tfDatumNarozeni, tfCislo;
    JButton btnSubmit, btnOdstran, btnZobraz, btnSmazVSE, btnExportuj;
    //Konstruktor
    public MainFrame(){
        InitGUI();
        setTitle("School Management");
        setSize(640,480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    //GUI
    public void InitGUI(){
        pWest = new JPanel(new GridLayout(6,1));
            tfJmeno = new JTextField("Jmeno"); pWest.add(tfJmeno);
            tfCislo = new JTextField("Telefoní číslo"); pWest.add(tfCislo);
            tfEmail = new JTextField("Email");pWest.add(tfEmail);
            Choice pohlavi = new Choice();
                pohlavi.add("Muž");
                pohlavi.add("Žena");
            pWest.add(pohlavi);
            tfDatumNarozeni = new JTextField("DD/MM/RRRR"); pWest.add(tfDatumNarozeni);
            btnSubmit = new JButton("Potvrd"); pWest.add(btnSubmit);
            btnSubmit.addActionListener(e -> {
                boolean add = true;
                String jmeno = tfJmeno.getText();
                int cislo = Integer.parseInt(tfCislo.getText());
                String Scislo = String.valueOf(cislo);
                if(Scislo.length() != 9){
                    JOptionPane.showMessageDialog(this,"Zadali jste neplatnou velikost telefoního čísla","ERROR",JOptionPane.ERROR_MESSAGE);
                    add = false;
                }
                String email = tfEmail.getText();
                if(!email.contains("@")){
                    JOptionPane.showMessageDialog(this,"Zadali jste neplatný email","ERROR",JOptionPane.ERROR_MESSAGE);
                    add = false;
                }
                String strPohlavi = pohlavi.getSelectedItem();
                String datumNarozeni = tfDatumNarozeni.getText();
                if(datumNarozeni.length() != 10 ){
                    JOptionPane.showMessageDialog(this,"Zadali jste Datum narození mimo platné rozmezí","ERROR",JOptionPane.ERROR_MESSAGE);
                    add = false;
                }if (datumNarozeni.contains("/")){
                        int pocetLom = 0;
                        for(int i = 0; i < datumNarozeni.length(); i++){
                            char znak = datumNarozeni.charAt(i);
                            char lom = '/';
                            if(znak == lom){
                                ++pocetLom;
                            }
                        }
                        if(pocetLom == 2){
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            int actRow = table.getRowCount();
                            actRow++;
                            if(add){
                                model.addRow(new Object[]{actRow, jmeno,email,cislo,strPohlavi,datumNarozeni});
                            }
                        }
                }else{
                    JOptionPane.showMessageDialog(this,"Zadali jste ve špatném formátu datum narození","ERROR",JOptionPane.ERROR_MESSAGE);
                    add = false;
                }
            });
        add(pWest,BorderLayout.WEST);

        pNorth = new JPanel(new GridLayout(1,1));
            lbNorth = new JLabel("Školní Management System");
            pNorth.add(lbNorth);
        add(pNorth,BorderLayout.NORTH);

        pCenter = new JPanel(new GridLayout(1,1));
            DefaultTableModel model = new DefaultTableModel();
                model.addColumn("ID");
                model.addColumn("Jmeno");
                model.addColumn("Email");
                model.addColumn("Cislo");
                model.addColumn("Pohlavi");
                model.addColumn("DatunNarozeni");
            table = new JTable(model); pCenter.add(new JScrollPane(table));
        add(pCenter,BorderLayout.CENTER);

        pEast = new JPanel(new GridLayout(4,1));
            btnOdstran = new JButton("Odstraň"); pEast.add(btnOdstran);
            btnOdstran.addActionListener(e -> {
                int index = table.getSelectedRow();
                model.removeRow(index);
            });
            btnZobraz = new JButton("Zobraz");  pEast.add(btnZobraz);
            btnZobraz.addActionListener(e -> {
                int index = table.getSelectedRow();
                JOptionPane.showMessageDialog(this,"Jmeno: "+model.getValueAt(index,1)+" Email: "+model.getValueAt(index,2)+" Tel: "+ model.getValueAt(index,3)+"\nPohlaví: "+model.getValueAt(index,4)+ " Datum narození: "+model.getValueAt(index,5),"INFORMATION",JOptionPane.INFORMATION_MESSAGE);
            });
            btnSmazVSE = new JButton("Smaž Vše");   pEast.add(btnSmazVSE);
            btnSmazVSE.addActionListener(e -> {
                model.setRowCount(0);
            });
            btnExportuj = new JButton("Exportuj"); pEast.add(btnExportuj);
            btnExportuj.addActionListener(e -> {
                try{
                    PrintWriter vystup = new PrintWriter("Export.txt");
                    vystup.println("Export Systému");
                    for(int rows = 0; rows < model.getRowCount(); rows++){
                        for(int collums = 0; collums < model.getColumnCount(); collums++){
                            vystup.print(model.getValueAt(rows,collums));
                            vystup.print(" ");
                        }
                        vystup.println("");
                    }
                    vystup.close();
                }catch (Exception a){
                    a.printStackTrace();
                }
            });
        add(pEast, BorderLayout.EAST);

        pSouth = new JPanel(new GridLayout(1,1));
            lbSouth = new JLabel("Jan Kubíček @2023 JAVA"); pSouth.add(lbSouth);
        add(pSouth,BorderLayout.SOUTH);

        pack();

    }

    //Main
    static public void main(String[] args){
        new MainFrame().setVisible(true);
    }
}
