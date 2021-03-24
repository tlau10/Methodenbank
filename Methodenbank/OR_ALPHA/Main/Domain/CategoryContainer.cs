using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.Domain
{
    
    public abstract class CategoryContainer : IMethodbaseElement
    {
        [XmlElement("HTMLFILE")]
        public string HtmlFile { get; set; }

        [XmlAttribute("Name", DataType = "string")]
        public string Name { get; set; }

        protected List<Category> categories = new List<Category>();

        public IList<Category> Categories
        {
            get { return new ReadOnlyCollection<Category>(categories); }
        }

        public void AddCategory(Category category)
        {
            categories.Add(category);
        }

        public void RemoveCategory(string categoryName)
        {
            foreach(Category category in new List<Category>(categories))
                if(category.Name.Equals(categoryName))
                 categories.Remove(category);
        }

        public Category FindCategoryByName(string name)
        {
            foreach (Category category in Categories)
            {
                if (category.Name.Equals(name))
                    return category;
                else if (category.FindCategoryByName(name) != null)
                    return category.FindCategoryByName(name);
            }
            return null;
        }

        public IList<Category> GetAllCategories()
        {
            List<Category> allCategories = new List<Category>();
            allCategories.AddRange(Categories);
            foreach (Category category in Categories)
                allCategories.AddRange(category.GetAllCategories());
            return allCategories;
        }

    }
}
