/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.empleados;

import cl.starlabs.controladores.CargoJpaController;
import cl.starlabs.controladores.EmpleadoJpaController;
import cl.starlabs.controladores.OficinaJpaController;
import cl.starlabs.modelo.Cargo;
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Oficina;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author cetecom
 */
public class AgregarEmpleado extends javax.swing.JFrame {

    /**
     * Creates new form AgregarEmpleado
     */
    public AgregarEmpleado() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        //Rellenando comboboxs (dropdown lists)
        //estableciendo conexion con db mediante persistencia
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        //foreach de cada elemento de la lista retornada por el controlador desde la db
        for(Oficina o : new OficinaJpaController(emf).listar()) {
            //se añaden elementos al dropdownlist
            slcNroOficina.addItem(o.getNumoficina()+": "+o.getCiudad().getNombre()+" ["+o.getCiudad().getProvincia().getRegion().getNombre().replaceAll("Región", "").replaceAll("de", "").trim()+"]");
        }
        
        // Rellenando los cargos
        for(Cargo c : new CargoJpaController(emf).listar()) {
            slcCargo.addItem(c.getNombre());
        }
        //cerrando entity manager factory para que no se turbe la persistencia
        emf.close();
        
        
        //Seteando otros valores por defecto...
        txtFechaNac.setDate(new Date(100, 0, 1));
        //Generando número randomico para el numero de vendedor
        txtNroEmpleado.setText((int)(Math.random()*10000)+"");
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        txtApaterno = new javax.swing.JTextField();
        txtAmaterno = new javax.swing.JTextField();
        slcNroOficina = new javax.swing.JComboBox<String>();
        slcCargo = new javax.swing.JComboBox<String>();
        slcSexo = new javax.swing.JComboBox();
        txtFechaNac = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtNroEmpleado = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPassword1 = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        txtPassword2 = new javax.swing.JPasswordField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        slcPorcentaje = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("StarLabs DreamHome :: Agregar Empleado");
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información del Empleado"));

        jLabel1.setText("Nombres");

        jLabel2.setText("Apellido Paterno");

        jLabel3.setText("Apellido Materno");

        jLabel4.setText("Fecha de Nacimiento");

        jLabel5.setText("Sexo");

        jLabel6.setText("Numero de Oficina");

        jLabel7.setText("Cargo");

        txtNombres.setNextFocusableComponent(txtApaterno);

        txtApaterno.setNextFocusableComponent(txtAmaterno);

        txtAmaterno.setNextFocusableComponent(txtFechaNac);

        slcNroOficina.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        slcNroOficina.setNextFocusableComponent(slcCargo);

        slcCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione..." }));
        slcCargo.setNextFocusableComponent(txtNroEmpleado);

        slcSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione...", "Masculino", "Femenino" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombres)
                    .addComponent(txtApaterno)
                    .addComponent(txtAmaterno)
                    .addComponent(slcNroOficina, 0, 166, Short.MAX_VALUE)
                    .addComponent(slcCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slcSexo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFechaNac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmaterno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slcSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(slcNroOficina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(slcCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        txtNombres.getAccessibleContext().setAccessibleName("txtNombre");
        txtApaterno.getAccessibleContext().setAccessibleName("txtApellidoPaterno");
        txtAmaterno.getAccessibleContext().setAccessibleName("txtApellidoMaterno");
        slcNroOficina.getAccessibleContext().setAccessibleName("comboBoxOficina");
        slcCargo.getAccessibleContext().setAccessibleName("comboBoxCargo");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de Acceso"));

        jLabel8.setText("Nro de Empleado");

        txtNroEmpleado.setNextFocusableComponent(txtPassword1);

        jLabel9.setText("Contraseña");

        txtPassword1.setNextFocusableComponent(txtPassword2);

        jLabel10.setText("Repetir Contraseña");

        txtPassword2.setNextFocusableComponent(btnGuardar);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(txtNroEmpleado)
                    .addComponent(txtPassword1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNroEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtNroEmpleado.getAccessibleContext().setAccessibleName("txtNumEmpleado");
        txtPassword1.getAccessibleContext().setAccessibleName("txtContraseña1");
        txtPassword2.getAccessibleContext().setAccessibleName("txtContraseña2");

        btnCancelar.setText("Cancelar");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Renta del Empleado"));

        jLabel11.setText("Salario ($)");

        jLabel12.setText("% Comisión x Venta");

        slcPorcentaje.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "0%", "5%", "10%", "15%", "20%", "25%", "30%", "35%", "40%", "45%", "50%" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(slcPorcentaje);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(txtSalario))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnCancelar.getAccessibleContext().setAccessibleName("btnCancelar");
        btnGuardar.getAccessibleContext().setAccessibleName("btnGuardar");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
    }//GEN-LAST:event_formKeyPressed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        //Recuperando valores
        String nombres = txtNombres.getText();
        String apaterno = txtApaterno.getText();
        String amaterno = txtAmaterno.getText();
        Date fecNac = txtFechaNac.getDate();
        Character sexo = slcSexo.getSelectedItem().toString().charAt(0);
        String oficina = slcNroOficina.getSelectedItem().toString().split(":")[0];
        String cargo = slcCargo.getSelectedItem().toString();
        String numeroEmpleado = txtNroEmpleado.getText();
        String password1 = txtPassword1.getText();
        String password2 = txtPassword2.getText();
        String salario = txtSalario.getText();
        Double porcentaje = Double.parseDouble(slcPorcentaje.getSelectedValue().toString().split("%")[0]);
        
        if(nombres.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Son necesarios los nombres del empleado"); txtNombres.requestFocus();
        }else{
            if(apaterno.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El apellido patertno del empleado esta vacío"); txtApaterno.requestFocus();
            }else{
                if(amaterno.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El apellido materno del empleado esta vacío"); txtAmaterno.requestFocus();
                }else{
                    if(sexo.compareTo('S') == 0) {
                        JOptionPane.showMessageDialog(null, "Seleccione el sexo del empleado"); slcSexo.requestFocus();
                    }else{
                        if(oficina.compareToIgnoreCase("Seleccione...") == 0) {
                            JOptionPane.showMessageDialog(null, "Seleccione la oficina en la que trabajará el empleado"); slcNroOficina.requestFocus();
                        }else{
                            if(cargo.compareToIgnoreCase("Seleccione...") == 0) {
                                JOptionPane.showMessageDialog(null, "Seleccione el cargo en el que trabajará el empleado"); slcCargo.requestFocus();
                            }else{
                                if(numeroEmpleado.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Escriba el número de empleado"); txtNroEmpleado.requestFocus();
                                }else{
                                    if(password1.isEmpty()) {
                                        JOptionPane.showMessageDialog(null, "Escriba una contraseña para el empleado"); txtPassword1.requestFocus();
                                    }else{
                                        if(password2.isEmpty()) {
                                            JOptionPane.showMessageDialog(null, "Ha olvidado reescribir la contraseña del empleado"); txtPassword2.requestFocus();
                                        }else{
                                            if(password1.compareTo(password2) != 0) {
                                                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden"); txtPassword1.setText(""); txtPassword2.setText(""); txtPassword1.requestFocus();
                                            }else{
                                                if(salario.isEmpty())
                                                {
                                                    JOptionPane.showMessageDialog(null, "Escriba el salario para el empleado"); txtSalario.requestFocus();
                                                }else{
                                                    //todo correcto... guardar
                                                    try {
                                                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
                                                        Empleado e = new Empleado(Integer.parseInt(numeroEmpleado), oficina, nombres, apaterno, amaterno, sexo, fecNac, Integer.parseInt(salario), porcentaje);
                                                        new EmpleadoJpaController(emf).create(e);
                                                        JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");
                                                        this.dispose();
                                                    }catch(Exception e) {
                                                        JOptionPane.showMessageDialog(null, "Error Inesperado: El servidor dijo: "+e.getMessage());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> slcCargo;
    private javax.swing.JComboBox<String> slcNroOficina;
    private javax.swing.JList slcPorcentaje;
    private javax.swing.JComboBox slcSexo;
    private javax.swing.JTextField txtAmaterno;
    private javax.swing.JTextField txtApaterno;
    private com.toedter.calendar.JDateChooser txtFechaNac;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNroEmpleado;
    private javax.swing.JPasswordField txtPassword1;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JTextField txtSalario;
    // End of variables declaration//GEN-END:variables
}
