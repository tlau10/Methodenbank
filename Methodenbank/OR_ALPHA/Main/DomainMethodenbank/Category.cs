using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.DomainMethodenbank
{
    public class Category: CategoryContainer
    {
        
        private List<Method> methods = new List<Method>();

        public IList<Method> Methods 
        {
            get { return new ReadOnlyCollection<Method>(methods); }
        }

        [XmlArray("METHODS")]
        [XmlArrayItem(typeof(NativeMethod), ElementName = "NATIVEMETHOD")]
        [XmlArrayItem(typeof(WebMethod), ElementName = "WEBMETHOD")]
        public List<Method> _DoNotUse_Methods
        {
            get { return methods; }
            set { methods = value; }
        }

        [XmlArray("CATEGORIES")]
        [XmlArrayItem(typeof(Category), ElementName = "CATEGORY")]
        public List<Category> _DoNotUse_Categories
        {
            get { return categories; }
            set { categories = value; }
        }

        public void AddMethod(Method method)
        {
            methods.Add(method);
        }

        public Method FindMethodByName(string name)
        {
            foreach (Method method in methods)
                if (name.Equals(method.Name))
                    return method;
            foreach (Category category in categories)
                if (category.FindMethodByName(name) != null)
                    return category.FindMethodByName(name);
            return null;
        }

        public void RemoveMethod(string methodName)
        {
            foreach (Method method in new List<Method>(methods))
                if (method.Name.Equals(methodName))
                    methods.Remove(method);
        }

    }
}
