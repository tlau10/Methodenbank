using OrAlpha.Main.DomainMethodenbank;
using OrAlpha.Main.Repository;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Windows.Forms;


namespace OrAlpha.Main.Service
{
    public class MethodService
    {
        private IMethodRepository methodRepository;

        public MethodService(IMethodRepository methodRepository)
        {
            this.methodRepository = methodRepository;
        }

        public void CreateNewMethod(Method method, string category)
        {
            methodRepository.SaveMethod(method, category);
        }

        public void CreateNewCategory(Category category, string parentCategory)
        {
            methodRepository.SaveCategory(category, parentCategory);
        }

        public IList<String> GetAllCategoryNames()
        {
            IList<String> allCategoryNames = new List<String>();
            foreach (Category category in methodRepository.LoadMethodbase().GetAllCategories())
                allCategoryNames.Add(category.Name);
            return allCategoryNames;
        }

        public IList<string> GetAllCategoryContainerNames()
        {
            IList<string> categories = GetAllCategoryNames();
            categories.Add(Methodbase.MethodbaseName);
            return categories;
        }

        public TreeNode GetCategoryTree()
        {
            TreeNode categoryTree = new TreeNode(Methodbase.MethodbaseName);
            foreach (Category category in methodRepository.LoadMethodbase().Categories)
                categoryTree.Nodes.Add(GetChildCategoryNodes(category));
            return categoryTree;
        }

        private TreeNode GetChildCategoryNodes(Category parentCategory)
        {
            TreeNode categoryNode = new TreeNode(parentCategory.Name);
            foreach (Category category in parentCategory.Categories)
                categoryNode.Nodes.Add(GetChildCategoryNodes(category));
            return categoryNode;
        }

        public TreeNode GetMethodTree()
        {
            TreeNode methodTree = new TreeNode(Methodbase.MethodbaseName);
            IList<Category> parentCategories = methodRepository.LoadMethodbase().Categories;
            foreach (Category category in parentCategories)
                methodTree.Nodes.Add(GetChildNodes(category));
            return methodTree;
        }

        private TreeNode GetChildNodes(Category parentCategory)
        {
            TreeNode returnNode = new TreeNode(parentCategory.Name);
            IList<Category> childCategories = parentCategory.Categories;
            foreach(Category category in childCategories)
                returnNode.Nodes.Add(GetChildNodes(category));
            foreach (Method method in parentCategory.Methods)
                returnNode.Nodes.Add(method.Name);
            return returnNode;
        }

        public IMethodbaseElement FindMethodbaseElementByName(String name)
        {
            if (methodRepository.FindMethodByName(name) != null)
                return methodRepository.FindMethodByName(name);
            if (methodRepository.FindCategoryContainerByName(name) != null)
                return methodRepository.FindCategoryContainerByName(name);
            return null;
        }

        public void ExecuteMethod(string methodName)
        {
            Method method = methodRepository.FindMethodByName(methodName);
            if (method != null)
                method.Execute();
        }

        public void OpenDocumentation(string methodName)
        {
            Method method = methodRepository.FindMethodByName(methodName);
            Process.Start(method.DocumentationPath);
        }

        public Category FindCategoryByName(string categoryName)
        {
            return methodRepository.FindCategoryByName(categoryName);
        }

        public void UpdateCategory(string categoryName, Category updatedCategory)
        {
            methodRepository.UpdateCategory(categoryName, updatedCategory);
        }

        public void MoveCategory(string categoryName, string sourceCategoryName, string destinationCategoryName)
        {
            CategoryContainer source = methodRepository.FindCategoryContainerByName(sourceCategoryName);
            CategoryContainer destination = methodRepository.FindCategoryContainerByName(destinationCategoryName);
            if (source != null && destination != null)
                MoveCategoryToOtherContainer(categoryName, source, destination);
        }

        private void MoveCategoryToOtherContainer(string categoryName, CategoryContainer source, CategoryContainer destination)
        {
            Category category = methodRepository.FindCategoryByName(categoryName);
            if (source.Categories.Contains(category))
            {
                methodRepository.DeleteCategory(category.Name, source.Name);
                methodRepository.SaveCategory(category, destination.Name);
            }
        }   

        public static MethodService NewInstance()
        {
            return new MethodService(new MethodRepositoryImpl());
        }

        public Method FindMethodByName(string methodName)
        {
            return methodRepository.FindMethodByName(methodName);
        }

        public void DeleteMethod(string methodName, string categoryName)
        {
            methodRepository.DeleteMethodFromCategory(methodName, categoryName);
        }

        public void DeleteCategory(string categoryName, string parentCategoryName)
        {
            methodRepository.DeleteCategory(categoryName, parentCategoryName);
        }

        public bool AuthenticateAdminUser(string userName, string password)
        {
            return methodRepository.LoadMethodbase().AuthenticateAdminUser(userName, password);
        }

        public void ChangeAdminPassword(string newPassword)
        {
            methodRepository.ChangePassword(newPassword);
        }
    }
}
