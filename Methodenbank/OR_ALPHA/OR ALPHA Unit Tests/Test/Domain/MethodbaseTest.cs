using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.DomainMethodenbank;
using System.Collections.Generic;

namespace OrAlpha.Test.Domain
{
    [TestClass]
    public class MethodbaseTest
    {
        private const string categoryName = "Solver";
        private const string methodName = "Primal-Dual-Wandler";
        private const string adminUserName = "grützinator";
        private const string adminPassword = "password";

        private Methodbase methodbase;
        private Category category;
        private Method method;

        [TestInitialize]
        public void SetUp()
        {
            methodbase = new Methodbase();
            category = FakeCategory();
            method = FakeMethod();
            category.AddMethod(method);
            methodbase.AddCategory(category);
        }

        private Category FakeCategory()
        {
            Category category = new Category();
            category.Name = categoryName;
            return category;
        }

        private Method FakeMethod()
        {
            Method method = new NativeMethod();
            method.Name = methodName;
            return method;
        }

        [TestMethod]
        public void ShouldFindMethodByName()
        {
          Assert.AreEqual(method, methodbase.FindMethodByName(method.Name));
        }

        [TestMethod]
        public void ShouldFindMethodbaseRootIfMethodBaseNameIsGiven()
        {
            // when
            CategoryContainer container = methodbase.FindCategoryContainerByName(methodbase.Name);
            
            // then
            Assert.AreEqual(methodbase, container);
        }

        [TestMethod]
        public void ShouldFindCategoryContainerByName()
        {
            // when
            CategoryContainer container = methodbase.FindCategoryContainerByName(categoryName);

            // then
            Assert.AreEqual(category, container);
        }

        [TestMethod]
        public void ShouldAuthenticateAdminUser()
        {
            // given
            methodbase.AdminUserName = adminUserName;
            methodbase.AdminPassword = adminPassword;

            // when
            bool isAuthenticated = methodbase.AuthenticateAdminUser(adminUserName, adminPassword);

            // then
            Assert.IsTrue(isAuthenticated);
         }

        [TestMethod]
        public void ShouldChangeAdminPassword()
        {
            // given
            string newPassword = "newPassword";
            methodbase.ChangeAdminPassword(newPassword);

            // when
            bool isAuthenticated = methodbase.AuthenticateAdminUser("admin",newPassword);

            // then
            Assert.IsTrue(isAuthenticated);
        }
    }
}
