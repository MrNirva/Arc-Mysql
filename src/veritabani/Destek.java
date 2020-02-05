package veritabani;

import static veritabani.Veritabani.icon;

/**
 *
 * @author Hrn
 */

public class Destek extends javax.swing.JFrame {

    private final String hakkindaNot = "Arcula Tech - 2019 Arc Mysql -- Sürüm 1.0.0 \n* 25.06.2019 İlk Sürüm Yayınlandı\n* İletişim: arculatech@gmail.com"
            + "\n* https://arculatech.blogspot.com"; 
    
    private final String baglantiNot = "Bilgilerinizi girdikten sonra bağlantı için Bağlan butonunu\nveya Sunucu -> Bağlan butonunu kullanabilirsiniz."
            + "\nBağlantı için sunucu/host bilginiz, kullanıcı adınız, parolanız \nve bağlantı kapısı gereklidir. \nVeritabanı Adı Alanı Boş Kalabilir."
            +"\nBağlantınız başarılıysa bağlantı butonu Yeşil, değilse Kırmızı yanar."
            + "\nBağlantınızı koparmak için Sunucu -> Bağlantıyı Kes"
            + "\nDüzenle -> Kişisel Verileri Sil veya Bağlan butonuna"
            + "\ntekrar dokunma seçenekleri ile yapabilirsiniz."
            + "\nBağlandığınızda parolanız hariç giriş bilgileriniz bilgisayarınıza kaydedilir."
            + "\nVerileriniz hiç bir şekilde tarafımızca görülmezler."
            + "\nBu nedenle sorumluluğu sizlere aittir."
            + "\nHer açılışta kayıtlı bilgileriniz varsa eğer otomatik olarak doldurulur"
            + "\nfakat parolanızı manuel olarak girmelisiniz."
            + "\nGiriş Verilerinizi silmek için \nDüzenle -> Kişisel Verileri Sile tıklayarak gerçekleştirebilirsiniz.";
    
    private final String listelemeNot = "Başarılı şekilde bağlantı yaptıktan sonra listelemeleri kullanabilirsiniz."
            + "\nVeritabanlarını Listele eğer varsa veri tabanlarınızı getirir."
            + "\nTabloları Listele eğer varsa tablolarınızı getirir."
            + "\nTabloları listelerken Veritabanı Adı alanı otomatik dolacaktır."
            + "\nVerileri listele ise eğer varsa kolonları ve verileri getirir."
            + "\nVeri Listelemenin altındaki alan otomatik olarak dolacaktır."
            + "\nVeri Listeleme Alanında Entera Basarakta Listeleyebilirsiniz."
            + "\nListeleme İşlemlerini Ekleme, Düzenleme ve Silme"
            + "\nolaylarını gerçekleştirdiğiniz zamanlarda kullanmanızı tavsiye ederiz.";
    
    private final String veritabaniOlusturmaNot = "Başarılı bir bağlantı kurduktan sonra"
            + "\nVeritabanı Oluştura basınız"
            + "\nBenzersiz bir isim giriniz ve tamama basınız."
            + "\nListeler otomatik yenilenmezse manuel yenileyiniz.";
    
    private final String tabloOlusturmaNot = "Başarılı bir bağlantı kurduktan sonra"
            + "\nListeden bir veritabanı seçiniz"
            + "\nTablo Oluştura basınız"
            + "\nBenzersiz bir isim giriniz ve tamama basınız."
            + "\nArdından Tablonuzda yer alacak başlık sayısını giriniz."
            + "\nSırasıyla başlıkları giriniz ve ardından veri türünü giriniz."
            + "\nSon olarak tamama basıp tablonuzu oluşturabilirsiniz."
            + "\nListeler otomatik yenilenmezse manuel yenileyiniz.";
    
    private final String  veriEklemeNot = "Eklenecek veriler arasına virgül (,) koymanız gerekmektedir."
            + "\nVeri eklemeden önce verileri listelerseniz kolonları görebilirsiniz."
            + "\nVeri eklerken id alanınıda elle doldurunuz \nPRIMARY desteklenmemektedir."
            + "\nVeri eklemeyi daha rahat yapabilmek için |*| butonunu tıklayınız.";
    
    private final String veriGuncellemeAdlandirmaNot = "Veri Güncellemek için."
            + "\nÖncelikle listeden güncellenecek veriyi seçiniz."
            + "\nArdından Güncelle butonunun üzerindeki boşluğa yeni değeri giriniz."
            + "\nSon olarak güncelleye basınız."
            + "\nVeri güncelleme başarılı olduktan sonra"
            + "\nliste otomatik yenilenmezse manuel yenileyiniz."
            + "\nGüncelleme Alanında Entera Basarakta Güncelleyebilirsiniz."
            + "\nAdlandırma işlemi içinse bir tablo seçin."
            + "\nTabloyu Adlandıra basınız."
            + "\nSeçilen tablo için benzersiz bir isim girin ve tamama basın.";
    
    private final String silmeNot = "Veritabanı silmek için bir veri tabanı seçip"
            + "\nVeritabanını Sile basabilirsiniz."
            + "\nTablo silmek için bir tablo seçip"
            + "\nTabloyu Sile basabilirsiniz."
            + "\nVeri Silmek içinse verinin herhangi bir özelliğini seçip"
            + "\nSile basmanız yeterlidir."
            + "\nSilme başarılı olduktan sonra"
            + "\nlisteler otomatik yenilenmezse manuel yenileyiniz.";
    
    private final String disaAktarmaNot = "Son başarılı bağlantı tarihiniz, bağlantı bilgileriniz, veritabanlarınız,"
            + "\ntablolarınız ve son seçili tablodaki verilerinizi dışarı aktarmak için kullanabilirsiniz."
            + "\nKullanmak için Dosya -> Verileri Dışa Aktara basınız"
            + "\nKonumu ve dosya adını uzantısı ile birlikte belirledikten sonra"
            + "\nSave bastığınızda verileriniz belirlediğiniz konuma kaydedilecektir."
            + "\nDışa aktardığınız verileri standart bir dosyadır şifrelenmemiştir."
            + "\nBu nedenle kaydedilen Verilerinizden siz sorumlusunuz.";
    
    private final String cikisNot = "Programdan çıkmak için kullanabilirsiniz."
            + "\nAlternatiftir.";
    
    private final String sifirlamaNot = "Programdaki tüm alanları varsayılan haline getirir"
            + "\nKayıtlı giriş bilgilerini silmez.";
    
    private final String verileriSilNot = "Kayıt dosyanızdaki verileri silmek için kullanabilirsiniz."
            + "\nVerileriniz silindikten sonra otomatik doldurma bir sonraki"
            + "\nkayda (bağlantıya) kadar devre dışı kalacaktır."
            + "\nSilinen verilerinizden sizler sorumlusunuz.";
    
    private final String baglanNot = "Bağlan butonu için alternatiftir.";
    
    private final String baglantiyiKesNot = "Bulunduğunuz bağlantıyı sonlandırmak için kullanabilirsiniz.";
    
    private final String guncellemeKontrolNot = "Programın en güncel sürümüne ulaşmak için kullanabilirsiniz."
            + "\nYeni sürüm varsa yeşil simge ve indirme bağlantısı verir."
            + "\nYeni sürüm yoksa gri simge gösterir."
            + "\nBağlantı başarısız olursa kırmızı simge gösterir."
            + "\nBağlantıdan sonra varsa sürüm notlarını gösterir."
            + "\nGüncelleme işlemlerinden sonra eski sürümü bilgisayarınızdan kaldırınız.";
    
    private final String destekNotlari = "Şuan bulunduğunuz sayfa.";
    
    private final String hakkindaNotlari = "Program ve yapımcı hakkında bilgileri içerir."
            + "\nSürüm bilgileri."
            + "\nYayın tarihi."
            + "\nİletişim adresi.";
    
    private final String kisayollarNot = "Verileri Dışa Aktarma -> Ctrl + S"
            + "\nÇıkış -> ESC"
            + "\nAlanları Varsayılana Sıfırla -> Ctrl + R"
            + "\nKişisel Verileri Sil -> Ctrl + V"
            + "\nBağlan -> Ctrl + B"
            + "\nBağlantı Kes -> Ctrl + K"
            + "\nGüncellemeleri Kontrol Et -> Ctrl + G"
            + "\nDestek Notları -> Ctrl + H"
            + "\nHakkında -> Ctrl + B";
    
    private final String zamanAsimiNot = "10 dakikalık zaman aşımı süresince herhangi bir veri"
            + "\ngönderilip alınmazsa bağlantınız sonlandırılır."
            + "\nZaman aşımı süresinden sonra Parola alanı güvenlik nedeniyle sıfırlanır.";
    
    private final String baglantiTarihiNot = "Son başarılı bağlantınızın tarih ve saat bilgisini gösterir."
            + "\nKayıt dosyanıza bu bilgiyi ekler."
            + "\nKişisel verileri veya manuel olarak kayıt dosyanızı silmediğiniz sürece"
            + "\nher girişte bu bilgiyi görebilirsiniz.";
    
    private final String aramaNot = "Dinamik Arama Çubuğu istediğiniz herhangi bir veriyi aramanıza"
            + "\nyardımcı olur.Yalnızca veriler alanında arama için kullanabilirsiniz."
            + "\nBüyük küçük harfe duyarlıdır."
            + "\nOldukça hızlı çalışır sorgularınıza anında cevap verir."
            + "\nTüm kolonlardaki verileri tarar."
            + "\nVeri Güncelleme ve Veri Silme ile birlikte kullanılamaz!"
            + "\nDinamik Arama sonrasında Güncelle ve Sil butonları devre dışı kalır."
            + "\nArama alanını boşalttığınızda tekrar kullanıma girerler.";
    
    private final String ekstraNotlar = "Bu programın tüm hakları Arcula Tech aittir."
            + "\nÜcretsiz olarak sunulmaktadır."
            + "\nKişisel verilerinizden sizler sorumlusunuz."
            + "\nİletişim için: arculatech@gmail.com";
    
    public Destek() {
        
        setIconImage(icon.getImage());
        initComponents();
        
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        basliklar = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        icerik = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Destek Notları");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ARC MYSQL DESTEK NOTLARI");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));

        jScrollPane1.setBackground(new java.awt.Color(51, 153, 255));

        basliklar.setBackground(new java.awt.Color(51, 153, 255));
        basliklar.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        basliklar.setForeground(new java.awt.Color(255, 255, 255));
        basliklar.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Hakkında", "Bağlantı", "Listeleme", "Veritabanı Oluşturma", "Tablo Oluşturma ", "Veri Ekleme", "Veri Güncelleme | Tablo Adlandırma", "Silme", "Dosya -> Verileri Dışa Aktarma", "Dosya -> Çıkış", "Düzenle -> Alanları Varsayılana Sıfırla", "Düzenle -> Kişisel Verileri Sil", "Sunucu -> Bağlan", "Sunucu -> Bağlantıyı Kes", "Yardım -> Güncellemeleri Kontrol Et", "Yardım -> Destek Notları", "Yardım -> Hakkında", "Kısayollar", "Zaman Aşımı", "Son Bağlantı Tarihi", "Dinamik Arama Çubuğu", "Ekstra Notlar" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        basliklar.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                basliklarValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(basliklar);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 102, 204));

        icerik.setEditable(false);
        icerik.setBackground(new java.awt.Color(102, 153, 255));
        icerik.setColumns(20);
        icerik.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        icerik.setForeground(new java.awt.Color(255, 255, 255));
        icerik.setRows(5);
        jScrollPane2.setViewportView(icerik);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("İletişim : arculatech@gmail.com");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void basliklarValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_basliklarValueChanged
        
        if(basliklar.getSelectedIndex()==0){
            icerik.setText(hakkindaNot);
        }
        else if(basliklar.getSelectedIndex()==1){
            icerik.setText(baglantiNot);
        }
        else if(basliklar.getSelectedIndex()==2){
            icerik.setText(listelemeNot);
        }
        else if(basliklar.getSelectedIndex()==3){
            icerik.setText(veritabaniOlusturmaNot);
        }
        else if(basliklar.getSelectedIndex()==4){
            icerik.setText(tabloOlusturmaNot);
        }
        else if(basliklar.getSelectedIndex()==5){
            icerik.setText(veriEklemeNot);
        }
        else if(basliklar.getSelectedIndex()==6){
            icerik.setText(veriGuncellemeAdlandirmaNot);
        }
        else if(basliklar.getSelectedIndex()==7){
            icerik.setText(silmeNot);
        }
        else if(basliklar.getSelectedIndex()==8){
            icerik.setText(disaAktarmaNot);
        }
        else if(basliklar.getSelectedIndex()==9){
            icerik.setText(cikisNot);
        }
        else if(basliklar.getSelectedIndex()==10){
            icerik.setText(sifirlamaNot);
        }
        else if(basliklar.getSelectedIndex()==11){
            icerik.setText(verileriSilNot);
        }
        else if(basliklar.getSelectedIndex()==12){
            icerik.setText(baglanNot);
        }
        else if(basliklar.getSelectedIndex()==13){
            icerik.setText(baglantiyiKesNot);
        }
        else if(basliklar.getSelectedIndex()==14){
            icerik.setText(guncellemeKontrolNot);
        }
        else if(basliklar.getSelectedIndex()==15){
            icerik.setText(destekNotlari);
        }
        else if(basliklar.getSelectedIndex()==16){
            icerik.setText(hakkindaNotlari);
        }
        else if(basliklar.getSelectedIndex()==17){
            icerik.setText(kisayollarNot);
        }
        else if(basliklar.getSelectedIndex()==18){
            icerik.setText(zamanAsimiNot);
        }
        else if(basliklar.getSelectedIndex()==19){
            icerik.setText(baglantiTarihiNot);
        }
        else if(basliklar.getSelectedIndex()==20){
            icerik.setText(aramaNot);
        }
        else if(basliklar.getSelectedIndex()==21){
            icerik.setText(ekstraNotlar);
        }
        
        
    }//GEN-LAST:event_basliklarValueChanged

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Destek().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> basliklar;
    private javax.swing.JTextArea icerik;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
