using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.DomainMethodenbank;

namespace OrAlpha.Test.Domain
{
    [TestClass]
    public class CategoryTest
    {
        private const string methodName = "Test Method";
        private Category category;
        private Method method;

        [TestInitialize]
        public void SetUp()
        {
            category = new Category();
            method = FakeMethod();
            category.AddMethod(method);
        }

        [TestMethod]
        public void ShouldAddNewMethod()
        {
            Assert.IsTrue(category.Methods.Contains(method));
        }

        [TestMethod]
        public void ShouldRemoveMethod()
        {
            // when
            category.RemoveMethod(methodName);

            // then
            Assert.IsFalse(category.Methods.Contains(method));
        }

        private Method FakeMethod()
        {
            Method method = new MethodStub();
            method.Name = methodName;
            return method;
        }

    }
}
