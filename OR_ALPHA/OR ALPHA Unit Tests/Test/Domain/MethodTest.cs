using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.DomainMethodenbank;
using OrAlpha.Test.Domain;

namespace OrAlpha.Test.Domain
{
    [TestClass]
    public class MethodTest
    {
        private const string methodName = "Power LP";
        private const string documentationPath = "C:\\Methodendatenbank\\Methoden\\PowerLP_NEU\\DOC";
        private const string htmlFile = "powerlp.HTM";

        private Method method;

        [TestInitialize]
        public void SetUp()
        {
            method = new MethodStub();
        }

        [TestMethod]
        public void ShouldSetName()
        {
            // when
            method.Name = methodName;

            // then
            Assert.AreEqual(methodName, method.Name);
        }

        [TestMethod]
        public void ShouldSetDocumentationPath()
        {
            // when
            method.DocumentationPath = methodName;

            // then
            Assert.AreEqual(methodName, method.DocumentationPath);
        }

        [TestMethod]
        public void ShouldSetHtmlFile()
        {
            // when
            method.HtmlFile = htmlFile;

            // then
            Assert.AreEqual(htmlFile, method.HtmlFile);
        }

    }
}
