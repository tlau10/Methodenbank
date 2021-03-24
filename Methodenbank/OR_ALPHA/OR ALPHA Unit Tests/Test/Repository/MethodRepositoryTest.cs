using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.Repository;
using OrAlpha.Main.DomainMethodenbank;

namespace OrAlpha.Test.Repository
{
    [TestClass]
    public class MethodRepositoryTest
    {
        private const string methodName = "Iterator";
        private const string categoryName = "Interaktive Solver";
        private const string parentCategoryName = "Solver";
        private const string htmlFile = "example.html";

        private IMethodRepository repository;
        private Category category;

        [TestInitialize]
        public void SetUp()
        {
            repository = new MethodRepositoryImpl();
            ((MethodRepositoryImpl)repository).TestMode = true;
            category = FakeCategory();
        }

        [TestMethod]
        public void ShouldLoadMethodbase()
        {
            // when
            Methodbase methodbase = repository.LoadMethodbase();

            // then
            Assert.IsTrue(methodbase.GetType() == typeof(Methodbase));

        }

        [TestMethod]
        public void ShouldSaveNewMethod()
        {
            // given
            Method method = new NativeMethod();
            method.Name = methodName;

            // when
            repository.SaveMethod(method, parentCategoryName);
            Category category = repository.LoadMethodbase().FindCategoryByName(parentCategoryName);

            // then
            Assert.IsTrue(category.Methods.Contains(method));
        }

        [TestMethod]
        public void ShouldSaveNewRootCategory()
        {
            // when
            repository.SaveCategory(category, Methodbase.MethodbaseName);

            // then
            Assert.IsTrue(repository.LoadMethodbase().Categories.Contains(category));
        }

        [TestMethod]
        public void ShouldSaveNewSubCategory()
        {
           // when
            repository.SaveCategory(category, parentCategoryName);
           Category parentCategory = repository.LoadMethodbase().FindCategoryByName(parentCategoryName); 

           // then
           Assert.IsTrue(parentCategory.Categories.Contains(category));
        }

        [TestMethod]
        public void ShouldDeleteMethodFromCategory()
        {
            // when
            repository.DeleteMethodFromCategory(methodName, categoryName);

            // then
            Assert.IsNull(repository.FindMethodByName(methodName));
        }

        [TestMethod]
        public void ShouldDeleteCategoryFromParentCategory()
        {
            // when
            repository.DeleteCategory(categoryName, parentCategoryName);

            // then
            Assert.IsNull(repository.FindCategoryByName(categoryName));

        }

        [TestMethod]
        public void ShouldDeleteCategoryFromMethodbaseRoot()
        {
            // when
            repository.DeleteCategory(parentCategoryName, Methodbase.MethodbaseName);

            // then
            Assert.IsNull(repository.FindCategoryByName(parentCategoryName));
        }

        [TestMethod]
        public void ShouldUpdateCategory()
        {
            // when 
            repository.UpdateCategory(categoryName, FakeModifiedCategory());
        }

        private Category FakeCategory()
        {
            Category category = new Category();
            category.Name = categoryName;
            return category;
        }

        private Category FakeModifiedCategory()
        {
            Category category = new Category();
            category.Name = parentCategoryName;
            category.HtmlFile = htmlFile;
            return category;
        }
    }
}
