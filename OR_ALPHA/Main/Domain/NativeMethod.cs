using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.Domain
{
    public class NativeMethod: Method
    {
        [XmlElement("EXECUTIONPATH")]
        public string ExecutionPath { get; set; }

        public override void Execute()
        {
            ProcessStartInfo startInfo = new ProcessStartInfo(ExecutionPath);
            startInfo.WorkingDirectory = ExecutionPath.Substring(0, ExecutionPath.LastIndexOf('\\'));
            Process.Start(startInfo);
        }
    }
}
