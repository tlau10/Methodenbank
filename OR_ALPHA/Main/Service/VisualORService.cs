using OrAlpha.Main.DomainVisualOR;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Xml.Serialization;

namespace OrAlpha.Main.Service
{
    public class VisualORService
    {

        private String visualORTreeview = Properties.Settings.Default.VisualORTreeview;

        private XmlSerializer serializer = new XmlSerializer(typeof(VisualORBase));
        private VisualORBase baseOR;


        public VisualORBase loadVisualORTree()
        {
            LoadXmlFile();
            return baseOR;
        }

        public void LoadXmlFile()
        {
            StreamReader reader = new StreamReader(visualORTreeview);
            baseOR = (VisualORBase) serializer.Deserialize(reader);     
            reader.Close();
        }

        public TreeNode CreateTreeView()
        {
            TreeNode baseTree = new TreeNode(VisualORBase.VisualORBase_Name);
            IList<Chapter> parentChapters = loadVisualORTree().Chapters;
            foreach (Chapter chapter in parentChapters)
            {
                baseTree.Nodes.Add(CreateChapterNodes(chapter));
            }
            return baseTree;
        }

        public TreeNode CreateChapterNodes(ChapterContainer parentNode)
        {
            TreeNode node = new TreeNode(parentNode.Name);
            IList<Chapter> chapterNodes = parentNode.Chapters;
            foreach (Chapter chapter in chapterNodes)
            {
                node.Nodes.Add(CreateChapterNodes(chapter));
            }
            return node;
        }


        public IElement FindElementByName(String name)
        {

            if (baseOR.FindChapterContainerByName(name) != null) 
            { 
                return baseOR.FindChapterContainerByName(name);
            }
            return null;
        }

        public static VisualORService NewInstance()
        {
            return new VisualORService();
        }
    }
}
