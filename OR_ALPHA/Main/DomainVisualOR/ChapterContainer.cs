using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.DomainVisualOR
{
    public class ChapterContainer : IElement
    {
        [XmlAttribute("Name", DataType = "string")]
        public string Name { get; set; }

        [XmlElement("HTMLFILE")]
        public string HtmlFile { get; set; }


        protected List<Chapter> chapters = new List<Chapter>();

        public IList<Chapter> Chapters
        {
            get { return new ReadOnlyCollection<Chapter>(chapters); }
        }

        public Chapter FindChapterByName(String name)
        {
            foreach (Chapter chapter in Chapters)
            {
                if (chapter.Name.Equals(name))             
                    return chapter;
                else if(chapter.FindChapterByName(name) != null) 
                    return chapter.FindChapterByName(name);         
            }
            return null;
        }
    }
}
