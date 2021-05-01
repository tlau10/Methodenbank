using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OrAlpha.Main.DomainVisualOR
{
    public interface IElement
    {
        string HtmlFile { get; set; }
        string Name { get; set; }
    }
}
