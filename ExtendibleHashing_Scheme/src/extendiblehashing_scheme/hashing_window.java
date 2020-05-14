package extendiblehashing_scheme;
import java.io.*; 
import java.util.*; 
import javax.swing.*;  

public class hashing_window extends javax.swing.JFrame 
{
    
  
    private ArrayList<Integer> arr; // contains keys
    static int keys[]=new int[20];
    int n, index, flag=0,capacity;
    int mod,local_d,global_d;
    ArrayList<Directory> d =new ArrayList<>();
    JFrame error_msg = new JFrame();
    
    public hashing_window(ArrayList<Integer> keylist, int bfr, int mod, int gd, int ld ) 
    {
        
        initComponents();
        text_keys.setEnabled(false);
        String value_put = "";
        for(int k=0;k<keylist.size();k++)
                value_put += (Integer.toString(keylist.get(k)) + " ");
        text_keys.setText(value_put);
        arr = new ArrayList<>();
        arr = keylist; 
        Search_btn.setEnabled(false);
        
        n = arr.size();
        capacity = bfr;
        this.mod = mod;
        local_d = ld;
        out_print.setEnabled(false);
        global_d = gd;
       
        for(int i=0;i<n;i++){
            keys[i]=arr.get(i);
        }
        
        
        //Creating Directory
        
        double num_directory = Math.pow(2,global_d); //Number of Directory
        for(int i=0;i < num_directory;i++)
        {
            Block b=new Block();
            String req_format="%" + global_d + "s";
            String binary_rep = Integer.toBinaryString( i );
            String put = (String.format( req_format, binary_rep ).replace( " ", "0" ));
            d.add(new Directory( put , b));
            b.block = local_d; //initially dir
        }
        
        //Creating Hashing
        createHash(arr,d); // For inserting keys in the blocks of directory
        
        //Dislpay the table before overflow
        display(d);
        
    }

    
    public class Directory
    {
        String data;
        Block b;
        Directory(String data, Block b)
        {
            this.data=data;
            this.b=b;
        }
    }
   
    public class Block
    {
        ArrayList<Integer> keys;
        int block;
        Block() 
        {
            this.keys=new ArrayList<>();
        }
    }

        
    public void display(ArrayList<Directory> d)
    {
        out_print.append("The Current Global Depth is : " + global_d + '\n' + '\n');
        for(int j=0;j < d.size();j++)
        {
            Directory x = d.get(j);
            out_print.append("Directory with " + x.data + " Has Local Depth : " + x.b.block + ":" + '\n' );
            out_print.append("       Elements -> " );
            for(int i=0;i<x.b.keys.size();i++)
            {
                out_print.append(x.b.keys.get(i)+" ");
            }
            out_print.append(" " + "\n");
        }
    }
    
    public void createHash(ArrayList<Integer> arr, ArrayList<Directory> d)
    {
        // filling till owerflow is not reached
        flag=0; // OwerFlow ==> Bucket is full
        n = arr.size();
        for(int i=0;i<n;i++)
        {
            int h=arr.get(i) % mod; //Hashed Value of each Key
            String bin = ( String.format( "%4s",Integer.toBinaryString(h) ) ).replace(" ", "0"); //Min hashed should be of lenth 4
            
            for(int j=0;j<d.size();j++)
            {
                Directory x = d.get(j);
                if(bin.endsWith(x.data.substring(x.data.length() - x.b.block)))
                {
                    if(x.b.keys.size() < capacity)
                        x.b.keys.add(arr.get(i));
                    
                    else{
                        i=999; // large Value
                        index = j; //Directory whose bucket is full
                        flag = 1;
                        break;
                    }      
                }
            }
        }
    }
    
    public void directory_doubling(ArrayList<Directory> d, ArrayList<Integer> arr)
    {
        //only increasing global depth,
        //not changing local depth of index
        
        global_d++;
        String req_format="%" + global_d +"s";
        int z = 0;
        for(int i=0;i<Math.pow(2,global_d);i++)
        {
            if(i < Math.pow(2,global_d-1))
            {
                d.get(i).data = String.format(req_format, Integer.toBinaryString(i)).replace(" ", "0");
            } 
            else
            {
                Block b1=new Block();
                b1.block = d.get(z).b.block;
                z++;
                d.add(new Directory(String.format(req_format,Integer.toBinaryString(i)).replace(" ", "0"), b1)); 
            }
        }
        createHash(arr,d);
    }

    public void block_seperation(ArrayList<Directory> d, ArrayList<Integer> arr) 
    {
        for(int i=0;i < d.size();i++){ //clear all arrays of blocks of all directories
            d.get(i).b.keys.clear();
        }
        
        if(index%2!=0)
        {
            Block b1 = new Block();
            Block b2 = new Block();
            b1.block = d.get(index).b.block + 1;
            b2.block = d.get(index).b.block + 1;
            int increment = (int)Math.pow(2,d.get(index).b.block);
            d.get(index).b = b1;
            d.get(index + increment).b = b2;
            createHash(arr, d);
        }
        
        else
        {
            Block b1 = new Block();
            Block b2 = new Block();
            b1.block = d.get(index).b.block + 1;
            b2.block = d.get(index).b.block + 1;
            int increment = (int)Math.pow(2,d.get(index).b.block);
            d.get(index).b = b1;
            d.get(index + increment).b = b2;
            createHash(arr, d);
        }
    }


    
    
    
    
    
    //------------------------------------------------------------------------// 
    //------------------------------------------------------------------------// 
    //------------------------------------------------------------------------// 
    //------------------------------------------------------------------------// 
    //------------------------------------------------------------------------// 
    //------------------------------------------------------------------------//
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        out_print = new javax.swing.JTextArea();
        insert_button = new javax.swing.JButton();
        Search_btn = new javax.swing.JButton();
        text_keys = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        text_insert = new javax.swing.JTextField();
        txt_next = new javax.swing.JButton();
        text_search = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        out_print.setColumns(20);
        out_print.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        out_print.setRows(5);
        jScrollPane1.setViewportView(out_print);

        insert_button.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        insert_button.setText("Insert");
        insert_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insert_buttonActionPerformed(evt);
            }
        });

        Search_btn.setText("Search");
        Search_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Search_btnActionPerformed(evt);
            }
        });

        text_keys.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel1.setText("Keys :");

        text_insert.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N

        txt_next.setText("Next");
        txt_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nextActionPerformed(evt);
            }
        });

        text_search.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_keys, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(insert_button, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(text_search, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_next, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(txt_next, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(text_keys, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(insert_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Search_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(text_search, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(text_insert, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nextActionPerformed
        
        if(flag == 1)
        {
            out_print.setText("");
            if(d.get(index).b.block == global_d) //if local depth is already equal to global depth
            {
                out_print.append("************************" +"\n");
                out_print.append("Global Depth is increased by 1"+"\n");
                out_print.append("************************" +"\n");
                directory_doubling(d,arr);
                display(d);
            }
            
            else if(d.get(index).b.block != global_d) //if local depth of overflow bucket is less than global depth
            {
                out_print.append("************************" +"\n");
                out_print.append("Local Depth of Overflow bucket is incresed, Block Split"+"\n");
                out_print.append("************************" +"\n");
                block_seperation(d,arr);
                display(d);
            }
        }
        else
        {
            String error = "Completed Entendible Hashing .. Thank You.";
            JOptionPane.showMessageDialog(error_msg,error);
            Search_btn.setEnabled(true);
        }
    }//GEN-LAST:event_txt_nextActionPerformed

    private void insert_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insert_buttonActionPerformed
        // Code for insert button
        
        String s = text_insert.getText();
        try{
            
            int i = Integer.parseInt(s);
            if(!arr.contains(i))
                arr.add(i);
            text_insert.setText("");
            
            String output = "";
            for(int k=0;k<arr.size();k++)
                output += (Integer.toString(arr.get(k)) + " ");
            text_keys.setText(output);
            ArrayList<Integer> insert_new = new ArrayList();
            insert_new.add(i);
            out_print.setText(""); 
            createHash(insert_new, d);
            display(d);
            
            
        }catch(NumberFormatException e){
             String error = "Please Enter the correct field : Number Required";
             JOptionPane.showMessageDialog(error_msg,error);
        }
        
        
        
    }//GEN-LAST:event_insert_buttonActionPerformed

    private void Search_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Search_btnActionPerformed
            String s = text_search.getText();
        try{
            
            int i = Integer.parseInt(s);
            text_search.setText("");
            
            String req_format="%" + global_d + "s";
            String put = Integer.toBinaryString( i );
            put = (String.format( req_format, put ).replace( " ", "0" ));
            put = put.substring(put.length() - global_d);
            int flag_find = 0;
            String out_search = "";        
            for(int j=0;j<d.size();j++)
            {
                Directory x = d.get(j);
                if(put.endsWith(x.data.substring(x.data.length() - x.b.block)))
                {
                    for(int m=0;m<x.b.keys.size();m++)
                        if(x.b.keys.get(m) == i)
                        {
                            flag_find = 1;
                            out_search += x.data + " ";
                            break;
                        }
         
                }
                
            }
            
            
            if(flag_find == 1)
            {
             String error = "Yes it the key exists in Directories : " + out_search;
             JOptionPane.showMessageDialog(error_msg,error);
            }
            else
            {
             String error = "No, the key does no exist.";
             JOptionPane.showMessageDialog(error_msg,error);
            }
            
            
            
        }catch(NumberFormatException e){
             String error = "Please Enter the correct field : Number Required";
             JOptionPane.showMessageDialog(error_msg,error);
        }
        
        
    }//GEN-LAST:event_Search_btnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(hashing_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(hashing_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(hashing_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(hashing_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new hashing_window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Search_btn;
    private javax.swing.JButton insert_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea out_print;
    private javax.swing.JTextField text_insert;
    private javax.swing.JTextField text_keys;
    private javax.swing.JTextField text_search;
    private javax.swing.JButton txt_next;
    // End of variables declaration//GEN-END:variables
}
