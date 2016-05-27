/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.propiedades;

import cl.starlabs.controladores.CiudadJpaController;
import cl.starlabs.controladores.PropiedadJpaController;
import cl.starlabs.controladores.PropietarioJpaController;
import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Propiedad;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.vista.utilidades.UtilidadBuscarPropiedad;
import cl.starlabs.vista.utilidades.UtilidadBuscarPropietario;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author cetecom
 */
public class ListarPropiedades extends javax.swing.JFrame {
    
    EntityManagerFactory emf = null;
    Propietario propietario = null;
    public ListarPropiedades() {
        initComponents();
        setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("PU");
        //rellenando tabla con valores por defecto
        this.rellenarTabla();
    }

    public ListarPropiedades(Propietario pro) {
        initComponents();
        setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("PU");
        this.propietario = pro;
        if(propietario != null) {
            txtPropietario.setText(propietario.getRut()+"-"+propietario.getDv());
            this.rellenarTablaPropietario(propietario.getRut()+"");
        }else{
            this.rellenarTabla();
        }

    }
    
    public void rellenarTabla() {
        for(Propiedad p : new PropiedadJpaController(emf).listar()) {
            DefaultTableModel modelo = (DefaultTableModel) tblPropiedades.getModel();
            Object [] fila = new Object[5];
            fila[0] = p.getNumpropiedad();
            fila[1] = p.getCalle();
            fila[2] = this.buscarCiudad(p.getCiudad());
            fila[3] = p.getNumpropietario().getApaterno()+" "+p.getNumpropietario().getAmaterno()+" "+p.getNumpropietario().getNombre();
            fila[4] = "Detalle";
            modelo.addRow(fila);
            tblPropiedades.setModel(modelo);
            tblPropiedades.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
            tblPropiedades.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));
        }
    }
    
    public void rellenarTablaPropietario(String rut) {
        if(new PropiedadJpaController(emf).listarPropiedadPorRutPropietario(rut) != null) {
            for(Propiedad p : new PropiedadJpaController(emf).listarPropiedadPorRutPropietario(rut)) {
                DefaultTableModel modelo = (DefaultTableModel) tblPropiedades.getModel();
                Object [] fila = new Object[5];
                fila[0] = p.getNumpropiedad();
                fila[1] = p.getCalle();
                fila[2] = this.buscarCiudad(p.getCiudad());
                fila[3] = p.getNumpropietario().getApaterno()+" "+p.getNumpropietario().getAmaterno()+" "+p.getNumpropietario().getNombre();
                fila[4] = "Detalle";
                modelo.addRow(fila);
                tblPropiedades.setModel(modelo);
                tblPropiedades.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
                tblPropiedades.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));
            }
        }else{
            this.tblPropiedades.removeAll();
        }
    }
    
    public void rellenarTablaPropiedad(String propiedad) {
        Propiedad p = new PropiedadJpaController(emf).buscarPropiedadPorNumero(new BigInteger(propiedad));
        if(p != null) {
            DefaultTableModel modelo = (DefaultTableModel) tblPropiedades.getModel();
            Object [] fila = new Object[5];
            fila[0] = p.getNumpropiedad();
            fila[1] = p.getCalle();
            fila[2] = this.buscarCiudad(p.getCiudad());
            fila[3] = p.getNumpropietario().getApaterno()+" "+p.getNumpropietario().getAmaterno()+" "+p.getNumpropietario().getNombre();
            fila[4] = "Detalle";
            modelo.addRow(fila);
            tblPropiedades.setModel(modelo);
            tblPropiedades.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
            tblPropiedades.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));
        }else{
            this.rellenarTabla();
        }
    }

    public String buscarCiudad(BigInteger ciudadId){
        try {
            return new CiudadJpaController(emf).findCiudad(new BigDecimal(ciudadId)).getNombre();
        } catch (Exception e) {
            return "Indefinida";
        }
    } 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPropiedades = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("StarLabs DreamHome :: Propieades en el sistema");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros"));

        jLabel2.setText("Propietario");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/iconos/find.png"))); // NOI18N
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/iconos/chart_organisation.png"))); // NOI18N
        jButton1.setText("Todo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPropietario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(264, 264, 264)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Propiedades en sistema"));

        tblPropiedades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero Propiedad", "Dirección", "Comuna", "Propietario", "Acciones"
            }
        ));
        jScrollPane1.setViewportView(tblPropiedades);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        UtilidadBuscarPropietario ubp = new UtilidadBuscarPropietario(propietario);
        ubp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        new ListarPropiedades().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListarPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListarPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListarPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListarPropiedades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListarPropiedades().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPropiedades;
    private javax.swing.JTextField txtPropietario;
    // End of variables declaration//GEN-END:variables
}

class ButtonRenderer extends JButton implements  TableCellRenderer
{

	//CONSTRUCTOR
	public ButtonRenderer() {
		//SET BUTTON PROPERTIES
		setOpaque(true);
	}
	
        @Override
	public Component getTableCellRendererComponent(JTable table, Object obj,
			boolean selected, boolean focused, int row, int col) {
		
		//SET PASSED OBJECT AS BUTTON TEXT
			setText((obj==null) ? "":obj.toString());
				
		return this;
	}
	
}

class ButtonEditor extends DefaultCellEditor
{
	protected JButton btn;
	 private String lbl;
	 private Boolean clicked;
	 
	 public ButtonEditor(JTextField txt) {
		super(txt);
		
		btn=new JButton();
		btn.setOpaque(true);
		
		//WHEN BUTTON IS CLICKED
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				fireEditingStopped();
			}
		});
	}
	 
	 //OVERRIDE A COUPLE OF METHODS
	 @Override
	public Component getTableCellEditorComponent(JTable table, Object obj,
			boolean selected, int row, int col) {

			//SET TEXT TO BUTTON,SET CLICKED TO TRUE,THEN RETURN THE BTN OBJECT
		/*lbl=(obj==null) ? "":obj.toString();*/
		 btn.setText(lbl);
		 clicked=true;
                lbl = String.valueOf(table.getModel().getValueAt(table.getSelectedRow(), 0));
		return btn;
                
	}
	 
	//IF BUTTON CELL VALUE CHNAGES,IF CLICKED THAT IS
	 @Override
	public Object getCellEditorValue() {

		 if(clicked)
			{
			//SHOW US SOME MESSAGE
                            //JOptionPane.showMessageDialog(btn, lbl+" Clicked");
                            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
                            DetallePropiedad dpp = new DetallePropiedad(new PropiedadJpaController(emf).buscarPropiedadPorNumero(new BigInteger(lbl)));
                            emf.close();
                            dpp.setVisible(true);
			}   
		//SET IT TO FALSE NOW THAT ITS CLICKED
		clicked=false;
	  return new String("Detalle");
	}
	
	 @Override
	public boolean stopCellEditing() {

	       //SET CLICKED TO FALSE FIRST
			clicked=false;
		return super.stopCellEditing();
	}
	 
	 @Override
	protected void fireEditingStopped() {
		// TODO Auto-generated method stub
		super.fireEditingStopped();
	}
}