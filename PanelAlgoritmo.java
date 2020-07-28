package paneles;

import conf.Conf;
import datos.DatosMetodoBasicos;
import datos.FamiliaEjecuciones;
import eventos.NavegacionListener;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import opciones.GestorOpciones;
import opciones.OpcionVistas;
import opciones.Vista;
import utilidades.Arrays;
import utilidades.NombresYPrefijos;
import utilidades.ServiciosString;
import utilidades.Texto;
import ventanas.Ventana;

public class PanelAlgoritmo extends JPanel implements ChangeListener, ComponentListener {
    static final long serialVersionUID = 14L;

    private static final int GROSOR_SPLIT_DIVIDER = 8;

    private static JSplitPane separadorCodigo;

    private static JSplitPane separadorVistas;

    private static JSplitPane separadorCentral;

    private GestorOpciones gOpciones = new GestorOpciones();

    private static PanelCodigo pCodigo;

    private static PanelCompilador pCompilador;

    private static PanelGrafo pGrafo;

    private static PanelCodigoBotones pCodigoBotones;

    private static PanelTraza pTraza;

    private static PanelPila pPila;

    private static PanelArbol pArbol;

    private static PanelCrono pCrono;

    private static PanelEstructura pEstructura;

    private static PanelControl pControl;

    private String tituloPanel;

    private String idTraza;

    private String[] nombresMetodos;

    public static NombresYPrefijos nyp = null;

    private static JScrollPane jspCompilador;

    private static JScrollPane jspCodigo;

    private static JScrollPane jspTraza;

    private static JScrollPane jspPila;

    private static JScrollPane jspCrono;

    private static JScrollPane jspEstructura;

    private static JScrollPane jspGrafo;

    private JPanel jspArbol;

    private JPanel contenedorCompilador;

    private JPanel contenedorCodigo;

    private JPanel contenedorTraza;

    private JPanel contenedorPila;

    private JPanel contenedorArbol;

    private JPanel contenedorControl;

    private JPanel contenedorGrafo;

    private JPanel contenedorCrono;

    private JPanel contenedorEstructura;

    private JTabbedPane panel1;

    private JTabbedPane panel2;

    private boolean mostrarNombreMetodos = false;

    private boolean ocupado = false;

    private boolean abriendoVistas = false;

    private JPanel panelGral;

    private String[] nombresVistas = new String[Vista.codigos.length];

    private NavegacionListener arbolNavegacionListener;

    private static Boolean grafoActivado = Boolean.valueOf(false);

    private static int panel1Pestana;

    private static int panel2Pestana;

    public PanelAlgoritmo() throws Exception {
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());
        pCodigo = new PanelCodigo(null);
        pTraza = new PanelTraza();
        pCompilador = new PanelCompilador();
        pCodigoBotones = new PanelCodigoBotones(pCodigo);
        jspTraza = new JScrollPane(pTraza);
        this.contenedorCodigo = new JPanel();
        this.contenedorCompilador = new JPanel();
        this.contenedorTraza = new JPanel();
        this.contenedorCodigo.setLayout(new BorderLayout());
        this.contenedorCompilador.setLayout(new BorderLayout());
        this.contenedorTraza.setLayout(new BorderLayout());
        this.contenedorCodigo.add(pCodigo.getPanel(), "Center");
        this.contenedorCodigo.add(pCodigoBotones, "South");
        this.contenedorCompilador.add(pCompilador.getPanel(), "Center");
        this.contenedorTraza.add(jspTraza);
        jspCompilador = new JScrollPane(this.contenedorCompilador);
        separadorCodigo = new JSplitPane(false, this.contenedorCodigo, jspCompilador);
        separadorCodigo.setResizeWeight(0.85D);
        separadorCodigo.setDividerLocation(0.8D);
        jPanel1.add(separadorCodigo, "Center");
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        try {
            pPila = new PanelPila(null);
            pArbol = new PanelArbol(null);
            pCrono = new PanelCrono(null);
            pGrafo = new PanelGrafo((DatosMetodoBasicos)null, null, null);
        } catch (OutOfMemoryError outOfMemoryError) {
            pArbol = null;
            throw outOfMemoryError;
        } catch (Exception exception) {
            pArbol = null;
            exception.printStackTrace();
            throw exception;
        }
        jspPila = new JScrollPane(pPila);
        jspGrafo = new JScrollPane(pGrafo);
        this.jspArbol = new JPanel();
        this.jspArbol.setLayout(new BorderLayout());
        this.jspArbol.add(pArbol, "Center");
        this.contenedorPila = new JPanel();
        this.contenedorArbol = new JPanel();
        this.contenedorGrafo = new JPanel();
        this.contenedorPila.setLayout(new BorderLayout());
        this.contenedorPila.add(jspPila, "Center");
        this.contenedorArbol.setLayout(new BorderLayout());
        this.contenedorArbol.add(this.jspArbol, "Center");
        this.contenedorGrafo.setLayout(new BorderLayout());
        this.contenedorGrafo.add(jspGrafo, "Center");
        jspCrono = new JScrollPane(pCrono);
        this.contenedorCrono = new JPanel();
        this.contenedorCrono.setLayout(new BorderLayout());
        this.contenedorCrono.add(jspCrono, "Center");
        jspEstructura = new JScrollPane(pEstructura);
        this.contenedorEstructura = new JPanel();
        this.contenedorEstructura.setLayout(new BorderLayout());
        this.contenedorEstructura.add(jspEstructura, "Center");
        quitarBordesJSP();
        this.panel1 = new JTabbedPane();
        this.panel2 = new JTabbedPane();
        this.panel1.addChangeListener(this);
        this.panel2.addChangeListener(this);
        this.nombresVistas = recopilarNombresVistas();
        if (Conf.disposicionPaneles == 1) {
            b = 1;
        } else {
            b = 0;
        }
        separadorVistas = new JSplitPane(b, this.panel1, this.panel2);
        separadorVistas.setDividerSize(8);
        separadorVistas.setOneTouchExpandable(true);
        separadorVistas.setResizeWeight(0.5D);
        separadorVistas.setDividerLocation(0.5D);
        jPanel2.add(separadorVistas, "Center");
        pControl = new PanelControl("", this);
        this.contenedorControl = new JPanel();
        this.contenedorControl.setLayout(new BorderLayout());
        this.contenedorControl.add(pControl, "Center");
        JPanel jPanel3 = new JPanel();
        separadorCentral = new JSplitPane(true, jPanel1, jPanel2);
        separadorCentral.setDividerSize(8);
        separadorCentral.setOneTouchExpandable(true);
        separadorCentral.setResizeWeight(0.3D);
        separadorCentral.setDividerLocation(0.3D);
        jPanel3.setLayout(new BorderLayout());
        jPanel3.add(separadorCentral, "Center");
        this.arbolNavegacionListener = pArbol.getNavegacionListener();
        this.jspArbol.addComponentListener(this.arbolNavegacionListener);
        this.panelGral = new JPanel();
        this.panelGral.setLayout(new BorderLayout());
        this.panelGral.add(this.contenedorControl, "North");
        this.panelGral.add(jPanel3, "Center");
        setLayout(new BorderLayout());
        add(this.panelGral, "Center");
        anadirMouseEventPaneles();
    }

    public void distribuirPaneles(int paramInt) { separadorVistas.setOrientation((paramInt == 1) ? 1 : 0); }

    public void idioma() throws Exception {
        pControl.idioma();
        if (estaOcupado()) {
            try {
                pTraza = new PanelTraza();
            } catch (Exception exception) {}
            jspTraza.removeAll();
            jspTraza = new JScrollPane(pTraza);
            pTraza.visualizar();
            this.contenedorTraza.removeAll();
            this.contenedorTraza.add(jspTraza);
            this.contenedorTraza.updateUI();
            updateUI();
        }
        String[] arrayOfString = getNombreVistasDisponibles();
        String[][] arrayOfString1 = Texto.idiomasDisponibles();
        for (byte b = 0; b < arrayOfString.length; b++) {
            for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
                for (byte b2 = 0; b2 < Vista.codigos.length; b2++) {
                    if (arrayOfString[b].equals(Texto.get(Vista.codigos[b2], arrayOfString1[b1][1]))) {
                        byte b3;
                        for (b3 = 0; b3 < this.panel1.getTabCount(); b3++) {
                            if (this.panel1.getTitleAt(b3).equals(arrayOfString[b]))
                                this.panel1.setTitleAt(b3, Texto.get(Vista.codigos[b2], Conf.idioma));
                        }
                        for (b3 = 0; b3 < this.panel2.getTabCount(); b3++) {
                            if (this.panel2.getTitleAt(b3).equals(arrayOfString[b]))
                                this.panel2.setTitleAt(b3, Texto.get(Vista.codigos[b2], Conf.idioma));
                        }
                    }
                }
            }
        }
        quitarBordesJSP();
        this.nombresVistas = recopilarNombresVistas();
    }

    private String[] recopilarNombresVistas() { return Texto.get(Vista.codigos, Conf.idioma); }

    public void abrirPanelCodigo(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
        this.contenedorCodigo.removeAll();
        pCodigo.abrir(paramString, paramBoolean1, paramBoolean2, false);
        jspCodigo = new JScrollPane(pCodigo.getPanel());
        pCodigoBotones.activarTodosBotones();
        quitarBordesJSP();
        this.contenedorCodigo.add(jspCodigo, "Center");
        this.contenedorCodigo.add(pCodigoBotones, "South");
        this.contenedorCodigo.updateUI();
    }

    public void cerrarPanelCodigo() throws Exception {
        this.contenedorCodigo.removeAll();
        pCodigo = new PanelCodigo(null);
        pCodigoBotones.desactivarTodosBotones();
        quitarBordesJSP();
        this.contenedorCodigo.updateUI();
        nyp = null;
    }

    public void abrirVistas() throws Exception {
        this.abriendoVistas = true;
        ubicarVistas();
        nyp = null;
        this.mostrarNombreMetodos = (Ventana.thisventana.traza.getNumMetodos() != 1);
        if (this.mostrarNombreMetodos) {
            nyp = new NombresYPrefijos();
            this.nombresMetodos = Ventana.thisventana.trazaCompleta.getNombresMetodos();
            String[] arrayOfString = ServiciosString.obtenerPrefijos(this.nombresMetodos);
            for (byte b = 0; b < this.nombresMetodos.length; b++)
                nyp.add(this.nombresMetodos[b], arrayOfString[b]);
        }
        try {
            pArbol = new PanelArbol(nyp);
            pPila = new PanelPila(nyp);
            if (grafoActivado.booleanValue())
                pGrafo.visualizar2(nyp);
            if (Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
                Ventana.thisventana.habilitarOpcionesDYV(true);
                pCrono = new PanelCrono(nyp);
                pEstructura = new PanelEstructura(nyp);
            } else {
                pTraza = new PanelTraza();
            }
            pControl.setValores(Ventana.thisventana.traza.getTitulo(), this);
            this.jspArbol.removeComponentListener(this.arbolNavegacionListener);
            this.arbolNavegacionListener = pArbol.getNavegacionListener();
            this.jspArbol.addComponentListener(this.arbolNavegacionListener);
            (new Thread() {
                public void run() throws Exception {
                    try {
                        wait(50L);
                    } catch (InterruptedException interruptedException) {}
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() throws Exception {
                            pArbol.getNavegacionListener().ejecucion(1);
                            pArbol.updateUI();
                        }
                    });
                }
            }).start();
            if (!Conf.arranqueEstadoInicial || FamiliaEjecuciones.getInstance().estaHabilitado()) {
                pControl.hacerFinal();
            } else {
                actualizar();
            }
            this.ocupado = true;
        } catch (Exception exception) {
            try {
                exception.printStackTrace();
                System.out.println("\n-Ha saltado una excepcion(PanelAlgoritmo)-\n");
                pArbol = new PanelArbol(null);
                pPila = new PanelPila(null);
                pGrafo = new PanelGrafo((DatosMetodoBasicos)null, null, null);
                pTraza = new PanelTraza();
                pCrono = new PanelCrono(null);
                pEstructura = new PanelEstructura(null);
                pControl = new PanelControl("", this);
            } catch (Exception exception1) {}
        }
        this.contenedorArbol.removeAll();
        this.contenedorPila.removeAll();
        this.contenedorTraza.removeAll();
        this.contenedorCrono.removeAll();
        this.contenedorEstructura.removeAll();
        this.contenedorGrafo.removeAll();
        this.jspArbol.removeAll();
        jspPila.removeAll();
        jspTraza.removeAll();
        jspCrono.removeAll();
        jspEstructura.removeAll();
        jspGrafo.removeAll();
        this.jspArbol.add(pArbol);
        jspPila = new JScrollPane(pPila);
        jspTraza = new JScrollPane(pTraza);
        jspCrono = new JScrollPane(pCrono);
        jspEstructura = new JScrollPane(pEstructura);
        jspGrafo = new JScrollPane(pGrafo);
        this.contenedorArbol.add(this.jspArbol);
        this.contenedorPila.add(jspPila);
        this.contenedorTraza.add(jspTraza);
        this.contenedorCrono.add(jspCrono);
        this.contenedorEstructura.add(jspEstructura);
        this.contenedorGrafo.add(jspGrafo);
        quitarBordesJSP();
        this.contenedorArbol.updateUI();
        this.contenedorPila.updateUI();
        this.contenedorTraza.updateUI();
        this.contenedorCrono.updateUI();
        this.contenedorEstructura.updateUI();
        this.contenedorControl.updateUI();
        this.contenedorGrafo.updateUI();
        this.abriendoVistas = false;
        recordarPestanaPaneles();
    }

    public void abrirVistas(String paramString) {
        this.abriendoVistas = true;
        ubicarVistas();
        try {
            pArbol = new PanelArbol(paramString, new ImageIcon(paramString));
            pPila = new PanelPila(null);
            pTraza = new PanelTraza();
            pGrafo = new PanelGrafo((DatosMetodoBasicos)null, null, null);
            this.ocupado = true;
            pControl.setValores(paramString.substring(paramString.lastIndexOf("\\") + 1, paramString.lastIndexOf(".")), this);
            Ventana.thisventana.setTitulo(paramString.substring(paramString.lastIndexOf("\\") + 1, paramString.length()));
        } catch (Exception exception) {
            try {
                exception.printStackTrace();
                System.out.println("\n-Ha saltado una excepcion-\n");
                pArbol = new PanelArbol(null);
                pPila = new PanelPila(null);
                pGrafo = new PanelGrafo((DatosMetodoBasicos)null, null, null);
                pTraza = new PanelTraza();
                pControl = new PanelControl("", this);
                this.ocupado = false;
            } catch (Exception exception1) {}
        }
        this.contenedorArbol.removeAll();
        this.contenedorPila.removeAll();
        this.contenedorTraza.removeAll();
        this.contenedorGrafo.removeAll();
        this.jspArbol.removeAll();
        jspPila.removeAll();
        jspTraza.removeAll();
        jspGrafo.removeAll();
        this.jspArbol.add(pArbol);
        jspPila = new JScrollPane(pPila);
        jspTraza = new JScrollPane(pTraza);
        jspGrafo = new JScrollPane(pGrafo);
        this.contenedorArbol.add(this.jspArbol);
        this.contenedorPila.add(jspPila);
        this.contenedorTraza.add(jspTraza);
        this.contenedorGrafo.add(jspGrafo);
        quitarBordesJSP();
        this.contenedorArbol.updateUI();
        this.contenedorPila.updateUI();
        this.contenedorTraza.updateUI();
        this.contenedorControl.updateUI();
        this.contenedorGrafo.updateUI();
        this.abriendoVistas = false;
    }

    public void cerrarVistas() throws Exception {
        try {
            this.ocupado = false;
            Ventana.thisventana.traza = null;
            Ventana.thisventana.trazaCompleta = null;
            pArbol = new PanelArbol(null);
            pPila = new PanelPila(null);
            pGrafo = new PanelGrafo((DatosMetodoBasicos)null, null, null);
            pTraza = new PanelTraza();
            pCrono = new PanelCrono(null);
            pControl.setValores("", this);
        } catch (Exception exception) {}
        this.contenedorArbol.removeAll();
        this.contenedorPila.removeAll();
        this.contenedorTraza.removeAll();
        this.contenedorCrono.removeAll();
        this.contenedorEstructura.removeAll();
        if (this.contenedorGrafo != null)
            this.contenedorGrafo.removeAll();
        this.jspArbol.removeAll();
        jspPila.removeAll();
        jspTraza.removeAll();
        jspCrono.removeAll();
        jspEstructura.removeAll();
        jspGrafo.removeAll();
        this.jspArbol.add(pArbol);
        jspPila = new JScrollPane(pPila);
        jspTraza = new JScrollPane(pTraza);
        jspCrono = new JScrollPane(pCrono);
        jspEstructura = new JScrollPane(pEstructura);
        jspGrafo = new JScrollPane(pGrafo);
        this.contenedorArbol.add(this.jspArbol);
        this.contenedorPila.add(jspPila);
        this.contenedorTraza.add(jspTraza);
        this.contenedorCrono.add(jspCrono);
        this.contenedorEstructura.add(jspEstructura);
        this.contenedorGrafo.add(this.jspArbol);
        quitarBordesJSP();
        this.contenedorArbol.updateUI();
        this.contenedorPila.updateUI();
        this.contenedorTraza.updateUI();
        this.contenedorCrono.updateUI();
        this.contenedorEstructura.updateUI();
        this.contenedorGrafo.updateUI();
        this.contenedorControl.updateUI();
        this.panel1.removeAll();
        this.panel2.removeAll();
        separadorVistas.setRightComponent(this.panel2);
        int i = separadorVistas.getDividerLocation();
        separadorVistas.setResizeWeight(0.5D);
        separadorVistas.setOneTouchExpandable(true);
        separadorVistas.setEnabled(true);
        FamiliaEjecuciones.getInstance().deshabilitar();
        nyp = null;
        grafoActivado = Boolean.valueOf(false);
        separadorVistas.setDividerLocation(i);
    }

    private void actualizarFamiliaEjecuciones(boolean paramBoolean) {
        int j;
        int i;
        int[] arrayOfInt = Conf.getTamanoMonitor();
        if (Conf.disposicionPaneles == 2) {
            i = arrayOfInt[1] / 5;
            j = Math.max(0, separadorVistas.getHeight() - i - 8);
        } else {
            i = arrayOfInt[0] / 6;
            j = Math.max(0, separadorVistas.getWidth() - i - 8);
        }
        separadorVistas.setDividerLocation(j);
        FamiliaEjecuciones.getInstance().actualizar(i, Conf.disposicionPaneles, paramBoolean);
    }

    public void ubicarVistas() throws Exception {
        boolean bool = FamiliaEjecuciones.getInstance().estaHabilitado();
        int i = separadorVistas.getDividerLocation();
        if (bool) {
            JScrollPane jScrollPane = FamiliaEjecuciones.getInstance().obtenerPanelEjecuciones();
            separadorVistas.setRightComponent(jScrollPane);
            actualizarFamiliaEjecuciones(false);
            separadorVistas.setResizeWeight(1.0D);
            separadorVistas.setOneTouchExpandable(true);
            separadorVistas.setEnabled(false);
            jScrollPane.removeComponentListener(this);
            jScrollPane.addComponentListener(this);
        } else {
            separadorVistas.setRightComponent(this.panel2);
            separadorVistas.setResizeWeight(0.5D);
            separadorVistas.setOneTouchExpandable(true);
            separadorVistas.setEnabled(true);
        }
        if (Conf.getVista(Vista.codigos[0]).getPanel() == 1 || bool) {
            this.panel1.add(Texto.get(Vista.codigos[0], Conf.idioma), this.contenedorArbol);
        } else {
            this.panel2.add(Texto.get(Vista.codigos[0], Conf.idioma), this.contenedorArbol);
        }
        if (Ventana.thisventana.getTraza() != null) {
            if (Conf.getVista(Vista.codigos[1]).getPanel() == 1 || bool) {
                this.panel1.add(Texto.get(Vista.codigos[1], Conf.idioma), this.contenedorPila);
            } else {
                this.panel2.add(Texto.get(Vista.codigos[1], Conf.idioma), this.contenedorPila);
            }
            if (Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
                if (Conf.getVista(Vista.codigos[2]).getPanel() == 1 || bool) {
                    this.panel1.add(Texto.get(Vista.codigos[2], Conf.idioma), this.contenedorCrono);
                } else {
                    this.panel2.add(Texto.get(Vista.codigos[2], Conf.idioma), this.contenedorCrono);
                }
                if (Conf.getVista(Vista.codigos[3]).getPanel() == 1 || bool) {
                    this.panel1.add(Texto.get(Vista.codigos[3], Conf.idioma), this.contenedorEstructura);
                } else {
                    this.panel2.add(Texto.get(Vista.codigos[3], Conf.idioma), this.contenedorEstructura);
                }
            } else if (Conf.getVista(Vista.codigos[2]).getPanel() == 1 || bool) {
                this.panel1.add(Texto.get(Vista.codigos[2], Conf.idioma), this.contenedorTraza);
            } else {
                this.panel2.add(Texto.get(Vista.codigos[2], Conf.idioma), this.contenedorTraza);
            }
            if (!Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas()) && !bool)
                if (Conf.getVista(Vista.codigos[0]).getPanel() == 1 && Conf.getVista(Vista.codigos[1]).getPanel() == 1 && Conf.getVista(Vista.codigos[2]).getPanel() == 1) {
                    this.panel2.add(Texto.get(Vista.codigos[0], Conf.idioma), this.contenedorArbol);
                    OpcionVistas opcionVistas = (OpcionVistas)this.gOpciones.getOpcion("OpcionVistas", false);
                    opcionVistas.getVista(Vista.codigos[0]).setPanel(2);
                    this.gOpciones.setOpcion(opcionVistas, 2);
                    Conf.setConfiguracionVistas();
                } else if (Conf.getVista(Vista.codigos[0]).getPanel() == 2 && Conf.getVista(Vista.codigos[1]).getPanel() == 2 && Conf.getVista(Vista.codigos[2]).getPanel() == 2) {
                    this.panel1.add(Texto.get(Vista.codigos[0], Conf.idioma), this.contenedorArbol);
                    OpcionVistas opcionVistas = (OpcionVistas)this.gOpciones.getOpcion("OpcionVistas", false);
                    opcionVistas.getVista(Vista.codigos[0]).setPanel(1);
                    this.gOpciones.setOpcion(opcionVistas, 2);
                    Conf.setConfiguracionVistas();
                }
            if (grafoActivado.booleanValue() && this.panel1 != null && this.panel2 != null)
                if (Conf.getVista(Vista.codigos[4]).getPanel() == 1 || bool) {
                    this.panel1.add(Texto.get(Vista.codigos[4], Conf.idioma), this.contenedorGrafo);
                } else {
                    this.panel2.add(Texto.get(Vista.codigos[4], Conf.idioma), this.contenedorGrafo);
                }
        }
        separadorVistas.setDividerLocation(i);
    }

    public boolean estaOcupado() { return this.ocupado; }

    protected void deshabilitarControles() throws Exception { pControl.deshabilitarControles(); }

    protected void habilitarControles() throws Exception { pControl.habilitarControles(); }

    protected void setValoresPanelControl(String paramString) { pControl.setValores(paramString, this); }

    public void actualizar() throws Exception {
        int i = separadorVistas.getDividerLocation();
        if (Ventana.thisventana.getTraza() != null && this.panel1 != null && this.panel2 != null) {
            boolean[] arrayOfBoolean = new boolean[Vista.codigos.length];
            for (byte b = 0; b < arrayOfBoolean.length; b++)
                arrayOfBoolean[b] = false;
            if (this.panel1.indexOfTab(this.nombresVistas[1]) == this.panel1.getSelectedIndex() || this.panel2.indexOfTab(this.nombresVistas[1]) == this.panel2.getSelectedIndex()) {
                (new Thread() {
                    public void run() throws Exception {
                        try {
                            wait(240L);
                        } catch (InterruptedException interruptedException) {}
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() throws Exception { pPila.visualizar(); }
                        });
                    }
                }).start();
                arrayOfBoolean[1] = true;
            }
            if (Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas()) && (this.panel1.indexOfTab(this.nombresVistas[2]) == this.panel1.getSelectedIndex() || this.panel2.indexOfTab(this.nombresVistas[2]) == this.panel2.getSelectedIndex())) {
                (new Thread() {
                    public void run() throws Exception {
                        try {
                            wait(20L);
                        } catch (InterruptedException interruptedException) {}
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() throws Exception { pCrono.visualizar(); }
                        });
                    }
                }).start();
                arrayOfBoolean[2] = true;
            }
            if (this.panel1.indexOfTab(this.nombresVistas[0]) == this.panel1.getSelectedIndex() || this.panel2.indexOfTab(this.nombresVistas[0]) == this.panel2.getSelectedIndex()) {
                (new Thread() {
                    public void run() throws Exception {
                        try {
                            wait(100L);
                        } catch (InterruptedException interruptedException) {}
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() throws Exception { pArbol.visualizar(false, true, false); }
                        });
                    }
                }).start();
                arrayOfBoolean[0] = true;
            }
            if (Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas()) && (this.panel1.indexOfTab(this.nombresVistas[3]) == this.panel1.getSelectedIndex() || this.panel2.indexOfTab(this.nombresVistas[3]) == this.panel2.getSelectedIndex())) {
                (new Thread() {
                    public void run() throws Exception {
                        try {
                            wait(220L);
                        } catch (InterruptedException interruptedException) {}
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() throws Exception { pEstructura.visualizar(); }
                        });
                    }
                }).start();
                arrayOfBoolean[3] = true;
            }
            if (!Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas()) && (this.panel1.indexOfTab(this.nombresVistas[2]) == this.panel1.getSelectedIndex() || this.panel2.indexOfTab(this.nombresVistas[2]) == this.panel2.getSelectedIndex())) {
                pTraza.visualizar();
                arrayOfBoolean[2] = true;
            }
            if (this.panel1.indexOfTab(this.nombresVistas[4]) == this.panel1.getSelectedIndex() || this.panel2.indexOfTab(this.nombresVistas[4]) == this.panel2.getSelectedIndex()) {
                (new Thread() {
                    public void run() throws Exception {
                        try {
                            wait(240L);
                        } catch (InterruptedException interruptedException) {}
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() throws Exception {
                                if (pGrafo != null)
                                    pGrafo.visualizar();
                            }
                        });
                    }
                }).start();
                arrayOfBoolean[4] = true;
            }
        }
        separadorVistas.setDividerLocation(i);
    }

    public void refrescarFormato(boolean paramBoolean) {
        if (pArbol != null && Ventana.thisventana.traza != null) {
            this.mostrarNombreMetodos = (Ventana.thisventana.traza.getNumMetodos() != 1);
            if (this.mostrarNombreMetodos) {
                nyp = new NombresYPrefijos();
                this.nombresMetodos = Ventana.thisventana.trazaCompleta.getNombresMetodos();
                String[] arrayOfString = ServiciosString.obtenerPrefijos(this.nombresMetodos);
                for (byte b = 0; b < this.nombresMetodos.length; b++)
                    nyp.add(this.nombresMetodos[b], arrayOfString[b]);
            }
            pArbol.visualizar(true, true, false);
            pPila.visualizar();
            if (grafoActivado.booleanValue())
                pGrafo.visualizar2(nyp);
            nyp = null;
            pCodigo.visualizar(paramBoolean);
            if (Ventana.thisventana.getTraza() != null && Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
                pCrono.visualizar();
                pEstructura.visualizar();
            } else {
                pTraza.visualizar();
            }
            if (Ventana.thisventana.traza != null)
                pControl.visualizar();
            (new Thread() {
                public void run() throws Exception {
                    try {
                        wait(250L);
                    } catch (InterruptedException interruptedException) {}
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() throws Exception {
                            if (pArbol.getNavegacionListener() != null)
                                pArbol.getNavegacionListener().ejecucion(1);
                        }
                    });
                }
            }).start();
        }
        if (pCodigo != null)
            pCodigo.redibujarLineasErrores();
        if (FamiliaEjecuciones.getInstance().estaHabilitado())
            actualizarFamiliaEjecuciones(true);
        updateUI();
    }

    public void refrescarZoomArbol(int paramInt) {
        if (pArbol != null) {
            pArbol.refrescarZoom(paramInt);
            pArbol.visualizar(false, true, false);
        }
        updateUI();
    }

    public void refrescarZoomPila(int paramInt) {
        if (pPila != null) {
            pPila.refrescarZoom(paramInt);
            pPila.visualizar();
        }
        updateUI();
    }

    public void refrescarZoomTraza(int paramInt) {
        if (pTraza != null) {
            pTraza.refrescarZoom(paramInt);
            pTraza.visualizar();
        }
        updateUI();
    }

    public void refrescarZoomGrafoDep(int paramInt) {
        if (pGrafo != null) {
            pGrafo.refrescarZoom(paramInt);
            pGrafo.visualizar();
        }
        updateUI();
    }

    public void refrescarZoom(int paramInt1, int paramInt2) {
        switch (paramInt1) {
            case 1:
                if (pPila != null) {
                    pPila.refrescarZoom(paramInt2);
                    pPila.visualizar();
                    pPila.updateUI();
                }
                break;
            case 0:
                if (pArbol != null) {
                    pArbol.refrescarZoom(paramInt2);
                    pArbol.visualizar(false, true, false);
                    pArbol.updateUI();
                }
                break;
            case 2:
                if (pCrono != null) {
                    pCrono.refrescarZoom(paramInt2);
                    pCrono.visualizar();
                    pCrono.updateUI();
                }
                break;
            case 3:
                if (pEstructura != null) {
                    pEstructura.refrescarZoom(paramInt2);
                    pEstructura.visualizar();
                    pEstructura.updateUI();
                }
                break;
            case 5:
                if (pGrafo != null) {
                    pGrafo.refrescarZoom(paramInt2);
                    pGrafo.visualizar();
                    pGrafo.updateUI();
                }
                break;
        }
    }

    public String getTituloPanel() { return this.tituloPanel; }

    public int[] dimPanelYGrafo() { return pArbol.dimPanelYGrafo(); }

    public int[] dimPanelYPila() { return pPila.dimPanelYPila(); }

    public int[] dimPanelYGrafoDep() { return pGrafo.dimPanelYGrafoDep(); }

    public int[] dimPanelYGrafoCrono() {
        int[] arrayOfInt1 = pCrono.dimPanelYGrafo();
        int[] arrayOfInt2 = dimPanelCrono();
        int[] arrayOfInt3 = new int[4];
        arrayOfInt3[0] = arrayOfInt2[0];
        arrayOfInt3[1] = arrayOfInt2[1];
        arrayOfInt3[2] = arrayOfInt1[2];
        arrayOfInt3[3] = arrayOfInt1[3];
        return arrayOfInt3;
    }

    public int[] dimPanelYGrafoEstructura() {
        int[] arrayOfInt1 = pEstructura.dimPanelYGrafo();
        int[] arrayOfInt2 = dimPanelEstructura();
        int[] arrayOfInt3 = new int[4];
        arrayOfInt3[0] = arrayOfInt2[0];
        arrayOfInt3[1] = arrayOfInt2[1];
        arrayOfInt3[2] = arrayOfInt1[2];
        arrayOfInt3[3] = arrayOfInt1[3];
        return arrayOfInt3;
    }

    public String getIdTraza() { return this.idTraza; }

    public int[] getZooms() {
        int[] arrayOfInt = new int[5];
        arrayOfInt[0] = pArbol.getZoom();
        arrayOfInt[1] = pPila.getZoom();
        if (Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
            arrayOfInt[2] = pCrono.getZoom();
            arrayOfInt[3] = pEstructura.getZoom();
        }
        arrayOfInt[4] = pGrafo.getZoom();
        return arrayOfInt;
    }

    public static int[] dimPanelEstructura() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = jspEstructura.getWidth();
        arrayOfInt[1] = jspEstructura.getHeight();
        return arrayOfInt;
    }

    public static int[] dimPanelCrono() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = jspCrono.getWidth();
        arrayOfInt[1] = jspCrono.getHeight();
        return arrayOfInt;
    }

    public static int[] dimPanelTraza() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = jspTraza.getWidth();
        arrayOfInt[1] = jspTraza.getHeight();
        return arrayOfInt;
    }

    public static int[] dimPanelPila() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = jspPila.getWidth();
        arrayOfInt[1] = jspPila.getHeight();
        return arrayOfInt;
    }

    public static int[] dimPanelGrafoDep() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = jspGrafo.getWidth();
        arrayOfInt[1] = jspGrafo.getHeight();
        return arrayOfInt;
    }

    public int[] dimPanelPrincipal() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = this.jspArbol.getWidth();
        arrayOfInt[1] = pArbol.alturaJSPArbol();
        return arrayOfInt;
    }

    public int[] dimPanelArbol() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = this.jspArbol.getWidth();
        arrayOfInt[1] = this.jspArbol.getHeight();
        return arrayOfInt;
    }

    public int[] dimGrafoPila() { return pPila.dimGrafo(); }

    public int[] dimGrafoDep() { return pGrafo.dimGrafo(); }

    public int[] dimGrafoPrincipal() { return pArbol.dimGrafo(); }

    public int[] dimGrafoVisiblePrincipal() { return pArbol.dimGrafoVisible(); }

    public int[] dimGrafoVisibleCrono() { return pCrono.dimGrafoVisible(); }

    public int[] dimGrafoVisibleEstructura() { return pEstructura.dimGrafoVisible(); }

    public int[] posicPanelPrincipal() {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = (int)this.jspArbol.getLocationOnScreen().getX();
        arrayOfInt[1] = (int)this.jspArbol.getLocationOnScreen().getY();
        return arrayOfInt;
    }

    public JComponent getPanelArbol() { return this.jspArbol; }

    public void setVistaActiva(String paramString) {
        byte b;
        for (b = 0; b < this.panel1.getTabCount(); b++) {
            if (this.panel1.getTitleAt(b).equals(paramString)) {
                this.panel1.setSelectedIndex(b);
                panel1Pestana = b;
            }
        }
        for (b = 0; b < this.panel2.getTabCount(); b++) {
            if (this.panel2.getTitleAt(b).equals(paramString)) {
                this.panel2.setSelectedIndex(b);
                panel2Pestana = b;
            }
        }
    }

    public JComponent getPanelPorNombre(String paramString) { return paramString.equals(Texto.get(Vista.codigos[0], Conf.idioma)) ? pArbol : (paramString.equals(Texto.get(Vista.codigos[1], Conf.idioma)) ? pPila : ((paramString.equals(Texto.get(Vista.codigos[2], Conf.idioma)) && !Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) ? pTraza : ((paramString.equals(Texto.get(Vista.codigos[2], Conf.idioma)) && Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) ? pCrono : (paramString.equals(Texto.get(Vista.codigos[3], Conf.idioma)) ? pEstructura : (paramString.equals(Texto.get(Vista.codigos[4], Conf.idioma)) ? pGrafo : null))))); }

    public Object getGrafoPorNombre(String paramString) { return paramString.equals(Texto.get(Vista.codigos[0], Conf.idioma)) ? pArbol.getGrafo() : (paramString.equals(Texto.get(Vista.codigos[1], Conf.idioma)) ? pPila.getGrafo() : ((paramString.equals(Texto.get(Vista.codigos[2], Conf.idioma)) && Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) ? pCrono.getGrafo() : (paramString.equals(Texto.get(Vista.codigos[3], Conf.idioma)) ? pEstructura.getGrafo() : (paramString.equals(Texto.get(Vista.codigos[4], Conf.idioma)) ? pGrafo.getGrafo() : null)))); }

    public Object getGrafoPorNumero(int paramInt) {
        switch (paramInt) {
            case 0:
                return pArbol.getGrafo();
            case 1:
                return pPila.getGrafo();
            case 3:
                return pCrono.getGrafo();
            case 4:
                return pEstructura.getGrafo();
            case 5:
                return pGrafo.getGrafo();
        }
        return null;
    }

    public int[] getDimPanelPorNombre(String paramString) {
        int[] arrayOfInt = new int[2];
        if (paramString.equals(Texto.get(Vista.codigos[0], Conf.idioma))) {
            arrayOfInt = dimPanelArbol();
        } else if (paramString.equals(Texto.get(Vista.codigos[1], Conf.idioma))) {
            arrayOfInt = dimPanelPila();
        } else if (paramString.equals(Texto.get(Vista.codigos[2], Conf.idioma)) && !Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
            arrayOfInt = dimPanelTraza();
        } else if (paramString.equals(Texto.get(Vista.codigos[2], Conf.idioma)) && Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
            arrayOfInt = dimPanelCrono();
        } else if (paramString.equals(Texto.get(Vista.codigos[3], Conf.idioma))) {
            arrayOfInt = dimPanelEstructura();
        }
        return arrayOfInt;
    }

    public int[] getPosicPanelPorNombre(String paramString) {
        int[] arrayOfInt = new int[2];
        if (paramString.equals(Texto.get(Vista.codigos[0], Conf.idioma))) {
            arrayOfInt[0] = (int)this.jspArbol.getLocationOnScreen().getX();
            arrayOfInt[1] = (int)this.jspArbol.getLocationOnScreen().getY();
        } else if (paramString.equals(Texto.get(Vista.codigos[1], Conf.idioma))) {
            arrayOfInt[0] = (int)jspPila.getLocationOnScreen().getX();
            arrayOfInt[1] = (int)jspPila.getLocationOnScreen().getY();
        } else if (paramString.equals(Texto.get(Vista.codigos[2], Conf.idioma)) && !Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
            arrayOfInt[0] = (int)jspTraza.getLocationOnScreen().getX();
            arrayOfInt[1] = (int)jspTraza.getLocationOnScreen().getY();
        } else if (paramString.equals(Texto.get(Vista.codigos[2], Conf.idioma)) && Arrays.contiene(1112, Ventana.thisventana.getTraza().getTecnicas())) {
            arrayOfInt[0] = (int)jspCrono.getLocationOnScreen().getX();
            arrayOfInt[1] = (int)jspCrono.getLocationOnScreen().getY();
        } else if (paramString.equals(Texto.get(Vista.codigos[3], Conf.idioma))) {
            arrayOfInt[0] = (int)jspEstructura.getLocationOnScreen().getX();
            arrayOfInt[1] = (int)jspEstructura.getLocationOnScreen().getY();
        } else if (paramString.equals(Texto.get(Vista.codigos[4], Conf.idioma))) {
            arrayOfInt[0] = (int)jspGrafo.getLocationOnScreen().getX();
            arrayOfInt[1] = (int)jspGrafo.getLocationOnScreen().getY();
        }
        return arrayOfInt;
    }

    public void setTextoCompilador(String paramString) { pCompilador.setTextoCompilador(paramString); }

    public void guardarClase() throws Exception { pCodigo.guardar(); }

    protected PanelCodigo getPanelCodigo() { return pCodigo; }

    public void cerrar() throws Exception {
        cerrarVistas();
        if (Ventana.thisventana.getClase() == null)
            cerrarPanelCodigo();
    }

    public boolean getEditable() { return pCodigo.getEditable(); }

    private void quitarBordesJSP() throws Exception {
        jspCompilador.setBorder(new EmptyBorder(false, false, false, false));
        this.jspArbol.setBorder(new EmptyBorder(false, false, false, false));
        jspTraza.setBorder(new EmptyBorder(false, false, false, false));
        jspPila.setBorder(new EmptyBorder(false, false, false, false));
        jspCrono.setBorder(new EmptyBorder(false, false, false, false));
        jspEstructura.setBorder(new EmptyBorder(false, false, false, false));
        jspGrafo.setBorder(new EmptyBorder(false, false, false, false));
    }

    public String[] getNombreVistasDisponibles() {
        String[] arrayOfString = new String[this.panel1.getTabCount() + this.panel2.getTabCount()];
        int i;
        for (i = 0; i < this.panel1.getTabCount(); i++)
            arrayOfString[i] = this.panel1.getTitleAt(i);
        for (i = 0; i < this.panel2.getTabCount(); i++)
            arrayOfString[i + this.panel1.getTabCount()] = this.panel2.getTitleAt(i);
        return arrayOfString;
    }

    public String[] getNombreVistasVisibles() {
        String[] arrayOfString = new String[2];
        byte b;
        for (b = 0; b < this.panel1.getTabCount(); b++) {
            if (this.panel1.getSelectedIndex() == b)
                arrayOfString[0] = this.panel1.getTitleAt(b);
        }
        for (b = 0; b < this.panel2.getTabCount(); b++) {
            if (this.panel2.getSelectedIndex() == b)
                arrayOfString[1] = this.panel2.getTitleAt(b);
        }
        return arrayOfString;
    }

    public int[] getCodigoVistasVisibles() {
        String[] arrayOfString1 = getNombreVistasVisibles();
        String[] arrayOfString2 = new String[arrayOfString1.length];
        int[] arrayOfInt = new int[arrayOfString1.length];
        for (byte b = 0; b < arrayOfString2.length; b++) {
            arrayOfString2[b] = Texto.getCodigo(arrayOfString1[b], Conf.idioma);
            arrayOfInt[b] = Vista.getPosic(arrayOfString2[b]);
        }
        return arrayOfInt;
    }

    public void vistaGrafoDependenciaVisible(DatosMetodoBasicos paramDatosMetodoBasicos) {
        boolean bool = FamiliaEjecuciones.getInstance().estaHabilitado();
        if (!grafoActivado.booleanValue() || (pGrafo != null && !pGrafo.esIgual(paramDatosMetodoBasicos))) {
            try {
                if (Conf.getVista(Vista.codigos[4]).getPanel() == 1 || bool) {
                    this.panel1.remove(this.contenedorGrafo);
                } else {
                    this.panel2.remove(this.contenedorGrafo);
                }
                this.contenedorGrafo = null;
                pGrafo = null;
                jspGrafo = null;
                nyp = null;
                this.mostrarNombreMetodos = (Ventana.thisventana.traza.getNumMetodos() != 1);
                if (this.mostrarNombreMetodos) {
                    nyp = new NombresYPrefijos();
                    this.nombresMetodos = Ventana.thisventana.trazaCompleta.getNombresMetodos();
                    String[] arrayOfString = ServiciosString.obtenerPrefijos(this.nombresMetodos);
                    for (byte b = 0; b < this.nombresMetodos.length; b++)
                        nyp.add(this.nombresMetodos[b], arrayOfString[b]);
                }
                pGrafo = new PanelGrafo(paramDatosMetodoBasicos, Ventana.thisventana, nyp);
                nyp = null;
                jspGrafo = new JScrollPane(pGrafo);
                this.contenedorGrafo = new JPanel();
                this.contenedorGrafo.setLayout(new BorderLayout());
                this.contenedorGrafo.add(jspGrafo, "Center");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (Conf.getVista(Vista.codigos[4]).getPanel() == 1 || bool) {
                this.panel1.add(Texto.get(Vista.codigos[4], Conf.idioma), this.contenedorGrafo);
            } else {
                this.panel2.add(Texto.get(Vista.codigos[4], Conf.idioma), this.contenedorGrafo);
            }
            zoomAjusteGrafoInicial();
        }
        setVistaActiva(Texto.get(Vista.codigos[4], Conf.idioma));
        grafoActivado = Boolean.valueOf(true);
    }

    public void vistaGrafoDependenciaVisible(List<DatosMetodoBasicos> paramList) {
        boolean bool = FamiliaEjecuciones.getInstance().estaHabilitado();
        if (!grafoActivado.booleanValue() || (pGrafo != null && !pGrafo.esIgual(paramList))) {
            try {
                if (Conf.getVista(Vista.codigos[4]).getPanel() == 1 || bool) {
                    this.panel1.remove(this.contenedorGrafo);
                } else {
                    this.panel2.remove(this.contenedorGrafo);
                }
                this.contenedorGrafo = null;
                pGrafo = null;
                jspGrafo = null;
                nyp = null;
                this.mostrarNombreMetodos = (Ventana.thisventana.traza.getNumMetodos() != 1);
                if (this.mostrarNombreMetodos) {
                    nyp = new NombresYPrefijos();
                    this.nombresMetodos = Ventana.thisventana.trazaCompleta.getNombresMetodos();
                    String[] arrayOfString = ServiciosString.obtenerPrefijos(this.nombresMetodos);
                    for (byte b = 0; b < this.nombresMetodos.length; b++)
                        nyp.add(this.nombresMetodos[b], arrayOfString[b]);
                }
                pGrafo = new PanelGrafo(paramList, Ventana.thisventana, nyp);
                nyp = null;
                jspGrafo = new JScrollPane(pGrafo);
                this.contenedorGrafo = new JPanel();
                this.contenedorGrafo.setLayout(new BorderLayout());
                this.contenedorGrafo.add(jspGrafo, "Center");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (Conf.getVista(Vista.codigos[4]).getPanel() == 1 || bool) {
                this.panel1.add(Texto.get(Vista.codigos[4], Conf.idioma), this.contenedorGrafo);
            } else {
                this.panel2.add(Texto.get(Vista.codigos[4], Conf.idioma), this.contenedorGrafo);
            }
            zoomAjusteGrafoInicial();
        }
        setVistaActiva(Texto.get(Vista.codigos[4], Conf.idioma));
        grafoActivado = Boolean.valueOf(true);
    }

    public static Boolean getGrafoActivado() { return grafoActivado; }

    public void stateChanged(ChangeEvent paramChangeEvent) {
        if (!this.abriendoVistas)
            actualizar();
    }

    public void componentResized(ComponentEvent paramComponentEvent) {
        if (paramComponentEvent.getSource() == FamiliaEjecuciones.getInstance().obtenerPanelEjecuciones()) {
            int i = (Conf.disposicionPaneles == 2) ? separadorVistas.getHeight() : separadorVistas.getHeight();
            if (separadorVistas.getDividerLocation() < i - 8 - 1)
                actualizarFamiliaEjecuciones(false);
        }
    }

    public void componentMoved(ComponentEvent paramComponentEvent) {}

    public void componentShown(ComponentEvent paramComponentEvent) {}

    public void componentHidden(ComponentEvent paramComponentEvent) {}

    private void zoomAjusteGrafoInicial() throws Exception {
        jspGrafo.getTopLevelAncestor().validate();
        int[] arrayOfInt = dimPanelYGrafoDep();
        int i = arrayOfInt[0];
        int j = arrayOfInt[1];
        int k = arrayOfInt[2];
        int m = arrayOfInt[3];
        double d1 = i / k;
        double d2 = j / m;
        double d3 = Math.min(d1, d2);
        int n = 0;
        if (k > i || m > j) {
            n = (int)(d3 * 100.0D) - 100;
        } else {
            n = (int)((d3 - 1.0D) * 100.0D) - 2;
        }
        pGrafo.refrescarZoom(n);
    }

    private void recordarPestanaPaneles() throws Exception {
        if (panel1Pestana < this.panel1.getTabCount()) {
            this.panel1.setSelectedIndex(panel1Pestana);
        } else {
            if (this.panel1 != null && this.panel1.getTabCount() > 0)
                this.panel1.setSelectedIndex(0);
            panel1Pestana = 0;
        }
        if (panel2Pestana < this.panel2.getTabCount()) {
            this.panel2.setSelectedIndex(panel2Pestana);
        } else {
            if (this.panel2 != null && this.panel2.getTabCount() > 0)
                this.panel2.setSelectedIndex(0);
            panel2Pestana = 0;
        }
    }

    public void subrayarLineaEditor(int paramInt) {
        getPanelCodigo().subrayarLineaEditor(paramInt);
        getPanelCodigo().focusLinea(paramInt);
    }

    public void removeSelects() throws Exception { getPanelCodigo().removeSelects(); }

    public void changeTheme(int paramInt) { getPanelCodigo().changeTheme(paramInt); }

    public JScrollPane getJSPCodigo() { return jspCodigo; }

    private void anadirMouseEventPaneles() throws Exception {
        this.panel1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent param1MouseEvent) { panel1Pestana = PanelAlgoritmo.this.panel1.getSelectedIndex(); }

            public void mousePressed(MouseEvent param1MouseEvent) {}

            public void mouseReleased(MouseEvent param1MouseEvent) {}

            public void mouseEntered(MouseEvent param1MouseEvent) {}

            public void mouseExited(MouseEvent param1MouseEvent) {}
        });
        this.panel2.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent param1MouseEvent) { panel2Pestana = PanelAlgoritmo.this.panel2.getSelectedIndex(); }

            public void mousePressed(MouseEvent param1MouseEvent) {}

            public void mouseReleased(MouseEvent param1MouseEvent) {}

            public void mouseEntered(MouseEvent param1MouseEvent) {}

            public void mouseExited(MouseEvent param1MouseEvent) {}
        });
    }
}
