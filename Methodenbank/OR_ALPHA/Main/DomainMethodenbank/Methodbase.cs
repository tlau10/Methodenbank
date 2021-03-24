using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.DomainMethodenbank
{
    [XmlRoot("METHODBASE")]
    public class Methodbase: CategoryContainer
    {
        public const string MethodbaseName = "Methodenbank";
        
        private AdminUser adminUser = new AdminUser();

        [XmlElement("ADMIN")]
        public AdminUser _DoNotUse_AdminUser
        {
            get { return adminUser;  }
            set { this.adminUser = value; }
        }

        public string AdminUserName 
        {
            set { adminUser.Name = value; } 
        }

        public string AdminPassword
        {
            set { adminUser.Password = value;  } 
        }

        [XmlArray("CATEGORIES")]
        [XmlArrayItem(typeof(Category), ElementName = "CATEGORY")]
        public List<Category> _DoNotUse_Categories
        {
            get { return categories; }
            set { categories = value; }
        }

        public Methodbase()
        {
            this.Name = MethodbaseName;
        }

        public Method FindMethodByName(string name)
        {
            foreach(Category category in categories) 
                if (category.FindMethodByName(name) != null)
                    return category.FindMethodByName(name);  
            return null;
        }


        public CategoryContainer FindCategoryContainerByName(string containerName)
        {
            if (Name.Equals(containerName))
                return this;
            return FindCategoryByName(containerName);
        }

        public bool AuthenticateAdminUser(string adminUserName, string adminPassword)
        {
            return adminUser.Authenticate(adminUserName, adminPassword);
        }

        public void ChangeAdminPassword(string newPassword)
        {
            adminUser.Password = newPassword;
        }
    }
}
