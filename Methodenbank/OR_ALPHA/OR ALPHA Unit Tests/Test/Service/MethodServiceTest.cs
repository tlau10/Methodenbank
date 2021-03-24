using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.Repository;
using Moq;
using OrAlpha.Main.Service;
using OrAlpha.Main.DomainMethodenbank;
using OrAlpha.Test.Domain;

namespace OrAlpha.Test.Service
{
    [TestClass]
    public class MethodServiceTest
    {

        private const string categoryName = "Interaktive Solver";
        private const string methodName = "Iterator";
        private const string sourceCategoryName = "Solver";
        private const string destinationCategoryName = "Kategorie A";

        private Mock<IMethodRepository> methodRepository;
        private Method method;
        private Category category;
        private Methodbase methodbase;
        private MethodService service;
        

        [TestInitialize]
        public void SetUp() 
        {
            method = FakeMethod();
            category = FakeCategory();
            methodbase = FakeMethodbase();
            methodRepository = new Mock<IMethodRepository>();
            methodRepository.Setup(mock => mock.FindCategoryContainerByName(destinationCategoryName)).Returns(FakeDestinationCategory());
            methodRepository.Setup(mock => mock.FindCategoryByName(destinationCategoryName)).Returns(FakeDestinationCategory());
            methodRepository.Setup(mock => mock.FindCategoryByName(categoryName)).Returns(category);
            methodRepository.Setup(mock => mock.FindCategoryContainerByName(sourceCategoryName)).Returns(FakeSourceCategory());
            methodRepository.Setup(mock => mock.FindCategoryContainerByName(Methodbase.MethodbaseName)).Returns(methodbase);      
            methodRepository.Setup(mock => mock.FindCategoryByName(sourceCategoryName)).Returns(FakeSourceCategory());
            methodRepository.Setup(mock => mock.FindMethodByName(It.IsAny<string>())).Returns(method);
            service = new MethodService(methodRepository.Object);
        }

        private Methodbase FakeMethodbase()
        {
            Methodbase methodbase = new Methodbase();
            methodbase.AddCategory(category);
            return methodbase;
        }

        private Category FakeCategory()
        {
            Category category = new Category();
            category.Name = categoryName; 
            category.AddMethod(method);
            return category;
        }

        private Category FakeDestinationCategory()
        {
            Category category = new Category();
            category.Name = destinationCategoryName;
            return category;
        }

         private Category FakeSourceCategory()
        {
            Category sourceCategory = new Category();
            sourceCategory.Name = sourceCategoryName;
            sourceCategory.AddCategory(category);
            return sourceCategory;
        }


        private Method FakeMethod()
        {
            Method method = new NativeMethod();
            method.Name = methodName;
            return method;
        }

        [TestMethod]
        public void ShouldCreateNewMethod()
        {
            // when
            service.CreateNewMethod(method, categoryName);

            // then
            methodRepository.Verify(mock => mock.SaveMethod(method, categoryName));
        }

        [TestMethod]
        public void ShouldCreateNewCategory()
        {
            // given
            Category category = new Category();

            // when
            service.CreateNewCategory(category, categoryName);

            // then
            methodRepository.Verify(mock => mock.SaveCategory(category, categoryName));
        }

        [TestMethod]
        public void ShouldMoveCategoryToOtherParentCategory()
        {
            // when
            service.MoveCategory(categoryName, sourceCategoryName, destinationCategoryName);

            // then
            methodRepository.Verify(mock => mock.DeleteCategory(categoryName, sourceCategoryName));
            methodRepository.Verify(mock => mock.SaveCategory(category, destinationCategoryName));
        }

        [TestMethod]
        public void ShouldMoveCategoryToMethodbaseRoot()
        {
            // when 
            service.MoveCategory(categoryName, sourceCategoryName, methodbase.Name);

            // then
            methodRepository.Verify(mock => mock.DeleteCategory(categoryName, sourceCategoryName));
            methodRepository.Verify(mock => mock.SaveCategory(category, methodbase.Name));
        }

        [TestMethod]
        public void ShouldMoveCategoryFromMethodbaseRoot()
        {
            // given
            methodbase.AddCategory(category);

            // when 
            service.MoveCategory(categoryName, methodbase.Name, destinationCategoryName);

            // then
            methodRepository.Verify(mock => mock.DeleteCategory(categoryName, methodbase.Name));
            methodRepository.Verify(mock => mock.SaveCategory(category, destinationCategoryName));
        }

        [TestMethod]
        public void ShouldFindMethodbaseElementByName()
        {
            // when
            IMethodbaseElement element = service.FindMethodbaseElementByName(categoryName);
            
            // then
            methodRepository.Verify(mock => mock.FindMethodByName(categoryName));
           
        }

        [TestMethod]
        public void ShouldDeleteCategory()
        {
            // when
            service.DeleteCategory(categoryName, sourceCategoryName);

            // then
            methodRepository.Verify(mock => mock.DeleteCategory(categoryName, sourceCategoryName));
        }

    }
}
