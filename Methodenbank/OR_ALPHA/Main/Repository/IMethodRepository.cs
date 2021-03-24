using OrAlpha.Main.DomainMethodenbank;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OrAlpha.Main.Repository
{
    public interface IMethodRepository
    {
        void SaveMethod(Method method, string parentCategory);
        void SaveCategory(Category category, string parentCategory);
        Methodbase LoadMethodbase();
        Method FindMethodByName(string methodName);
        Category FindCategoryByName(string categoryName);
        void DeleteMethodFromCategory(string methodName, string categoryName);
        void DeleteCategory(string categoryName, string parentCategoryName);
        CategoryContainer FindCategoryContainerByName(string containerName);
        void ChangePassword(string newPassword);
        void UpdateCategory(string categoryName, Category updatedCategory);
    }
}
