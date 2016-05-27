/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.propiedades;

import cl.starlabs.controladores.CiudadJpaController;
import cl.starlabs.controladores.PropiedadJpaController;
import cl.starlabs.controladores.TipopropiedadesJpaController;
import cl.starlabs.modelo.Ciudad;
import cl.starlabs.modelo.Empleado;
import cl.starlabs.modelo.Propiedad;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Tipopropiedades;
import cl.starlabs.vista.utilidades.UtilidadBuscarPropietario;
import java.awt.GraphicsConfiguration;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Victor Manuel Araya
 */
public class RegistrarPropiedad extends javax.swing.JFrame {

    EntityManagerFactory emf = null;
    Empleado e = null;
    Propietario p = null;
    Propiedad propi = null;
    
    public RegistrarPropiedad() {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("PU");
        this.rellenarCiudades();
        this.rellenarTipos();
    }

    public RegistrarPropiedad(Empleado e) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.e = e;
        lblEmpleado.setText(e.getNumempleado()+" - "+e.getApaterno()+" "+e.getAmaterno()+" "+e.getNombre());
        emf = Persistence.createEntityManagerFactory("PU");
        this.rellenarCiudades();
        this.rellenarTipos();
    }
    
    public RegistrarPropiedad(Empleado e, Propietario prop) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.e = e;
        lblEmpleado.setText(e.getNumempleado()+" - "+e.getApaterno()+" "+e.getAmaterno()+" "+e.getNombre());
        emf = Persistence.createEntityManagerFactory("PU");
        this.rellenarCiudades();
        this.rellenarTipos();
        this.p = prop;
        txtPropietario.setText(p.getRut()+"-"+p.getDv()+": "+p.getApaterno()+" "+p.getAmaterno()+" "+p.getNombre());
    }
    
    public RegistrarPropiedad(Empleado e, Propietario prop, Propiedad propi) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.e = e;
        this.setTitle("StarLabs DreamHome :: Edición de Propiedad #"+propi.getNumpropiedad());
        lblEmpleado.setText(e.getNumempleado()+" - "+e.getApaterno()+" "+e.getAmaterno()+" "+e.getNombre());
        txtDireccion.setText(propi.getCalle());
        emf = Persistence.createEntityManagerFactory("PU");
        cmbCiudad.addItem(this.buscarCiudad(propi.getCiudad()));
        cmbTipo.addItem(propi.getTipo().getNombre());
        txtZipCode.setText(propi.getCodigopostal());
        txtHabitaciones.setText(propi.getHab()+"");
        int valorRenta = ((int)propi.getRenta());
        txtRenta.setText(valorRenta+"");
        this.rellenarCiudades();
        this.rellenarTipos();
        this.p = prop;
        this.propi = propi;
        txtPropietario.setText(p.getRut()+"-"+p.getDv()+": "+p.getApaterno()+" "+p.getAmaterno()+" "+p.getNombre());
        if(propi.getDisponible() == '1') {
            chkDisponible.setSelected(true);
        }else{
            chkDisponible.setSelected(false);
        }
        jButton3.setText("Actualizar");
    }
    
    
    
    public void rellenarCiudades() {
        cmbCiudad.addItem("Seleccione...");
        for(Ciudad c : new CiudadJpaController(emf).listar()) {
            cmbCiudad.addItem(c.getNombre());
        }
    }
    
    public void rellenarTipos() {
        cmbTipo.addItem("Seleccione...");
        for(Tipopropiedades tp : new TipopropiedadesJpaController(emf).listar()) {
            cmbTipo.addItem(tp.getNombre());
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblEmpleado = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbCiudad = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        lblDireccionCompleta = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtZipCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        txtHabitaciones = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtRenta = new javax.swing.JTextField();
        chkDisponible = new javax.swing.JCheckBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("StarLabs DreamHome :: Registrar Propiedad");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de la propiedad"));

        jLabel1.setText("Empleado");

        lblEmpleado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEmpleado.setText("Determinando...");

        jLabel3.setText("Propietario");

        txtPropietario.setEditable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/iconos/find.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Dirección");

        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDireccionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDireccionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        jLabel5.setText("Ciudad");

        cmbCiudad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCiudadItemStateChanged(evt);
            }
        });

        jLabel6.setText("Dirección Completa");

        lblDireccionCompleta.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblDireccionCompleta.setText("Dirección...");

        jLabel8.setText("ZIP Code");

        txtZipCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtZipCodeKeyTyped(evt);
            }
        });

        jLabel9.setText("Tipo");

        jLabel10.setText("# Habitaciones");

        txtHabitaciones.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHabitacionesKeyTyped(evt);
            }
        });

        jLabel11.setText("Renta ($)");

        txtRenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRentaKeyTyped(evt);
            }
        });

        chkDisponible.setSelected(true);
        chkDisponible.setText("Disponible para arriendo");
        chkDisponible.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkDisponibleMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccion)
                            .addComponent(cmbCiudad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblEmpleado)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPropietario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(22, 22, 22)
                                .addComponent(txtHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRenta, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkDisponible))
                            .addComponent(lblDireccionCompleta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHabitaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtRenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(chkDisponible))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDireccionCompleta, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        UtilidadBuscarPropietario utbp = new UtilidadBuscarPropietario(p, "registroPropiedad", e);
        utbp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtDireccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyPressed
        
    }//GEN-LAST:event_txtDireccionKeyPressed

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyReleased
        lblDireccionCompleta.setText(txtDireccion.getText());
    }//GEN-LAST:event_txtDireccionKeyReleased

    private void cmbCiudadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCiudadItemStateChanged
        Ciudad c = new CiudadJpaController(emf).buscarPorNombre(cmbCiudad.getSelectedItem().toString());
        if(c == null) {
            lblDireccionCompleta.setText(txtDireccion.getText()+", Ciudad Desconocida");
        }else{
            lblDireccionCompleta.setText(txtDireccion.getText()+", "+c.getNombre()+", "+c.getProvincia().getNombre()+", "+c.getProvincia().getRegion().getNombre()+", "+c.getProvincia().getRegion().getPais().getNombre());
        }
        
    }//GEN-LAST:event_cmbCiudadItemStateChanged
    
    public String buscarCiudad(BigInteger ciudadId){
        try {
            return new CiudadJpaController(emf).findCiudad(new BigDecimal(ciudadId)).getNombre();
        } catch (Exception e) {
            return "Indefinida";
        }
    } 
    
    private void txtZipCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtZipCodeKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtZipCodeKeyTyped

    private void txtHabitacionesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHabitacionesKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtHabitacionesKeyTyped

    private void txtRentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRentaKeyTyped
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtRentaKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(txtPropietario.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Debe buscar al propietario del inmueble para poder registrar la propiedad");
            jButton1ActionPerformed(evt);
        }else{
            if(txtDireccion.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Debe escribir la dirección de la propiedad");
                txtDireccion.requestFocus();
            }else{
                if(cmbCiudad.getSelectedItem().toString().compareToIgnoreCase("Seleccione...") == 0) {
                   JOptionPane.showMessageDialog(null, "Debe seleccionar la ciudad para complementar la dirección de la propiedad"); 
                   cmbCiudad.requestFocus();
                }else{
                    if(txtZipCode.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe especificar el codigo postal de la dirección de la propiedad");
                        txtZipCode.requestFocus();
                    }else{
                        if(cmbTipo.getSelectedItem().toString().compareToIgnoreCase("Seleccione...") == 0) {
                            JOptionPane.showMessageDialog(null, "Seleccione el tipo de propiedad a registrar");
                            cmbTipo.requestFocus();
                        }else{
                            if(txtHabitaciones.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Debe especificar la cantidad de habitaciones con las que cuenta la propiedad");
                                txtHabitaciones.requestFocus();
                            }else{
                                if(txtRenta.getText().isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Debe escribir el valor de arriendo de la propiedad");
                                    txtRenta.requestFocus();
                                }else{
                                    if(!chkDisponible.isSelected()) {
                                        int valor = JOptionPane.showConfirmDialog(null, "¿Esta seguro de marcar esta propiedad como no disponible?");
                                        if(valor == 1) //si desea dejarla como disponible
                                        {
                                            chkDisponible.setSelected(true);
                                        }else{
                                            chkDisponible.requestFocus();
                                        }
                                    }

                                        //registro
                                        String direccion = txtDireccion.getText();
                                        String ciudad = cmbCiudad.getSelectedItem().toString();
                                        String zipcode = txtZipCode.getText();
                                        String tipo = cmbTipo.getSelectedItem().toString();
                                        String habitaciones = txtHabitaciones.getText();
                                        String renta = txtRenta.getText();
                                        char disponible = 0;
                                        if(chkDisponible.isSelected()) {
                                            disponible = 1;
                                        }else{
                                            disponible = 0;
                                        }
                                        //Buscando ciudad escogida
                                        Ciudad city = new CiudadJpaController(emf).buscarPorNombre(ciudad);
                                        Tipopropiedades tip = new TipopropiedadesJpaController(emf).buscarPorNombre(tipo);
                                        if(jButton3.getText().compareToIgnoreCase("Actualizar") != 0) {
                                            try {
                                                Propiedad prop = new Propiedad(new PropiedadJpaController(emf).ultimoId(), direccion, city.getIdciudad().toBigInteger(), zipcode, new BigInteger(habitaciones), new Double(renta), disponible, e, p, tip);
                                                new PropiedadJpaController(emf).create(prop);
                                                JOptionPane.showMessageDialog(null, "Propiedad Registrada con éxito");
                                                this.dispose();
                                                ListarPropiedades lpp = new ListarPropiedades(p);
                                                lpp.setVisible(true);
                                            } catch (Exception e) {
                                                JOptionPane.showMessageDialog(null, "Error: El servidor dijo: "+e.getMessage());
                                            }
                                        }else{
                                            try {
                                                Propiedad prop = propi;
                                                prop.setNumempleado(propi.getNumempleado());
                                                prop.setNumpropietario(p);
                                                prop.setNumpropiedad(propi.getNumpropiedad());
                                                prop.setCalle(txtDireccion.getText());
                                                prop.setCiudad(city.getIdciudad().toBigInteger());
                                                prop.setCodigopostal(zipcode);
                                                prop.setHab(new BigInteger(habitaciones));
                                                prop.setRenta(new Double(renta));
                                                prop.setDisponible(disponible);
                                                new PropiedadJpaController(emf).edit(prop);
                                                JOptionPane.showMessageDialog(null, "Propiedad actualizada con éxito");
                                                this.dispose();
                                            }catch(Exception e) {
                                                JOptionPane.showMessageDialog(null, "Error: El servidor dijo: "+e.getMessage());
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void chkDisponibleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkDisponibleMouseClicked
 
    }//GEN-LAST:event_chkDisponibleMouseClicked

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
            java.util.logging.Logger.getLogger(RegistrarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarPropiedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrarPropiedad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkDisponible;
    private javax.swing.JComboBox cmbCiudad;
    private javax.swing.JComboBox cmbTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDireccionCompleta;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtHabitaciones;
    private javax.swing.JTextField txtPropietario;
    private javax.swing.JTextField txtRenta;
    private javax.swing.JTextField txtZipCode;
    // End of variables declaration//GEN-END:variables
}
