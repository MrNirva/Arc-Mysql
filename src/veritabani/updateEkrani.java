package veritabani;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Thread.MAX_PRIORITY;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static veritabani.Veritabani.icon;

/**
 *
 * @author Hrn
 */

public class updateEkrani extends javax.swing.JFrame {
    
    private int bekleme = 60;
    private final ImageIcon gBulunamadi = new ImageIcon("0a.png");
    private final ImageIcon gBulundu = new ImageIcon("1r.png");
    private final ImageIcon gBasarisiz = new ImageIcon("2c.png");
    private final ImageIcon tariyor = new ImageIcon("3tech.gif");
    
    private UpdateKontrol updateK = null;
    private boolean yeniSurumVar = false;
    private float suankiSurumNo = 1.0f;
    private float sonSurumNo = 0.0f;
    private String nelerVar = "Son Sürümde Eklenenler\n";
    private String yeniSurumAdresi = "";
    private String noktalar = "";
    private final char simge = '.';
    protected static boolean calisiyor = true;
    
    public updateEkrani(Float surumNo) {
        
        initComponents();
        setIconImage(icon.getImage());
        calisiyor = true;
        updateK = new UpdateKontrol();
        suankiSurumNo = surumNo;
        suankiS.setText(suankiS.getText()+String.valueOf(suankiSurumNo));
        kontrol.setIcon(tariyor);
        String surumYazisi = "  Şuanki Sürüm : " + surumNo + " / Son Sürüm : Kontrol Ediliyor";
        surumYazisiT.setText(surumYazisi);
        addWindowListener(new WindowAdapter() {
        @Override
            public void windowClosing(WindowEvent we){

                calisiyor = false;
                dispose();
                
            }
        });
        
        Thread t1 = new Thread(() -> {
            
            kontrol.setIcon(tariyor);
            saydir();
            
        });
        
        
        
        Thread t2 = new Thread(() -> {
            
            sonSurumNo = Float.valueOf(updateK.sonSurumuCek());
            nelerVar += updateK.yeniNelerVar();
            
            if(sonSurumNo > suankiSurumNo){
                
                yeniSurumVar = true;
                yeniSurumAdresi = updateK.yeniSurumAdresi();
                
            }
            else{
                
                yeniSurumVar = false;
            }
                
        });
        
        
        t1.setPriority(MAX_PRIORITY);
        t1.start();
        t2.start();
        
    }
    
    public void saydir(){ 
        
        Timer t1 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                
                if(!calisiyor){
                    this.cancel();
                    t1.cancel();
                }
                
                bekleme--;
                //System.out.println(bekleme);
                
                noktalar += simge;
                if(noktalar.length() > 3){
                    noktalar = "";
                }
                surumYazisiT.setText("Şuanki Sürüm : 1.0 / Son Sürüm : Kontrol Ediliyor"+noktalar);
                
                if(bekleme <= 0){
                    JOptionPane.showMessageDialog(rootPane,"Bekleme Süresi Aşıldı Bağlantı Başarısız!","Hata!",2);
                    kontrol.setIcon(gBasarisiz);
                    surumYazisiT.setBackground(Color.red);
                    surumYazisiT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    surumYazisiT.setText("Şuanki Sürüm : " + suankiSurumNo + " / Son Sürüm : Çekilemedi!");
                    eklenenler.setText(nelerVar+"Bir Şey Bulunamadı...");
                    
                    this.cancel();
                    t1.cancel();
                }
                
                
                if(sonSurumNo != 0.0f && !(nelerVar.equals("Son Sürümde Eklenenler\n"))){
                    if(yeniSurumVar){
                        
                        surumYazisiT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        kontrol.setIcon(gBulundu);
                        surumYazisiT.setBackground(Color.green);
                        surumYazisiT.setText("Yeni Sürüm -> "+sonSurumNo+" İndirmek İçin Tıklayın");
                        eklenenler.setText(nelerVar);
                        
                    }else{
                        
                        surumYazisiT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        surumYazisiT.setBackground(Color.blue);
                        kontrol.setIcon(gBulunamadi);
                        surumYazisiT.setText("Şuanki Sürüm : " + suankiSurumNo + " / Son Sürüm : "+sonSurumNo+" ");
                        eklenenler.setText("En Güncel Sürümü Kullanıyorsunuz\n"+nelerVar);
                    }
                    
                    
                    this.cancel();
                    t1.cancel();
                }
            }
        };
        t1.schedule(task,0, 1000);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        kontrol = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        eklenenler = new javax.swing.JTextArea();
        surumYazisiT = new javax.swing.JTextField();
        suankiS = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Güncelleme Kontrol");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        eklenenler.setEditable(false);
        eklenenler.setColumns(20);
        eklenenler.setFont(new java.awt.Font("Monospaced", 0, 15)); // NOI18N
        eklenenler.setRows(5);
        eklenenler.setText("Son Sürümde Eklenenler");
        jScrollPane1.setViewportView(eklenenler);

        surumYazisiT.setEditable(false);
        surumYazisiT.setBackground(new java.awt.Color(0, 102, 204));
        surumYazisiT.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        surumYazisiT.setForeground(new java.awt.Color(255, 255, 255));
        surumYazisiT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        surumYazisiT.setText("Şuanki Sürüm : 1.0 / Son Sürüm : Kontrol Ediliyor");
        surumYazisiT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                surumYazisiTMouseClicked(evt);
            }
        });

        suankiS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        suankiS.setText("Şuanki Sürüm : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(surumYazisiT, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(kontrol, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(suankiS, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(suankiS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kontrol, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(surumYazisiT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    private void surumYazisiTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_surumYazisiTMouseClicked
       
        if(yeniSurumVar){
            
            try {
            Desktop.getDesktop().browse(new URL(yeniSurumAdresi).toURI());
            } 
            catch (Exception e){
            
            }
        }
        else{
            
        }
        
    }//GEN-LAST:event_surumYazisiTMouseClicked


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new updateEkrani(1.00f).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea eklenenler;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel kontrol;
    private javax.swing.JLabel suankiS;
    private javax.swing.JTextField surumYazisiT;
    // End of variables declaration//GEN-END:variables
}
