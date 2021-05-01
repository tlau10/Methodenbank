using OrAlpha.Main.Util;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.Domain
{
    public class AdminUser
    {
        private const string defaultUserName = "admin";
        private const string defaultPassword = "password";

        private string name = Sha256Util.GetHashValue(defaultUserName);
        private string password = Sha256Util.GetHashValue(defaultPassword);

        [XmlIgnore]
        public string Name { 
            get { return name; }
            set { this.name = Sha256Util.GetHashValue(value); }
        }

        [XmlIgnore]
        public string Password 
        { 
            get { return password; }
            set { this.password = Sha256Util.GetHashValue(value);  } 
        }

        [XmlElement("USERNAME")]
        public string _DoNotUse_Name
        {
            get { return name; }
            set { this.name = value; }
        }

        [XmlElement("PASSWORD")]
        public string _DoNotUse_Password
        {
            get { return password; }
            set { this.password = value; }
        }

        public bool Authenticate(string adminUserName, string adminPassword)
        {
            return (Sha256Util.GetHashValue(adminUserName).Equals(name) &&
                Sha256Util.GetHashValue(adminPassword).Equals(password));
        }
    }
}
