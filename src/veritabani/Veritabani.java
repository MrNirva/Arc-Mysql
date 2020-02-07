package veritabani;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.MAX_PRIORITY;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Hrn
 */

public class Veritabani extends javax.swing.JFrame {
    
    protected final static ImageIcon icon = new ImageIcon("db1.png");
    private Baglanti baglan = null;
    private DefaultListModel model2 = new DefaultListModel();
    private DefaultListModel model3 = new DefaultListModel();
    private Color bRenk = null;
    private File save = null;
    private FileReader fr = null;
    private FileWriter fw = null;
    private Scanner scanner = null;
    public static String ara_yol = System.getProperty("user.home") + "\\Documents\\Arcula\\ArcMysql\\Save\\";
    private final String kayit = ara_yol+"Save.arc";
    private Date simdikiZaman = null;
    private DateFormat df = null;
    private final float surumNo = 1.0f;
    private final int makBeklemeSuresi = 600;
    private int zamanAsimi = makBeklemeSuresi;
    private Thread t1 = null;
    private final String[] secenekler = {"Evet","Hayır"};
    private JTable tablom = null;
    private TableModel tabloModelim = null;
    
    public Veritabani() {
        
        File klasor_arcula = new File(System.getProperty("user.home")+"\\Documents\\Arcula");
        klasor_arcula.mkdir();
        
        File klasor_arcMysql = new File(System.getProperty("user.home")+"\\Documents\\Arcula\\ArcMysql");
        klasor_arcMysql.mkdir();
        
        File klasor_save = new File(System.getProperty("user.home")+"\\Documents\\Arcula\\ArcMysql\\Save");
        klasor_save.mkdir();
        
        setIconImage(icon.getImage());
        initComponents();
        tablolarimTablosu.setModel(model2);
        veritabaniTablosu.setModel(model3);
        bRenk = baglanti.getBackground();
        
        ArrayList<String> arr = kayitDosyasiKontrol();
        if(!(arr.isEmpty())){ 
            
            kayitVarEkle(arr);
            
        }
        
        defaultTablo();
        
        addWindowListener(new WindowAdapter() {
        @Override
            public void windowClosing(WindowEvent we){
            
            int secenek = JOptionPane.showOptionDialog(null,"Kapatmak İstediğinize Emin misiniz?","ARC MYSQL",-1,2,icon,secenekler,secenekler[1]);
                if(secenek==0){
                    
                System.exit(0);
                
                }
            }
        });
        
    }
    
    public void defaultTablo(){
        
        String[][] bosVeriler = new String[20][4];
        tabloOlustur(bosVeriler);
        
    }
    
    public void tabloOlustur(String[][] verilerim){
        
        ArrayList<String> kolonAdlari = null;
        
        if(baglan!=null){
            
            kolonAdlari = baglan.kIsimleri(tabloAdi.getText());
            
        }else{
            
            kolonAdlari = new ArrayList<>();
            kolonAdlari.add("id");
            kolonAdlari.add("ad");
            kolonAdlari.add("soyad");
            kolonAdlari.add("email");
            
        }
        
        
        
        
        tablom = new JTable();

        scrollpane.setViewportView(tablom);
        
        String[] kolonAd = new String[kolonAdlari.size()];
        
        for(int i=0; i<kolonAdlari.size();i++){
            
            kolonAd[i] = kolonAdlari.get(i);
            
        }
        
        tabloModelim = new DefaultTableModel(verilerim,kolonAd){
           
            @Override
            public boolean isCellEditable(int row, int column) { //Tüm tablo düzenlemelerini devre dışı bırakmak için
                return false; //override edip kendimize göre şekillendiriyoruz.
            }   
        };
        
        tablom.setModel(tabloModelim);
        
        tablom.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               
               int row = tablom.rowAtPoint(e.getPoint());
               int col = tablom.columnAtPoint(e.getPoint());
               
               row = tablom.getSelectedRow();
               col = tablom.getSelectedColumn();
               
               guncellenecekVeri.setText((String) tabloModelim.getValueAt(row, col));
                
            }
        });
    }
    
    
    public void versionKontrol(){
        
        Thread versionKontrolu = new Thread(() -> {
                new updateEkrani(surumNo).setVisible(true);
        });
        versionKontrolu.start();
        
    }
    
    
    public void zamanAsiminiYenile(){
        
        if(baglan != null){
            
           zamanAsimi = makBeklemeSuresi;
           
        }
    }
    
    public void zamanAsimi(){
        
        t1 = new Thread(() -> {
            
            geriSayim();
            
        });
        
        t1.setPriority(MAX_PRIORITY);
        t1.start();
        
    }
    
    
    public void geriSayim(){ 
        
        Timer timer1 = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                
                //System.out.println(zamanAsimi);
                zamanAsimi--;
                
                if(zamanAsimi == 0 || baglan == null){
                    
                    if(baglan != null){
                        baglan = null;
                    }
                    parola.setText("");
                    baglanti.setBackground(bRenk);
                    zamanAsimi = makBeklemeSuresi;
                    this.cancel();
                    timer1.cancel();
                    t1.interrupt();
                    t1 = null;
                    
                    if(zamanAsimi == 0){
                        
                        JOptionPane.showMessageDialog(rootPane, "Zaman Aşımı Süresi Boyunca Herhangi Bir İşlem Yapmadınız"
                                + "\nBağlantınız Sonlandırıldı Parola Alanı Sıfırlandı.", "Zaman Aşımı", 1, icon);
                        
                    }
                    
                }
            }
        };
        timer1.schedule(task,0, 1000);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        icPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        sunucu = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        kullaniciAdi = new javax.swing.JTextField();
        host = new javax.swing.JTextField();
        kapi = new javax.swing.JTextField();
        veritabaniAdi = new javax.swing.JTextField();
        parola = new javax.swing.JPasswordField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        baglanti = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablolarimTablosu = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        veritabaniTablosu = new javax.swing.JList<>();
        tablolariListele = new javax.swing.JButton();
        veritabanlariniListele = new javax.swing.JButton();
        veritabaniOlustur = new javax.swing.JButton();
        veritabaniSil = new javax.swing.JButton();
        tabloOlustur = new javax.swing.JButton();
        tabloAdlandir = new javax.swing.JButton();
        tabloSil = new javax.swing.JButton();
        tabloAdi = new javax.swing.JTextField();
        verileriListele = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        veriEkle = new javax.swing.JButton();
        veriSil = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        sonGiris = new javax.swing.JLabel();
        veriPanelim = new javax.swing.JPanel();
        scrollpane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        guncelle = new javax.swing.JButton();
        guncellenecekVeri = new javax.swing.JTextField();
        aramaYap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ustMenu = new javax.swing.JMenuBar();
        menu1 = new javax.swing.JMenu();
        verileriDisaAktarMenu = new javax.swing.JMenuItem();
        cikisMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        sifirlamaMenu = new javax.swing.JMenuItem();
        verileriSilMenu = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        baglanMenu = new javax.swing.JMenuItem();
        baglantiyiKesMenu = new javax.swing.JMenuItem();
        surum = new javax.swing.JMenu();
        guncellemeMenu = new javax.swing.JMenuItem();
        destekMenu = new javax.swing.JMenuItem();
        hakkındaMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Arc Mysql");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);
        setResizable(false);

        icPanel.setBackground(new java.awt.Color(204, 255, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        sunucu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sunucu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sunucu.setText("Sunucu/Host Adı");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Kullanıcı Adı");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Parola");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Kapı");
        jLabel4.setToolTipText("");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Veritabanı Adı");

        kullaniciAdi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        kullaniciAdi.setText("root");
        kullaniciAdi.setToolTipText("Kullanıcı Adı");

        host.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        host.setText("localhost");
        host.setToolTipText("Sunucu/Host Adı");

        kapi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        kapi.setText("3306");
        kapi.setToolTipText("Kapı");

        veritabaniAdi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        veritabaniAdi.setToolTipText("Veritabanı Adı");

        parola.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        parola.setToolTipText("Parola");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        baglanti.setBackground(java.awt.Color.lightGray);
        baglanti.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        baglanti.setForeground(new java.awt.Color(255, 255, 255));
        baglanti.setText("Bağlan");
        baglanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baglantiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sunucu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kullaniciAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parola, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kapi, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(veritabaniAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(baglanti, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(kapi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(parola, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(1, 1, 1)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(veritabaniAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sunucu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(kullaniciAdi)
                                    .addComponent(host, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(baglanti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tablolarimTablosu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tablolarimTablosu.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                tablolarimTablosuValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(tablolarimTablosu);

        veritabaniTablosu.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        veritabaniTablosu.setToolTipText("");
        veritabaniTablosu.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                veritabaniTablosuValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(veritabaniTablosu);

        tablolariListele.setBackground(new java.awt.Color(0, 153, 204));
        tablolariListele.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tablolariListele.setForeground(new java.awt.Color(255, 255, 255));
        tablolariListele.setText("Tabloları Listele");
        tablolariListele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablolariListeleActionPerformed(evt);
            }
        });

        veritabanlariniListele.setBackground(new java.awt.Color(0, 51, 102));
        veritabanlariniListele.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        veritabanlariniListele.setForeground(new java.awt.Color(255, 255, 255));
        veritabanlariniListele.setText("Veritabanlarını Listele");
        veritabanlariniListele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veritabanlariniListeleActionPerformed(evt);
            }
        });

        veritabaniOlustur.setBackground(new java.awt.Color(0, 153, 0));
        veritabaniOlustur.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        veritabaniOlustur.setForeground(new java.awt.Color(255, 255, 255));
        veritabaniOlustur.setText("Veritabanı Oluştur");
        veritabaniOlustur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veritabaniOlusturActionPerformed(evt);
            }
        });

        veritabaniSil.setBackground(new java.awt.Color(255, 51, 51));
        veritabaniSil.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        veritabaniSil.setForeground(new java.awt.Color(255, 255, 255));
        veritabaniSil.setText("Veritabanını Sil");
        veritabaniSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veritabaniSilActionPerformed(evt);
            }
        });

        tabloOlustur.setBackground(new java.awt.Color(0, 153, 0));
        tabloOlustur.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabloOlustur.setForeground(new java.awt.Color(255, 255, 255));
        tabloOlustur.setText("Tablo Oluştur");
        tabloOlustur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabloOlusturActionPerformed(evt);
            }
        });

        tabloAdlandir.setBackground(new java.awt.Color(255, 153, 0));
        tabloAdlandir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabloAdlandir.setForeground(new java.awt.Color(255, 255, 255));
        tabloAdlandir.setText("Tabloyu Adlandır");
        tabloAdlandir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabloAdlandirActionPerformed(evt);
            }
        });

        tabloSil.setBackground(new java.awt.Color(255, 51, 51));
        tabloSil.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tabloSil.setForeground(new java.awt.Color(255, 255, 255));
        tabloSil.setText("Tabloyu Sil");
        tabloSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabloSilActionPerformed(evt);
            }
        });

        tabloAdi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tabloAdi.setToolTipText("Tablo Adı");
        tabloAdi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabloAdiKeyPressed(evt);
            }
        });

        verileriListele.setBackground(new java.awt.Color(153, 102, 255));
        verileriListele.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        verileriListele.setForeground(new java.awt.Color(255, 255, 255));
        verileriListele.setText("Verileri Listele");
        verileriListele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verileriListeleActionPerformed(evt);
            }
        });

        veriEkle.setBackground(new java.awt.Color(0, 153, 0));
        veriEkle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        veriEkle.setForeground(new java.awt.Color(255, 255, 255));
        veriEkle.setText("Veri Ekle");
        veriEkle.setToolTipText("");
        veriEkle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veriEkleActionPerformed(evt);
            }
        });

        veriSil.setBackground(new java.awt.Color(255, 51, 51));
        veriSil.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        veriSil.setForeground(new java.awt.Color(255, 255, 255));
        veriSil.setText("Sil");
        veriSil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                veriSilActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 51, 51));
        jLabel1.setText("Son Bağlantı Tarihiniz : ");

        veriPanelim.setBackground(new java.awt.Color(204, 255, 204));

        scrollpane.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout veriPanelimLayout = new javax.swing.GroupLayout(veriPanelim);
        veriPanelim.setLayout(veriPanelimLayout);
        veriPanelimLayout.setHorizontalGroup(
            veriPanelimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(veriPanelimLayout.createSequentialGroup()
                .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
        );
        veriPanelimLayout.setVerticalGroup(
            veriPanelimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));

        guncelle.setBackground(new java.awt.Color(255, 153, 0));
        guncelle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        guncelle.setForeground(new java.awt.Color(255, 255, 255));
        guncelle.setText("Güncelle");
        guncelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncelleActionPerformed(evt);
            }
        });

        guncellenecekVeri.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        guncellenecekVeri.setToolTipText("Güncellencek Veriler");
        guncellenecekVeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                guncellenecekVeriKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(guncellenecekVeri)
                    .addComponent(guncelle, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(guncellenecekVeri, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guncelle, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        );

        aramaYap.setToolTipText("Dinamik Arama Çubuğu");
        aramaYap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                aramaYapKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Arama :");

        javax.swing.GroupLayout icPanelLayout = new javax.swing.GroupLayout(icPanel);
        icPanel.setLayout(icPanelLayout);
        icPanelLayout.setHorizontalGroup(
            icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, icPanelLayout.createSequentialGroup()
                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, icPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(veritabaniSil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(veritabanlariniListele, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(veritabaniOlustur, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tabloSil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(tablolariListele, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tabloOlustur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tabloAdlandir, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(icPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(aramaYap, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(veriPanelim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(icPanelLayout.createSequentialGroup()
                                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(verileriListele, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(veriSil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(veriEkle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tabloAdi))
                                .addGap(8, 8, 8)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, icPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sonGiris, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, icPanelLayout.createSequentialGroup()
                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        icPanelLayout.setVerticalGroup(
            icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(icPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aramaYap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(8, 8, 8)
                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(veriPanelim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(icPanelLayout.createSequentialGroup()
                        .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, icPanelLayout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addComponent(tablolariListele))
                            .addComponent(veritabanlariniListele)
                            .addComponent(tabloAdi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tabloOlustur, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(veritabaniOlustur)
                            .addComponent(verileriListele))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, icPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(tabloAdlandir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(veritabaniSil)
                            .addGroup(icPanelLayout.createSequentialGroup()
                                .addComponent(veriEkle, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                                .addGap(1, 1, 1))))
                    .addGroup(icPanelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabloSil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(veriSil))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(icPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sonGiris, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menu1.setText("Dosya");

        verileriDisaAktarMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        verileriDisaAktarMenu.setText("Verileri Dışa Aktar");
        verileriDisaAktarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verileriDisaAktarMenuActionPerformed(evt);
            }
        });
        menu1.add(verileriDisaAktarMenu);

        cikisMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        cikisMenu.setText("Çıkış");
        cikisMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cikisMenuActionPerformed(evt);
            }
        });
        menu1.add(cikisMenu);

        ustMenu.add(menu1);

        jMenu2.setText("Düzenle");

        sifirlamaMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        sifirlamaMenu.setText("Alanları Varsayılana Sıfırla");
        sifirlamaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sifirlamaMenuActionPerformed(evt);
            }
        });
        jMenu2.add(sifirlamaMenu);

        verileriSilMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        verileriSilMenu.setText("Kişisel Verileri Sil");
        verileriSilMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verileriSilMenuActionPerformed(evt);
            }
        });
        jMenu2.add(verileriSilMenu);

        ustMenu.add(jMenu2);

        jMenu1.setText("Sunucu");

        baglanMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        baglanMenu.setText("Bağlan");
        baglanMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baglanMenuActionPerformed(evt);
            }
        });
        jMenu1.add(baglanMenu);

        baglantiyiKesMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        baglantiyiKesMenu.setText("Bağlantıyı Kes");
        baglantiyiKesMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baglantiyiKesMenuActionPerformed(evt);
            }
        });
        jMenu1.add(baglantiyiKesMenu);

        ustMenu.add(jMenu1);

        surum.setText("Yardım");

        guncellemeMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        guncellemeMenu.setText("Güncellemeleri Kontrol Et");
        guncellemeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guncellemeMenuActionPerformed(evt);
            }
        });
        surum.add(guncellemeMenu);

        destekMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        destekMenu.setText("Destek Notları");
        destekMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destekMenuActionPerformed(evt);
            }
        });
        surum.add(destekMenu);

        hakkındaMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        hakkındaMenu.setText("Hakkında");
        hakkındaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hakkındaMenuActionPerformed(evt);
            }
        });
        surum.add(hakkındaMenu);

        ustMenu.add(surum);

        setJMenuBar(ustMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(icPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(icPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    public ArrayList<String> kayitDosyasiKontrol(){
        
        save = new File(kayit);
        ArrayList<String> arr = new ArrayList<>();
        
        if(save.exists()){
            
            System.out.println("Dosya Bulundu");
            
            try {
                fr = new FileReader(kayit);
                scanner = new Scanner(fr);
                
                while(scanner.hasNext()){
                    
                    arr.add(scanner.next());
                    
                }
                
            } catch (FileNotFoundException ex) {
                System.out.println("Hata Oluştu");
            }
            finally{
                if(fr != null){
                    try {
                        fr.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Veritabani.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    if(scanner != null){
                        scanner.close();
                    }
                }
            }
            
            return arr;
            
        }else{
            
            System.err.println("Dosya Bulunamadı");
            versionKontrol();
            Thread destekGoruntule = new Thread(() -> {
                new Destek().setVisible(true);
            });
            destekGoruntule.start();
            
            try {
                save.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Veritabani.class.getName()).log(Level.SEVERE, null, ex);
            }
            return arr;
        }
        
    }
    
    public void kayitEkle(){
        
        try {
            simdikiZaman = new Date();
            df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            fw = new FileWriter(kayit);
            fw.write(df.format(simdikiZaman)+"\n");
            fw.write(host.getText()+"\n");
            fw.write(kullaniciAdi.getText()+"\n");
            fw.write(kapi.getText()+"\n");
            fw.write(veritabaniAdi.getText());
            
        } catch (IOException ex) {
            Logger.getLogger(Veritabani.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
            if(fw != null){
                
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Veritabani.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        } 
    }
    
    public void kayitVarEkle(ArrayList<String> arr){
        
        try {
        sonGiris.setText(arr.get(0)+" "+arr.get(1));    
        host.setText(arr.get(2));
        kullaniciAdi.setText(arr.get(3));
        kapi.setText(arr.get(4));
        veritabaniAdi.setText(arr.get(5));
        
        }catch (Exception e) {
            
            System.out.println("Eksik Eleman Hata Yakalandı!");
            
            host.setText("localhost");
            kullaniciAdi.setText("root");
            kapi.setText("3306");
            
        }
        
        System.out.println("Save Eklendi Başarılı!");
        
    }
    
    private void baglantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baglantiActionPerformed

        if(!(baglanti.getBackground() == Color.green)){
        
        try {
            defaultTablo();
            model2.clear();
            model3.clear();
            
            tabloAdi.setText("");
            guncellenecekVeri.setText("");
            
            
            char[] chr = parola.getPassword();
            String sifre = "";
            for(int i = 0; i < chr.length; i++){
                sifre += chr[i];
            }
            baglan = new Baglanti(kullaniciAdi.getText(), sifre, veritabaniAdi.getText(), host.getText(), Integer.valueOf(kapi.getText()));
            boolean bDonus = baglan.baglan(true);
            setColor(bDonus);
            if(bDonus==true){
            simdikiZaman = new Date();
            df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sonGiris.setText(df.format(simdikiZaman)+"\n");
            kayitEkle();
            zamanAsimi();
            guncelle.setEnabled(true);
            veriSil.setEnabled(true);
            guncelle.setToolTipText("");
            veriSil.setToolTipText("");
            
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Başarısız!");
        }
        }else{
            baglan = null;
            baglanti.setBackground(bRenk);
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Kesildi...");
        }
        
    }//GEN-LAST:event_baglantiActionPerformed
 
    private void verileriListeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verileriListeleActionPerformed

        if(baglan!=null){
        zamanAsiminiYenile();
        if(!(tabloAdi.getText().equals(""))){
        try {

            guncellenecekVeri.setText("");
            
            String[][] donus = baglan.veriCek(tabloAdi.getText());
            tabloOlustur(donus);
            
        } catch (Exception e) {
            
        }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Tablo Seçiniz veya Tablo Adı Kısmına Manuel Giriniz!","Hata!",2);
        }
        }else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    } 
    }//GEN-LAST:event_verileriListeleActionPerformed

    private void tablolariListeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablolariListeleActionPerformed

        if(baglan!=null){
        zamanAsiminiYenile();
        if(veritabaniTablosu.getSelectedIndex()!=-1){        
        model2.clear();
        try{
        ArrayList<String> arr2 = baglan.tabloIsimleri();
        for(int i = 0; i < arr2.size(); i++){
            
            model2.addElement(arr2.get(i)+"\n");
            
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Tablo Bulunamadı veya Bağlantı Yapılamadı");
        }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Veritabanı Seçiniz!","Hata!",2);
        }
        }
        else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
    }//GEN-LAST:event_tablolariListeleActionPerformed

    private void veriSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veriSilActionPerformed

    if(baglan!=null){    
    
    zamanAsiminiYenile();   
    
    if(tablom.getSelectedRow() != -1){
        
        int secenek = JOptionPane.showOptionDialog(rootPane,"Dikkat Silme İşlemi Aynı Özellikleri Taşıyan Başka Verileride Silebilir!", "Kontrol", 0, 2, icon, secenekler, secenekler[1]);
        
        try {
            
        if(secenek==0){
            
        ArrayList<String> kolonAdlari = baglan.kIsimleri(tabloAdi.getText());
        
        ArrayList<String> veriler = new ArrayList<>();
        for(int i=0; i<kolonAdlari.size();i++){
            
            veriler.add((String) tabloModelim.getValueAt(tablom.getSelectedRow(), i));

        }
        
        String[][] donus = baglan.veriSil(kolonAdlari,veriler,tabloAdi.getText());
        
        tabloOlustur(donus);
            
        }
        
            
        }catch (Exception e) {
            
            JOptionPane.showMessageDialog(rootPane, "Hata Veri Silme Başarısız...");
        
          }
        }else{
        
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Silinecek Veriyi Seçiniz!","Hata!",2);
        
        }
        }else{
        
           JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
        }
    }//GEN-LAST:event_veriSilActionPerformed

    private void cikisMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cikisMenuActionPerformed
            
        int secenek = JOptionPane.showOptionDialog(null,"Kapatmak İstediğinize Eminmisiniz?","ARC MYSQL",-1,2,icon,secenekler,secenekler[1]);

        if(secenek==0){

            System.exit(0);

        } 
    }//GEN-LAST:event_cikisMenuActionPerformed

    private void guncelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncelleActionPerformed

    if(baglan!=null){

        zamanAsiminiYenile();
        
        if(tablom.getSelectedRow() != -1){
            
        try{
        
        String eskiDeger = (String) tabloModelim.getValueAt(tablom.getSelectedRow(), tablom.getSelectedColumn());
        String kolonAdi = tabloModelim.getColumnName(tablom.getSelectedColumn());
        
        String[][] donus = baglan.veriGuncelle(tabloAdi.getText(),kolonAdi,guncellenecekVeri.getText(),eskiDeger);
        
        tabloOlustur(donus);
            
        }catch(Exception e){
            
        }
        
        }else{
           JOptionPane.showMessageDialog(rootPane, "Öncelikle Güncellenecek Veriyi Seçin","Hata!",2); 
        }
        
     }
    else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2); 
        
    }
    
    }//GEN-LAST:event_guncelleActionPerformed

    private void tablolarimTablosuValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_tablolarimTablosuValueChanged

       zamanAsiminiYenile();
       String _tabloAdi = "";
        try {
            _tabloAdi = tablolarimTablosu.getSelectedValue();
            _tabloAdi = _tabloAdi.trim();
            tabloAdi.setText(_tabloAdi);
            
        } catch (Exception e) {
            
            tabloAdi.setText(_tabloAdi);
            
        }
       
    }//GEN-LAST:event_tablolarimTablosuValueChanged

    private void veriEkleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veriEkleActionPerformed
    
    if(baglan!=null){
    zamanAsiminiYenile();    
    ArrayList<String> arr = new ArrayList<>();
    arr = baglan.tabloIsimleri();
    
    boolean karsilastir = arr.contains(tabloAdi.getText());
    if(karsilastir){
        
    String eklenecekVeri = JOptionPane.showInputDialog(rootPane, "Eklenecek Veriyi Giriniz"
            + "\nVeriler Arasına Virgül Koyunuz. örn Arcula,Tech,arculatech", "Veri Girişi", 1);
    while(eklenecekVeri.equals("")){
        JOptionPane.showMessageDialog(rootPane, "Eklenecek Veri Boş Olamaz");
        eklenecekVeri = JOptionPane.showInputDialog(rootPane, "Eklenecek Veriyi Giriniz"
                + "\nBoşluk Bırakmadan, Veriler Arasına Virgül Koyunuz.", "Veri Girişi", 1);
    }
        ArrayList<String> gonderilecekVeri = new ArrayList<>();
        ArrayList<String> kolonAdlari = baglan.kIsimleri(tabloAdi.getText());
        String veri = eklenecekVeri;
        char[] chr = veri.toCharArray();
        String eklenecek = "";
        int veriSayisi = 1;
        for(int i=0; i<veri.length(); i++){

            if(chr[i] != ','){
                eklenecek += chr[i];
            }else{
                gonderilecekVeri.add(eklenecek);
                eklenecek = "";
                veriSayisi++;
            }
            
        }
        if(veriSayisi == kolonAdlari.size()){
        
        
        gonderilecekVeri.add(eklenecek);
        
        Integer kolonSayisi = kolonAdlari.size();
        
        String[][] donus = baglan.veriEkle(kolonAdlari, gonderilecekVeri, tabloAdi.getText(), kolonSayisi);
        
        try {
            guncellenecekVeri.setText("");
            
            tabloOlustur(donus);
            
        } catch (Exception e) {
            
        }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Hata Girdiğiniz Veri Sayısı Kolon Sayısı İle Uyuşmuyor"
                    + "\nGirdiğiniz Veri Sayısı : "+veriSayisi+" | Kolon Sayınız : "+kolonAdlari.size()+" ","Hata!",2);
        }
    }else if(karsilastir==false){
        JOptionPane.showMessageDialog(rootPane, "Geçerli Bir Tablo Seçiniz","Hata!",2);
    }
    }
    else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
    }//GEN-LAST:event_veriEkleActionPerformed

    private void baglanMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baglanMenuActionPerformed

        if(!(baglanti.getBackground() == Color.green)){
        
        try {
            defaultTablo();
            model2.clear();
            model3.clear();
            
            tabloAdi.setText("");
            guncellenecekVeri.setText("");
            
            char[] chr = parola.getPassword();
            String sifre = "";
            for(int i = 0; i < chr.length; i++){
                sifre += chr[i];
            }
            baglan = new Baglanti(kullaniciAdi.getText(), sifre, veritabaniAdi.getText(), host.getText(), Integer.valueOf(kapi.getText()));
            boolean bDonus = baglan.baglan(true);
            setColor(bDonus);
            if(bDonus==true){
            simdikiZaman = new Date();
            df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            sonGiris.setText(df.format(simdikiZaman)+"\n");
            kayitEkle();
            zamanAsimi();
            guncelle.setEnabled(true);
            veriSil.setEnabled(true);
            guncelle.setToolTipText("");
            veriSil.setToolTipText("");
            
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Başarısız!");
        }
        }
        else{
            baglan = null;
            baglanti.setBackground(bRenk);
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Kesildi...");
        }
    }//GEN-LAST:event_baglanMenuActionPerformed

    private void hakkındaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hakkındaMenuActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Arcula Tech - 2019 Arc Mysql -- Sürüm 1.0.0\n* 25.06.2019 İlk Sürüm Yayınlandı"
                + "\n* arculatech@gmail.com"
                + "\n* https://arculatech.blogspot.com", "Mysql Bağlan Hakkında", 1);
    }//GEN-LAST:event_hakkındaMenuActionPerformed

    private void destekMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destekMenuActionPerformed
        
        new Destek().setVisible(true);
        
        
    }//GEN-LAST:event_destekMenuActionPerformed

    private void baglantiyiKesMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baglantiyiKesMenuActionPerformed
   
        try {
            
            if(baglanti.getBackground() == Color.GREEN){
             
            baglan = null;
            baglanti.setBackground(bRenk);
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Kesildi...");
            
            }
            else{
                
                JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Bağlantı Sağlayın");
                
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Kesilemedi!");
        }
        
    }//GEN-LAST:event_baglantiyiKesMenuActionPerformed

   
    private void sifirlamaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sifirlamaMenuActionPerformed
        
        try {
            
            model2.clear();
            model3.clear();
            defaultTablo();
            
            guncelle.setEnabled(true);
            veriSil.setEnabled(true);
            guncelle.setToolTipText("");
            veriSil.setToolTipText("");
            
            sonGiris.setText("");
            tabloAdi.setText("");
            guncellenecekVeri.setText("");
            host.setText("localhost");
            kullaniciAdi.setText("root");
            parola.setText("");
            kapi.setText("3306");
            baglan = null;
            baglanti.setBackground(bRenk);
            
            Thread.sleep(200);
            defaultTablo();
            
        }catch(Exception e){
             
            
        }
        
        
    }//GEN-LAST:event_sifirlamaMenuActionPerformed

    private void verileriDisaAktarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verileriDisaAktarMenuActionPerformed
    
    
    JFileChooser aktarma = new JFileChooser();
    aktarma.setDialogTitle("Kayıt Dosyası Yolu ve İsmi Belirleyin");
    
    int secim = aktarma.showSaveDialog(null);
    
    if(secim == aktarma.APPROVE_OPTION){
        
        try {
            
            save = aktarma.getSelectedFile();
            
            save.createNewFile();
                
                fw = new FileWriter(save);
                
                simdikiZaman = new Date();
                df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                
                fw.write("Dışa Aktarım Tarihi : "+df.format(simdikiZaman)+"\n");
                
                if(!sonGiris.getText().equals("")){
                    fw.write("\n");
                    fw.write("Son Başarılı Bağlantı Tarihiniz : "+sonGiris.getText()+"\n");
                    
                }
                
                fw.write("Sunucu : "+host.getText()+"\n");
                fw.write("Kullanıcı Adı : "+kullaniciAdi.getText()+"\n");
                
                char[] chr = parola.getPassword();
                String sifre = "";
                for(int i = 0; i < chr.length; i++){
                    sifre += chr[i];
                }
                
                fw.write("Parola : "+sifre+"\n");
                fw.write("Kapı : "+kapi.getText()+"\n");
                fw.write("Veritabanı : "+veritabaniAdi.getText()+"\n");
                fw.write("\n");
                if(!(model3.isEmpty())){
                    fw.write("**** Veritabanlari ****\n");
                    fw.write("\n");
                    for(int i = 0; i<model3.size(); i++){
                        
                      fw.write((String)model3.getElementAt(i));
                      
                    } 
                }
                fw.write("\n");
                if(!(model2.isEmpty())){
                    fw.write("---- Tablolar ----\n");
                    fw.write("\n");
                    for(int i = 0; i<model2.size(); i++){
                        
                        fw.write((String)model2.getElementAt(i));
                        
                    }
                }
                    
                    
                    if(!tabloAdi.getText().isEmpty() && baglan!=null){
                        
                        fw.write("\n");
                        fw.write("++++ Son Seçili Tablodaki Veriler ++++\n");
                        fw.write("\n");
                        
                        ArrayList<String> kolonAdlari = baglan.kIsimleri(tabloAdi.getText());
                        String birlestir = "";
                        for(int i=0; i<kolonAdlari.size();i++){
                            
                            birlestir += kolonAdlari.get(i)+"   ";
                            
                        }
                        
                        fw.write(birlestir);
                        fw.write("\n");
                        
                        
                        ArrayList<String> donenVeriler = baglan.veriDondur(tabloAdi.getText());
                        
                        for(String veri : donenVeriler){
                            
                            fw.write(veri+"\n");
                            
                        } 
                    }
                
               
            JOptionPane.showMessageDialog(rootPane, "Dışa Aktarım Başarılı...");
                
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "Dışa Aktarım Başarısız!");
        }
        finally{
            
            if(fw != null){
                
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Veritabani.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }
        
    }
    
    
  

        
    }//GEN-LAST:event_verileriDisaAktarMenuActionPerformed

    private void veritabaniTablosuValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_veritabaniTablosuValueChanged

        zamanAsiminiYenile();
        String tabloAdi = "";
        try {
            tabloAdi = veritabaniTablosu.getSelectedValue();
            tabloAdi = tabloAdi.trim();
            veritabaniAdi.setText(tabloAdi);
            baglan.setDb_ismi(tabloAdi);
            baglan.baglan(false);
            
        } catch (Exception e) {
            
            veritabaniAdi.setText(tabloAdi);
            
        }
        
    }//GEN-LAST:event_veritabaniTablosuValueChanged

    private void veritabanlariniListeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veritabanlariniListeleActionPerformed

        if(baglan!=null){
        zamanAsiminiYenile();
        model3.clear();
        try{
        
        ArrayList<String> arr2 = baglan.veritabaniIsimleri();
        for(int i = 0; i < arr2.size(); i++){
            
            model3.addElement(arr2.get(i)+"\n");
            
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Veritabani Bulunamadı veya Bağlantı Yapılamadı","Hata!",2);
        }
        }
        else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
        
    }//GEN-LAST:event_veritabanlariniListeleActionPerformed

    private void verileriSilMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verileriSilMenuActionPerformed
        
       
        save = new File(kayit);
        if(save.delete()){
            
            JOptionPane.showMessageDialog(rootPane, "Başarıyla Silindi...");
            
        }else{
            
            JOptionPane.showMessageDialog(rootPane, "Kayit Dosyasi Bulunamadi...");
            
        }
        
        kayitDosyasiKontrol();
        
    }//GEN-LAST:event_verileriSilMenuActionPerformed

    private void tabloSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabloSilActionPerformed

        if(baglan!=null){
        zamanAsiminiYenile();
        if(tablolarimTablosu.getSelectedIndex()!=-1){
            
        try{
        
        int secenek = JOptionPane.showOptionDialog(rootPane, " "+tablolarimTablosu.getSelectedValue()+"Seçili Tablo Silinsin mi? Geri Getirmek Mümkün Değildir.", "Kontrol", 1, 0, icon, secenekler, secenekler[1]);
        
        if(secenek==0){
            
        ArrayList<String> arr2 = baglan.tabloSil(tabloAdi.getText());
        model2.clear();
        
        defaultTablo();
        
        for(int i = 0; i < arr2.size(); i++){
            
            model2.addElement(arr2.get(i)+"\n");
            
        }
        }
        else{
            
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Tablo Bulunamadı veya Bağlantı Yapılamadı!");
        }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Tablo Seçiniz!","Hata!",2);
        }
        }else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
    }//GEN-LAST:event_tabloSilActionPerformed

    private void veritabaniSilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veritabaniSilActionPerformed

        if(baglan!=null){
        zamanAsiminiYenile();
        if(veritabaniTablosu.getSelectedIndex()!=-1){
        try{
        
        
        int secenek = JOptionPane.showOptionDialog(rootPane, veritabaniTablosu.getSelectedValue()+"Veritabanı Silinsin mi? Geri Getirmek Mümkün Değildir.", "Kontrol", 1, 0, icon, secenekler, secenekler[1]);
        
        if(secenek==0){

        ArrayList<String> arr2 = baglan.veritabaniSil();
        model3.clear();
        
        for(int i = 0; i < arr2.size(); i++){
            
            model3.addElement(arr2.get(i)+"\n");
            
        }
        }
        else{
            
        }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Veritabani Bulunamadı veya Bağlantı Yapılamadı!");
        }
        }
        else{
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Veritabanı Seçiniz!","Hata!",2);
        }
        }
        else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
    }//GEN-LAST:event_veritabaniSilActionPerformed

    private void veritabaniOlusturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_veritabaniOlusturActionPerformed

        if(baglan != null){
        zamanAsiminiYenile();
        try{
        ArrayList<String> veritabaniIsimleri = new ArrayList<>();
        veritabaniIsimleri = baglan.veritabaniIsimleri();
        
        String veritabaniAdi = JOptionPane.showInputDialog(rootPane, "Veritabanınız İçin Bir İsim Giriniz", "Veritabanı Oluşturma",1);

        while(veritabaniAdi.equals("") || veritabaniIsimleri.contains(veritabaniAdi)){
            if(veritabaniAdi.equals("")){
                JOptionPane.showMessageDialog(rootPane, "Hata Veritabanı Adı Boş Bırakılamaz", "Hata!", 2);
            }
            else if(veritabaniIsimleri.contains(veritabaniAdi)){
                JOptionPane.showMessageDialog(rootPane, "Hata Aynı İsimde Veritabanınız Zaten Bulunmakta", "Hata!", 2);
            }
            
            veritabaniAdi = JOptionPane.showInputDialog(rootPane, "Veritabanınız İçin Bir İsim Giriniz", "Veritabanı Oluşturma",1);
        }
        
        ArrayList<String> arr2 = baglan.veritabaniOlustur(veritabaniAdi);
        model3.clear();
        
        for(int i = 0; i < arr2.size(); i++){
            
            model3.addElement(arr2.get(i)+"\n");
            
        }
        } catch (Exception e) {
            
        }
        }
        else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
        
    }//GEN-LAST:event_veritabaniOlusturActionPerformed

    private void tabloOlusturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabloOlusturActionPerformed

    if(baglan!=null){
    zamanAsiminiYenile();    
    if(veritabaniTablosu.getSelectedIndex()!=-1){
    ArrayList<String> arr = new ArrayList<>();
        arr = baglan.tabloIsimleri();
        
        try{
        String tabloAdi = JOptionPane.showInputDialog(rootPane, "Tablonuz İçin Bir İsim Giriniz", "Tablo Oluşturma",1);
        
        while(tabloAdi.equals("")){
            JOptionPane.showMessageDialog(rootPane, "Hata Tablo Adı Boş Bırakılamaz!","Hata",2);
            tabloAdi = JOptionPane.showInputDialog(rootPane, "Tablonuz İçin Bir İsim Giriniz", "Tablo Oluşturma",1);
        }
        
        while(arr.contains(tabloAdi)){

            JOptionPane.showMessageDialog(rootPane, "Hata Aynı İsimde Bir Tablo Zaten Bulunmakta");
            
            tabloAdi = JOptionPane.showInputDialog(rootPane, "Tablonuz İçin Bir İsim Giriniz", "Tablo Oluşturma",1);
        }
        
        String veriSayisi = JOptionPane.showInputDialog(rootPane, "Tablonuzdaki Başlık Sayısı", "Tablo Oluşturma",1);
        
        while(Integer.valueOf(veriSayisi) < 1){
            
            JOptionPane.showMessageDialog(rootPane, "Hata Veri Sayısı 1 ve Üstü Olmalıdır");
            veriSayisi = JOptionPane.showInputDialog(rootPane, "Tablonuzdaki Veri Sayısı", "Tablo Oluşturma",1);
            
        }
        ArrayList<String> basliklar = new ArrayList<>();
        ArrayList<String> baslikTurleri = new ArrayList<>();
        for(int i = 0; i < Integer.valueOf(veriSayisi); i++){
            
        String veriBasliklari = JOptionPane.showInputDialog(rootPane, "Tablonuzdaki ["+(i+1)+"]. Başlığın İsmi", "Tablo Oluşturma",1);
        String baslikTuru = JOptionPane.showInputDialog(rootPane, "Tablonuzdaki ["+(i+1)+"]. Başlığın Türü (INT,TEXT,VARCHAR(Uzunluk),DATE,FLOAT)"
                + "\nYalnızca Bir Tür Giriniz", "Tablo Oluşturma",1);
        basliklar.add(veriBasliklari);
        baslikTurleri.add(baslikTuru);
                
        }
        ArrayList<String> arr2 = new ArrayList<>();
        baglan.tabloOlustur(tabloAdi,basliklar,baslikTurleri);
        
        arr2 = baglan.tabloIsimleri();
            model2.clear();
            
            for(int i=0; i<arr2.size();i++){
                model2.addElement(arr2.get(i));
            }
            
        } catch (Exception e) {
            
        }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Veritabanı Seçiniz!","Hata!",2);
        }
    }else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
    }//GEN-LAST:event_tabloOlusturActionPerformed

    private void tabloAdlandirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabloAdlandirActionPerformed

        if(baglan!=null){
        zamanAsiminiYenile();    
        if(tablolarimTablosu.getSelectedIndex()!=-1){
        try{
        ArrayList<String> arr = new ArrayList<>();
        arr = baglan.tabloIsimleri();
        String yeniTabloAdi = JOptionPane.showInputDialog(rootPane, "Yeni Tablo Adını Giriniz ","Tablo Adlandır",1);
        
        while(yeniTabloAdi.equals("")||arr.contains(yeniTabloAdi)){
            if(yeniTabloAdi.equals("")){
                JOptionPane.showMessageDialog(rootPane, "Hata Yeni Tablo Adı Boş Bırakılamaz");
            }
            else if(yeniTabloAdi.equals(tabloAdi.getText())){
                JOptionPane.showMessageDialog(rootPane, "Zaten Tablonuz Bu Ada Sahip");
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Hata Aynı İsimde Bir Tablo Zaten Bulunmakta");
            }
            
            yeniTabloAdi = JOptionPane.showInputDialog(rootPane, "Yeni Tablo Adını Giriniz ","Tablo Adlandır",1);
        }
        
            
            baglan.tabloAdlandir(tabloAdi.getText(),yeniTabloAdi);
            arr = baglan.tabloIsimleri();
            model2.clear();
            
            for(int i=0; i<arr.size();i++){
                model2.addElement(arr.get(i));
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Hata Adlandırma Başarısız !","Hata",2);
        }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Tablo Seçiniz!","Hata!",2);
        }
        }
        else{
        
        JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
    }
        
    }//GEN-LAST:event_tabloAdlandirActionPerformed

    private void guncellenecekVeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_guncellenecekVeriKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){   
            
            if(baglan!=null){
        
            zamanAsiminiYenile();
            
            try{
        
            String eskiDeger = (String) tabloModelim.getValueAt(tablom.getSelectedRow(), tablom.getSelectedColumn());
            String kolonAdi = tabloModelim.getColumnName(tablom.getSelectedColumn());
        
            String[][] donus = baglan.veriGuncelle(tabloAdi.getText(),kolonAdi,guncellenecekVeri.getText(),eskiDeger);
        
            tabloOlustur(donus);
            
            }catch(Exception e){
            
            }
            }
            else{
        
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2); 
        
            }
        }
 
    }//GEN-LAST:event_guncellenecekVeriKeyPressed

    private void tabloAdiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabloAdiKeyPressed
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           
            if(baglan!=null){
            zamanAsiminiYenile();
            if(!(tabloAdi.getText().equals(""))){
            try {

            guncellenecekVeri.setText("");
            
            ArrayList<String> kolonIsimleri = baglan.kIsimleri(tabloAdi.getText());
            
            String[][] donus = baglan.veriCek(tabloAdi.getText());
            tabloOlustur(donus);
            
            } catch (Exception e) {
            
            }
            }else{
            JOptionPane.showMessageDialog(rootPane, "Öncelikle Bir Tablo Seçiniz veya Tablo Adı Kısmına Manuel Giriniz!");
            }
            }else{
        
            JOptionPane.showMessageDialog(rootPane, "Bağlantı Kurulamadı!","Hata!",2);
        
            } 
            
        }   
    }//GEN-LAST:event_tabloAdiKeyPressed

    private void guncellemeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guncellemeMenuActionPerformed
     
        versionKontrol();
        
    }//GEN-LAST:event_guncellemeMenuActionPerformed

    
    public void dinamikArama(String ara){
        
        TableRowSorter<TableModel> trs = new TableRowSorter<TableModel>(tabloModelim);
        
        if(!ara.equals("")){
            
            guncelle.setEnabled(false);
            guncelle.setToolTipText("Arama İle Birlikte Kullanılamaz!");
            veriSil.setEnabled(false);
            veriSil.setToolTipText("Arama İle Birlikte Kullanılamaz!");
            guncellenecekVeri.setText("");
            
        }else{
            guncelle.setEnabled(true);
            veriSil.setEnabled(true);
            guncelle.setToolTipText("");
            veriSil.setToolTipText("");
        }
        
        tablom.setRowSorter(trs);
        
        trs.setRowFilter(RowFilter.regexFilter(ara));
        
    }
    
    
    private void aramaYapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_aramaYapKeyReleased
        // Arama bitene kadar bekler released
        
        if(baglan!=null){
        String ara = aramaYap.getText();
        dinamikArama(ara);
        }
    }//GEN-LAST:event_aramaYapKeyReleased

    public void setColor(boolean baglantiB){
        
        if(baglantiB){
            baglanti.setBackground(Color.GREEN);
        }else{
            baglanti.setBackground(Color.RED);
        }  
    }

    public static void main(String args[]) {

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Veritabani().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aramaYap;
    private javax.swing.JMenuItem baglanMenu;
    private javax.swing.JButton baglanti;
    private javax.swing.JMenuItem baglantiyiKesMenu;
    private javax.swing.JMenuItem cikisMenu;
    private javax.swing.JMenuItem destekMenu;
    private javax.swing.JButton guncelle;
    private javax.swing.JMenuItem guncellemeMenu;
    private javax.swing.JTextField guncellenecekVeri;
    private javax.swing.JMenuItem hakkındaMenu;
    private javax.swing.JTextField host;
    private javax.swing.JPanel icPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField kapi;
    private javax.swing.JTextField kullaniciAdi;
    private javax.swing.JMenu menu1;
    private javax.swing.JPasswordField parola;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JMenuItem sifirlamaMenu;
    private javax.swing.JLabel sonGiris;
    private javax.swing.JLabel sunucu;
    private javax.swing.JMenu surum;
    private javax.swing.JTextField tabloAdi;
    private javax.swing.JButton tabloAdlandir;
    private javax.swing.JButton tabloOlustur;
    private javax.swing.JButton tabloSil;
    private javax.swing.JButton tablolariListele;
    private javax.swing.JList<String> tablolarimTablosu;
    private javax.swing.JMenuBar ustMenu;
    private javax.swing.JButton veriEkle;
    private javax.swing.JPanel veriPanelim;
    private javax.swing.JButton veriSil;
    private javax.swing.JMenuItem verileriDisaAktarMenu;
    private javax.swing.JButton verileriListele;
    private javax.swing.JMenuItem verileriSilMenu;
    private javax.swing.JTextField veritabaniAdi;
    private javax.swing.JButton veritabaniOlustur;
    private javax.swing.JButton veritabaniSil;
    private javax.swing.JList<String> veritabaniTablosu;
    private javax.swing.JButton veritabanlariniListele;
    // End of variables declaration//GEN-END:variables

}
