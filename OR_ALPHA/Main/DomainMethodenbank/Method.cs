using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.DomainMethodenbank
{
    [XmlInclude(typeof(NativeMethod))]
    [XmlInclude(typeof(WebMethod))]
    public abstract class Method: IMethodbaseElement
    {
        [XmlAttribute("Name", DataType = "string")]
        public string Name { get; set; }

        [XmlElement("DOCUMENTATIONPATH")]
        public string DocumentationPath { get; set; }

        [XmlElement("HTMLFILE")]
        public string HtmlFile { get; set; }

        public abstract void Execute();
    }
}
