using OrAlpha.Main.Domain;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.Domain
{
    public class WebMethod : Method
    {
        [XmlElement("URL")]
        public string Url { get; set; }

        public override void Execute()
        {
            Process.Start(Url);
        }

    }
}
