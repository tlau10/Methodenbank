package de.fh_konstanz.simubus.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import sun.awt.image.ToolkitImage;
import sun.awt.image.URLImageSource;
import de.fh_konstanz.simubus.util.ImageUtil;

public class ReportFrame extends JFrame implements TreeSelectionListener
{

   private static final long      serialVersionUID   = -4810698480560188558L;

   private DefaultMutableTreeNode top                = null;

   private File                   filePath           = null;

   JTree                          menuTree           = null;

   private JSplitPane             reportPanel        = null;

   private JScrollPane            reportMenuPanel    = null;

   private JScrollPane            reportContentPanel = null;

   private JEditorPane            reportContentPane  = null;

   private JTabbedPane            pane               = null;

   private static ReportFrame     instance;

   public static ReportFrame getInstance( Rectangle d )
   {
      if ( instance == null )
         instance = new ReportFrame( d );
      return instance;
   }

   private ReportFrame( Rectangle d )
   {
      super( "Reports" );

      this
            .setIconImage( new ToolkitImage( new URLImageSource( ImageUtil.getImageUrl( "haltestelle.png" ) ) ) );

      this.setLayout( new BorderLayout() );
      this.setPreferredSize( new Dimension( 800, 600 ) );
      this.setSize( new Dimension( 800, 600 ) );
      this.setBounds( d.x + d.width / 2 - 400, d.y + d.height / 2 - 300, 800, 600 );

      this.reportContentPane = new JEditorPane();
      this.reportContentPane.setEditable( false );

      this.reportContentPanel = new JScrollPane( this.reportContentPane );
      reportContentPanel.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
      reportContentPanel.setVisible( true );
      top = new DefaultMutableTreeNode( "" );
      // filePath = new File("c:" + File.separator + "temp" + File.separator);
      filePath = new File( "reports" );
      this.createReportHierarchie( top, filePath );
      menuTree = new JTree( top );

      menuTree.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );
      menuTree.addTreeSelectionListener( this );

      this.reportMenuPanel = new JScrollPane( menuTree );
      this.reportMenuPanel.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

      this.reportPanel = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
      this.reportPanel.setDividerLocation( 250 );
      this.reportPanel.setLeftComponent( this.reportMenuPanel );
      this.reportPanel.setRightComponent( this.reportContentPanel );

      this.add( reportPanel );
      this.pack();
   }

   public void createReportHierarchie()
   {
      this.createReportHierarchie( top, filePath );
      expandAll( menuTree, true );
   }

   public void expandAll( JTree tree, boolean expand )
   {
      DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();

      // Traverse tree from root
      expandAll( tree, new TreePath( root ), expand );
   }

   private void expandAll( JTree tree, TreePath parent, boolean expand )
   {
      // Traverse children
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getLastPathComponent();
      if ( node.getChildCount() >= 0 )
      {
         for ( Enumeration e = node.children(); e.hasMoreElements(); )
         {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();
            TreePath path = parent.pathByAddingChild( n );
            expandAll( tree, path, expand );
         }
      }

      // Expansion or collapse must be done bottom-up
      if ( expand )
      {
         tree.expandPath( parent );
      }
      else
      {
         tree.collapsePath( parent );
      }
   }

   private void createReportHierarchie( DefaultMutableTreeNode top, File file )
   {
      DefaultMutableTreeNode currentHead = top;
      File[] files = file.listFiles( new FilenameFilter()
      {
         public boolean accept( File dir, String name )
         {
            if ( name.toLowerCase().endsWith( ".html" ) || dir.isDirectory() )
               return true;
            return false;
         }
      } );
      if ( files != null )
      {
         for ( int i = 0; i < files.length; i++ )
         {
            if ( files[ i ].canRead() )
            {
               if ( files[ i ].isDirectory() )
               {
                  DefaultMutableTreeNode entry = new DefaultMutableTreeNode( files[ i ].getName() );
                  currentHead.add( entry );
                  this.createReportHierarchie( entry, files[ i ] );
               }
               else
               {
                  currentHead.add( new DefaultMutableTreeNode( files[ i ].getName().toString() ) );
               }
            }
         }
      }
   }

   private void setPage( String page, JEditorPane target )
   {
      File f = new File( page );
      try
      {
         target.setPage( f.toURL() );
         this.repaint();
      }
      catch ( IOException e )
      {
         System.err.println( "Attempted to read a bad URL: " );
      }
   }

   public void valueChanged( TreeSelectionEvent e )
   {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) ( (JTree) e.getSource() )
            .getLastSelectedPathComponent();
      if ( node == null )
         return;

      Object nodeInfo = node.getUserObject();
      if ( node.isLeaf() )
      {
         DefaultMutableTreeNode pnode = node;
         File reports = new File( "reports" );

         // String page = "c:/temp";
         String page = reports.getAbsolutePath();

         String[] parents = new String[node.getLevel()];
         for ( int i = 1; i <= node.getLevel(); i++ )
         {
            parents[ node.getLevel() - i ] = ( (DefaultMutableTreeNode) pnode.getParent() ).getUserObject()
                  .toString();
            pnode = ( (DefaultMutableTreeNode) pnode.getParent() );
         }
         for ( int i = 0; i < node.getLevel(); i++ )
         {

            page = page + "/" + parents[ i ];
         }
         page = page + "/" + nodeInfo.toString();
         page = page.replaceAll( "//", "/" );
         this.setPage( page, this.reportContentPane );
      }
      else
      {
         DefaultMutableTreeNode pnode = node;
         File reports = new File( "reports" );

         // String page = "c:/temp";
         String page = reports.getAbsolutePath();
         String[] parents = new String[node.getLevel()];
         for ( int i = 1; i <= node.getLevel(); i++ )
         {
            parents[ node.getLevel() - i ] = ( (DefaultMutableTreeNode) pnode.getParent() ).getUserObject()
                  .toString();
            pnode = ( (DefaultMutableTreeNode) pnode.getParent() );
         }
         for ( int i = 0; i < node.getLevel(); i++ )
         {
            page = page + "/" + parents[ i ];
         }
         // page = page + "/" + nodeInfo.toString() + "/index.html";
         page = page.replaceAll( "//", "/" );
         this.setPage( page, this.reportContentPane );
      }
   }

   /**
    * 
    * @param index
    */
   public void setSelectedPage( int index )
   {
      this.pane.setSelectedIndex( index );
   }
}