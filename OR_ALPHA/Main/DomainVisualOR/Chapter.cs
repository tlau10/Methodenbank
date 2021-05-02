using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.DomainVisualOR
{
    public class Chapter : ChapterContainer
    {
        [XmlArray("CHAPTERS")]
        [XmlArrayItem(typeof(Chapter), ElementName = "CHAPTER")]
        public List<Chapter> ChapterList
        {
            get { return chapters; }
            set { chapters = value; }
        }

    }
}
