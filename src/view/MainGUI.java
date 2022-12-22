package view;

import java.awt.Container;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

public class MainGUI extends JFrame{
	private JSplitPane mainSplit;
	private JSplitPane lateralSplit;

    private JScrollPane objectListScroll;
	private ObjectList objectList;

    private JTabbedPane propertiesTab;
	private RenderTab renderTab;
	private SceneTab sceneTab;
    private ObjectTab objectTab;

    private PreviewPanel previewPanel;

    private InformationLabel InformationLabel;

    public MainGUI() {
        super("JRenderEngine");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.creteGUI();
        this.objectList.updateListFromModel(); // update the list for the first time
        pack();
    }

    private void creteGUI() {

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // Create the menu
        this.setJMenuBar(new MenuBar());

        // Create the tabbed properties pane
        this.propertiesTab = new JTabbedPane();
        this.sceneTab = new SceneTab();
        this.renderTab = new RenderTab();
        this.objectTab = new ObjectTab(null, false);
        this.propertiesTab.addTab("Scene", this.sceneTab);
        this.propertiesTab.addTab("Render", this.renderTab);
        this.propertiesTab.addTab("Object", this.objectTab);

        // Create preview panel
        this.previewPanel = new PreviewPanel();

        // Create object list
        this.objectList = new ObjectList();
        this.objectListScroll = new JScrollPane(objectList);

        // Create lateral split
        this.lateralSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.objectListScroll, this.propertiesTab);

        // Create main split
        this.mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.lateralSplit, this.previewPanel);

        // Create information label
        this.InformationLabel = new InformationLabel();

        // Set main gui
        this.add(this.mainSplit, BorderLayout.CENTER);
        this.add(InformationLabel, BorderLayout.SOUTH);

        //System.out.println("GUI created");
    }   

    public ObjectList getObjectList() {
        return this.objectList;
    }

	public PreviewPanel getPreviewPanel() {
		return this.previewPanel;
	}

	public void setObjectTab(ObjectTab ot) {
        //System.out.println("Object tab updated to current selected object");
        if(ot == null){
            //System.out.println("ot is null");
            this.objectTab = new ObjectTab(null, false);
        }
        else{
            this.objectTab = ot;
            this.propertiesTab.setComponentAt(2, ot);
        }
        revalidate();
        repaint();
	}

    public RenderTab getRenderTab() {
        return this.renderTab;
    }

    public InformationLabel getInformationLabel() {
        return this.InformationLabel;
    }

}
