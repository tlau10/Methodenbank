using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.DomainMethodenbank;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrAlpha.Test.Domain
{
    [TestClass]
    public class CategoryContainerTest
    {
        private const string htmlFile = "example.html";
        private const string categoryName = "Interaktive Solver";

        private Category category;
        private Category subCategory;
        private CategoryContainer container;

        [TestInitialize]
        public void SetUp()
        {
            container = new CategoryContainerStub();
            category = FakeCategory();
            container.AddCategory(category);
            subCategory = new Category();
            category.AddCategory(subCategory);
        }

        private Category FakeCategory()
        {
            Category category = new Category();
            category.Name = categoryName;
            return category;
        }

        [TestMethod]
        public void ShouldSetHtmlFile()
        {
            // when
            container.HtmlFile = htmlFile;

            // then
            Assert.AreEqual(htmlFile, container.HtmlFile);
        }

        [TestMethod]
        public void ShouldSetName()
        {
            // when
            container.Name = categoryName;

            // then
            Assert.AreEqual(categoryName, container.Name);
        }

        [TestMethod]
        public void ShouldAddNewCategory()
        {
            Assert.IsTrue(container.Categories.Contains(category));
        }

        [TestMethod]
        public void ShouldRemoveCategory()
        {
            // when
            container.RemoveCategory(categoryName);

            // then
            Assert.IsNull(container.FindCategoryByName(categoryName));
        }

        [TestMethod]
        public void ShouldReturnAllCategories()
        {
            // when
            IList<Category> categories = container.GetAllCategories();

            // then
            Assert.IsTrue(categories.Contains(subCategory));
        }

    }

    class CategoryContainerStub : CategoryContainer
    {

    }
}
