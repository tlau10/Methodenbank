using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.DomainVisualOR
{
    [XmlRoot("BASE")]
    public class VisualORBase : ChapterContainer
    {
        public const String VisualORBase_Name = "Visual-OR";
      
        [XmlArray("CHAPTERS")]
        [XmlArrayItem(typeof(Chapter), ElementName = "CHAPTER")]
        public List<Chapter> ChapterList
        {
            get { return chapters; }
            set { chapters = value; }
        }

        public ChapterContainer FindChapterContainerByName(String name)
        {
            if (Name.Equals(name))
                return this;
            return FindChapterByName(name);
        }

    }
}
