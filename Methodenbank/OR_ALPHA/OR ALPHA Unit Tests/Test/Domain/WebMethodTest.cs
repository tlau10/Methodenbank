using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.DomainMethodenbank;

namespace OrAlpha.Test.Domain
{
    [TestClass]
    public class WebMethodTest
    {

        private const string url = "http://www.example.com";

        [TestMethod]
        public void ShouldSetUrl()
        {
            // given
            WebMethod method = new WebMethod();

            // when
            method.Url = url;

            // when
            Assert.AreEqual(url, method.Url);
        }

    }
}
