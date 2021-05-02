using OrAlpha.Main.DomainMethodenbank;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace OrAlpha.Main.Repository
{
    public class MethodRepositoryImpl : IMethodRepository
    {
        private XmlSerializer serializer = new XmlSerializer(typeof(Methodbase));
        private Methodbase methodbase;

        public Boolean TestMode { get; set; }

        public MethodRepositoryImpl()
        {
            LoadXmlFile();
        }

        public void SaveMethod(Method method, string parentCategory)
        {
            AddMethodToParentCategory(method, parentCategory);
            SaveXmlFile();
        }

        private void AddMethodToParentCategory(Method method, string parentCategory)
        {
            Category parent = methodbase.FindCategoryByName(parentCategory);
            if (parent != null)
                parent.AddMethod(method);
        }

        public void SaveCategory(Category category, string parentCategory)
        {
            AddCategoryToContainer(category, parentCategory);
            SaveXmlFile();
        }

        private void AddCategoryToContainer(Category category, string parentCategory)
        {
            CategoryContainer parent = methodbase.FindCategoryContainerByName(parentCategory);
            if (parent != null)
                parent.AddCategory(category);
        }

        public Methodbase LoadMethodbase()
        {
            LoadXmlFile();
            return methodbase;
        }

        public Method FindMethodByName(string methodName)
        {
            return methodbase.FindMethodByName(methodName);
        }

        public Category FindCategoryByName(string categoryName)
        {
            return methodbase.FindCategoryByName(categoryName);
        }

        public CategoryContainer FindCategoryContainerByName(string containerName)
        {
            return methodbase.FindCategoryContainerByName(containerName);
        }


        public void DeleteMethodFromCategory(string methodName, string categoryName)
        {
            Category category = methodbase.FindCategoryByName(categoryName);
            if (category != null)
                category.RemoveMethod(methodName);
            SaveXmlFile();
        }

        public void DeleteCategory(string categoryName, string parentCategoryName)
        {
            CategoryContainer container = methodbase.FindCategoryContainerByName(parentCategoryName);
            if (container != null)
                container.RemoveCategory(categoryName);
            SaveXmlFile();
        }

        public void UpdateCategory(string categoryName, Category updatedCategory)
        {
            Category category = methodbase.FindCategoryByName(categoryName);
            if (category != null)
                MapUpdatedCategoryToOriginalCategory(updatedCategory, category);
            SaveXmlFile();
        }

        private void MapUpdatedCategoryToOriginalCategory(Category updatedCategory, Category category)
        {
            category.Name = updatedCategory.Name;
            category.HtmlFile = updatedCategory.HtmlFile;
        }

        public void ChangePassword(string newPassword)
        {
            methodbase.ChangeAdminPassword(newPassword);
            SaveXmlFile();
        }

        private void LoadXmlFile()
        {
            if (!TestMode)
            {
                StreamReader reader = new StreamReader(Properties.Settings.Default.XmlFilePath);
                methodbase = (Methodbase)serializer.Deserialize(reader);
                reader.Close();
            }
           
        }

        private void SaveXmlFile()
        {
            if (!TestMode)
            {
                StreamWriter writer = new StreamWriter(Properties.Settings.Default.XmlFilePath);
                serializer.Serialize(writer, methodbase);
                writer.Close();
            }
        }
    }
}
